
 CREATE PROCEDURE GetTotalUniversityAllocations
    @GivenYear INT
AS
BEGIN

    DECLARE @TotalSpent MONEY;

    SELECT @TotalSpent = SUM(Amount)
    FROM UniversityAllocationsView
    WHERE Year = @GivenYear;

    SELECT @TotalSpent AS TotalSpentOnUniversityAllocations;
END;