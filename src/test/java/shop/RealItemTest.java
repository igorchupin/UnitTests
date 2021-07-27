package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RealItemTest {
    RealItem testRealItem;
    private static final double WEIGTH = 100;

    @BeforeEach
    void setUp() {
        testRealItem = new RealItem();
    }

    @Test
    @Tag("Real_item_set_and_get_weigth")
    @Tag ("Smoke")
    @DisplayName("Set weigth and get weigth")
    void setWeightAndGetWeigth() {

        double weigthBefore = testRealItem.getWeight();
        testRealItem.setWeight(WEIGTH);
        double weigthAfter = testRealItem.getWeight();

        assertAll("Weigth",
                () -> assertEquals(0.0, weigthBefore, "incorrect default value"),
                () -> assertEquals(100, weigthAfter, "Incorrect weigth was set" )
        );
    }

}