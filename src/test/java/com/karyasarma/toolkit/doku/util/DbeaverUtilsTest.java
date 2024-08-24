package com.karyasarma.toolkit.doku.util;

import org.junit.jupiter.api.Test;

import static com.karyasarma.toolkit.doku.util.DbeaverUtils.fromCopyAsJsonToText;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Daniel Joi Partogi Hutapea
 */
class DbeaverUtilsTest
{
    @Test
    void fromCopyAsJsonToTextTest()
    {
        String rows = "{\n" +
            "\"register\": [\n" +
            "\t{\n" +
            "\t\t\"id\" : 575533,\n" +
            "\t\t\"reusable_status\" : true,\n" +
            "\t\t\"amount\" : 0.00,\n" +
            "\t\t\"min_amount\" : null,\n" +
            "\t\t\"max_amount\" : null,\n" +
            "\t\t\"acquirer_trx_id\" : \"VA004T1724410692157TKC4ZIUOZKr\",\n" +
            "\t\t\"transaction_settings\" : \"{\\\"source\\\": \\\"H2H\\\", \\\"settings\\\": {\\\"partnerServiceId\\\": \\\"23981\\\"}, \\\"apiFormat\\\": \\\"SNAP\\\", \\\"apiVersion\\\": \\\"1.0\\\"}\",\n" +
            "\t\t\"additional_info\" : \"{\\\"channel\\\": \\\"VIRTUAL_ACCOUNT_BNI\\\", \\\"billDetails\\\": [{\\\"billNo\\\": \\\"1724410691\\\", \\\"billCode\\\": \\\"01\\\", \\\"billName\\\": \\\"Bill A for Jan\\\", \\\"billAmount\\\": {\\\"value\\\": \\\"10000.00\\\", \\\"currency\\\": \\\"IDR\\\"}, \\\"billShortName\\\": \\\"Bill A\\\", \\\"additionalInfo\\\": {}, \\\"billSubCompany\\\": \\\"00001\\\", \\\"billDescription\\\": {\\\"english\\\": \\\"Maintenance\\\", \\\"indonesia\\\": \\\"Pemeliharaan\\\"}}], \\\"virtualAccountConfig\\\": {\\\"reusableStatus\\\": false}, \\\"info1FromMerchantCreateVa\\\": \\\"Info 1 from Merchant Create VA\\\"}\",\n" +
            "\t\t\"client_id\" : \"BRN-0206-1710295749973\",\n" +
            "\t\t\"uuid\" : 2226240823175812151107118729748021926050,\n" +
            "\t\t\"request_id\" : \"RID_C_20240823175811885\",\n" +
            "\t\t\"invoice_number\" : \"INV_BNI_20240823175811\",\n" +
            "\t\t\"company_code\" : \"23981\",\n" +
            "\t\t\"company_name\" : null,\n" +
            "\t\t\"virtual_account_number\" : \"2398117244106918\",\n" +
            "\t\t\"payment_number\" : \"17244106918\",\n" +
            "\t\t\"customer_name\" : \"C_1724410691\",\n" +
            "\t\t\"customer_email\" : \"test.bni.1724410691@test.com\",\n" +
            "\t\t\"partner_name\" : \"DOKU\",\n" +
            "\t\t\"partner_client_id\" : null,\n" +
            "\t\t\"currency\" : \"IDR\",\n" +
            "\t\t\"billing_type\" : \"NO_BILL\",\n" +
            "\t\t\"feature\" : \"AMGPC\",\n" +
            "\t\t\"status\" : \"ACTIVE\",\n" +
            "\t\t\"created_date\" : \"2024-08-23T10:58:12.151Z\",\n" +
            "\t\t\"expired_date\" : \"2024-08-23T11:58:11.000Z\",\n" +
            "\t\t\"updated_date\" : null,\n" +
            "\t\t\"merchant_acquirer_id\" : 431,\n" +
            "\t\t\"channel_id\" : \"VIRTUAL_ACCOUNT_BNI\",\n" +
            "\t\t\"service_id\" : \"VIRTUAL_ACCOUNT\",\n" +
            "\t\t\"acquirer_id\" : \"BNI\",\n" +
            "\t\t\"customer_phone\" : \"6281724410691\",\n" +
            "\t\t\"free_text\" : \"[{\\\"en\\\": \\\"Free text 1724410691885\\\", \\\"id\\\": \\\"Tulisan bebas 1724410691885\\\"}]\",\n" +
            "\t\t\"risk_engine_screening_type\" : null,\n" +
            "\t\t\"risk_engine_status\" : null,\n" +
            "\t\t\"risk_engine_reason\" : \"{\\n  \\\"source\\\" : \\\"H2H\\\",\\n  \\\"settings\\\" : {\\n    \\\"partnerServiceId\\\" : \\\"23981\\\"\\n  },\\n  \\\"apiFormat\\\" : \\\"SNAP\\\",\\n  \\\"apiVersion\\\" : \\\"1.0\\\"\\n}\",\n" +
            "\t\t\"acquirer_response_code\" : null,\n" +
            "\t\t\"acquirer_response_message\" : null,\n" +
            "\t\t\"promo\" : null,\n" +
            "\t\t\"amount_details\" : null,\n" +
            "\t\t\"paid_amount\" : 0.00,\n" +
            "\t\t\"discount_amount\" : 0.00,\n" +
            "\t\t\"migration_id\" : null,\n" +
            "\t\t\"channel_code\" : \"H2H\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"id\" : 575532,\n" +
            "\t\t\"reusable_status\" : false,\n" +
            "\t\t\"amount\" : 12500.00,\n" +
            "\t\t\"min_amount\" : null,\n" +
            "\t\t\"max_amount\" : null,\n" +
            "\t\t\"acquirer_trx_id\" : \"VA004T1724410494270T2bRSDpm92v\",\n" +
            "\t\t\"transaction_settings\" : \"{\\\"source\\\": \\\"H2H\\\", \\\"settings\\\": {\\\"partnerServiceId\\\": \\\"98829172\\\"}, \\\"apiFormat\\\": \\\"SNAP\\\", \\\"apiVersion\\\": \\\"1.0\\\"}\",\n" +
            "\t\t\"additional_info\" : \"{\\\"channel\\\": \\\"VIRTUAL_ACCOUNT_BNI\\\", \\\"description\\\": \\\"Used on flow BNI-Non-SNAP 1724410494\\\", \\\"promoDeleted\\\": {\\\"list\\\": [{\\\"code\\\": \\\"DJPH01\\\", \\\"reserveId\\\": \\\"bd2f4b7b-83d0-407f-bc18-9eb3b5\\\"}], \\\"applicationId\\\": \\\"PG\\\"}, \\\"virtualAccountConfig\\\": {\\\"reusableStatus\\\": true}, \\\"info1FromMerchantCreateVa\\\": \\\"Info 1 from Merchant Create VA Longeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer\\\"}\",\n" +
            "\t\t\"client_id\" : \"BRN-0206-1710295749973\",\n" +
            "\t\t\"uuid\" : 2226240823175454262107118729792021877565,\n" +
            "\t\t\"request_id\" : \"RID_C_20240823175454016\",\n" +
            "\t\t\"invoice_number\" : \"INV_BNI_20240823175454\",\n" +
            "\t\t\"company_code\" : \"98829172\",\n" +
            "\t\t\"company_name\" : null,\n" +
            "\t\t\"virtual_account_number\" : \"9882917200000086\",\n" +
            "\t\t\"payment_number\" : \"00000086\",\n" +
            "\t\t\"customer_name\" : \"C_1724410494\",\n" +
            "\t\t\"customer_email\" : \"test.bni.1724410494@test.com\",\n" +
            "\t\t\"partner_name\" : \"DOKU\",\n" +
            "\t\t\"partner_client_id\" : null,\n" +
            "\t\t\"currency\" : \"IDR\",\n" +
            "\t\t\"billing_type\" : \"FIX_BILL\",\n" +
            "\t\t\"feature\" : \"ADGPC\",\n" +
            "\t\t\"status\" : \"ACTIVE\",\n" +
            "\t\t\"created_date\" : \"2024-08-23T10:54:54.262Z\",\n" +
            "\t\t\"expired_date\" : \"2024-08-23T11:54:54.000Z\",\n" +
            "\t\t\"updated_date\" : null,\n" +
            "\t\t\"merchant_acquirer_id\" : 431,\n" +
            "\t\t\"channel_id\" : \"VIRTUAL_ACCOUNT_BNI\",\n" +
            "\t\t\"service_id\" : \"VIRTUAL_ACCOUNT\",\n" +
            "\t\t\"acquirer_id\" : \"BNI\",\n" +
            "\t\t\"customer_phone\" : \"6281724410494\",\n" +
            "\t\t\"free_text\" : \"[{\\\"en\\\": \\\"Free text 1724410494016\\\", \\\"id\\\": \\\"Tulisan bebas 1724410494016\\\"}]\",\n" +
            "\t\t\"risk_engine_screening_type\" : null,\n" +
            "\t\t\"risk_engine_status\" : null,\n" +
            "\t\t\"risk_engine_reason\" : null,\n" +
            "\t\t\"acquirer_response_code\" : null,\n" +
            "\t\t\"acquirer_response_message\" : null,\n" +
            "\t\t\"promo\" : null,\n" +
            "\t\t\"amount_details\" : null,\n" +
            "\t\t\"paid_amount\" : 12500.00,\n" +
            "\t\t\"discount_amount\" : 0.00,\n" +
            "\t\t\"migration_id\" : null,\n" +
            "\t\t\"channel_code\" : \"H2H\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"id\" : 575531,\n" +
            "\t\t\"reusable_status\" : false,\n" +
            "\t\t\"amount\" : 12500.00,\n" +
            "\t\t\"min_amount\" : null,\n" +
            "\t\t\"max_amount\" : null,\n" +
            "\t\t\"acquirer_trx_id\" : \"VA004T1724408923839TBABWAqPWgf\",\n" +
            "\t\t\"transaction_settings\" : \"{\\\"source\\\": \\\"H2H\\\", \\\"settings\\\": {\\\"partnerServiceId\\\": \\\"98829172\\\"}, \\\"apiFormat\\\": \\\"SNAP\\\", \\\"apiVersion\\\": \\\"1.0\\\"}\",\n" +
            "\t\t\"additional_info\" : \"{\\\"channel\\\": \\\"VIRTUAL_ACCOUNT_BNI\\\", \\\"description\\\": \\\"Used on flow BNI-Non-SNAP 1724408923\\\", \\\"promoDeleted\\\": {\\\"list\\\": [{\\\"code\\\": \\\"DJPH01\\\", \\\"reserveId\\\": \\\"bd2f4b7b-83d0-407f-bc18-9eb3b5\\\"}], \\\"applicationId\\\": \\\"PG\\\"}, \\\"virtualAccountConfig\\\": {\\\"reusableStatus\\\": true}, \\\"info1FromMerchantCreateVa\\\": \\\"Info 1 from Merchant Create VA\\\"}\",\n" +
            "\t\t\"client_id\" : \"BRN-0206-1710295749973\",\n" +
            "\t\t\"uuid\" : 2226240823172843831107118729114021628430,\n" +
            "\t\t\"request_id\" : \"RID_C_20240823172843636\",\n" +
            "\t\t\"invoice_number\" : \"INV_BNI_20240823172843\",\n" +
            "\t\t\"company_code\" : \"98829172\",\n" +
            "\t\t\"company_name\" : null,\n" +
            "\t\t\"virtual_account_number\" : \"9882917200000085\",\n" +
            "\t\t\"payment_number\" : \"00000085\",\n" +
            "\t\t\"customer_name\" : \"C_1724408923\",\n" +
            "\t\t\"customer_email\" : \"test.bni.1724408923@test.com\",\n" +
            "\t\t\"partner_name\" : \"DOKU\",\n" +
            "\t\t\"partner_client_id\" : null,\n" +
            "\t\t\"currency\" : \"IDR\",\n" +
            "\t\t\"billing_type\" : \"FIX_BILL\",\n" +
            "\t\t\"feature\" : \"ADGPC\",\n" +
            "\t\t\"status\" : \"ACTIVE\",\n" +
            "\t\t\"created_date\" : \"2024-08-23T10:28:43.831Z\",\n" +
            "\t\t\"expired_date\" : \"2024-08-23T11:28:43.000Z\",\n" +
            "\t\t\"updated_date\" : null,\n" +
            "\t\t\"merchant_acquirer_id\" : 431,\n" +
            "\t\t\"channel_id\" : \"VIRTUAL_ACCOUNT_BNI\",\n" +
            "\t\t\"service_id\" : \"VIRTUAL_ACCOUNT\",\n" +
            "\t\t\"acquirer_id\" : \"BNI\",\n" +
            "\t\t\"customer_phone\" : \"6281724408923\",\n" +
            "\t\t\"free_text\" : \"[{\\\"en\\\": \\\"Free text 1724408923636\\\", \\\"id\\\": \\\"Tulisan bebas 1724408923636\\\"}]\",\n" +
            "\t\t\"risk_engine_screening_type\" : null,\n" +
            "\t\t\"risk_engine_status\" : null,\n" +
            "\t\t\"risk_engine_reason\" : null,\n" +
            "\t\t\"acquirer_response_code\" : null,\n" +
            "\t\t\"acquirer_response_message\" : null,\n" +
            "\t\t\"promo\" : null,\n" +
            "\t\t\"amount_details\" : null,\n" +
            "\t\t\"paid_amount\" : 12500.00,\n" +
            "\t\t\"discount_amount\" : 0.00,\n" +
            "\t\t\"migration_id\" : null,\n" +
            "\t\t\"channel_code\" : \"H2H\"\n" +
            "\t}\n" +
            "]}\n";

        String result = fromCopyAsJsonToText(rows, false, false);
        System.out.println("Result:\n" + result);
        assertNotNull(result);
    }
}
