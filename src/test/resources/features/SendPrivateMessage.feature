Feature: Send a private message
  In order to send a private message to other user
  As a user
  I want to send a private message to other user

  Scenario: Send private message
    When I send a private message with title "The product is not working"
    Then An id is generated for this message
    And There is a message sent with title "The product is not working"