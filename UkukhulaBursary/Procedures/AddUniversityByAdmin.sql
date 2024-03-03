CREATE OR ALTER PROCEDURE [dbo].[CreateAdminUniversityApplication] @UniversityName VARCHAR(100)
AS
BEGIN
DECLARE @UniversityId INT
DECLARE @StatusId int
INSERT INTO University (UniversityName) VALUES (@UniversityName)
SELECT @UniversityId = ID FROM University WHERE UniversityName = @UniversityName
SELECT @StatusId = ID FROM ApplicationStatus WHERE [Status] = 'Approved' OR [Status] = 'Accepted'
INSERT INTO UniversityApplication (UniversityID,Motivation,StatusID,ReviewerComment)
VALUES (@UniversityId , 'Added by admin', @StatusId,'Added by Admin')
END