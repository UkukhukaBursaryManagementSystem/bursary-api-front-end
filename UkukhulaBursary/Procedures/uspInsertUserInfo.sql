CREATE PROCEDURE [dbo].[upsInsertUserInfo]
    @FirstName VARCHAR(50)
    ,@LastName VARCHAR(50)
    ,@ContactID INT
    ,@UserRoleID INT
    ,@IsUserActive BIT
AS
  INSERT INTO [dbo].[User](
    [FirstName]
    ,[LastName]
    ,[ContactID]
    ,[UserRoleID]
    ,[IsUserActive]
  )
  VALUES(@FirstName
    ,@LastName
    ,@ContactID
    ,@UserRoleID
    ,@IsUserActive)

  RETURN SCOPE_IDENTITY()
GO