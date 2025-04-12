import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Tests follow and unfollow functionality for a specific user:
 * - Logs in and searches for user "MARIELKATA"
 * - Verifies following and unfollowing behavior from profile page
 */

public class FollowUnfollowTests extends TestObject {

    private void loginAndNavigateToUser(WebDriver webDriver) {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.navigateTo();
        Assert.assertTrue(loginPage.isUrlLoaded(), "Login page is not loaded!");
        Assert.assertEquals(loginPage.getSignInElementText(), "Sign in", "Sign in form is not loaded!");
        loginPage.populateUsername("slavlzrv2");
        loginPage.populatePassword("1234567890A");
        loginPage.clickSignIn();

        Follow followPage = new Follow(webDriver);
        followPage.searchUser("MARIELKATA");
        followPage.clickFollowButton();
    }

    @Test
    public void followUserMarielkata() {
        WebDriver webDriver = getDriver();
        loginAndNavigateToUser(webDriver);

        ProfilePage profilePage = new ProfilePage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(driver -> {
            profilePage.navigateToProfile(32);
            return profilePage.isUserFollowed();
        });

        Assert.assertTrue(profilePage.isUrlLoaded(32), "Profile page did not load properly.");
        Assert.assertTrue(profilePage.isUsernameAsExpected("MARIELKATA"), "Username is not as expected!");
        Assert.assertTrue(profilePage.isUserFollowed(), "Expected the user to be followed (Unfollow button not found)!");
    }

    @Test
    public void unfollowUserMarielkata() {
        WebDriver webDriver = getDriver();
        loginAndNavigateToUser(webDriver);

        ProfilePage profilePage = new ProfilePage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(driver -> {
            profilePage.navigateToProfile(32);
            return profilePage.isUserFollowed();
        });

        profilePage.unfollowUser();

        Assert.assertTrue(profilePage.isUserUnfollowed(), "Expected the button to be in 'Follow' state after unfollowing!");
    }
}