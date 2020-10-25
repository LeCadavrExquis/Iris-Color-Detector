package detector;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC1;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class Detector {
    private CascadeClassifier classifier;

    public Detector() {
        try {
            String fileName = "haarcascade_eye.xml";
            URL url = Detector.class.getClassLoader().getResource(fileName);
            File file = Loader.cacheResource(url);
            String classifierName = file.getAbsolutePath();
            classifier = new CascadeClassifier(classifierName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mat drawDetectedEyes(@NotNull Mat image) {
        Mat grayImage = new Mat(image.rows(), image.cols(), CV_8UC1);
        cvtColor(image, grayImage, CV_BGR2GRAY);
        RectVector faces = new RectVector();
        classifier.detectMultiScale(grayImage, faces);
        long total = faces.size();

        for (long i = 0; i < total; i++) {
            Rect r = faces.get(i);
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            rectangle(image, new Point(x, y), new Point(x + w, y + h), Scalar.RED, 1, CV_AA, 0);
        }

        return image;
    }

    public IrisColor getIrisColor(Mat mat) {
        //TODO implement getIrisColor(Mat)
        return null;
    }
}
