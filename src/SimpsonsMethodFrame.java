    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;

    import net.objecthunter.exp4j.Expression;
    import net.objecthunter.exp4j.ExpressionBuilder;

    public class SimpsonsMethodFrame extends JFrame {

        JLabel FunctionLabel, aLabel, bLabel, nLabel, decimalLabel;
        JTextField aField, bField, nField;
        JButton SolveButton;
        JPanel Panel1, Panel2;
        JComboBox<Integer> decimalComboBox;
        Container container;
        JTable Table;
        SimpsonsAbstractTableModel Model;
        GridBagLayout layout;

        String functionString;
        Expression expression;
        int decimalPlaces = 3;

        JLabel integralLabel;
        JLabel sumLabel;

        public SimpsonsMethodFrame(String Function) {
            this.functionString = Function;

            try {
                String formattedFunction = Function
                        .replaceAll("([0-9])([a-zA-Z(])", "$1*$2") // Add * between numbers and variables/functions
                        .replaceAll("([)])([a-zA-Z])", "$1*$2")    // Add * between closing parenthesis and function/variable
                        .replaceAll("([a-zA-Z])([0-9])", "$1*$2")  // Add * between function name and number
                        .replaceAll("e", "2.718281828459045");     // Replace 'e' with Euler's number


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

//            addToContainer(sumLabel, 0, 4);

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
            nLabel = new JLabel("n (even) = ");
            nField = new JTextField(5);
            SolveButton = new JButton("SOLVE");

            integralLabel = new JLabel("Integral result: ");
            sumLabel = new JLabel("Sum of F(xi): ");

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
            addToPanel(Panel2, nLabel, 4, 0);
            addToPanel(Panel2, nField, 5, 0);
            addToPanel(Panel2, decimalLabel, 6, 0);
            addToPanel(Panel2, decimalComboBox, 7, 0);
            addToPanel(Panel2, SolveButton, 8, 0);
            addToContainer(Panel2, 0, 1);

            Model = new SimpsonsAbstractTableModel() {
                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    SimpsonsAnswer answer = simpsonsAnswers.get(rowIndex);
                    String format = "%." + decimalPlaces + "f";

                    switch (columnIndex) {
                        case 0: return answer.getXn();
                        case 1: return String.format(format, answer.getX());
                        case 2: return String.format(format, answer.getFx());
                        case 3: return answer.getMod2();
                        case 4: return answer.getRule();
                        case 5: return String.format(format, answer.getFxi());
                        default: return null;
                    }
                }
            };

            Table = new JTable(Model);
            addToContainer(new JScrollPane(Table), 0, 2);
            addToContainer(sumLabel, 0, 3);      // Now appears first
            addToContainer(integralLabel, 0, 4);

            SolveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double a = Double.parseDouble(aField.getText());
                        double b = Double.parseDouble(bField.getText());
                        int n = Integer.parseInt(nField.getText());

                        if (n % 2 != 0) {
                            JOptionPane.showMessageDialog(SimpsonsMethodFrame.this,
                                    "Number of intervals must be even",
                                    "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        performSimpsons(a, b, n);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SimpsonsMethodFrame.this,
                                "Invalid input for a, b, or n", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            aField.addKeyListener(new KeyListener() {
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


            this.setSize(800, 500);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - this.getWidth()) / 2;
            int y = (screenSize.height - this.getHeight()) / 2;
            this.setLocation(x, y);
            this.setTitle("SIMPSON'S 1/3 RULE CALCULATOR");
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

        private void performSimpsons(double a, double b, int n) {
            if (n % 2 != 0) {
                throw new IllegalArgumentException("n must be even for Simpson's Rule");
            }

            Model.simpsonsAnswers.clear();
            Model.fireTableDataChanged();

            double h = (b - a) / n;
            double sum = 0.0;
            double sumFxi = 0.0;

            for (int i = 0; i <= n; i++) {
                double x = a + i * h;
                double fx = evaluateFunction(x);

                int weight;
                if (i == 0 || i == n) {
                    weight = 1;
                } else if (i % 2 == 0) {
                    weight = 2;
                } else {
                    weight = 4;
                }

                sum += weight * fx;
                sumFxi += fx;

                Model.simpsonsAnswers.add(new SimpsonsAnswer("x" + i, x, fx, i % 2, weight, weight * fx));
            }

            double integral = (h / 3.0) * sum;

            String format = "%." + decimalPlaces + "f";
            sumLabel.setText(String.format("Sum of F(xi): " + format, sumFxi));
            integralLabel.setText(String.format("Integral result: " + format, integral));

            Model.fireTableDataChanged();
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