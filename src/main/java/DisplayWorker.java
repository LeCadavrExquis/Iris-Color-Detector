import UI.DisplayPanel;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

class DisplayWorker extends SwingWorker<String, BufferedImage> {
    private JRadioButton radioButton;
    private DisplayPanel displayPanel;
    private MyCamera camera;
    private Function<Mat, BufferedImage> drawDetectedEyes;

    public DisplayWorker(JRadioButton radioButton, DisplayPanel displayPanel, MyCamera camera, Function<Mat, BufferedImage> drawDetectedEyes) {
        this.radioButton = radioButton;
        this.displayPanel = displayPanel;
        this.camera = camera;
        this.drawDetectedEyes = drawDetectedEyes;
    }

    @Override
    protected String doInBackground(){
        while (!isCancelled()) {
            try {
                BufferedImage grabbedImage = null;
                if (radioButton.isSelected()){
                    grabbedImage = drawDetectedEyes.apply(camera.grabImage());
                }else{
                    grabbedImage = camera.grabBufferedImage();
                }

                displayPanel.setImage(grabbedImage);

            } catch (FrameGrabber.Exception e) {
                return "Camera error:" + e.getMessage();
            }
        }

        return "OK";
    }
}