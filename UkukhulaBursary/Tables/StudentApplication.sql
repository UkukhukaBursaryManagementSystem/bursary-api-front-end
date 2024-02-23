CREATE TABLE [dbo].[StudentApplication]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [StudentID] INT NOT NULL,
  [Motivation] TEXT NOT NULL,
  [ReviewerComment] TEXT NOT NULL DEFAULT 'N/A',
  [BursaryAmount] MONEY NOT NULL,
  [StatusId] INT NOT NULL DEFAULT 1,
  [Date] DATE NOT NULL DEFAULT GETDATE() ,
  [FundingYear] INT NOT NULL
)
GO


ALTER TABLE [dbo].[StudentApplication]
  ADD CONSTRAINT [FK_StudentApplicationStudentID] FOREIGN KEY([StudentID]) REFERENCES [dbo].[Student]
GO

ALTER TABLE [dbo].[StudentApplication]
ADD CONSTRAINT [FK_StudentApplicationStatusID] FOREIGN KEY(StatusID) REFERENCES [dbo].[ApplicationStatus]
GO
