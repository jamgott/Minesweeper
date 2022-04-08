import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
/* begin colorchanger */
class ColorBulb extends JComponent {
    private int red, green, blue;
    
    public ColorBulb() {
        red = 0;
        green = 0;
        blue = 0;
    }

    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(new Color(red, green, blue));
        gc.fillRect(0, 0, 300, 300);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}

public class ColorChanger implements Runnable {
    
    @Override
    public void run() {
        JFrame frame = new JFrame("Color Changer");
        frame.setLayout(new BorderLayout());
        
        ColorBulb color = new ColorBulb();
        frame.add(color, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);
        
        JLabel label = new JLabel("Red Color");
        panel.add(label);
        
        JTextField redTextField = new JTextField(5);
        panel.add(redTextField);
        
        JButton button = new JButton("Change Red!");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String r = redTextField.getText();
                int red = Integer.parseInt(r);
                color.setRed(red);
                color.repaint();
            }
        });
        
        color.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                color.setGreen(e.getX() % 255);
                color.setBlue(e.getY() % 255);
                color.repaint();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ColorChanger());
    }
}