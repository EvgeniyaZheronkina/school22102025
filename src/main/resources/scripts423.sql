SELECT student.name, student.age, faculty.name 
FROM student INNER JOIN faculty ON student.id = faculty_id;

SELECT student.name, student.age, avatar.file_path 
FROM student RIGHT JOIN avatar ON student.id = avatar.id;