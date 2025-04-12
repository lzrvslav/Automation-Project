import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserEditTests extends TestObject {

    /**
     * Logs in with valid credentials, navigates to the profile page,
     * opens the edit modal, updates user information, and verifies that
     * the changes are reflected on the profile page.
     */

    @Test
    public void modifyUserProfileTest() {
        WebDriver webDriver = getDriver();

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isUrlLoaded(), "The login page is not loaded");
        Assert.assertEquals(loginPage.getSignInElementText(), "Sign in", "Login form is not loaded");

        String existingUsername = "slavlzrv2";
        String existingPassword = "1234567890A";
        int userId = 9314;

        loginPage.populateUsername(existingUsername);
        loginPage.populatePassword(existingPassword);
        loginPage.clickSignIn();
        loginPage.onSignInMessage("Successful login!");

        HomePage homePage = new HomePage(webDriver);
        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");

        Header header = new Header(webDriver);
        header.clickProfileLinkWithHandle();

        ProfilePage profilePage = new ProfilePage(webDriver);
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "The user profile page is not loaded");
        Assert.assertTrue(profilePage.isUsernameAsExpected(existingUsername), "The username is not as expected");

        UserEditPage userEditPage = new UserEditPage(webDriver);
        userEditPage.openEditProfileModal();

        String newUsername = "slavlzrv2_updated";
        String newEmail = "updatedEmail@test.bg";
        String newPassword = "1234567890A";
        String newPublicInfo = "This is my updated profile info!";

        userEditPage.populateEditUsername(newUsername);
        userEditPage.populateEditEmail(newEmail);
        userEditPage.populateEditPassword(newPassword);
        userEditPage.populateEditConfirmPassword(newPassword);
        userEditPage.populateEditPublicInfo(newPublicInfo);

        userEditPage.clickSave();

        Assert.assertTrue(profilePage.isUrlLoaded(userId), "The user profile page is not re-loaded properly.");
        Assert.assertTrue(profilePage.isUsernameAsExpected(newUsername),
                "The username on profile page did not update to the new value.");
    }
}