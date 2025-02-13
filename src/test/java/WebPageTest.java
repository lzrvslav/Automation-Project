import org.testng.annotations.DataProvider;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class WebPageTest {

    @DataProvider(name = "pageContentData")
    public Object[][] pageContentData() {
        return new Object[][] {
                {"My Website", "Welcome to My Website", "© 2025 My Website"},
                {"Test Site", "Hello World", "© 2024 Test Site"},
                {"Shop Online", "Best Deals", "© 2023 Shop Online"},
                {"News Portal", "Latest Headlines", "© 2022 News Portal"}
        };
    }

    @Test(dataProvider = "pageContentData", groups = "smoke")
    public void testPageContent(String expectedTitle, String expectedHeader, String expectedFooter) {
        String actualTitle = expectedTitle;
        String actualHeader = expectedHeader;
        String actualFooter = expectedFooter;

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualTitle, expectedTitle, "Title does not match!");
        softAssert.assertEquals(actualHeader, expectedHeader, "Header does not match!");
        softAssert.assertEquals(actualFooter, expectedFooter, "Footer does not match!");
        softAssert.assertAll();
    }
}
