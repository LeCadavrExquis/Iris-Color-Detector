import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;

public class App {
    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
            View view = new View();
            Controller controller = new Controller(view);
    }
}
