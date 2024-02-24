/****** Object:  StoredProcedure [dbo].[AllocateFundsToUniversity]    Script Date: 2024/02/24 11:13:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[AllocateFundsToUniversity]
    @UniversityID INT,
    @Amount MONEY
AS
BEGIN
    BEGIN TRY
        DECLARE @AdminBalance MONEY

        SELECT @AdminBalance = TotalAmount
        FROM BursaryDetails
        WHERE [Year] = YEAR(GETDATE());

        IF @AdminBalance < @Amount
        BEGIN
       
            DECLARE @ErrorMessage NVARCHAR(4000) = 'Insufficient funds in the admin balance.';
            RAISERROR(@ErrorMessage, 16, 1);
            RETURN;
        END
		DECLARE @oldAmount money
		SELECT @oldAmount = Amount FROM UniversityAllocation WHERE ID=@UniversityID
        UPDATE UniversityAllocation
        SET Amount = @oldAmount + @Amount
        WHERE ID = @UniversityID;
    
    END TRY
    BEGIN CATCH
   
        DECLARE @ErrorSeverity INT;
        DECLARE @ErrorState INT;

        SELECT 
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE();

        IF @@TRANCOUNT > 0
            ROLLBACK;

     
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH;
END

