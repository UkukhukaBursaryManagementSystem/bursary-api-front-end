CREATE PROCEDURE UpdateStudentDetails
    @StudentID INT,
    @FirstName VARCHAR(50),
    @LastName VARCHAR(50),
    @IDNumber CHAR(13),
    @Gender VARCHAR(10),
    @PhoneNumber CHAR(10),
    @Email VARCHAR(100),
    @Ethnicity VARCHAR(20),
    @CourseOfStudy VARCHAR(100),
    @DepartmentName VARCHAR(100),
    @ReviewerComment TEXT,
    @Motivation TEXT,
    @RequestedAmount MONEY,
    @FundingYear INT,
    @ApplicationStatus VARCHAR(20)

AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Update User table
        UPDATE [dbo].[User]
        SET FirstName = @FirstName,
            LastName = @LastName
        WHERE ID = (SELECT UserID FROM [dbo].[Student] WHERE ID = @StudentID);

        -- Update Contact table
        UPDATE [dbo].[Contact]
        SET PhoneNumber = @PhoneNumber,
            Email = @Email
        WHERE ID = (SELECT ContactID FROM [dbo].[User] WHERE ID = (SELECT UserID FROM [dbo].[Student] WHERE ID = @StudentID));

        -- Update Student table
        UPDATE [dbo].[Student]
        SET IDNumber = @IDNumber,
            GenderID = (SELECT ID FROM [dbo].[Gender] WHERE [Identity] = @Gender),
            EthnicityID = (SELECT ID FROM [dbo].[Ethnicity] WHERE Ethnic = @Ethnicity),
            DepartmentID = (SELECT ID FROM [dbo].[Department] WHERE Name = @DepartmentName),
            CourseOfStudy = @CourseOfStudy
        WHERE ID = @StudentID;

        -- Update StudentApplication table
        UPDATE [dbo].[StudentApplication]
        SET ReviewerComment = @ReviewerComment,
            Motivation = @Motivation,
            BursaryAmount = @RequestedAmount,
            FundingYear = @FundingYear,
            StatusId = (SELECT ID FROM [dbo].[ApplicationStatus] WHERE Status = @ApplicationStatus)
        WHERE StudentID = @StudentID;

        COMMIT;
    END TRY
    BEGIN CATCH
    
        ROLLBACK;
        THROW;
    END CATCH;
END;