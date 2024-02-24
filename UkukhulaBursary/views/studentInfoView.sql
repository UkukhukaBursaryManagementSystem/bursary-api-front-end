SELECT
userAlias.FirstName,
userAlias.LastName,
studentAlias.IDNumber,
genderAlias.[Identity] AS GenderIdentity,
ethnicityAlias.[Ethnic] AS Ethnic,
universityAlias.ID AS UniversityID,
universityAlias.UniversityName,
studentAlias.CourseOfStudy,
contactAlias.PhoneNumber,
contactAlias.Email
FROM
Student as studentAlias INNER JOIN [User] as userAlias
ON studentAlias.UserID = userAlias.ID
INNER JOIN Contact AS contactAlias
ON userAlias.ContactID = contactAlias.ID
INNER JOIN Gender as genderAlias
ON studentAlias.GenderID = genderAlias.ID
INNER JOIN Ethnicity AS ethnicityAlias
ON studentAlias.EthnicityID = ethnicityAlias.ID
INNER JOIN University AS universityAlias
ON studentAlias.UniversityID = universityAlias.ID