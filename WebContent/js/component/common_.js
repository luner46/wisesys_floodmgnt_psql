/**
 *  기본 js 파일
 */

/**
 * selectNow
 * @return String
*/
function selectNow () {
	var date_now = new Date();
	var now_year = date_now.getFullYear();
	var now_month = date_now.getMonth() + 1;
	var now_day = date_now.getDate();
	// var now_hour = date_now.getHours();
	// var now_minute = date_now.getMinutes();
	var result_param = '';
	
	// now_minute = now_minute < 10 ? '0' + now_minute : now_minute;
	// now_hour = now_hour < 10 ? '0' + now_hour : now_hour;
	now_day = now_day < 10 ? '0' + now_day : now_day;
	now_month = now_month < 10 ? '0' + now_month : now_month;
	
	result_param = now_year+'-'+now_month+'-'+now_day // +' '+now_hour+':'+now_minute;
	return result_param;
}

/**
 * selectLocaleNum
 * @description 천 단위 구분 기호 추가 (ex. 9,999,999)
 * @param num
 * @return String
*/
function setLocaleNum(num){
	if (!isNaN(num) && num !== null && num !== ''){return parseFloat(num).toLocaleString('ko-KR');}
	
	if (isNaN(num) || num === null || num === ''){return '-';}
	
	return num;
}

