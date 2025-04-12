# Automation-Project
This repository contains a Java Maven-based UI test automation framework utilizing Selenium WebDriver and TestNG. It automates UI testing for the Skillo Training Platform.

Technologies Used:

 - Java with Maven for project management
 - Selenium WebDriver for browser automation
 - TestNG for test execution and reporting

Design Pattern:

 - Implements the Page Object Model (POM) with PageFactory for maintainable and scalable test code.

Key Features

 - Automates at least five distinct test scenarios covering core functionalities of the Skillo website.
 - All tests are configured to execute via the testng.xml file
 - Tests run in the Google Chrome browser.
 - On test failure, test reports are automatically generated and saved in the reports/ directory.
 - Additionally, screenshots are captured upon failure and stored in the screenshots/ directory.



Test Scenarios:

 - The framework automates the following test classes:
 - SignUpTests: Validates user registration functionality.
 - LoginTests: Verifies user login and authentication processes.
 - UserEditTests: Tests the ability to edit user profile information.
 - FollowUnfollowTests: Checks the functionality of following and unfollowing users.
 - LogOutTests: Ensures that users can successfully log out of their accounts.

Running the Tests:

 - git clone https://github.com/lzrvslav/Automation-Project.git
   cd Automation-Project
 - mvn clean install
 - mvn test -DsuiteXmlFile=testng.xml

License:

 - This project is licensed under the MIT License.