# myRetail Product Rest API




## Tech Stack:
Spring, Java, MongoDB, Angular.JS


## Request Table

|Request type|Endpoint|Description|
|---|---|---|
|GET|/product/{id}|Return the product information matching the ID|
|GET|/product| Returns a list of all the products stored in the DB|
|PUT|/product/{id}|Update a products info|
|PUT|/product|Create a new product|
|DELETE|/product/{id} |Removes the product from local DB|


##Instructions for Running

1. Start a local instance of mongoDB

2.a. Run it in any Java IDE.  
2.a.1 build and run from your chosen IDE as you would any other program.  

2.b. Run a Build version.  
2.b.1 In the JAR folder is a runnable jar  
2.b.2 run the command: java -jar myRetail.jar  

5. REST interactions can also be done with a tool like PostMan.

