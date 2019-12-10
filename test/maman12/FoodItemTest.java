package maman12;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Adjusted to task requirements and limitations.
 *
 * @author George Merenzon
 * @version 2020a
 */
class FoodItemTest {

    private static final String DEFAULT_NAME = "item";
    private static final long DEFAULT_CATALOGUE_NUMBER = 9999;
    private static final Date DEFAULT_DATE = setupDate(1, 1, 2000);
    private static final Date DEFAULT_DATE_TOMORROW = setupDate(2, 1, 2000);
    private static final int DEFAULT_QUANTITY = 0;
    private static final int DEFAULT_PRICE = 1;

    // asserting valid construction
    @Test
    void whenExpiryDateBeforeProduction_thenSetExpiryDayAfterProduction() {
        assertSettingValidExpiryDate(DEFAULT_DATE, DEFAULT_DATE_TOMORROW);
        assertSettingValidExpiryDate(setupDate(1, 3, 2000), setupDate(29, 2, 2000));
    }

    @Test
    void whenMinTempGreaterThanMax_swapMinMaxValues() {
        assertAll("min temperature is below max",
                () -> assertMinMaxTemperature(1, 13),
                () -> assertMinMaxTemperature(20, 5),
                () -> assertMinMaxTemperature(100, 100),
                () -> assertMinMaxTemperature(-100, -200)
        );
    }

    @Test
    void whenEmptyName_setDefaultName() {
        FoodItem item = new FoodItem("", 123, 1, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 1, 2, 1);

        assertEquals(DEFAULT_NAME, item.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 999, 10_000, 111111})
    void whenInvalidCatalogueNumber_thenSetDefaultValue(int invalidValue) {
        assertAll("given non-positive number should set default value",
                () -> assertValidCatalogueNumber(DEFAULT_CATALOGUE_NUMBER, invalidValue),
                () -> assertValidCatalogueNumber(1000, 1000),
                () -> assertValidCatalogueNumber(3456, 3456)
        );
    }

    @Test
    void whenInvalidQuantity_setupDefaultValue() {
        assertAll("given negative quantity should set default value",
                () -> assertValidQuantity(DEFAULT_QUANTITY, -1),
                () -> assertValidQuantity(2, 2),
                () -> assertValidQuantity(DEFAULT_QUANTITY, 0)
        );
    }

    @Test
    void whenInvalidPrice_setupDefaultValue() {
        assertAll("given invalid price should set default value",
                () -> assertValidPrice(DEFAULT_PRICE, 0),
                () -> assertValidPrice(DEFAULT_PRICE, -1),
                () -> assertValidPrice(879, 879)
        );
    }

    // setting values
    @Test
    void whenSetInvalidQuantityDoesNotChange() {
        assertAll("when invalid quantity doesn't change value",
                () -> assertQualityDoesNotChange(1, -1),
                () -> assertQualityDoesNotChange(100, -100),
                () -> assertQualityDoesNotChange(0, 0)
        );
    }

    @Test
    void whenSetInvalidProductionDateDoesNotChange() {
        assertAll("when invalid production date doesn't change value",
                () -> assertProductionDoesNotChange(DEFAULT_DATE, DEFAULT_DATE_TOMORROW),
                () -> assertProductionDoesNotChange(DEFAULT_DATE_TOMORROW.tomorrow(), setupDate(10, 12, 2012)),
                () -> assertProductionDoesNotChange(setupDate(10, 12, 2012), setupDate(23, 3, 2025)),
                () -> assertProductionDoesNotChange(DEFAULT_DATE, DEFAULT_DATE)
        );
    }

    @Test
    void whenSetInvalidExpiryDateDoesNotChange() {
        assertAll("when invalid production date doesn't change value",
                () -> assertExpiryDoesNotChange(DEFAULT_DATE_TOMORROW, DEFAULT_DATE),
                () -> assertExpiryDoesNotChange(setupDate(10, 12, 2012), DEFAULT_DATE_TOMORROW.tomorrow()),
                () -> assertExpiryDoesNotChange(DEFAULT_DATE, DEFAULT_DATE)
        );
    }

