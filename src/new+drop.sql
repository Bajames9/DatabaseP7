
--Note, current code only works under the assumption there is a room named "START" which is the starting room.
--If the starting room is not named "START" then the code needs to be adjusted to reflect the intended starting room.

CREATE OR REPLACE PACKAGE GamePkg AS

    -- Holds the current explorer for THIS DB session
    current_exp_id Explorers.expID%TYPE := NULL;

    -- Creates a new explorer and sets it as current
    PROCEDURE new_explorer(
        p_exp_name   IN Explorers.name%TYPE,
        p_username   IN Explorers.username%TYPE
    );

    -- Drops a treasure from current explorer into room
    PROCEDURE drop_treasure(
        p_tres_id    IN Treasures.tresID%TYPE
    );

END GamePkg;
/


CREATE OR REPLACE PACKAGE BODY GamePkg AS

    --new
    PROCEDURE new_explorer(
        p_exp_name   IN Explorers.name%TYPE,
        p_username   IN Explorers.username%TYPE
    )
    IS
        v_room_id Rooms.roomID%TYPE;
        v_exp_id  Explorers.expID%TYPE;
    BEGIN
        -- Get starting room
        SELECT roomID
        INTO v_room_id
        FROM Rooms
        WHERE name = 'START';

        -- Generate new explorer ID
        v_exp_id := explorers_seq.NEXTVAL;

        -- Insert explorer
        INSERT INTO Explorers (expID, name, username, room_ID, bag_wt, bag_cnt)
        VALUES (v_exp_id, p_exp_name, p_username, v_room_id, 0, 0);

        -- Set current explorer for this session
        current_exp_id := v_exp_id;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20010, 'Starting room "START" does not exist.');
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(-20011, 'Explorer name already exists.');
    END new_explorer;


    --drop
    PROCEDURE drop_treasure(
        p_tres_id    IN Treasures.tresID%TYPE
    )
    IS
        v_room_id   Explorers.room_ID%TYPE;
        v_weight    Treasures.weight%TYPE;
        v_exists    NUMBER;
    BEGIN
        -- Ensure current explorer exists
        IF current_exp_id IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'No active explorer. Use NEW first.');
        END IF;

        -- Check if treasure belongs to current explorer
        SELECT COUNT(*)
        INTO v_exists
        FROM Treasures
        WHERE tresID = p_tres_id
          AND expID = current_exp_id;

        IF v_exists = 0 THEN
            RAISE_APPLICATION_ERROR(-20021, 'Treasure not found in your inventory.');
        END IF;

        -- Get explorer's current room
        SELECT room_ID
        INTO v_room_id
        FROM Explorers
        WHERE expID = current_exp_id;

        -- Get treasure weight
        SELECT weight
        INTO v_weight
        FROM Treasures
        WHERE tresID = p_tres_id;

        -- Move treasure to room
        UPDATE Treasures
        SET expID = NULL,
            npcID = NULL,
            roomID = v_room_id
        WHERE tresID = p_tres_id;

        -- Update explorer inventory stats
        UPDATE Explorers
        SET bag_cnt = bag_cnt - 1,
            bag_wt  = bag_wt - v_weight
        WHERE expID = current_exp_id;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20022, 'Data inconsistency detected.');
    END drop_treasure;

END GamePkg;
/
