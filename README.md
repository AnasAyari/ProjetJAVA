# Car Configurator

A Java application that allows users to create and customize cars by choosing different models, colors, engines, wheels, and interiors. The project was developed to practice Object-Oriented Programming concepts in Java.

## Features

- Create and manage users
- Configure custom cars
- Choose:
  - Car model
  - Color
  - Engine
  - Wheels
  - Interior
- Automatic price calculation
- Generate purchase receipts
- User-friendly Java Swing interface
- Custom exception handling

## Project Structure

### Car Package
Contains all classes related to vehicle configuration:

- Car
- Vehicle
- CarModel
- CarColor
- Engine
- Wheels
- Interior
- Component

### User Package
Handles user information:

- User
- Identifiable

### Receipt Package
Manages purchase receipts:

- Receipt

### Main Classes

- Main.java
- CarConfiguratorWindow.java

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming (OOP)

## OOP Concepts Applied

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces
- Custom Exceptions
- Builder Pattern

## Running the Project

### Requirements

- Java 17 or later
- Eclipse IDE (recommended)

### Steps

1. Clone the repository:

```bash
git clone https://github.com/AnasAyari/ProjetJAVA.git
```

2. Open the project in Eclipse.

3. Run `Main.java`.

## Example

A user can create a custom car by selecting:

- SUV Model
- Black Color
- 2.0L Engine
- Alloy Wheels
- Leather Interior

The application then calculates the total price and generates a receipt containing the vehicle details.

## Future Improvements

- Save configurations to files or a database
- Export receipts as PDF
- Add vehicle images
- Improve the graphical interface
- Add authentication and user accounts

## Author

**Anas Ayari**
**Seif Hachmi**
