CREATE OR ALTER VIEW [dbo].[StudentAllocationView]
AS
SELECT StudentAllocation.StudentID,
       University.UniversityName,
       StudentAllocation.Amount,
       StudentAllocation.Year
FROM [dbo].[StudentAllocation] StudentAllocation
INNER JOIN [dbo].[Student] AS Student ON StudentAllocation.StudentID = Student.ID
INNER JOIN [dbo].[University] AS University ON Student.UniversityID = University.ID;