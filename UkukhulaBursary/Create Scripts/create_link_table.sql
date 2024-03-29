CREATE TABLE [dbo].[documentLink]
(
	[ID] INT IDENTITY(1,1) PRIMARY KEY NOT NULL ,
	[ApplicationID] INT UNIQUE NOT NULL,
	[LinkUUID] VARCHAR(MAX)  NOT NULL,
	[Link]  VARCHAR(MAX) NOT NULL,
	[ExpiryDate] DATETIME  NOT NULL DEFAULT DATEADD(DAY, 7, GETDATE()),
)
GO
