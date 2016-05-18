create table PATIENT(
patientID int  NOT NULL IDENTITY(1,1),
firstName nvarchar(255) NOT NULL,
lastName nvarchar(255) NOT NULL,
dob nvarchar(255), 
street nvarchar(255),
city nvarchar(255),
[state] nvarchar(255),
zip nvarchar(255)

constraint patientID_PK PRIMARY KEY(patientID)


)