/* create an index for the course table using the attributes semester and year */
create index course_index
on course(semester, year);

/* create an index for the student table using the attributes first_name and last_name */
create index student_name_index
on student(first_name, last_name);
