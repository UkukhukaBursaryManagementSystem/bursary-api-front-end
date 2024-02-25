
ALTER VIEW vStudentApplications
as
SELECT 
	studentApp.ID AS applicationID,
    userAlias.FirstName,
    userAlias.LastName,
    studentAlias.IDNumber,
    genderAlias.[Identity] AS GenderIdentity,
    ethnicityAlias.[Ethnic] AS Ethnicity,
	contactAlias.PhoneNumber, 
    contactAlias.Email,
    universityAlias.UniversityName,
	department.[Name] as Department,
    studentAlias.CourseOfStudy,
	studentApp.ReviewerComment,
	studentApp.Motivation,
	studentApp.BursaryAmount,
	studentApp.FundingYear,
	studentAppStatus.[Status],
	studentApp.HeadOfDepartmentID,
	HodUser.FirstName +' ' +HodUser.LastName as HODName


FROM 
	StudentApplication as studentApp INNER JOIN Student as studentAlias 
	ON studentApp.StudentID = studentAlias.ID
   INNER JOIN [User] as userAlias
   ON studentAlias.UserID = userAlias.ID
   INNER JOIN Contact AS contactAlias
   ON userAlias.ContactID = contactAlias.ID
   INNER JOIN Gender as genderAlias 
   ON studentAlias.GenderID = genderAlias.ID
   INNER JOIN Ethnicity AS  ethnicityAlias
   ON studentAlias.EthnicityID = ethnicityAlias.ID
   INNER JOIN University AS universityAlias
	ON studentAlias.UniversityID = universityAlias.ID
	INNER JOIN ApplicationStatus AS studentAppStatus
	ON studentApp.StatusID = studentAppStatus.ID
	INNER JOIN Department as department 
	ON studentAlias.DepartmentID = department.ID
	INNER JOIN UniversityRepresentative as hod
	ON hod.ID = studentApp.HeadOfDepartmentID
	INNER JOIN [User] as [HodUser] ON
	HodUser.ID = hod.UserID

GO