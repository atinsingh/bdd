Feature: Testing a auth for an User
  Scenario: When a user calls API to get authenticated
    Given Api server is is running at "http://pragrablog.herokuapp.com"
    When User calls "/api/authenticate" end point with username "admin" and password "admin"
    Then api should return status code 200
    And api should also have "id_token" in body