

import javax.swing.*;
import java.awt.*;

public class studentapp {

    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new studentapp().show());
    }

    private void show() {
        frame = new JFrame("Student Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Simple label + button for now
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome to Student Database UI", JLabel.CENTER);
        JButton btn = new JButton("Test Button");

        panel.add(label, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
