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
        testVirtualItem.setSizeOnDisk(SIZE);
    }


    @Test
    @Tag("Virtual_item_to_String")
    @Tag ("Smoke")
    @DisplayName("Virtual item to String")
    void testToString() {

        String expectedResult = String.format ("Class: %s; Name: %s; Price: %s; Size on disk: %s",
                testVirtualItem.getClass(), testVirtualItem.getName(), testVirtualItem.getPrice(),
                testVirtualItem.getSizeOnDisk());

        assertEquals(expectedResult, testVirtualItem.toString(), "Something went wrong with To_string");
    }
}