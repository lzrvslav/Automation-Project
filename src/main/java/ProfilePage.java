import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProfilePage {
    public static final String PAGE_URL_WITHOUT_USER_ID = "http://training.skillo-bg.com:4300/users/";
    private final WebDriver webDriver;

    @FindBy(tagName = "h2")
    private WebElement usernameText;

    @FindBy(xpath = "//*[@class='col-4 app-post ng-star-inserted']")
    private List<WebElement> postAppPostItem;

    private By unfollowButtonLocator = By.xpath("//button[contains(@class, 'profile-edit-btn') and normalize-space(text())='Unfollow']");

    public ProfilePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToProfile(int userId) {
        webDriver.get(PAGE_URL_WITHOUT_USER_ID + userId);
    }

    public boolean isUrlLoaded(int userId) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        String expectedPattern = PAGE_URL_WITHOUT_USER_ID + userId + "(/)?(\\?.*)?";
        try {
            wait.until(ExpectedConditions.urlMatches(expectedPattern));
        } catch (TimeoutException ex) {
            System.out.println("Expected URL pattern: " + expectedPattern);
            System.out.println("Actual URL: " + webDriver.getCurrentUrl());
            return false;
        }
        return true;
    }

    public boolean isUsernameAsExpected(String username) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(usernameText, username));
        } catch (TimeoutException e) {
            System.out.println("Username text not found on profile page: " + e);
            return false;
        }
        return true;
    }

    public boolean isUserFollowed() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        try {
            WebElement unfollow = wait.until(ExpectedConditions.visibilityOfElementLocated(unfollowButtonLocator));
            return "Unfollow".equalsIgnoreCase(unfollow.getText().trim());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void unfollowUser() {
        if (isUserFollowed()) {
            WebElement unfollow = webDriver.findElement(unfollowButtonLocator);
            unfollow.click();
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOf(unfollow));
        }
    }

    public boolean isUserUnfollowed() {
        By followStateButtonLocator = By.xpath("//button[contains(@class, 'profile-edit-btn') and normalize-space(text())='Follow']");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        try {
            WebElement followButton = wait.until(ExpectedConditions.visibilityOfElementLocated(followStateButtonLocator));
            return "Follow".equalsIgnoreCase(followButton.getText().trim());
        } catch (TimeoutException e) {
            return false;
        }
    }
}