package Lecture17.Exercises;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class ProfileTests {

    // Hardcoded password for login attempts
    private final String PASSWORD = "1234567890"; // Hardcoded password
    private WebDriver driver;

    // Setup WebDriver before each test
    @BeforeMethod
    private void setUpTest() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver(); // Initialize Chrome WebDriver
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Close WebDriver after each test
    @AfterMethod
    private void tearDownTest() {
        if (this.driver != null) {
            this.driver.quit(); // Properly close WebDriver and all browser windows
        }
    }

    // Data provider for login credentials
    @DataProvider(name = "getUsers")
    public Object[][] getUsers() {
        return new Object[][]{
                {"slavlzrv", 9204}, // Valid user
        };
    }

    @Test(dataProvider = "getUsers")
    public void testUploadFile(String username, int userId) {
        // Navigate to the home page
        this.driver.navigate().to("http://training.skillo-bg.com:4200/posts/all");
        WebElement loginNavigationLink = this.driver.findElement(By.id("nav-link-login"));
        loginNavigationLink.click();

        // Wait until the login page is loaded
        WebDriverWait explicitWait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/login"));

        // Find the username field and enter username
        WebElement usernameField = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("defaultLoginFormUsername")));
        usernameField.sendKeys(username);

        // Find the password field and enter password
        WebElement passwordField = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("defaultLoginFormPassword")));
        passwordField.sendKeys(this.PASSWORD);

        // Click the login button
        WebElement loginButton = this.driver.findElement(By.id("sign-in-button"));
        loginButton.click();

        // Wait until the homepage is loaded after login
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/posts/all"));

        // Navigate to post creation page
        this.driver.navigate().to("http://training.skillo-bg.com:4200/posts/create");

        // Wait until post creation page is loaded
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/posts/create"));

        // Locate the file input field
        WebElement fileInput = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));

        // Provide the file path dynamically
        String filePath = System.getProperty("user.home") + "\\Pictures\\Test\\test-image.jpg";
        fileInput.sendKeys(filePath);

        // Wait for image preview to appear
        WebElement imagePreview = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='image-preview']")));

        // Locate the caption input field and enter text
        WebElement captionField = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@formcontrolname='caption']")));
        captionField.sendKeys("Just testing things out!");

        // Click on the create post button
        WebElement createPost = explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='create-post']")));
        createPost.click();

        // Validate that the "Post created!" toast message appears
        WebElement toastMessage = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message ng-star-inserted']")));

        // Verify the message text
        String actualMessage = toastMessage.getText();
        String expectedMessage = "Post created!";
        assert actualMessage.equals(expectedMessage) : "Expected '" + expectedMessage + "' but got '" + actualMessage + "'";

        // Print success message
        System.out.println("Post creation verified successfully: " + actualMessage);
    }

    // Test to verify uploaded posts appear on the profile page
    @Test(dataProvider = "getUsers")
    public void verifyUploadedPostsOnProfile(String username, int userId) {

        // Navigate to the home page
        this.driver.navigate().to("http://training.skillo-bg.com:4200/posts/all");
        WebElement loginNavigationLink = this.driver.findElement(By.id("nav-link-login"));
        loginNavigationLink.click();

        // Wait until the login page is loaded
        WebDriverWait explicitWait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/login"));

        // Find the username field and enter username
        WebElement usernameField = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("defaultLoginFormUsername")));
        usernameField.sendKeys(username);

        // Find the password field and enter password
        WebElement passwordField = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("defaultLoginFormPassword")));
        passwordField.sendKeys("1234567890"); // Hardcoded password (Ensure it's correct)

        // Click the login button
        WebElement loginButton = this.driver.findElement(By.id("sign-in-button"));
        loginButton.click();

        // Wait until the homepage is loaded after login
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/posts/all"));

        // Navigate to the profile page
        WebElement profileLink = explicitWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-profile")));
        profileLink.click();

        // Wait for the profile page to load
        explicitWait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4200/users/" + userId));

        // Verify that the uploaded post appears
        List<WebElement> uploadedPosts = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//app-post[contains(@class, 'app-post')]")));

        assert uploadedPosts.size() > 0 : "No posts found in profile, post upload might have failed!";

        // Print success message
        System.out.println("Uploaded posts successfully found in the profile page!");
    }
}
