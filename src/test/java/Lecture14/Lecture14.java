package Lecture14;

import org.testng.Assert;
import org.testng.annotations.*; // Everything with the wildcard '*' inherits directly under the annotation.
import org.testng.asserts.SoftAssert;

public class Lecture14 {

    //DataProvider is a method that supplies data to a test method.
    // It allows you to run the same test method multiple times with different sets of data.
    // It executes same code with two different values.
    @DataProvider(name="testData")
    public Object[][] createData(){
        return new Object[][] {
                { 25, 25 },
                { 26, 37},
        };
    }

    // The test must accept parameters!
    @Test(dataProvider = "testData")
    public void dataTest(int num1, int num2){
        int sum = num1+ num2;
        Assert.assertEquals(num1, num2, "The two texts are not equal");
        System.out.println("The sum is: " + sum);
    }


    // @Before annotations:
    // To set up the test environment before executing tests,
    // To ensure that the necessary preconditions are met before running the test methods,
    // ~ providing a consistent starting state for each test.
    // Ex: before clean up the folder from screenshots
    // Ex: before clean up test reports

    // @After annotations:
    // To clean up the test environment after executing tests.
    // They ensure that any changes made during the test execution are reverted, maintaining a
    // ~ clean state for subsequent tests or test runs.
    // Ex: to make screenshot after fail
    // Ex: to send report with the failed tests

    //For setting up configurations (like DB connection) specific to a single test class.
    @BeforeClass
    public void myBeforeClassMethod(){
        System.out.println("This is authentication method for the class");
    }

    // Before every test method it resets data
    @BeforeMethod
    public void myBeforeTestMethod(){
        System.out.println("Before every test method I reset data");
    }

    // Useful for setting up configurations that need to be reset before each test method.
    @BeforeMethod
    public void secondBeforeTestMethod(){
        System.out.println("Second Before every test");
    }

    // Useful for cleaning up configurations specific to a single test class.
    // It passes even after there is an error
    @AfterClass
    public void myAfterClassMethod(){
        System.out.println("This is close the authentication method for the class");
    }

    // Useful for cleaning up configurations that need to be reset after each test method.
    // It passes even after there is an error
    @AfterMethod
    public void myAfterTestMethod(){
        System.out.println("After every test method I reset data");
    }

    //Useful for cleaning up configurations that need to be reset after each test method.
    // It passes even after there is an error
    @AfterMethod
    public void secondAfterTestMethod(){
        System.out.println("Second After every test");
    }

    //Annotation @Test is used to configure and control test methods.
    @Test(groups = { "smoke", "checkintest" })
    public void myFirstTest(){
        int a = 5;
        int b = 10;
        int sum = a + b;
        System.out.println("myFirstTest:");
        System.out.println("The sum is: " + sum);
    }

    @Test(groups = { "smoke", "checkintest" })
    public void myFirstFailedTest(){
        System.out.println("myFirstFailedTest:");
        // Define the variables
        int a = 5;
        int b = 10;
        int sum = a+b;

        // if the condition is true shall pass.
        // if the condition is false will fail and -> message
        // assert.false can be used for negative tests -> expected condition shall be "false"
        // ~ condition must be true and then expected "error message"
//       Assert.assertTrue(false, "Failure message is logged here instead of this one");
//
//        // We check the mathematical calculation
//        Assert.assertEquals(a,b,"The numbers are not equal");

        // Define the variables
        String actual = "ala bala";
        String expected = "la bala";

        // We check the variables are equal
        Assert.assertEquals(actual, expected, "The two texts are not equal");
        System.out.println("The sum is: " + sum);
    }

    public void myFirstSoftTest(){
        System.out.println("myFirstSoftTest:");

        // Define as object:
        // ~ Class name -> variable = new class name
        SoftAssert softAssert = new SoftAssert();

        int a = 5;
        int b = 10;
        int sum = a+b;
        // We check the mathematical calculation
        softAssert.assertEquals(a,b,"The number are not equal");

        // Define the variables
        String actual = "ala bala";
        String expected = "la bala";

        // We check the variables are equal
        softAssert.assertEquals(actual, expected, "The two texts are not equal");

        // Print the message
        System.out.println("The sum is: " + sum);

        // When we are done with asserts:
        // We have to push all the asserts - all errors. Explicitly say when they should apply/terminate the code.
        //Finally, we fail the script.
        softAssert.assertAll();
    }

    @Test
    public void myLast(){
        System.out.println("myLastTest:");
        int a = 15;
        int b = 10;
        int sum = a+b;
        System.out.println("This is the last test with the following sum: " + sum);
    }
}
