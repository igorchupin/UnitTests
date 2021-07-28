package parser;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;


class JsonParserTest {

    Cart testCart;
    JsonParser testParser;
    File newFile;
    Gson gson;
    int cartNameLength = 10; //Set the Cart name length
    String cartName;
    String pathToFolder = "src/main/resources/";
    String pathToFile;



    public String generateCardName () {
        String cartName = "";
        for (int i = 0; i <= cartNameLength  ; i++) {
            int rndChar = 97 + (new Random().nextInt(122 - 97));
            Character tempChar = (char) rndChar;
            cartName = cartName + tempChar;
        }
        return cartName;
    }


       public Cart addingItemsToCart (Cart cart) {

            RealItem testRealItem = new RealItem();
            testRealItem.setName("Test Real Item Name");
            testRealItem.setWeight(100);
            testRealItem.setPrice(10);

            VirtualItem testVirtualItem = new VirtualItem();
            testRealItem.setName("Test Virtual Item");
            testVirtualItem.setSizeOnDisk(200);
            testRealItem.setPrice(20);

            testCart.addRealItem(testRealItem);
            testCart.addVirtualItem(testVirtualItem);

          return cart;
         }


    @BeforeEach
    void setUp() {
        cartName = new String(generateCardName());
        testCart = new Cart(cartName);
        testParser = new JsonParser();
        pathToFile = pathToFolder + cartName + ".json";
        newFile = new File(pathToFile);
        gson = new Gson();
    }

    @AfterEach
    void tearDown() throws IOException {
        Path fileToDeletePath = Paths.get(pathToFile );
        Files.deleteIfExists(fileToDeletePath);
    }



    @Test
    @Tag("JsonParser")
    @Tag("Write_to_file")
    @Tag ("Smoke")
    @DisplayName("File was created successfully")
    public void writeToFileSmoke() throws IOException {
        testParser.writeToFile(testCart);

        assertFalse(newFile.createNewFile(), "File was not created by *writeToFile*");
    }



    @Test
    @Tag("JsonParser")
    @Tag("Write_to_file")
    @Tag ("Critical_Path")
    @DisplayName("Write in already existing file")
    public void writeToFileAlreadyexists() throws IOException {
        newFile.createNewFile();
        long sizeBefore = newFile.length();
        testParser.writeToFile(testCart);
        long sizeAfter = newFile.length();

        assertTrue(sizeBefore < sizeAfter, "File size not changed");
    }



    @Test
    @Tag("JsonParser")
    @Tag("Write_to_file")
    @Tag ("Extended")
    @DisplayName("Creating file with null in the name")
    public void writeToFileNullName() throws IOException {
        Path fileToDeletePath = Paths.get(pathToFolder + "null.json");
        Cart cartNull = new Cart(null);
        testParser.writeToFile(cartNull);

        assertTrue(Files.deleteIfExists(fileToDeletePath));
    }



    @Test
    @Tag("JsonParser")
    @Tag("Write_to_file")
    @Tag ("Smoke")
    @DisplayName("File contains correct data from Object")
    public void writeToFileContainsCorrectData() throws IOException {
        testParser.writeToFile(testCart);
        Reader reader = new FileReader(pathToFile);
        Cart createdCart = gson.fromJson(reader, Cart.class);
        reader.close();

        assertEquals(testCart.getCartName(), createdCart.getCartName(), "Wrong information was " +
                "written in the file");
    }



    //Disabled case!!
    @Test
    @Tag("JsonParser")
    @Disabled
    @Tag("Write_to_file")
    @Tag ("Critical_Path")
    @DisplayName("Exception because file is read only")
    public void writeToFileReadOnly() throws IOException {
        newFile.createNewFile();
        newFile.setReadOnly();
        System.out.println(newFile.createNewFile());
        assertThrows(IOException.class, () -> testParser.writeToFile(testCart));
    }



    @Test
    @Tag("JsonParser")
    @Tag("Read_From_File")
    @Tag ("Smoke")
    @DisplayName("Positive test, reading data from file")
    public void readFromFileSmoke() throws IOException {

        addingItemsToCart(testCart);
        File testCartFile = new File(pathToFile);

        FileWriter writer = new FileWriter(testCartFile);
        writer.write(gson.toJson(testCart));
        writer.close();

        Reader reader = new FileReader(pathToFile);
        Cart expectedCart = gson.fromJson(reader, Cart.class);
        reader.close();

        Cart actualCart = testParser.readFromFile(testCartFile);

        assertAll("Values",
                () -> assertEquals(expectedCart.getCartName(), actualCart.getCartName(), "incorrect cart " +
                        "name value"),
                () -> assertEquals(expectedCart.getTotalPrice(), actualCart.getTotalPrice(), "Incorrect total " +
                        "Price" )
        );
    }



    @ParameterizedTest
    @Tag("JsonParser")
    @Tag("Read_From_File")
    @Tag ("Critical_Path")
    @DisplayName("Parametrized Test: Reading data from unexciting files")
    @ValueSource(strings = {
            "empty",
            "src\\main\\\\rsources\\\\cart.json",
            "src/main/resources/CartName",
            "/vCartName.json",
            "src/main/resources/andrew-cartn.json",
            "src/main/resources/CartNme",
            "C:\\Users\\cloud_qa1\\.android",})

    void testWithValueSource(String filePath) {
        newFile = new File(filePath);
        Exception exception = assertThrows(NoSuchFileException.class, () ->
                testParser.readFromFile(newFile));

        assertTrue(exception.getMessage().contains("not found!"), "the NoSuchFileException was expected " +
                "but: \n" +
                "1. It was NOT thrown OR \n" +
                "2. Another exception was thrown OR \n" +
                "3.Exception contains incorrect message\n");
    }



    @Test
    @Tag("JsonParser")
    @Tag("Read_From_File")
    @Tag ("Extended")
    @DisplayName("Reading data from empty file")
    public void readFromEmptyFile() throws IOException  {
        newFile.createNewFile();
        Cart newCart = testParser.readFromFile(newFile);
        Exception exception = assertThrows(NullPointerException.class, () ->
                newCart.getCartName(), "Reading from empty file failed");
    }

}