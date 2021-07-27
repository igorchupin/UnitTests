package shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualItemTest {

    VirtualItem testVirtualItem;
    private static final double SIZE = 999;

    @BeforeEach
    void setUp() {
        testVirtualItem = new VirtualItem();
    }

    @Test
    @Tag("Virtual_item_set_and_get_size")
    @Tag ("Smoke")
    @DisplayName("Set size and get size")
    void setSizeOnDisk() {
        double sizeBefore = testVirtualItem.getSizeOnDisk();
        testVirtualItem.setSizeOnDisk(SIZE);
        double sizeAfter = testVirtualItem.getSizeOnDisk();

        assertAll("Weigth",
                () -> assertEquals(0.0, sizeBefore, "incorrect default size"),
                () -> assertEquals(999, sizeAfter, "Incorrect size was set" )
        );
    }

}