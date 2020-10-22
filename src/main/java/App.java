public class App {
    public static void main(String[] args) {
        launch();
    }

    public static void launch() {
        MyPresenter myPresenter = new MyPresenter(new MyView());
    }
}
