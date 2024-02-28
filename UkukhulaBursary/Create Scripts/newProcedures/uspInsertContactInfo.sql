CREATE PROCEDURE [dbo].[uspInsertContactInfo]
  @PhoneNumber CHAR(10)
  ,@Email VARCHAR(100)
AS
  INSERT INTO [dbo].[Contact](
    [PhoneNumber]
    ,[Email]
  )
  VALUES(
    @PhoneNumber,
    @Email
  )

  RETURN SCOPE_IDENTITY()
  
GO