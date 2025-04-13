import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class LoginPage extends NavigationAndUrlValidation {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/login";
    private final WebDriver webDriver;

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement usernameField;

    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;

    @FindBy(id = "sign-in-button")
    private WebElement loginButton;

    @FindBy(xpath = "//p[@class='h4 mb-4']")
    private WebElement singInTitle;

    @FindBy(xpath = "//div[contains(@class, 'toast-message')]")
    private WebElement signInMessage;

    public LoginPage(WebDriver webDriver) {
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

    public void populatePassword(String password) {
        this.passwordField.sendKeys(password);
    }

    public void clickSignIn() {
        this.loginButton.click();
    }

    public String getSignInElementText() {
        String singInText = "";
        WebDriverWait explicitWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        try {
            explicitWait.until(ExpectedConditions.visibilityOf(this.singInTitle));
            singInText = this.singInTitle.getText();
        } catch (TimeoutException exception) {
            System.out.println("Title element on Sign In is not loaded. Inner exception: " + exception);
        }
        return singInText;
    }

    public void onSignInMessage(String message) {
        WebDriverWait signInMessageWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        try {
            signInMessageWait.until(
                    ExpectedConditions.textToBePresentInElement(this.signInMessage, message));
        } catch (TimeoutException exception) {
            Assert.fail("Sign in message is not present. Inner exception: " + exception);
        }
    }

    public String getSignInMessage() {
        WebDriverWait signInMessageWait = new WebDriverWait(this.webDriver, Duration.ofMillis(1500));
        try {
            signInMessageWait.until(ExpectedConditions.visibilityOf(this.signInMessage));
            return this.signInMessage.getText();
        } catch (TimeoutException exception) {
            System.out.println("Sign in message is not visible. Inner exception: " + exception);
            return null;
        }
    }

    public String getUsernameFieldClass() {
        return this.usernameField.getAttribute("class");
    }

    public String getPasswordFieldClass() {
        return this.passwordField.getAttribute("class");
    }

    public void verifyLoginPageLoaded() {
        navigateTo();
        Assert.assertTrue(isUrlLoaded(), "The login page is not loaded");
        Assert.assertEquals(getSignInElementText(), "Sign in", "Login form is not loaded");
    }

    public void loginWithCredentials(String username, String password) {
        populateUsername(username);
        populatePassword(password);
        clickSignIn();
    }
}