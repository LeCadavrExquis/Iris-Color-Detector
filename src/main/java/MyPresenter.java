import detector.Detector;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.image.BufferedImage;

public class MyPresenter {
    private MyView view;
    private Detector detector;
    private MyCamera camera;

    private MyPresenter(MyView view, Detector detector, MyCamera camera) {
        this.view = view;
        this.detector = detector;
        this.camera = camera;
        startCanvas();
    }

    public MyPresenter(MyView view) throws FrameGrabber.Exception {
        this(view, new Detector(), new MyCamera());
    }

    public String getIrisColor() throws FrameGrabber.Exception {
        return detector.getIrisColor(camera.grabImage()).toString();
    }

    private void startCanvas() {
        while (view.isVisible()) {
            try {
                BufferedImage grabbedImage = camera.grabBufferedImage();
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
}
