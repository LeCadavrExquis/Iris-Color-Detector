import org.bytedeco.librealsense.frame;

import javax.swing.*;
import java.awt.*;

public class MyView extends JFrame {
    //TODO: implement UI
    JPanel canvasPanel = new JPanel();

    public MyView() {
        this.setSize(550, 300);
        this.setVisible(true);
        this.add(canvasPanel);
    }

    public void paint(Graphics g) {
        g.drawString("A myFrame object", 10, 50);
    }

    public void setCanvas(Canvas canvas) {
        canvasPanel.add(canvas);
    }
}
