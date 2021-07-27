package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RealItemTest {

    RealItem testRealItem;
    private static final double WEIGTH = 100;
    private static final double PRICE = 10;

    @BeforeEach
    void setUp() {
        testRealItem = new RealItem();
        testRealItem.setName("TestRealItem");
        testRealItem.setPrice(PRICE);
        testRealItem.setWeight(WEIGTH);
    }


    @Test
    @Tag("Virtual_item_to_String")
    @Tag ("Smoke")
    @DisplayName("Virtual item to String")
    void testToString() {

        String expectedResult = String.format ("Class: %s; Name: %s; Price: %s; Weight: %s",
                testRealItem.getClass(), testRealItem.getName(), testRealItem.getPrice(),
                testRealItem.getWeight());

        assertEquals(expectedResult, testRealItem.toString(), "Something went wrong with To_string");
    }


}