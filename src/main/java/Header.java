import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Header {
    private final WebDriver webDriver;

    @FindBy(id = "nav-link-profile")
    private WebElement profileLink;

    @FindBy(id = "nav-link-login")
    private WebElement loginLink;

    @FindBy(id = "nav-link-home")
    private WebElement homeLink;

    @FindBy(id = "nav-link-new-post")
    private WebElement newPostLink;

    @FindBy(xpath = "//a[.//i[contains(@class, 'fa-sign-out-alt')]]")
    private WebElement logoutLink;

    public Header(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickHomeLink(){
        WebDriverWait homePageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
        homePageLinkWait.until(ExpectedConditions.elementToBeClickable(this.homeLink));
        this.homeLink.click();
    }

    public void clickHomeLinkWithHandle(){
        waitAndClick(this.homeLink);
    }
    public void clickProfileLinkWithHandle(){
        waitAndClick(this.profileLink);
    }
    public void clickLoginLinkWithHandle(){
        waitAndClick(this.loginLink);
    }
    public void clickNewPostLinkWithHandle(){
        waitAndClick(this.newPostLink);
    }

    public void clickLogoutLink() {
        waitAndClick(this.logoutLink);
    }

    private void waitAndClick(WebElement element){
        try {
            WebDriverWait pageLinkWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));
            pageLinkWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch(TimeoutException exception){
            Assert.fail("Header navigation link is not found. Inner exception: " + exception);
        }
        element.click();
    }
}