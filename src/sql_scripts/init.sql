-- Drop the tables if they exist
DROP TABLE IF EXISTS TherapistAttendance;
DROP TABLE IF EXISTS Appointment;
DROP TABLE IF EXISTS Client;
DROP TABLE IF EXISTS Service;
DROP TABLE IF EXISTS Therapist;
DROP TABLE IF EXISTS UserLogin;

-- Create the UserLogin table
CREATE TABLE UserLogin(
    ID int AUTO_INCREMENT PRIMARY KEY,
    LoginName VARCHAR(255),
    Password VARCHAR(255),
    IsAdmin boolean
);

-- Create the Service table
CREATE TABLE Service (
    ID int AUTO_INCREMENT PRIMARY KEY,
    ServiceName VARCHAR(255),
    Duration TIME,
    Cost int,
    IsActive boolean,
    ServiceLastDate date
);

-- Create the Therapist table
CREATE TABLE Therapist (
    ID int AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(255),
    PhoneNumber VARCHAR(255),
    Address VARCHAR(255),
    IsActive boolean,
    ResignationDate date
);

-- Create the Appointment table
CREATE TABLE Appointment (
    ID int AUTO_INCREMENT PRIMARY KEY,
    ClientName VARCHAR(255),
    ClientPhoneNumber VARCHAR(255),
    AppointmentDate date,
    AppointmentTime time,
    TherapistID int,
    ServiceID int,
    FOREIGN KEY (TherapistID) REFERENCES Therapist(ID),
    FOREIGN KEY (ServiceID) REFERENCES Service(ID)
);

-- Create the TherapistAttendance table
CREATE TABLE TherapistAttendance (
    ID int AUTO_INCREMENT PRIMARY KEY,
    TherapistID int,
    Date date,
    CheckinTime time,
    CheckoutTime time,
    FOREIGN KEY (TherapistID) REFERENCES Therapist(ID)
);

INSERT INTO UserLogin (LoginName, Password, IsAdmin) VALUES ('owner', 'e6c3da5b206634d7f3f3586d747ffdb36b5c675757b380c6a5fe5c570c714349', True),
('owner_wife', '1ba3d16e9881959f8c9a9762854f72c6e6321cdd44358a10a4e939033117eab9', True),
('receptionist1', '3acb59306ef6e660cf832d1d34c4fba3d88d616f0bb5c2a9e0f82d18ef6fc167', False),
('receptionist2', 'a417b5dc3d06d15d91c6687e27fc1705ebc56b3b2d813abe03066e5643fe4e74', False),
('deskstaff', '0eeac8171768d0cdef3a20fee6db4362d019c91e10662a6b55186336e1a42778', False);

INSERT INTO Service (ServiceName, Duration, Cost, IsActive, ServiceLastDate) VALUES ('Swedish Massage', '01:00:00', 120, True, '2024-12-31'),
('Deep Tissue Massage', '01:30:00', 150, True, '2024-12-31'),
('Facial Treatment', '00:45:00', 100, True, '2024-12-31'),
('Aromatherapy', '01:00:00', 130, True, '2024-12-31'),
('Hot Stone Therapy', '01:00:00', 140, True, '2024-12-31'),
('Reflexology', '00:30:00', 90, True, '2024-12-31'),
('Detox Wrap', '01:00:00', 110, True, '2024-12-31'),
('Manicure', '00:45:00', 70, True, '2024-12-31'),
('Pedicure', '00:45:00', 80, True, '2024-12-31'),
('Hair Styling', '01:00:00', 100, True, '2024-12-31'),
('Makeup Session', '00:30:00', 90, True, '2024-12-31'),
('Yoga Class', '01:00:00', 50, True, '2024-12-31');

INSERT INTO Therapist (FirstName, PhoneNumber, Address, IsActive, ResignationDate) VALUES ('Emily Johnson', '8713501649', '52581 Johnson Ridges Apt. 250 West Jessica, SC 38202', True, NULL),
('Michael Smith', '6458525109', '395 Laura Row Suite 476 Coltontown, FL 74872', True, NULL),
('Jessica Davis', '8625497371', '5460 Brown Summit Suite 049 Kimberlyfort, AL 38434', False, '2023-10-08'),
('James Wilson', '7048165981', '2138 Rodriguez Valleys Suite 985 Carterborough, PR 71331', True, NULL),
('Jennifer Moore', '7062912812', 'Unit 3682 Box 5940 DPO AA 49836', True, NULL),
('David Brown', '7265377437', '14291 Parker Stravenue Seanville, PA 63389', True, NULL),
('Sarah Jones', '6364121041', '41599 Brian Islands New Tammy, VI 14630', True, NULL),
('John Miller', '6635968605', 'USS Zamora FPO AE 26761', True, '2024-01-21'),
('Ashley Martinez', '9936443707', '5530 Thomas Plains New Andrew, TN 36777', True, NULL),
('Daniel Garcia', '7192991670', '045 Lauren Ports Apt. 726 East Jessica, NJ 43615', True, NULL);

