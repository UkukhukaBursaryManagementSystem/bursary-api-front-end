CREATE TABLE [dbo].[Contact]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [PhoneNumber] CHAR(10) NOT NULL,
  [Email] VARCHAR(100) UNIQUE NOT NULL
);
GO

CREATE TABLE [dbo].[BBDAdministrator]
(
  [ID] INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
  [UserID] INT UNIQUE NOT NULL
)
GO

CREATE TABLE [dbo].[BursaryDetails]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Year] INT NOT NULL,
  [TotalAmount] MONEY NOT NULL,
);
GO

CREATE TABLE [dbo].[Department]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Name] VARCHAR(100) NOT NULL
)
GO

CREATE TABLE [dbo].[Ethnicity]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Ethnic] VARCHAR(20) NOT NULL
)
GO

CREATE TABLE [dbo].[Gender]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Identity] VARCHAR(10) NOT NULL
);
GO

CREATE TABLE [dbo].[ApplicationStatus]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Status] VARCHAR(20) NOT NULL
)
GO

CREATE TABLE [dbo].[Student]
(
  [ID] INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
  [UserID] INT UNIQUE NOT NULL,
  [IDNumber] CHAR(13) UNIQUE NOT NULL,
  [GenderID] INT NOT NULL,
  [EthnicityID] INT NOT NULL,
  [UniversityID] INT NOT NULL,
  [DepartmentID] INT NOT NULL,
  [CourseOfStudy] VARCHAR(100) NOT NULL
)
GO

CREATE TABLE [dbo].[StudentAllocation]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [StudentID] INT NOT NULL,
  [Amount] MONEY NOT NULL,
  [Year] INT NOT NULL DEFAULT YEAR(GETDATE()),
);
GO


CREATE TABLE [dbo].[StudentApplication]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [StudentID] INT NOT NULL,
  [Motivation] TEXT NOT NULL,
  [ReviewerComment] TEXT NOT NULL DEFAULT 'N/A',
  [BursaryAmount] MONEY NOT NULL,
  [StatusId] INT NOT NULL DEFAULT 1,
  [Date] DATE NOT NULL DEFAULT GETDATE() ,
  [FundingYear] INT NOT NULL,
  [HeadOfDepartmentID] INT NOT NULL
)
GO


CREATE TABLE [dbo].[StudentDocuments]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [StudentID] INT UNIQUE NOT NULL,
  [IdCopy] VARCHAR(MAX) NOT NULL,
  [AcademicTranscript] VARCHAR(MAX) NOT NULL,
  [CurriculumVitae] VARCHAR(MAX) NOT NULL,
)
GO


CREATE TABLE [dbo].[University]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [UniversityName] VARCHAR(100) NOT NULL
);


CREATE TABLE [dbo].[UniversityAllocation]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [UniversityID] INT NOT NULL,
  [Amount] MONEY NOT NULL,
  [BursaryDetailsID] INT
);
GO

CREATE TABLE [dbo].[UniversityApplication]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [UniversityID] INT NOT NULL,
  [Motivation] TEXT NOT NULL,
  [StatusID] INT NOT NULL DEFAULT 1,
  [ReviewerComment] TEXT NOT NULL DEFAULT 'N/A',
)
GO

CREATE TABLE [dbo].[UniversityRepresentative]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [UserID] INT UNIQUE NOT NULL,
  [UniversityID] INT NOT NULL,
  [DepartmentID] INT NOT NULL,
)
GO

CREATE TABLE [dbo].[User]
(
  [ID] INT IDENTITY(1,1) PRIMARY KEY NOT NULL ,
  [FirstName] VARCHAR(50) NOT NULL,
  [LastName] VARCHAR(50) NOT NULL,
  [ContactID] INT NOT NULL,
  [UserRoleID] INT NOT NULL,
  [IsUserActive] BIT NOT NULL DEFAULT 1
)
GO

CREATE TABLE [dbo].[UserRole]
(
  [ID] INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  [Role] VARCHAR(20) NOT NULL
)
GO

CREATE TABLE [dbo].[documentLink]
(
	[ID] INT IDENTITY(1,1) PRIMARY KEY NOT NULL ,
	[ApplicationID] INT UNIQUE NOT NULL,
	[LinkUUID] VARCHAR(MAX)  NOT NULL,
	[Link]  VARCHAR(MAX) NOT NULL,
	[ExpiryDate] DATETIME  NOT NULL DEFAULT DATEADD(DAY, 7, GETDATE()),
)
GO


