DROP TABLE Connections;
DROP TABLE Treasures;
DROP SEQUENCE   treasures_seq;
DROP TABLE NPCs;
DROP SEQUENCE npc_seq;
DROP TABLE Explorers;
DROP SEQUENCE explorers_seq;
DROP TABLE Rooms;
DROP SEQUENCE rooms_seq;
DROP TRIGGER TreasuresUpdateTrigger;
DROP TRIGGER weightCountDefaultZeroTrigger ;


CREATE TABLE Rooms
(
	roomID		NUMBER(6,0)
,	name		VARCHAR2(50) UNIQUE
,	description	VARCHAR2(255)
,	CONSTRAINT PKRoomID PRIMARY KEY (roomID)
);

CREATE SEQUENCE rooms_seq
  START WITH     100
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;
  /
CREATE TABLE NPCs
(
	npcID	NUMBER(6,0)
,	Type	VARCHAR2(50)
,	RoomID	NUMBER(6,0)
,	CONSTRAINT PKNpcID PRIMARY KEY(npcID)
,	CONSTRAINT FKRoomID FOREIGN KEY(RoomID) REFERENCES Rooms (roomID)
);

/* updated npc table for extra credit
CREATE TABLE NPCs
(
	npcID	NUMBER(6,0)
,	Type	VARCHAR2(50)
,	RoomID	NUMBER(6,0)
,job VARCHAR2(30) --added
,description VARCHAR2(200) --added
,	CONSTRAINT PKNpcID PRIMARY KEY(npcID)
,	CONSTRAINT FKRoomID FOREIGN KEY(RoomID) REFERENCES Rooms (roomID)
);
*/

CREATE SEQUENCE npc_seq
  START WITH     100
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;


CREATE TABLE Connections
(
	room1	NUMBER(6,0)
,	room2	NUMBER(6,0)
,	CONSTRAINT PKroom1 PRIMARY KEY(room1,room2)
,	CONSTRAINT FKroom1 FOREIGN KEY(room1) REFERENCES Rooms (roomID)
,	CONSTRAINT FKroom2 FOREIGN KEY(room2) REFERENCES Rooms (roomID)
,	CONSTRAINT IDCheck CHECK(room1 != room2)
);

CREATE TABLE Explorers
(
	expID NUMBER(6,0)
,	name VARCHAR2(15) UNIQUE
,	username VARCHAR2(15)
,	room_ID NUMBER(6,0)
,   bag_wt  NUMBER(6,0) DEFAULT 0
,   bag_cnt NUMBER(6,0) DEFAULT 0
,	CONSTRAINT PKExp PRIMARY KEY(expID)
,	CONSTRAINT FKRoom FOREIGN KEY(room_ID) REFERENCES Rooms(roomID)
,   CONSTRAINT BagWeightCK CHECK ( bag_wt <= 100 )
,   CONSTRAINT BagCountCK CHECK ( bag_cnt <= 2 )
);

CREATE SEQUENCE explorers_seq
  START WITH     100
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;

CREATE TABLE Treasures
(
	tresID  NUMBER(6,0)
,   Name    VARCHAR2(250)
,	expID   NUMBER(6,0)
,	npcID   NUMBER(6,0)
,	roomID  NUMBER(6,0)
,	description   VARCHAR2(255)
,	value   NUMBER(6,0)
,	weight  NUMBER(6,0)
,   CONSTRAINT ValueCheckT CHECK (value > 0)
,   CONSTRAINT WeightCheckT CHECK (weight >= 0)
,	CONSTRAINT PKTresT PRIMARY KEY (tresID)
,	CONSTRAINT FKexpT FOREIGN KEY(expID) REFERENCES Explorers(expID)
,	CONSTRAINT FKnpcT FOREIGN KEY(npcID) REFERENCES Npcs(npcID)
,	CONSTRAINT FKRoomT FOREIGN KEY(roomID) REFERENCES Rooms(roomID)
--	CONSTRAINT CK_TreasureLocationT CHECK (
	--	(CASE WHEN expID IS NOT NULL THEN 1 ELSE 0 END) +
	--	(CASE WHEN npcID IS NOT NULL THEN 1 ELSE 0 END) +
	--	(CASE WHEN roomID IS NOT NULL THEN 1 ELSE 0 END) = 1
--	)

);
/*
  CREATE TABLE NPC_STATE
  (
    expID NUMBER NOT NULL,
    npcID NUMBER NOT NULL,
    last_state VARCHAR2(30) DEFAULT 'not met',
    quest_status VARCHAR2(30) DEFAULT 'not started',
    talk_count NUMBER DEFAULT 0,
 -- last_talked_at TIMESTAMP DEFAULT SYSTIMESTAMP, -- if needed 
    PRIMARY KEY (expID, npcID)
  );
*/

CREATE SEQUENCE treasures_seq
  START WITH     100
  INCREMENT BY   1
  NOCACHE
  NOCYCLE;
/
CREATE OR REPLACE TRIGGER TreasuresUpdateTrigger
    BEFORE UPDATE OF expID, npcID, roomID ON TREASURES
    FOR EACH ROW
    Declare 
      counter INTEGER := 0; 
    BEGIN
      IF :NEW.expID IS NULL THEN counter := counter + 1; END IF;  
		  IF :NEW.npcID IS NULL THEN counter := counter + 1; END IF; 
		  IF :NEW.roomID IS NULL THEN counter := counter + 1; END IF; 
      IF counter != 2 THEN RAISE_APPLICATION_ERROR(-20001,'One Id Must not be NULL And others Must be NULL'); End IF;
    END;
/

Create or Replace Trigger weightCountDefaultZeroTrigger 
  BEFORE INSERT ON Explorers
    FOR EACH ROW
  BEGIN
    IF :NEW.bag_cnt != 0 THEN :NEW.bag_cnt := 0; END IF;
    IF :NEW.bag_wt != 0 then :NEW.bag_wt := 0; END IF;
  END; 
  /


/* 

*/
  COMMIT; 