--SET SERVEROUTPUT ON;

CREATE OR REPLACE TRIGGER "expCurRoomCheckTreasureGrab"
    BEFORE UPDATE OF expID ON TREASURES
    FOR EACH ROW
    DECLARE
        connection Explorers.room_ID%type;
    BEGIN
        SELECT room_ID 
        INTO connection
        FROM EXPLORERS
        WHERE :New.EXPID = EXPLORERS.EXPID;

        IF connection IS NULL OR connection != :Old.roomid THEN
            RAISE_APPLICATION_ERROR(-20003, 'Item is not in same room as explorer');
        END IF;
    END;

/
CREATE OR REPLACE PROCEDURE grab
    (
        exp_ID IN EXPLORERS.EXPID%type,
        tres_ID IN TREASURES.TRESID%type
    )
IS
        bagCntOld EXPLORERS.BAG_CNT%type;
        bagCntNew EXPLORERS.BAG_CNT%type;
        roomMismatchEXP EXCEPTION;
        PRAGMA EXCEPTION_INIT(roomMismatchEXP, -20003);

    BEGIN



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

    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: In grab procedure');
    END;
/



