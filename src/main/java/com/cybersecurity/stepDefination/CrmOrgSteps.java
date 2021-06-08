package com.cybersecurity.stepDefination;

import java.io.FileOutputStream;
import java.io.PrintStream;

import org.openqa.selenium.WebDriver;

import com.cybersecurity.pages.CrmOrgPage;

import commonUtils.BaseTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CrmOrgSteps extends BaseTest{
	
//WebDriver driver;
	
	CrmOrgPage crmOrg = new CrmOrgPage(driver);
	
	

	@Given("^User is on Login Page$")
	public void user_is_on_Login_Page() throws Throwable {
		 PrintStream logs = new PrintStream(new FileOutputStream("AllLogs.txt"));
		   System.setOut(logs);
		   
		  driver.manage().window().maximize();
		   //driver.manage().deleteAllCookies();
	}

	@Given("^user enter \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_enter_and(String arg1, String arg2) throws Throwable {
		
		crmOrg.usernamePassword(arg1, arg2);
	   
	}

	@Then("^user click on login button$")
	public void user_click_on_login_button() throws Throwable {
		crmOrg.loginButton();
	    
	}

	@Then("^user verify home page$")
	public void user_verify_home_page() throws Throwable {
		crmOrg.homePage();
	   
	}

	@Then("^User signout$")
	public void user_signout() throws Throwable {
		crmOrg.logOut();
	    
	}
}
