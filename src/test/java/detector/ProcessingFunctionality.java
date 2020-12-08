package detector;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC3;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.junit.jupiter.api.Assertions.fail;

class ProcessingFunctionality {
    @Test
    void processPhoto(){
        Java2DFrameConverter toBufferedImageConverter= new Java2DFrameConverter();
        OpenCVFrameConverter.ToMat toMatConverter = new OpenCVFrameConverter.ToMat();
        Mat mat = imread("jetbrains://idea/navigate/reference?project=Iris-detector&path=greenCutEye.jpg");
        BufferedImage image = toBufferedImageConverter.convert(toMatConverter.convert(mat));

        int height = mat.rows();
        int width = mat.cols();
        Mat tempHsv = new Mat(height,width, CV_8UC3);
        cvtColor(mat, tempHsv, CV_BGR2HSV);


        BufferedImage debugImage = toBufferedImageConverter.convert(toMatConverter.convert(tempHsv));

        fail("not implemented");
    }
}
