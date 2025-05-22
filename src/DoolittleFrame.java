import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoolittleFrame extends JFrame {
    JTextField txf1, txf2, txf3, txf4, txf5, txf6, txf7, txf8, txf9, txf10, txf11, txf12;
    JLabel e1, e2, e3, x1, x2, x3, x11, x22, x33, x111, x222, x333;
    JTextArea txa1;
    JButton solve;
    Container container;
    GridBagLayout layout;
    JComboBox<Integer> decimalSelector;
    JPanel panel;

    public DoolittleFrame() {

        txa1 = new JTextArea(15, 50);
        txa1.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txa1.setEditable(false);

        txf1 = new JTextField(5);  // C1
        txf2 = new JTextField(5);  // A11
        txf3 = new JTextField(5);  // A12
        txf4 = new JTextField(5);  // A13
        txf5 = new JTextField(5);  // C2
        txf6 = new JTextField(5);  // A21
        txf7 = new JTextField(5);  // A22
        txf8 = new JTextField(5);  // A23
        txf9 = new JTextField(5);  // C3
        txf10 = new JTextField(5); // A31
        txf11 = new JTextField(5); // A32
        txf12 = new JTextField(5); // A33

        e1 = new JLabel("="); e2 = new JLabel("="); e3 = new JLabel("=");
        x1 = new JLabel("x1"); x2 = new JLabel("x2"); x3 = new JLabel("x3");
        x11 = new JLabel("x1"); x22 = new JLabel("x2"); x33 = new JLabel("x3");
        x111 = new JLabel("x1"); x222 = new JLabel("x2"); x333 = new JLabel("x3");


        solve = new JButton("SOLVE");

        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);


        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(txf1); panel1.add(e1);
        panel1.add(txf2); panel1.add(x1);
        panel1.add(txf3); panel1.add(x2);
        panel1.add(txf4); panel1.add(x3);
        addToContainer(panel1, 0, 0);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel2.add(txf5); panel2.add(e2);
        panel2.add(txf6); panel2.add(x11);
        panel2.add(txf7); panel2.add(x22);
        panel2.add(txf8); panel2.add(x33);
        addToContainer(panel2, 0, 1);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel3.add(txf9); panel3.add(e3);
        panel3.add(txf10); panel3.add(x111);
        panel3.add(txf11); panel3.add(x222);
        panel3.add(txf12); panel3.add(x333);
        addToContainer(panel3, 0, 2);

        panel = new JPanel();

        decimalSelector = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});
        decimalSelector.setSelectedIndex(0);


        panel.add(solve);
        panel.add(decimalSelector);

        addToContainer(panel, 0, 3);
        addToContainer(new JScrollPane(txa1), 0, 4);



        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double c1 = Double.parseDouble(txf1.getText());
                    double a11 = Double.parseDouble(txf2.getText());
                    double a12 = Double.parseDouble(txf3.getText());
                    double a13 = Double.parseDouble(txf4.getText());

                    double c2 = Double.parseDouble(txf5.getText());
                    double a21 = Double.parseDouble(txf6.getText());
                    double a22 = Double.parseDouble(txf7.getText());
                    double a23 = Double.parseDouble(txf8.getText());

                    double c3 = Double.parseDouble(txf9.getText());
                    double a31 = Double.parseDouble(txf10.getText());
                    double a32 = Double.parseDouble(txf11.getText());
                    double a33 = Double.parseDouble(txf12.getText());

                    StringBuilder sb = new StringBuilder();
                    sb.append("Matrix A and Vector C:\n");
                    sb.append(String.format("A = [%.2f %.2f %.2f]\tC = [%.2f]\n", a11, a12, a13, c1));
                    sb.append(String.format("    [%.2f %.2f %.2f]\t    [%.2f]\n", a21, a22, a23, c2));
                    sb.append(String.format("    [%.2f %.2f %.2f]\t    [%.2f]\n\n", a31, a32, a33, c3));

                    double u11 = a11;
                    double u12 = a12;
                    double u13 = a13;

                    double l21 = a21 / u11;
                    double l31 = a31 / u11;

                    double u22 = a22 - l21 * u12;
                    double u23 = a23 - l21 * u13;

                    double l32 = (a32 - l31 * u12) / u22;
                    double u33 = a33 - l31 * u13 - l32 * u23;
                    sb.append("Calculated Values:\n");
                    sb.append(String.format("U11 = %.3f, U12 = %.3f, U13 = %.3f\n", u11, u12, u13));
                    sb.append(String.format("U22 = %.3f, U23 = %.3f, U33 = %.3f\n", u22, u23, u33));
                    sb.append(String.format("L21 = %.3f, L31 = %.3f, L32 = %.3f\n\n", l21, l31, l32));

                    sb.append("LU Decomposition:\n");
                    sb.append(String.format("L = [1.000 0.000 0.000]\n"));
                    sb.append(String.format("    [%.3f 1.000 0.000]\n", l21));
                    sb.append(String.format("    [%.3f %.3f 1.000]\n", l31, l32));
                    sb.append("\n");
                    sb.append(String.format("U = [%.3f %.3f %.3f]\n", u11, u12, u13));
                    sb.append(String.format("    [0.000 %.3f %.3f]\n", u22, u23));
                    sb.append(String.format("    [0.000 0.000 %.3f]\n\n", u33));

                    double r1 = c1;
                    double r2 = c2 - l21 * r1;
                    double r3 = c3 - l31 * r1 - l32 * r2;

                    sb.append("Forward Substitution:\n");
                    sb.append(String.format("R1 = %.3f\n", r1));
                    sb.append(String.format("R2 = %.3f\n", r2));
                    sb.append(String.format("R3 = %.3f\n\n", r3));

                    double x3 = r3 / u33;
                    double x2 = (r2 - u23 * x3) / u22;
                    double x1 = (r1 - u12 * x2 - u13 * x3) / u11;

                    sb.append("Backward Substitution (Solution):\n");
                    sb.append(String.format("x1 = %.5f\n", x1));
                    sb.append(String.format("x2 = %.5f\n", x2));
                    sb.append(String.format("x3 = %.5f\n", x3));

                    txa1.setText(sb.toString());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(container, "Please enter valid numeric inputs.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setTitle("Doolittle LU Decomposition");
        setSize(650, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addToContainer(Component c, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        container.add(c, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DoolittleFrame::new);
    }
}
