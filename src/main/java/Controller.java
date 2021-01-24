import detector.Detector;
import detector.EyesNotFoundException;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class Controller extends WindowAdapter implements ActionListener {
    private View view;
    private Detector detector;
    private MyCamera camera;

    private Controller(View view, Detector detector, MyCamera camera) {
        this.view = view;
        this.detector = detector;
        this.camera = camera;

        view.setListeners(this::actionPerformed, this);
    }

    public Controller(View view)  {
        this(view, new Detector(), new MyCamera());
    }

    public PresentationMode getPresentation() {

        BufferedImage originalImage = null;

        int dialogResult = JOptionPane.showConfirmDialog(view, "Do you want to grab a photo from disc ? (if not will be taken from camera)", "", JOptionPane.YES_NO_OPTION);
        switch (dialogResult) {
            case JOptionPane.YES_OPTION:
                try {
                    JFileChooser fc = new JFileChooser();
                    int returnVal = fc.showOpenDialog(view);

                    if (returnVal != JFileChooser.APPROVE_OPTION){
                        throw new FileNotFoundException();
                    }

                    originalImage = ImageIO.read(fc.getSelectedFile());
                } catch (FileNotFoundException fnfE) {
                    view.message("File not found !", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ioE) {
                    view.message("Cannot read the file", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case JOptionPane.NO_OPTION:
                if (!camera.isConnected()) {
                    connectCamera();
                }
                try {
                    originalImage = camera.grabBufferedImage();
                } catch (FrameGrabber.Exception e) {
                    view.message("Fail grabing an image from camera", JOptionPane.ERROR_MESSAGE);
                }

                disconnectCamera();

                break;
        }
        List<Mat> cutEyes = null;
        try {
            cutEyes = detector.getCutEyes(camera.convertBufferedImage2Mat(originalImage));
        } catch (EyesNotFoundException e) {
            //TODO: not sure if it is necessary
            view.message("Eyes not found", JOptionPane.ERROR_MESSAGE);
        }

        for (int i = 0; i < cutEyes.size(); i++) {
            imwrite("tmpcut" + i + ".jpg", cutEyes.get(i));
        }



        return new PresentationMode(
                originalImage,
                camera.convertMat2BufferedImage(detector.drawDetectedEyes(camera.convertBufferedImage2Mat(originalImage))),
                cutEyes.stream().map(camera::convertMat2BufferedImage).collect(Collectors.toList()),
                detector.getFilteredEyes(cutEyes)
        );
    }

    public String getIrisColor() throws FrameGrabber.Exception {
        String colorName = "";
        int failCounter = 0;
        while (colorName.isEmpty()) {
            try {
                colorName = detector.getIrisColor(camera.grabImage());
            } catch (EyesNotFoundException e) {
                failCounter++;
            } finally {
                if (failCounter > 20) {
                    colorName = "cannot define, sorry";
                }
            }
        }
        return colorName;
    }

    public void connectCamera() {
        try {
            camera.start();
        } catch (FrameGrabber.Exception e) {
            view.message("Failed connecting to camera", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void disconnectCamera() {
        try {
            camera.stop();
        } catch (FrameGrabber.Exception e) {
            view.message("Failed disconnecting from camera", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void startCameraDisplay() {
        if (camera.isConnected()) {
            view.startDisplay(
                    camera,
                    mat -> camera.convertMat2BufferedImage(detector.drawDetectedEyes(mat))
            );
        }else {
            view.message("Camera not connected", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void stopCameraDisplay() {
        view.stopDisplay();
    }

    private void terminate() {
        if (camera.isConnected()) {
            System.out.println("attempt to disconnect camera");
            disconnectCamera();
        }
        System.out.println("closing app...");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "close":
                terminate();
                break;
            case "connectCamera":
                connectCamera();
                //there is no break statement with premeditation
            case "startDisplay":
                startCameraDisplay();
                break;
            case "stopDisplay":
                view.stopDisplay();
                stopCameraDisplay();
                break;
            case "disconnectCamera":
                disconnectCamera();
                break;
            case "presentation":
                view.setPresentationMode(getPresentation());
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        terminate();
    }
}