INSERT INTO Appointment (ClientName, ClientPhoneNumber, AppointmentDate, AppointmentTime, TherapistID, ServiceID) VALUES ('Jaclyn Harris', '9450341825', '2023-10-16', '13:15:00', 4, 7),
('Oscar Grant', '6442141037', '2023-10-13', '12:45:00', 7, 12),
('Lisa Conrad', '6769755204', '2024-01-30', '15:30:00', 10, 6),
('Jeffrey Chandler', '7059272458', '2023-11-03', '16:30:00', 10, 3),
('Heather Gomez', '7839378114', '2024-02-03', '13:30:00', 9, 11),
('Michael Duncan', '7505928289', '2024-02-05', '14:45:00', 2, 12),
('Thomas White', '8631073380', '2023-08-18', '17:00:00', 10, 1),
('Evelyn Jacobs', '7483201326', '2023-08-22', '08:15:00', 2, 4),
('Lisa Conrad', '8317026884', '2023-10-02', '18:00:00', 7, 4),
('Erica Miller', '6640039038', '2024-02-29', '09:30:00', 1, 10),
('Justin Hoffman', '9869211404', '2023-11-25', '09:30:00', 8, 9),
('Tracy Perez', '9363175734', '2023-09-25', '17:45:00', 9, 11),
('Linda Bell', '6341826964', '2024-02-01', '18:00:00', 5, 3),
('Thomas White', '9737460498', '2023-09-18', '18:15:00', 6, 10),
('Jessica Greene', '7656523787', '2023-10-20', '11:45:00', 4, 7),
('Brittany Cook', '8500257140', '2023-09-14', '13:45:00', 8, 12),
('Jade Smith', '7398597828', '2024-01-02', '15:00:00', 5, 12),
('Jodi Wilson', '9200600369', '2023-08-14', '18:00:00', 6, 1),
('Jodi Wilson', '7054155873', '2023-10-24', '09:00:00', 1, 1),
('Christopher Kerr', '6130472952', '2024-02-29', '14:30:00', 4, 6),
('Ashley Lewis', '6775643616', '2023-09-17', '14:45:00', 2, 3),
('Donald Patel', '6951905383', '2023-12-22', '10:15:00', 9, 6),
('Linda Harmon', '9134315256', '2023-11-23', '11:45:00', 9, 10),
('Jason Morgan', '9945970843', '2024-01-09', '08:00:00', 5, 10),
('Lauren Conner', '9266262987', '2024-02-21', '09:30:00', 10, 4),
('Andrew Ruiz', '8261744382', '2023-09-10', '16:30:00', 1, 8),
('Samantha Smith', '9007104745', '2023-08-10', '10:45:00', 6, 5),
('Bradley Smith', '6252624902', '2023-12-14', '08:15:00', 7, 12),
('Adriana Collier', '7174483839', '2023-11-14', '14:45:00', 10, 5),
('Jessica Greene', '9158498995', '2023-10-29', '09:45:00', 6, 3),
('Crystal Mason', '9882086095', '2023-10-17', '13:45:00', 2, 12),
('Julia Patel', '8418087065', '2023-08-10', '18:45:00', 4, 12),
('Jaclyn Harris', '8431455232', '2024-01-04', '09:00:00', 5, 3),
('John Hughes', '7843868935', '2023-10-12', '10:45:00', 1, 3),
('Tiffany Shaw', '6063597725', '2023-10-02', '16:30:00', 9, 4),
('Michael Beard', '8700805500', '2024-01-23', '17:15:00', 5, 9),
('Crystal Mason', '7381769795', '2023-11-08', '16:45:00', 3, 8),
('Kyle Eaton', '9296508838', '2023-11-15', '10:00:00', 10, 1),
('Norma Gonzalez', '9104049670', '2023-08-24', '15:15:00', 1, 3),
('Erica Miller', '6345906194', '2023-08-02', '09:30:00', 4, 8),
('Tanya Hayes', '9600874109', '2023-12-04', '11:30:00', 9, 9),
('Stacey Swanson', '8638979714', '2023-09-18', '15:30:00', 6, 9),
('Evelyn Jacobs', '9948158238', '2024-02-25', '13:00:00', 5, 4),
('Norma Gonzalez', '9538288870', '2024-02-05', '14:45:00', 9, 8),
('Ashley Lewis', '9354667424', '2023-08-02', '12:15:00', 8, 10),
('Daniel Johnson', '8759275458', '2023-09-23', '09:00:00', 10, 1),
('Jade Smith', '7495489010', '2024-01-24', '11:45:00', 7, 12),
('Thomas Holt', '9317374357', '2024-01-26', '09:45:00', 2, 1),
('Michael Duncan', '8374834974', '2023-09-12', '08:45:00', 9, 1),
('Thomas White', '8183812950', '2023-08-04', '17:30:00', 2, 2),
('Adriana Collier', '8877923397', '2023-12-12', '09:30:00', 5, 11),
('Michael Duncan', '8228718846', '2023-09-24', '09:00:00', 4, 2),
('Julia Patel', '6626762990', '2023-12-08', '17:00:00', 7, 12),
('Brian Graham', '9892503380', '2024-01-21', '12:00:00', 1, 12),
('Terry Duncan', '6341064179', '2023-12-04', '13:00:00', 1, 5),
('Cynthia Frederick', '9911371664', '2024-02-13', '15:45:00', 8, 11),
('Samantha Smith', '9747450954', '2023-12-12', '15:15:00', 3, 3),
('Jason Morgan', '9901879760', '2023-10-01', '11:30:00', 1, 2),
('Andrew Noble', '7237815739', '2023-09-01', '15:15:00', 6, 4),
('Tyler Barber', '8043826558', '2023-09-17', '18:15:00', 6, 4),
('Jade Smith', '8672908913', '2023-12-19', '17:45:00', 5, 4),
('Austin Warren', '6312892542', '2023-10-28', '16:30:00', 2, 12),
('Jade Smith', '8370432503', '2023-12-08', '09:30:00', 9, 3),
('Kimberly Harrison', '8268699113', '2024-01-31', '16:15:00', 10, 12),
('Stephanie Mccormick', '8669727222', '2024-02-03', '09:00:00', 3, 12),
('Cynthia Frederick', '7427812003', '2023-09-01', '09:45:00', 8, 6),
('Jodi Wilson', '6299187000', '2024-01-10', '09:30:00', 6, 1),
('Ryan Davis', '6677416279', '2023-09-03', '16:30:00', 5, 10),
('Linda Bell', '8156948142', '2023-09-24', '14:00:00', 7, 4),
('Karen Gomez', '8338374048', '2023-12-31', '08:45:00', 8, 3),
('Thomas Holt', '8219571806', '2023-09-25', '10:15:00', 1, 7),
('Terry Gonzalez', '9934217145', '2023-09-19', '18:45:00', 9, 8),
('Richard Santos', '9793524271', '2023-11-19', '16:00:00', 9, 7),
('Joseph Martinez', '7644598650', '2023-11-24', '17:45:00', 5, 10),
('Michael Duncan', '6866986463', '2024-02-14', '16:00:00', 4, 1),
('Joseph Martinez', '7434273772', '2023-09-08', '17:45:00', 6, 8),
('Bryan Knight', '9182378304', '2024-02-13', '14:00:00', 4, 2),
('Emily Clark', '7560281941', '2024-02-26', '18:30:00', 5, 2),
('Cynthia Frederick', '9269338846', '2023-12-20', '16:30:00', 3, 10),
('Adriana Collier', '7950723520', '2023-10-22', '13:15:00', 3, 4),
('Jeffrey Chandler', '6713253792', '2023-08-25', '14:15:00', 5, 9),
('Stephanie Mccormick', '9975612245', '2023-08-13', '16:15:00', 2, 7),
('Heather Gomez', '7377793744', '2023-08-07', '15:00:00', 8, 1),
('Tyler Barber', '7640671686', '2023-10-20', '11:30:00', 3, 12),
('Stephanie Mccormick', '7737472721', '2024-01-21', '12:00:00', 7, 1),
('Ashley Lewis', '8374613443', '2023-08-21', '12:15:00', 9, 8),
('Julia Patel', '9293474542', '2023-11-23', '14:00:00', 7, 4),
('Stephanie Mccormick', '7515018196', '2023-10-03', '10:00:00', 10, 8),
('Ashley Lewis', '9612743626', '2023-11-12', '09:30:00', 10, 8),
('John Hughes', '9292848343', '2024-01-04', '17:15:00', 1, 1),
('Rachel Carpenter', '6657097696', '2024-02-22', '16:00:00', 4, 6),
('Oscar Grant', '7827693678', '2024-01-10', '08:30:00', 9, 11),
('Daniel Ward', '8956652367', '2024-01-27', '10:30:00', 4, 8),
('Joseph Martinez', '7173196941', '2023-09-22', '14:30:00', 5, 9),
('Joel Lee', '8481862869', '2023-11-28', '11:45:00', 10, 7),
('Ryan Davis', '9250595723', '2024-01-13', '13:45:00', 10, 4),
('Jaclyn Harris', '8123845851', '2023-11-11', '17:00:00', 9, 12),
('Lauren Conner', '6587527892', '2023-09-29', '15:15:00', 1, 6),
('Jason Carter', '7752441181', '2023-09-07', '13:00:00', 9, 4),
('Oscar Grant', '6501630248', '2023-10-01', '16:00:00', 5, 5),
('Thomas White', '8093596973', '2023-11-02', '18:15:00', 4, 8),
('Christine Jackson', '9160459859', '2024-01-09', '15:15:00', 3, 8),
('Jaclyn Harris', '6930994807', '2023-12-03', '09:15:00', 3, 12),
('Cynthia Frederick', '9304628258', '2023-11-24', '11:15:00', 10, 8),
('Christopher Kerr', '8197102220', '2024-02-15', '08:30:00', 3, 1),
('Anthony Decker', '8931131569', '2023-11-14', '09:15:00', 1, 7),
('Karen Gomez', '9859885851', '2024-02-21', '14:00:00', 6, 11),
('John Hill', '6530511213', '2024-02-12', '14:15:00', 5, 5),
('Bradley Smith', '9950685230', '2023-11-28', '10:00:00', 6, 5),
('Rachel Carpenter', '8120013707', '2024-01-19', '13:00:00', 10, 4),
('Thomas White', '8362968937', '2023-09-02', '09:45:00', 7, 6),
('Terry Duncan', '7568604760', '2023-09-03', '13:15:00', 8, 4),
('Kevin Murray', '6017943113', '2023-10-21', '13:30:00', 3, 10),
('Adriana Collier', '6629712464', '2024-01-05', '16:45:00', 8, 2),
('Cynthia Frederick', '9584644379', '2023-11-09', '13:30:00', 2, 8),
('Brittany Cook', '6146812210', '2023-10-04', '16:30:00', 6, 5),
('Eric Ryan', '6602075372', '2023-12-10', '11:45:00', 6, 8),
('Tanya Hayes', '8509080309', '2023-11-12', '18:00:00', 9, 9),
('Julia Patel', '9119577267', '2023-08-21', '15:30:00', 8, 8),
('Richard Santos', '6445412451', '2023-11-14', '17:45:00', 4, 11),
('Oscar Grant', '7277408097', '2024-01-08', '15:15:00', 4, 8),
('Emily Clark', '9475483451', '2024-01-05', '11:00:00', 10, 3),
('Norma Gonzalez', '9633347651', '2023-11-07', '11:00:00', 1, 8),
('Erica Miller', '8693478918', '2023-09-12', '10:15:00', 5, 8),
('Karen Gomez', '6720285456', '2023-11-02', '16:45:00', 9, 6),
('Tyler Barber', '6347917684', '2023-12-04', '14:15:00', 10, 8),
('Brittany Cook', '9402142520', '2023-10-08', '09:00:00', 4, 4),
('Jaclyn Harris', '9916216155', '2024-02-23', '14:00:00', 8, 9),
('Andrew Noble', '6815009693', '2023-11-04', '13:00:00', 9, 11),
('Karen Gomez', '8392712339', '2023-09-16', '09:30:00', 10, 7),
('Christopher Kerr', '6675956081', '2023-11-02', '14:15:00', 8, 9),
('Andrew Ruiz', '8866771007', '2024-01-22', '08:15:00', 5, 12),
('Mathew Watkins', '7462309248', '2023-11-24', '10:45:00', 4, 8),
('Jeffrey Chandler', '9409608425', '2024-01-10', '13:00:00', 8, 1),
('Evelyn Jacobs', '9778488111', '2023-12-19', '11:00:00', 9, 11),
('Andrew Noble', '9239887003', '2023-10-19', '11:30:00', 3, 3),
('Tyler Barber', '7312530207', '2023-10-02', '11:15:00', 10, 3),
('Mario Hernandez', '6999687280', '2024-01-09', '11:30:00', 6, 3),
('Kevin Murray', '6120711918', '2023-12-11', '11:15:00', 6, 6),
('Jason Morgan', '6499634916', '2023-08-29', '14:30:00', 10, 4),
('Tiffany Shaw', '9250270181', '2023-10-25', '11:45:00', 4, 8),
('Erica Miller', '8131748792', '2023-12-28', '16:30:00', 6, 6),
('Tracy Perez', '8890645438', '2023-11-22', '15:00:00', 3, 4),
('Erica Miller', '8168685910', '2023-09-26', '11:15:00', 9, 10),
('Tracy Perez', '6301634621', '2024-02-14', '16:00:00', 10, 8),
('Lisa Conrad', '6099421419', '2023-09-18', '16:15:00', 4, 3),
('Robert Martinez', '6925895961', '2023-08-11', '08:00:00', 1, 6),
('Jade Smith', '6392328803', '2023-11-17', '15:15:00', 10, 4),
('Stephanie Mccormick', '6252412443', '2023-11-17', '13:30:00', 8, 10),
('Ryan Davis', '8348133294', '2023-08-10', '10:30:00', 1, 12),
('Bradley Smith', '7024338545', '2023-09-18', '16:15:00', 8, 4),
('Ashley Lewis', '9171317910', '2024-02-29', '11:00:00', 2, 12),
('Lauren Conner', '6364832957', '2024-02-22', '12:00:00', 4, 9),
('Tiffany Shaw', '7657976161', '2024-01-19', '17:15:00', 8, 5),
('Michael Wright', '8957736605', '2023-10-17', '10:45:00', 10, 1),
('Lawrence Sullivan', '7845111989', '2024-01-15', '15:15:00', 6, 7),
('Terry Gonzalez', '7750557023', '2023-08-09', '14:15:00', 6, 1),
('Jessica Greene', '7216138479', '2024-01-26', '18:45:00', 3, 8),
('Heather Gomez', '9613759134', '2023-08-24', '16:15:00', 2, 2),
('Tyler Barber', '7183570692', '2024-02-18', '13:45:00', 8, 7),
('Gabriel Vasquez', '6931213119', '2024-02-22', '13:30:00', 6, 12),
('Anthony Decker', '8561319675', '2023-11-05', '12:30:00', 2, 4),
('Joseph Jones', '6696495794', '2023-08-02', '16:45:00', 4, 7),
('Tracy Perez', '8534638915', '2023-09-01', '12:45:00', 9, 12),
('Samantha Smith', '6786668802', '2024-02-11', '17:45:00', 10, 7),
('Adriana Collier', '9556574285', '2024-01-13', '13:00:00', 9, 11),
('Jason Carter', '8560847170', '2023-12-15', '12:15:00', 8, 2),
('Joseph Martinez', '8883683164', '2024-01-01', '09:15:00', 9, 12),
('Lisa Conrad', '6475765048', '2023-11-15', '12:15:00', 10, 5),
('Linda Harmon', '7478560034', '2023-12-14', '17:45:00', 1, 5),
('Brian Graham', '6379487767', '2023-10-24', '08:45:00', 5, 8),
('Richard Santos', '6435563278', '2024-01-30', '11:15:00', 10, 12),
('Tracy Perez', '8532043047', '2024-01-25', '10:00:00', 1, 3),
('Terry Gonzalez', '6229310173', '2024-01-28', '09:15:00', 9, 6),
('Robert Martinez', '6130126610', '2023-12-21', '15:15:00', 7, 10),
('Kevin Murray', '8908199837', '2023-08-24', '17:45:00', 6, 3),
('Kevin Murray', '8476808048', '2023-10-19', '11:15:00', 8, 11),
('Brittany Cook', '7249601745', '2023-08-02', '16:30:00', 10, 9),
('Andrew Noble', '6112105294', '2024-02-11', '13:30:00', 5, 7),
('Jessica Greene', '9741313457', '2023-11-27', '11:30:00', 9, 12),
('Rachel Carpenter', '8294811748', '2023-11-06', '12:15:00', 1, 5),
('Jason Carter', '8570617166', '2024-02-20', '09:00:00', 4, 5),
('Crystal Mason', '6643641558', '2024-01-05', '09:15:00', 5, 10),
('Eric Ryan', '6790061074', '2024-01-26', '13:15:00', 4, 3),
('Rachel Carpenter', '9190512505', '2023-12-03', '13:45:00', 6, 6),
('Linda Harmon', '7335826090', '2023-08-31', '11:15:00', 10, 8),
('Jason Morgan', '8825385762', '2024-01-08', '11:30:00', 6, 8),
('Emily Clark', '9858047255', '2023-09-01', '13:15:00', 4, 7),
('Terry Duncan', '6883564038', '2024-02-07', '12:45:00', 1, 4),
('Tiffany Shaw', '9255325131', '2023-09-26', '16:30:00', 10, 11),
('Jason Carter', '7769499344', '2023-09-28', '11:30:00', 1, 8),
('Joseph Martinez', '9910688021', '2023-08-26', '12:30:00', 2, 11),
('Andrew Noble', '7244785706', '2023-11-05', '11:15:00', 8, 7),
('Mario Hernandez', '8621882025', '2023-09-13', '09:00:00', 7, 4),
('Lisa Conrad', '9177993930', '2024-02-05', '14:15:00', 6, 7),
('Linda Bell', '6029162197', '2023-09-22', '11:30:00', 2, 12),
('Jason Carter', '9216099045', '2023-10-04', '16:15:00', 10, 3),
('Michael Beard', '6053313409', '2024-01-15', '12:45:00', 6, 12),
('Bradley Smith', '9938495982', '2023-11-15', '10:00:00', 7, 5),
('Terry Duncan', '6777227003', '2023-11-10', '15:00:00', 5, 2),
('Gabriel Vasquez', '9719074133', '2023-08-20', '08:30:00', 4, 4),
('Jason Morgan', '8433304580', '2023-11-03', '09:45:00', 8, 10),
('Bradley Smith', '8231432035', '2023-12-15', '12:30:00', 7, 1),
('Eric Ryan', '8686206973', '2023-11-12', '10:00:00', 5, 2),
('Daniel Ward', '7552814874', '2023-12-06', '10:30:00', 6, 3),
('Linda Harmon', '9846939607', '2024-01-24', '14:00:00', 4, 3),
('Julia Patel', '6553144242', '2023-09-07', '09:00:00', 5, 1),
('Mathew Watkins', '6662534845', '2023-12-29', '12:15:00', 1, 3),
('Evelyn Jacobs', '8676573461', '2023-10-22', '11:45:00', 7, 1),
('Christopher Kerr', '7070832962', '2024-02-05', '10:15:00', 10, 7),
('Norma Gonzalez', '6243789023', '2023-09-23', '16:15:00', 9, 4),
('Evelyn Jacobs', '8585166145', '2024-02-16', '14:45:00', 9, 4),
('Donald Patel', '9150172496', '2023-10-19', '16:45:00', 9, 1),
('Lauren Conner', '6755927992', '2023-12-20', '17:45:00', 3, 11),
('Norma Gonzalez', '7146185159', '2024-02-13', '18:00:00', 1, 1),
('Oscar Grant', '8202978050', '2024-02-21', '18:15:00', 2, 1),
('Joseph Jones', '8113163763', '2023-08-18', '15:45:00', 7, 12),
('Samantha Smith', '6435059390', '2023-10-19', '10:00:00', 5, 9),
('Heather Gomez', '9112992945', '2023-12-21', '09:15:00', 5, 1),
('Linda Bell', '7733265701', '2023-08-17', '08:15:00', 1, 8),
('Eric Ryan', '6251364725', '2023-11-14', '08:00:00', 4, 4),
('Kevin Murray', '9460753330', '2024-01-12', '17:30:00', 4, 10),
('John Hill', '8679965333', '2023-09-09', '08:15:00', 1, 12),
('Joel Lee', '9038671665', '2023-08-07', '14:45:00', 2, 3),
('Jeffrey Chandler', '7523260233', '2023-12-05', '10:45:00', 4, 8),
('Justin Hoffman', '9257277138', '2023-11-11', '11:45:00', 1, 6),
('Joel Lee', '7410000691', '2024-01-23', '09:15:00', 8, 12),
('Cheryl Lopez', '6117523612', '2023-08-17', '18:45:00', 2, 3),
('Terry Duncan', '8730635972', '2024-01-13', '17:15:00', 3, 6),
('Joel Lee', '6646540120', '2024-01-25', '14:00:00', 3, 3),
('Donald Patel', '9231514763', '2023-12-13', '17:00:00', 4, 4),
('Jessica Greene', '8147230935', '2024-02-13', '11:15:00', 9, 10),
('John Hughes', '8816779510', '2023-08-23', '11:00:00', 8, 1),
('Kimberly Harrison', '6269225689', '2024-01-20', '12:30:00', 4, 4),
('John Hughes', '9598989571', '2023-12-25', '14:15:00', 3, 2),
('Heather Gomez', '6068226296', '2023-11-13', '14:15:00', 1, 4),
('Joseph Jones', '8897867743', '2024-01-09', '14:30:00', 1, 1),
('Austin Warren', '6629027745', '2024-01-13', '14:45:00', 10, 7),
('Emily Clark', '6953226951', '2023-08-03', '16:00:00', 1, 6),
('Gabriel Vasquez', '8023927316', '2024-02-24', '10:30:00', 5, 8),
('Brittany Cook', '6268453240', '2023-11-26', '13:00:00', 6, 5),
('Kimberly Harrison', '8622872709', '2023-09-09', '16:15:00', 10, 7),
('Daniel Johnson', '6265621057', '2023-08-17', '15:30:00', 4, 9),
('Richard Santos', '7124816198', '2023-08-04', '18:30:00', 4, 9),
('Jeffrey Chandler', '7808294652', '2023-11-01', '13:15:00', 4, 1),
('Joel Lee', '9365139789', '2023-10-15', '10:00:00', 4, 10),
('Robert Martinez', '6953168413', '2024-01-18', '11:30:00', 1, 8),
('Emily Clark', '9185921071', '2023-11-10', '11:45:00', 2, 1),
('Tanya Hayes', '9159067588', '2023-11-15', '16:30:00', 10, 6),
('Kimberly Harrison', '6126791782', '2024-01-07', '18:15:00', 9, 10),
('Lauren Conner', '8986449199', '2023-09-10', '08:15:00', 1, 1),
('Linda Bell', '6779928247', '2023-09-03', '14:30:00', 2, 1),
('Austin Warren', '8936260559', '2023-09-07', '15:15:00', 10, 10),
('Cheryl Lopez', '9157324015', '2023-08-23', '15:45:00', 7, 3),
('Brian Graham', '9897131718', '2023-10-26', '11:00:00', 4, 2),
('Karen Gomez', '8689511153', '2023-12-17', '11:45:00', 1, 12),
('Joseph Jones', '8209516315', '2023-09-02', '09:15:00', 4, 7),
('Stacey Swanson', '6361154585', '2024-01-12', '12:00:00', 1, 11),
('Samantha Smith', '7908745832', '2023-08-07', '14:00:00', 6, 8),
('Eric Ryan', '9739352794', '2023-11-07', '16:45:00', 6, 10),
('Lawrence Sullivan', '8887720230', '2024-01-23', '09:00:00', 1, 2),
('Daniel Johnson', '6684678037', '2023-11-10', '13:30:00', 4, 8),
('Brian Graham', '7584554453', '2023-11-15', '09:00:00', 3, 10),
('Anthony Decker', '9459244258', '2024-02-28', '08:45:00', 7, 8),
('Joseph Jones', '7105704034', '2023-10-18', '08:15:00', 2, 9),
('John Hill', '9281836967', '2024-02-24', '10:15:00', 10, 2),
('Kyle Eaton', '7033797531', '2023-09-16', '08:00:00', 6, 12),
('Crystal Mason', '6254852660', '2024-02-14', '14:15:00', 6, 1),
('Thomas Holt', '8665911115', '2023-10-19', '08:45:00', 10, 8),
('Linda Harmon', '6827173741', '2024-02-24', '15:45:00', 1, 3),
('Lawrence Sullivan', '8443882555', '2024-01-06', '14:15:00', 6, 6),
('Andrew Ruiz', '7679118696', '2023-10-27', '18:45:00', 5, 9),
('Mathew Watkins', '9273715835', '2024-01-20', '17:45:00', 2, 1),
('Kyle Eaton', '6910069854', '2023-10-31', '11:00:00', 1, 4),
('Beth Martinez', '6169619988', '2024-01-16', '16:45:00', 8, 12),
('Crystal Mason', '7189766311', '2023-10-18', '09:15:00', 8, 5),
('Tiffany Shaw', '9016245024', '2023-08-18', '16:45:00', 8, 3),
('Ryan Davis', '9907784692', '2023-08-19', '11:45:00', 10, 4),
('Richard Santos', '9957331450', '2023-10-22', '08:45:00', 1, 8),
('Anthony Decker', '8045242285', '2023-09-16', '12:00:00', 1, 3),
('Andrew Ruiz', '7739298239', '2023-08-06', '12:30:00', 5, 2),
('John Hughes', '8412454101', '2023-10-15', '18:00:00', 6, 2),
('Daniel Ward', '8139089392', '2023-08-29', '18:30:00', 10, 9),
('Bryan Knight', '8544768361', '2024-02-20', '14:00:00', 10, 6),
('Terry Gonzalez', '9410462444', '2023-09-18', '13:30:00', 7, 7),
('Christine Jackson', '7143571855', '2023-08-01', '17:00:00', 2, 1),
('Terry Gonzalez', '6640436909', '2023-12-30', '12:45:00', 3, 7),
('Daniel Johnson', '7128651771', '2024-01-29', '08:15:00', 2, 2),
('Kyle Eaton', '9719912694', '2024-02-21', '17:45:00', 9, 11),
('Ryan Davis', '9743618366', '2024-02-19', '09:45:00', 3, 8),
('Daniel Johnson', '6310233015', '2023-08-18', '18:45:00', 5, 2),
('Michael Wright', '6829106691', '2024-01-31', '14:00:00', 2, 10),
('Stacey Swanson', '8289240829', '2023-08-17', '08:30:00', 8, 7),
('Justin Hoffman', '9043327773', '2023-11-25', '12:00:00', 4, 12),
('Kyle Eaton', '9629485253', '2023-08-03', '08:30:00', 10, 1),
('Michael Wright', '6374456309', '2023-09-20', '08:15:00', 4, 2),
('Michael Wright', '8725616452', '2023-10-17', '09:15:00', 7, 11),
('Michael Wright', '7960072293', '2023-11-20', '12:45:00', 2, 9),
('Thomas Holt', '7598911492', '2024-02-02', '09:30:00', 9, 12),
('Bryan Knight', '9387299970', '2023-11-21', '14:15:00', 1, 10);

