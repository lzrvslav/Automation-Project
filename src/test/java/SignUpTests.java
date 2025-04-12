import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests the user registration functionality:
 * - Verifies sign-up with valid dynamically generated data
 * - Validates proper handling of invalid email, password, confirmation, and public info fields
 * - Confirms form-level validation and success messaging behavior
 */

public class SignUpTests extends TestObject {

    @Test(priority = 1)
    public void SignUpWithDynamicData() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        Assert.assertTrue(signup.isUrlLoaded(), "The sign up page is not loaded");
        Assert.assertEquals(signup.getSignUpElementText(), "Sign up", "Sign up form is not loaded");

        signup.registerWithDynamicData();

        String successMessage = signup.getSignUpSuccessMessage();
        Assert.assertTrue(successMessage.contains("Successful register"), "User did not register successfully");
    }

    @Test(priority = 2)
    public void signUpWithInvalidEmail() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        signup.populateUsername("invalidEmailUser");
        signup.populateEmail("not_an_email");
        signup.populateBirthDate("01/01/2000");
        signup.populatePassword("Valid123@");
        signup.populateConfirmPassword("Valid123@");
        signup.populatePublicInfo("Test info");
        signup.clickSignUp();

        Assert.assertTrue(signup.isEmailInvalid(), "Expected invalid email state to be displayed.");
    }

    @Test(priority = 3)
    public void signUpWithInvalidPassword() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        signup.populateUsername("invalidPasswordUser");
        signup.populateEmail("test@example.com");
        signup.populateBirthDate("01/01/2000");
        signup.populatePassword("12A"); // Too short or weak
        signup.populateConfirmPassword("12A");
        signup.populatePublicInfo("Test info");
        signup.clickSignUp();

        Assert.assertTrue(signup.isPasswordInvalid(), "Expected invalid password state to be displayed.");
    }

    @Test(priority = 4)
    public void signUpWithInvalidConfirmPassword() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        signup.populateUsername("invalidConfirmPasswordUser");
        signup.populateEmail("test@example.com");
        signup.populateBirthDate("01/01/2000");
        signup.populatePassword("Valid123@");
        signup.populateConfirmPassword("Mismatch123@"); // Mismatch
        signup.populatePublicInfo("Test info");
        signup.clickSignUp();

        Assert.assertTrue(signup.isConfirmPasswordInvalid(), "Expected invalid confirm password state to be displayed.");
    }

    @Test(priority = 5)
    public void signUpWithEmptyPublicInfo() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        signup.populateUsername("noPublicInfoUser");
        signup.populateEmail("test@example.com");
        signup.populateBirthDate("01/01/2000");
        signup.populatePassword("Valid123@");
        signup.populateConfirmPassword("Valid123@");
        signup.populatePublicInfo(""); // Empty field
        signup.clickSignUp();

        Assert.assertTrue(signup.isPublicInfoInvalid(), "Expected invalid public info state to be displayed.");
    }
}
