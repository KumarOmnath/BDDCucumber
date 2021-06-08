package com.cybersecurity.stepDefination;



import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

import commonUtils.BaseTest;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends BaseTest {
	@Before()
	public void beforeInitilization()
	{
		System.out.println("<<<<<<<<<browser Started>>>>>>>>>>>>>");
	}
	
	@After
	public static void cleanUp (Scenario scenario) throws IOException, InterruptedException{
	if(scenario.isFailed()) {
		String screenshotName = scenario.getName().replaceAll(" ", "_");
		
		
			 //This takes a screenshot from the driver at save it to the specified location
			 File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			 
			
			 //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
			 File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots" + screenshotName+ System.currentTimeMillis()+".png");
			 
			 //Copy taken screenshot from source location to destination location
			 Files.copy(sourcePath, destinationPath);   
			 
			 //This attach the specified screenshot to the test
			 Reporter.addScreenCaptureFromPath(destinationPath.toString());
			 
		
			 }

        //BaseTest.tearDown();
	}
//	@After(order = 0)
//	 public void AfterSteps() throws IOException {
//		BaseTest.tearDown();
//	 }
	
}

