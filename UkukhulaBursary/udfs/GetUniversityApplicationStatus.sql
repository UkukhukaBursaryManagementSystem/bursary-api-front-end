CREATE FUNCTION dbo.GetUniversityApplicationStatusByUniversityName
(
    @UniversityName VARCHAR(100)
)
RETURNS VARCHAR(20)
AS
BEGIN
    DECLARE @Status VARCHAR(20)

    SELECT @Status = AS.Status
    FROM dbo.ApplicationStatus AS AS
    JOIN dbo.UniversityApplication AS UA ON AS.ID = UA.StatusID
    JOIN dbo.University AS U ON UA.UniversityID = U.ID
    WHERE U.UniversityName = @UniversityName

    RETURN @Status
END
GO