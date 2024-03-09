Feature: Verify BoredAPI Response

  Scenario: Verify BoredAPI Response Code and Schema
    Given the BoredAPI endpoint is available
    When a GET request is made to the API
    Then the response code should be 200
    And the response schema should match the expected structure