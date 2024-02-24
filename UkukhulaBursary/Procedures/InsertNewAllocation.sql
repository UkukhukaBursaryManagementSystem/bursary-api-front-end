/****** Object:  StoredProcedure [dbo].[InsertNewAllocation]    Script Date: 2024/02/24 11:14:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[InsertNewAllocation] 
    @universityID int, 
    @amount money
AS 
BEGIN 
    DECLARE @detailsID int 
    SELECT @detailsID = ID FROM BursaryDetails WHERE [Year] = YEAR(GETDATE());
    
    INSERT INTO UniversityAllocation (UniversityID, BursaryDetailsID, Amount)
    VALUES (@universityID, @detailsID, @amount);
END
