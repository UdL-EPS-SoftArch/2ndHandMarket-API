Feature: Post Advertisement
  In order to advertise a second hand product to sell
  As a seller
  I want to create an advertisement about the product and selling conditions

  @quick
  Scenario: Create advertisement
    Given I login as "user" with password "password"
    When I post an advertisement with title "Selling old Spring Boot" and price "1.0"
    Then The status is 201
    And There is an advertisement with title "Selling old Spring Boot"
    And There is an advertisement with price "1.0"

  @slow
  Scenario Outline: Create a valid advertisement
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "<title>"
    And I fill in description with "<description>"
    And I fill in price with "<price>"
    And I fill in negotiablePrice with "<negotiablePrice>"
    And I fill in paidShipping with "<paidShipping>"
    And I fill in tags with "<tags>"
    And I fill in category with "<category>"
    And I fill in brand with "<brand>"
    And I fill in color with "<color>"
    And I fill in weight with "<weight>"
    And I post the advertisement
    Then The status is 201
    And There is an advertisement with title "<title>"
    And There is an advertisement with description "<description>"
    And There is an advertisement with negotiablePrice "<negotiablePrice>"
    And There is an advertisement with paidShipping "<paidShipping>"
    And There is an advertisement with tags "<tags>"
    And There is an advertisement with category "<category>"
    And There is an advertisement with brand "<brand>"
    And There is an advertisement with color "<color>"
    And There is an advertisement with weight "<weight>"

    Examples:
      | title     | description   | price    | negotiablePrice | paidShipping | tags         | category | brand | color | weight |
      | luke      | skywalker     | 42       | false           | false        | star,wars    | sci-fi   |       |       |        |
      | clock     |               | 10.5     | true            | true         |              |          | cool  | blue  | 1.1    |

  @slow
  Scenario Outline: Create an invalid advertisement
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "<title>"
    And I fill in price with "<price>"
    Then There is no advertisement

    Examples:
      | title    | price |
      |          |       |
      | whatever |       |
      |          | 1.1   |
      | whatever | -1    |
      | whatever | 0.00  |

  @quick
  Scenario: Authentication required error when creating an advertisement if not authenticated
    Given I'm not logged in
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    Then The advertisement error message is "Full authentication is required to access this resource"

  @quick
  Scenario: Update advertisement
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    And I fill in title with "unicorns"
    And I put the advertisement with id "1"
    Then The status is 200
    Then There is an advertisement with title "unicorns"

  @quick
  Scenario: Owner required error when updating an advertisement if not the owner
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    Then The status is 201
    And I fill in title with "unicorn"
    And I login as "user2" with password "password"
    And I put the advertisement with id "1"
    Then The status is 403

  @quick
  Scenario: Delete advertisement
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    Then The status is 201
    And I delete the advertisement with id "1"
    Then The status is 204
    Then There are no advertisements

  @quick
  Scenario: Owner required error when deleting an advertisement if not the owner
    Given I login as "user" with password "password"
    And I create a new advertisement
    And I fill in title with "ponies"
    And I fill in price with "42"
    And I post the advertisement
    Then The status is 201
    And I login as "user2" with password "password"
    And I delete the advertisement with id "1"
    Then The status is 403