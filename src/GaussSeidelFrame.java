
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaussSeidelFrame extends JFrame {

    JLabel lblA11, lblA12, lblA13, lblB1;
    JLabel lblA21, lblA22, lblA23, lblB2;
    JLabel lblA31, lblA32, lblA33, lblB3;
    JLabel lblEa, lblDecimalPlaces, lblEstimated;
    JTextField tfA11, tfA12, tfA13, tfB1;
    JTextField tfA21, tfA22, tfA23, tfB2;
    JTextField tfA31, tfA32, tfA33, tfB3;
    JTextField tfEa;
    JComboBox<Integer> decimalComboBox;
    JButton btnSolve;
    JTable table;
    GaussSeidelTableModel model;
    int decimalPlaces = 3;

    public GaussSeidelFrame() {
        setTitle("GAUSS-SEIDEL METHOD CALCULATOR");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        lblA11 = new JLabel("a11");
        lblA12 = new JLabel("a12");
        lblA13 = new JLabel("a13");
        lblB1 = new JLabel("b1");

        lblA21 = new JLabel("a21");
        lblA22 = new JLabel("a22");
        lblA23 = new JLabel("a23");
        lblB2 = new JLabel("b2");

        lblA31 = new JLabel("a31");
        lblA32 = new JLabel("a32");
        lblA33 = new JLabel("a33");
        lblB3 = new JLabel("b3");

        tfA11 = new JTextField(7);
        tfA12 = new JTextField(7);
        tfA13 = new JTextField(7);
        tfB1 = new JTextField(7);

        tfA21 = new JTextField(7);
        tfA22 = new JTextField(7);
        tfA23 = new JTextField(7);
        tfB2 = new JTextField(7);

        tfA31 = new JTextField(7);
        tfA32 = new JTextField(7);
        tfA33 = new JTextField(7);
        tfB3 = new JTextField(7);

        lblEa = new JLabel("Ea ≤ ");
        tfEa = new JTextField(7);

        lblDecimalPlaces = new JLabel("Decimal Places: ");
        Integer[] decimalOptions = {1, 2, 3, 4, 5, 6};
        decimalComboBox = new JComboBox<>(decimalOptions);
        decimalComboBox.setSelectedItem(decimalPlaces);
        decimalComboBox.addActionListener(e -> {
            decimalPlaces = (int) decimalComboBox.getSelectedItem();
            model.setDecimalPlaces(decimalPlaces);
            model.fireTableDataChanged();
        });

        btnSolve = new JButton("SOLVE");
        lblEstimated = new JLabel("Estimated Solution: ");

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ipC = new GridBagConstraints();
        ipC.insets = new Insets(2, 2, 2, 2);
        ipC.fill = GridBagConstraints.HORIZONTAL;

        ipC.gridx = 0; ipC.gridy = 0; inputPanel.add(lblA11, ipC);
        ipC.gridx = 1; inputPanel.add(tfA11, ipC);
        ipC.gridx = 2; inputPanel.add(lblA12, ipC);
        ipC.gridx = 3; inputPanel.add(tfA12, ipC);
        ipC.gridx = 4; inputPanel.add(lblA13, ipC);
        ipC.gridx = 5; inputPanel.add(tfA13, ipC);
        ipC.gridx = 6; inputPanel.add(lblB1, ipC);
        ipC.gridx = 7; inputPanel.add(tfB1, ipC);

        ipC.gridx = 0; ipC.gridy = 1; inputPanel.add(lblA21, ipC);
        ipC.gridx = 1; inputPanel.add(tfA21, ipC);
        ipC.gridx = 2; inputPanel.add(lblA22, ipC);
        ipC.gridx = 3; inputPanel.add(tfA22, ipC);
        ipC.gridx = 4; inputPanel.add(lblA23, ipC);
        ipC.gridx = 5; inputPanel.add(tfA23, ipC);
        ipC.gridx = 6; inputPanel.add(lblB2, ipC);
        ipC.gridx = 7; inputPanel.add(tfB2, ipC);

        ipC.gridx = 0; ipC.gridy = 2; inputPanel.add(lblA31, ipC);
        ipC.gridx = 1; inputPanel.add(tfA31, ipC);
        ipC.gridx = 2; inputPanel.add(lblA32, ipC);
        ipC.gridx = 3; inputPanel.add(tfA32, ipC);
        ipC.gridx = 4; inputPanel.add(lblA33, ipC);
        ipC.gridx = 5; inputPanel.add(tfA33, ipC);
        ipC.gridx = 6; inputPanel.add(lblB3, ipC);
        ipC.gridx = 7; inputPanel.add(tfB3, ipC);

        ipC.gridx = 0; ipC.gridy = 3;
        inputPanel.add(lblEa, ipC);
        ipC.gridx = 1;
        inputPanel.add(tfEa, ipC);
        ipC.gridx = 2;
        inputPanel.add(lblDecimalPlaces, ipC);
        ipC.gridx = 3;
        inputPanel.add(decimalComboBox, ipC);
        ipC.gridx = 4;
        inputPanel.add(btnSolve, ipC);

        c.gridx = 0; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL;
        container.add(inputPanel, c);

        model = new GaussSeidelTableModel();
        model.setDecimalPlaces(decimalPlaces);
        table = new JTable(model);
        c.gridy = 1;
        c.gridwidth = 8;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        container.add(new JScrollPane(table), c);

        c.gridy = 2;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        container.add(lblEstimated, c);

        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveGaussSeidel();
            }
        });

        setVisible(true);
    }

    private void solveGaussSeidel() {
        model.clearAnswers();

        try {
            double[][] A = new double[3][3];
            double[] B = new double[3];
            A[0][0] = Double.parseDouble(tfA11.getText());
            A[0][1] = Double.parseDouble(tfA12.getText());
            A[0][2] = Double.parseDouble(tfA13.getText());
            B[0] = Double.parseDouble(tfB1.getText());

            A[1][0] = Double.parseDouble(tfA21.getText());
            A[1][1] = Double.parseDouble(tfA22.getText());
            A[1][2] = Double.parseDouble(tfA23.getText());
            B[1] = Double.parseDouble(tfB2.getText());

            A[2][0] = Double.parseDouble(tfA31.getText());
            A[2][1] = Double.parseDouble(tfA32.getText());
            A[2][2] = Double.parseDouble(tfA33.getText());
            B[2] = Double.parseDouble(tfB3.getText());

            double tolerance = Double.parseDouble(tfEa.getText());

            double[] x = new double[3];
            double[] prevX = new double[3];
            double[] ea = new double[3];

            for (int i = 0; i < 3; i++) {
                x[i] = 0;
                prevX[i] = 0;
                ea[i] = Double.MAX_VALUE;
            }

            int iteration = 0;
            boolean converged;

            do {
                iteration++;
                for (int i = 0; i < 3; i++) {
                    prevX[i] = x[i];
                }

                x[0] = (B[0] - A[0][1] * x[1] - A[0][2] * x[2]) / A[0][0];
                x[1] = (B[1] - A[1][0] * x[0] - A[1][2] * x[2]) / A[1][1];
                x[2] = (B[2] - A[2][0] * x[0] - A[2][1] * x[1]) / A[2][2];

                for (int i = 0; i < 3; i++) {
                    ea[i] = Math.abs(x[i] - prevX[i]);
                }

                model.addAnswer(new GaussSeidelAnswer(iteration, x.clone(), ea.clone()));

                converged = ea[0] <= tolerance && ea[1] <= tolerance && ea[2] <= tolerance;

                if (iteration >= 100) {
                    JOptionPane.showMessageDialog(this, "Max iterations reached without convergence.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            } while (!converged);

            lblEstimated.setText(String.format("Estimated Solution: x₁= %." + decimalPlaces + "f, x₂= %." + decimalPlaces + "f, x₃= %." + decimalPlaces + "f",
                    x[0], x[1], x[2]));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, "Math error: " + ex.getMessage(), "Math Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GaussSeidelFrame::new);
    }
}
