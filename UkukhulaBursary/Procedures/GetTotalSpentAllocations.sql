/****** Object:  StoredProcedure [dbo].[uspGetTotalSpentPerYear]    Script Date: 2024/02/24 11:16:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[uspGetTotalSpentPerYear]
@BudgetID INT
AS
SELECT SUM(Amount)
	From UniversityAllocation
	WHERE BursaryDetailsID = @BudgetID;