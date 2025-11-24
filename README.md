## wisesys-template

---

### 환경

- JDK : 17
- Dynamic Web Module : 3.1
- Apache Tomcat : 9.0.107

---

### Version

#### V1.0.2 <small>(2025.11.07)</small>


1. jquery 버전 변경 (구버전 삭제)
    - jquery-3.7.1.min.js

2. Cubrid 버전 mybatis-context.xml 템플릿 추가
    - mybatis-context.xml

3. 캐시 방지 코드 추가
    - web.xml
    - CacheHeadersFilter.java
    
4. 백업 백맵 전환 코드 추가
    - common_gis.js
    - common_lib.js 

5. Database Connection 상태 확인 코드 추가
    - CommonDBConnectionUtil.java
    
6. 사용자 접속 로그 입력 로직 추가 <small>(시스템의 목적에 따라 MENU_MAP(메뉴 항목)이나 저장되는 항목에 대한 수정 필요)</small>
    - CommonInterceptor.java
    - CommonLoggerUtil.java
    - CommonLoggerService.java
    
7. 정규 표현식 템플릿 추가
    - CommonRegExpUtil.java
    
---

#### V1.0.1 <small>(2025.08.01)</small>

1. Util Java 코드 추가
    - BannerPrinter.java
    - CommonDateUtil.java
    - CommonFileUtil.java
    - CommonInterceptor.java
    - CommonPaginationUtil.java
    - CommonStringUtil.java

2. JavaScript Library 추가
    - jquery-1.12.1.js
    - jquery-cookie-1.4.1.js
    - jquery-ui.min.js
    - highchart-8.0.4.js
    - ol_3
    - ol_4

---

#### V1.0.0 <small>(2025.07.30)</small>

초기 템플릿 배포
