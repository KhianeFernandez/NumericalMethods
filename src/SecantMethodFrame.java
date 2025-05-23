import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SecantMethodFrame extends JFrame {

    JLabel FunctionLabel, x0Label, x1Label, EaLabel, EstimatedRoot, decimalLabel;
    JTextField x0Field, x1Field, EaField;
    JButton SolveButton;
    JPanel Panel1, Panel2;
    JComboBox<Integer> decimalComboBox;
    Container container;
    JTable Table;
    SecantAbstractTableModel Model;
    GridBagLayout layout;

    String functionString;
    Expression expression;
    int decimalPlaces = 3;

    public SecantMethodFrame(String Function) {
        this.functionString = Function;
        try {
            String formattedFunction = Function
                    .replaceAll("([0-9])([x])", "$1*$2")
                    .replaceAll("e", "2.718281828459045");

            expression = new ExpressionBuilder(formattedFunction)
                    .variable("x")
                    .build();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid function: " + e.getMessage(),
                    "Function Error", JOptionPane.ERROR_MESSAGE);
        }

        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);

        Panel1 = new JPanel();
        FunctionLabel = new JLabel("F(x) = " + Function);
        FunctionLabel.setSize(10, 10);
        addToPanel(Panel1, FunctionLabel, 0, 0);
        addToContainer(Panel1, 0, 0);

        Panel2 = new JPanel();
        x0Label = new JLabel("x₀ = ");
        x0Field = new JTextField(5);
        x1Label = new JLabel("x₁ = ");
        x1Field = new JTextField(5);
        EaLabel = new JLabel("Ea ≤ ");
        EaField = new JTextField(5);
        SolveButton = new JButton("SOLVE");
        EstimatedRoot = new JLabel("Estimated Root = ");
        decimalLabel = new JLabel("Decimal Places: ");

        Integer[] decimalOptions = {1, 2, 3, 4, 5, 6};
        decimalComboBox = new JComboBox<>(decimalOptions);
        decimalComboBox.setSelectedItem(decimalPlaces);
        decimalComboBox.addActionListener(e -> {
            decimalPlaces = (int) decimalComboBox.getSelectedItem();
            Model.fireTableDataChanged();
        });

        addToPanel(Panel2, x0Label, 0, 0);
        addToPanel(Panel2, x0Field, 1, 0);
        addToPanel(Panel2, x1Label, 2, 0);
        addToPanel(Panel2, x1Field, 3, 0);
        addToPanel(Panel2, EaLabel, 4, 0);
        addToPanel(Panel2, EaField, 5, 0);
        addToPanel(Panel2, decimalLabel, 6, 0);
        addToPanel(Panel2, decimalComboBox, 7, 0);
        addToPanel(Panel2, SolveButton, 8, 0);
        addToContainer(Panel2, 0, 1);

        Model = new SecantAbstractTableModel() {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                SecantAnswer answer = SecantAnswers.get(rowIndex);
                String format = "%." + decimalPlaces + "f";

                switch (columnIndex) {
                    case 0: return answer.getN();
                    case 1: return String.format(format, answer.getX0());
                    case 2: return String.format(format, answer.getX1());
                    case 3: return String.format(format, answer.getFx0());
                    case 4: return String.format(format, answer.getFx1());
                    case 5: return String.format(format, answer.getX2());
                    case 6:
                        if (rowIndex == 0) return "";
                        return String.format(format, answer.getEa());
                    default: return null;
                }
            }
        };

        Table = new JTable(Model);
        addToContainer(new JScrollPane(Table), 0, 2);
        addToContainer(EstimatedRoot, 0, 3);

        SolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x0 = Double.parseDouble(x0Field.getText());
                    double x1 = Double.parseDouble(x1Field.getText());
                    double tolerance = Double.parseDouble(EaField.getText());

                    performSecant(x0, x1, tolerance);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SecantMethodFrame.this,
                            "Invalid input for x₀, x₁, or Ea.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        x0Field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    new OptionFrame();
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        this.setSize(700, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setTitle("SECANT METHOD CALCULATOR");
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private double evaluateFunction(double xValue) {
        try {
            return expression.setVariable("x", xValue).evaluate();
        } catch (ArithmeticException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error evaluating function at x=" + xValue +
                    ": " + e.getMessage(), "Evaluation Error", JOptionPane.ERROR_MESSAGE);
            return Double.NaN;
        }
    }

    private void performSecant(double x0, double x1, double tolerance) {
        Model.SecantAnswers.clear();
        Model.fireTableDataChanged();

        double fx0 = evaluateFunction(x0);
        double fx1 = evaluateFunction(x1);

        if (Double.isNaN(fx0) || Double.isNaN(fx1)) return;

        int n = 0;
        double x2 = 0;
        double prevX2 = Double.NaN;

        while (true) {
            n++;

            if (Math.abs(fx1 - fx0) < 1e-10) {
                JOptionPane.showMessageDialog(this,
                        "Division by zero encountered. Please choose different initial points.",
                        "Calculation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            x2 = x1 - (fx1 * (x1 - x0)) / (fx1 - fx0);

            if (n == 1) {
                Model.addIterativeAnswer(new SecantAnswer(n, x0, x1, fx0, fx1, x2, Double.NaN));
            } else {
                double ea = Math.abs(x2 - prevX2);
                Model.addIterativeAnswer(new SecantAnswer(n, x0, x1, fx0, fx1, x2, ea));

                if (ea <= tolerance) {
                    break;
                }
            }

            prevX2 = x2;
            x0 = x1;
            x1 = x2;
            fx0 = fx1;
            fx1 = evaluateFunction(x2);

            if (Double.isNaN(fx1)) return;

            if (n >= 100) {
                JOptionPane.showMessageDialog(this,
                        "Maximum iterations reached without convergence.",
                        "Convergence Error", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }

        EstimatedRoot.setText(String.format("Estimated Root = %." + decimalPlaces + "f", x2));
    }


    public void addToPanel(JPanel panel, Component component,
                           int gridx, int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(3,3,3,3);
        panel.add(component, constraints);
    }

    public void addToContainer(Component component,
                               int gridx, int gridy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(3,3,3,3);
        this.add(component, constraints);
    }
}
