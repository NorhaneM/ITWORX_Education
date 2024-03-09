@Runner
Feature: courseCreation | Create a Course at Winjigo website

  Scenario: user should be able to create a course on Winjigo website

  Given user navigate to home page
  When user open courses page from left side navigation bar
  And user click on create course button and fill mandatory data
  Then user back to courses list page and assert that course title is displayed



