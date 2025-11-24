package kr.go.hrfco.common.util;

import java.util.regex.Pattern;

/**
 * <pre>
 * @ClassName   : CommonRegExpUtil
 * @Description : 정규표현식
 * @Modification 
 *
 * -----------------------------------------------------
 * 2025.11.06, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.11.06
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

public final class CommonRegExpUtil {
	private CommonRegExpUtil() {}

	/**
	 * 정규 표현식
	 * 
	 * <pre>
	 * validStr = CommonRegExpUtil.REGEXP_.matcher(orgnlStr).matches();
	 * return boolean;
	 * </pre>
	 * 
	 * @author 최준규
	 * @since 2025.11.06
	 */

	public static final String REGEXP_NULL_BYTE_STR = "\\x00";
	public static final Pattern REGEXP_NULL_BYTE = Pattern.compile(REGEXP_NULL_BYTE_STR);

	public static final String REGEXP_ASCII_STR = "[\\u0000-\\u001F&&[^\\r\\n\\t]]";
	public static final Pattern REGEXP_ASCII = Pattern.compile(REGEXP_ASCII_STR);
	
	public static final String REGEXP_INT_STR = "^[0-9]*$";
	public static final Pattern REGEXP_INT = Pattern.compile(REGEXP_INT_STR);
	
	public static final String REGEXP_KOR_STR = "^[가-힣ㄱ-ㅎㅏ-ㅣ]*$";
	public static final Pattern REGEXP_KOR = Pattern.compile(REGEXP_KOR_STR);
	
	public static final String REGEXP_ENG_STR = "^[A-Za-z]*$";
	public static final Pattern REGEXP_ENG = Pattern.compile(REGEXP_ENG_STR);

	public static final String REGEXP_CODE_LEN_2_STR = "^[0-9]{2}$";
	public static final Pattern REGEXP_CODE_LEN_2 = Pattern.compile(REGEXP_CODE_LEN_2_STR);
	
	public static final String REGEXP_CODE_LEN_4_STR = "^[0-9]{4}$";
	public static final Pattern REGEXP_CODE_LEN_4 = Pattern.compile(REGEXP_CODE_LEN_4_STR);
	
	public static final String REGEXP_CODE_LEN_6_STR = "^[0-9]{6}$";
	public static final Pattern REGEXP_CODE_LEN_6 = Pattern.compile(REGEXP_CODE_LEN_6_STR);
	
	public static final String REGEXP_EMAIL_STR = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]+$";
	public static final Pattern REGEXP_EMAIL = Pattern.compile(REGEXP_EMAIL_STR);
	
	public static final String REGEXP_SEARCH_STR = "^[가-힣\\s]*$";
	public static final Pattern REGEXP_SEARCH = Pattern.compile(REGEXP_SEARCH_STR);
	
	public static final String REGEXP_TELEPHONE_NO_STR = "^01[016789]-?[0-9]{3,4}-?[0-9]{4}$";
	public static final Pattern REGEXP_TELEPHONE_NO = Pattern.compile(REGEXP_TELEPHONE_NO_STR);
	
	public static final String REGEXP_TXT_STR = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9\\s\\-_.]+$";
	public static final Pattern REGEXP_TXT = Pattern.compile(REGEXP_TXT_STR);
	
	public static final String REGEXP_TAGS_STR = "(?is)<[^>]+>";
	public static final Pattern REGEXP_TAGS = Pattern.compile(REGEXP_TAGS_STR);
	
	public static final String REGEXP_TAGS_COMMENT_STR = "(?is)<([^>]*?)/\\*.*?\\*/([^>]*?)>";
	public static final Pattern REGEXP_TAGS_COMMENT = Pattern.compile(REGEXP_TAGS_COMMENT_STR);

	public static final String REGEXP_STACKED_SQL_STR = "\";\\s*(select|insert|update|delete|drop|alter|create)\\b\"";
	public static final Pattern REGEXP_STACKED_SQL = Pattern.compile(REGEXP_STACKED_SQL_STR, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	public static final String REGEXP_META_ATTACK_STR = "(--|/\\*|\\*/)" + "|\\bunion\\s+all\\s+select\\b" + "|\\bunion\\s+select\\b" + "|\\bexec\\b|\\bxp_\\w+\\b" + "|\\bbenchmark\\s*\\(|\\bsleep\\s*\\(";
	public static final Pattern REGEXP_META_ATTACK = Pattern.compile(REGEXP_META_ATTACK_STR, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	public static final String REGEXP_TAUTOLOGY_STR = "\\b(or|and)\\b\\s+\\d+\\s*=\\s*\\d+";
	public static final Pattern REGEXP_TAUTOLOGY = Pattern.compile(REGEXP_TAUTOLOGY_STR, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final String REGEXP_IDENT_STR = "(?:[\\p{L}A-Za-z0-9_]+|\"[^\"]+\"|`[^`]+`|\\[[^\\]]+\\])";
	public static final String REGEXP_SQL_STR = "(?<![A-Za-z0-9_])(" + "(drop|truncate)\\s+(table|database)\\b" + "|delete\\s+(?:[A-Za-z0-9_]+\\s+)?from\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*" + "|select\\s+[\\s\\S]{0,200}?\\bfrom\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*" + "|update\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*\\s+set\\s+" + REGEXP_IDENT_STR + "|insert\\s+into\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*(?:\\s*\\(|\\s+values\\b|\\s+select\\b)" + "|create\\s+(?:temporary\\s+)?table\\b\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*" + "|alter\\s+table\\b\\s+" + REGEXP_IDENT_STR + "(?:\\s*\\.\\s*" + REGEXP_IDENT_STR + ")*" + "|create\\s+(database|schema)\\b\\s+" + REGEXP_IDENT_STR + ")";
	public static final Pattern REGEXP_SQL = Pattern.compile(REGEXP_SQL_STR, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	public static final String REGEXP_HTML_COMMENTS_STR = "(?is)<!--.*?-->";
	public static final Pattern REGEXP_HTML_COMMENTS = Pattern.compile(REGEXP_HTML_COMMENTS_STR);
	
	public static final String REGEXP_DANGEROUS_BLOCK_STR = "(?is)<\\s*(?:script|style|iframe|object|embed|svg|math|form)[\\s\\S]*?>[\\s\\S]*?<\\s*/\\s*(?:script|style|iframe|object|embed|svg|math|form)\\s*>";
	public static final Pattern REGEXP_DANGEROUS_BLOCK = Pattern.compile(REGEXP_DANGEROUS_BLOCK_STR);
	
	public static final String REGEXP_DANGEROUS_SINGLE_STR = "(?is)<\\s*(?:script|style|iframe|object|embed|svg|math|form)[^>]*?/\\s*>";
	public static final Pattern REGEXP_DANGEROUS_SINGLE = Pattern.compile(REGEXP_DANGEROUS_SINGLE_STR);
	
	public static final String REGEXP_EVENT_ATTR_STR = "(?i)\\s+(?:on\\w+|xmlns|xlink:href)\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)";
	public static final Pattern REGEXP_EVENT_ATTR = Pattern.compile(REGEXP_EVENT_ATTR_STR);
	
	public static final String REGEXP_JS_OR_DATA_URI_STR = "(?i)(href|src)\\s*=\\s*([\"']?)\\s*(?:javascript:|data:)[^\"'\\s>]*\\2";
	public static final Pattern REGEXP_JS_OR_DATA_URI = Pattern.compile(REGEXP_JS_OR_DATA_URI_STR);
	
	public static final String REGEXP_STYLE_ATTR_STR = "(?i)style\\s*=\\s*(['\"])(.*?)\\1";
	public static final Pattern REGEXP_STYLE_ATTR = Pattern.compile(REGEXP_STYLE_ATTR_STR);
	
	public static final String REGEXP_SRCSET_DATA_STR = "(?i)srcset\\s*=\\s*([\"']?)[^\"'>]*data:[^\"'>]*\\1";
	public static final Pattern REGEXP_SRCSET_DATA = Pattern.compile(REGEXP_SRCSET_DATA_STR);
	
	public static final String REGEXP_EXPRESSION_STR = "(?is).*expression\\s*\\(.*";
	public static final Pattern REGEXP_EXPRESSION = Pattern.compile(REGEXP_EXPRESSION_STR);
	
	public static final String REGEXP_JS_IN_CSS_STR = "(?is).*url\\s*\\(\\s*['\"]?javascript:.*";
	public static final Pattern REGEXP_JS_IN_CSS = Pattern.compile(REGEXP_JS_IN_CSS_STR);
	
	public static final String REGEXP_FIXED_STR = "(?is).*position\\s*:\\s*fixed.*";
	public static final Pattern REGEXP_FIXED = Pattern.compile(REGEXP_FIXED_STR);
}
