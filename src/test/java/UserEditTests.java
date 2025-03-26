import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserEditTests extends TestObject {

    @Test
    public void modifyUserProfileTest() {
        WebDriver webDriver = getDriver();

        // 1) Login
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isUrlLoaded(), "The login page is not loaded");
        Assert.assertEquals(loginPage.getSignInElementText(), "Sign in", "Login form is not loaded");

        // Provide valid credentials
        String existingUsername = "slavlzrv2";
        String existingPassword = "1234567890A";
        int userId = 9314;

        loginPage.populateUsername(existingUsername);
        loginPage.populatePassword(existingPassword);
        loginPage.clickSignIn();
        loginPage.onSignInMessage("Successful login!");

        // 2) Verify Home page is loaded
        HomePage homePage = new HomePage(webDriver);
        Assert.assertTrue(homePage.isUrlLoaded(), "Home page is not loaded");

        // 3) Navigate to Profile page
        Header header = new Header(webDriver);
        header.clickProfileLinkWithHandle();

        ProfilePage profilePage = new ProfilePage(webDriver);
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "The user profile page is not loaded");
        Assert.assertTrue(profilePage.isUsernameAsExpected(existingUsername), "The username is not as expected");

        // 4) Open User Edit form
        UserEditPage userEditPage = new UserEditPage(webDriver);
        userEditPage.openEditProfileModal();

        // 5) Fill the form with new data
        String newUsername = "slavlzrv2_updated";
        String newEmail = "updatedEmail@test.bg";
        String newPassword = "1234567890A";
        String newPublicInfo = "This is my updated profile info!";

        userEditPage.populateEditUsername(newUsername);
        userEditPage.populateEditEmail(newEmail);
        userEditPage.populateEditPassword(newPassword);
        userEditPage.populateEditConfirmPassword(newPassword);
        userEditPage.populateEditPublicInfo(newPublicInfo);

        // 6) Save changes
        userEditPage.clickSave();

        // 7) Re-verify the Profile page is loaded with updated info
        Assert.assertTrue(profilePage.isUrlLoaded(userId), "The user profile page is not re-loaded properly.");

        // The profile username show the updated username
        Assert.assertTrue(profilePage.isUsernameAsExpected(newUsername),
                "The username on profile page did not update to the new value.");
    }
}