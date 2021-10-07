## Purpose 

Showcase usage of Quarkus with Java 17 language features

### Includes features

* Java Records
* Multi-line strings
* New Optional methods
* New List/Map creation methods

## High level overview

Exercise service => Database + REST
Wellness service => Calls Exercise service and external Recipes service with REST Client. Also sends Orders to Kafka
Mail service => Reads Orders from Kafka and sends email
