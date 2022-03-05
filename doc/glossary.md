# Glossary
### House:
A self-contained unit representing a single household for which the application is being used.

### Housemate:
An authenticated user registered under a House.

### Hub:
A cloud server managing syncing feature of the application.

### Inventory:
a HashMap that stores all items in the house where the
key is a string representing the itemID and the value
is an Item object.

### Item:
Each Item object has a corresponding itemID, as well as 
a set DesiredQuantity (the minimum amount you want to
have in the house at all times) as well as a CurrentQuantity
(the current amount of this item that you have in the house).

### Shopping List:
an ArrayList that holds Item objects that stores all 
items in the house for which the quantity desired is 
more than the current quantity. (e.g. you want more
of this item in the house than you current do so more 
needs to be bought)

### Receipt:
Contains an ArrayList that holds ReceiptItem Objects
to denote the items that were bought after a housemate
goes shopping and buys items for the house. After logging
the Receipt, debts are created for which all other members
of the house owe the person who paid for this receipt
their share of the receipt price.

### Receipt Item:
Very similar to an Item, a receipt Item has an ItemID, but
also has a price as well as a quantity to denote the 
price and the number bought. The price should be inputted
as the price for all items bought not just the price of a 
single item.

### Debt:
Denotes the amount of money that one Housemate owes another. 
Automatically calculated when a receipt is logged

### Debtor:
A Housemate who owes money to another housemate.

### Creditor:
A Housemate who had paid for a product or service for a Debtor

### Bulletin Board
A message board on where bulletin can be posted by Housemates. Other Housemates can interact with the bulletin by replying.

### Bulletin:
A bulletin object consist of name, content, and replies.

### Housemate Agreement
A special instance of Bulletin that cannot be changed without agreement of every housemate. A bulletin is converted to Housemate Agreement if it was marked as a proposal by the poster and approved by all Housemates.
