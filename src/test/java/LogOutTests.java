import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogOutTests extends TestObject {

    /**
     * Tests the logout functionality:
     * - Logs in with valid credentials
     * - Logs out via header
     * - Verifies redirection to login page and presence of logout confirmation message
     */

    @Test
    public void testLogoutFunctionality() {
        LogOut logOutPage = loginAndLogout();

        Assert.assertTrue(logOutPage.isUrlLoaded(),
                "Logout failed: The login page did not load after clicking logout button.");

        logOutPage.onLogOutMessage("Successful logout!");
        String actualMessage = logOutPage.getLogOutMessageText();
        Assert.assertTrue(actualMessage.contains("Successful logout!"),
                "Logout message not displayed or incorrect.");
    }

    private LogOut loginAndLogout() {
        WebDriver webdriver = getDriver();

        LoginPage loginPage = new LoginPage(webdriver);
        loginPage.navigateTo();
        loginPage.populateUsername("slavlzrv2");
        loginPage.populatePassword("1234567890A");
        loginPage.clickSignIn();

        Header header = new Header(webdriver);
        header.clickLogoutLink();

        LogOut logOutPage = new LogOut(webdriver);
        logOutPage.waitForLoginPageToLoad();
        return logOutPage;
    }
}