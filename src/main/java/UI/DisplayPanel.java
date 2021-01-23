package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class DisplayPanel extends JPanel {
    private BufferedImage image = null;

    public DisplayPanel(){
        super();
        this.setImage(getLoadingImage());
    }

    public void setImage(BufferedImage image) {
        this.image = image != null ? image : getLoadingImage();
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        this.repaint();
        this.revalidate();
    }

    private BufferedImage getLoadingImage() {
        try {
            //TODO: change it to input stream
            return ImageIO.read(new File("src/main/resources/rys1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        if (image != null){
            int width = image.getWidth();
            int height = image.getHeight();

            g2d.drawImage(image, 0, 0, width, height, null);
        }
    }
}
