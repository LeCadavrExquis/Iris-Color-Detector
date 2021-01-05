package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel() {
        try {
            //TODO: change it to input stream
            this.image = ImageIO.read(new File("src/main/resources/rys1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        int width = image.getWidth();
        int height = image.getHeight();

        this.setPreferredSize(new Dimension(width, height));

        g2d.drawImage(image, 0, 0, width, height, null);
    }
}
