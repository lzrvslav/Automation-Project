import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests login functionality:
 * - Validates successful login flow and UI behavior
 * - Validates feedback on invalid login attempts
 * - Confirms login page accessibility from navigation
 */

public class LoginTests extends TestObject {

    @DataProvider(name = "getUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                {"slavlzrv2", "1234567890A", 9314, "Successful login!"}
        };
    }

    @DataProvider(name = "errorMessages")
    public Object[][] errorMessages() {
        return new Object[][]{
                {"blasfsafasfabla", "asfsaf", "Wrong username or password!"},
                {"dbsdhsh", "asdasfsaf", "Wrong username or password!"},
                {"dbsdhsh", "", "Wrong username or password!"},
                {"", "dbsdhsh", "Wrong username or password!"},
                {"", "", ""},
                {"blasfsafasfabla@tezt.xom", "asfsaf", "Wrong username or password!"},
                {"blasfsafasfabla@.xom", "asfsaf", "Wrong username or password!"},
                {"blasfsafasfabla@", "asfsaf", "Wrong username or password!"},
                {"swwrr@test.com", "asdasfsaf", "Wrong username or password!"},
                {"viasfasfasfsadko@teafasfasfst.com", "", "Wrong username or password!"},
                {"", "1234567890", "Wrong username or password!"},
                {"username@", "password", "Wrong username or password!"},
                {"user@domain", "password", "Wrong username or password!"},
                {"user@domain.c", "password", "Wrong username or password!"},
                {"user@.com", "password", "Wrong username or password!"},
                {"<script>", "123456", "Wrong username or password!"},
                {"' OR '1'='1", "' OR '1'='1", "Wrong username or password!"},
                {"test@domain.com", "", "Wrong username or password!"},
                {"username", "pass", "Wrong username or password!"},
                {"!@#$%^&*()", "password", "Wrong username or password!"},
                {"username", "!@#$%^&*()", "Wrong username or password!"},
                {"user@domain.toolongtld", "password", "Wrong username or password!"},
                {"user@localhost", "password", "Wrong username or password!"},
                {"user@127.0.0.1", "password", "Wrong username or password!"},
                {"username", "password", "Wrong username or password!"},
                {"username ", "password", "Wrong username or password!"},
                {" username ", " password ", "Wrong username or password!"},
                {"1234567890", "password", "Wrong username or password!"},
                {"user123", "12345678901234567890", "Wrong username or password!"},
                {"admin'--", "admin", "Wrong username or password!"},
                {"admin'--", "1234", "Wrong username or password!"},
                {"' OR 1=1 --", "password", "Wrong username or password!"}
        };
    }

    @Test(dataProvider = "getUsers", priority = 1)
    public void LoginUser(String username, String password, int userId, String signInMessage) {
        WebDriver webdriver = getDriver();

        LoginPage login = new LoginPage(webdriver);
        ProfilePage profile = new ProfilePage(webdriver);
        Header header = new Header(webdriver);
        HomePage home = new HomePage(webdriver);

        login.verifyLoginPageLoaded();
        login.loginWithCredentials(username, password);
        login.onSignInMessage(signInMessage);

        Assert.assertTrue(home.isUrlLoaded(), "Home page is not loaded");

        header.clickProfileLinkWithHandle();
        Assert.assertTrue(profile.isUrlLoaded(userId), "The user profile page is not loaded");
        Assert.assertTrue(profile.isUsernameAsExpected(username), "The username is not as expected");
    }

    @Test(dataProvider = "errorMessages", priority = 2)
    public void LoginErrorMessages(String username, String password, String signInMessageExpected) {
        WebDriver webdriver = getDriver();
        LoginPage login = new LoginPage(webdriver);

        login.verifyLoginPageLoaded();
        login.loginWithCredentials(username, password);

        if (username.isEmpty() && password.isEmpty() && signInMessageExpected.isEmpty()) {
            String usernameClass = login.getUsernameFieldClass();
            String passwordClass = login.getPasswordFieldClass();

            Assert.assertTrue(usernameClass.contains("ng-invalid") && usernameClass.contains("ng-touched"),
                    "Username field should be invalid and touched");
            Assert.assertTrue(passwordClass.contains("ng-invalid") && passwordClass.contains("ng-touched"),
                    "Password field should be invalid and touched");
        } else {
            String actualMessage = login.getSignInMessage();
            Assert.assertEquals(actualMessage, signInMessageExpected,
                    "Unexpected sign-in error message!");
        }
    }

    @Test(priority = 3)
    public void LoginUserPathVerification() {
        WebDriver webdriver = getDriver();
        LoginPage login = new LoginPage(webdriver);
        Header header = new Header(webdriver);
        HomePage home = new HomePage(webdriver);

        home.navigateTo();
        Assert.assertTrue(home.isUrlLoaded(), "The home page is not loaded");

        header.clickLoginLinkWithHandle();
        login.verifyLoginPageLoaded();
    }
}