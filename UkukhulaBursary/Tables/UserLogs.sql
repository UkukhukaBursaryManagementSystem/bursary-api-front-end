USE bbdbursary;

CREATE TABLE UserLogs (
    [LogID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [UserId] INT NOT NULL,
	[UserName] VARCHAR(255) NOT NULL,
    [MsToken] VARCHAR(2000) NOT NULL,
    [UkukhulaToken] VARCHAR(2000) NOT NULL
);