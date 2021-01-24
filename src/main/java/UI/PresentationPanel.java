package UI;

import javax.swing.*;
import java.awt.*;

public class PresentationPanel extends JPanel {
    private DisplayPanel displayPanel1;
    private DisplayPanel displayPanel2;
    private JLabel label;

    public PresentationPanel(String title, DisplayPanel displayPanel1, DisplayPanel displayPanel2) {
        this.displayPanel1 = displayPanel1;
        this.displayPanel2 = displayPanel2;
        this.label = new JLabel(title);

        this.setLayout(new BorderLayout());

        this.label.setFont(new Font("MS Gothic", Font.BOLD, 15));

        this.add(label, BorderLayout.NORTH);
        this.add(displayPanel1, BorderLayout.WEST);
        if (displayPanel2 != null){
            this.add(displayPanel2, BorderLayout.EAST);
        }

        this.revalidate();
        this.repaint();
    }

//    @Override
//    public void repaint() {
//        displayPanel1.repaint();
//        if (displayPanel2 != null){
//            displayPanel2.repaint();
//        }
//        super.repaint();
//    }
}
