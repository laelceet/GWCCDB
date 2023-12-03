CREATE TRIGGER trg_UpdateExecPosition
ON exec_member
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @PositionName NVARCHAR(20), @AcademicYear VARCHAR(20);
    SELECT TOP 1 @PositionName = role, @AcademicYear = academic_year
    FROM inserted;

    -- Check if the position title does not start with "Co-"
    IF LEFT(@PositionName, 3) <> 'Co-'
    BEGIN
        -- Update the PositionName to Co-[PositionName]
        UPDATE exec_member
        SET role = 'Co-' + @PositionName
        WHERE role = @PositionName
          AND academic_year = @AcademicYear;
    END
END; 
