package kr.go.hrfco.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
//import java.util.Base64;
//import java.util.Base64.Decoder;
//import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonStringUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CommonStringUtil.class);
	
	/**
	 *  updateSHA256 암호화 (복호화 불가능)
	 * <pre>
	 * ex) 
	 * String nameSHA256 = CommonStringUtil.updateSHA256("name");
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>targetString (String)</b> ex) "name"
	 * @return <b>targetString (String)</b>
	*/
	public static String updateSHA256(String targetString){
		
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(targetString.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			
			targetString = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			log.error(e.toString());
			// e.printStackTrace(); 
			targetString = null; 
		}
		
		return targetString;
	}
	
	/**
	 *  updateAES256 암호화 (복호화 가능, 키 길이가 16이여야함)
	 * <pre>
	 * ex) 
	 * String nameAES256 = CommonStringUtil.updateAES256("name", "secret_key123456");
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>targetString (String)</b> ex) "name"
	 * @param <b>cryptoKey (String)</b> ex) "secret_key123456"
	 * @return <b>enStr (String)</b> ex) "ZL+fiPNlnVipWniEt1adcg=="
	*/
	public static String updateAES256(String targetString, String cryptoKey){
		String iv;
		String enStr = null;
		
		try{
			iv = cryptoKey.substring(0, 16);
			byte[] keyBytes = new byte[16];
			byte[] b = iv.getBytes("UTF-8");
			int len = b.length;
			if(len > keyBytes.length)	len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			Key keySpec = new SecretKeySpec(keyBytes, "AES");
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

			byte[] encrypted = c.doFinal(targetString.getBytes("UTF-8"));
			Encoder encoder = Base64.getEncoder();

			enStr = new String(encoder.encode(encrypted));
			
		}catch(IllegalArgumentException e){
			log.error(e.toString());
			// e.printStackTrace(); 
		} catch (UnsupportedEncodingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (BadPaddingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (InvalidKeyException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		}
		
		return enStr;
	}
	
	/**
	 *  updateAES256 복호화(키길이가 16이여야함)
	 * <pre>
	 * ex) 
	 * String name = CommonStringUtil.updateAES256ToBefore("ZL+fiPNlnVipWniEt1adcg==", "secret_key123456");
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>targetString (String)</b> ex) "ZL+fiPNlnVipWniEt1adcg=="
	 * @param <b>cryptoKey (String)</b> ex) "secret_key123456"
	 * @return <b>deStr (String)</b> ex) "name"
	*/
	public static String updateAES256ToBefore(String targetString, String cryptoKey){
		String iv;
		String deStr = null;
		
		try{
			iv = cryptoKey.substring(0, 16);
			byte[] keyBytes = new byte[16];
			byte[] b = iv.getBytes("UTF-8");
			int len = b.length;
			if(len > keyBytes.length)	len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			Key keySpec = new SecretKeySpec(keyBytes, "AES");
			
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));

			Decoder decoder = Base64.getDecoder();

			deStr = new String(c.doFinal(decoder.decode(targetString.getBytes())),"UTF-8");

		}catch(IllegalArgumentException e){
			log.error(e.toString());
			// e.printStackTrace(); 
		} catch (UnsupportedEncodingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (BadPaddingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (InvalidKeyException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			log.error(e.toString());
			// e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			log.error(e.toString());
			// e.printStackTrace();
		}
		
		return deStr;
	}
	
	/**
	 * 관측소 빈곳 채워 새로운 파일 만들기(라인별 리스트 생성후 비교)
	 * <pre>
	 * ex)
	 * String S = File.separator;
	 * String baseArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST.txt";
	 * String returnArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST2.txt";
	 * String downloadFilePath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST5.txt";
	 * CommonStringUtil.insertStnListFile(baseArrayListPath, returnArrayListPath, downloadFilePath);
	 * 
	 * file ex)
	 * """
	 * 90
	 * 92
	 * ...
	 * 100
	 * """
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>baseArrayListPath (String)</b> ex) "src/resource/ASOS_AWS_STN_LIST.txt"
	 * @param <b>returnArrayListPath (String)</b> ex) "src/resource/ASOS_AWS_STN_LIST2.txt"
	 * @param <b>downloadFilepath (String)</b> ex) "src/resource/ASOS_AWS_STN_LIST5.txt"
	*/
	public static void insertStnListFile(String baseArrayListFilepath, String compareArrayListFilepath, String downloadFilepath) {
		ArrayList <String> baseArrayList = new ArrayList <String>();
		ArrayList <String> compareArrayList = new ArrayList <String>();
		ArrayList <Integer> intArrayList = new ArrayList <Integer>();
        File useFile;
        FileReader useFilereader;
        BufferedReader useBufReader;
        String useline;
        
		try {
			// 파일을 arrayList로
			useFile = new File(baseArrayListFilepath);
			useFilereader = new FileReader(useFile);
			useBufReader = new BufferedReader(useFilereader);
			useline = "";
			
            while((useline = useBufReader.readLine()) != null){
                baseArrayList.add(useline);
            }          
            
            useBufReader.close();
            
            useFile = new File(compareArrayListFilepath);
			useFilereader = new FileReader(useFile);
			useBufReader = new BufferedReader(useFilereader);
			useline = "";
			
            while((useline = useBufReader.readLine()) != null){
                baseArrayList.add(useline);
            }          
            
            useBufReader.close();
			
			// 리스트 비교 후 채운뒤 없는 부분 채우기
			for(String baseItem : baseArrayList) {
	            if(!compareArrayList.contains(baseItem)) {
	            	compareArrayList.add(baseItem);
	            }
	        }
			
			// String 리스트를 Integer 리스트로 변환 후 정렬
            intArrayList = new ArrayList<Integer>(compareArrayList.size()) ;
            
            for (String myInt : compareArrayList) { 
            	intArrayList.add(Integer.valueOf(myInt)); 
            }
            
            Collections.sort(intArrayList);
            
            // Integer 리스트를 String 리스트로
            compareArrayList = new ArrayList<String>(intArrayList.size());
            
            for (Integer myString : intArrayList) { 
            	compareArrayList.add(Integer.toString(myString)); 
            }
            
            // 채운 리스트 파일쓰기
            useFile = new File(downloadFilepath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(useFile));
            
            if(useFile.isFile() && useFile.canWrite()){
                for(String i : compareArrayList) {
                    bufferedWriter.write(i);
                    bufferedWriter.newLine();
                }
                
                bufferedWriter.close();
            }
            
		}catch(IOException e) {
			log.error(e.toString());
			// e.printStackTrace();
		}
	}
	
	/**
	 * null 체크(string반환)
	 * <pre>
	 * ex)
	 * String test = CommonStringUtil.updateNullToString("test"); 
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>obj (Object)</b> ex) null
	 * @return <b>returnString (String)</b> ex) ""
	*/
	public static String updateNullToString(Object obj) {
		String returnString = "";
		
		try {
			if (obj instanceof String) { 
				returnString = (String)obj;
				
			}  else if (Objects.isNull(obj) ||  Double.isNaN((Double)obj) ||  Double.isInfinite((Double)obj)) { 
				// System.out.println("null값 확인 필요.");
				
			}  else {
				returnString = obj.toString();
				// System.out.println("값 확인 필요.");
			}
			
			return returnString;
			
		}catch(NullPointerException e) {
			log.error(e.toString());
			// e.printStackTrace();
        	return returnString;
		}
		
	}
	
	
	/**
	 * null 체크 (오브젝트, 디폴트)
	 * <pre>
	 * ex)
	 * String test = (String) CommonStringUtil.updateNullToDefault("test", "0"); 
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>confirmObj (Object)</b> ex) "test"
	 * @param <b>defaultObj (Object)</b> ex) "0"
	 * @return <b>returnObj (Object)</b> ex) "test"
	*/
	public static Object updateNullToDefault(Object confirmObj, Object defaultObj) {
		Object returnObj = defaultObj;
		
		try {
			if (!Objects.isNull(confirmObj)) { 
				// System.out.println("null값 확인 필요.");
				returnObj = confirmObj;
			}  
			
			return returnObj;
			
		}catch(NullPointerException e) {
			log.error(e.toString());
			// e.printStackTrace();
        	return returnObj;
		}
		
	}
	
	/**
	 * null 여부 판단 (str이 null일 경우 true, 그렇지 않으면 false)
	 * 
	 * @author 김민수
	 * @since 2025.03.31
	 * @param str 입력 문자열
	 * @return result 
	 */
	public boolean isNull(String str) {
	    boolean result = false;
	    if (str == null) {
	        result = true;
	    } else {
	        result = false;
	    }
	    return result;
	}

	/**
	 * null 변환 (str이 null일 경우 빈 문자열 ""로 변환, 그렇지 않으면 그대로 리턴)
	 * 
	 * @author 김민수
	 * @since 2025.03.31
	 * @param str 입력 문자열
	 * @return result
	 */
	public String selectNull(String str) {
	    String result = "";
	    if (str == null) {
	        result = "";
	    } else {
	        result = str;
	    }
	    return result;
	}

	/**
	 * 비밀번호를 SHA-256으로 암호화 (SHA-256 해시 문자열 반환)
	 * 
	 * @author 김민수
	 * @since 2025.03.31
	 * @param str 입력 문자열
	 * @return result
	 * @throws IllegalArgumentException 입력 문자열이 null인 경우
	 * @throws IllegalStateException SHA-256 해시 생성 실패 시
	 */
	public String saltHashPwd(String str) {
        if (str == null) throw new IllegalArgumentException("입력 문자열 null");

        String result = "";
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : salt) sb.append(String.format("%02x", b));
            sb.append(":");
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 해시 생성 실패", e);
        }
        return result;
    }

	/**
	 * 비밀번호 강도 체크 (모든 조건 만족 시 "0", 그렇지 않으면 틀린 부분의 숫자 조합 리턴 (예: "136"))
	 * 
	 * @author 김민수
	 * @since 2025.03.31
	 * @param str 입력 문자열
	 * @return result
	 * @throws IllegalArgumentException 입력 문자열이 null인 경우
	 */
	public String selectStrengthPwd(String str) {
	    if (str == null) throw new IllegalArgumentException("입력 문자열 null");

	    StringBuilder sb = new StringBuilder();
	    if (str.length() < 8) sb.append("1"); // 길이 8자 이상
	    if (!Pattern.compile("[A-Z]").matcher(str).find()) sb.append("2"); // 대문자 포함
	    if (!Pattern.compile("[a-z]").matcher(str).find()) sb.append("3"); // 소문자 포함
	    if (!Pattern.compile("[0-9]").matcher(str).find()) sb.append("4"); // 숫자포함
	    if (!Pattern.compile("[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?`~]").matcher(str).find()) sb.append("5"); // 특수문자포함
	    if (Pattern.compile("\\s").matcher(str).find()) sb.append("6"); // 공백문자 금지

	    String result = "";
	    if (sb.length() == 0) {
	        result = "0";
	    } else {
	        result = sb.toString();
	    }
	    return result;
	}

	/**
	 * 디렉토리 생성
	 * 
	 * @author 김민수
	 * @since 2025.03.31 
	 * @param path 디렉토리 경로
	 * @param dt 날짜 문자열 (yyyyMMdd 형식)
	 * @throws IllegalArgumentException 경로 또는 날짜가 null이거나 잘못된 날짜 형식일 경우
	 * @throws RuntimeException 디렉토리 생성 실패 시
	 */
	public void createDic(String path, String dt) {
	    if (path == null || dt == null) throw new IllegalArgumentException("경로 또는 날짜 null");
	    if (!dt.matches("\\d{8}")) throw new IllegalArgumentException("날짜 형식이 yyyyMMdd 형식이 아님");

	    try {
	        new SimpleDateFormat("yyyyMMdd").setLenient(false);
	        new SimpleDateFormat("yyyyMMdd").parse(dt);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException("유효하지않은날짜 : " + dt);
	    }

	    String year = dt.substring(0, 4);
	    String month = dt.substring(4, 6);
	    String day = dt.substring(6, 8);
	    File dir = new File(path, year + File.separator + month + File.separator + day);

	    boolean result = false;
	    if (!dir.exists()) {
	        result = dir.mkdirs();
	        if (result) {
	            log.info("디렉토리 생성됨: {}", dir.getAbsolutePath());
	        } else {
	            throw new RuntimeException("디렉토리 생성 실패 : " + dir.getAbsolutePath());
	        }
	    } else {
	        log.info("디렉토리 이미 존재함 : {}", dir.getAbsolutePath());
	    }
	}

	/**
	 * SQL 인젝션 의심 문자열 검사 (이상이 없으면 true, 의심되는 패턴이 있으면 false)
	 * 
	 * @author 김민수
	 * @since 2025.03.31
	 * @param input 입력 문자열
	 * @return result
	 */
	public boolean isSqlInjection(String input) {
	    boolean result = true;
	    if (input == null) result = false;
	        
	    // 인젝션 공격 의심되는 패턴
	    String[] patterns = {
	        "select", "insert", "update", "delete", "drop", "truncate", "exec", "union", "sleep", "benchmark",
	        " or ", " and ", "1=1", "1 = 1", "--", "#", "/*", "*/", ";", "'", "\"", "=", "*", "+", "?",
	        "from", "where", "join", "substr", "user_tables", "user_table_columns",
	        "information_schema", "sysobject", "table_schema", "declare", "dual", "(", ")"
	    };
	    
	    // 소문자로 변환하여 검사
	    String lowered = input.toLowerCase();
	    
	    for (String pattern : patterns) {
	        if (lowered.contains(pattern)) {
	            result = false;
	            break;
	        }
	    }
	    return result;
	}
	
	/**
     * SecureRandom 기반의 임시비밀번호 생성 메서드
     *
     * @author 김민수
     * @since 2025.04.08
     * @param userId 
     * @param charSet 사용할 문자셋 (XML 파일에서 로딩)
     * @return 64자리 임시비밀번호
     */
	public String createUserTempPwd(String userId, String charSet) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(64);
		
		for(int i=0; i<64; i++) {
			int index = random.nextInt(charSet.length());
			sb.append(charSet.charAt(index));
		}
		
		return sb.toString();
	}
	
	/**
	 * 비밀번호 강도계산
	 * 
	 * @author 최준규
	 * @since 2025.03.26
	 * @param String pswdStrength 비밀번호 강도
	 * @return String pswdStrengthResult
	 */

	public String selectStrengthResult(String pswdStrength) {
		if (pswdStrength == null || pswdStrength.trim().isEmpty()) {
			log.info("pswdStrength 확인 필요.");
			return null;
		}
		
		String pswdStrengthResult = "";
		String[] splitPswdStrength;
				
		try {
			if (pswdStrength.contains(",")) {
				splitPswdStrength = pswdStrength.split(",");
				
				switch (splitPswdStrength.length) {
					case 2:
					case 3:
						pswdStrengthResult = "보통";
						break;
					case 4:
						pswdStrengthResult = "약함";
						break;
					case 5:
					case 6:
						pswdStrengthResult = "매우 약함";
						break;
				default:
					log.info("splitPswdStrength 확인 필요.");
					return null;
				}
			} else if (pswdStrength != null && pswdStrength != "" && pswdStrength.equals("0")) {
				pswdStrengthResult = "매우 강함";
			} else if (pswdStrength != null && pswdStrength != "" && !pswdStrength.equals("0")) {
				pswdStrengthResult = "강함";
			} else {
				log.info("유효하지 않은 pswdStrength.");
				return null;
			}
		} catch (ArrayStoreException e) {
			log.error(e.toString());
			return null;
		} catch (NullPointerException e) {
			log.error(e.toString());
			return null;
		} catch (IndexOutOfBoundsException e) {
			log.error(e.toString());
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
			return null;
		}
		return pswdStrengthResult;
	}

	/**
	 * 특수문자 이스케이프
	 * 
	 * @author 최준규
	 * @since 2025.03.26
	 * @param String targetString 대상 문자열
	 * @return String escapeHtmlResult
	 */

	public String selectEscapeHtml(String targetString) {
		if (targetString == null || targetString.trim().isEmpty()) {
			log.info("escapeHtml targetString 확인 필요.");
			return null;
		}
		
		String escapeHtmlResult = "";
		
		try {
			escapeHtmlResult = targetString.replace("<", "&lt;")
							.replace(">", "&gt;")
							.replace("\"", "&quot;")
							.replace("'", "&#x27;")
							.replace("/", "&#x2F;")
							.replace("(", "&#x28;")
							.replace(")", "&#x29;");
							// .replace("&", "&amp;")
		} catch (NullPointerException e) {
			log.error(e.toString());
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
			return null;
		}
		return escapeHtmlResult;
	}

	/**
	 * XSS 필터링
	 * 
	 * @author 최준규
	 * @since 2025.03.26
	 * @param String targetString 대상 문자열
	 * @return String xssFilterResult
	 */

	public String selectXssFilter(String targetString) {
		if (targetString == null || targetString.trim().isEmpty()) {
			log.info("XssFilter targetString 확인 필요.");
			return null;
		}
		
		String xssFilterResult = "";
		
		try {
			xssFilterResult = targetString.replaceAll("(?i)javascript:", "")
							.replaceAll("(?i)script", "")
							.replaceAll("(?i)eval\\((.*)\\)", "")
							.replace("<", "&lt;")
							.replace(">", "&gt;")
							.replace("\"", "&quot;")
							.replace("'", "&#x27;")
							.replace("/", "&#x2F;")
							.replace("(", "&#x28;")
							.replace(")", "&#x29;");
							// .replace("&", "&amp;")
		} catch (NullPointerException e) {
			log.error(e.toString());
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
			return null;
		}
		return xssFilterResult;
	}
	
	/**
	 * 특수문자 역변환
	 * 
	 * @author 최준규
	 * @since 2025.03.31
	 * @param String targetString 대상 문자열
	 * @return String htmlEscapeResult
	 */

	public String selectHtmlEscape(String targetString) {
		if (targetString == null || targetString.trim().isEmpty()) {
			log.info("HtmlEscape targetString 확인 필요.");
			return null;
		}
		
		String htmlEscapeResult = "";
		
		try {
			htmlEscapeResult = targetString.replace("&lt;", "<")
					.replace("&gt;", ">")
					.replace("&quot;", "\"")
					.replace("&#x27;", "'")
					.replace("&#x2F;", "/")
					.replace("&#x28;", "(")
					.replace("&#x29;", ")");
					// .replace("&amp;", "&");
		} catch (NullPointerException e) {
			log.error(e.toString());
			return null;
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
			return null;
		}
		return htmlEscapeResult;
	}
	
	/**
	 * 검색 코드 유효성 체크
	 * 
	 * <pre>
	 * String lsirbCd = commonStringUtil.checkValidCode(lsirbCd, REGEXP_LSIRB_CD, DEFAULT_LSIRB_CD);
	 * </pre>
	 * 
	 * @author 최준규
	 * @since 2025.09.24
	 * @param String targetString 대상 코드 문자열
	 * @param Pattern targetRegExp 대상 문자열
	 * @param String defaultCd 기본 코드 문자열
	 */
	
	public String checkValidCode(String targetCd, Pattern targetRegExp, String defaultCd){
        try{
            if (targetCd == null) {return defaultCd;}

            targetCd = targetCd.replaceAll("\\s+", " ").trim();
            
            if (targetCd.isEmpty()) {return defaultCd;}
            
            if (targetRegExp != null && !targetRegExp.matcher(targetCd).matches()) {return defaultCd;}
            
            return targetCd;
        } catch (IllegalArgumentException e) {
    		log.error(e.toString());
    		return defaultCd;
        } catch (NullPointerException e) {
            log.error(e.toString());
            return defaultCd;
        }
    }

	/**
	 * 검색어 문자열 유효성 체크 및 정규화
	 * 
	 * <pre>
	 * String searchStr = commonStringUtil.checkValidString(searchStr, REGEXP_SEARCH_STR, MAX_SEARCH_STR_LEN);
	 * </pre>
	 * 
	 * @author 최준규
	 * @since 2025.09.24
	 * @param String targetString 대상 문자열
	 * @param Pattern targetRegExp 대상 정규 표현식
	 * @param int maxStrLen 최대 문자열 허용 길이
	 */
	
    public String checkValidString(String targetString, Pattern targetRegExp, int maxStrLen) {
    	try{
            if (targetString == null) {return "";}

            targetString = Normalizer.normalize(targetString, Normalizer.Form.NFKC);

            targetString = targetString.replaceAll("\\s+", " ").trim();

            if (targetString.isEmpty() || targetString.length() > maxStrLen) {return "";}

            if (!targetRegExp.matcher(targetString).matches()) {return "";}

            return targetString;
        } catch (IllegalArgumentException e) {
    		log.error(e.toString());
    		return "";
        } catch (NullPointerException e) {
            log.error(e.toString());
            return "";
        }
    }
	
	/**
	 * 사용자 아이디 저장 (쿠키 생성)
	 * 
	 * <pre>
	 * ex) setCookie("user_id", user_id, saveId, 30 * (60 * 60 * 24), res);
	 * </pre>
	 * 
	 * @author 최준규
	 * @date 2024.09.30
	 * @param String cookieName 설정할 쿠키 이름 
	 * @param String cookieValue 저장될 쿠키의 값 
	 * @param String targetCheckbox 아이디 저장 체크 여부 (true/false)
	 * @param int cookieMaxAge 쿠키 최대 수명 ex) 30 * (60 * 60 * 24) => 30일
	 * @param String res HttpServletResponse
	 */
	
	public static void setCookie(String cookieName, String cookieValue, String targetCheckbox, int cookieMaxAge, HttpServletResponse res) throws NoSuchAlgorithmException {
	    Cookie cookie;
	    
	    if (targetCheckbox.equals("true")) {
	        cookie = new Cookie(cookieName, cookieValue);
	        cookie.setMaxAge(cookieMaxAge);
	    } else {
	        cookie = new Cookie(cookieName, "");
	        cookie.setMaxAge(0);
	    }
	    
	    cookie.setPath("/");
	    res.addCookie(cookie);
	}

	/**
	 * SQL Injection 확인
	 * 
	 * <pre>
	 * validString = isObviouslySqlAttack(targetString);
	 * </pre>
	 * 
	 * @author 김지영
	 * @date 2025.10.02
	 * @param String html SQL Injection 의심 문자열
	 * @return boolean
	 */
	
	public static boolean isObviouslySqlAttack(String html) {
        if (html == null) return false;

        String text = CommonRegExpUtil.REGEXP_TAGS.matcher(html).replaceAll(" ");

        if (text.isEmpty()) return false;
        
        // HTML 엔티티 디코딩 (괄호, 따옴표, &nbsp; 등 우회 방지)
        try { 
	    	text = StringEscapeUtils.unescapeHtml4(text); 
	    } catch (Exception ignore) {}

        // URL 디코딩(선택, 공격자가 %28 같은 걸 쓸 때 대비)
        try { 
        	text = java.net.URLDecoder.decode(text, java.nio.charset.StandardCharsets.UTF_8.name()); 
        } catch (Exception ignore) {}

        if (CommonRegExpUtil.REGEXP_STACKED_SQL.matcher(text).find()) return true;
        if (CommonRegExpUtil.REGEXP_META_ATTACK.matcher(text).find()) return true;
        if (CommonRegExpUtil.REGEXP_TAUTOLOGY.matcher(text).find()) return true;
        if (CommonRegExpUtil.REGEXP_SQL.matcher(text).find()) return true;

        if (text.chars().anyMatch(ch -> Character.isISOControl(ch) && ch!='\n' && ch!='\r' && ch!='\t'))
            return true;

        return false;
    }
	

    /**
	 * sanitize (실행용 태그 속성 제거)
	 * 
	 * <pre>
	 * validString = sanitize(targetString);
	 * </pre>
	 * 
	 * @author 김지영
	 * @since 2025.10.02
	 * @param String html
	 * @return boolean 
	 */
	
	public static String sanitize(String html) {
		if (html == null) return null;
		String out = html;
		
		out = CommonRegExpUtil.REGEXP_HTML_COMMENTS.matcher(out).replaceAll("");       
		out = CommonRegExpUtil.REGEXP_DANGEROUS_BLOCK.matcher(out).replaceAll("");      
		out = CommonRegExpUtil.REGEXP_DANGEROUS_SINGLE.matcher(out).replaceAll("");    
		out = out.replaceAll("(?is)<([^>]*?)/\\*.*?\\*/([^>]*?)>", "<$1$2>");
		out = CommonRegExpUtil.REGEXP_EVENT_ATTR.matcher(out).replaceAll("");           
		out = CommonRegExpUtil.REGEXP_JS_OR_DATA_URI.matcher(out).replaceAll("$1=\"#\""); 
		out = CommonRegExpUtil.REGEXP_SRCSET_DATA.matcher(out).replaceAll("srcset=\"\"");
		// 널바이트(악성코드 삽입용 제어문자) 제거
		out = out.replaceAll("\\x00", "");
		out = out.replaceAll("[\\u0000-\\u001F&&[^\\r\\n\\t]]", "");
		
		Matcher m = CommonRegExpUtil.REGEXP_STYLE_ATTR.matcher(out);
		
		StringBuffer sb = new StringBuffer();
		
		while (m.find()) {
			String styleContent = m.group(2);
			
			if (styleContent.matches("(?is).*expression\\s*\\(.*") || styleContent.matches("(?is).*url\\s*\\(\\s*['\"]?javascript:.*") || styleContent.matches("(?is).*position\\s*:\\s*fixed.*")) {
				m.appendReplacement(sb, ""); 
			} else {
				m.appendReplacement(sb, "style=\"" + styleContent + "\"");
		    }
		}
		m.appendTail(sb);
		out = sb.toString();
	
		return out;
	}
}