DROP DATABASE IF EXISTS quiz;
CREATE DATABASE quiz;
USE quiz;

CREATE TABLE answer(
		id INT(11) NOT NULL AUTO_INCREMENT,
		attemptId INT(11),
        questionId INT(11),
        answerText VARCHAR(50),
        PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

select * from answer;