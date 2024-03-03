CREATE PROCEDURE [dbo].[uspReviewUniversityApplication]
@ApplicationID INT,
@NewStatus INT,
@ReviewComment TEXT
AS	
	BEGIN TRY
		BEGIN TRANSACTION
			UPDATE  [dbo].[UniversityApplication] 
			SET [StatusID] = @NewStatus,[ReviewerComment] = @ReviewComment
			WHERE [ID] = @ApplicationID
		COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
		ROLLBACK TRANSACTION;
		THROW;
	END CATCH
	