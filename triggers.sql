

delimiter //
DROP TRIGGER IF EXISTS add_points_answer;
CREATE TRIGGER add_points_answer
AFTER INSERT
ON answer FOR EACH ROW
BEGIN
    IF EXISTS (SELECT * FROM questionnaire WHERE id=NEW.questionnaire AND iscanceled=0) THEN
        UPDATE usertable
			SET points = points + 1
		WHERE id=(SELECT user FROM questionnaire WHERE id=NEW.questionnaire AND iscanceled=0);
    END IF;
END;

delimiter //
DROP TRIGGER IF EXISTS add_points_userdata;
CREATE TRIGGER add_points_userdata
AFTER INSERT
ON userdata FOR EACH ROW
BEGIN
    IF EXISTS (SELECT * FROM questionnaire WHERE id=NEW.questionnaire AND iscanceled=0) THEN
        UPDATE usertable
			SET points = points + 2*(CAST(NEW.answer1!='' AS SIGNED INTEGER)+CAST(NEW.answer2!='' AS SIGNED INTEGER)+CAST(NEW.answer3!='' AS SIGNED INTEGER))
		WHERE id=(SELECT user FROM questionnaire WHERE id=NEW.questionnaire AND iscanceled=0);
    END IF;
END;

delimiter //
DROP TRIGGER IF EXISTS subtract_points_answer;
CREATE TRIGGER subtract_points_answer
AFTER DELETE
ON answer FOR EACH ROW
BEGIN
    IF EXISTS (SELECT * FROM questionnaire WHERE id=OLD.questionnaire AND iscanceled=0) THEN
        UPDATE usertable
			SET points = points - 1
		WHERE id=(SELECT user FROM questionnaire WHERE id=OLD.questionnaire AND iscanceled=0);
    END IF;
END;

delimiter //
DROP TRIGGER IF EXISTS subtract_points_userdata;
CREATE TRIGGER subtract_points_userdata
AFTER DELETE
ON userdata FOR EACH ROW
BEGIN
    IF EXISTS (SELECT * FROM questionnaire WHERE id=OLD.questionnaire AND iscanceled=0) THEN
        UPDATE usertable
			SET points = points - 2*(CAST(OLD.answer1!='' AS SIGNED INTEGER)+CAST(OLD.answer2!='' AS SIGNED INTEGER)+CAST(OLD.answer3!='' AS SIGNED INTEGER))
		WHERE id=(SELECT user FROM questionnaire WHERE id=OLD.questionnaire AND iscanceled=0);
    END IF;
END;

