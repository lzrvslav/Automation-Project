import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests user profile editing functionality:
 * - Logs in with valid credentials
 * - Navigates to profile page
 * - Edits username and email through the edit modal
 * - Verifies updated values on the profile page
 */

public class UserEditTests extends TestObject {

    @Test
    public void modifyUserProfileTest() {
        WebDriver webDriver = getDriver();

        String existingUsername = "slavlzrv2";
        String existingPassword = "1234567890A";
        int userId = 9314;

        // Login
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.verifyLoginPageLoaded();
        loginPage.loginWithCredentials(existingUsername, existingPassword);
        loginPage.onSignInMessage("Successful login!");

        // Go to profile
        Header header = new Header(webDriver);
        header.clickProfileLinkWithHandle();

        ProfilePage profilePage = new ProfilePage(webDriver);
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "Profile page is not loaded.");
        Assert.assertTrue(profilePage.isUsernameAsExpected(existingUsername), "Initial username does not match.");

        // Edit profile
        UserEditPage userEditPage = new UserEditPage(webDriver);
        userEditPage.clickUserEditIcon();

        String newUsername = "slavlzrv2_updated";
        String newEmail = "updatedEmail@test.bg";

        userEditPage.editUsername(newUsername);
        userEditPage.editEmail(newEmail);
        userEditPage.clickSaveButton();

        // Verify updates
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "Profile page did not reload after update.");
        Assert.assertTrue(profilePage.isUsernameAsExpected(newUsername), "Updated username not reflected.");
    }
}