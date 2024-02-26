CREATE PROCEDURE [dbo].[UpdateStudentApplicationDTO] (
    @StudentApplicationID INT,
    @FirstName VARCHAR(100),
    @LastName VARCHAR(100),
    @IDNumber VARCHAR(13),
    @PhoneNumber VARCHAR(100),
    @Email VARCHAR(100),
    @CourseOfStudy VARCHAR(100),
    @ReviewerComment TEXT,
    @Motivation TEXT,
  @BursaryAmount MONEY,
  @FundingYear INT,
   @Date DATE,
	@UniversityName VARCHAR(100),
	@Department VARCHAR(100),
   @HeadOfDepartmentID INT,
   @UserID INT,
    @GenderID INT, 
    @EthnicityID INT,
   @ContactID INT,
    @UniversityID INT,
    @DepartmentID INT,
		@StudentID INT,
   @ApplicationStatusID INT
)
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        -- Update User
        UPDATE [dbo].[User]
        SET FirstName = @FirstName, LastName = @LastName,ContactID =@ContactID
        WHERE ID = @UserID;

        UPDATE [dbo].[Contact]
        SET PhoneNumber = @PhoneNumber, Email = @Email
        WHERE ID = @ContactID;

		-- Update University
        UPDATE [dbo].[University]
        SET UniversityName = @UniversityName
        WHERE ID = @UniversityID;

		-- Update Department
        UPDATE [dbo].[Department]
        SET Name = @Department
        WHERE ID = @DepartmentID;

        -- Update Student
        UPDATE [dbo].[Student]
        SET CourseOfStudy = @CourseOfStudy,
            GenderID = @GenderID,
            EthnicityID = @EthnicityID,
            DepartmentID = @DepartmentID,
            UniversityID = @UniversityID,
            IDNumber = @IDNumber
        WHERE ID = @StudentID;

		
		UPDATE [dbo].[StudentApplication]
		SET Motivation = @Motivation, ReviewerComment = @ReviewerComment, BursaryAmount = @BursaryAmount,
			[Date] = @Date, FundingYear = @FundingYear,StudentID = @StudentID,StatusId = @ApplicationStatusID
		WHERE ID = @StudentApplicationID

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
        THROW;
    END CATCH;
END;
