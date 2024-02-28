CREATE PROCEDURE [dbo].[uspAddStudentDocuments]
@StudentID INT,
@IdCopy VARCHAR(MAX),
@AcademicTranscript VARCHAR(MAX),
@CurriculumVitae VARCHAR(MAX)

AS 
BEGIN TRY
	BEGIN TRANSACTION
		IF NOT EXISTS ( SELECT 1 FROM [dbo].[StudentDocuments] WHERE [dbo].[StudentDocuments].[StudentID] = @StudentID )
			BEGIN
				INSERT INTO [dbo].[StudentDocuments] (StudentID, IdCopy, AcademicTranscript, CurriculumVitae)
				VALUES (@StudentID, 
						@IdCopy,
						@AcademicTranscript,
						@CurriculumVitae)
			END
		ELSE
			BEGIN
				UPDATE  [dbo].[StudentDocuments] 
				SET [IdCopy] = @IdCopy,[CurriculumVitae] = @CurriculumVitae ,[AcademicTranscript] = @AcademicTranscript
				WHERE [StudentID] = @StudentID
			END
			
	COMMIT
END TRY
BEGIN CATCH
	ROLLBACK TRANSACTION;
	THROW;
END CATCH
GO


EXEC [dbo].[uspAddStudentDocuments] 1, 'my id','my trans','my cv'
go
