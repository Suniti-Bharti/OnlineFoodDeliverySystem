
-- Improved schema for Food Delivery system
CREATE DATABASE IF NOT EXISTS fooddeliverydb;
USE fooddeliverydb;

-- Drop tables if exist
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;

-- users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- restaurants
CREATE TABLE restaurants (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    location VARCHAR(200),
    rating DECIMAL(2,1) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- menu_items
CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    item_name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    is_available TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- orders
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'PLACED',
    total_amount DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- order_items
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id) ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Indexes for performance
CREATE INDEX idx_menu_rest ON menu_items(restaurant_id);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_rest ON orders(restaurant_id);

-- Seed data (restaurants + menus + a user)
INSERT INTO users (name, email, phone, address, password) VALUES
('Adarsh Kumar', 'adarsh@example.com', '9876543210', 'Patna, Bihar', '12345');

INSERT INTO restaurants (name, location, rating) VALUES
('Tandoori House', 'Delhi', 4.6),
('Spicy Treat', 'Mumbai', 4.4),
('Green Leaf', 'Bangalore', 4.3);

INSERT INTO menu_items (restaurant_id, item_name, description, price) VALUES
(1,'Chicken Tandoori','Smoky tandoori chicken',350.00),
(1,'Butter Naan','Soft naan with butter',40.00),
(2,'Veg Thali','Full veg thali meal',180.00),
(2,'Masala Dosa','South Indian dosa',90.00),
(3,'Salad Bowl','Healthy salad mix',120.00),
(3,'Pasta Alfredo','Creamy white sauce pasta',250.00);
