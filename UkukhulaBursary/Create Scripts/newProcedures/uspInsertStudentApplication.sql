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
	THROW;
END CATCH
GO



exec [dbo].[uspCreateStudentWithApplication] 'Bulelani', 'Gabonewe', '9901935984086','0748158252','bulelani@gmail.com','BSc Enginnering', 1,1,200000,1,'I love this guys.Always makes me laugh during the lecture',80000,1,2024;
GO



exec [dbo].[uspCreateStudentWithApplication] 'Johnnyy', 'Doeyy', '123456789101','1234567891','john.doe@example.com','Computer Science', 1,1,3,3,'I love this guys.Always makes me laugh during the lecture',1000,3,2024;
GO


{
    "FirstName": "Johnnyy",
    "LastName": "Doeyy",
    "IDNumber": "123456789101",
    "PhoneNumber": "1234567891",
    "Email": "john.doe@example.com",
    "CourseOfStudy": "Computer Science",
    "GenderID": 1,
    "EthnicityID": 1,
    "DepartmentID": 3,
    "UniversityID": 3,
    "ApplicationMotivation": "I like this guy",
    "BursaryAmount": 1000.00,
    "HeadOfDepartmentID": 3,
    "FundingYear": 2024
}