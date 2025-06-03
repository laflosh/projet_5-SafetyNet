# Project 5 – SafetyNet

**SafetyNet** is the 5th project of the Java Developer path at OpenClassrooms.  
The purpose of this application is to efficiently connect individuals with emergency services to facilitate professional interventions.

## Project Objective

To develop the backend of the SafetyNet application by creating a **REST API** capable of returning various types of information depending on client requests.

A **JSON** file located at the root of the project contains the initial dataset required to start development.  
Three main entities are defined: `Person`, `MedicalRecord`, and `Firestation`.

## Developed Features

- Implementation of **CRUD routes** for each entity.
- Creation of **custom endpoints** to meet specific requirements.

## Specific Endpoints

The following endpoints allow for:

- Retrieving all persons covered by a specific firestation.
- Getting information about children living at a given address.
- Listing all phone numbers of residents near a firestation.
- Retrieving the people living at a given address along with their corresponding firestation.
- Listing all persons covered by one or more firestations.
- Retrieving people by their last name.
- Listing all email addresses from a specific city.

## Testing

A complete unit test campaign was implemented to verify all developed functionalities.  
Code coverage is measured using the **JaCoCo** tool, and reports are available in the `site` folder of the repository.

## Tech Stack

- Java  
- Spring Boot  
- Spring Web Starter  
- JUnit 5
