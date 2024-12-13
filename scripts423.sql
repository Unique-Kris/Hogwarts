SELECT Student.name AS student_name, Student.age, Faculty.name AS faculty_name
FROM Student
JOIN Faculty ON Student.faculty_id = Faculty.id;

SELECT Student.name AS student_name, Student.age
FROM Student
JOIN Avatar ON Student.id = Avatar.student_id;