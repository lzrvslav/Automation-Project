import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LogOut extends NavigationAndUrlValidation {

    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/login";
    private final WebDriver webDriver;

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement usernameField;

    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;

    @FindBy(id = "sign-in-button")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(@class, 'toast-message') and @aria-label='Successful logout!']")
    private WebElement logOutMessage;

    public LogOut(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isUrlLoaded() {
        return super.isUrlLoaded(webDriver, PAGE_URL);
    }

    public void onLogOutMessage(String message) {
        WebDriverWait logOutMessageWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        try {
            logOutMessageWait.until(ExpectedConditions.textToBePresentInElement(this.logOutMessage, message));
        } catch (TimeoutException exception) {
            Assert.fail("Logout message is not present. Inner exception: " + exception);
        }
    }

    public String getLogOutMessageText() {
        WebDriverWait logOutMessageWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(5));
        try {
            logOutMessageWait.until(ExpectedConditions.visibilityOf(this.logOutMessage));
            return this.logOutMessage.getText();
        } catch (TimeoutException exception) {
            System.out.println("Logout message is not visible. Inner exception: " + exception);
            return null;
        }
    }
}