package com.portal.enums;

public enum AuditOperationType {
	APPLICATION_START,
	LOGIN_FAIL,
	USER_DELETED,USER_INSERTED,USER_UPDATED,USER_LOGGED,
	PERSON_DELETED,PERSON_INSERTED,	PERSON_UPDATED,	
	ACCESS_LIST_DELETED, ACCESS_LIST_INSERTED, ACCESS_LIST_UPDATED,	ADDRESS_DELETED, ADDRESS_INSERTED, ADDRESS_UPDATED,
	CLASSIFIER_DELETED, CLASSIFIER_INSERTED, CLASSIFIER_UPDATED, CHECKPOINT_DELETED, CHECKPOINT_INSERTED, CHECKPOINT_UPDATED,
	PARAMETER_DELETED, PARAMETER_INSERTED, PARAMETER_UPDATED, 
	HOLDING_DELETED, HOLDING_INSERTED, HOLDING_UPDATED, 
	CUSTOMER_DELETED, CUSTOMER_INSERTED, CUSTOMER_UPDATED,
	MENU_DELETED, MENU_INSERTED, MENU_UPDATED,
	SALE_EXPORT, SALE_INSERTED, SALE_UPDATED, SALE_DELETED,	SALE_EDITED,
	PORTION_INSERTED, PORTION_EDITED, PORTION_DELETED, 
	BRAND_UPDATED, BRAND_DELETED, BRAND_INSERTED, 
	MODEL_UPDATED,  MODEL_DELETED, MODEL_INSERTED,
	PARTNER_GROUP_UPDATED, PARTNER_GROUP_DELETED, PARTNER_GROUP_INSERTED, 
	CHANNEL_UPDATED,  CHANNEL_DELETED,	CHANNEL_INSERTED,
	PERSON_TYPE_UPDATED, PERSON_TYPE_DELETED, PERSON_TYPE_INSERTED,
	BANK_UPDATED, BANK_DELETED,	BANK_INSERTED,
	PRODUCT_UPDATED, PRODUCT_DELETED, PRODUCT_INSERTED,
	PRODUCT_MODEL_UPDATED, PRODUCT_MODEL_DELETED, PRODUCT_MODEL_INSERTED,
	PRODUCT_PRICE_UPDATED, PRODUCT_PRICE_DELETED, PRODUCT_PRICE_INSERTED, 
	ITEM_TYPE_UPDATED, ITEM_TYPE_DELETED, ITEM_TYPE_INSERTED, 
	QUALIFICATION_UPDATED, QUALIFICATION_DELETED, QUALIFICATION_INSERTED, QUALIFICATION_MOVED,
	PAYMENT_METHOD_UPDATED, PAYMENT_METHOD_DELETED, PAYMENT_METHOD_INSERTED, PAYMENT_RULE_UPDATED,
	PAYMENT_RULE_DELETED, PAYMENT_RULE_INSERTED, PARTNER_UPDATED, PARTNER_DELETED, PARTNER_INSERTED,
	PRICE_LIST_UPDATED, PRICE_LIST_INSERTED, PRICE_LIST_DELETED, 
	BANK_ACCOUNT_INSERTED, BANK_ACCOUNT_DELETED, BANK_ACCOUNT_UPDATED,
	ITEM_INSERTED, ITEM_DELETED, ITEM_UPDATED, ITEM_MODEL_INSERTED, ITEM_MODEL_DELETED,	ITEM_MODEL_UPDATED,
	PRICE_ITEM_INSERTED, PRICE_ITEM_DELETED, PRICE_ITEM_UPDATED, PRICE_ITEM_MODEL_INSERTED,	PRICE_ITEM_MODEL_DELETED, PRICE_ITEM_MODEL_UPDATED,
	SOURCE_INSERTED, SOURCE_DELETED, SOURCE_UPDATED, 
	LEAD_INSERTED, LEAD_DELETED, LEAD_UPDATED,
	PROPOSAL_INSERTED, PROPOSAL_DELETED, PROPOSAL_UPDATED,
	PROPOSAL_DETAIL_INSERTED, PROPOSAL_DETAIL_DELETED, PROPOSAL_DETAIL_UPDATED,
	DOCUMENT_INSERTED, DOCUMENT_DELETED, DOCUMENT_UPDATED,
	PROPOSAL_DETAIL_VEHICLE_INSERTED, PROPOSAL_DETAIL_VEHICLE_DELETED, PROPOSAL_DETAIL_VEHICLE_UPDATED,
	VEHICLE_INSERTED, VEHICLE_DELETED, VEHICLE_UPDATED,
	PROPOSAL_PAYMENT_INSERTED, PROPOSAL_PAYMENT_DELETED, PROPOSAL_PAYMENT_UPDATED,
	PERSON_RELATED_INSERTED, PERSON_RELATED_DELETED, PERSON_RELATED_UPDATED,
	PARTNER_PERSON_COMMISSION_INSERTED, PARTNER_PERSON_COMMISSION_DELETED, PARTNER_PERSON_COMMISSION_UPDATED,
	TEAM_INSERTED, TEAM_UPDATED, TEAM_DELETED,
	TASK_INSERTED, TASK_UPDATED, TASK_DELETED,
	TASK_USER_INSERTED, TASK_USER_UPDATED, TASK_USER_DELETED,
	TASK_USER_TIME_INSERTED, TASK_USER_TIME_UPDATED, TASK_USER_TIME_DELETED,
	SERVICE_ORDER_INSERTED,	SERVICE_ORDER_UPDATED, SERVICE_ORDER_DELETED,
	CHECK_LIST_DELETED,	CHECK_LIST_INSERTED, CHECK_LIST_UPDATED, CHECK_LIST_GROUP_DELETED, CHECK_LIST_GROUP_UPDATED,
	CHECK_LIST_ANSWER_DELETED,	CHECK_LIST_ANSWER_INSERTED, CHECK_LIST_ANSWER_UPDATED,
	STAGE_DELETED, STAGE_INSERTED, STAGE_UPDATED, 
	STAGE_MOVEMENT_DELETED, STAGE_MOVEMENT_INSERTED, STAGE_MOVEMENT_UPDATED,
	CHECK_LIST_GROUP_INSERTED, CHECK_LIST_QUESTION_DELETED, CHECK_LIST_QUESTION_UPDATED, CHECK_LIST_QUESTION_INSERTED,
	JIRA_INTEGRATION_INSERTED, JIRA_INTEGRATION_CONSULT, JIRA_INTEGRATION__ERROR, JIRA_INTEGRATION_NOT_FOUND

}