INSERT INTO TherapistAttendance (TherapistID, Date, CheckinTime, CheckoutTime) VALUES (4, '2023-10-16', '14:45:00', '12:15:00'),
(7, '2023-10-13', '12:00:00', '15:15:00'),
(10, '2024-01-30', '10:45:00', '09:15:00'),
(10, '2023-11-03', '08:45:00', '14:45:00'),
(9, '2024-02-03', '11:15:00', '09:15:00'),
(2, '2024-02-05', '13:30:00', '15:00:00'),
(10, '2023-08-18', '09:15:00', '08:15:00'),
(2, '2023-08-22', '16:30:00', '08:45:00'),
(7, '2023-10-02', '14:15:00', '12:30:00'),
(1, '2024-02-29', '10:45:00', '10:15:00'),
(8, '2023-11-25', '11:30:00', '13:00:00'),
(9, '2023-09-25', '17:45:00', '12:30:00'),
(5, '2024-02-01', '16:00:00', '12:30:00'),
(6, '2023-09-18', '08:15:00', '11:30:00'),
(4, '2023-10-20', '18:30:00', '13:30:00'),
(8, '2023-09-14', '08:30:00', '09:30:00'),
(5, '2024-01-02', '17:45:00', '17:45:00'),
(6, '2023-08-14', '14:15:00', '15:30:00'),
(1, '2023-10-24', '15:15:00', '12:30:00'),
(4, '2024-02-29', '14:00:00', '18:15:00'),
(2, '2023-09-17', '16:00:00', '13:45:00'),
(9, '2023-12-22', '13:15:00', '13:15:00'),
(9, '2023-11-23', '08:00:00', '14:45:00'),
(5, '2024-01-09', '17:30:00', '13:45:00'),
(10, '2024-02-21', '09:30:00', '08:15:00'),
(1, '2023-09-10', '14:00:00', '15:00:00'),
(6, '2023-08-10', '08:45:00', '17:45:00'),
(7, '2023-12-14', '16:45:00', '11:00:00'),
(10, '2023-11-14', '13:00:00', '18:45:00'),
(6, '2023-10-29', '08:15:00', '10:00:00'),
(2, '2023-10-17', '13:30:00', '15:30:00'),
(4, '2023-08-10', '12:30:00', '14:45:00'),
(5, '2024-01-04', '15:45:00', '18:00:00'),
(1, '2023-10-12', '08:00:00', '12:15:00'),
(9, '2023-10-02', '17:15:00', '11:45:00'),
(5, '2024-01-23', '10:45:00', '11:00:00'),
(3, '2023-11-08', '11:15:00', '17:45:00'),
(10, '2023-11-15', '09:15:00', '10:00:00'),
(1, '2023-08-24', '16:45:00', '11:00:00'),
(4, '2023-08-02', '08:15:00', '12:15:00'),
(9, '2023-12-04', '16:00:00', '15:30:00'),
(6, '2023-09-18', '10:00:00', '11:30:00'),
(5, '2024-02-25', '11:00:00', '12:30:00'),
(9, '2024-02-05', '13:00:00', '17:45:00'),
(8, '2023-08-02', '12:30:00', '17:45:00'),
(10, '2023-09-23', '09:30:00', '11:15:00'),
(7, '2024-01-24', '08:45:00', '10:45:00'),
(2, '2024-01-26', '11:30:00', '10:15:00'),
(9, '2023-09-12', '18:30:00', '18:30:00'),
(2, '2023-08-04', '10:00:00', '14:30:00'),
(5, '2023-12-12', '18:00:00', '18:00:00'),
(4, '2023-09-24', '16:45:00', '16:45:00'),
(7, '2023-12-08', '09:00:00', '17:30:00'),
(1, '2024-01-21', '08:00:00', '13:00:00'),
(1, '2023-12-04', '10:30:00', '14:00:00'),
(8, '2024-02-13', '10:00:00', '08:00:00'),
(3, '2023-12-12', '10:30:00', '16:15:00'),
(1, '2023-10-01', '17:15:00', '12:15:00'),
(6, '2023-09-01', '13:30:00', '09:45:00'),
(6, '2023-09-17', '10:30:00', '13:00:00'),
(5, '2023-12-19', '15:45:00', '18:30:00'),
(2, '2023-10-28', '15:45:00', '09:30:00'),
(9, '2023-12-08', '10:15:00', '17:45:00'),
(10, '2024-01-31', '16:30:00', '15:30:00'),
(3, '2024-02-03', '17:00:00', '15:45:00'),
(8, '2023-09-01', '13:15:00', '15:30:00'),
(6, '2024-01-10', '13:45:00', '17:00:00'),
(5, '2023-09-03', '16:45:00', '10:15:00'),
(7, '2023-09-24', '12:30:00', '14:00:00'),
(8, '2023-12-31', '17:15:00', '16:15:00'),
(1, '2023-09-25', '15:45:00', '08:45:00'),
(9, '2023-09-19', '12:45:00', '09:30:00'),
(9, '2023-11-19', '08:00:00', '17:45:00'),
(5, '2023-11-24', '11:30:00', '08:00:00'),
(4, '2024-02-14', '10:30:00', '11:00:00'),
(6, '2023-09-08', '18:30:00', '14:30:00'),
(4, '2024-02-13', '14:00:00', '11:45:00'),
(5, '2024-02-26', '11:15:00', '14:15:00'),
(3, '2023-12-20', '14:00:00', '14:00:00'),
(3, '2023-10-22', '17:45:00', '08:00:00'),
(5, '2023-08-25', '14:00:00', '10:15:00'),
(2, '2023-08-13', '17:45:00', '15:15:00'),
(8, '2023-08-07', '17:30:00', '12:30:00'),
(3, '2023-10-20', '17:00:00', '14:15:00'),
(7, '2024-01-21', '16:45:00', '18:00:00'),
(9, '2023-08-21', '11:15:00', '11:45:00'),
(7, '2023-11-23', '17:45:00', '18:45:00'),
(10, '2023-10-03', '13:45:00', '08:30:00'),
(10, '2023-11-12', '15:00:00', '08:30:00'),
(1, '2024-01-04', '11:15:00', '08:30:00'),
(4, '2024-02-22', '11:30:00', '15:30:00'),
(9, '2024-01-10', '11:45:00', '09:00:00'),
(4, '2024-01-27', '08:30:00', '11:45:00'),
(5, '2023-09-22', '09:00:00', '17:15:00'),
(10, '2023-11-28', '12:45:00', '09:00:00'),
(10, '2024-01-13', '14:45:00', '13:00:00'),
(9, '2023-11-11', '14:45:00', '15:15:00'),
(1, '2023-09-29', '11:45:00', '10:30:00'),
(9, '2023-09-07', '13:15:00', '08:15:00'),
(5, '2023-10-01', '08:15:00', '16:45:00'),
(4, '2023-11-02', '17:15:00', '15:00:00'),
(3, '2024-01-09', '11:30:00', '18:15:00'),
(3, '2023-12-03', '11:45:00', '14:30:00'),
(10, '2023-11-24', '17:00:00', '16:45:00'),
(3, '2024-02-15', '18:45:00', '14:15:00'),
(1, '2023-11-14', '08:30:00', '16:00:00'),
(6, '2024-02-21', '10:00:00', '11:30:00'),
(5, '2024-02-12', '15:45:00', '09:30:00'),
(6, '2023-11-28', '17:30:00', '10:00:00'),
(10, '2024-01-19', '11:15:00', '15:00:00'),
(7, '2023-09-02', '11:15:00', '08:15:00'),
(8, '2023-09-03', '11:00:00', '12:45:00'),
(3, '2023-10-21', '15:00:00', '12:30:00'),
(8, '2024-01-05', '16:45:00', '17:30:00'),
(2, '2023-11-09', '18:15:00', '14:00:00'),
(6, '2023-10-04', '10:00:00', '09:30:00'),
(6, '2023-12-10', '12:00:00', '13:45:00'),
(9, '2023-11-12', '18:30:00', '16:15:00'),
(8, '2023-08-21', '15:15:00', '10:45:00'),
(4, '2023-11-14', '12:00:00', '08:00:00'),
(4, '2024-01-08', '11:15:00', '09:15:00'),
(10, '2024-01-05', '11:30:00', '11:45:00'),
(1, '2023-11-07', '15:45:00', '11:00:00'),
(5, '2023-09-12', '11:45:00', '18:45:00'),
(9, '2023-11-02', '18:00:00', '10:45:00'),
(10, '2023-12-04', '08:45:00', '16:15:00'),
(4, '2023-10-08', '12:30:00', '14:30:00'),
(8, '2024-02-23', '16:15:00', '15:30:00'),
(9, '2023-11-04', '12:15:00', '15:45:00'),
(10, '2023-09-16', '18:00:00', '18:45:00'),
(8, '2023-11-02', '14:15:00', '12:30:00'),
(5, '2024-01-22', '15:30:00', '10:45:00'),
(4, '2023-11-24', '10:30:00', '13:15:00'),
(8, '2024-01-10', '17:15:00', '16:30:00'),
(9, '2023-12-19', '17:15:00', '10:30:00'),
(3, '2023-10-19', '17:15:00', '10:30:00'),
(10, '2023-10-02', '13:30:00', '11:30:00'),
(6, '2024-01-09', '16:00:00', '18:15:00'),
(6, '2023-12-11', '14:00:00', '12:45:00'),
(10, '2023-08-29', '08:00:00', '09:15:00'),
(4, '2023-10-25', '09:45:00', '10:45:00'),
(6, '2023-12-28', '08:00:00', '08:15:00'),
(3, '2023-11-22', '09:45:00', '09:00:00'),
(9, '2023-09-26', '18:15:00', '08:00:00'),
(10, '2024-02-14', '14:30:00', '13:00:00'),
(4, '2023-09-18', '18:00:00', '11:30:00'),
(1, '2023-08-11', '18:00:00', '13:00:00'),
(10, '2023-11-17', '13:45:00', '14:30:00'),
(8, '2023-11-17', '10:45:00', '10:45:00'),
(1, '2023-08-10', '16:45:00', '17:45:00'),
(8, '2023-09-18', '15:45:00', '11:45:00'),
(2, '2024-02-29', '08:00:00', '12:45:00'),
(4, '2024-02-22', '17:15:00', '17:30:00'),
(8, '2024-01-19', '11:45:00', '13:15:00'),
(10, '2023-10-17', '12:15:00', '08:30:00'),
(6, '2024-01-15', '11:45:00', '12:15:00'),
(6, '2023-08-09', '08:30:00', '10:45:00'),
(3, '2024-01-26', '17:00:00', '08:30:00'),
(2, '2023-08-24', '16:00:00', '14:00:00'),
(8, '2024-02-18', '16:00:00', '16:30:00'),
(6, '2024-02-22', '10:30:00', '10:15:00'),
(2, '2023-11-05', '13:45:00', '08:15:00'),
(4, '2023-08-02', '15:00:00', '18:45:00'),
(9, '2023-09-01', '14:00:00', '11:45:00'),
(10, '2024-02-11', '16:30:00', '09:15:00'),
(9, '2024-01-13', '10:45:00', '13:15:00'),
(8, '2023-12-15', '15:30:00', '18:00:00'),
(9, '2024-01-01', '10:00:00', '14:45:00'),
(10, '2023-11-15', '14:45:00', '09:00:00'),
(1, '2023-12-14', '08:45:00', '14:15:00'),
(5, '2023-10-24', '08:00:00', '14:30:00'),
(10, '2024-01-30', '12:00:00', '08:45:00'),
(1, '2024-01-25', '17:45:00', '08:15:00'),
(9, '2024-01-28', '14:30:00', '16:30:00'),
(7, '2023-12-21', '16:00:00', '12:15:00'),
(6, '2023-08-24', '10:15:00', '09:45:00'),
(8, '2023-10-19', '14:30:00', '08:30:00'),
(10, '2023-08-02', '17:30:00', '10:15:00'),
(5, '2024-02-11', '09:15:00', '10:00:00'),
(9, '2023-11-27', '17:30:00', '11:30:00'),
(1, '2023-11-06', '11:15:00', '09:30:00'),
(4, '2024-02-20', '10:30:00', '11:00:00'),
(5, '2024-01-05', '15:15:00', '18:45:00'),
(4, '2024-01-26', '18:30:00', '10:45:00'),
(6, '2023-12-03', '18:00:00', '08:00:00'),
(10, '2023-08-31', '14:30:00', '14:00:00'),
(6, '2024-01-08', '15:45:00', '16:15:00'),
(4, '2023-09-01', '09:30:00', '13:45:00'),
(1, '2024-02-07', '14:15:00', '09:30:00'),
(10, '2023-09-26', '09:30:00', '11:30:00'),
(1, '2023-09-28', '16:45:00', '15:30:00'),
(2, '2023-08-26', '10:45:00', '18:00:00'),
(8, '2023-11-05', '17:00:00', '14:30:00'),
(7, '2023-09-13', '10:15:00', '12:30:00'),
(6, '2024-02-05', '10:00:00', '13:30:00'),
(2, '2023-09-22', '10:00:00', '16:00:00'),
(10, '2023-10-04', '08:15:00', '12:15:00'),
(6, '2024-01-15', '13:00:00', '11:15:00'),
(7, '2023-11-15', '13:45:00', '09:30:00'),
(5, '2023-11-10', '08:45:00', '08:00:00'),
(4, '2023-08-20', '13:15:00', '17:30:00'),
(8, '2023-11-03', '10:00:00', '13:45:00'),
(7, '2023-12-15', '08:30:00', '11:00:00'),
(5, '2023-11-12', '11:45:00', '17:00:00'),
(6, '2023-12-06', '08:45:00', '10:15:00'),
(4, '2024-01-24', '09:00:00', '12:45:00'),
(5, '2023-09-07', '10:00:00', '10:45:00'),
(1, '2023-12-29', '18:45:00', '12:00:00'),
(7, '2023-10-22', '18:45:00', '09:45:00'),
(10, '2024-02-05', '14:45:00', '08:30:00'),
(9, '2023-09-23', '14:45:00', '10:00:00'),
(9, '2024-02-16', '17:15:00', '16:45:00'),
(9, '2023-10-19', '14:00:00', '12:30:00'),
(3, '2023-12-20', '08:00:00', '14:30:00'),
(1, '2024-02-13', '13:15:00', '17:45:00'),
(2, '2024-02-21', '08:30:00', '16:00:00'),
(7, '2023-08-18', '14:00:00', '13:30:00'),
(5, '2023-10-19', '11:00:00', '12:45:00'),
(5, '2023-12-21', '16:45:00', '12:00:00'),
(1, '2023-08-17', '09:30:00', '10:15:00'),
(4, '2023-11-14', '17:00:00', '12:00:00'),
(4, '2024-01-12', '16:15:00', '14:00:00'),
(1, '2023-09-09', '11:45:00', '11:45:00'),
(2, '2023-08-07', '14:00:00', '14:45:00'),
(4, '2023-12-05', '18:00:00', '15:30:00'),
(1, '2023-11-11', '12:00:00', '14:45:00'),
(8, '2024-01-23', '09:15:00', '09:30:00'),
(2, '2023-08-17', '11:00:00', '15:00:00'),
(3, '2024-01-13', '10:15:00', '14:45:00'),
(3, '2024-01-25', '16:45:00', '13:30:00'),
(4, '2023-12-13', '17:15:00', '18:30:00'),
(9, '2024-02-13', '17:30:00', '10:45:00'),
(8, '2023-08-23', '14:15:00', '18:15:00'),
(4, '2024-01-20', '16:45:00', '15:30:00'),
(3, '2023-12-25', '14:45:00', '08:15:00'),
(1, '2023-11-13', '15:45:00', '17:15:00'),
(1, '2024-01-09', '15:15:00', '11:00:00'),
(10, '2024-01-13', '11:45:00', '18:00:00'),
(1, '2023-08-03', '10:45:00', '17:00:00'),
(5, '2024-02-24', '11:15:00', '14:30:00'),
(6, '2023-11-26', '17:30:00', '08:15:00'),
(10, '2023-09-09', '10:45:00', '13:00:00'),
(4, '2023-08-17', '17:15:00', '16:45:00'),
(4, '2023-08-04', '08:00:00', '16:30:00'),
(4, '2023-11-01', '09:45:00', '10:45:00'),
(4, '2023-10-15', '08:15:00', '10:15:00'),
(1, '2024-01-18', '10:45:00', '17:15:00'),
(2, '2023-11-10', '08:00:00', '13:30:00'),
(10, '2023-11-15', '17:15:00', '18:30:00'),
(9, '2024-01-07', '15:15:00', '18:45:00'),
(1, '2023-09-10', '11:00:00', '17:30:00'),
(2, '2023-09-03', '15:15:00', '09:30:00'),
(10, '2023-09-07', '17:30:00', '08:15:00'),
(7, '2023-08-23', '17:30:00', '08:45:00'),
(4, '2023-10-26', '13:00:00', '18:15:00'),
(1, '2023-12-17', '14:15:00', '09:00:00'),
(4, '2023-09-02', '15:45:00', '11:00:00'),
(1, '2024-01-12', '13:00:00', '08:00:00'),
(6, '2023-08-07', '15:30:00', '15:45:00'),
(6, '2023-11-07', '18:45:00', '12:30:00'),
(1, '2024-01-23', '17:45:00', '09:45:00'),
(4, '2023-11-10', '12:00:00', '14:15:00'),
(3, '2023-11-15', '09:00:00', '10:45:00'),
(7, '2024-02-28', '08:15:00', '08:30:00'),
(2, '2023-10-18', '11:45:00', '10:30:00'),
(10, '2024-02-24', '17:30:00', '13:00:00'),
(6, '2023-09-16', '12:45:00', '11:45:00'),
(6, '2024-02-14', '18:30:00', '15:00:00'),
(10, '2023-10-19', '11:00:00', '10:45:00'),
(1, '2024-02-24', '10:15:00', '10:15:00'),
(6, '2024-01-06', '15:00:00', '10:30:00'),
(5, '2023-10-27', '11:00:00', '18:45:00'),
(2, '2024-01-20', '16:30:00', '12:45:00'),
(1, '2023-10-31', '14:00:00', '17:15:00'),
(8, '2024-01-16', '11:30:00', '18:00:00'),
(8, '2023-10-18', '12:45:00', '12:45:00'),
(8, '2023-08-18', '10:45:00', '13:00:00'),
(10, '2023-08-19', '17:00:00', '13:30:00'),
(1, '2023-10-22', '16:00:00', '15:45:00'),
(1, '2023-09-16', '12:45:00', '14:30:00'),
(5, '2023-08-06', '18:45:00', '16:45:00'),
(6, '2023-10-15', '14:15:00', '16:00:00'),
(10, '2023-08-29', '09:15:00', '15:45:00'),
(10, '2024-02-20', '09:15:00', '12:45:00'),
(7, '2023-09-18', '10:45:00', '18:00:00'),
(2, '2023-08-01', '11:45:00', '14:45:00'),
(3, '2023-12-30', '18:15:00', '12:00:00'),
(2, '2024-01-29', '18:30:00', '09:45:00'),
(9, '2024-02-21', '18:15:00', '14:30:00'),
(3, '2024-02-19', '15:30:00', '18:00:00'),
(5, '2023-08-18', '16:45:00', '17:45:00'),
(2, '2024-01-31', '15:30:00', '15:00:00'),
(8, '2023-08-17', '17:45:00', '16:15:00'),
(4, '2023-11-25', '14:15:00', '13:30:00'),
(10, '2023-08-03', '12:00:00', '16:45:00'),
(4, '2023-09-20', '11:45:00', '16:15:00'),
(7, '2023-10-17', '16:15:00', '17:30:00'),
(2, '2023-11-20', '17:00:00', '17:45:00'),
(9, '2024-02-02', '15:30:00', '13:30:00'),
(1, '2023-11-21', '12:00:00', '08:15:00');