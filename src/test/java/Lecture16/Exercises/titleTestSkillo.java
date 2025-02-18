package Lecture16.Exercises;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class titleTestSkillo {
    @Test
    public void getTitleTest(){
        // declaration and instantiation of objects/variables
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        String baseUrl = "http://training.skillo-bg.com:4300/posts/all";
        String expectedTitle = "ISkillo";
        String actualTitle = "";

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
        // get the actual value of the title
        actualTitle = driver.getTitle();

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Title: " + actualTitle);
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
        driver.close();

    }
}
