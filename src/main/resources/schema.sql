CREATE TABLE AGENT (
  ID       BIGINT               AUTO_INCREMENT,
  LOGIN    VARCHAR(20) NOT NULL,
  PASSWORD VARCHAR(60) NOT NULL,
  BALANCE  DECIMAL     NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
);

ALTER TABLE AGENT
  ADD CONSTRAINT MIN_BALANCE CHECK BALANCE >= 0;

ALTER TABLE AGENT
  ADD CONSTRAINT UNIQUE_LOGIN UNIQUE (LOGIN);