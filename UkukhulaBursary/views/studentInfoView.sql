CREATE VIEW StudentInfoView AS
SELECT 
    userAlias.FirstName,
    userAlias.LastName,
    userAlias.IDNumber,
    genderAlias.[Identity] AS GenderIdentity,
    ethnicityAlias.[Ethnic] AS Ethnic,
    universityAlias.ID AS UniversityID,
    universityAlias.UniversityName,
    userAlias.CourseOfStudy,
    contactAlias.PhoneNumber, 
    contactAlias.Email
FROM 
    (SELECT 
        userAlias.FirstName, 
        userAlias.LastName, 
        studentAlias.IDNumber,
        studentAlias.GenderID,
        studentAlias.EthnicityID,
        studentAlias.UniversityID,
        studentAlias.CourseOfStudy,
        userAlias.ContactID
    FROM 
        [dbo].[User] AS userAlias
    JOIN 
        Student AS studentAlias ON studentAlias.UserID = userAlias.ID) AS userAlias
JOIN 
    (SELECT 
        genderAlias.[Identity],
        studentAlias.IDNumber AS StudentIDNumber,
        studentAlias.GenderID AS GenderID
    FROM 
        Gender AS genderAlias
    JOIN 
        Student AS studentAlias ON studentAlias.GenderID = genderAlias.ID) AS genderAlias ON userAlias.GenderID = genderAlias.GenderID
JOIN
    (SELECT 
        ethnicityAlias.ID,
        ethnicityAlias.[Ethnic],
        studentAlias.EthnicityID AS StudentEthnicityID
    FROM 
        Ethnicity AS ethnicityAlias
    JOIN 
        Student AS studentAlias ON studentAlias.EthnicityID = ethnicityAlias.ID) AS ethnicityAlias ON userAlias.EthnicityID = ethnicityAlias.StudentEthnicityID
JOIN
    (SELECT 
        userAlias.ID,
        userAlias.UniversityName,
        studentAlias.UniversityID AS StudentUniversityID
    FROM 
        University AS userAlias
    JOIN 
        Student AS studentAlias ON studentAlias.UniversityID = userAlias.ID) AS universityAlias ON userAlias.UniversityID = universityAlias.StudentUniversityID
JOIN
    (SELECT 
        contactAlias.ID,
        contactAlias.PhoneNumber,
        contactAlias.Email, 
        userAlias.ContactID AS UserContactID
    FROM 
        Contact AS contactAlias
    JOIN 
        [dbo].[User] AS userAlias ON userAlias.ContactID = contactAlias.ID) AS contactAlias ON userAlias.ContactID = contactAlias.UserContactID;
