import UI.DisplayPanel;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;

class DisplayWorker extends SwingWorker<Void, BufferedImage> {
    private JRadioButton radioButton;
    private DisplayPanel displayPanel;
    private MyCamera camera;
    private Function<Mat, BufferedImage> drawDetectedEyes;

    public DisplayWorker(JRadioButton radioButton, DisplayPanel displayPanel, MyCamera camera, Function<Mat, BufferedImage> drawDetectedEyes) {
        super();
        this.radioButton = radioButton;
        this.displayPanel = displayPanel;
        this.camera = camera;
        this.drawDetectedEyes = drawDetectedEyes;
    }

    @Override
    protected Void doInBackground(){
        while (!isCancelled()) {
            try {
                BufferedImage grabbedImage = radioButton.isSelected() ?
                        drawDetectedEyes.apply(camera.grabImage()) :
                        camera.grabBufferedImage();

                publish(grabbedImage);

            } catch (FrameGrabber.Exception e ) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void process(List<BufferedImage> chunks) {
        BufferedImage lastFrame = chunks.get(chunks.size() - 1);
        displayPanel.setImage(lastFrame);
    }
}