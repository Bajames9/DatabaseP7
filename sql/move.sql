-- Enable DBMS Output to see fail Pass MSG
CREATE OR REPLACE TRIGGER explorerUpdate
    BEFORE UPDATE OF ROOM_ID ON EXPLORERS
    FOR EACH ROW
    DECLARE
        connection  NUMBER;
    BEGIN

        SELECT COUNT(*) INTO connection
        FROM CONNECTIONS
        WHERE ROOM1 = :OLD.ROOM_ID and ROOM2 = :New.ROOM_ID;


        IF connection = 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'connection does not exists');
        END IF;
    END;






CREATE OR REPLACE PROCEDURE move
(
        ExplorerID IN EXPLORERS.EXPID%type,
        RoomID IN ROOMS.ROOMID%type
) IS
        currentRoom ROOMS.ROOMID%TYPE;
        con_does_not_exsit_error EXCEPTION;
        PRAGMA EXCEPTION_INIT(con_does_not_exsit_error, -20002);

BEGIN

    SELECT room_ID INTO currentRoom
    FROM EXPLORERS
    WHERE EXPID = ExplorerID;

    Update EXPLORERS
    SET ROOM_ID = RoomID
    WHERE EXPID = ExplorerID;

    DBMS_OUTPUT.PUT_LINE( 'PASS – Exp ID:' ||ExplorerID|| ', Curr Room: '||currentRoom||', New Room: '||RoomID);


    EXCEPTION
        WHEN con_does_not_exsit_error THEN
        ROLLBACK;
            DBMS_OUTPUT.PUT_LINE( 'FAIL – Exp ID:' ||ExplorerID|| ', Curr Room: '||currentRoom||', New Room: '||RoomID);
            DBMS_OUTPUT.PUT_LINE('Error: room connection does not exist');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('FAIL – Exp ID:' ||ExplorerID|| ', Curr Room: '||currentRoom||', New Room: '||RoomID);


END;



CREATE OR REPLACE PROCEDURE show_conns(p_explorer_id IN NUMBER)
IS
    v_room_id NUMBER;
BEGIN
    -- find current room
    SELECT room_ID
    INTO v_room_id
    FROM Explorers
    WHERE expID = p_explorer_id;

    -- loop to find both connected rooms
    FOR conn IN (
        SELECT
            CASE
                WHEN room1 = v_room_id THEN room2
                ELSE room1
            END AS connected_room
        FROM Connections
        WHERE room1 = v_room_id OR room2 = v_room_id
    )
    LOOP
        DBMS_OUTPUT.PUT_LINE('Connected Room ID: ' || conn.connected_room);
    END LOOP;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Explorer not found.');
END;





