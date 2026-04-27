Insert Into Explorers(Expid, Name, Username, Room_ID, Bag_Wt, Bag_Cnt)
VALUES(Explorers_SEQ.nextval, 'test', 'test', 101, 0, 0);

Insert Into Treasures(TresID, Name, ExpID, NPCId, RoomID, Description, Value, Weight)
VALUES(Treasures_SEQ.NEXTVAL, 'testing item', null, null, 105, 'testing', 10, 2);

commit;