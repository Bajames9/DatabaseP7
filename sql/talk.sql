CREATE OR REPLACE PROCEDURE talk_to_npc(
    explorer_id IN NUMBER,
    npc_id_input IN NUMBER,
    message_text OUT VARCHAR2
)
IS
    explorer_room_id NUMBER;
    npc_room_id NUMBER;
    current_talk_count NUMBER;
    npc_name_text VARCHAR2(50);
    npc_job_text VARCHAR2(30);
    npc_description_text VARCHAR2(200);
BEGIN
    SELECT room_ID
    INTO explorer_room_id
    FROM Explorers
    WHERE expID = explorer_id;

    SELECT roomID, name, job, description
    INTO npc_room_id, npc_name_text, npc_job_text, npc_description_text
    FROM NPCS
    WHERE npcID = npc_id_input;

    IF explorer_room_id != npc_room_id THEN
        message_text := 'you are not in the same room as this npc';
        RETURN;
    END IF;

    BEGIN
        SELECT talk_count
        INTO current_talk_count
        FROM NPC_STATE
        WHERE expID = explorer_id
        AND npcID = npc_id_input;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            current_talk_count := 0;

            INSERT INTO NPC_STATE(
                expID,
                npcID,
                talk_count,
                last_state,
                last_talked_at
            )
            VALUES(
                explorer_id,
                npc_id_input,
                0,
                'not_met'
              --,SYSTIMESTAMP
            );
    END;

    current_talk_count := current_talk_count + 1;

    UPDATE NPC_STATE
    SET talk_count = current_talk_count,
        last_state = 'talked'
     --,last_talked_at = SYSTIMESTAMP
    WHERE expID = explorer_id
    AND npcID = npc_id_input;

    /* 
    need to change condition
    example jobs can be guard merchant

    first talk
    npc says hi explorer and shows description

    talk 2 to 9
    npc gives message based on job

    talk 10 or more
    npc gets annoyed
    */

    IF 1 = current_talk_count  THEN
        message_text := npc_name_text || ': hi explorer. ' || npc_description_text;
        
        IF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ': do some thing';

        ELSIF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ': I may have a quest for you.';

        ELSIF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ':  do i know what im doing'; 
    
    ELSIF 10 > current_talk_count  THEN

        ELSIF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ': do have any dead wish or what .';

         ELSIF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ': do you need help, if not complete the quest';

         ELSIF npc_job_text = 'npc_job' THEN
        message_text := npc_name_text || ': there alot thing to do';


    ELSIF current_talk_count >= 10 THEN
        message_text := npc_name_text || ': why are you still talking to me';

    ELSE
        message_text := npc_name_text || ': we already talked.';
    END IF;

END;
/