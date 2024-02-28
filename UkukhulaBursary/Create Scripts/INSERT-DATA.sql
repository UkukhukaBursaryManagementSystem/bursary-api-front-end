-- Insert sample data for Department
INSERT INTO Department (Name)
VALUES
('Computer Science'),
('Computer Engineering'),
('Computer Game Design'),
('Software Engineering'),
('Data Science'),
('Artificial Intelligence'),
('Cybersecurity'),
('Information Systems');

GO

-- Insert sample data for University
INSERT INTO University (UniversityName)
VALUES
  ('University of Cape Town'),
  ('University of the Witwatersrand'),
  ('Stellenbosch University'),
  ('University of Pretoria'),
  ('University of Johannesburg'),
  ('Rhodes University'),
  ('University of KwaZulu-Natal'),
  ('University of the Western Cape'),
  ('North-West University'),
  ('University of South Africa');
GO

INSERT INTO Gender ([Identity])
VALUES ('Male'), ('Female'), ('Other')
GO
 
-- Insert sample data for Ethnicity
INSERT INTO Ethnicity (Ethnic)
VALUES ('African'), ('Indian'), ('Colored');
GO
 
-- Insert sample data for UserRole
INSERT INTO UserRole (Role)
VALUES ('Admin'), ('HOD'), ('Student');
GO
 
-- Insert sample data for Contact
INSERT INTO Contact (PhoneNumber, Email)
VALUES
  ('1234567890', 'john.doe@example.com'),
  ('9876543210', 'jane.smith@example.com'),
  ('4567891230', 'michael.johnson@example.com'),
  ('7891234560', 'emily.brown@example.com'),
  ('3216549870', 'william.taylor@example.com'),
  ('6549873210', 'olivia.jones@example.com'),
  ('2345678901', 'daniel.martinez@example.com'),
  ('8901234567', 'sophia.garcia@example.com'),
  ('4321098765', 'james.lopez@example.com'),
  ('6789012345', 'grace.hernandez@example.com'),
  ('8765432109', 'alexander.gonzalez@example.com'),
  ('5432109876', 'charlotte.rodriguez@example.com'),
  ('2109876543', 'benjamin.perez@example.com'),
  ('9876543210', 'mia.williams@example.com'),
  ('6543210987', 'ethan.lee@example.com');

GO


-- Insert sample data for BursaryDetails
INSERT INTO BursaryDetails ([Year], TotalAmount)
VALUES
  (2023, 3000000),
  (2024, 3500000)
GO
-- Insert sample data for Users
INSERT INTO [User] (FirstName, LastName, ContactID, UserRoleID, IsUserActive)
VALUES
  ('John', 'Doe', 1, 1, 1),
  ('Jane', 'Smith', 2, 2, 1),
  ('Michael', 'Johnson', 3, 3, 1),
  ('Emily', 'Brown', 4, 2, 1),
  ('William', 'Taylor', 5, 3, 1),
  ('Olivia', 'Jones', 6, 1, 1),
  ('Daniel', 'Martinez', 7, 3, 1),
  ('Sophia', 'Garcia', 8, 2, 1),
  ('James', 'Lopez', 9, 3, 1),
  ('Grace', 'Hernandez', 10, 1, 1),
  ('Alexander', 'Gonzalez', 11, 2, 1),
  ('Charlotte', 'Rodriguez', 12, 3, 1),
  ('Benjamin', 'Perez', 13, 1, 1),
  ('Mia', 'Williams', 14, 3, 1),
  ('Ethan', 'Lee', 15, 2, 1);
  GO


---------------------Insert into the application status table------------------------
INSERT INTO [dbo].ApplicationStatus ([Status])
VALUES('REVIEW'), ('APPROVED'),
  ('REJECTED');

GO


-- Insert sample data for UniversityApplication
INSERT INTO UniversityApplication (UniversityID, Motivation)
VALUES
  (1, 'Motivation text '),
  (2, 'Motivation text '),
  (3, 'Motivation text '),
   (4, 'Motivation text '),
  (5, 'Motivation text '),
   (6, 'Motivation text '),
  (7, 'Motivation text '),
   (8, 'Motivation text '),
  (9, 'Motivation text '),
  (10, 'Motivation text ')

GO



-- Insert sample data for UniversityAllocation
INSERT INTO UniversityAllocation (UniversityID, Amount, BursaryDetailsID)
VALUES
  (1, 500000, 1),
  (2, 750000, 2);

GO

-- Insert sample data for Student
INSERT INTO Student (UserID, IDNumber, GenderID, EthnicityID, UniversityID, DepartmentID, CourseOfStudy)
VALUES
  (1, '9610259654785', 1, 1, 1, 1,'Computer Science'),
  (2, '9710259654785', 2, 2, 2, 2,'Computer Engineering'),
  (3, '9410259654785', 1, 3, 3, 3,'Computer Game Design'),
  (4, '0010259654785', 2, 1, 4, 4,'Software Engineering'),
  (5, '0110259654785', 1, 2, 1, 2,'Computer Engineering'),
  (6, '0310259654785', 2, 3, 2, 6,'Artificial Intelligence'),
  (7, '0110259654785', 1, 1, 3, 7,'Cybersecurity'),
  (8, '9410259654785', 2, 2, 4, 5,'Data Science'),
  (9, '9610259654785', 1, 3, 1, 5,'Data Science');
go 
-- Insert sample data for StudentApplication
INSERT INTO StudentApplication (StudentID, Motivation, BursaryAmount,FundingYear,HeadOfDepartmentID )
VALUES
  (1, 'Motivation text for student 1', 50000,2024, 1),
  (2, 'Motivation text for student 2', 50000,2024, 2),
  (3, 'Motivation text for student 1', 50000,2024, 3),
  (4, 'Motivation text for student 1', 50000,2024, 4),
  (5, 'Motivation text for student 1', 50000,2024, 1),
  (6, 'Motivation text for student 2', 50000,2024, 2),
  (7, 'Motivation text for student 1', 50000,2024, 3),
  (8, 'Motivation text for student 1', 50000,2024, 4),
  (9, 'Motivation text for student 1', 50000,2024,1);
  
 
GO 
-- Insert sample data for BBDAdministrator
INSERT INTO BBDAdministrator ([UserID])
VALUES
  (10),
  (11);
GO 
-- Insert sample data for UniversityRepresentative
INSERT INTO UniversityRepresentative (UserID, UniversityID, DepartmentID)
VALUES
  (12, 1, 1),
  (13, 2, 2),
  (14, 3, 3),
  (15, 4, 4);

 GO
