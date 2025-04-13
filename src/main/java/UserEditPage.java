import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class UserEditPage extends LoginPage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/";
    private final WebDriver webDriver;

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement usernameField;

    @FindBy(id = "defaultLoginFormPassword")
    private WebElement passwordField;

    @FindBy(id = "sign-in-button")
    private WebElement loginButton;

    @FindBy(css = ".fas.fa-user-edit")
    private WebElement userEditIcon;

    @FindBy(xpath = "//input[@formcontrolname='username' and @type='text']")
    private WebElement editUsernameField;

    @FindBy(xpath = "//input[@formcontrolname='email' and @type='text']")
    private WebElement editEmailField;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveButton;

    public UserEditPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickUserEditIcon() {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(userEditIcon));
            userEditIcon.click();
        } catch (TimeoutException e) {
            Assert.fail("User edit icon not clickable. Inner exception: " + e);
        }
    }

    public void editUsername(String newUsername) {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(editUsernameField));
            editUsernameField.clear();
            editUsernameField.sendKeys(newUsername);
        } catch (TimeoutException e) {
            Assert.fail("Username field not editable. Inner exception: " + e);
        }
    }

    public void editEmail(String newEmail) {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(editEmailField));
            editEmailField.clear();
            editEmailField.sendKeys(newEmail);
        } catch (TimeoutException e) {
            Assert.fail("Email field not editable. Inner exception: " + e);
        }
    }

    public void clickSaveButton() {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(saveButton));
            saveButton.click();
        } catch (TimeoutException e) {
            Assert.fail("Save button not clickable. Inner exception: " + e);
        }
    }
}