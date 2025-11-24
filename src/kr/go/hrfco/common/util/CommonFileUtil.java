
package kr.go.hrfco.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class CommonFileUtil {

	private static final Logger log = LoggerFactory.getLogger(CommonFileUtil.class);
	
	/**
	 * 디렉토리 생성
	 * 
	 * @author 김민수
	 * @since 2025.03.26
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
	 * 파일 용량 체크
	 * 
	 * @author 최준규
	 * @since 2025.03.26
	 * @param MultipartFile file 입력 파일
	 * @param int stdFileVol 기준 용량
	 * @return boolean validFile
	 */

	public boolean isFileVol(MultipartFile file, int stdFileVol) {
		if (file == null || file.isEmpty()) {
			log.info("isFileVol 확인 필요.");
			return false;
		}
		
		boolean validFile = false;
		
		try {
			long bytes = file.getSize();
			if (bytes <= stdFileVol && bytes >= 0) {
				validFile = true;
			} else {
				validFile = false;
			}
		} catch (NullPointerException e) {
			log.error(e.toString());
			return false;
		} catch (NumberFormatException e) {
			log.error(e.toString());
			return false;
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
			return false;
		} catch (SecurityException e) {
			log.error(e.toString());
			return false;
		}
		return validFile;
	}

	/**
	 * 파일명 길이 체크
	 * 
	 * @author 최준규
	 * @since 2025.03.26
	 * @param String fileName 입력 파일 문자열
	 * @param int stdStringLength 기준 문자열 길이
	 * @return boolean validFileName
	 */

	public boolean isStringLength(String fileName, int stdStringLength) {
		if (fileName == null || fileName.trim().isEmpty()) {
			log.info("isStringLength 확인 필요.");
			return false;
		}
		
		boolean validFileName = false;
		
		try {
			String baseFileName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
			if (baseFileName != null && !baseFileName.trim().isEmpty()) {
				String replaceFileName = baseFileName.replaceAll("\\.", "").replaceAll("/", "").replaceAll("\\\\", "");

				if (fileName.contains("/") || fileName.contains("\\") || fileName.contains("..")) {
					validFileName = false;
					log.info("유효하지 않은 fileName");
				} else {
					if (replaceFileName.length() <= stdStringLength && replaceFileName.length() > 0) {
						validFileName = true;
					} else {
						validFileName = false;
					}
				}
			} else {
				log.info("isStringLength 확인 필요.");
				return false;
			}
		} catch (NullPointerException e) {
			log.error(e.toString());
			return false;
		} catch (NoSuchElementException e) {
			log.error(e.toString());
			return false;
		} catch (StringIndexOutOfBoundsException e) {
	        log.error(e.toString());
			return false;
	    }
		return validFileName;
	}

	/**
	 * 확장자 체크
	 * 
	 * @author 최준규
	 * @since 2025.03.31
	 * @param String fileName 입력 파일 문자열
	 * @return boolean validFileExt
	 */

	public boolean isExt(String fileName) {
		if (fileName == null || fileName.trim().isEmpty()) {
			log.info("isExt 확인 필요.");
			return false;
		}
		
		boolean validFileExt = false;
		
		try {
			if (!fileName.contains(".")) {
				return false;
			}
			String baseFileName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (baseFileName.equals("txt") || baseFileName.equals("pdf") || baseFileName.equals("hwp") || baseFileName.equals("hwpx") || baseFileName.equals("jpg") || baseFileName.equals("jpeg") || baseFileName.equals("png") || baseFileName.equals("gif")) {
				validFileExt = true;
			} else {
				log.info("유효하지 않은 Ext");
				validFileExt = false;
			}
		} catch (NullPointerException e) {
			log.error(e.toString());
			return false;
		} catch (NoSuchElementException e) {
			log.error(e.toString());
			return false;
		} catch (StringIndexOutOfBoundsException e) {
	        log.error(e.toString());
			return false;
	    }
		return validFileExt;
	}

	/**
	 * Zip파일 압축 해제
	 * 
	 * @author 최준규
	 * @since 2025.08.06
	 * @param File zipFile
	 * @param File targetDir
	 */
	
    public void unzip(File zipFile, File targetDir) throws IOException {
    	// IO 처리를 위한 버퍼 생성 (1024byte = 1kb)
        byte[] buffer = new byte[1024];
        
        // zipFileList : 원본 압축 파일
        // unzipFileList : 압축 해제된 파일의 리스트

        // 입력된 zipFile을 stream 형식으로 처리 (입력 순서대로 처리)
        try (ZipInputStream zipFileList = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            
            // zipFileList에 남은 목록이 없을 때까지 반복
            while ((zipEntry = zipFileList.getNextEntry()) != null) {
            	
            	// targetDir(임시 폴더)와 zipEntry의 파일명을 이용해 출력 파일 생성
                File outFile = new File(targetDir, zipEntry.getName());
            	
                // 입력 파일의 정상적인 경로를 탐지
            	String nmlTargetDir = targetDir.getCanonicalPath();
            	String nmlFile = outFile.getCanonicalPath();
            	
            	// 비정상적인 경로를 호출할 경우, 예외 처리
            	if(!nmlFile.startsWith(nmlTargetDir + File.separator)) {
            		throw new IOException();
            	}
            	
            	// Zip 파일 내부에 디렉토리가 존재할 경우, 예외 처리
                if (zipEntry.isDirectory()) {
                    if (!outFile.isDirectory() && !outFile.mkdirs()) {
                        throw new IOException();
                    }
                    continue;
                }
                
                // 파일 데이터를 가져와 임시 폴더의 대상 파일로 복사
                try (FileOutputStream unzipFileList = new FileOutputStream(outFile)) {
                    int fileLength;
                    while ((fileLength = zipFileList.read(buffer)) > 0) {
                    	unzipFileList.write(buffer, 0, fileLength);
                    }
                }
                
                // 메모리 해제를 위한 Entry 해제 (선택)
                zipFileList.closeEntry();
            }
        } catch (IOException e) {
        	log.error(e.toString());
        	throw e;
        }
    }
}
