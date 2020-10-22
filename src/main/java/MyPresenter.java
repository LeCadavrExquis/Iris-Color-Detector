import detector.Detector;

public class MyPresenter {
    private MyView view;
    private Detector detector;
    private MyCamera camera;

    private MyPresenter(MyView view, Detector detector, MyCamera camera) {
        this.view = view;
        this.detector = detector;
        this.camera = camera;
    }

    public MyPresenter(MyView view) {
        this(view, new Detector(), new MyCamera());
    }

    public String getIrisColor() {
        return detector.getIrisColor(camera.grabImage()).toString();
    }
}