    @Test
    void whenSetInvalidPriceDoesNotChange() {
        assertAll("when invalid price doesn't change value",
                () -> assertPriceDoesNotChangeOnInvalid(DEFAULT_PRICE, -1),
                () -> assertPriceDoesNotChangeOnInvalid(10, 10),
                () -> assertPriceDoesNotChangeOnInvalid(DEFAULT_PRICE, 0)
        );
    }


    // asserting public methods
    @Test
    void foodItemCanDetermineEquals() {
        FoodItem item1 = new FoodItem("default", 123, 1, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 10, 11, 1);
        FoodItem item2 = new FoodItem(item1);

        assertAll("should compare food items",
                () -> assertTrue(item1.equals(item2)),
                () -> assertFalse(setupDefaultFoodItemDates(DEFAULT_DATE, DEFAULT_DATE_TOMORROW).equals(setupDefaultFoodItemDates(DEFAULT_DATE.tomorrow(), DEFAULT_DATE.tomorrow()))),
                () -> assertFalse(setupDefaultItemTemperatures(1, 3).equals(setupDefaultItemTemperatures(3, 25))),
                () -> assertTrue(setupDefaultItemTemperatures(1, 10).equals(setupDefaultItemTemperatures(1, 10)))
        );
    }

    @Test
    void whenOnlyQuantityDiffers_thenFoodItemsEqual() {
        assertAll("quantity should not affect determining if object are equal",
                () -> assertTrue(setupDefaultItemQuantity(3).equals(setupDefaultItemQuantity(0))),
                () -> assertTrue(setupDefaultItemQuantity(0).equals(setupDefaultItemQuantity(1000))),
                () -> assertTrue(setupDefaultItemQuantity(0).equals(setupDefaultItemQuantity(0)))
        );
    }

    @Test
    void objectCanDetermineIfItemIsFresh() {

        assertTrue(setupDefaultFoodItemDates(DEFAULT_DATE, DEFAULT_DATE_TOMORROW.tomorrow())
                .isFresh(DEFAULT_DATE.tomorrow()));
        assertFalse(setupDefaultFoodItemDates(DEFAULT_DATE, DEFAULT_DATE_TOMORROW)
                .isFresh(DEFAULT_DATE));
        assertFalse(setupDefaultFoodItemDates(DEFAULT_DATE, DEFAULT_DATE)
                .isFresh(DEFAULT_DATE));
        assertTrue(setupDefaultFoodItemDates(DEFAULT_DATE, setupDate(9, 12, 2019))
                .isFresh(setupDate(3, 11, 2019)));
    }

    @Test
    void foodItemShouldHaveGivenFormat() {

        assertToStringUsesFormatMethod("milk", 1234, setupDate(14, 12, 2019), setupDate(21, 12, 2019), 3);
        assertToStringUsesFormatMethod("coffee", 4567, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 9);
    }

    @Test
    void foodItemCanDetermineOlderItem() {
        FoodItem item1 = new FoodItem("coffee", 4567, 9, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 13, 25, 67);
        FoodItem item2 = new FoodItem("milk", 1234, 3, setupDate(14, 12, 2019), setupDate(21, 12, 2019), 1, 10, 1);
        assertTrue(item1.olderFoodItem(item2));
        assertTrue(setupDefaultFoodItemDates(setupDate(13, 12, 1987), setupDate(1, 1, 2000)).olderFoodItem(
                setupDefaultFoodItemDates(setupDate(13, 12, 1999), setupDate(1, 1, 2000))));
        assertFalse(item2.olderFoodItem(item1));
        assertFalse(item2.olderFoodItem(item2));
        assertFalse(item1.olderFoodItem(item1));
    }

    @Test
    void foodItemCanDetermineHowManyExistingItemsCanBuy() {
        FoodItem item = new FoodItem("milk", 1234, 4, DEFAULT_DATE, DEFAULT_DATE, 1, 10, 1);
        assertEquals(3, item.howManyItems(3));
        assertEquals(2, item.howManyItems(2));
        assertEquals(4, item.howManyItems(5));
    }

