import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MyPresenterTest {
    MyPresenter presenter = new MyPresenter(mock(MyView.class));


    @Test
    void getIrisColor() {
        Assertions.fail("not implemented!");
    }
}
