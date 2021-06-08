package com.cybersecurity.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commonUtils.GenericUtility;

public class facebook {
	WebDriver driver;

	Logger LOG = Logger.getLogger(CrmOrgPage.class);
	GenericUtility common = new GenericUtility();

	// **********Intilize the page**********
	public facebook(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

	@FindBy(xpath = "//*[@name='email']")
	private WebElement userName;

	@FindBy(xpath = "//*[@name='pass']")
	private WebElement PassWord;

	@FindBy(xpath = "//*[@value='Log ']")
	private WebElement loginButton;

	public WebElement getUserName() {
		return userName;
	}

	public WebElement getPassWord() {
		return PassWord;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void usenameDisplay() throws Exception {
		common.validateElementDisplayed(getUserName());
	}

	
	public void passwordDisplay() throws Exception {
		common.validateElementDisplayed(getPassWord());
	}
	
	
	public void loginbuttonDisplay() throws Exception {
		common.validateElementDisplayed(getLoginButton());
	}
	

	
	
	
}
