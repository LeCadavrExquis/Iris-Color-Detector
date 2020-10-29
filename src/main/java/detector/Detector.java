package detector;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.bytedeco.opencv.global.opencv_imgproc.CV_AA;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

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

    public IrisColor getIrisColor(Mat mat) throws EyesNotFoundException {
        Set<Mat> cutEyes = getCutEyes(mat);
        //TODO: decompose to HSV
        return null;
    }

    private Set<Mat> getCutEyes(Mat mat) throws EyesNotFoundException {
        RectVector eyes = new RectVector();
        classifier.detectMultiScale(mat, eyes);
        Set<Rect> checkedEyes = checkEyes(eyes);

        if (checkedEyes.isEmpty()) {
            throw new EyesNotFoundException();
        }

        return checkedEyes.stream()
                .map(roi -> mat.apply(roi))
                .collect(Collectors.toSet());
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
