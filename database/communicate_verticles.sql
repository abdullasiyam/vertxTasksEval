DROP DATABASE IF EXISTS communicate_verticles;

CREATE DATABASE communicate_verticles;

USE communicate_verticles;

CREATE TABLE messages (
    id INT(15) PRIMARY KEY AUTO_INCREMENT,
    message_content VARCHAR(30),
    message_from VARCHAR(30),
    carried_by VARCHAR(30)
);

