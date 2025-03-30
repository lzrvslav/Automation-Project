import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestObject {

    public static final String TEST_RESOURCES_DIR = "src\\test\\resources\\";
    public static final String REPORTS_DIR = TEST_RESOURCES_DIR.concat("reports\\");
    // public static final String DOWNLOAD_DIR = TEST_RESOURCES_DIR.concat("download\\");
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat("screenshot\\");

    private WebDriver webDriver;

    @BeforeSuite
    protected void setupTestSuite() throws IOException {
        cleanDirectory(REPORTS_DIR);
        cleanDirectory(SCREENSHOT_DIR);
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    @BeforeMethod
    @Parameters({"browser"})
    protected void setUpTest(@Optional("chrome") String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                this.webDriver = new FirefoxDriver();
                break;
            case "edge":
                this.webDriver = new EdgeDriver();
                break;
            case "chrome":
            default:
                this.webDriver = new ChromeDriver();
                break;
        }

        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterMethod
    protected void tearDownTest(ITestResult testResult) {
        takeScreenshot(testResult);
        quitDriver();
    }

    @AfterSuite
//    protected void deleteDownloadedFiles() throws IOException{
//        cleanDirectory(DOWNLOAD_DIR);
//    }

    protected WebDriver getDriver() {
        return this.webDriver;
    }

    private void quitDriver() {
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    private void cleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);

        Assert.assertTrue(directory.isDirectory(), "Invalid directory");

        FileUtils.cleanDirectory(directory);

        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0){
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        } else {
            System.out.printf("Unable to delete the files in Directory: %s%n", directoryPath);
        }
    }

    private void takeScreenshot(ITestResult testResult) {
        if (testResult.getStatus() != ITestResult.FAILURE) return;

        try {
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            String testName = testResult.getName();

            String params = Arrays.stream(testResult.getParameters())
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining(", "));

            if (!params.isEmpty()) {
                testName += "_" + params;
            }

            FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR + testName + ".jpg"));
        } catch (IOException ex) {
            System.out.println("Unable to create a screenshot file: " + ex.getMessage());
        }
    }
}