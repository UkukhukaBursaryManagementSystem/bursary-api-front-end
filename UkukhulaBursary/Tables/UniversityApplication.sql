CREATE TABLE [dbo].[UniversityApplication]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [UniversityID] INT NOT NULL,
  [Motivation] TEXT NOT NULL,
  [StatusID] INT NOT NULL DEFAULT 1,
  [ReviewerComment] TEXT NOT NULL DEFAULT 'N/A',
)
GO

ALTER TABLE [dbo].[UniversityApplication]
ADD CONSTRAINT [FK_UniversityApplicationUniversityID] FOREIGN KEY([UniversityID]) REFERENCES [dbo].[University]
GO

ALTER TABLE [dbo].[UniversityApplication]
ADD CONSTRAINT [FK_UniversityApplicationStatusID] FOREIGN KEY(StatusID) REFERENCES [dbo].[ApplicationStatus]
GO
