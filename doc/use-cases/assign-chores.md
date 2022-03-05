### Use case - Assign Chores

**Scope**
- Housemate Management Application

**Level**
- User Goal

**Primary actor**
- Housemate A

**Stakeholders and interest**
- Housemate A: Wants convenient assignment of chores. 
- Housemate B: Wants easy access to their chore assignments. Wants workload to be fair.

**Preconditions**
- User is identified and authenticated 
- Chore list database is set up and connected to Hub

**Postconditions**
- Chore list is appropriately updated
- Housemate B is notified of their chore assignment

**Main success scenario**
1. Housemate A opens chore list in the application 
2. Housemate A selects between personal chore list tab or group chore list tab and proceeds accordingly 
3. Housemate A writes name of chore to assign, selects Housemate B, and how long the chore takes to refresh.
4. System saves entered information along with identity of Housemate A.
5. System presents updated chore list and notifies Housemate B
6. Housemate B opens the app and views the assigned chore and who added the chore.

**Extensions**
  
3a. Invalid input (e.g. unsupported characters, empty data field)
1. System signals error and rejects entry

3b. The responsible Housemate is not specified.
1. System automatically assigns new chores to Housemates based on number of chores already assigned

   1a. Housemates have the same number of chores.
   1. System randomly assigns chore.

4a. Failure to save information  
1. System signals error and rejects entry

*a At any time, chore list is updated by other housemate
1. System updates local copy of chore list as needed 
2. Any additions are made to the newest version of the chore list

*b At any time, system crashes:  
1. Housemate restarts system.
2. System reconstructs prior state if possible, works as default otherwise

**Special requirements**
- Touch screen UI on a small flat panel monitor. Text must be readable from .5 meters
- Notification delivery time within 5 seconds
- Support for common languages and currencies

**Technology and data variations**
- N/A

**Frequency of occurrence**
- Moderate frequency

**Miscellaneous**
- N/A