### Use case - Buy Groceries

**Scope**
- Housemate Management Application

**Level**
- User goal 

**Primary actor**
- Creditor

**Stakeholders and interests**
- Creditor: Wants simple and accurate addition of items. Wants prompt confirmation from Debtors. Wants way to keep track of who paid off the money owed.
- Debtors: Wants clear, easy-to-read list of items. Wants easy way to opt out of payment for items. Want accurate amount owed to the Creditor

**Preconditions**
- Creditor and Debtors are housemates registered under a same house.

**Postconditions**
- Amount of money owed by each Debtors is calculated.
- IOU amounts are confirmed by Debtors.
- Payments are confirmed by the Creditor. 

**Main success scenario**
1. Creditor initiates receipt creation.
2. System captures identity of Creditor.
3. System prompts Creditor to add items and costs until all times have been added.
4. System notifies Debtors.
5. Debtors opt-in/out of item.
6. System dynamically calculates and displays item cost and calculate running total.
7. Debtors confirm their choices.
8. System process debt for each Debtor once all Debtors have confirmed.
9. System notifies Creditor and Debtors.
10. System archives the receipt.

**Extensions**  

3a. Invalid cost:
1. System signals error and rejects input
2. Creditor reenters valid cost  

7a. Item without Debtors:
1. System opts-in and notifies Creditor  

9a. Item already exists in Inventory
1. System adds the quantity bought to current amount.
2. System updates the Shopping List, removing any fully stocked Item.

9b. Item is a new purchase.
1. System adds the new entry to the Inventory.

*a. At any time, System crashes:
1. User restarts system.
2. System fetches any changes from Hub

**Special requirements**
- Touch screen UI with flexible dimensions
- Notification delivery time within 5 seconds
- Listing updates for everyone whenever a Debtors confirms their choices
- Support for common languages and currencies

**Technology and data variations**
- N/A

**Frequency of occurrence**
- Moderate frequency

**Miscellaneous**
- N/A