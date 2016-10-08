Feature: Search Advertisement
  In order to search an advertisement
  As a buyer
  I want to write key words in order to find the advertisement I was searching for.

  Scenario: Search advertisement
    When I search an advertisement with the keyWord "Selling"
    Then There is an advertisement with keyWord "Selling"
