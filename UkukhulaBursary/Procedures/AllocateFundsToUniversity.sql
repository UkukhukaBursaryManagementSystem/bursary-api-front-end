CREATE PROCEDURE InsertUniversityAllocation
  @UniversityName VARCHAR(100),
  @Amount MONEY,
  @Year INT
AS
BEGIN
  SET NOCOUNT ON;

  DECLARE @UniversityID INT
  DECLARE @BursaryDetailsID INT


  SELECT @UniversityID = ID
  FROM University
  WHERE UniversityName = @UniversityName;

 
  SELECT @BursaryDetailsID = ID
  FROM BursaryDetails
  WHERE [Year] = @Year;


  INSERT INTO UniversityAllocation (UniversityID, Amount, BursaryDetailsID)
  VALUES (@UniversityID, @Amount, @BursaryDetailsID);
  
END
GO