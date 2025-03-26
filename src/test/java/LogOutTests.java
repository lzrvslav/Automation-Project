import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.WebDriver;

public class LogOutTests extends TestObject {

    @Test
    public void testLogoutFunctionality() {
        // Step 1: Login
        WebDriver webdriver = getDriver();
        LoginPage loginPage = new LoginPage(webdriver);
        loginPage.navigateTo();
        loginPage.populateUsername("slavlzrv2");
        loginPage.populatePassword("1234567890A");
        loginPage.clickSignIn();

        // Assertion to verify successful login
        // Step 2: Logout using Header
        Header header = new Header(webdriver);
        header.clickLogoutLink();

        // Step 3: Verify that the URL is the login page after logout
        LogOut logOutPage = new LogOut(webdriver);
        WebDriverWait wait = new WebDriverWait(webdriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(LogOut.PAGE_URL));
        Assert.assertTrue(logOutPage.isUrlLoaded(),
                "Logout failed: The login page did not load after clicking logout button.");

        // Verify the presence of a logout message
        logOutPage.onLogOutMessage("Successful logout!");
        String actualMessage = logOutPage.getLogOutMessageText();
        Assert.assertTrue(actualMessage.contains("Successful logout!"),
        "Logout message not displayed or incorrect.");
    }
}