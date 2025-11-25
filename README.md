# Online Food Delivery System (Java + Swing + JDBC + MySQL)

This is my Java-based desktop application made using Java and Swing.  
It simulates an online food ordering system where users can browse restaurants, see menus, add items to cart, place orders, and track them in real time.  
I built this project mainly to practice Java OOP, collections, GUI design, JDBC and MySQL connectivity.

---

## 📌 Overview

This application includes:

### ✔ User Side
- Signup
- Login
- Restaurant list
- Menu items
- Add to cart
- Place Order
- Track Order (status changes automatically)

### ✔ Admin Side (Basic Structure)
- Manage restaurants
- Manage menu items
- View orders

Order tracking is simulated using a background thread that updates status from:
```
PLACED → PREPARING → OUT FOR DELIVERY → DELIVERED
```

The project is divided into packages like `dao`, `model`, `ui`, `utils`, `main` for good structure.

---

## 💻 Technologies Used
- Java 11+
- Swing GUI
- MySQL 8.x
- JDBC
- IntelliJ IDEA

---

## ⭐ Main Features

- User signup & login
- Browse restaurants
- View menu items for each restaurant
- Add items to cart
- Place order (writes into `orders` and `order_items` tables)
- Real-time order tracking simulation
- DAO pattern for database handling
- Clean folder & package structure
- Uses Java Collections, OOP & Exceptions

---

## 📁 Project Structure

```
OnlineFoodDeliverySystem/
 ├── README.md
 ├── database.sql
 ├── src/main/java/com/onlinefooddelivery/
 │     ├── dao/
 │     ├── model/
 │     ├── ui/
 │     ├── utils/
 │     └── main/
 ├── .gitignore
 └── screenshots/     (optional folder for images)
```

---

## 🗄️ Database Setup (MySQL)

Use the provided **database.sql** file.

Example:

```sql
CREATE DATABASE IF NOT EXISTS fooddeliverydb;
USE fooddeliverydb;
```

Tables created:
- users
- restaurants
- menu_items
- orders
- order_items

If sample data exists, you can test login with:

```
Email: adarsh@example.com
Password: 12345
```

---

## 🔧 JDBC Database Connection Setup

Edit:

```
src/main/java/com/onlinefooddelivery/utils/DBConnection.java
```

Update credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/fooddeliverydb";
private static final String USERNAME = "root";
private static final String PASSWORD = "root@12345678";
```

Also make sure MySQL Connector JAR (mysql-connector-j) is added to project Libraries.

---

## ▶️ How to Run the Project

1. Open the project in IntelliJ IDEA
2. Mark `src/main/java` as **Sources Root**
3. Import MySQL Connector JAR
4. Run MySQL server and import database.sql
5. Build → Rebuild Project
6. Run the Main class:

```
com.onlinefooddelivery.main.Main
```

If everything is correct, the Login window will open.

---

## 🧪 How to Test the App

1. Signup a new user
2. Login
3. Select a restaurant
4. Add menu items to cart
5. View Cart → click **Place Order**
6. Order Tracking window appears (status updates automatically)

Check MySQL tables:

```sql
SELECT * FROM users;
SELECT * FROM orders;
SELECT * FROM order_items;
```

---

## ❗ Troubleshooting

**DB connection error?**
- Check your MySQL is running
- Check DBConnection.java credentials
- Make sure JDBC driver is added

**Package errors?**
- Only `src/main/java` must be marked as Sources Root.

**JAR missing?**
- Add MySQL connector manually or add Maven dependency.

---

![WhatsApp Image 2025-11-25 at 21 18 27_57a20c5d](https://github.com/user-attachments/assets/db5c47b3-ecf2-45c7-ab92-b5fa64749bca)

![WhatsApp Image 2025-11-25 at 21 18 30_1fbdb5a9](https://github.com/user-attachments/assets/3ada162e-fd22-454b-b680-2df92cd9c9f9)

![WhatsApp Image 2025-11-25 at 21 18 31_d9245e40](https://github.com/user-attachments/assets/a3c9d06c-3636-40aa-a433-0ba1d574c9f1)

