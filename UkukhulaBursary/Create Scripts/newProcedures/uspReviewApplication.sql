ALTER PROCEDURE [dbo].[uspReviewStudentApplication]
@ApplicationID INT,
@NewStatus INT,
@ReviewComment TEXT
AS	
	BEGIN TRY
		BEGIN TRANSACTION
			DECLARE @Amount MONEY;
			DECLARE @UniversityID INT;
			DECLARE @ApplicationYear INT;

			DECLARE @StudentID INT;

			SELECT @Amount = [dbo].[StudentApplication].[BursaryAmount], @ApplicationYear = [dbo].[StudentApplication].[FundingYear], @UniversityID = [dbo].[Student].[UniversityID] ,
					@StudentID = [dbo].[StudentApplication].[StudentID]
			FROM [dbo].[StudentApplication] 
			INNER JOIN [dbo].[Student] 
			ON [dbo].[Student].[ID] = [dbo].[StudentApplication].[StudentID] 
			WHERE [dbo].[StudentApplication].[ID] = @ApplicationID;


 			IF (@Amount >=  [dbo].[udfGetUniversityRemainingAmount](@UniversityID,@ApplicationYear))
				BEGIN
					UPDATE  [dbo].[StudentApplication] 
					SET [StatusID] = @NewStatus,[ReviewerComment] = @ReviewComment
					WHERE [ID] = @ApplicationID;

					INSERT INTO [dbo].[StudentAllocation]([StudentID],
					[Amount],
					[Year])
					VALUES(@StudentID,
							@Amount,
							@ApplicationYear);
				END
			ELSE 
				BEGIN
					;THROW 51000, 'Insufficient university funds to support the student for the Requestet amount', 1; 
				END;
		COMMIT TRANSACTION
	END TRY

	BEGIN CATCH
	  IF (@@TRANCOUNT > 0)
		ROLLBACK TRANSACTION;
		THROW;
	END CATCH
GO

EXEC [dbo].[uspReviewStudentApplication] 21, 2, 'Trying out the approve and reject student application'
go

