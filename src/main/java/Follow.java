import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Follow {
    private final WebDriver webDriver;

    @FindBy(id = "search-bar")
    private WebElement searchBar;

    @FindBy(xpath = "//button[normalize-space(text())='Follow']")
    private WebElement followButton;

    public Follow(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void searchUser(String username) {
        searchBar.clear();
        searchBar.sendKeys(username);

        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(followButton));
    }

    public void clickFollowButton() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(followButton));
            followButton.click();
            wait.until(ExpectedConditions.stalenessOf(followButton));
        } catch (StaleElementReferenceException | TimeoutException exception) {
            System.out.println("Exception occurred during follow button interaction: " + exception.getMessage());
        }
    }
}