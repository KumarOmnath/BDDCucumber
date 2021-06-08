@All
Feature: i am on facebook screen to validate framework

  @one
  Scenario: crm page start
    Given User is on Login Page
    And user enter "kumaromnathtest@gmail.com" and "123456789 "
    Then user click on login button
    And user verify home page
    Then User signout

  Scenario Outline: Title of your other crm scenario
    Given User is on Login Page
    And user enter "<user>" and "<pass> "
    Then user click on login button
    And user verify home page
    Then User signout

    Examples: 
      | user                    | pass       |
      | kumaromn@gmail.com | 123456 |
      | kumarom@gmail.com | omnath     |
      | kumaromn@gmail.com | kii@fjijf |
