CREATE PROCEDURE [dbo].[uspInsertAdmin]
  @FirstName VARCHAR(50),
  @LastName VARCHAR(50),
  @PhoneNumber CHAR(10),
  @Email VARCHAR(100)
AS

      DECLARE @ContactID INT
      DECLARE @UserID INT
      
      EXEC @ContactID =   [dbo].[uspInsertContactInfo] @PhoneNumber,@Email

      EXEC @UserID = [dbo].[upsInsertUserInfo] @FirstName,@LastName,@ContactID,1,1

      INSERT INTO [dbo].[BBDAdministrator](
		[UserID]
      )VALUES (
        @UserID
      )
GO


