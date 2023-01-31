ALTER TABLE `carbon_worklog`.`task_user` ADD COLUMN `stage_value` DECIMAL(10,0) NULL DEFAULT NULL AFTER `date_finish`;
ALTER TABLE `carbon_worklog`.`task_user` ADD COLUMN `recived_value` DECIMAL(10,0) NULL DEFAULT NULL AFTER `stage_value`;


DELIMITER $$
USE `carbon_worklog`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_task_payment`(IN pTaskID INT, IN pValueToPay DECIMAL(10,0) , IN pPaymentByTeam BOOL)
BEGIN

SET @numberOfUsersTask = (SELECT count(usr_id) FROM task_user WHERE tsk_id = pTaskID);

IF (pPaymentByTeam) THEN
   UPDATE `carbon_worklog`.`task_user` SET `stage_value` = pValueToPay, `recived_value` = pValueToPay/@numberOfUsersTask  WHERE `tsk_id` = pTaskID;
ELSE
   UPDATE `carbon_worklog`.`task_user` SET `stage_value` = pValueToPay, `recived_value` = pValueToPay  WHERE `tsk_id` = pTaskID;
END IF;

END$$

DELIMITER ;
;



