-- SQL script to create college_db and tables with sample data
DROP DATABASE IF EXISTS college_db;
CREATE DATABASE college_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE college_db;

CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  rollno VARCHAR(100) UNIQUE NOT NULL,
  phone VARCHAR(50),
  address VARCHAR(500),
  photo_url VARCHAR(1000),
  password VARCHAR(255),
  COA INT DEFAULT 0,
  DSA INT DEFAULT 0,
  PDC INT DEFAULT 0,
  POM INT DEFAULT 0,
  WEBDEV INT DEFAULT 0,
  DMS INT DEFAULT 0
);

CREATE TABLE teachers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  subject VARCHAR(100) NOT NULL,
  phone VARCHAR(50),
  photo_url VARCHAR(1000),
  password VARCHAR(255)
);

CREATE TABLE admin (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);

-- Insert teacher list (as required)
INSERT INTO teachers (name, subject, phone, photo_url, password) VALUES
('Naveen Saini', 'COA', NULL, 'naveen123'),
('Shiv Ram Dubey', 'DSA', NULL, 'shiv123'),
('Shanti Chandra', 'PDC', NULL, 'shanti123'),
('Sandeep Kesharwani', 'POM', NULL, 'sandeep123'),
('Bibhash Ghoshal', 'WEB-DEV', NULL, 'bibhash123'),
('Saurabh Verma', 'DMS', NULL, 'saurabh123');

-- Sample students
INSERT INTO students (name, rollno, phone, address, photo_url, password, COA, DSA, PDC, POM, WEBDEV, DMS) VALUES
('Ankit Sharma','R001','9999999001','123 College Road','assets/student_placeholder.jpg','pass1',78,82,75,80,85,79),
('Riya Singh','R002','9999999002','45 Campus Lane','assets/student_placeholder.jpg','pass2',88,91,79,84,90,86),
('Mohit Kumar','R003','9999999003','12 Hostel Street','assets/student_placeholder.jpg','pass3',67,71,69,72,70,68);

-- Admin user
INSERT INTO admin (username, password) VALUES ('admin', 'admin');
