import detector.Detector;
import detector.EyesNotFoundException;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MyPresenter implements ActionListener {
    private MyView view;
    private Detector detector;
    private MyCamera camera;
    private boolean showEyes = false;

    private MyPresenter(MyView view, Detector detector, MyCamera camera) {
        this.view = view;
        this.detector = detector;
        this.camera = camera;

        view.setActionListener(this::actionPerformed);

        startCameraDisplay();
    }

    public MyPresenter(MyView view) throws FrameGrabber.Exception {
        this(view, new Detector(), new MyCamera());
    }

    public String getIrisColor() throws FrameGrabber.Exception {
        String colorName = "";
        int failCounter = 0;
        while (colorName.isEmpty()) {
            try {
                colorName = detector.getIrisColor(camera.grabImage()).toString();
            } catch (EyesNotFoundException e) {
                failCounter++;
            } finally {
                if (failCounter > 20) {
                    colorName = "cannot define, sorry";
                }
            }
        }
        return colorName;
    }

    private void startCameraDisplay() {
        while (view.isVisible()) {
            try {
                BufferedImage grabbedImage = null;
                if (showEyes){
                    Mat mat = detector.drawDetectedEyes(camera.grabImage());
                    grabbedImage = camera.convertMat2BufferedImage(mat);
                }else{
                    grabbedImage = camera.grabBufferedImage();
                }

                view.showImage(grabbedImage);
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
                break;
            }
        }
        terminate();
    }

    private void terminate() {
        try {
            camera.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "detect":
                try {
                    view.setEyeColor(getIrisColor());
                } catch (FrameGrabber.Exception exception) {
                    exception.printStackTrace();
                }
                break;
            case "showEyes":
                JRadioButton button = (JRadioButton) e.getSource();
                if (button.isSelected()){
                    showEyes = true;
                }else{
                    showEyes = false;
                }
                break;
        }
    }
}
