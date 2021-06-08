@All
Feature: i am on facebook homescree validation

  
  Scenario: facebook home screen validation
    Given User is on Login Page
    Then validate username box & password box present
    Then Login button is present
    And user enter "kumaromnathtesting@gmail.com" and "123456789 "
    Then user click on login button
    And user verify home page
    Then User signout
