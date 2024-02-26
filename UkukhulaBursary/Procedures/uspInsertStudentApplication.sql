ALTER PROCEDURE [dbo].[uspCreateStudentWithApplication]
  @FirstName VARCHAR(50),
  @LastName VARCHAR(50),
  @IDNumber CHAR(13),
  @PhoneNumber CHAR(10),
  @Email VARCHAR(100),
  @CourseOfStudy VARCHAR(100),
  @GenderID INT,
  @EthnicityID INT,
  @DepartmentID INT,
  @UniversityID INT,
  @ApplicationMotivation TEXT,
  @BursaryAmount MONEY,
  @HeadOfDepartmentID INT,
  @FundingYear INT
AS
BEGIN TRY
  BEGIN TRANSACTION
   DECLARE @StudentID INT
    EXEC @StudentID  = [dbo].[uspInsertStudent] @FirstName ,@LastName ,@IDNumber ,@PhoneNumber,@Email,@CourseOfStudy ,@GenderID,3,@EthnicityID,@DepartmentID,@UniversityID
 

    INSERT INTO [dbo].[StudentApplication] (
      [Motivation],
      [StudentID],
      [BursaryAmount],
	  [HeadOfDepartmentID],
	  [FundingYear]
    ) VALUES (
      @ApplicationMotivation,
      @StudentID,
      @BursaryAmount,
	  @HeadOfDepartmentID,
	  @FundingYear
     
    )
  COMMIT
END TRY
BEGIN CATCH
  IF (@@TRANCOUNT > 0)
    ROLLBACK TRANSACTION;
  exec [dbo].[uspGetErrorInfo]
  PRINT 'Transaction failed! ' + error_message() 
END CATCH
GO



exec [dbo].[uspCreateStudentWithApplication] 'Bulelani', 'Gabonewe', '9901935984086','0748158252','bulelani@gmail.com','BSc Enginnering', 1,1,2,1,'I love this guys.Always makes me laugh during the lecture',80000,1,2024;
GO