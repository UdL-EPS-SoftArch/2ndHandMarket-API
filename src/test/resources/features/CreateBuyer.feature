Feature: Create Buyer
  In order to create a buyer to buy products a second hand
  As a buyer
  I want to give a clients necessary information about the my personal dades

  @quick
  Scenario: Create buyer user
    When I create a buyer with name "Matthew" and lastname "Ghant"
    Then A buyer have id
    And I fill in name with "Matthew"
    And I fill in lastname with "Ghant"

