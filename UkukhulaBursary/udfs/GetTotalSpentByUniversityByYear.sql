/****** Object:  UserDefinedFunction [dbo].[udfGetTotalSpentByUniversityByYear]    Script Date: 2024/02/24 11:18:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER FUNCTION [dbo].[udfGetTotalSpentByUniversityByYear](
    @targetYear INT,
    @universityID INT
)
RETURNS MONEY
AS
BEGIN
    DECLARE @totalAmount MONEY

    -- Calculate the total spent for the specified year and university
    SELECT @totalAmount = COALESCE(SUM(StudentAllocationAlias.Amount), 0)
    FROM StudentAllocation AS StudentAllocationAlias 
    JOIN Student AS StudentAlias ON StudentAllocationAlias.StudentID = StudentAlias.ID
    JOIN StudentApplication AS StudentApplicationAlias  ON  StudentAlias.ID = StudentApplicationAlias.StudentID
    WHERE StudentAllocationAlias.[Year] = @targetYear
      AND StudentAlias.UniversityID = @universityID
      AND StudentApplicationAlias.Status = 'APPROVED'

    RETURN @totalAmount
END;

