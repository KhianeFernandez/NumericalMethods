import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionFrame extends JFrame {

    JLabel FunctionLabel;
    JButton BisectionButton, SecantButton, DoolittleButton, GaussButton, SimpsonButton;
    Container container;
    GridBagLayout layout;

    public OptionFrame (){

        layout = new GridBagLayout();
        container = this.getContentPane();
        container.setLayout(layout);

//        FunctionLabel = new JLabel("F(x) = " + Function);
        BisectionButton = new JButton("BISECTION METHOD");
        SecantButton = new JButton("SECANT METHOD");
        DoolittleButton = new JButton("DOOLITTLE METHOD");
        GaussButton = new JButton("Gauss-Seidel METHOD");
        SimpsonButton = new JButton("SIMPSON'S 1/3 METHOD");

        BisectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Function = JOptionPane.showInputDialog(null,"Enter Equation:");

                if(Function == null){
                    JOptionPane.showMessageDialog(null,"No Function");
                }else{
                    new BisectionMethodFrame(Function);
                    dispose();
                }


            }
        });

        SecantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Function = JOptionPane.showInputDialog(null,"Enter Equation:");

                if(Function == null){
                    JOptionPane.showMessageDialog(null,"No Function");
                }else{
                    new SecantMethodFrame(Function);
                    dispose();
                }



            }
        });

        DoolittleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new DoolittleFrame();
                    dispose();

            }
        });

        GaussButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new GaussSeidelFrame();
                    dispose();

            }
        });

        SimpsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Function = JOptionPane.showInputDialog(null,"Enter Equation:");

                if(Function == null){
                    JOptionPane.showMessageDialog(null,"No Function");
                }else{
                    new SimpsonsMethodFrame(Function);
                    dispose();
                }

            }
        });

//        addToContainer(FunctionLabel,0,0);
        addToContainer(BisectionButton,0,1);
        addToContainer(SecantButton,0,2);
        addToContainer(DoolittleButton,0,3);
        addToContainer(GaussButton,0,4);
        addToContainer(SimpsonButton,0,5);

        this.setSize(450,300);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;

        this.setLocation(x,y);
        this.setTitle("OPTION FRAME");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void addToContainer(Component component,
                               int gridx, int gridy){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(4,4,4,4);
        this.add(component,constraints);
    }


}
