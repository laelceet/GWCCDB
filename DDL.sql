/* create the student table */
create table student(
	id int identity (1000,1) unique not null,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	grade varchar(20) not null,
	high_school_name varchar(100) not null,
	primary key (id),
	check (grade in ('freshman', 'sophomore', 'junior', 'senior'))
)

/* create the course table */
create table course(
	id int identity (1, 1) not null unique,
	type varchar(15) not null,
	semester varchar(10) not null,
	year numeric(4, 0),
	primary key (id),
	check (type in ('Python', 'Arduino')),
	check (semester in ('Fall', 'Spring'))
)	

/* create the facilitator table */
create table facilitator (
	id int identity (1000,1) unique not null,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	grade varchar(20) not null,
	status varchar(20) not null,
	check (grade in ('freshman', 'sophomore', 'junior', 'senior')),
	check (status in ('active', 'inactive')),
	primary key (id)
)


/* create the lead_group table */
create table lead_group (
	id int identity (1,1) unique not null,
	group_lead_id int,
	primary key (id),
	foreign key (group_lead_id) references facilitator(id) on delete set null
)

/* create the session table */
create table session(
	id int identity(1, 1) not null unique, 
	title varchar(20) not null,
	course_id int, 
	date date not null, 
	group_id int, 
	primary key(id),  
	foreign key (course_id) references course(id) on delete set null, 
	foreign key (group_id) references lead_group(id) on delete set null
)

/* create the group facilitators table */
create table group_facilitators (
	group_id int not null,
	facilitator_id int not null,
	primary key (group_id, facilitator_id),
	foreign key (group_id) references lead_group(id) on delete cascade,
	foreign key (facilitator_id) references facilitator(id) on delete cascade
)

/* create the exec_member table */
create table exec_member(
	role varchar(20) not null,
	facilitator_id int not null,
	academic_year varchar(20) not null,
	foreign key (facilitator_id) references facilitator(id) on delete cascade
)

/* create the meeting table */
create table meeting(
	id int identity (1,1) unique not null,
	date date not null,
	semester varchar(6),
	primary key (id),
	check (semester in ('Fall', 'Spring'))
)

/* create the enroll table */
create table enroll (
	student_id int, 
	course_id int, 
	primary key(student_id, course_id), 
	foreign key (student_id) references student(id) on delete cascade, 
	foreign key (course_id) references course(id) on delete cascade
)

/* create the student_attends table */
create table student_attends(
	student_id int not null, 
	session_id int not null, 
	primary key (student_id, session_id),
	foreign key (student_id) references student(id) on delete cascade,
	foreign key (session_id) references session(id) on delete cascade
)

/* create the assists table */
create table assists (
	facilitator_id int, 
	session_id int, 
	primary key(facilitator_id, session_id), 
	foreign key (facilitator_id) references facilitator(id) on delete cascade, 
	foreign key (session_id) references session(id) on delete cascade
)

/* create the facilitator_attends table */
create table facilitator_attends(
	meeting_id int not null,
	facilitator_id int not null,
	primary key (meeting_id, facilitator_id),
	foreign key (meeting_id) references meeting(id) on delete cascade,
	foreign key (facilitator_id) references facilitator(id) on delete cascade
)

/* create an index for the course table using the attributes semester and year */
create index course_index
on course(semester, year);
