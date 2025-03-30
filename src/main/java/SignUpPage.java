import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class SignUpPage extends NavigationAndUrlValidation {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/register";
    private final WebDriver webDriver;

    // Random data generation support
    private static final Random random = new Random();
    private static final String[] publicInfoSamples = {
            "Hi!", "Cool!", "Testing sign up.", "Testing.", "Test!"
    };

    @FindBy(xpath = "//input[@formcontrolname='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@formcontrolname='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@formcontrolname='birthDate']")
    private WebElement birthDateField;

    @FindBy(xpath = "//input[@id='defaultRegisterFormPassword']")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@id='defaultRegisterPhonePassword']")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//textarea[@formcontrolname='publicInfo' and @placeholder='Public info']")
    private WebElement publicInfoField;

    @FindBy(xpath = "//button[normalize-space(text())='Sign in']")
    private WebElement signUpButton;

    @FindBy(tagName = "h4")
    private WebElement signUpFormTitle;

    @FindBy(xpath = "//div[contains(@class, 'toast-message') and contains(text(), 'Successful register')]")
    private WebElement signUpSuccessMessage;

    public SignUpPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isUrlLoaded() {
        return super.isUrlLoaded(webDriver, PAGE_URL);
    }

    public void navigateTo() {
        super.navigateTo(webDriver, PAGE_URL);
    }

    public void populateUsername(String username) {
        this.usernameField.sendKeys(username);
        String typedUserName = this.usernameField.getAttribute("value");
        Assert.assertEquals(typedUserName, username, "Username is not typed correctly");
    }

    public void populateEmail(String email) {
        this.emailField.sendKeys(email);
        String typedEmail = this.emailField.getAttribute("value");
        Assert.assertEquals(typedEmail, email, "Email is not typed correctly");
    }

    // Populates the birth date field with a random date between 1980-01-01 and 2025-12-31
    public void populateBirthDate() {
        long minDay = LocalDate.of(1980, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2025, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateString = randomDate.format(formatter);
        birthDateField.click();
        birthDateField.clear();
        birthDateField.sendKeys(dateString);
    }

    public void populateBirthDate(String dateString) {
        birthDateField.click();
        birthDateField.clear();
        birthDateField.sendKeys(dateString);
    }

    public void populatePassword(String password) {
        this.passwordField.sendKeys(password);
        String typedPassword = this.passwordField.getAttribute("value");
        Assert.assertEquals(typedPassword, password, "Password is not typed correctly");
    }

    public void populateConfirmPassword(String confirmPassword) {
        this.confirmPasswordField.sendKeys(confirmPassword);
        String typedConfirmPassword = this.confirmPasswordField.getAttribute("value");
        Assert.assertEquals(typedConfirmPassword, confirmPassword, "Confirm password is not typed correctly");
    }

    public void populatePublicInfo(String publicInfo) {
        this.publicInfoField.sendKeys(publicInfo);
        String typedPublicInfo = this.publicInfoField.getAttribute("value");
        Assert.assertEquals(typedPublicInfo, publicInfo, "Public info is not typed correctly");
    }

    public void clickSignUp() {
        this.signUpButton.click();
    }

    public String getSignUpElementText() {
        String signUpText = "";
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try {
            explicitWait.until(ExpectedConditions.visibilityOf(this.signUpFormTitle));
            signUpText = this.signUpFormTitle.getText();
        } catch (TimeoutException exception) {
            System.out.println("Title element on Sign Up is not loaded. Inner exception: " + exception);
        }
        return signUpText;
    }

    public String getSignUpSuccessMessage() {
        String message = "";
        WebDriverWait wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(this.signUpSuccessMessage));
            message = this.signUpSuccessMessage.getText();
        } catch (TimeoutException exception) {
            System.out.println("Sign up success message is not loaded. Inner exception: " + exception);
        }
        return message;
    }

    public boolean isEmailInvalid() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(driver -> emailField.getAttribute("class").contains("is-invalid"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPasswordInvalid() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(driver -> passwordField.getAttribute("class").contains("is-invalid"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isConfirmPasswordInvalid() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(driver -> confirmPasswordField.getAttribute("class").contains("is-invalid"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPublicInfoInvalid() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
            wait.until(driver -> publicInfoField.getAttribute("class").contains("is-invalid"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Dynamic Data Generation Methods.
    public static String generateUsername() {
        return "user" + random.nextInt(10000);
    }

    public static String generateEmail(String username) {
        return username + "@abv.bg";
    }

    public static String generatePassword() {
        // Returns an 8-character random password appended with a constant for complexity.
        return UUID.randomUUID().toString().substring(0, 8) + "@A1";
    }

    public static String getRandomPublicInfo() {
        return publicInfoSamples[random.nextInt(publicInfoSamples.length)];
    }

    // Method for Dynamic Registration.
    public void registerWithDynamicData() {
        String username = generateUsername();
        String email = generateEmail(username);
        String password = generatePassword();
        String publicInfo = getRandomPublicInfo();

        populateUsername(username);
        populateEmail(email);
        populateBirthDate();
        populatePassword(password);
        populateConfirmPassword(password);
        populatePublicInfo(publicInfo);
        clickSignUp();
    }
}