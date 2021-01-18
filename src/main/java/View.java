import UI.DisplayPanel;
import UI.SettingsPanel;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class View extends JFrame {
    private DisplayPanel displayPanel;
    private SettingsPanel settingsPanel;
    private DisplayWorker displayWorker = null;

    public View() {
        super("Iris Color Detector");

        this.setLayout(new BorderLayout());

        this.settingsPanel = new SettingsPanel();
        this.displayPanel = new DisplayPanel();

        this.add(new JLabel("Iris color detector"), BorderLayout.NORTH);
        this.add(displayPanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.LINE_END);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        refresh();
    }

    public void refresh(){
        this.revalidate();
        this.repaint();
        this.pack();
    }

    public void startDisplay(MyCamera camera, Function<Mat, BufferedImage> drawDetectedEyes){
        this.displayPanel = new DisplayPanel();
        this.displayWorker = new DisplayWorker(settingsPanel.getShowEyesButton(), displayPanel, camera, drawDetectedEyes);

        displayWorker.execute();
    }

    public void stopDisplay() {
        this.displayWorker.cancel(true);
    }

    public void setEyeColor(String color){
        settingsPanel.setColorEyeText(color);
    }

    public void setListeners(ActionListener aL, WindowListener wL){
        settingsPanel.setActionListener(aL);

        this.addWindowListener(wL);
    }

    public void message(String message, int messageType) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Dialog",
                messageType
        );
    }
}
