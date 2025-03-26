import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        super(webDriver);  // calls LoginPage constructor
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void navigateTo() {
        try {
            super.navigateTo(webDriver, PAGE_URL);
        } catch (Exception e) {
            System.out.println("Error navigating to User Edit Page: " + e.getMessage());
        }
    }

    public void openEditProfileModal() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(this.userEditIcon));
            this.userEditIcon.click();
            // Wait until the modal is visible
            wait.until(ExpectedConditions.visibilityOf(this.modifyYourProfileTitle));
        } catch (TimeoutException te) {
            System.out.println("Timeout while opening edit profile modal: " + te.getMessage());
        } catch (Exception e) {
            System.out.println("Error in openEditProfileModal: " + e.getMessage());
        }
    }

    public void populateEditUsername(String newUsername) {
        try {
            editUsernameField.clear();
            editUsernameField.sendKeys(newUsername);
        } catch (Exception e) {
            System.out.println("Error populating edit username: " + e.getMessage());
        }
    }

    public void populateEditEmail(String newEmail) {
        try {
            editEmailField.clear();
            editEmailField.sendKeys(newEmail);
        } catch (Exception e) {
            System.out.println("Error populating edit email: " + e.getMessage());
        }
    }

    public void populateEditPassword(String newPassword) {
        try {
            editPasswordField.clear();
            editPasswordField.sendKeys(newPassword);
        } catch (Exception e) {
            System.out.println("Error populating edit password: " + e.getMessage());
        }
    }

    public void populateEditConfirmPassword(String newPassword) {
        try {
            editConfirmPasswordField.clear();
            editConfirmPasswordField.sendKeys(newPassword);
        } catch (Exception e) {
            System.out.println("Error populating edit confirm password: " + e.getMessage());
        }
    }

    public void populateEditPublicInfo(String publicInfo) {
        try {
            editPublicInfoField.clear();
            editPublicInfoField.sendKeys(publicInfo);
        } catch (Exception e) {
            System.out.println("Error populating edit public info: " + e.getMessage());
        }
    }

    public void clickSave() {
        try {
            saveButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
        }
    }
}