package Lecture16.Exercises;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


    public class loginTestSkillo {
        @Test
        public void loginTestSkillo() throws InterruptedException {
            // Start block for open browser, maximize and open skillo site
            WebDriverManager.chromedriver().setup();
            ChromeDriver webDriver = new ChromeDriver();
            webDriver.manage().window().maximize();
            webDriver.get("http://training.skillo-bg.com:4200/posts/all");
            // End of block

            WebElement login = webDriver.findElement(By.id("nav-link-login"));
            login.click();
            Thread.sleep(600);

            WebElement username = webDriver.findElement(By.id("defaultLoginFormUsername"));
            username.sendKeys("slavlzrv");

            WebElement password = webDriver.findElement(By.id("defaultLoginFormPassword"));
            password.sendKeys("1234567890");

            WebElement loginButton = webDriver.findElement(By.id("sign-in-button"));
            loginButton.click();
            Thread.sleep(500);

            WebElement profile = webDriver.findElement(By.id("nav-link-profile"));
            profile.click();
            Thread.sleep(500);

            WebElement profileEdit = webDriver.findElement(By.className("fa-user-edit"));
            profileEdit.click();
            Thread.sleep(500);

//            WebElement profileEdit = webDriver.findElement(By.xpath("//i[contains(@class, 'fa-user-edit')]"));
//            profileEdit.click();

            WebElement publicInfo = webDriver.findElement(By.xpath("//textarea[@formcontrolname='publicInfo']"));
            publicInfo.click();
            Thread.sleep(500);
            publicInfo.sendKeys("Beer");
            Thread.sleep(500);
            WebElement save = webDriver.findElement(By.xpath("//button[text()='Save']"));

            // close current window
            webDriver.close();

            // close all windows
            webDriver.quit();
        }
    }
