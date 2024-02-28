ALTER PROCEDURE [dbo].[uspInsertStudent]
  @FirstName VARCHAR(50),
  @LastName VARCHAR(50),
  @IDNumber CHAR(13),
  @PhoneNumber CHAR(10),
  @Email VARCHAR(100),
  @CourseOfStudy VARCHAR(100),
  @GenderID INT,
  @UserRoleID INT,
  @EthnicityID INT,
  @DepartmentID INT,
  @UniversityID INT

AS

      DECLARE @ContactID INT
      DECLARE @UserID INT
      
      EXEC @ContactID =   [dbo].[uspInsertContactInfo] @PhoneNumber,@Email

      EXEC @UserID = [dbo].[upsInsertUserInfo] @FirstName,@LastName,@ContactID,@UserRoleID,0

      INSERT INTO [dbo].[Student](
        [DepartmentID]
        ,[UniversityID]
        ,[UserID]
		,[IDNumber]
		,[EthnicityID]
		,[CourseOfStudy]
		,[GenderID]
      )VALUES (
        @DepartmentID,
        @UniversityID,
        @UserID,
		@IDNumber,
		@EthnicityID,
		@CourseOfStudy,
		@GenderID
      )
 
	RETURN SCOPE_IDENTITY()
GO

