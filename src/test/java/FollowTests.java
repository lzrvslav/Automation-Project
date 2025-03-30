import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowTests extends TestObject {

    @Test
    public void followUserMarielkata() {
        WebDriver webDriver = getDriver();

        // Step 1: Navigate to Login Page and log in.
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isUrlLoaded(), "Login page is not loaded!");
        Assert.assertEquals(loginPage.getSignInElementText(), "Sign in", "Sign in form is not loaded!");
        loginPage.populateUsername("slavlzrv2");
        loginPage.populatePassword("1234567890A");
        loginPage.clickSignIn();

        // Step 2: Use the header search field to find "MARIELKATA" and click Follow.
        Follow follow = new Follow(webDriver);
        follow.searchUser("MARIELKATA");
        follow.clickFollowButton();

        // Follow action to be processed.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 3: Navigate to MARIELKATA's profile (user ID 32) and verify that the "Unfollow" button exists.
        ProfilePage profilePage = new ProfilePage(webDriver);
        profilePage.navigateToProfile(32);
        Assert.assertTrue(profilePage.isUrlLoaded(32), "Profile page did not load properly.");
        Assert.assertTrue(profilePage.isUsernameAsExpected("MARIELKATA"), "Username is not as expected!");
        Assert.assertTrue(profilePage.isUserFollowed(), "Expected the user to be followed (Unfollow button not found)!");

        // Step 4: Unfollow the user.
        profilePage.unfollowUser();

        // Step 5: Verify that after unfollowing, the profile shows the button in "Follow" state.
        Assert.assertTrue(profilePage.isUserUnfollowed(), "Expected the button to be in 'Follow' state after unfollowing!");
    }
}