import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

public class MyCamera {
    protected FrameGrabber grabber;
    protected CanvasFrame canvasFrame;
    protected OpenCVFrameConverter.ToMat converter;

    public MyCamera() throws FrameGrabber.Exception {
        this.grabber = FrameGrabber.createDefault(0);
        grabber.start();
        this.canvasFrame = new CanvasFrame("SomeTitle", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        this.converter = new OpenCVFrameConverter.ToMat();
    }


    public Mat grabImage() throws FrameGrabber.Exception {
        return converter.convert(grabber.grab());
    }

    public void stop() throws FrameGrabber.Exception {
        canvasFrame.dispose();
        grabber.stop();
    }
}
