import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends NavigationAndUrlValidation {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/posts/all";
    private final WebDriver webDriver;

    public HomePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean isUrlLoaded(){
        return isUrlLoaded(webDriver, PAGE_URL);
    }

    public void navigateTo(){
        super.navigateTo(webDriver, PAGE_URL);
    }
}