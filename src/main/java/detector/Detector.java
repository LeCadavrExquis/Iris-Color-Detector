package detector;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.CV_8UC3;

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

    public Mat drawDetectedEyes(@NotNull Mat mat) {
        RectVector eyes = new RectVector();
        classifier.detectMultiScale(mat, eyes);
        Set<Rect> checkedEyes = checkEyes(eyes);

        checkedEyes.stream().
                forEach(r -> rectangle(mat, new Point(r.x(), r.y()),
                        new Point(r.x() + r.width(), r.y() + r.height()),
                        Scalar.RED, 1, CV_AA, 0));
        return mat;
    }

    public String getIrisColor(Mat mat) throws EyesNotFoundException {
        List<Mat> cutEyes = getCutEyes(mat);
        for(int i=0; i<cutEyes.size(); i++)
        {
            int height = cutEyes.get(i).rows();
            int width = cutEyes.get(i).cols();
            Mat tempHsv = new Mat(height,width, CV_8UC3);
            cvtColor(cutEyes.get(i), tempHsv, CV_BGR2HSV);
            Java2DFrameConverter toBufferedImageConverter= new Java2DFrameConverter();
            OpenCVFrameConverter.ToMat toMatConverter = new OpenCVFrameConverter.ToMat();
            BufferedImage debugImage = toBufferedImageConverter.convert(toMatConverter.convert(tempHsv));
            int x= 0;
        }

        return IrisColor.BRAZOWY.toString();
    }

    private List<Mat> getCutEyes(Mat mat) throws EyesNotFoundException {
        RectVector eyes = new RectVector();
        classifier.detectMultiScale(mat, eyes);
        Set<Rect> checkedEyes = checkEyes(eyes);

        if (checkedEyes.isEmpty()) {
            throw new EyesNotFoundException();
        }

        return checkedEyes.stream()
                .map(roi -> mat.apply(roi))
                .collect(Collectors.toList());
    }

    private Set<Rect> checkEyes(RectVector eyes) {
        Set<Rect> checkedEyes = new HashSet<>();
        long total = eyes.size();
        for (long i = 0; i < total; i++) {
            Rect r = eyes.get(i);
            for (long j = 1; j < total; j++) {
                Rect tmpR = eyes.get(j);
                if ((tmpR.y() > r.y() - r.height() / 2) && (tmpR.y() < r.y() + r.height() / 2)) {
                    checkedEyes.add(r);
                    checkedEyes.add(tmpR);
                    return checkedEyes;
                }
            }
        }
        return checkedEyes;
    }
}
