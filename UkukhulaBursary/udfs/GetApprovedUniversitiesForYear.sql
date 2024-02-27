CREATE FUNCTION dbo.GetApprovedUniversityCountForYear(@Year INT)
RETURNS INT
AS
BEGIN
    DECLARE @ApprovedCount INT;

    SELECT @ApprovedCount = COUNT(DISTINCT UA.UniversityID)
    FROM dbo.UniversityApplication UA
    INNER JOIN dbo.ApplicationStatus AS AS1 ON UA.StatusID = AS1.ID
    INNER JOIN dbo.BursaryDetails BD ON UA.Year = BD.Year
    WHERE AS1.Status = 'Approved' AND BD.Year = @Year;

    RETURN @ApprovedCount;
END;
GO