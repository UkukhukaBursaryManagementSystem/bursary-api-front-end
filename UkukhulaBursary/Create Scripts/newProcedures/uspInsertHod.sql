/****** Object:  StoredProcedure [dbo].[uspInsertStudent]    Script Date: 2024/02/28 13:13:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[uspInsertHOD]
  @FirstName VARCHAR(50),
  @LastName VARCHAR(50),
  @PhoneNumber CHAR(10),
  @Email VARCHAR(100),
  @DepartmentID INT,
  @UniversityID INT

AS

      DECLARE @ContactID INT
      DECLARE @UserID INT
      
      EXEC @ContactID =   [dbo].[uspInsertContactInfo] @PhoneNumber,@Email

      EXEC @UserID = [dbo].[upsInsertUserInfo] @FirstName,@LastName,@ContactID,2,1

      INSERT INTO [dbo].[UniversityRepresentative](
        [DepartmentID]
        ,[UniversityID]
        ,[UserID]
      )VALUES (
        @DepartmentID,
        @UniversityID,
        @UserID
      )
GO


