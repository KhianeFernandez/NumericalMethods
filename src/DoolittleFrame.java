import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoolittleFrame extends JFrame{
    JTextField txf1, txf2, txf3, txf4, txf5, txf6  ,txf7 ,txf8 ,txf9  ,txf10 ,txf11,txf12;
    JLabel  e1,e2,e3, x1,x2,x3,x11,x22,x33,x111,x222,x333;
    JTextArea txa1;
    JButton solve;
    Container container;
    GridBagLayout layout = new GridBagLayout();

    public DoolittleFrame(){
        txa1 = new JTextArea(10,10);

        txf1 = new JTextField(10);
        txf2 = new JTextField(10);
        txf3 = new JTextField(10);
        txf4 = new JTextField(10);
        txf5 = new JTextField(10);
        txf6 = new JTextField(10);
        txf7 = new JTextField(10);
        txf8 = new JTextField(10);
        txf9 = new JTextField(10);
        txf10 = new JTextField(10);
        txf11 = new JTextField(10);
        txf12 = new JTextField(10);

        e1 = new JLabel("=");
        e2 = new JLabel("=");
        e3 = new JLabel("=");

        x1= new JLabel("x1");
        x2= new JLabel("x2");
        x3= new JLabel("x3");
        x11= new JLabel("x1");
        x22= new JLabel("x2");
        x33= new JLabel("x3");
        x111= new JLabel("x1");
        x222= new JLabel("x2");
        x333= new JLabel("x3");


        solve = new JButton("SOLVE");

        layout = new GridBagLayout();
        container=this.getContentPane();
        container.setLayout(layout);

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(txf1);
        panel1.add(e1);
        panel1.add(txf2);
        panel1.add(x1);
        panel1.add(txf3);
        panel1.add(x2);
        panel1.add(txf4);
        panel1.add(x3);
        addToContainer(panel1,0,0);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel2.add(txf5);
        panel2.add(e2);
        panel2.add(txf6);
        panel2.add(x11);
        panel2.add(txf7);
        panel2.add(x22);
        panel2.add(txf8);
        panel2.add(x33);
        addToContainer(panel2,0,1);

        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel3.add(txf9);
        panel3.add(e3);
        panel3.add(txf10);
        panel3.add(x111);
        panel3.add(txf11);
        panel3.add(x222);
        panel3.add(txf12);
        panel3.add(x333);
        addToContainer(panel3,0,2);

        addToContainer(solve, 0, 3);
        addToContainer(txa1,0,4);


        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double c11 = Double.parseDouble(txf1.getText());
                double a11 = Double.parseDouble(txf2.getText());
                double a12 = Double.parseDouble(txf3.getText());
                double a13 = Double.parseDouble(txf4.getText());
                double c12 = Double.parseDouble(txf5.getText());
                double a21 = Double.parseDouble(txf6.getText());
                double a22 = Double.parseDouble(txf7.getText());
                double a23 = Double.parseDouble(txf8.getText());
                double c13 = Double.parseDouble(txf9.getText());
                double a31 = Double.parseDouble(txf10.getText());
                double a32 = Double.parseDouble(txf11.getText());
                double a33 = Double.parseDouble(txf12.getText());

                StringBuilder sb = new StringBuilder();


                sb.append("\t|| \t").append(a11).append(" \t").append(a12).append(" \t").append(a13).append("\t ||\t \t||\t").append(c11).append("\t||\n");
                sb.append("[A] = \t").append("|| \t").append(a21).append(" \t").append(a22).append(" \t").append(a23).append("\t ||\t").append("[C] = \t||\t").append(c12).append("\t||\n");
                sb.append("\t|| \t").append(a31).append(" \t").append(a32).append(" \t").append(a33).append("\t ||\t \t||\t").append(c13).append("\t||\n");
                sb.append("\n");
                sb.append("|| \t").append("1").append(" \t").append("0").append(" \t").append("0").append("\t ||\t").append("|| \t").append("U11").append(" \t").append("U12").append(" \t").append("U13").append("\t ||").append("\n");
                sb.append("|| \t").append("L21").append(" \t").append("1").append(" \t").append("0").append("\t ||\t").append("|| \t").append("0").append(" \t").append("U22").append(" \t").append("U23").append("\t ||").append("\n");
                sb.append("|| \t").append("L31").append(" \t").append("L32").append(" \t").append("1").append("\t ||\t").append("|| \t").append("0").append(" \t").append("0").append(" \t").append("U33").append("\t ||").append("\n");
                sb.append("\n");
                sb.append("U11  =  ").append(a11).append("\t U12  =  ").append(a12).append("\t U13  =  ").append(a13);
                sb.append("\n");
                double l21 = ((a21)/(a11));
                double l31 = ((a31)/(a11));
                sb.append("L21  =  ").append(l21).append("\t L31  =  ").append(l31).append("\n");
                sb.append("\n");
                double u22 = ((a22)-(l21*a12));
                double u23 = ((a23)-(l21*a13));
                double l32 = (((a32)-(l31*(a12)))/(u22));
                double u33 = ((a33)-(l31*(a13))-(l32*u23));
                sb.append("U22  =  ").append(u22).append("\t U23  =  ").append(u23).append("\t U33  =  ").append(u33).append("\t L32  =  ").append(l32).append("\n");
                sb.append("\n");
                sb.append("\t|| \t").append("1").append(" \t").append("0").append(" \t").append("0").append("\t ||\t \t||\t").append(a11).append(" \t").append(a12).append(" \t").append(a13).append("\t||\n");
                sb.append("[L] = \t").append("|| \t").append(l21).append(" \t").append("1").append(" \t").append("0").append("\t ||\t").append("[U] = \t||\t").append("0").append(" \t").append(u22).append(" \t").append(u23).append("\t||\n");
                sb.append("\t|| \t").append(l31).append(" \t").append(l32).append(" \t").append("1").append("\t ||\t \t||\t").append("0").append(" \t").append("0").append(" \t").append(u33).append("\t||\n");
                sb.append("\n");
                sb.append("FORWARD SUBSTITUTION, [L][R] = [C]");
                sb.append("\n");
                sb.append("|| \t").append("1").append(" \t").append("0").append(" \t").append("0").append("\t ||\t").append("|| \t").append("R1\t ||").append(" \t \t \t").append("|| \t").append(c11).append("\t ||").append("\n");
                sb.append("|| \t").append(l21).append(" \t").append("1").append(" \t").append("0").append("\t ||\t").append("|| \t").append("R2\t ||").append(" \t").append("=").append(" \t||\t").append(c12).append("\t ||").append("\n");
                sb.append("|| \t").append(l31).append(" \t").append(l32).append(" \t").append("1").append("\t ||\t").append("|| \t").append("R3\t ||").append(" \t \t \t").append("|| \t").append(c13).append("\t ||").append("\n");
                sb.append("\n");
                double l211,l311,l322;
                if(l21>0){
                    l211=(l21*-1);
                } else {
                    l211=(l21*-1);
                }
                if(l31>0){
                    l311=(l31*-1);
                } else {
                    l311=(l31*-1);
                }
                if(l32>0){
                    l322=(l32*-1);
                } else {
                    l322=(l32*-1);
                }
                double r1 = c11;
                double r2 = (c12+(l211*r1));
                double r3 = (c13+(l311*r1)+(l322*r2));
                sb.append("R1 = ").append(r1).append("\t");
                sb.append("R2 = ").append(r2).append("\t");
                sb.append("R3 = ").append(r3).append("\t");
                sb.append("\n");
                sb.append("FORWARD SUBSTITUTION, [R] = [U][X]");
                sb.append("\n");
                sb.append("|| \t").append(r1).append("\t || \t \t").append("\t|| \t").append(a11).append(" \t").append(a12).append(" \t").append(a13).append("\t ||\t").append("|| \t").append("X1\t ||").append("\n");
                sb.append("|| \t").append(r2).append("\t || \t =").append("\t|| \t").append("0").append(" \t").append(u22).append(" \t").append(u23).append("\t ||\t").append("|| \t").append("X2\t ||").append("\n");
                sb.append("|| \t").append(r3).append("\t || \t \t").append("\t|| \t").append("0").append(" \t").append("0").append(" \t").append(u33).append("\t ||\t").append("|| \t").append("X3\t ||").append("\n");
                sb.append("\n");
                double x3,x2,x1,u233,a122,a133;
                if(u23>0){
                    u233=(u23*-1);
                } else {
                    u233=(u23*-1);
                }
                if(a12>0){
                    a122=(a12*-1);
                } else {
                    a122=(a12*-1);
                }
                if(a13>0){
                    a133=(a13*-1);
                } else {
                    a133=(a13*-1);
                }
                x3=(r3/u33);
                x2=((r2+(u233*x3))/u22);
                x1=((r1+(a122*x2)+(a133*x3))/a11);
                sb.append("x3 = ").append(x3).append("\t");
                sb.append("x2 = ").append(x2).append("\t");
                sb.append("x1 = ").append(x1).append("\t");





                txa1.setText(String.valueOf(sb));

            }
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);


    }

    public void  addToContainer(Component component, int gridx, int gridy){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = gridx;
        c.gridy = gridy;
        container.add(component,c);
    }

}

