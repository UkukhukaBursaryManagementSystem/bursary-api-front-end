CREATE OR ALTER FUNCTION GetRemainingUniversityFunds(@UniversityName varchar(100), @Year int)
RETURNS MONEY
AS
BEGIN
DECLARE @RemainingBalance MONEY
DECLARE @TotalAllocated MONEY
DECLARE @TotalSpent MONEY

SELECT @TotalAllocated = [dbo].getTotalAllocatedToUniversity (@UniversityName , @Year);
SELECT @TotalSpent = dbo.GetTotalUniversityAllocatedToStudents(@UniversityName, @Year);
SET @RemainingBalance = @TotalAllocated - @TotalSpent
RETURN @RemainingBalance
END