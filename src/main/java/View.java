import UI.DisplayPanel;
import UI.SettingsPanel;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Function;

public final class View extends JFrame {
    private static DisplayPanel displayPanel;
    private SettingsPanel settingsPanel;
    private DisplayWorker displayWorker = null;

    public View() {
        super("Iris Color Detector");

        this.setLayout(new BorderLayout());

        this.settingsPanel = new SettingsPanel();
        this.displayPanel = new DisplayPanel();

        JLabel label = new JLabel("Iris color detector", SwingConstants.CENTER);
        label.setFont(new Font("MS Gothic", Font.ITALIC, 30));

        this.add(label, BorderLayout.NORTH);
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

    public void startDisplay(MyCamera camera,Function<Mat, BufferedImage> drawDetectedEyes){
        this.displayWorker = new DisplayWorker(this, camera, drawDetectedEyes);
        try {
            this.getDisplayPanel().setImage(camera.grabBufferedImage());
            this.pack();
        } catch (FrameGrabber.Exception e) {
        }
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

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }
}
