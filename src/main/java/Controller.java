import detector.Detector;
import detector.EyesNotFoundException;
import org.bytedeco.javacv.FrameGrabber;
import javax.swing.*;
import java.awt.event.*;

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
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        terminate();
    }
}
