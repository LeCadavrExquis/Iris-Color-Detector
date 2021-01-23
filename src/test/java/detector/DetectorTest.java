package detector;

import org.bytedeco.opencv.opencv_core.Mat;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DetectorTest {
    Detector detector = new Detector();
    String pathToResources = "src/test/resources/";

    @Test
    void drawDetectedEyes() {
        fail("not implelemnted");
    }

    @Test
    void getIrisColor_ZIELONY() throws EyesNotFoundException {
        Mat testImage = imread(pathToResources + "phoebe.jpg");

        assertEquals("ZIELONY", detector.getIrisColor(testImage));
    }

    @Test
    void getIrisColor_BRAZOWY() throws EyesNotFoundException {
        Mat testImage = imread(pathToResources + "anneHathaway.jpg");

        assertEquals("BRAZOWY", detector.getIrisColor(testImage));
    }

    @Test
    void getIrisColor_NIEBIESKI() throws EyesNotFoundException {
        Mat testImage = imread(pathToResources + "blueEyesTest.jpg");

        assertEquals("NIEBIESKI", detector.getIrisColor(testImage));
    }
}
