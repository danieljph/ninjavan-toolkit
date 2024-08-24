package com.karyasarma.toolkit.doku.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Daniel Joi Partogi Hutapea
 */
class LiquibaseYamlUtilsTest
{
    @Test
    void generateLiquibaseChangesetIdTest()
    {
        String liquibaseYaml = "databaseChangeLog:\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_00_initiate-tag\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - tagDatabase:\n" +
            "            tag: 1.0.0\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_01_createType_dispute_status_type\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE dispute_status_type AS ENUM ('SUCCESS', 'FAILED');\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE dispute_status_type CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_02_createType_feature\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"feature\" AS ENUM ('REGISTER', 'DIRECT_INQUIRY', 'MERCHANT_GENERATE_PAYMENT_CODE', 'DOKU_GENERATE_PAYMENT_CODE');\n" +
            "              COMMENT ON TYPE \"feature\" IS 'Enumeration for feature ';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"feature\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_03_createType_instruction_status\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"instruction_status\" AS ENUM ('ACTIVE', 'INACTIVE');\n" +
            "              COMMENT ON TYPE \"instruction_status\" IS 'Enum for identifying instruction status';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"instruction_status\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_04_createType_method\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"method\" AS ENUM ('AGGREGATOR_MERCHANT', 'DIRECT_MERCHANT');\n" +
            "              COMMENT ON TYPE \"method\" IS 'Enumeration for method';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"method\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_05_createType_state_trans\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"state_trans\" AS ENUM ('OPEN', 'PROCESS', 'DONE');\n" +
            "              COMMENT ON TYPE \"state_trans\" IS 'Enumeration for state of the process';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"state_trans\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_06_createType_status\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"status\" AS ENUM ('ACTIVE', 'INACTIVE', 'DELETE', 'NEW');\n" +
            "              COMMENT ON TYPE \"status\" IS 'Enumeration for status';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"status\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_07_createType_status_trans\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - sql:\n" +
            "            sql: >\n" +
            "              CREATE TYPE \"status_trans\" AS ENUM ('FAILED', 'SUCCESS', 'EXPIRED', 'USED', 'REFUND', 'VOID');\n" +
            "              COMMENT ON TYPE \"status_trans\" IS 'Enumeration for transaction status';\n" +
            "      rollback:\n" +
            "        - sql:\n" +
            "            sql: DROP TYPE \"status_trans\" CASCADE;\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_08_createTable_channel\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_channel_id\n" +
            "                  name: id\n" +
            "                  remarks: auto generated id as primary key\n" +
            "                  startWith: 9\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: name\n" +
            "                  remarks: identifying name of the channel\n" +
            "                  type: VARCHAR(64)\n" +
            "            remarks: Table for channel\n" +
            "            tableName: channel\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_09_createTable_merchant\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_merchant\n" +
            "                  name: client_id\n" +
            "                  remarks: for identifying merchant client id\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: name\n" +
            "                  remarks: for identifying merchant name\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: partner_client_id\n" +
            "                  remarks: for identifying merchant partner client id\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: partner_name\n" +
            "                  remarks: for identifying merchant partner name\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: business_client_id\n" +
            "                  remarks: for identifying merchant business client id\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: company_code\n" +
            "                  remarks: for identifying merchant company code\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: shared_key\n" +
            "                  remarks: for identifying merchant shared key\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: inquiry_url\n" +
            "                  remarks: for identifying merchant inquiry url\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: payment_url\n" +
            "                  remarks: for identifying merchant payment url\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: created_date\n" +
            "                  remarks: created date for the date when this merchant was created\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: updated_date\n" +
            "                  remarks: updated date for the date when this merchant was updated\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: expired_time\n" +
            "                  remarks: for identifying merchant default expired time for transaction\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: method\n" +
            "                  remarks: for identifying the method the merchant use\n" +
            "                  type: METHOD\n" +
            "              - column:\n" +
            "                  name: feature\n" +
            "                  remarks: for identifying the feature the merchant use\n" +
            "                  type: FEATURE\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for identifying the of the merchant\n" +
            "                  type: STATUS\n" +
            "              - column:\n" +
            "                  name: encrypt_status\n" +
            "                  remarks: for store merchant encrypt status\n" +
            "                  type: BOOLEAN\n" +
            "              - column:\n" +
            "                  defaultValueBoolean: false\n" +
            "                  name: migration_process\n" +
            "                  remarks: Is flagging the merchant is in the process of migration\n" +
            "                  type: BOOLEAN\n" +
            "            remarks: Table for accommodate merchant data\n" +
            "            tableName: merchant\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_10_createTable_register\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_register\n" +
            "                  name: id\n" +
            "                  remarks: for register transaction id\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "                  remarks: for client id of the register\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: company_code\n" +
            "                  remarks: for company code of the register\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "                  remarks: for virtual account number of the register\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "                  remarks: for invoice number of the register\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: reusable_status\n" +
            "                  remarks: whether the register can be reuse or not\n" +
            "                  type: BOOLEAN\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for the status of the register\n" +
            "                  type: STATUS\n" +
            "              - column:\n" +
            "                  name: amount\n" +
            "                  remarks: for amount of the register\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: max_amount\n" +
            "                  remarks: for maximal amount of the register\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: min_amount\n" +
            "                  remarks: for minimum amount of the register\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: currency\n" +
            "                  remarks: currency code of amount using for register\n" +
            "                  type: VARCHAR(3)\n" +
            "              - column:\n" +
            "                  name: payment_number\n" +
            "                  remarks: for payment number of the register\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: customer_name\n" +
            "                  remarks: for customer name of the register\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: customer_email\n" +
            "                  remarks: for customer email of the register\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: customer_phone\n" +
            "                  remarks: for customer phone of the register\n" +
            "                  type: VARCHAR(16)\n" +
            "              - column:\n" +
            "                  name: partner_client_id\n" +
            "                  remarks: for client id of partner which relate to merchant client id of\n" +
            "                    register\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: partner_name\n" +
            "                  remarks: for partner name which using partner_client_id\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: info1\n" +
            "                  remarks: contains additional register data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info2\n" +
            "                  remarks: contains additional register data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info3\n" +
            "                  remarks: contains additional register data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "                  remarks: for the uuid of the register\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: created_date\n" +
            "                  remarks: created date for the date when this register was created\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: expired_date\n" +
            "                  remarks: expired date for the date when this register will be expired\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: updated_date\n" +
            "                  remarks: updated date for the date when this register was updated\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "                  remarks: for request id of the register\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: additional_info\n" +
            "                  remarks: for identifying register additional info\n" +
            "                  type: VARCHAR(4096)\n" +
            "              - column:\n" +
            "                  name: feature\n" +
            "                  remarks: for identify feature data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: billing_type\n" +
            "                  remarks: for identify billing type data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: risk_engine_screening_type\n" +
            "                  remarks: Risk Engine Screening Type for this register\n" +
            "                  type: VARCHAR(30)\n" +
            "              - column:\n" +
            "                  name: risk_engine_status\n" +
            "                  remarks: Risk Engine Status for this register\n" +
            "                  type: VARCHAR(30)\n" +
            "              - column:\n" +
            "                  name: risk_engine_reason\n" +
            "                  remarks: Risk Engine Reason for this register\n" +
            "                  type: VARCHAR(1024)\n" +
            "            remarks: Table for accommodate register transaction data\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_11_createTable_identifier\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_identifier_id\n" +
            "                  name: id\n" +
            "                  remarks: for identifier id\n" +
            "                  startWith: 15\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "                  remarks: for client id of the identifier\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: name\n" +
            "                  remarks: for name of the identifier\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: value\n" +
            "                  remarks: for value of the identifier\n" +
            "                  type: VARCHAR(512)\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for status of the identifier\n" +
            "                  type: STATUS\n" +
            "              - column:\n" +
            "                  name: feature\n" +
            "                  remarks: for identify feature data.\n" +
            "                  type: VARCHAR(32)\n" +
            "            remarks: Table for accommodate identifier merchant\n" +
            "            tableName: identifier\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_12_createTable_inquiry\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_inquiry\n" +
            "                  name: id\n" +
            "                  remarks: for inquiry transaction id\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "                  remarks: for client id of the inquiry\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: register_id\n" +
            "                  remarks: for register id of the inquiry\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: transaction_no\n" +
            "                  remarks: for transaction no of the inquiry\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: company_code\n" +
            "                  remarks: for company code of the inquiry\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "                  remarks: for virtual account number of the inquiry\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "                  remarks: for invoice number of the inquiry\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: payment_number\n" +
            "                  remarks: for payment number of the inquiry\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: amount\n" +
            "                  remarks: for amount of the inquiry\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: max_amount\n" +
            "                  remarks: for maximal amount of the inquiry\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: min_amount\n" +
            "                  remarks: for minimum amount of the inquiry\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: trace_no\n" +
            "                  remarks: for system trace number of inquiry\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: currency\n" +
            "                  remarks: currency code of amount using for inquiry\n" +
            "                  type: VARCHAR(3)\n" +
            "              - column:\n" +
            "                  name: datetime\n" +
            "                  remarks: for datetime request from acquirer\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: created_date\n" +
            "                  remarks: created date for the date when this inquiry was created\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: expired_date\n" +
            "                  remarks: expired date for the date when this inquiry will be expired\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: updated_date\n" +
            "                  remarks: updated date for the date when this inquiry was updated\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: response_message\n" +
            "                  remarks: for the response message of the inquiry\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  defaultValueBoolean: true\n" +
            "                  name: reusable_status\n" +
            "                  remarks: for the reusable status of the inquiry\n" +
            "                  type: BOOLEAN\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for the status of the inquiry\n" +
            "                  type: STATUS_TRANS\n" +
            "              - column:\n" +
            "                  name: customer_name\n" +
            "                  remarks: for customer name of the inquiry\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: customer_email\n" +
            "                  remarks: for customer email of the inquiry\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: customer_phone\n" +
            "                  remarks: for customer phone of the inquiry\n" +
            "                  type: VARCHAR(16)\n" +
            "              - column:\n" +
            "                  name: partner_client_id\n" +
            "                  remarks: for client id of partner which relate to merchant client id of\n" +
            "                    inquiry\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: partner_name\n" +
            "                  remarks: for partner name which using partner_client_id\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: info1\n" +
            "                  remarks: contains additional inquiry data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info2\n" +
            "                  remarks: contains additional inquiry data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info3\n" +
            "                  remarks: contains additional inquiry data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: register_uuid\n" +
            "                  remarks: for the register uuid of the inquiry\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "                  remarks: for the uuid of the inquiry\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "                  remarks: for request id of the inquiry\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: additional_info\n" +
            "                  remarks: for identifying inquiry additional info\n" +
            "                  type: VARCHAR(4096)\n" +
            "              - column:\n" +
            "                  name: feature\n" +
            "                  remarks: for identify feature data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: billing_type\n" +
            "                  remarks: for identify billing type data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: risk_engine_status\n" +
            "                  remarks: Risk Engine Status for this inquiry\n" +
            "                  type: VARCHAR(50)\n" +
            "              - column:\n" +
            "                  name: risk_engine_screening_type\n" +
            "                  remarks: Risk Engine Screening Type for this inquiry\n" +
            "                  type: VARCHAR(30)\n" +
            "              - column:\n" +
            "                  name: risk_engine_reason\n" +
            "                  remarks: Risk Engine Reason for this inquiry\n" +
            "                  type: VARCHAR(1024)\n" +
            "            remarks: Table for accommodate inquiry transaction data\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_13_createTable_payment\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_payment\n" +
            "                  name: id\n" +
            "                  remarks: for payment transaction id\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "                  remarks: for client id of the payment\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: register_id\n" +
            "                  remarks: for the register id of the payment\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: inquiry_id\n" +
            "                  remarks: for register id of the payment\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: transaction_no\n" +
            "                  remarks: for the transaction number of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: company_code\n" +
            "                  remarks: for company code of the payment\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "                  remarks: for virtual account number of the payment\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "                  remarks: for invoice number of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: payment_number\n" +
            "                  remarks: for payment number of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: trace_no\n" +
            "                  remarks: for the trace number of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: host_refnum\n" +
            "                  remarks: for the hostref number of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: amount\n" +
            "                  remarks: for amount of the payment\n" +
            "                  type: numeric(14)\n" +
            "              - column:\n" +
            "                  name: currency\n" +
            "                  remarks: currency code of amount using for payment\n" +
            "                  type: VARCHAR(3)\n" +
            "              - column:\n" +
            "                  name: created_date\n" +
            "                  remarks: created date for the date when this payment was created\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: updated_date\n" +
            "                  remarks: for the updated date of the payment\n" +
            "                  type: TIMESTAMP WITHOUT TIME ZONE\n" +
            "              - column:\n" +
            "                  name: response_message\n" +
            "                  remarks: for the response message of the payment\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for the status of the payment\n" +
            "                  type: STATUS_TRANS\n" +
            "              - column:\n" +
            "                  name: customer_name\n" +
            "                  remarks: for customer name of the payment\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: customer_email\n" +
            "                  remarks: for customer email of the payment\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: partner_client_id\n" +
            "                  remarks: for client id of partner which relate to merchant client id of\n" +
            "                    payment\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: partner_name\n" +
            "                  remarks: for partner name which using partner_client_id\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: info1\n" +
            "                  remarks: contains additional payment data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info2\n" +
            "                  remarks: contains additional payment data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: info3\n" +
            "                  remarks: contains additional payment data information provide by merchant\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: register_uuid\n" +
            "                  remarks: for the register uuid of the payment\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: inquiry_uuid\n" +
            "                  remarks: for the inquiry uuid of the payment\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "                  remarks: for the uuid of the payment\n" +
            "                  type: numeric(40)\n" +
            "              - column:\n" +
            "                  name: channel_id\n" +
            "                  remarks: for the channel id of the payment\n" +
            "                  type: VARCHAR(8)\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "                  remarks: for request id of the payment\n" +
            "                  type: VARCHAR(128)\n" +
            "              - column:\n" +
            "                  name: additional_info\n" +
            "                  remarks: for identifying payment additional info\n" +
            "                  type: VARCHAR(4096)\n" +
            "              - column:\n" +
            "                  name: notification_center_header\n" +
            "                  remarks: for store header request notification center.\n" +
            "                  type: VARCHAR(1024)\n" +
            "              - column:\n" +
            "                  name: notification_center_body\n" +
            "                  remarks: for store parameter request notification center.\n" +
            "                  type: VARCHAR(8192)\n" +
            "              - column:\n" +
            "                  name: feature\n" +
            "                  remarks: for identify feature data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: billing_type\n" +
            "                  remarks: for identify billing type data.\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: dispute_id\n" +
            "                  remarks: Dispute Id for this payment\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: transaction_date\n" +
            "                  remarks: Payment date time from payment request\n" +
            "                  type: TIMESTAMP WITH TIME ZONE\n" +
            "              - column:\n" +
            "                  name: risk_engine_screening_type\n" +
            "                  remarks: Risk Engine Screening Type for this payment\n" +
            "                  type: VARCHAR(30)\n" +
            "              - column:\n" +
            "                  name: risk_engine_status\n" +
            "                  remarks: Risk Engine Status for this payment\n" +
            "                  type: VARCHAR(30)\n" +
            "              - column:\n" +
            "                  name: risk_engine_reason\n" +
            "                  remarks: Risk Engine Reason for this payment\n" +
            "                  type: VARCHAR(1024)\n" +
            "            remarks: Table for accommodate payment transaction data\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_14_createTable_payment_instruction\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_payment_instruction_id\n" +
            "                  name: id\n" +
            "                  remarks: auto generated id as primary key\n" +
            "                  startWith: 87\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: channel_id\n" +
            "                  remarks: identifying channel id of the payment instruction\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: language_id\n" +
            "                  remarks: identifying language id of the payment instruction\n" +
            "                  type: CHAR(2)\n" +
            "              - column:\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                  name: step_number\n" +
            "                  remarks: identifying step number of the payment instruction\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: step\n" +
            "                  remarks: identifying step of the payment instruction\n" +
            "                  type: VARCHAR(256)\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: identifying status of the payment instruction\n" +
            "                  type: INSTRUCTION_STATUS\n" +
            "            remarks: Table for payment instruction\n" +
            "            tableName: payment_instruction\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_15_createTable_bank_support\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                  name: id\n" +
            "                  remarks: for bank support id\n" +
            "                  startWith: 37\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: name\n" +
            "                  remarks: for bank support name\n" +
            "                  type: VARCHAR\n" +
            "              - column:\n" +
            "                  name: channel_id\n" +
            "                  remarks: for bank support channel id\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: for bank support status\n" +
            "                  type: INSTRUCTION_STATUS\n" +
            "            remarks: Table for accommodate bank support payment data\n" +
            "            tableName: bank_support\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_16_createTable_dispute\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  autoIncrement: true\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_dispute\n" +
            "                  name: id\n" +
            "                  remarks: Serial for dispute\n" +
            "                  type: INTEGER\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "                  remarks: client id from merchant\n" +
            "                  type: VARCHAR(22)\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "                  remarks: status for this dispute\n" +
            "                  type: DISPUTE_STATUS_TYPE\n" +
            "              - column:\n" +
            "                  name: dispute_id\n" +
            "                  remarks: Id for this dispute\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: reason\n" +
            "                  remarks: reason if dispute status failed\n" +
            "                  type: VARCHAR(250)\n" +
            "              - column:\n" +
            "                  name: initiate_date\n" +
            "                  remarks: Initiate date for this dispute\n" +
            "                  type: TIMESTAMP WITH TIME ZONE\n" +
            "              - column:\n" +
            "                  name: type\n" +
            "                  remarks: Type for this dispute\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: action\n" +
            "                  remarks: Action for this dispute\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: amount\n" +
            "                  remarks: Amount for this dispute\n" +
            "                  type: numeric(12)\n" +
            "              - column:\n" +
            "                  name: reconcile_amount\n" +
            "                  remarks: Reconcile Amount for this dispute\n" +
            "                  type: numeric(12)\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "                  remarks: Virtual Account Number for this dispute\n" +
            "                  type: VARCHAR(32)\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "                  remarks: Invoice Number for this dispute\n" +
            "                  type: VARCHAR(64)\n" +
            "            tableName: dispute\n" +
            "\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_17_createIndex_register_merchant_request_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "            indexName: register_merchant_request_idx\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_18_createIndex_register_invoice_merchant_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "            indexName: register_invoice_merchant_idx\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_19_createIndex_register_vanumber_status_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "            indexName: register_vanumber_status_idx\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_20_createIndex_register_uuid_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "            indexName: register_uuid_idx\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_21_createIndex_register_created_date_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: created_date\n" +
            "            indexName: register_created_date_idx\n" +
            "            tableName: register\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_22_createIndex_inquiry_vanumber_status_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "            indexName: inquiry_vanumber_status_idx\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_23_createIndex_inquiry_registerid_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: register_id\n" +
            "            indexName: inquiry_registerid_idx\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_24_createIndex_inquiry_clientrequestid_status_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "            indexName: inquiry_clientrequestid_status_idx\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_25_createIndex_inquiry_invoice_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "            indexName: inquiry_invoice_idx\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_26_createIndex_inquiry_uuid_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "            indexName: inquiry_uuid_idx\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_27_createIndex_payment_vanumber_status_idx\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: virtual_account_number\n" +
            "              - column:\n" +
            "                  name: status\n" +
            "            indexName: payment_vanumber_status_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_28_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: client_id\n" +
            "              - column:\n" +
            "                  name: request_id\n" +
            "            indexName: payment_clientrequestid_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_29_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: register_id\n" +
            "            indexName: payment_registerid_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_30_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: inquiry_id\n" +
            "            indexName: payment_inquiryid_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_31_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: invoice_number\n" +
            "            indexName: payment_invoice_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_32_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: uuid\n" +
            "            indexName: payment_uuid_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_33_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: transaction_date\n" +
            "            indexName: payment_transaction_date_idx\n" +
            "            tableName: payment\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_34_createIndex_\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createIndex:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  name: initiate_date\n" +
            "            indexName: dispute_initdate_idx\n" +
            "            tableName: dispute\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_35\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_12346\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_36\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_12349\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_37\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_700000000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_38\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_770000001\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_39\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_770000002\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_40\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_770000003\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_41\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_770000004\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_42\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_788888888\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_43\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_788888889\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_44\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8000000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_45\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8123456\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_46\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8600000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_47\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8610000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_48\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8630000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_49\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8700000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_50\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8800001\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_51\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8800002\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_52\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8800003\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_53\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_8998\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_54\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9000\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_55\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9875\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_56\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 9223372036854775807\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9876\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_57\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9901\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_58\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9902\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_59\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9903\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_60\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createSequence:\n" +
            "            cacheSize: 1\n" +
            "            cycle: true\n" +
            "            dataType: bigint\n" +
            "            incrementBy: 1\n" +
            "            maxValue: 999990\n" +
            "            minValue: 1\n" +
            "            sequenceName: seq_paycode_9904\n" +
            "            startValue: 2\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_61\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - createTable:\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  constraints:\n" +
            "                    nullable: false\n" +
            "                    primaryKey: true\n" +
            "                    primaryKeyName: pk_language_id\n" +
            "                  name: id\n" +
            "                  remarks: identifying id of the language\n" +
            "                  type: CHAR(2)\n" +
            "              - column:\n" +
            "                  name: name\n" +
            "                  remarks: identifying name of the language\n" +
            "                  type: VARCHAR(64)\n" +
            "              - column:\n" +
            "                  name: character_encode\n" +
            "                  remarks: identifying character encode of the language\n" +
            "                  type: VARCHAR(10)\n" +
            "            remarks: Table for language\n" +
            "            tableName: language\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_62\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: client_id\n" +
            "            baseTableName: dispute\n" +
            "            constraintName: dispute_merchant_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: client_id\n" +
            "            referencedTableName: merchant\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_63\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: client_id\n" +
            "            baseTableName: identifier\n" +
            "            constraintName: fk_identifier_client_id\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: client_id\n" +
            "            referencedTableName: merchant\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_64\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: client_id\n" +
            "            baseTableName: inquiry\n" +
            "            constraintName: inquiry_merchant_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: client_id\n" +
            "            referencedTableName: merchant\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_65\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: register_id\n" +
            "            baseTableName: inquiry\n" +
            "            constraintName: inquiry_register_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: id\n" +
            "            referencedTableName: register\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_66\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: dispute_id\n" +
            "            baseTableName: payment\n" +
            "            constraintName: payment_dispute_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: id\n" +
            "            referencedTableName: dispute\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_67\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: inquiry_id\n" +
            "            baseTableName: payment\n" +
            "            constraintName: payment_inquiry_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: id\n" +
            "            referencedTableName: inquiry\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_68\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: channel_id\n" +
            "            baseTableName: payment_instruction\n" +
            "            constraintName: payment_instruction_channel_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: id\n" +
            "            referencedTableName: channel\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_69\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: language_id\n" +
            "            baseTableName: payment_instruction\n" +
            "            constraintName: payment_instruction_language_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: id\n" +
            "            referencedTableName: language\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_70\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: client_id\n" +
            "            baseTableName: payment\n" +
            "            constraintName: payment_merchant_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: client_id\n" +
            "            referencedTableName: merchant\n" +
            "            validate: true\n" +
            "  - changeSet:\n" +
            "      id: 1.0.0_71\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addForeignKeyConstraint:\n" +
            "            baseColumnNames: client_id\n" +
            "            baseTableName: register\n" +
            "            constraintName: register_merchant_fkey\n" +
            "            deferrable: false\n" +
            "            initiallyDeferred: false\n" +
            "            onDelete: NO ACTION\n" +
            "            onUpdate: NO ACTION\n" +
            "            referencedColumnNames: client_id\n" +
            "            referencedTableName: merchant\n" +
            "            validate: true\n" +
            "\n" +
            "  - changeSet:\n" +
            "      id: 1.4.0_11_modifyDataType_inquiry_additional_info_setType_varchar4096\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - modifyDataType:\n" +
            "            columnName: additional_info\n" +
            "            newDataType: VARCHAR(4096)\n" +
            "            tableName: inquiry\n" +
            "      rollback:\n" +
            "        - modifyDataType:\n" +
            "            columnName: additional_info\n" +
            "            newDataType: VARCHAR(2048)\n" +
            "            tableName: inquiry\n" +
            "  - changeSet:\n" +
            "      id: 1.4.0_12_modifyDataType_payment_additional_info_setType_varchar4096\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - modifyDataType:\n" +
            "            columnName: additional_info\n" +
            "            newDataType: VARCHAR (     4096 )\n" +
            "            tableName: payment\n" +
            "      rollback:\n" +
            "        - modifyDataType:\n" +
            "            columnName: additional_info\n" +
            "            newDataType: VARCHAR(2048)\n" +
            "            tableName: payment\n" +
            "\n" +
            "  - changeSet:\n" +
            "      id: addColumn-example\n" +
            "      author: daniel\n" +
            "      changes:\n" +
            "        - addColumn:\n" +
            "            tableName: register\n" +
            "            columns:\n" +
            "              - column:\n" +
            "                  defaultValueBoolean: false\n" +
            "                  name: is_migrated\n" +
            "                  remarks: Flagging for migrated register\n" +
            "                  type: BOOLEAN\n";

        String result = LiquibaseYamlUtils.generateLiquibaseChangesetId(liquibaseYaml);
        System.out.println(result);
        assertNotNull(result);
    }
}
