-- SQL Setup for SportFlow Application
CREATE DATABASE IF NOT EXISTS sportflow;
USE sportflow;

-- Table for User information
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    weight_kg FLOAT,
    height_cm FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Sports Activities
CREATE TABLE IF NOT EXISTS activities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    activity_type VARCHAR(50), -- e.g., 'Running', 'Cycling', 'Swimming'
    duration_minutes INT,
    distance_km FLOAT,
    calories_burned INT,
    intensity VARCHAR(20), -- 'Low', 'Medium', 'High'
    activity_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table for Nutrition Logs
CREATE TABLE IF NOT EXISTS nutrition (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    meal_type VARCHAR(50), -- 'Breakfast', 'Lunch', 'Dinner', 'Snack'
    calories INT,
    protein_g FLOAT,
    carbs_g FLOAT,
    fats_g FLOAT,
    log_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table for Performance Goals
CREATE TABLE IF NOT EXISTS goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    goal_type VARCHAR(100), -- e.g., 'Lose 5kg', 'Run 10km'
    target_value FLOAT,
    current_value FLOAT DEFAULT 0,
    deadline DATE,
    status VARCHAR(20) DEFAULT 'In Progress', -- 'In Progress', 'Achieved', 'Failed'
    FOREIGN KEY (user_id) REFERENCES users(id)
);
