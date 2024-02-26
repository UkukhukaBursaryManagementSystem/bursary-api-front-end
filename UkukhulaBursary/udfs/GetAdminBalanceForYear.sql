CREATE FUNCTION dbo.GetAdminBalanceForYear
(
    @Year INT
)
RETURNS MONEY
AS
BEGIN
    DECLARE @AdminBalance MONEY;

    SELECT @AdminBalance = TotalAmount
    FROM dbo.BursaryDetails
    WHERE [Year] = @Year;

    RETURN @AdminBalance;
END;