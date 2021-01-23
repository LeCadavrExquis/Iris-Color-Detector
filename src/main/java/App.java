import org.bytedeco.javacv.FrameGrabber;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View view = new View();
            Controller controller = new Controller(view);
        });

    }
}
