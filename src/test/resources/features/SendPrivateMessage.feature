Feature: Send a private message
  In order to send a private message to other user
  As a user
  I want to send a private message to other user

  Scenario: Send private message
    When I send a private message with title "Test private"
    Then The status is 201
    And There is a message sent with title "Test private"
