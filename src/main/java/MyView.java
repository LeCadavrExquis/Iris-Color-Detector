import UI.ImagePanel;
import UI.SettingsPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyView extends JFrame {
    ImagePanel imagePanel = new ImagePanel();
    SettingsPanel settingsPanel = new SettingsPanel();

    public MyView() {
        super("Iris Color Detector");
        this.setSize(740, 580);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(new JLabel("Iris color detector"), BorderLayout.NORTH);
        this.add(imagePanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.LINE_END);
    }

    public void showImage(BufferedImage image) {
        imagePanel.setImage(image);
        this.revalidate();
        this.repaint();
    }

    public void setEyeColor(String color){
        settingsPanel.setColorEyeText(color);
    }

    public void setActionListener(ActionListener ac){
        settingsPanel.setActionListener(ac);
    }

}
