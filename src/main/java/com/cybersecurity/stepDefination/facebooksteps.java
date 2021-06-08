package com.cybersecurity.stepDefination;

import com.cybersecurity.pages.facebook;

import commonUtils.BaseTest;
import cucumber.api.java.en.Then;

public class facebooksteps extends BaseTest {

	facebook face = new facebook(driver);
	
	@Then("^validate username box & password box present$")
	public void validate_username_box_password_box_present() throws Throwable {
		face.usenameDisplay();
		face.passwordDisplay();
	  
	}

	@Then("^Login button is present$")
	public void login_button_is_present() throws Throwable {
	    face.loginbuttonDisplay();
	}

}
