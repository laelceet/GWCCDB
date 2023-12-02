/* insert into student table */
insert into student (first_name, last_name, grade, high_school_name)
values
	('Julia', 'Smith', 'junior', 'Cleveland High School'),
	('Ava', 'Martin', 'freshman', 'Local High School'),
	('Eleanor', 'Miller', 'sophomore', 'Greater Cleveland High School'),
	('Charlotte', 'Williams', 'senior', 'Cleveland Prep School'),
	('Olivia', 'Johnson', 'sophomore', 'Cleveland High School'),
	('Mia', 'Garcia', 'freshman', 'Local High School'),
	('Danielle', 'Munich', 'junior', 'Global High School'),
	('Bethany', 'Baldwell', 'freshman', 'Shaker Rocks High'), 
	('Stephanie', 'Hippo', 'sophomore', 'Cleveland Prep School'),
	('Theodora', 'Thompson', 'senior', 'Akron School of Arts'),
	('Helga', 'Germaain', 'sophomore', 'Local High School'),
	('Zoe', 'Fern', 'senior', 'Local High School'),
	('Anika', 'Reagan', 'junior', 'Cuyahoga High School'),
	('Julia', 'Smith', 'freshman', 'Cuyahoga High School');

/* insert into course table */
insert into course (type, semester, year)
values
	('Python', 'Spring', 2023),
	('Arduino', 'Spring', 2023),
	('Python', 'Fall', 2023),
	('Arduino', 'Fall', 2023);

/* insert into facilitator table */
insert into facilitator (first_name, last_name, grade, status)
values
	('Lauren', 'Eterno', 'junior', 'active'),
	('Zoe', 'Goldberg', 'junior', 'active'),
	('Ali', 'Puccio', 'junior', 'active'),
	('Joy', 'Fan', 'sophomore', 'active'),
	('Tina', 'Lane', 'senior', 'inactive'),
	('Ana', 'Perez', 'senior', 'active'),
	('Franny', 'Fresh', 'freshman', 'active'),
    ('Anna', 'Smith', 'sophomore', 'active'),
    ('Emily', 'Cher', 'senior', 'active'),
    ('Eve', 'Bro', 'sophomore', 'active'),
    ('Emma', 'Gui', 'senior', 'active'),
    ('Eliza', 'Kim', 'junior', 'active'),
    ('Jenny', 'Garcia', 'freshman', 'active'),
    ('Sarah', 'Johnson', 'sophomore', 'active');

/* insert into lead_group table */
insert into lead_group (group_lead_id)
values
	(1000), 
	(1001), 
	(1008), 
	(1002), 
	(1003), 
	(1005);

/* insert into session */
insert into session (title, course_id, date, group_id)
values
	('Intro', 3,'2023-09-02', 1),
	('Variables', 3, '2023-09-09', 2),
	('Loops', 3, '2023-09-16', 3),
	('Intro', 4, '2023-09-03', 4),
	('Circuits', 4, '2023-09-10', 5),
	('Wires', 4, '2023-09-17', 6),
	('Intro', 1, '2023-02-03', 1),
	('Variables', 1, '2023-02-10', 2),
	('Loops', 1, '2023-02-17', 3),
	('Intro', 2, '2023-02-04', 4),
	('Circuits', 2, '2023-02-11', 5),
	('Wires', 2, '2023-02-18', 6);

/* insert into group_facilitators table */
insert into group_facilitators (group_id, facilitator_id)
values	
	(1, 1006),
	(1, 1013),
	(2, 1007),
	(2, 1004),
	(3, 1009),
	(4, 1010),
	(5, 1011),
	(6, 1012);

/* insert into exec_member table */
insert into exec_member (role, facilitator_id, academic_year)
values
	('President', 1006, '2023-2024'),
	('Vice President', 1004, '2023-2024'),
	('PR Chair', 1005, '2023-2023'),
	('President', 1003, '2022-2023'),
	('Vice President', 1002, '2022-2023'),
	('PR Chair', 1001, '2022-2023');

/* insert into meeting table */
insert into meeting (date, semester)
values
	('2023-02-01', 'Spring'),
	('2023-09-07', 'Fall');

/* insert into enroll table */
insert into enroll (student_id, course_id)
values
	(1001, 1),
	(1001, 4),
	(1002, 2),
	(1002, 3),
	(1003, 3),
	(1004, 4),
	(1005, 1),
	(1006, 2),
	(1007, 4),
	(1008, 4),
	(1008, 3),
	(1009, 1),
	(1010, 2),
	(1011, 2),
	(1012, 3),
	(1013, 1);

/* insert into attends table */
insert into student_attends (student_id, session_id)
values
	(1001, 7),
	(1001, 8),
	(1001, 9),
	(1002, 2),
	(1002, 10),
	(1002, 11),
	(1002, 12),
	(1002, 1),
	(1002, 2),
	(1002, 3),
	(1003, 1),
	(1003, 2),
	(1004, 4),
	(1004, 5),
	(1004, 6),
	(1005, 7),
	(1005, 8),
	(1005, 9),
	(1006, 10),
	(1006, 12),
	(1007, 4),
	(1007, 6),
	(1008, 4),
	(1008, 5),
	(1008, 6),
	(1008, 1),
	(1008, 2),
	(1008, 3),
	(1009, 7),
	(1009, 8),
	(1009, 9),
    	(1010, 10),
	(1011, 11),
	(1012, 3),
	(1013, 8);

/* insert into assists table */
insert into assists (facilitator_id, session_id)
values
	(1007, 1),
	(1009, 2),
	(1006, 3),
	(1010, 4),
	(1000, 5),
	(1011, 6),
	(1012, 7),
	(1013, 8),
	(1002, 9),
	(1005, 10),
	(1000, 11),
	(1001, 12);

/* insert into facilitator_attends table */
insert into facilitator_attends (meeting_id, facilitator_id)
values
	(1, 1000),
	(2, 1000),
	(1, 1001),
	(2, 1001),
	(1, 1002),
	(2, 1002),
	(1, 1003),
	(2, 1003),
	(1, 1004),
	(2, 1004),
	(1, 1005),
	(2, 1005),
	(1, 1006),
	(2, 1006),
	(1, 1007),
	(2, 1007),
	(1, 1008),
	(2, 1008),
	(1, 1009),
	(2, 1009),
	(1, 1010),
	(2, 1011),
	(1, 1012),
	(2, 1013);
