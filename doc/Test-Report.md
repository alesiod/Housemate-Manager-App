#Test Report:
For this Test Report I will walk through how we tested
our app through the text-based interface.

Upon running the app, the system initializes a house and
asks you to input the number of housemates, for this test,
we will say there are 3 housemates.

Then, it asks you to enter the names of the housemates,
one at a time. For this test we will name the housemates
'Alice', 'Bob' and 'Charlie' and so we enter those names.

The system confirms as each housemate is added to the 
house.

We will enter the number 3 to ask the system to display
the roommates. Doing so confirms that Alice, Bob and
Charlie were all added successfully.

To test the ability to add Housemates, we will enter '1'.
Doing so will ask for the name of the housemate we wish
to add. For this test, let's name this housemate 'Dan.'

Again, we enter '3' to confirm that Dan was added 
successfully. He was. Now, we will enter '2' to remove a 
Housemate. For this test, we will remove Alice. Entering
Alice's name and entering '3' to check the housemates
once again confirms that Alice was removed successfully.

Now we will test the 'Receipt' aspect of our app. Let's 
say for this test that Bob went shopping and wants to 
log his receipt. We input '5' to start a receipt and enter
Bob as the name of the housemate who paid for it. The
System then asks for the number of items bought. For this
test let's suppose that 2 items were bought.

The system asks for the name of the first item that was 
bought. For this test, let's suppose that Bob bought 10
apples for a total of 20 dollars and 15 bananas for a total
of 5 dollars. We enter this information and the system will
log this receipt automatically.

We can look at the inventory to confirm that the items were
added properly and see that they were.

The last feature that we want to test from our app is to make
sure that when an item is bought that already exists in the 
inventory it updates the inventory correctly. By starting a
receipt to say that we 5 more apples for a price of dollars 
and then viewing the inventory we can confirm that the total
number of apples in the inventory is correct, 15.

Lastly we can input '6' to view the past receipts and
confirm that they are listed and correct.