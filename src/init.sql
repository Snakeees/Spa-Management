-- Drop the tables if they exist
DROP TABLE IF EXISTS TherapistAttendance;
DROP TABLE IF EXISTS Appointment;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Therapist;
DROP TABLE IF EXISTS UserLogin;

-- Create the UserLogin table
CREATE TABLE UserLogin (
    ID int PRIMARY KEY,
    LoginName VARCHAR(255),
    Password VARCHAR(255),
    IsAdmin boolean,
    IsActive boolean
);

-- Create the Service table
CREATE TABLE Service (
    ID int PRIMARY KEY,
    ServiceName VARCHAR(255),
    Duration int,
    Cost int,
    IsActive boolean,
    ServiceLastDate date
);

-- Create the Therapist table
CREATE TABLE Therapist (
    ID int PRIMARY KEY,
    FirstName VARCHAR(255),
    PhoneNumber VARCHAR(255),
    Address VARCHAR(255),
    IsActive boolean,
    ResignationDate date
);

-- Create the Client table
CREATE TABLE Client (
    ID int PRIMARY KEY,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    PhoneNumber VARCHAR(255),
    Address VARCHAR(255)
);

-- Create the Appointment table
CREATE TABLE Appointment (
    ID int PRIMARY KEY,
    ClientID int,
    AppointmentDate date,
    AppointmentTime time,
    TherapistID int,
    ServiceID int,
    IsDone boolean,
    IsPaid boolean,
    FOREIGN KEY (ClientID) REFERENCES Client(ID),
    FOREIGN KEY (TherapistID) REFERENCES Therapist(ID),
    FOREIGN KEY (ServiceID) REFERENCES Service(ID)
);

-- Create the TherapistAttendance table
CREATE TABLE TherapistAttendance (
    ID int PRIMARY KEY,
    TherapistID int,
    Date date,
    CheckinTime time,
    CheckoutTime time,
    FOREIGN KEY (TherapistID) REFERENCES Therapist(ID)
);
-- Insert an admin entry into the UserLogin table
INSERT INTO UserLogin (ID, LoginName, Password, IsAdmin, IsActive) VALUES
(1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', TRUE, TRUE),
(2, 'nonadmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', FALSE, TRUE);

INSERT INTO Client (ID, FirstName, LastName, PhoneNumber, Address) VALUES
(101, 'Alice', 'Brown', '555-1234', '123 Elm Street'),
(102, 'Bob', 'Davis', '555-5678', '456 Oak Street'),
(103, 'Carol', 'Evans', '555-9012', '789 Pine Street');

INSERT INTO Service (ID, ServiceName, Duration, Cost, IsActive, ServiceLastDate) VALUES
(201, 'Massage Therapy', 60, 100, TRUE, '2023-12-31'),
(202, 'Facial Treatment', 30, 50, TRUE, '2023-12-31'),
(203, 'Aromatherapy', 45, 75, TRUE, '2023-12-31');

INSERT INTO Therapist (ID, FirstName, PhoneNumber, Address, IsActive, ResignationDate) VALUES
(1, 'John Doe', '123-456-7890', '1234 Maple St', TRUE, NULL),
(2, 'Jane Smith', '234-567-8901', '5678 Oak Ave', TRUE, NULL),
(3, 'Emily Johnson', '345-678-9012', '9101 Pine Blvd', FALSE, '2023-12-31');

INSERT INTO Appointment (ID, ClientID, AppointmentDate, AppointmentTime, TherapistID, ServiceID, IsDone, IsPaid) VALUES
(1, 101, '2023-11-15', '09:00:00', 1, 201, TRUE, TRUE),
(2, 102, '2023-11-16', '10:30:00', 2, 202, FALSE, FALSE),
(3, 103, '2023-11-17', '14:00:00', 1, 203, FALSE, TRUE);
