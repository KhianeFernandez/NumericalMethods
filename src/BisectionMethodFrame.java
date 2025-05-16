import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class BisectionMethodFrame extends JFrame {

    JLabel FunctionLabel, aLabel, bLabel, EaLabel, EstimatedRoot, decimalLabel;
    JTextField aField, bField, EaField;
    JButton SolveButton;
    JPanel Panel1, Panel2;
    JComboBox<Integer> decimalComboBox;
    Container container;
    JTable Table;
    BisectionAbstractTableModel Model;
    GridBagLayout layout;

    String functionString;
    Expression expression;
    int decimalPlaces = 3;

    public BisectionMethodFrame(String Function) {
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
        aLabel = new JLabel("a = ");
        aField = new JTextField(5);
        bLabel = new JLabel("b = ");
        bField = new JTextField(5);
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

        addToPanel(Panel2, aLabel, 0, 0);
        addToPanel(Panel2, aField, 1, 0);
        addToPanel(Panel2, bLabel, 2, 0);
        addToPanel(Panel2, bField, 3, 0);
        addToPanel(Panel2, EaLabel, 4, 0);
        addToPanel(Panel2, EaField, 5, 0);
        addToPanel(Panel2, decimalLabel, 6, 0);
        addToPanel(Panel2, decimalComboBox, 7, 0);
        addToPanel(Panel2, SolveButton, 8, 0);
        addToContainer(Panel2, 0, 1);

        Model = new BisectionAbstractTableModel() {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                BisectionAnswer answer = BisectionAnswers.get(rowIndex);
                String format = "%." + decimalPlaces + "f";

                switch (columnIndex) {
                    case 0: return answer.getN();
                    case 1: return String.format(format, answer.getX0());
                    case 2: return String.format(format, answer.getX1());
                    case 3: return String.format(format, answer.getX2());
                    case 4: return String.format(format, answer.getFx2());
                    case 5: return String.format(format, answer.getEa());
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
                    double a = Double.parseDouble(aField.getText());
                    double b = Double.parseDouble(bField.getText());
                    double tolerance = Double.parseDouble(EaField.getText());

                    performBisection(a, b, tolerance);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BisectionMethodFrame.this,
                            "Invalid input for a, b, or Ea.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setSize(700, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setTitle("BISECTION METHOD CALCULATOR");
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

    private void performBisection(double a, double b, double tolerance) {
        Model.BisectionAnswers.clear();
        Model.fireTableDataChanged();

        double fa = evaluateFunction(a);
        double fb = evaluateFunction(b);

        if (Double.isNaN(fa) || Double.isNaN(fb)) return;

        if (fa * fb > 0) {
            JOptionPane.showMessageDialog(this,
                    "No root found in interval (f(a) and f(b) have same sign)",
                    "No Root", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int n = 0;
        double xn = 0;
        double previousXn = a;
        double error = Double.MAX_VALUE;

        while (error > tolerance) {
            previousXn = xn;
            xn = (a + b) / 2.0;
            double fxn = evaluateFunction(xn);

            if (Double.isNaN(fxn)) return;

            if (n > 0) {
                error = Math.abs(xn - previousXn);
            }

            Model.addIterativeAnswer(new BisectionAnswer(n + 1, a, b, xn, fxn, n > 0 ? error : Double.NaN));

            if (fxn == 0) break;

            if (fa * fxn < 0) {
                b = xn;
            } else {
                a = xn;
                fa = fxn;
            }
            n++;
        }

        EstimatedRoot.setText(String.format("Estimated Root = %." + decimalPlaces + "f", xn));
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