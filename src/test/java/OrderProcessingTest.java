import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.*;

public class OrderProcessingTest {

    @DataProvider(name = "orderStatusData")
    public Object[][] orderStatusData() {
        return new Object[][] {
                {"Shipped", "Shipped"},
                {"Processing", "Processing"},
                {"Delivered", "Delivered"},
                {"Cancelled", "Cancelled"}
        };
    }

    @Test(dataProvider = "orderStatusData", groups = "smoke")
    public void testOrderStatusUpdate(String expectedStatus, String actualStatus) {
        Assert.assertEquals(actualStatus, expectedStatus, "Order status does not match!");
    }

    @DataProvider(name = "totalPriceData")
    public Object[][] totalPriceData() {
        return new Object[][] {
                {150.00, 150.00},
                {200.50, 200.50},
                {99.99, 99.99},
                {75.25, 75.25}
        };
    }

    @Test(dataProvider = "totalPriceData")
    public void testTotalPriceCalculation(double expectedTotalPrice, double actualTotalPrice) {
        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price does not match!");
    }
}
