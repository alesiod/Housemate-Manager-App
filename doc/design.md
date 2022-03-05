#Domain Model:
```plantuml
@startuml

hide circle
hide empty methods

'classes

class Inventory{}
class Item{}
class Receipt{}
Class Housemate{}
Class ControllerActivity{}      
Class House{}
Class ReceiptItem{}    
Class ReceiptManager{}
Class ChoreManager{}
Class AuthKey{}
    
' Associations
 
ControllerActivity "1" -right- "1" House : contains
ControllerActivity "1" -up- "1" Inventory : contains
ControllerActivity "1" -left- "1" ReceiptManager : contains
ControllerActivity "1" -down- "1" ChoreManager : contains
Inventory "1" -up- "1..*" Item : stored in
House "1" - "1..*" Housemate : contains
ChoreManager "1" -down- "1..*" Chore : manages
ReceiptManager "1" -left- "1" Receipt : \tis controlled by\t
Receipt "1" -- "1..*" ReceiptItem : contains
ReceiptManager "1" - "1" Inventory : \tadds Items to\t\t
Chore"1..*" - "1" Housemate : \t\tassigned to \t\t
ChoreManager -[hidden]right- House : \t\tassigned to \t\t
House -right- AuthKey : has a unique
 
 @enduml
 ```

#Sequence Diagram For Adding/Removing Housemates

```plantuml

 'Sequence Diagram
 
 hide footbox

participant " : House" as House
participant " : Housemate" as Housemate


House -> Housemate : addHousemate()
House -> Housemate : removeHousemate()

```
#Sequence Diagram For Creating A Receipt

```plantuml

 'Sequence Diagram
 
 hide footbox

participant " : Housemate" as Housemate
participant " : ReceiptManager" as ReceiptManager
participant " : Receipt" as Receipt
participant " : Inventory" as Inventory



ReceiptManager -> Receipt : startReceipt()
ReceiptManager -> Receipt : addItem()
ReceiptManager -> Receipt : removeItem()
ReceiptManager -> Receipt : itemsDone()
ReceiptManager -> Receipt : endReceipt()
Receipt -> Inventory : process()
Receipt -> Housemate : addDebt()
Inventory -> Inventory : addItem()
Inventory <- Inventory : removeItem()

```


#Sequence Diagram for Editing Inventory/Shopping List
```plantuml

 'Sequence Diagram
 
 hide footbox

participant " : Inventory" as Inventory
participant " : ShoppingList" as ShoppingList
participant " : Item" as Item


Inventory -> Inventory : addItem()
Inventory <- Inventory : removeItem()
Inventory -> ShoppingList : getShoppingList()
ShoppingList -> Item : subDesired()
ShoppingList -> Item : addDesired()


```
#Sequence Diagram for Editing Chore List
```plantuml

 'Sequence Diagram
 
 hide footbox

participant " : ChoreManager" as ChoreManager
participant " : Chore" as Chore
participant " : Housemate1" as Housemate1
participant " : Housemate2" as Housemate2



ChoreManager -> Chore : new Chore()
Chore -> Housemate1 : setAssigner();
Chore -> Housemate2 : setOwner();
ChoreManager -> Chore : removeChore()


```

#Sequence Diagram for Editing Debts
```plantuml

 'Sequence Diagram
 
hide footbox

participant " : Housemate1" as Housemate1 
participant " : Housemate2" as Housemate2


Housemate1 -> Housemate2 : addDebt()
Housemate1 -> Housemate2 : removeDebt()



```

#Sequence Diagram for Firebase Communication
```plantuml

 'Sequence Diagram
 
hide footbox

participant " : House" as House
participant " : ReceiptManager" as ReceiptManager
participant " : FirestoreFacade" as FirestoreFacade 
participant " : ChoreManager" as ChoreManager
participant " : Inventory" as Inventory

FirestoreFacade -> House : createHouseIfNotExists()
FirestoreFacade -> House : setHouse()
FirestoreFacade -> House : retrieveHouse()
FirestoreFacade -> ReceiptManager : setReceipts()
FirestoreFacade -> ReceiptManager : retrieveReceipts()
FirestoreFacade -> ChoreManager : setChore()
FirestoreFacade -> ChoreManager : retreiveChore()
FirestoreFacade -> Inventory : setInven()
FirestoreFacade -> Inventory : retrieveInven()
FirestoreFacade -> FirestoreFacade : set()



```






#Class Diagram:
```plantuml
@startUML

class AuthKey{
salt : String
key : String
---
validatePassword(String) : Boolean
generateSalt() : String
generateKey(String, String) : String
}


class Chore{
Name : String
Assigner : String
Owner : String
---
equals(Chore) : Boolean
}

class ChoreManager{
ChoreList : ArrayList<Chore>
---
assignChore(String, String, String) : void
removeChore(String) : Boolean
removeChore(String, String, String) : Boolean
getChoreListNames() : List<String>

}

class House{
housemates : List<Housemate>
username : String
authKey : AuthKey
---
validatePassword(String) : Boolean
addHousemate(String) : Boolean
removeHousemate(String) : Boolean
getHousemate(String) : Housemate
removeDebt(Housemate, Housemate, double) : double
addDebt(Housemate, Housemate, double) : double

}

class Housemate{
name : String
debts : HashMap<String, Double>
---
addDebt(Housemate, double) : void
getDebtsStringForm() : String
}

class Inventory{
items : HashMap<String, Item>
---
setItems(HashMap<String, Item>) : void
addItem(String, int) : void
useItem(String, int) : Boolean
addDesired(String, int) : void
subDesired(String, int) : boolean
hasItem(String) : Boolean
toStringShoppingList : String
}

class Item{
itemID String
desiredQuantity : int
currentQuantity : int
---

}

class Receipt{
debtors : List<Housemate>
payer : Housemate
items : Map<String, ReceiptItem>
itemsDone : Boolean
date : String
---
getDebtors() : List<Housemate>
addItem(string, int, double)
rmItem(string, int) : Boolean
optInItem(String, Housemate) : void
optOutItem(String, Housemate) : void
process(Inventory) : void
getTotal(Housemate) : double
}

class ReceiptItem{
itemID : String
quantity : int
price : double
debtors : List<String> 
---
paidBy(Housemate) : Boolean
optOut(Housemate) : void
optIn(Housemate) : void
eachPays() : double
}

class ReceiptManager{
pastReceipts Map<String, String>
r : Receipt
---
endReceipt(Inventory) : void
}


'Associations

House -- "(1..*)" Housemate
House -- "(1..1)" Inventory
House -left- "(1..1)" AuthKey
House -- "(1..1)" ReceiptManager
House -- "(1..1)" ChoreManager
ChoreManager -- "(1..*)" Chore
ReceiptManager -- "(1..1)" Receipt
Receipt -- "(1..*)"ReceiptItem
Inventory -- "(1..*)"Item



```
