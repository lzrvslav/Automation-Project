import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page object for the user profile edit page:
 * - Handles the editing of user information (username, email, password, public info, etc.)
 * - Provides methods for navigating to the page, opening the profile edit modal,
 *   and interacting with form fields to update user details
 * - Offers functionality to save the changes and interact with page elements
 */

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

    @FindBy(xpath = "//input[@formcontrolname='password' and @type='password']")
    private WebElement editPasswordField;

    @FindBy(xpath = "//input[@formcontrolname='confirmPassword' and @type='password']")
    private WebElement editConfirmPasswordField;

    @FindBy(xpath = "//textarea[@formcontrolname='publicInfo']")
    private WebElement editPublicInfoField;

    @FindBy(xpath = "//button[normalize-space(text())='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//h4[contains(text(), 'Modify Your Profile')]")
    private WebElement modifyYourProfileTitle;

    public UserEditPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void navigateTo() {
        super.navigateTo(webDriver, PAGE_URL);
    }

    public void openEditProfileModal() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(userEditIcon));
        userEditIcon.click();
        wait.until(ExpectedConditions.visibilityOf(modifyYourProfileTitle));
    }

    public void populateEditUsername(String newUsername) {
        editUsernameField.clear();
        editUsernameField.sendKeys(newUsername);
    }

    public void populateEditEmail(String newEmail) {
        editEmailField.clear();
        editEmailField.sendKeys(newEmail);
    }

    public void populateEditPassword(String newPassword) {
        editPasswordField.clear();
        editPasswordField.sendKeys(newPassword);
    }

    public void populateEditConfirmPassword(String newPassword) {
        editConfirmPasswordField.clear();
        editConfirmPasswordField.sendKeys(newPassword);
    }

    public void populateEditPublicInfo(String publicInfo) {
        editPublicInfoField.clear();
        editPublicInfoField.sendKeys(publicInfo);
    }

    public void clickSave() {
        saveButton.click();
    }
}