import org.bytedeco.javacv.FrameGrabber;

public class App {
    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        try {
            Controller controller = new Controller(new View());
        } catch (FrameGrabber.Exception e) {
            //TODO: send information for a user
            e.printStackTrace();
        }
    }
}
