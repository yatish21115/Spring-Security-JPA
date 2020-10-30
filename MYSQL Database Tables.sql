CREATE TABLE users (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(45) NOT NULL,
  username varchar(45) NOT NULL,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  password varchar(64) NOT NULL,
  enabled tinyint(4) DEFAULT NULL,
  security varchar(225) NOT NULL,
  answer varchar(225) NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY email_UNIQUE (email)
);
 
CREATE TABLE roles (
  role_id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  PRIMARY KEY (role_id)
);
 
CREATE TABLE users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  KEY user_fk_idx (user_id),
  KEY role_fk_idx (role_id),
  CONSTRAINT `role_fk` FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT `user_fk` FOREIGN KEY (user_id) REFERENCES users (user_id)
);

INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('CREATOR');
INSERT INTO roles (name) VALUES ('EDITOR');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO users (firstname, lastname, email, username, password, enabled) VALUES ('narendra', 'sharma', 'narendra@gmail.com', 'narendra', '$2a$10$cTUErxQqYVyU2qmQGIktpup5chLEdhD2zpzNEyYqmxrHHJbSNDOG.', '1');
INSERT INTO users (firstname, lastname, email, username, password, enabled) VALUES ('manju', 'sharma', 'manju@gmail.com', 'manju', '$2a$10$.tP2OH3dEG0zms7vek4ated5AiQ.EGkncii0OpCcGq4bckS9NOULu', '1');
INSERT INTO users (firstname, lastname, email, username, password, enabled) VALUES ('jyoti', 'sharma', 'jyoit@gmail.com', 'jyoti', '$2a$10$E2UPv7arXmp3q0LzVzCBNeb4B4AtbTAGjkefVDnSztOwE7Gix6kea', '1');
INSERT INTO users (firstname, lastname, email, username, password, enabled) VALUES ('bhumika', 'sharma', 'bhumika@gmail.com', 'bhumika', '$2a$10$GQT8bfLMaLYwlyUysnGwDu6HMB5G.tin5MKT/uduv2Nez0.DmhnOq', '1');
INSERT INTO users (firstname, lastname, email, username, password, enabled) VALUES ('yatish', 'sharma', 'yatish@gmail.com', 'yatish', '$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.', '1');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2); 
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3); 
INSERT INTO users_roles (user_id, role_id) VALUES (4, 2); 
INSERT INTO users_roles (user_id, role_id) VALUES (4, 3);
INSERT INTO users_roles (user_id, role_id) VALUES (5, 4);