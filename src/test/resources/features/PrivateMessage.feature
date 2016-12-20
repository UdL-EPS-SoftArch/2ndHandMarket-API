Feature: Send a private message
  In order to send a private message to other user
  As a user
  I want to be able to define a title and a body for the private message

  Scenario: Send private message
    When A private message with title "The product is not working" and body "testBody" is sent from "SenderUser" to user "testUser"
    And There is a private message with title "The product is not working"
    And There is a private message with body "testBody"
    And There is a private message sent to user "testUser"
    And There is a private message sent from user "SenderUser"
    And There are 1 private messages