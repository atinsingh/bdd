Feature: Tag creation and listing
  Scenario: Create a tag
    Given User is authenticated with username "admin" password "admin"
    When user calls "/api/tags" with tag name as "cucumber"
    Then api should ruturn with status 201 in response
    And it should have id as key
    And response should also have cucumber in response

