import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquationFrame extends JFrame {

    JLabel FofXLabel;
    JTextField FunctionField;

    JButton SubmitButton;
    FlowLayout layout;
    Container container;

    public EquationFrame(){

        layout = new FlowLayout();
        container = this.getContentPane();
        container.setLayout(layout);

        FofXLabel = new JLabel("F(x) = ");

        Font italic = FofXLabel.getFont();
        Font italicFont = new Font(italic.getFamily(),Font.ITALIC,italic.getSize());

        FofXLabel.setFont(italicFont);

        FunctionField = new JTextField(15);
        SubmitButton = new JButton("SUBMIT");

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!FunctionField.getText().isEmpty()){
                    new OptionFrame(FunctionField.getText());
                    dispose();
                }

            }
        });

        container.add(FofXLabel);
        container.add(FunctionField);
        container.add(SubmitButton);

        this.setSize(350,100);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;


        this.setTitle("EQUATION FRAME");
        this.setLocation(x,y);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
