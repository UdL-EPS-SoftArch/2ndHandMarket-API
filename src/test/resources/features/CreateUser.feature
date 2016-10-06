Feature: Create User
  In order to create a user to sell/buy products a second hand
  As a user
  I want to give a clients/properties necessary information about the personal dades

  @quick
  Scenario: Create an user
    When I create an user with name "Matthew" and lastname "Ghant"
    Then An user have id different an other user
