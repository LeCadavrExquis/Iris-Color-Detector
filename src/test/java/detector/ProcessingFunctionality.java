package detector;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_core.CV_32SC4;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.junit.jupiter.api.Assertions.fail;

class ProcessingFunctionality {
    String pathToResources = "src/test/resources/";

    @Test
    void processPhoto(){
        Mat image = imread(pathToResources + "blueEyesTest.jpg");
        imwrite("debug1.jpg", image);
        Mat blur = new Mat();
        GaussianBlur(image, blur, new Size(3,3), 0);
        imwrite("debug12.jpg", blur);
        Mat hsv = new Mat();
        Mat filtered = new Mat();
        int H_MIN = 0;
        int H_MAX = 30; // 179 max
        int S_MIN = 0;
        int S_MAX = 255;
        int V_MIN = 0;
        int V_MAX = 255; //~70%
        cvtColor(image, hsv, CV_BGR2HSV);
        inRange(hsv, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filtered);

        imwrite("debug2.jpg", filtered);
        cvtColor(blur, hsv, CV_BGR2HSV);
        inRange(hsv, new Mat(1, 1, CV_32SC4, new Scalar(H_MIN, S_MIN, V_MIN, 0)), new Mat(1, 1, CV_32SC4, new Scalar(H_MAX, S_MAX, V_MAX, 0)), filtered);

        imwrite("debug22.jpg", filtered);

        fail("not implemented");
    }
}
