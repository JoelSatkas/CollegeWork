CREATE TABLE Citizens
(
PersonID int NOT NULL AUTO_INCREMENT,
LastName varchar(255) NOT NULL,
FirstName varchar(255) NOT NULL,
Salary int NOT NULL,
PRIMARY KEY (PersonID)
);

CREATE TABLE Taxation
(
ID int NOT NULL AUTO_INCREMENT,
ExemptBand double NOT NULL,
AfterBand double NOT NULL,
AfterBandPer double NOT NULL,
RemainderPer double NOT NULL,
TaxCharge double NOT NULL,
PRIMARY KEY (ID)
);