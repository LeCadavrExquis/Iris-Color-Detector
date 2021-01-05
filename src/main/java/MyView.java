import UI.ImagePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyView extends JFrame {
    ImagePanel imagePanel = new ImagePanel();

    public MyView() {
        super("Iris Color Detector");
        this.setSize(740, 580);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(imagePanel);
    }

    public void showImage(BufferedImage image) {
        imagePanel.setImage(image);
        this.revalidate();
        this.repaint();
    }


}
