ALTER TABLE Student
ADD CONSTRAINT check_age CHECK (age >= 16);

ALTER TABLE Student
ADD CONSTRAINT unique_name UNIQUE (name);

ALTER TABLE Student
ADD CONSTRAINT not_null_name CHECK (name IS NOT NULL);

ALTER TABLE Faculty
ADD CONSTRAINT unique_name_color UNIQUE (name, color);

CREATE TABLE Student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT DEFAULT 20,
    faculty_id INT,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id),
    UNIQUE (name),
    CHECK (age >= 16)
);