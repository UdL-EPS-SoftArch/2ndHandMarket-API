Feature: Send a private message
  In order to send a private message to other user
  As a user
  I want to be able to define a title and a body for the private message

  Scenario: Send private message
    When I send a private message with title "The product is not working" and body "testBody" to user "testUser"
    And There is an private message with title "The product is not working"
    And There is an private message with body "testBody"
    And There is an private message sent to user "testUser"