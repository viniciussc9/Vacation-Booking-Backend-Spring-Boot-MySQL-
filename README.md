# Vacation Booking Backend

Spring Boot back-end for a single-page vacation booking application.  
The service exposes REST APIs for vacations, excursions, customers, and checkout, backed by a MySQL database and consumed by an Angular front end.

---

## Features

- **Domain model for travel bookings**
  - `Vacation`, `Excursion`, `Customer`, `CartItem`, `Cart`, `Address`, `Country`, `State`.
- **RESTful API**
  - Spring Data REST repositories with a common base path `/api`.
  - Dedicated `CheckoutController` for placing orders.
- **Checkout workflow**
  - Accepts a customer plus cart items in one request.
  - Generates an order tracking number and persists the order and line items.
- **Validation**
  - Bean Validation annotations on entities and DTOs to enforce the inputs expected by the Angular front end.
- **Data access layer**
  - JPA entities mapped to an existing MySQL schema.
  - Repository interfaces with cross-origin support for the front end.
- **Service layer**
  - `Purchase`, `PurchaseResponse`, `CheckoutService`, `CheckoutServiceImpl` implementing the business logic.
- **Sample data**
  - Programmatic seeding of example customers on startup (without overwriting existing data).

---

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** MySQL
- **Build:** Maven
- **Other:** Lombok, Spring Data REST, Bean Validation (Jakarta Validation)

---

## Project Structure


src/
  main/
    java/com/d288/vdasi11/
      config/
        RestDataConfig.java        # Exposes entities via Spring Data REST under /api
      entities/
        Address.java
        Cart.java
        CartItem.java
        Country.java
        Customer.java
        Excursion.java
        State.java
        Vacation.java
      dao/
        AddressRepository.java
        CartItemRepository.java
        CountryRepository.java
        CustomerRepository.java
        ExcursionRepository.java
        StateRepository.java
        VacationRepository.java
      services/
        Purchase.java
        PurchaseResponse.java
        CheckoutService.java
        CheckoutServiceImpl.java
      controllers/
        CheckoutController.java    # POST /api/checkout/purchase
      VacationDemoApplication.java # Spring Boot entry point

  test/
    java/com/d288/vdasi11/
      ... (unit tests and repository tests)
