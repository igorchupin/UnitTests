package shop;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    Cart testCart;
    RealItem testRealIem;
    VirtualItem testVirtualItem;
    private static final int WEIGTHORSIZE = 99;
    private static final double priceForSet = 10;
    private static final double totalPriceExpected = priceForSet + priceForSet * 0.2;


    @BeforeEach
    void setUp() {
        testCart = new Cart("Name Of Cart");
        testRealIem = new RealItem();
        testRealIem.setPrice(priceForSet);
        testVirtualItem = new VirtualItem();
        testVirtualItem.setPrice(priceForSet);
    }


    @Test
    @Tag("Cart_Get_total_Price")
    @Tag("Cart_Add_Real_Item")
    @Tag("Cart_Add_Virtual_Item")
    @Tag ("Smoke")
    @DisplayName("Add Real And Virtual Item and then get total Price")
    void addRealVirtualItemAndGetTotalPrice() {
        testCart.addRealItem(testRealIem);
        testCart.addVirtualItem(testVirtualItem);
        assertEquals(totalPriceExpected*2, testCart.getTotalPrice(), "Something wrong with getting total price");
    }

}