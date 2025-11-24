/**
 * MAP 관련 js 파일
 */

/**
 * VWorld 서비스 상태 점검 함수
 *
 * - Hybrid 레이어의 타일 5개를 병렬로 요청하여 상태를 검사
 * - 모든 타일 요청은 각각 2초(timeout) 안에 응답을 받아야 함
 * - 5개 모두 응답 실패 또는 각각 2초 초과 시 → VWorld 전체 오류로 간주
 * - 결과는 전역 변수 vworldGlobalError에 반영
 *
 * <pre>
 * // VWorld 오류 여부를 판단하는 전역변수
 * let vworldGlobalError = false;
 * 
 * $(function(){
 * 		checkVworldHealth();
 * });
 * </pre>
 * 
 * @param {Function} callback - 상태 점검 이후 호출할 함수 (선택적)
 */
function checkVworldHealth(callback) {
	const TIMEOUT_MS = 2000; // 응답시간 2초 
	const testTile = [
		"https://xdworld.vworld.kr/2d/Hybrid/service/8/218/99.png",
		"https://xdworld.vworld.kr/2d/Hybrid/service/8/218/100.png",
		"https://xdworld.vworld.kr/2d/Hybrid/service/8/218/101.png",
		"https://xdworld.vworld.kr/2d/Hybrid/service/8/219/99.png",
		"https://xdworld.vworld.kr/2d/Hybrid/service/8/219/100.png",
	];

	function fetchWithTimeout(url, timeout) {
		return Promise.race([
			fetch(url).then(res => res.ok),
			new Promise(resolve => setTimeout(() => resolve(false), timeout))
		]);
	}

	const checks = testTile.map(url => fetchWithTimeout(url, TIMEOUT_MS));

	Promise.all(checks).then(results => {
		vworldGlobalError = results.every(res => res === false);
		// console.log("vworld 상태:", vworldGlobalError ? "에러 있음 → 로컬 타일 사용" : "정상");
		if (callback) callback();
	});
}

/**
 * 
 * createResilientXYZLayer
 * VWorld 타일 요청이 실패한 경우 사내 타일 백맵으로 대체되는 XYZ 타일 레이어 생성 함수
 *
 * - 타일 요청 시, vworldGlobalError 값을 참조해 VWorld 또는 사내 타일 백맵 URL 사용
 * - VWorld 상태 비정상(vworldGlobalError=true)인 경우, 사내 타일 백맵을 자동으로 사용
 *
 * <pre>
 * var satellite = createResilientXYZLayer(
 * 		"satellite",
 * 		"https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg",
 *		"/wiseWorld/2d/Satellite/service/{z}/{x}/{y}.png",
 *		true
 * );
 * </pre>
 * 
 * @param {String} name - 레이어 이름
 * @param {String} vworldUrl - VWorld 타일 URL 템플릿
 * @param {String} replaceUrl - 사내 타일 백맵 URL 템플릿
 * @param {Boolean} visible - 레이어 표시 여부
 * @returns {ol.layer.Tile} - OpenLayers 타일 레이어 객체
 */
function createResilientXYZLayer(name, vworldUrl, replaceUrl, visible) {
	return new ol.layer.Tile({
		name: name,
		visible: visible,
		source: new ol.source.XYZ({
			tileUrlFunction: function (tileCoord) {
				const z = tileCoord[0];
				const x = tileCoord[1];
				const y = -tileCoord[2] - 1;

				let urlTemplate;
				if (vworldGlobalError) {
					urlTemplate = replaceUrl; // 오류 시 대체 타일 사용
				} else {
					urlTemplate = vworldUrl; // 정상 시 VWorld 타일 사용
				}

				return urlTemplate.replace("{z}", z).replace("{x}", x).replace("{y}", y);
			}
		})
	});
}