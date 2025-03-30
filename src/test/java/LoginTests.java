import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends TestObject {

    @DataProvider(name = "getUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                // Valid credentials
                {"slavlzrv2", "1234567890A", 9314, "Successful login!"}
        };
    }

    @DataProvider(name = "errorMessages")
    public Object[][] errorMessages() {
        return new Object[][]{
                // Wrong credentials
                {"blasfsafasfabla", "asfsaf", "Wrong username or password!"},
                // Email test cases
                {"blasfsafasfabla", "asfsaf", "Wrong username or password!"},
                {"dbsdhsh", "asdasfsaf", "Wrong username or password!"},
                // Empty password
                {"dbsdhsh", "", "Wrong username or password!"},
                // Empty username
                {"", "dbsdhsh", "Wrong username or password!"},
                // Completely empty form
                {"", "", ""},
                {"blasfsafasfabla@tezt.xom", "asfsaf", "Wrong username or password!"},
                {"blasfsafasfabla@.xom", "asfsaf", "Wrong username or password!"},
                {"blasfsafasfabla@", "asfsaf", "Wrong username or password!"},
                {"swwrr@test.com", "asdasfsaf", "Wrong username or password!"},
                {"viasfasfasfsadko@teafasfasfst.com", "", "Wrong username or password!"},
                // Valid format but incorrect credentials
                {"", "1234567890", "Wrong username or password!"},
                // Edge cases
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
                {"测试用户", "密码123", "Wrong username or password!"},
                {"потребител", "паролa", "Wrong username or password!"},
                {"ユーザー", "パスワード", "Wrong username or password!"},
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
                {"' OR 1=1 --", "password", "Wrong username or password!"},
        };
    }

    @Test(dataProvider = "getUsers", priority = 1)
    public void LoginUser(String username, String password, int userId, String signInMessage) {
        WebDriver webdriver = getDriver();

        // Page object instances
        LoginPage login = new LoginPage(webdriver);
        ProfilePage profile = new ProfilePage(webdriver);
        Header header = new Header(webdriver);
        HomePage home = new HomePage(webdriver);

        // Verify login page is loaded.
        login.verifyLoginPageLoaded();

        // Populate credentials and click sign in.
        login.loginWithCredentials(username, password);

        // Wait for and verify the sign-in toast message
        login.onSignInMessage(signInMessage);

        // Verify that home page is loaded after successful login
        Assert.assertTrue(home.isUrlLoaded(), "Home page is not loaded");

        // Navigate to profile and verify the user ID + username
        header.clickProfileLinkWithHandle();
        Assert.assertTrue(profile.isUrlLoaded(userId), "The user profile page is not loaded");
        Assert.assertTrue(profile.isUsernameAsExpected(username), "The username is not as expected");
    }

    @Test(dataProvider = "errorMessages", priority = 2)
    public void LoginErrorMessages(String username, String password, String signInMessageExpected) {
        WebDriver webdriver = getDriver();
        LoginPage login = new LoginPage(webdriver);

        // Verify login page is loaded.
        login.verifyLoginPageLoaded();

        // Populate credentials and click sign in.
        login.loginWithCredentials(username, password);

        // Check if both fields are empty and no message is expected
        if (username.isEmpty() && password.isEmpty() && signInMessageExpected.isEmpty()) {
            // Verify invalid field classes
            String usernameClass = login.getUsernameFieldClass();
            String passwordClass = login.getPasswordFieldClass();

            Assert.assertTrue(usernameClass.contains("ng-invalid") && usernameClass.contains("ng-touched"),
                    "Username field should be invalid and touched");
            Assert.assertTrue(passwordClass.contains("ng-invalid") && passwordClass.contains("ng-touched"),
                    "Password field should be invalid and touched");
        } else {
            // Verify the standard sign-in error message
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

        // Go to home page and verify
        home.navigateTo();
        Assert.assertTrue(home.isUrlLoaded(), "The home page is not loaded");

        // Click 'Login' link in the header
        header.clickLoginLinkWithHandle();

        // Verify login page is loaded.
        login.verifyLoginPageLoaded();
    }
}