###Use Cases - Generate Shopping List

**Scope**  
- Housemate Management Application and Hub

**Level**  
- User Goal  

**Primary actor**  
- Housemate  

**Stakeholders and interest**  
- Housemate: Wants accurate, fast data entry, and easy viewing access to shopping list.  

**Preconditions**  
- User is identified and authenticated
- Inventory holds at least one fully stocked item

**Postconditions**  
Shopping list is appropriately updated

**Main success scenario**
1. Housemate opens Inventory in the application
2. System displays current Inventory and an empty Shopping List
3. Housemate reduces the quantity of item(s) in Inventory
4. System updates Shopping List by adding the updated item(s) by the same quantity.
5. Housemate adds new Item(s) to Shopping List by inputting item name and quantity
6. System displays the Shopping List with item(s) to be restocked and item(s) to be purchased for the first time.

**Extensions**  

3a. Item quantity reduced to zero.
1. System hides the Item from Inventory

5a. Invalid input (e.g. unsupported characters).
1. System signals error and rejects input
2. Housemate enters valid input

5b. Item removal from Shopping List.
1. Housemate reduces quantity of an Item on Shopping List.
2. System updates the quantity of the Item or, if the quantity is zero, remove item from Shopping List

*a At any time, shopping list is updated by other housemate  
1. System updates local copy of shopping list as needed
2. Any additions are made to the newest version of the shopping list  

*b At any time, system crashes:  
1. Housemate restarts system
2. System reconstructs prior state if possible, works as default otherwise  

**Special requirements**  
- Touch screen UI on a small flat panel monitor. Text must be readable from .5 meters  

**Technology and data variations list**  
- N/A

**Frequency of occurrence**  
- Moderate-high frequency  

**Miscellaneous**  
- N/A