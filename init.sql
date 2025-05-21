CREATE DATABASE IF NOT EXISTS survey;
USE survey;

-- Actor table
CREATE TABLE actors (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        fname VARCHAR(50),
                        uname VARCHAR(50),
                        pass VARCHAR(50)
);

-- User Question Table
CREATE TABLE userQuestions (
                               id INT,
                               surveycode VARCHAR(5),
                               total INT
);

-- Questions table
CREATE TABLE questions (
                           surveycode VARCHAR(5),
                           question VARCHAR(255),
                           option1 VARCHAR(255),
                           option2 VARCHAR(255),
                           option3 VARCHAR(255),
                           option4 VARCHAR(255)
);

-- Survey Answer table
CREATE TABLE surveyquestions (
                                 surveycode VARCHAR(5),
                                 qno INT,
                                 opno INT
);