---------------Add the constraints-----------------------------
ALTER TABLE [dbo].[documentLink]
ADD CONSTRAINT [FK_Application_link] FOREIGN KEY ([ApplicationID]) REFERENCES [dbo].[StudentApplication](ID);
GO


ALTER TABLE [dbo].[BBDAdministrator]
ADD CONSTRAINT [FK_BBDAdministratorUserID] FOREIGN KEY([UserID]) REFERENCES [dbo].[User]
GO

ALTER TABLE [dbo].[Student]
ADD CONSTRAINT [FK_StudentUserID] FOREIGN KEY ([UserID]) REFERENCES [dbo].[User]
GO

ALTER TABLE [dbo].[Student]
ADD CONSTRAINT [FK_StudentGender] FOREIGN KEY ([GenderID]) REFERENCES [dbo].[Gender]
GO

ALTER TABLE [dbo].[Student]
ADD CONSTRAINT [FK_StudentEthnicity] FOREIGN KEY ([EthnicityID]) REFERENCES [dbo].[Ethnicity]
GO

ALTER TABLE [dbo].[Student]
ADD CONSTRAINT [FK_StudentUniversity] FOREIGN KEY ([UniversityID]) REFERENCES [dbo].[University]
GO

ALTER TABLE [dbo].[Student]
ADD CONSTRAINT [FK_StudentDepartment] FOREIGN KEY ([DepartmentID]) REFERENCES [dbo].[Department]
GO


ALTER TABLE [dbo].[StudentAllocation]
  ADD CONSTRAINT [FK_StudentAllocationStudentID]
      FOREIGN KEY ([StudentID])
      REFERENCES [dbo].[Student]
GO

ALTER TABLE [dbo].[StudentApplication]
  ADD CONSTRAINT [FK_StudentApplicationStudentID] FOREIGN KEY([StudentID]) REFERENCES [dbo].[Student]
GO

ALTER TABLE [dbo].[StudentApplication]
ADD CONSTRAINT [FK_StudentApplicationStatusID] FOREIGN KEY(StatusID) REFERENCES [dbo].[ApplicationStatus]
GO


ALTER TABLE [dbo].[StudentApplication]
ADD CONSTRAINT [FK_HeadOfDepartmentID] FOREIGN KEY(HeadOfDepartmentID) REFERENCES [dbo].[UniversityRepresentative](ID)
GO

ALTER TABLE [dbo].[StudentDocuments]
  ADD CONSTRAINT [FK_StudentDocumentsApplication]
      FOREIGN KEY ([StudentID])
      REFERENCES [dbo].[Student]
GO

ALTER TABLE [dbo].[UniversityAllocation]
  ADD CONSTRAINT [FK_UniversityAllocationUniversityID]
      FOREIGN KEY ([UniversityID])
      REFERENCES [dbo].[University]
GO

ALTER TABLE [dbo].[UniversityAllocation]
   ADD CONSTRAINT [FK_UniversityAllocationBursaryDetailsID]
        FOREIGN KEY ([BursaryDetailsID])
      REFERENCES [dbo].[BursaryDetails]
GO

ALTER TABLE [dbo].[UniversityApplication]
ADD CONSTRAINT [FK_UniversityApplicationUniversityID] FOREIGN KEY([UniversityID]) REFERENCES [dbo].[University]
GO

ALTER TABLE [dbo].[UniversityApplication]
ADD CONSTRAINT [FK_UniversityApplicationStatusID] FOREIGN KEY(StatusID) REFERENCES [dbo].[ApplicationStatus]
GO

ALTER TABLE [dbo].[UniversityRepresentative]
    ADD  CONSTRAINT [FK_UniversityRepresentativeUser]
      FOREIGN KEY ([UserID])
      REFERENCES [dbo].[User]
GO

ALTER TABLE [dbo].[UniversityRepresentative]
  ADD CONSTRAINT [FK_UniversityRepresentativeDepartment] 
      FOREIGN KEY ([DepartmentID]) 
      REFERENCES [dbo].[Department]
GO

ALTER TABLE [dbo].[UniversityRepresentative]
    ADD CONSTRAINT [FK_UniversityRepresentativeUniversity]
      FOREIGN KEY ([UniversityID])
      REFERENCES [dbo].[University]
GO

ALTER TABLE [dbo].[User]
ADD CONSTRAINT FK_UserRole FOREIGN KEY([UserRoleID]) REFERENCES [dbo].[UserRole]
GO

ALTER TABLE [dbo].[User]
ADD CONSTRAINT FK_Contact FOREIGN KEY([ContactID]) REFERENCES [dbo].[Contact]
GO


