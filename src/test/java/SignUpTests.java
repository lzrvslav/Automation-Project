import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTests extends TestObject {

    @Test(priority = 1)
    public void SignUpWithDynamicData() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        Assert.assertTrue(signup.isUrlLoaded(), "The sign up page is not loaded");
        Assert.assertEquals(signup.getSignUpElementText(), "Sign up", "Sign up form is not loaded");

        // Using the dynamic registration method to populate and submit the form.
        signup.registerWithDynamicData();

        // Verify that the success message is displayed.
        String successMessage = signup.getSignUpSuccessMessage();
        Assert.assertTrue(successMessage.contains("Successful register"), "User did not register successfully");
    }

    @Test(priority = 2)
    public void signUpWithInvalidData() {
        WebDriver webDriver = getDriver();
        SignUpPage signup = new SignUpPage(webDriver);

        signup.navigateTo();
        Assert.assertTrue(signup.isUrlLoaded(), "The sign up page is not loaded");
        Assert.assertEquals(signup.getSignUpElementText(), "Sign up", "Sign up form is not loaded");

        signup.populateUsername("negativeTestUser" + System.currentTimeMillis());
        signup.populateEmail("not_an_email");
        signup.populateBirthDate("12-12-2000");
        signup.populatePassword("12A");
        signup.populateConfirmPassword("12Aa");
        signup.populatePublicInfo("");

        signup.clickSignUp();

        // Assert that each input field has the "is-invalid" class.
        Assert.assertTrue(signup.isEmailInvalid(), "Expected invalid email state to be displayed.");
        Assert.assertTrue(signup.isPasswordInvalid(), "Expected invalid password state to be displayed.");
        Assert.assertTrue(signup.isConfirmPasswordInvalid(), "Expected invalid confirm password state to be displayed.");
        Assert.assertTrue(signup.isPublicInfoInvalid(), "Expected invalid public info state to be displayed.");
    }
}