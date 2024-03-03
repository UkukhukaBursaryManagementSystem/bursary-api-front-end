CREATE OR ALTER FUNCTION GetTotalUniversityAllocatedToStudents(@UniversityName varchar(100), @Year int)
RETURNS MONEY
AS
BEGIN
DECLARE @TotalAllocated MONEY;
SELECT @TotalAllocated = ISNULL(SUM(Amount),0) FROM [dbo].[StudentAllocationView] WHERE UniversityName = @UniversityName AND [Year] = @Year
RETURN @TotalAllocated
END