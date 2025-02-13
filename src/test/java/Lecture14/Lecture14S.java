package Lecture14;

import org.testng.annotations.Test;

public class Lecture14S {

    @Test
    public void suiteTest1(){
        int a = 5;
        int b = 10;
        int sum = a+b;
        System.out.println("suiteTest1:");
        System.out.println("The sum is: " + sum);
    }

    @Test(groups = { "smoke", "checkintest" })
    public void suiteTest2(){
        int a = 5;
        int b = 10;
        int sum = a+b;
        System.out.println("suiteTest2:");
        System.out.println("The sum is: " + sum);
    }
}
