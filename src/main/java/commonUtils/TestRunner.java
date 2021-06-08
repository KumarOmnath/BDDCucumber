package commonUtils;


import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;




@CucumberOptions(
        features = "src/test/resource/Features",
        glue = {"com.cybersecurity.stepDefination"},dryRun = false, strict = true,monochrome = true,
       tags = {"@All"},plugin={"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/ExtentReport.html"})

public class TestRunner extends AbstractTestNGCucumberTests {

@BeforeClass
    public static void application_Launch() throws Exception {
	BaseTest.getBrowser(PropertyReader.getProperty("browserName"));
	BaseTest.getBrowserEventFire();
    }
 
    
 
    @AfterClass
    public static void tearDown() throws Exception {
    	
    	BaseTest.tearDown();
    	
    	
        }
    
    @AfterClass
    public static void writeExtentReport() {
       Reporter.loadXMLConfig(new File("src/test/resource/extent-config.xml"));

       
       Reporter.setSystemInfo("Test User", System.getProperty("user.name"));
       Reporter.setSystemInfo("Application Name", "CyberSecurity");
       Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name").toString());
       Reporter.setSystemInfo("Environment", "QA");
       Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
    }
    

    
}