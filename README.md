**WildMarket: A Market for Teknoys**
======


### **CRUD GUIDELINES**


Create 
  - Listing an Item (SELLER ONLY)

Read
  - Can view all listed Items (ALL USERS)
  - Read User information such as Credits, Role, Name, and etc (ALL USERS)

Update
  - Can update own listing (SELLER ONLY)
  - Credits are updated upon transactions such as Taxes, Buying, Selling, and etc (ALL USERS)

Delete
  - Items are deleted after buying (ALL USERS)
  - Items can be deleted by owner (SELLER ONLY)

### **Atomicity**
  - SetAutocommit is set to false on buying an item in order to prevent race conditions from happening

Views Showcase
----

### Login
![image](https://github.com/tr-ggr/JDBC-JAVAFX/assets/132801873/d437a277-d110-490d-8174-b1477e883007)



### Buyer Homepage
![image](https://github.com/tr-ggr/JDBC-JAVAFX/assets/132801873/9d29a06e-349e-4523-95e6-5d25c1a785c9)



### Seller Homepage
![image](https://github.com/tr-ggr/JDBC-JAVAFX/assets/132801873/3f8f60c6-101b-422a-ab84-d161a831b516)


###### Made with love by Tristan James Y. Tolentino x JavaFX and BINI music

