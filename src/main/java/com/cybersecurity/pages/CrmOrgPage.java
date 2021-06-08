package com.cybersecurity.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.model.Log;

import commonUtils.BaseTest;
import commonUtils.GenericUtility;
/**
 * Crm class contain locator & page specific method
 * 
 * @return true
 */

public class CrmOrgPage {
	WebDriver driver;

	Logger LOG = Logger.getLogger(CrmOrgPage.class);
	GenericUtility common = new GenericUtility();

	// **********Intilize the page**********
	public CrmOrgPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//*[@name='email']")
	private WebElement userName;

	@FindBy(xpath = "//*[@name='pass']")
	private WebElement PassWord;

	@FindBy(xpath = "//*[@value='Log In']")
	private WebElement loginButton;

	@FindBy(xpath = "(//*[.='Home'])[3]")
	private WebElement welComeAdmin;
	
	@FindBy(xpath = "(//*[.='Account Settings'])[6]")
	private WebElement accountSetting;
	
	

	@FindBy(xpath = "(//span[.='Log Out'])[2]")
	private WebElement logOutButton;

	// **************************************************

	public WebElement getUserName() {
		return userName;
	}

	public WebElement getAccountSetting() {
		return accountSetting;
	}

	public WebElement getPassWord() {
		return PassWord;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public WebElement getWelComeAdmin() {
		return welComeAdmin;
	}

	public WebElement getLogOutButton() {
		return logOutButton;
	}

	public void usernamePassword(String userName, String password) throws Exception {
		LOG.info("getting started login username");
		common.typeTextToTextBox(getUserName(), userName);
		common.typeTextToTextBox(getPassWord(), password);
	}

	

	public void loginButton() {
		common.JseClick(getLoginButton());
		LOG.info("Clicked on Login Button");
	}

	public void homePage() throws Exception {
		LOG.info("Veryfing home page");
		common.Highlighting_Element(getWelComeAdmin());
		common.validateElementDisplayed(getWelComeAdmin());
}
	
	public void logOut() throws Exception {
		LOG.info("logout from Application");
		common.JseClick(getAccountSetting());
		common.Highlighting_Element(getLogOutButton());
		common.JseClick(getLogOutButton());
		
	}

}
