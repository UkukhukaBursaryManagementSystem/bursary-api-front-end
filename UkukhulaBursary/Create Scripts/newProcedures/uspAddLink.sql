CREATE PROCEDURE [dbo].[uspInsertLink]
@ApplicationID INT,
@LinkUUID VARCHAR(MAX),
@Link VARCHAR(MAX)
AS
	BEGIN TRY
		BEGIN TRANSACTION
			IF NOT EXISTS ( SELECT 1 FROM [dbo].[documentLink] WHERE [dbo].[documentLink].[ApplicationID] = @ApplicationID )
				BEGIN
					INSERT INTO [dbo].[documentLink] (ApplicationID, LinkUUID, Link)
					VALUES (@ApplicationID, 
							@LinkUUID,
							@Link)
				END
			ELSE
				BEGIN
					UPDATE  [dbo].[documentLink] 
					SET [LinkUUID] = @LinkUUID,[Link] = @Link ,	[ExpiryDate] = DATEADD(DAY, 7, GETDATE())
					WHERE [ApplicationID] = @ApplicationID
				END

		COMMIT TRANSACTION

	END TRY

	BEGIN CATCH
		IF (@@TRANCOUNT > 0)
			ROLLBACK TRANSACTION;
			THROW;

	END CATCH
GO