    @Test
    void name() {
        FoodItem item1 = new FoodItem("milk", 1234, 4, DEFAULT_DATE, DEFAULT_DATE, 1, 10, 1);
        FoodItem item2 = new FoodItem("milk", 1234, 4, DEFAULT_DATE, DEFAULT_DATE, 1, 10, 9);

        assertTrue(item1.isCheaper(item2));
        assertFalse(item1.isCheaper(item1));
        assertFalse(item2.isCheaper(item1));
    }

    // utility methods
    private void assertSettingValidExpiryDate(Date productionDate, Date expiryDate) {
        FoodItem item = setupDefaultFoodItemDates(productionDate, expiryDate);
        assertTrue(isExpiryOneDayAfterProduction(item.getProductionDate(), item.getExpiryDate()));
    }

    private FoodItem setupDefaultFoodItemDates(Date productionDate, Date expiryDate) {
        return new FoodItem("default", 123, 1, productionDate, expiryDate, 10, 11, 1);
    }

    private void assertMinMaxTemperature(int minTemp, int maxTemp) {
        FoodItem item = setupDefaultItemTemperatures(minTemp, maxTemp);
        assertTrue(item.getMinTemperature() <= item.getMaxTemperature());
    }

    private FoodItem setupDefaultItemTemperatures(int minTemp, int maxTemp) {
        return new FoodItem("default", 123, 1, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, minTemp, maxTemp, 1);
    }

    private boolean isExpiryOneDayAfterProduction(Date productionDate, Date expiryDate) {
        Date prodTomorrow = productionDate.tomorrow();

        return prodTomorrow.equals(expiryDate);
    }

    private void assertValidCatalogueNumber(long expected, long setup) {
        FoodItem item = new FoodItem("", setup, 1, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 1, 2, 1);
        assertEquals(expected, item.getCatalogueNumber());
    }

    private void assertValidQuantity(int expectedQuantity, int setupQuantity) {
        FoodItem item = new FoodItem("", 123, setupQuantity, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 1, 2, 1);
        assertEquals(expectedQuantity, item.getQuantity());
    }

    private void assertValidPrice(int expected, int setupPrice) {
        FoodItem item = new FoodItem("", 123, 0, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 1, 2, setupPrice);
        assertEquals(expected, item.getPrice());
    }

    private void assertQualityDoesNotChange(int expected, int setQuantity) {
        FoodItem item = new FoodItem("default", 123, expected, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 10, 11, 1);
        item.setQuantity(setQuantity);
        assertEquals(expected, item.getQuantity());
    }

    private void assertProductionDoesNotChange(Date expected, Date setDate) {
        FoodItem item = setupDefaultFoodItemDates(expected, expected.tomorrow());

        item.setProductionDate(setDate);
        Date productionDate = item.getProductionDate();

        assertTrue(productionDate.equals(expected));
    }

    private void assertExpiryDoesNotChange(Date expected, Date setDate) {
        FoodItem item = setupDefaultFoodItemDates(expected, expected);

        item.setExpiryDate(setDate);
        Date expiryDate = item.getExpiryDate();

        assertTrue(expiryDate.equals(expected));
    }

    private void assertPriceDoesNotChangeOnInvalid(int expected, int set) {
        FoodItem item = new FoodItem("default", 123, expected, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 10, 11, expected);
        item.setPrice(set);
        assertEquals(expected, item.getPrice());
    }

    private FoodItem setupDefaultItemQuantity(int quantity) {
        return new FoodItem("", 123, quantity, DEFAULT_DATE, DEFAULT_DATE_TOMORROW, 1, 2, 1);
    }


    private void assertToStringUsesFormatMethod(String name, int catalogueNumber, Date productionDate, Date expiryDate, int quantity) {
        FoodItem item = new FoodItem(name, catalogueNumber, quantity, productionDate, expiryDate, 1, 10, 1);
        assertEquals(expectedToStringValues(name, catalogueNumber, productionDate, expiryDate, quantity), item.toString());
    }

    private String expectedToStringValues(String name, int catalogueNumber, Date productionDate, Date expiryDate, int quantity) {
        return "FoodItem:" + name + "\tCatalogueNumber:" + catalogueNumber + "\tProductionDate:" + productionDate + "\tExpiryDate:" + expiryDate + "\tQuantity:" + quantity;
    }

    private static Date setupDate(int day, int month, int year) {
        return new Date(day, month, year);
    }
}
