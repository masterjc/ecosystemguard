CREATE DATABASE IF NOT EXISTS ecosystemguard;

USE ecosystemguard;

DROP TABLE IF EXISTS ipInfo;
DROP TABLE IF EXISTS hostInfo;
DROP TABLE IF EXISTS accountInfo;

CREATE TABLE accountInfo (
	username VARCHAR(254) NOT NULL,
	password VARCHAR(64) NOT NULL,
	telephoneNumber VARCHAR(32),
	recoverMail VARCHAR(254),
	PRIMARY KEY( username )
) ENGINE=innodb;

CREATE TABLE ipInfo (
	hostId VARCHAR(64) NOT NULL,
	publicIp VARCHAR(39) NOT NULL,
	lastChange DATETIME NOT NULL,
	PRIMARY KEY( hostId )
) ENGINE=innodb;

CREATE TABLE hostInfo (
	hostId VARCHAR(64) NOT NULL,
	userName VARCHAR(254) NOT NULL,
	summary VARCHAR(256) NOT NULL,
	version VARCHAR(16) NOT NULL,
	description VARCHAR(1000),
	PRIMARY KEY( hostId )
) ENGINE=innodb;

COMMIT;