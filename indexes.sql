/* create an index for the course table using the attributes semester and year */
create index course_index
on course(semester, year);
