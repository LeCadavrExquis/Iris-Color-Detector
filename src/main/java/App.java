import org.bytedeco.javacv.FrameGrabber;

public class App {
    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        try {
            MyPresenter myPresenter = new MyPresenter(new MyView());
        } catch (FrameGrabber.Exception e) {
            //TODO: send information for a user
            e.printStackTrace();
        }
    }
}
