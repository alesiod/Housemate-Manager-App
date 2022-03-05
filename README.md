# Team 1D

## Final Iteration:

### As of Final Iteration, our application is able to:
- All features from Iteration 1 & 2 implemented into Android App
- Store house information in and retrieve house information from Firebase
- Handle debt-tracking
- Error Handling:
  - Errors for attempts to create multiple houses have been handled
- Display all debts owed between Housemates
- View a list of all Past Receipts that have been logged for this House
- Dropdown or Autocomplete is implemented for all appropriate functionality of the application

### Also Note:
- Our application runs in Android Studio, using Firebase and running on a Pixel 4a API 30 VM.

## Iteration 2:

### As of Iteration 2, our application is able to:
- All features from Iteration 1 implemented into Android App
- Add/Remove from Inventory and ShoppingList Directly
  - previously this could only be done through creating a Receipt
- Allow housemates to opt in/out of individual items on the receipt
- Create a Chore List and add/remove Chores from that list
- Error Handling:
  - Errors for attempts to create duplicates have been handled
  - Errors for attempts to delete non-existent items are handled

### As of Iteration 2, our application is not able to but will be able to eventually:
- Display the debts that each housemate owes their fellow housemates
  - this is currently being tracked behind the scenes but never displayed
- View a log of past receipts
  - This is being tracked and logged behind the scenes but never displayed
- The selection of Housemates for Chore assignment is currently based on the
user typing in the name and correctly matching the housemate name. In the next
iteration, this will be replaced with dropdowns to select housemate.
- The same dropdown method will be implemented for Receipt Item names in the 
opt-in/out feature.

### Also Note:
At this time, our application is not able to manage multiple houses,
it also does not have any data persistence and for each run, a new 
house is created and the application resets everything to being empty.
This is something that was a class-wide assumption for this iteration
and will be discussed and implemented later in the semester.

Our application runs in Android Studio, using a Pixel 4a API 30 VM.

## Iteration 1:

### As of Iteration 1, our application is able to:
1. Create a House
   1. along with a house, a corresponding Inventory and 
        manager are also created
2. Add/Remove Housemates to the House
3. View the Inventory and Shopping List of the House
4. Create Receipt to denote a shopping trip which adds items
    to the Inventory.
5. View Past Receipts
6. Add Debts to each Housemate

### As of Iteration 1, our application is not able to but will be able to eventually:
1. Add/Remove from Inventory without a Receipt
2. View and Manage Debts
### Also Note:
At this time, our application is not able to manage multiple houses,
it also does not have any data persistence and for each run, a new 
house is created and the application resets everything to being empty.
This is something that was a class-wide assumption for this iteration
and will be discussed and implemented later in the semester.

### Other Assumptions for this Iteration:
At this point, our application works correctly under the 
assumption that the user will use it properly. Our application 
does crash when it receives an unexpected input. This error 
catching is to be implemented in later iterations.

Our application runs in the terminal and operates through
prompts and user inputs. In future iterations this will be 
implemented through the android app rather than the terminal.

The Main method is located in Main.java so the application
should be run through there with no command line arguments.
