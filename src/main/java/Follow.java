import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Follow {
    private final WebDriver webDriver;

    // Locator for the "Follow" button in the dropdown.
    private By followButtonLocator = By.xpath("//button[normalize-space(text())='Follow']");

    public Follow(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void searchUser(String username) {
        WebElement searchBar = webDriver.findElement(By.id("search-bar"));
        searchBar.clear();
        searchBar.sendKeys(username);
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(followButtonLocator));
    }

    public void clickFollowButton() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(followButtonLocator));
        try {
            button.click();
            wait.until(ExpectedConditions.stalenessOf(button));
        } catch (StaleElementReferenceException e) {
        }
    }
}
