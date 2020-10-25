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

    private class ImagePanel extends JPanel {
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
            super.paintComponent(g);
            int width = image.getWidth();
            int height = image.getHeight();
            g.drawImage(image, 0, 0, width, height, null);
        }
    }
}
