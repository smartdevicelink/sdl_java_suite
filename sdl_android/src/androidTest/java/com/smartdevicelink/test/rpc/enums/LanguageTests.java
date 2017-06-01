package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.Language;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Language}
 */
public class LanguageTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.en_us);
		Language enumEnUs = Language.valueForString(example);
		example = mContext.getString(R.string.es_mx);
		Language enumEsMx = Language.valueForString(example);
		example = mContext.getString(R.string.fr_ca);
		Language enumFrCa = Language.valueForString(example);
		example = mContext.getString(R.string.de_de);
		Language enumDeDe = Language.valueForString(example);
		example = mContext.getString(R.string.es_es);
		Language enumEsEs = Language.valueForString(example);
		example = mContext.getString(R.string.en_gb);
		Language enumEnGb = Language.valueForString(example);
		example = mContext.getString(R.string.ru_ru);
		Language enumRuRu = Language.valueForString(example);
		example = mContext.getString(R.string.tr_tr);
		Language enumTrTr = Language.valueForString(example);
		example = mContext.getString(R.string.pl_pl);
		Language enumPlPl = Language.valueForString(example);
		example = mContext.getString(R.string.fr_fr);
		Language enumFrFr = Language.valueForString(example);
		example = mContext.getString(R.string.it_it);
		Language enumItIt = Language.valueForString(example);
		example = mContext.getString(R.string.sv_se);
		Language enumSvSe = Language.valueForString(example);
		example = mContext.getString(R.string.pt_pt);
		Language enumPtPt = Language.valueForString(example);
		example = mContext.getString(R.string.nl_nl);
		Language enumNlNl = Language.valueForString(example);
		example = mContext.getString(R.string.en_au);
		Language enumEnAu = Language.valueForString(example);
		example = mContext.getString(R.string.zh_cn);
		Language enumZhCn = Language.valueForString(example);
		example = mContext.getString(R.string.zh_tw);
		Language enumZhTw = Language.valueForString(example);
		example = mContext.getString(R.string.ja_jp);
		Language enumJaJp = Language.valueForString(example);
		example = mContext.getString(R.string.ar_sa);
		Language enumArSa = Language.valueForString(example);
		example = mContext.getString(R.string.ko_kr);
		Language enumKoKr = Language.valueForString(example);
		example = mContext.getString(R.string.pt_br);
		Language enumPtBr = Language.valueForString(example);
		example = mContext.getString(R.string.cs_cz);
		Language enumCsCz = Language.valueForString(example);
		example = mContext.getString(R.string.da_dk);
		Language enumDaDk = Language.valueForString(example);
		example = mContext.getString(R.string.no_no);
		Language enumNoNo = Language.valueForString(example);
		
		assertNotNull("EN-US returned null", enumEnUs);
		assertNotNull("ES-MX returned null", enumEsMx);
		assertNotNull("FR-CA returned null", enumFrCa);
		assertNotNull("DE-DE returned null", enumDeDe);
		assertNotNull("ES-ES returned null", enumEsEs);
		assertNotNull("EN-GB returned null", enumEnGb);
		assertNotNull("RU-RU returned null", enumRuRu);
		assertNotNull("TR-TR returned null", enumTrTr);
		assertNotNull("PL-PL returned null", enumPlPl);
		assertNotNull("FR-FR returned null", enumFrFr);
		assertNotNull("IT-IT returned null", enumItIt);
		assertNotNull("SV-SE returned null", enumSvSe);
		assertNotNull("PT-PT returned null", enumPtPt);
		assertNotNull("NL-NL returned null", enumNlNl);
		assertNotNull("EN-AU returned null", enumEnAu);
		assertNotNull("ZH-CN returned null", enumZhCn);
		assertNotNull("ZH-TW returned null", enumZhTw);
		assertNotNull("JA-JP returned null", enumJaJp);
		assertNotNull("AR-SA returned null", enumArSa);
		assertNotNull("KO-KR returned null", enumKoKr);
		assertNotNull("PT-BR returned null", enumPtBr);
		assertNotNull("CS-CZ returned null", enumCsCz);
		assertNotNull("DA-DK returned null", enumDaDk);
		assertNotNull("NO-NO returned null", enumNoNo);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    Language temp = Language.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    Language temp = Language.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies the possible enum values of Language.
	 */
	public void testListEnum() {
 		List<Language> enumValueList = Arrays.asList(Language.values());

		List<Language> enumTestList = new ArrayList<Language>();
		
		enumTestList.add(Language.EN_US);
		enumTestList.add(Language.ES_MX);
		enumTestList.add(Language.FR_CA);
		enumTestList.add(Language.DE_DE);
		enumTestList.add(Language.ES_ES);
		enumTestList.add(Language.EN_GB);
		enumTestList.add(Language.RU_RU);
		enumTestList.add(Language.TR_TR);
		enumTestList.add(Language.PL_PL);
		enumTestList.add(Language.FR_FR);
		enumTestList.add(Language.IT_IT);
		enumTestList.add(Language.SV_SE);
		enumTestList.add(Language.PT_PT);
		enumTestList.add(Language.NL_NL);
		enumTestList.add(Language.EN_AU);
		enumTestList.add(Language.ZH_CN);
		enumTestList.add(Language.ZH_TW);
		enumTestList.add(Language.JA_JP);
		enumTestList.add(Language.AR_SA);
		enumTestList.add(Language.KO_KR);
		enumTestList.add(Language.PT_BR);
		enumTestList.add(Language.CS_CZ);
		enumTestList.add(Language.DA_DK);
		enumTestList.add(Language.NO_NO);
		
		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}