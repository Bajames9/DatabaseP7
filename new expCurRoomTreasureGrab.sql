create or replace TRIGGER "expCurRoomCheckTreasureGrab"
    BEFORE UPDATE OF expID ON TREASURES
    FOR EACH ROW
    DECLARE
        connection Explorers.room_ID%type;
    BEGIN

        IF :NEW.EXPID iS NULL THEN
            RETURN;
        END IF; 
        SELECT room_ID 
        INTO connection
        FROM EXPLORERS
        WHERE :New.EXPID = EXPLORERS.EXPID;

        IF connection IS NULL OR connection != :Old.roomid THEN
            RAISE_APPLICATION_ERROR(-20003, 'Item is not in same room as explorer');
        END IF;
    END;