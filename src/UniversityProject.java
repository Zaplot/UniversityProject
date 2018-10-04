/****************************************************************************************************************
 * This program is designed to calculate a definite integral of the function f (x) = Ax^3 +Bx^2 + Cx + D
 * using the "Thomas Simpson's Method".
 *
 *
 *
 * May be used for commercial purposes
 * Developed by @Zapletin_Daniil
 * to contact me : zaplot1994@gmail.com
 ****************************************************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.RIGHT;

class gui extends JFrame {
    //Initialization of JPanel module
    private JPanel panel = new JPanel();

    //Initialization of JLabels for JTextFields
    private JLabel labelA = new JLabel("Input A:", RIGHT);
    private JLabel labelB = new JLabel("Input B:", RIGHT);
    private JLabel labelC = new JLabel("Input C:", RIGHT);
    private JLabel labelD = new JLabel("Input D:", RIGHT);
    private JLabel labelX0 = new JLabel("Input X\u2080:", RIGHT);
    private JLabel labelX1 = new JLabel("Input X\u2081:", RIGHT);
    private JLabel labelN = new JLabel("Input N:", RIGHT);


    //Initialization of JTextFields for UI input
    private JTextField textA = new JTextField(6);
    private JTextField textB = new JTextField(6);
    private JTextField textC = new JTextField(6);
    private JTextField textD = new JTextField(6);
    private JTextField textX0 = new JTextField(6);
    private JTextField textX1 = new JTextField(6);
    private JTextField textN = new JTextField(6);

    //Initialization of JButton for confirming input
    private JButton button = new JButton("Confirm");

    gui() {
        super("Solution");
        setLayout(new GridBagLayout());
        JOptionPane.showMessageDialog(null, "\n" +
                        "This program is designed to calculate a definite integral of the function\n" +
                        "f (x) = Ax\u00B3+Bx\u00B2+Cx+D     using the \"Thomas Simpson Method\".\n" +
                        "In the future, you need to enter the coefficients of the function \n" +
                        "the min and max iteration counts  x\u2080 and x\u2081 and the count of integration steps N." +
                        "\n\nIf everything is clear, click OK.",
                "Information", JOptionPane.INFORMATION_MESSAGE);
        //Combining JLabels with JTextFields
        labelA.setLabelFor(textA);
        labelB.setLabelFor(textB);
        labelC.setLabelFor(textC);
        labelD.setLabelFor(textD);
        labelX0.setLabelFor(textX0);
        labelX1.setLabelFor(textX1);
        labelN.setLabelFor(textN);


        button.addActionListener(new ButtonListener());

        panel.add(labelA);
        panel.add(textA);
        panel.add(labelB);
        panel.add(textB);
        panel.add(labelC);
        panel.add(textC);
        panel.add(labelD);
        panel.add(textD);
        panel.add(labelX0);
        panel.add(textX0);
        panel.add(labelX1);
        panel.add(textX1);
        panel.add(labelN);
        panel.add(textN);
        panel.add(button);

        //Initialization settings for window
        setBounds(100, 100, 290, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panel);
    }

    //This method get JTextField Object and return it converted to Double. Also it checks input for illegal format
    private double getTextToDouble(JTextField text) {
        try {
            Double.parseDouble(text.getText());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Input format for : " + text.getText() + " is illegal! Input number!"
                    , "Error message", JOptionPane.ERROR_MESSAGE);
        }
        return Double.parseDouble(text.getText());

    }

    //This method returns 2D mass where mass[0][] is a mass of odd X  and mass[1][0] is a mass of even X
    private double[][] xInitialization() {
       /* if (getTextToDouble(textX0)>=getTextToDouble(textX1))
                JOptionPane.showMessageDialog(null,"X0 count can't be grater than X1 count","Error message",JOptionPane.ERROR_MESSAGE);
                */
        final double h = (getTextToDouble(textX1) - getTextToDouble(textX0)) / ( 2*getTextToDouble(textN));
        double[][] massOfY = new double[2][];
        massOfY[0] = new double[(int) getTextToDouble(textN)];
        massOfY[1] = new double[(int) getTextToDouble(textN) - 1];

        for (int i = 1; i <= 2*getTextToDouble(textN)-1; i++) {
            double x = getTextToDouble(textX0)+i*h;
            if (i%2!=0)
             massOfY[0][i / 2] = x;
            else
             massOfY[1][i / 2 - 1] = x;
        }

        return massOfY;
    }
//This method gets x value and return F(x) value
    private double getValue(double x) {
        return getTextToDouble(textA) * Math.pow(x, 3) + getTextToDouble(textB)
                * Math.pow(x, 2) + getTextToDouble(textC) * x + getTextToDouble(textD);
    }
// This method returns integration value by "Thomas Simpson's" method
    private double solution() {
        final double h = (getTextToDouble(textX1) - getTextToDouble(textX0)) / ( 2*getTextToDouble(textN)) / 3;
        double[][] mass = xInitialization();
        double answer, evenSum = 0, oddSum = 0;
        for (int i = 0; i < 2; i++) {
            for (double element : mass[i]) {
                if (i == 0)
                    oddSum += getValue(element);
                else
                    evenSum += getValue(element);
            }
        }
        answer = h * (getValue(getTextToDouble(textX0)) + 4 * oddSum + 2 * evenSum + getValue(getTextToDouble(textX1)));
        return answer;
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(null, "The result of integrating " +
                    "F(x)=" + (getTextToDouble(textA) == 0 ? "" : getTextToDouble(textA) + "*X\u00B3 + ")
                    + (getTextToDouble(textB) == 0 ? "" : getTextToDouble(textB) + "*X\u00B2 + ")
                    + (getTextToDouble(textC) == 0 ? "" : getTextToDouble(textC) + "*X ")
                    + (getTextToDouble(textD) == 0 ? "" : " + " + getTextToDouble(textD)) + " on a segment from "
                    + getTextToDouble(textX0) + " to " + getTextToDouble(textX1) +
                    " = " + solution(), "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
}


public class UniversityProject{
    public static void main(String[] args) {
        // Thread-safe launch with lambda -> new Runnable()
        EventQueue.invokeLater(() -> {
            gui myGui = new gui();
            myGui.setVisible(true);
        });

    }
}
