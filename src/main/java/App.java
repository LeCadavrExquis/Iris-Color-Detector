import org.bytedeco.javacv.FrameGrabber;

public class App {
    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        Controller controller = new Controller(new View());

        controller.startCameraDisplay();
    }
}
