package detector;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.jetbrains.annotations.NotNull;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
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

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_core.CV_32SC4;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class Detector {
    public final static String BLUE = "Niebieskie";
    public final static String BROWN = "Brązowe";
    public final static String GREEN = "Zielone";
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

    public Mat drawDetectedEyes(Mat mat) {
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

    public List<Mat> getCutEyes(Mat mat) throws EyesNotFoundException {
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

    public List<BufferedImage> getFilteredEyes(List<Mat> eyes){

        return eyes.stream().map(
                    eye -> {
                        Mat blur = new Mat();
                        GaussianBlur(eye, blur, new Size(3,3), 0);

                        Mat hsv = new Mat();
                        cvtColor(blur, hsv, CV_BGR2HSV);

                        int H_MIN, H_MAX, S_MIN, S_MAX, V_MIN, V_MAX;

                        Mat filteredBlue = new Mat();
                        H_MIN = 20; //95
                        H_MAX = 179; // 179 max // 115
                        S_MIN = 0;
                        S_MAX = 120;
                        V_MIN = 0;
                        V_MAX = 255; //~70%
                        inRange(blur, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filteredBlue);

                        Mat filteredBrown1 = new Mat();
                        H_MIN = 90;
                        H_MAX = 179; // 179 max
                        S_MIN = 0;
                        S_MAX = 70;
                        V_MIN = 30;
                        V_MAX = 120; //~70%
                        inRange(blur, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filteredBrown1);

                        Mat filteredBrown2 = new Mat();
                        H_MIN = 169;
                        H_MAX = 179; // 179 max

                        inRange(blur, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filteredBrown2);

                        Mat filteredGreen = new Mat();
                        H_MIN = 0;
                        H_MAX = 30; // 179 max
                        S_MIN = 35;
                        S_MAX = 125;
                        V_MIN = 40;
                        V_MAX = 125; //~70%
                        inRange(blur, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filteredGreen);

                        UByteRawIndexer idxBlue = filteredBlue.createIndexer();
                        UByteRawIndexer idxBrown1 = filteredBrown1.createIndexer();
                        UByteRawIndexer idxBrown2 = filteredBrown2.createIndexer();
                        UByteRawIndexer idxGreen = filteredGreen.createIndexer();

                        BufferedImage imageToDisplay = new Java2DFrameConverter().convert(new OpenCVFrameConverter.ToMat().convert(eye));
                        for (int i = 0; i < filteredBlue.cols(); i++){
                            for (int j = 0; j < filteredBlue.rows(); j++){
                                Color color = new Color(0, 0, 0);

                                if (idxBrown1.get((long) i, (long) j) == 255
                                ||  idxBrown2.get((long) i, (long) j) == 255) {
                                    color = new Color(139, 69, 19);
                                } else if (idxBlue.get((long) i, (long) j) == 255){
                                    color = new Color(0, 191, 255);
                                } else if (idxGreen.get((long) i, (long) j) == 255) {
                                    color = new Color(0, 128, 0);
                                }

                                imageToDisplay.setRGB(i, j, color.getRGB());
                            }
                        }

                        return imageToDisplay;
                    }
            ).collect(Collectors.toList());

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
