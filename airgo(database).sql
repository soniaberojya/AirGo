CREATE database airGo;

USE airgo;


CREATE TABLE flightType
(
t_id INT(10) AUTO_INCREMENT PRIMARY KEY ,
p_type VARCHAR(50) 

);

CREATE TABLE flightInfo
(
  flight_number    INT(5) PRIMARY KEY AUTO_INCREMENT ,
  capacity        INT NOT NULL,
  SOURCE          VARCHAR(10) NOT NULL,
  s_date          date NOT NULL,
  s_time          TIME NOT NULL,
  destination     VARCHAR(10) NOT NULL,
  d_date          date NOT NULL,
  d_time          TIME NOT NULL,
  fare            int(10) NOT NULL,
  type            INT NOT NULL, 
 
);

CREATE TABLE users
(
  user_id   INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL unique,
  password  VARCHAR(50) NOT NULL,
  wallet DOUBLE   
);

ALTER TABLE users MODIFY wallet INT DEFAULT 0; 
ADD DEFAULT (0) FOR wallet;




CREATE TABLE passengers
(
  pass_no      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  pass_uID     INT NOT NULL,
  flight_no    INT NOT NULL,
  pass_fname   VARCHAR(50) NOT NULL,
  pass_lname   VARCHAR(50) NOT NULL,
  pass_age     INT NOT NULL,
  pass_gender  VARCHAR(20) NOT NULL CHECK(pass_gender IN('male','female','other')),
  pass_phone   VARCHAR(20) UNIQUE NOT NULL CHECK(pass_phone LIKE '__________'),
  pass_email   VARCHAR(20) NOT NULL CHECK(pass_email REGEXP '^[A-Za-z0-9._%\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
  pass_address VARCHAR(20) NOT NULL
 
);


INSERT INTO flighttype(p_type) VALUES('Domestic');
INSERT INTO flighttype(p_type) VALUES('International');


INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Mumbai','2021-03-03','13:30:00','Hyderabad','2021-03-03','15:00:00',2000,1);

INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Hyderabad','2021-03-03','13:30:00','Mumbai','2021-03-03','15:00:00',2000,1);


INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Mumbai','2021-03-03','15:20:00','Bangaluru','2021-03-03','17:00:00',2300,1);

INSERT INTO flightinfo(flight_number,capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(4,50,'Bangaluru','2021-03-03','15:20:00','Mumbai','2021-03-03','17:00:00',2300,1);


INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Mumbai','2021-03-03','13:30:00','Delhi','2021-03-03','15:45:00',2537,1);

INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Bangaluru','2021-03-03','15:30:00','Hyderabad','2021-03-03','16:55:00',1537,1);

INSERT INTO flightinfo(capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(50,'Hyderabad','2021-03-03','15:30:00','Bangaluru','2021-03-03','16:55:00',1537,1);

#****************************************************************************************************

INSERT INTO flightinfo(flight_number,capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(8,100,'Mumbai','2021-03-03','20:30:00','London','2021-04-03','12:30:00',65000,2);


INSERT INTO flightinfo(flight_number,capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(9,100,'Bangaluru','2021-07-03','20:30:00','Paris','2021-08-03','16:50:00',64780,2);

INSERT INTO flightinfo(flight_number,capacity,SOURCE,s_date,s_time,destination,d_date,d_time,fare,TYPE)
VALUES(10,100,'Mumbai','2021-09-03','20:30:00','Paris','2021-04-03','05:20:00',49509,2);



INSERT INTO users(user_name,PASSWORD,wallet) VALUES('sonia','sonia',20000);
INSERT INTO users(user_name,PASSWORD,wallet) VALUES('swapna','swapna',6000);
INSERT INTO users(user_name,PASSWORD,wallet) VALUES('madhu','madhu',3000);
INSERT INTO users(user_name,PASSWORD,wallet) VALUES('sayali','sayali',1500);
INSERT INTO users(user_name,PASSWORD,wallet) VALUES('bindu','bindu',1500);



INSERT INTO passengers(pass_uID,flight_no,pass_fname,pass_lname,pass_age,pass_gender,pass_phone, pass_email, pass_address)
VALUES(1,9,'sonia','Berojya',22,'female','1183745289','sonia@gmail.com','maharashtra');


INSERT INTO passengers(pass_uID,flight_no,pass_fname,pass_lname,pass_age,pass_gender,pass_phone, pass_email, pass_address)
VALUES(2,3,'Swapna','valusa',22,'female','9876543210','swapna@gmail.com','telangana');



INSERT INTO passengers(pass_uID,flight_no,pass_fname,pass_lname,pass_age,pass_gender,pass_phone, pass_email, pass_address)
VALUES(2,5,'Swapna','valusa','22','female','1111111111','swapna@gmail.com','telangana');

INSERT INTO passengers(pass_uID,flight_no,pass_fname,pass_lname,pass_age,pass_gender,pass_phone, pass_email, pass_address)
VALUES(1,8,'sonia','Berojya',22,'female','2222222222','swapna@gmail.com','telangana');


SELECT * from passengers;
SELECT * from flightinfo;
SELECT * from users;
SELECT * from flighttype;

