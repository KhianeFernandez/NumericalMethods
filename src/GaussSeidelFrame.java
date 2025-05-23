import javax.swing.*;
import java.awt.*;

public class GaussSeidelFrame extends JFrame {
    private JTextField[][] coefficientFields;
    private JTextField[] constantFields, initialGuessFields;
    private JTextField errorField;
    private JButton solveButton;
    private JTable resultTable;
    private GaussSeidelTableModel tableModel;

    private final int variablesCount = 3;

    public GaussSeidelFrame() {
        setTitle("Gauss-Seidel Method");
        setLayout(new GridBagLayout());

        coefficientFields = new JTextField[variablesCount][variablesCount];
        constantFields = new JTextField[variablesCount];
        initialGuessFields = new JTextField[variablesCount];

        JPanel inputPanel = new JPanel(new GridLayout(variablesCount + 2, variablesCount + 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("System of Equations"));

        for (int i = 0; i < variablesCount; i++) {
            for (int j = 0; j < variablesCount; j++) {
                coefficientFields[i][j] = new JTextField(4);
                inputPanel.add(coefficientFields[i][j]);
            }
            JLabel label = new JLabel(" = ");
            inputPanel.add(label);
            constantFields[i] = new JTextField(4);
            inputPanel.add(constantFields[i]);
        }

        inputPanel.add(new JLabel("Initial Guess:"));
        for (int i = 0; i < variablesCount; i++) {
            initialGuessFields[i] = new JTextField(4);
            inputPanel.add(initialGuessFields[i]);
        }

        inputPanel.add(new JLabel("Error ≤"));
        errorField = new JTextField("0.01");
        inputPanel.add(errorField);

        solveButton = new JButton("Solve");
        inputPanel.add(solveButton);

        add(inputPanel, createGBC(0, 0));

        tableModel = new GaussSeidelTableModel(variablesCount);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        add(scrollPane, createGBC(0, 1));

        solveButton.addActionListener(e -> solveGaussSeidel());

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void solveGaussSeidel() {
        double[][] A = new double[variablesCount][variablesCount];
        double[] b = new double[variablesCount];
        double[] x = new double[variablesCount];

        try {
            for (int i = 0; i < variablesCount; i++) {
                for (int j = 0; j < variablesCount; j++) {
                    A[i][j] = Double.parseDouble(coefficientFields[i][j].getText());
                }
                b[i] = Double.parseDouble(constantFields[i].getText());
                x[i] = Double.parseDouble(initialGuessFields[i].getText());
            }

            double tolerance = Double.parseDouble(errorField.getText());
            tableModel.clear();

            double[] prev = new double[variablesCount];
            int iteration = 0;
            double error = 100;

            while (error > tolerance && iteration < 100) {
                System.arraycopy(x, 0, prev, 0, variablesCount);

                for (int i = 0; i < variablesCount; i++) {
                    double sum = 0;
                    for (int j = 0; j < variablesCount; j++) {
                        if (i != j) sum += A[i][j] * x[j];
                    }
                    x[i] = (b[i] - sum) / A[i][i];
                }

                error = 0;
                for (int i = 0; i < variablesCount; i++) {
                    error = Math.max(error, Math.abs((x[i] - prev[i]) / x[i]) * 100);
                }

                tableModel.addRow(new GaussSeidelAnswer(++iteration, x, error));
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please fill all fields with valid numbers.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private GridBagConstraints createGBC(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GaussSeidelFrame::new);
    }
}
