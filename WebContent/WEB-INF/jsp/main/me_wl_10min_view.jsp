<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/js/plugin/jquery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/js/plugin/jquery/jqueryui/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="/js/plugin/jquery/jqueryui/jquery-ui.min.css" />
<title>Data Serve API Test</title>
</head>
<script>
function meWlData(){
	$.ajax({
		url: '/selectRealTimeMeWl10min',
		success: function(data){
			$('.pastDataContainer .me_wl_table tbody').empty();
			
			let yyyymmddhhmm = '';
			let wlobscd = '', obsnm = '', agcnm = '', addr = '', etcaddr = '', lat = '', lon = '', gucd = '';
			let pfg = '', wlOrgVal = 0, fwOrgVal = 0, wlSubVal = 0, fwSubVal = 0;
			let realTimeDataTable = '';
			
			data.forEach(item => {
				yyyymmddhhmm = item.yyyymmddhhmm;
				wlobscd = item.wlobscd;
				obsnm = item.obsnm;
				agcnm = item.agcnm;
				addr = item.addr;
				etcaddr = item.etcaddr;
				lat = item.lat;
				lon = item.lon;
				gucd = item.gucd;
				pfg = item.pfg;
				wlVal = item.wlVal;
				fwVal = item.fwVal;
				
				realTimeDataTable += '<tr><td class="t_left">' + obsnm + '</td><td class="t_center">' + wlobscd + '</td><td class="t_center">' + wlVal + '</td><td class="t_center">' + fwVal + '</td></tr>';
			});
			
			$('.realTimeDataTitle .realTime').html('(' + yyyymmddhhmm + ')');
			$('.realTimeDataContainer .table_wrapper .me_wl_table tbody').html(realTimeDataTable);
		}
	});
}

function pastMeWlData(){
	const now = new Date();
	now.setMonth(2);
	now.setDate(10);
	now.setHours(01);
	now.setMinutes(00);
	
	const targetTime = now.getFullYear().toString() + (now.getMonth() + 1).toString().padStart(2, '0') + now.getDate().toString().padStart(2, '0') + now.getHours().toString().padStart(2, '0') + now.getMinutes().toString().padStart(2, '0');

	$.ajax({
		url: '/selectPastMeWl10min',
		data: {targetTime: targetTime},
		success: function(data){
			$('.pastDataContainer .me_wl_table tbody').empty();
			
			let yyyymmddhhmm = '';
			let wlobscd = '', obsnm = '', agcnm = '', addr = '', etcaddr = '', lat = '', lon = '', gucd = '';
			let pfg = '', wlVal = 0, fwVal = 0;
			let pastDataTable = '';
			
			if (data.length > 0) {
				data.forEach(item => {
					yyyymmddhhmm = item.yyyymmddhhmm;
					wlobscd = item.wlobscd;
					obsnm = item.obsnm;
					agcnm = item.agcnm;
					addr = item.addr;
					etcaddr = item.etcaddr;
					lat = item.lat;
					lon = item.lon;
					gucd = item.gucd;
					pfg = item.pfg;
					wlVal = item.wlVal;
					fwVal = item.fwVal;
					
					pastDataTable += '<tr><td class="t_left">' + obsnm + '</td><td class="t_center">' + wlobscd + '</td><td class="t_center">' + wlVal + '</td><td class="t_center">' + fwVal + '</td></tr>';
				});
			} else {
				pastDataTable = '<tr style="text-align: center;"><td colspan=4>조건에 맞는 검색 결과가 없습니다.</td></tr>';				
			}
			
			$('.pastTimeDataTitle .pastTime').html('(' + targetTime + ')');
			$('.pastDataContainer .table_wrapper .me_wl_table tbody').html(pastDataTable);
		}
	});
}

function uffBsceData(){
	$.ajax({
		url: '/selectRealTimeUffBsceData',
		success: function(data){
			let initTm = '';
			let bsn = '', rmseVal = '', wlVal = '', rnVal = '', durVal = '', oxVal = '';
			let realTimeDataTable = '';
			
			data.forEach(item => {
				$('.realTimeDataContainer .table_wrapper .uff_bsce_table tbody').empty();
				
				initTm = item.initTm;
				bsn = item.bsn;
				rmseVal = item.rmseVal;
				wlVal = item.wlVal;
				rnVal = item.rnVal;
				durVal = item.durVal;
				oxVal = item.oxVal;

				realTimeDataTable += '<tr><td class="t_left">' + bsn + '</td><td class="t_center">' + rmseVal + '</td><td class="t_center">' + wlVal + '</td><td class="t_center">' + rnVal + '</td><td class="t_center">' + durVal + '</td><td class="t_center">' + oxVal + '</td></tr>';
			});
			
			$('.realTimeDataContainer .table_wrapper .uff_bsce_table tbody').html(realTimeDataTable);
		}
	});
}

$(function(){
	meWlData();
	uffBsceData();
});
</script>
<style>
.t_center {text-align: center;}
.t_left {text-align: left;}

.dataContainer {display: flex; width: 100%;}

.table_wrapper {height: 350px; overflow-y: auto;}

.realTimeDataTitle {text-align: center;}
.realTimeDataContainer {flex: 5; padding: 30px;}
.realTimeDataContainer table {width: 100%;}

.pastTimeDataTitle {text-align: center;}
.pastDataContainer {flex: 5; padding: 30px;}
.pastDataContainer .btnDiv {display: flex; justify-content: right; padding: 10px 20px;}
.pastDataContainer table {width: 100%;}

.col_divider {width: 1px; height: 800px; margin: 60px 10px; background: #000000;}
.row_divider {width: 870px; height: 1px; margin: 20px auto; background: #000000;}
</style>
<body>
<div class="dataContainer">
	<div class="realTimeDataContainer">
		<div class="realTimeDataTitle">
			<h3>실시간 데이터 <span class="realTime"></span></h3>
		</div>
		<div class="table_wrapper">
			<table class="me_wl_table">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: 30%;" />
					<col style="width: 10%;" />
					<col style="width: auto;" />
				</colgroup>
				<thead>
					<tr>
						<th>관측소 명</th>
						<th>관측소 코드</th>
						<th>수위</th>
						<th>유량</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<div class="row_divider"></div>
		<div class="table_wrapper">
			<table class="uff_bsce_table">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: auto;" />
					<col style="width: auto;" />
					<col style="width: auto;" />
					<col style="width: auto;" />
					<col style="width: auto;" />
				</colgroup>
				<thead>
					<tr>
						<th>bsn</th>
						<th>wl</th>
						<th>rmse</th>
						<th>rn</th>
						<th>dur</th>
						<th>ox</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<div class="col_divider"></div>
	<div class="pastDataContainer">
		<div class="pastTimeDataTitle">
			<h3>과거 데이터 <span class="pastTime"></span></h3>
		</div>
		<div class="btnDiv">
			<button class="pastReferenceBtn" onclick='pastMeWlData();'>조회</button>
		</div>
		<div class="table_wrapper">
			<table class="me_wl_table">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: 30%;" />
					<col style="width: 10%;" />
					<col style="width: auto;" />
				</colgroup>
				<thead>
					<tr>
						<th>관측소 명</th>
						<th>관측소 코드</th>
						<th>수위</th>
						<th>유량</th>
					</tr>
				</thead>
				<tbody><tr style="text-align: center;"><td colspan=4>조건에 맞는 검색 결과가 없습니다.</td></tr></tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>