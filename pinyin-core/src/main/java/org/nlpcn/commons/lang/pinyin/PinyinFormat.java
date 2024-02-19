/** 
 * File    : PinyinFormat.java 
 * Created : 2014年1月22日 
 * By      : luhuiguo 
 */
package org.nlpcn.commons.lang.pinyin;

/**
 * 
 * @author luhuiguo
 */
public class PinyinFormat {

	public static final PinyinFormat DEFAULT_PINYIN_FORMAT = new PinyinFormat();

	public static final PinyinFormat UNICODE_PINYIN_FORMAT = new PinyinFormat(
			YuCharType.WITH_U_UNICODE, ToneType.WITH_TONE_MARK);
	
	public static final PinyinFormat TONELESS_PINYIN_FORMAT = new PinyinFormat(
			YuCharType.WITH_V, ToneType.WITHOUT_TONE);

	public static final PinyinFormat ABBR_PINYIN_FORMAT = new PinyinFormat(
			YuCharType.WITH_U_AND_COLON, ToneType.WITH_ABBR,
			CaseType.LOWERCASE, "", true);

	private YuCharType yuCharType = YuCharType.WITH_U_AND_COLON;

	private ToneType toneType = ToneType.WITH_TONE_NUMBER;

	private CaseType caseType = CaseType.LOWERCASE;

	private String separator = " ";

	private boolean onlyPinyin = false;

	public PinyinFormat() {
		super();
	}

	public PinyinFormat(YuCharType yuCharType, ToneType toneType,
			CaseType caseType, String separator, boolean onlyPinyin) {
		super();
		this.yuCharType = yuCharType;
		this.toneType = toneType;
		this.caseType = caseType;
		this.separator = separator;
		this.onlyPinyin = onlyPinyin;
	}

	public PinyinFormat(YuCharType yuCharType, ToneType toneType,
			CaseType caseType, String separator) {
		this(yuCharType, toneType, caseType, separator, false);
	}

	public PinyinFormat(YuCharType yuCharType, ToneType toneType,
			CaseType caseType) {
		this(yuCharType, toneType, caseType, " ");
	}

	public PinyinFormat(YuCharType yuCharType, ToneType toneType) {
		this(yuCharType, toneType, CaseType.LOWERCASE);
	}

	public YuCharType getYuCharType() {
		return yuCharType;
	}

	public void setYuCharType(YuCharType yuCharType) {
		this.yuCharType = yuCharType;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public ToneType getToneType() {
		return toneType;
	}

	public void setToneType(ToneType toneType) {
		this.toneType = toneType;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public boolean isOnlyPinyin() {
		return onlyPinyin;
	}

	public void setOnlyPinyin(boolean onlyPinyin) {
		this.onlyPinyin = onlyPinyin;
	}

}
