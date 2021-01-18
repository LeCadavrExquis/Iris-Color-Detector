import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;

public class MyCamera {
    protected FrameGrabber grabber;
    protected OpenCVFrameConverter.ToMat toMatConverter;
    protected Java2DFrameConverter toBufferedImageConverter;
    private boolean isConnected = false;

    public MyCamera()  {
        this.toMatConverter = new OpenCVFrameConverter.ToMat();
        this.toBufferedImageConverter = new Java2DFrameConverter();
    }

    public BufferedImage grabBufferedImage() throws FrameGrabber.Exception {
        return toBufferedImageConverter.convert(grabber.grab());
    }

    public Mat grabImage() throws FrameGrabber.Exception {
        return toMatConverter.convert(grabber.grab());
    }

    public BufferedImage convertMat2BufferedImage(Mat image) {
        return toBufferedImageConverter.convert(toMatConverter.convert(image));
    }

    public void start() throws FrameGrabber.Exception {
        this.grabber = FrameGrabber.createDefault(0);
        grabber.start();

        isConnected = true;
    }

    public void stop() throws FrameGrabber.Exception {
        grabber.stop();

        isConnected = false;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
