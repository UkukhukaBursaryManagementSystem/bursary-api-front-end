CREATE OR ALTER PROCEDURE UpdateStudentDetails
    @ApplicationID INT,
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
    DECLARE @StudentID INT;

    SELECT @StudentID = StudentID FROM [dbo].[StudentApplication] WHERE ID = @ApplicationID;


    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE [dbo].[User]
        SET FirstName = @FirstName,
            LastName = @LastName
        WHERE ID = (SELECT UserID FROM [dbo].[Student] WHERE ID = @StudentID);

        UPDATE [dbo].[Contact]
        SET PhoneNumber = @PhoneNumber,
            Email = @Email
        WHERE ID = (SELECT ContactID FROM [dbo].[User] WHERE ID = (SELECT UserID FROM [dbo].[Student] WHERE ID = @StudentID));


        UPDATE [dbo].[Student]
        SET IDNumber = @IDNumber,
            GenderID = (SELECT ID FROM [dbo].[Gender] WHERE [Identity] = @Gender),
            EthnicityID = (SELECT ID FROM [dbo].[Ethnicity] WHERE Ethnic = @Ethnicity),
            DepartmentID = (SELECT ID FROM [dbo].[Department] WHERE Name = @DepartmentName),
            CourseOfStudy = @CourseOfStudy
        WHERE ID = @StudentID;


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