-- MySQL JDBC practice schema + sample data

CREATE DATABASE IF NOT EXISTS jdbc_practice;
USE jdbc_practice;

DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    credits INT NOT NULL
);

CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade CHAR(2),
    enrolled_on DATE NOT NULL,
    CONSTRAINT fk_enroll_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_enroll_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

INSERT INTO students (name, email) VALUES
('Asha Verma', 'asha.verma@example.com'),
('Ravi Kumar', 'ravi.kumar@example.com'),
('Mei Lin', 'mei.lin@example.com');

INSERT INTO courses (title, credits) VALUES
('Database Systems', 4),
('Java Programming', 3),
('Operating Systems', 4);

INSERT INTO enrollments (student_id, course_id, grade, enrolled_on) VALUES
(1, 1, 'A', '2026-01-15'),
(1, 2, 'B+', '2026-01-16'),
(2, 2, 'A-', '2026-01-16'),
(3, 3, NULL, '2026-01-20');
