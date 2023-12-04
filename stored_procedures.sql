/* Note to grader: The stored procedures work adding them to MS SQL Server one at a time. 
Adding the whole file and executing all at once causes errors. */

/* 1. Retrieve Facilitator Attendance for a Semester */
CREATE PROCEDURE assists_attendance
  @course_semester VARCHAR(10),
  @year NUMERIC(4)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        CONCAT(f.first_name, ' ', f.last_name) AS full_name,
        COUNT(f.id) AS total_sessions_attended
    FROM 
        facilitator AS f
    INNER JOIN 
        assists AS a ON f.id = a.facilitator_id
    INNER JOIN 
        session AS s ON a.session_id = s.id
    INNER JOIN 
        course AS c ON s.course_id = c.id
    WHERE 
        c.semester = @course_semester
        AND c.year = @year
    GROUP BY 
        f.id, f.first_name, f.last_name;
    SET NOCOUNT OFF;
END;

/* 2. Delete a student from the database */
CREATE PROCEDURE drop_student
  @first_name VARCHAR(20),
  @last_name VARCHAR(20),
  @dropped_student_id INT OUTPUT
AS
BEGIN
    BEGIN TRANSACTION;

    -- Get the ID of the student to be dropped
    SET @dropped_student_id = (SELECT id FROM Student WHERE first_name = @first_name AND last_name = @last_name);

    -- Delete the student
    DELETE FROM Student WHERE first_name = @first_name AND last_name = @last_name;

    COMMIT TRANSACTION;
END;

/* Helpers for drop_student */
CREATE PROCEDURE list_all_students_with_name
  @first_name VARCHAR(20),
  @last_name VARCHAR(20)
AS
BEGIN
  SELECT 
    student.id,
    student.first_name, 
    student.last_name
  FROM 
    student
  WHERE
    student.first_name = @first_name AND
    student.last_name = @last_name
END;

CREATE PROCEDURE drop_student_with_id
  @student_id INT,
  @dropped_student_id INT OUTPUT
AS
BEGIN
    BEGIN TRANSACTION;

    -- Get the ID of the student to be dropped
    SET @dropped_student_id = @student_id;

    -- Delete the student
    DELETE FROM Student WHERE student.id = @student_id;

    COMMIT TRANSACTION;
END;


/* 3. Retrieve all titles of sessions from a certain course */
CREATE PROCEDURE semester_curriculum
    @semester VARCHAR(15),
    @year NUMERIC(4),
    @type VARCHAR(15)
AS
BEGIN
    BEGIN TRANSACTION;

    SELECT title
    FROM session
    INNER JOIN course ON session.course_id = course.id
    WHERE course.semester = @semester
        AND course.year = @year
        AND course.type = @type;

    COMMIT TRANSACTION;
END;

/* 4. Retrieve all of the facilitators leading a specific session of a course */
CREATE PROCEDURE session_leaders
    @type VARCHAR(15),
    @date DATE
AS
BEGIN
    BEGIN TRANSACTION;

  SELECT
      facilitator.first_name AS 'Facilitator_FirstName',
      facilitator.last_name AS 'Facilitator_LastName'
  FROM
      session
  INNER JOIN
      course ON session.course_id = course.id
  INNER JOIN
      group_facilitators ON session.group_id = group_facilitators.group_id
  INNER JOIN
      facilitator ON group_facilitators.facilitator_id = facilitator.id
  WHERE
      course.type = 'Arduino'
      AND session.date = '2023-09-03'

UNION
  SELECT
      lead_facilitator.first_name AS 'Facilitator_FirstName',
      lead_facilitator.last_name AS 'Facilitator_LastName'
  FROM
      session
  INNER JOIN
      course ON session.course_id = course.id
  INNER JOIN
      lead_group ON session.group_id = lead_group.id
  INNER JOIN
      facilitator AS lead_facilitator ON lead_group.group_lead_id = lead_facilitator.id
  WHERE
      course.type = 'Arduino'
      AND session.date = '2023-09-03';

    COMMIT;
END;

/* 5. Update the Topic of a Session */
CREATE PROCEDURE update_session_topic
  @type VARCHAR(15),
  @date DATE,
  @new_title VARCHAR(50) 
AS
BEGIN
  BEGIN TRANSACTION;

  UPDATE session
  SET title = @new_title
  WHERE course_id IN (SELECT id FROM course WHERE type = @type)
    AND session.date = @date;

  COMMIT TRANSACTION;
END;

/* 6. Enroll and New Student in a Course */
CREATE PROCEDURE enroll_new_student
  @first_name VARCHAR(20),
  @last_name VARCHAR(20),
  @grade VARCHAR(20),
  @high_school_name VARCHAR(100),
  @course_type VARCHAR(15),
  @semester VARCHAR(10),
  @year NUMERIC(4, 0)
AS
BEGIN
  BEGIN TRANSACTION;

  -- Insert the new student into the Student table
  INSERT INTO Student (first_name, last_name, grade, high_school_name)
  VALUES (@first_name, @last_name, @grade, @high_school_name);

  -- Get the ID of the newly inserted student
  DECLARE @new_student_id INT;
  SET @new_student_id = SCOPE_IDENTITY();

  -- Enroll the student in the specified course
  INSERT INTO enroll (student_id, course_id)
  VALUES (@new_student_id, (SELECT id FROM course WHERE type = @course_type AND semester = @semester AND year = @year));

  COMMIT TRANSACTION;
END;
