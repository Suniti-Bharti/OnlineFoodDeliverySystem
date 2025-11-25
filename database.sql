-- Create database
CREATE DATABASE IF NOT EXISTS fooddeliverydb;
USE fooddeliverydb;

-- Users table (customers)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    address VARCHAR(255),
    password VARCHAR(100)
);

-- Restaurants table
CREATE TABLE restaurants (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    location VARCHAR(255),
    rating DECIMAL(2,1)
);

-- Menu items
CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT,
    item_name VARCHAR(100),
    description VARCHAR(255),
    price DECIMAL(10,2),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
);

-- Orders table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    restaurant_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50),
    total_amount DECIMAL(10,2),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
);

-- Order Items
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

-- Delivery Partner table
CREATE TABLE delivery_partners (
    partner_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(15),
    vehicle VARCHAR(50),
    status VARCHAR(50)
);

-- Sample Data
INSERT INTO users (name, email, phone, address, password) VALUES
('Adarsh Kumar', 'adarsh@example.com', '9876543210', 'Patna, Bihar', '12345');

INSERT INTO restaurants (name, location, rating) VALUES
('Burger Tower', 'Patna', 4.5),
('Pizza Magic', 'Gaya', 4.2);

INSERT INTO menu_items (restaurant_id, item_name, description, price) VALUES
(1, 'Veg Burger', 'Cheese veg burger', 120.00),
(1, 'French Fries', 'Crispy fries', 80.00),
(2, 'Paneer Pizza', 'Medium sized', 300.00);
