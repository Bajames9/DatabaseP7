INSERT INTO Rooms(roomid, Name, Description)
VALUES (101, 'Start Room', 'Parking Lot A');

Insert Into Rooms(roomid, Name, Description)
VALUES (104, 'Davis 115', 'Davis 115');

Insert Into Rooms(roomid, Name, Description)
VALUES (105, 'Davis 225', 'Networking Lab');

Insert Into Rooms(roomid, Name, Description)
VALUES (108, 'Dalton Dining', 'A loud, raucous dining hall');

Insert Into Rooms(roomid, Name, Description)
VALUES (109, 'CR 142', 'A state of the art cyber security lab');

Insert Into Rooms(roomid, Name, Description)
VALUES (111, 'End Room', 'A black hole rapidly eating matter..');

INSERT INTO Connections(room1, room2) 
VALUES (101, 105);

INSERT INTO Connections(room1, room2) 
VALUES (105, 101);

INSERT INTO Connections(room1, room2) 
VALUES (101, 108);

INSERT INTO Connections(room1, room2) 
VALUES (108, 101);

INSERT INTO Connections(room1, room2) 
VALUES (101, 109);

INSERT INTO Connections(room1, room2) 
VALUES (109, 101);

INSERT INTO Connections(room1, room2) 
VALUES (101, 104);

INSERT INTO Connections(room1, room2) 
VALUES (104, 101);

INSERT INTO Connections(room1, room2) 
VALUES (104, 105);

INSERT INTO Connections(room1, room2) 
VALUES (105, 104);

INSERT INTO Connections(room1, room2) 
VALUES (108, 109);

INSERT INTO Connections(room1, room2) 
VALUES (109, 108);

INSERT INTO Connections(room1, room2) 
VALUES (104, 109);

INSERT INTO Connections(room1, room2) 
VALUES (109, 104);

INSERT INTO Connections(room1, room2) 
VALUES (108, 111);

INSERT INTO Connections(room1, room2) 
VALUES (111, 108);

INSERT INTO Connections(room1, room2) 
VALUES (104, 111);

INSERT INTO Connections(room1, room2) 
VALUES (111, 104);

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'A crumpled up note', null, null, 104, 'Someones half eaten homework', 1, 1); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'Lost ONE card', null, null, 105, 'Aaaron A. Aaronson', 10, 2); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, '10 hotdogs, 8 buns', null, null, 108, 'Why do they sell them this way?', 14, 7); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'A broken display panel', null, null, 109, 'A broken display panel',  250, 90);

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'A crisp $10 bill', null, null, 101, 'No creases', 10, 1); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'An unused textbook', null, null, 104, 'CS 220', 200, 25); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'Lost Iphone', null, null, 108, 'Someone probably forgot this', 500, 5); 

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'Laptop Charger', null, null, 109, 'Looks like it has never been used',75, 8);

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'Ethernet Cable', null, null, 105, '100 feet', 75, 10); 

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Dan', 'Dan', 101, 0, 0);

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Bailey', 'Bailey', 101, 0, 0);

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Ryan', 'Ryan', 101, 0, 0);

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Prem', 'Prem', 101, 0, 0);

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Aiden', 'Aiden', 101, 0, 0);

Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'Kade', 'Kade', 101, 0, 0);