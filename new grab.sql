create or replace PROCEDURE grab
    (
        exp_ID IN EXPLORERS.EXPID%type,
        tres_ID IN TREASURES.TRESID%type
    )
IS
        bagCntOld EXPLORERS.BAG_CNT%type;
        bagCntNew EXPLORERS.BAG_CNT%type;
        roomMismatchEXP EXCEPTION;
        PRAGMA EXCEPTION_INIT(roomMismatchEXP, -20003);
        nonExistantTreaure Exception;
        PRAGMA EXCEPTION_INIT(nonExistantTreaure, -20010); 
        tresWeight TREASURES.WEIGHT%TYPE; 
        alreadyHaveItem Exception;
        PRAGMA EXCEPTION_INIT(alreadyHaveItem, -20011);

    BEGIN

        SELECT WEIGHT INTO tresWeight
        FROM TREASURES
        WHERE TRESID = tres_ID;

        SELECT BAG_CNT INTO bagCntOld
        FROM EXPLORERS
        WHERE EXPID = exp_ID;


        UPDATE EXPLORERS
        SET BAG_CNT =1 + (SELECT BAG_CNT FROM EXPLORERS WHERE EXPID = exp_ID),
        BAG_WT = (SELECT BAG_WT FROM EXPLORERS WHERE EXPID = exp_ID) +
                 (SELECT WEIGHT FROM TREASURES WHERE TRESID = tres_ID)
        WHERE EXPID = exp_ID;

        SELECT BAG_CNT INTO bagCntNew
        FROM EXPLORERS
        WHERE EXPID = exp_ID;

        if bagCntOld != bagCntNew
        THEN
            UPDATE TREASURES
            SET ROOMID = null,expID = exp_ID
            WHERE TRESID = tres_ID;
        end if;

    EXCEPTION
    WHEN roomMismatchEXP THEN
        DBMS_OUTPUT.PUT_LINE('Error: Item is not in same room as explorer');
        RAISE;

    WHEN nonExistantTreaure THEN
        DBMS_OUTPUT.PUT_LINE('Treasure does not exist');
        RAISE;
        
    WHEN alreadyhaveItem THEN
        DBMS_OUTPUT.PUT_LINE('You already have that item');
        RAISE;

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: In grab procedure');
        RAISE; 
    END;