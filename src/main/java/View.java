import UI.DisplayPanel;
import UI.PresentationPanel;
import UI.SettingsPanel;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Function;

public final class View extends JFrame implements ActionListener{
    private JPanel displayPanel;
    private SettingsPanel settingsPanel;
    private DisplayWorker displayWorker = null;
    private PresentationMode presentationMode = null;
    private int currentStep = 1;

    public View() {
        super("Iris Color Detector");

        this.setLayout(new BorderLayout());

        this.settingsPanel = new SettingsPanel();

        JPanel panel = new JPanel();
        panel.add(new DisplayPanel());
        this.displayPanel = panel;

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
        this.displayPanel.repaint();
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
        settingsPanel.setActionListener(aL, this::actionPerformed);

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
        return (DisplayPanel) displayPanel.getComponent(0);
    }

    public void setDisplayPanel(JPanel panel){
        this.displayPanel.remove(0);
        this.displayPanel.add(panel);
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public void setPresentationMode(PresentationMode presentationMode) {
        this.presentationMode = presentationMode;
        this.setPresentationStep(1);
        this.refresh();
    }

    public void setPresentationStep(int step){
        switch (step) {
            case 1 :
                this.setDisplayPanel(new PresentationPanel(
                        PresentationMode.stepsDict.get(1),
                        new DisplayPanel(this.presentationMode.getOriginalImage()),
                        null
                ));
                break;
            case 2:
                this.setDisplayPanel(new PresentationPanel(
                        PresentationMode.stepsDict.get(2),
                        new DisplayPanel(this.presentationMode.getDetectedEyes()),
                        null
                ));
                break;
            case 3:
                this.setDisplayPanel(new PresentationPanel(
                        PresentationMode.stepsDict.get(3),
                        new DisplayPanel(this.presentationMode.getCutedEyes().get(0)),
                        new DisplayPanel(this.presentationMode.getCutedEyes().get(1))
                ));
                break;
            case 4:
                this.setDisplayPanel(new PresentationPanel(
                        PresentationMode.stepsDict.get(4),
                        new DisplayPanel(this.presentationMode.getFilteredEyes().get(0)),
                        new DisplayPanel(this.presentationMode.getFilteredEyes().get(1))
                ));
                break;
        }
        this.refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "step_forward":
                currentStep = ++currentStep > 4 ? 4 : currentStep;
                this.setPresentationStep(currentStep);
                break;
            case "step_backward":
                currentStep = --currentStep < 1 ? 1 : currentStep;
                this.setPresentationStep(currentStep);
                break;
        }
    }
}
