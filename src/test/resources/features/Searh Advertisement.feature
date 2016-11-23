Feature: Search Advertisement
  In order to search an advertisement
  As a buyer
  I want to write key words in order to find the advertisement I was searching for.

  Scenario: Search advertisement
    Given There is an advertisement with the tag "selling"
    Then I get an advertisement with tag "selling"

