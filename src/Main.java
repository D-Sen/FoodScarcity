import presentation.NotificationLog;

import javax.swing.*;

public class Main {


    public static void createAndShowGui() {
        NotificationLog ui = new NotificationLog();
        JPanel root =  ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.setSize(700,500);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Main method to show GUI
     */

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }

        });
    }
}
