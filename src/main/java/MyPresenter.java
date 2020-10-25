import detector.Detector;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;

public class MyPresenter {
    private MyView view;
    private Detector detector;
    private MyCamera camera;

    private MyPresenter(MyView view, Detector detector, MyCamera camera) {
        this.view = view;
        this.detector = detector;
        this.camera = camera;
        startCameraDisplay();
    }

    public MyPresenter(MyView view) throws FrameGrabber.Exception {
        this(view, new Detector(), new MyCamera());
    }

    public String getIrisColor() throws FrameGrabber.Exception {
        return detector.getIrisColor(camera.grabImage()).toString();
    }

    private void startCameraDisplay() {
        while (view.isVisible()) {
            try {
                BufferedImage grabbedImage = camera.grabBufferedImage();
                Mat img = detector.drawDetectedEyes(camera.grabImage());
                view.showImage(camera.convertMat2BufferedImage(img));
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
}
