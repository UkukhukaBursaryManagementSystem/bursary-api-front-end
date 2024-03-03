CREATE OR ALTER FUNCTION   [dbo].getTotalAllocatedToUniversity (@UniversityName VARCHAR(100), @AllocationYear INT)
RETURNS MONEY
AS
BEGIN
DECLARE @TotalAllocated MONEY
SELECT @TotalAllocated =   ISNULL(SUM(Amount), 0) FROM [dbo].[UniversityAllocationsView]  WHERE UniversityName = @UniversityName AND [Year] = @AllocationYear
RETURN @TotalAllocated
END