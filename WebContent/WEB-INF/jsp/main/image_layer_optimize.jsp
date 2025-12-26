<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/js/plugin/ol_4/ol.js"></script>
<script type="text/javascript" src="/js/plugin/ol_3/proj4.js"></script>
<script type="text/javascript" src="/js/plugin/ol_4/ext.js" ></script>
<script type="text/javascript" src="/js/plugin/jquery/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/js/plugin/jquery/jqueryui/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/component/common_lib.js"></script>
<link rel="stylesheet" type="text/css" href="/js/plugin/jquery/jqueryui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="/js/plugin/ol_4/ol.css" />
<title>Image Layer Optimize Test</title>
<style>
html, body {margin: 0; padding: 0}
body {width: 100%; height: 100%; overflow: hidden;}
.type_select {display: flex; gap: 10px; position: absolute; top: 5%; right: 3%; padding: 20px; border: 1px solid #000000; border-radius: 10px; background-color: #FFFFFF;}
.font-highlight {color: red; font-weight: bold;}
</style>
<script>
let imageLayer = "", tifLayer="", manholeLayer="";

function selectImageLayer(wl, resolution) {
	let selected_url = "";
	
	if (wl === 'null' || wl === null) {
		selected_url = '/layer/' + resolution + '_color.png';	
	} else {
		selected_url = '/layer/' + resolution + '/' + resolution + '_' + wl + '_color.png';	
	}
	
	if (map.getLayers().getArray().indexOf(imageLayer) !== -1) {
		map.removeLayer(imageLayer);
	} else if (map.getLayers().getArray().indexOf(tifLayer) !== -1) {
		map.removeLayer(tifLayer);
	}
	
	imageLayer = new ol.layer.Image({
		source: new ol.source.ImageStatic({
			url: selected_url,
			imageExtent: [14123057.4501, 4499697.1045, 14134728.5116, 4517051.1863],
            projection: 'EPSG:3857',
            interpolate: false
		}),
        opacity: 1.0
	});
	
	map.addLayer(imageLayer);
}

function selectManholeLayer(){
	if (map.getLayers().getArray().indexOf(manholeLayer) !== -1) {
		map.removeLayer(manholeLayer);
	}
	
	manholeLayer = new ol.layer.Tile({
		source: new ol.source.TileWMS({
			url: 'http://211.209.185.144:3020/sims2/wms',
			params: {
				'LAYERS': 'sims2:sims2_node_AY01',
				'TILED': true,
				'FORMAT': 'image/png',
				'TRANSPARENT': true
			},
            serverType: 'geoserver',
            crossOrigin: 'anonymous',
		})
	});
	
	map.addLayer(manholeLayer);
}

let linkLayer, nodeLayer;
const waterOverlays = [];
const nodeSource = new ol.source.Vector();

function selectLinkData(){
	$.ajax({
		url: '/selectAy01LinkData',
		dataType: 'json',
		success: function(data){
			/* data.forEach(item => {
				let name = item.name;
				let smh = item.smh;
				let emh = item.emh;
				let length = item.length;
				let slope = item.slope;
				let centerX = item.centerX;
				let centerY = item.centerY;
				let angleDeg = item.angleDeg;
				
				const feature = new ol.Feature({
					geometry: new ol.geom.Point([centerX, centerY]),
					angleDeg: angleDeg
				});
				
				linkSource.addFeature(feature);
			}); */
			data.forEach(item => {
				addWaterOverlay(item.startX, item.startY, item.angleDeg, Math.floor(item.length));
			});
			
			updateAllWaterOverlays();
		}
	});
}

function selectNodeData(){
	$.ajax({
		url: '/selectAy01NodeData',
		dataType: 'json',
		success: function(data){
			data.forEach(item => {
				const feature = new ol.Feature({
					geometry: new ol.geom.Point([item.centerX, item.centerY])
				});
				
				nodeSource.addFeature(feature);
			});
			
			nodeLayer = new ol.layer.Vector({
			    source: nodeSource,
			    style: new ol.style.Style({
			        image: new ol.style.Icon({
			            src: '/images/manhole.png',
			            scale: 0.05,
			            anchor: [0.5, 0.5],
			            anchorXUnits: 'fraction',
			            anchorYUnits: 'fraction'
			        })
			    })
			});
			
			map.addLayer(nodeLayer);
		}
	});
}

function addWaterOverlay(startX, startY, angleDeg, length){
	const el = document.createElement('img');
	el.src = '/images/water_flow.svg';
	el.style.position = 'absolute';
	// el.style.width = length + 'px';
	el.style.height = '30px';
	el.style.transformOrigin = '0% 50%';
	
	// const angle = (angleDeg - 90) || 0;
	// el.style.transform = 'translate(-50%, -50%) rotate(-' + angle + 'deg)';

    el.addEventListener('dragstart', function(e) {e.preventDefault();});
    el.addEventListener('mousedown', function(e) {e.preventDefault();});
    
	el.draggable = false;
	el.style.pointerEvents = 'none';
	
	const overlay = new ol.Overlay({
		element: el,
	    position: [startX, startY],
	    positioning: 'center-left',
	    stopEvent: false
	});

	map.addOverlay(overlay);
	
	waterOverlays.push({
        overlay: overlay,
        length: length,
        angleDeg: angleDeg
    });
}

function updateAllWaterOverlays(){
    if (!map) return;
    const resolution = map.getView().getResolution();

    waterOverlays.forEach(obj => {
        const el = obj.overlay.getElement();
        if (!el) return;

        let pxWidth = obj.length / resolution;

        pxWidth = Math.max(10, pxWidth);
        el.style.width = pxWidth + 'px';
        
        el.style.height = '30px';

        const cssAngle = -(90 - obj.angleDeg);
        el.style.transform = 'rotate(' + cssAngle + 'deg)';
    });
}

$(function(){
	const view = new ol.View({
        projection: 'EPSG:3857',
        center: [14126647.0651, 4507716.7171],
		zoom: 13,
		minZoom: 8,
		maxZoom: 18
	});
	
	base_layer = [satellite, hybrid];
	
	map = new ol.Map({
		logo: false,
		target: 'map_wrap',
		layers: base_layer,
		view: view,
		controls: ol.control.defaults({zoom: false})
	});
	
	map.getView().on('change:resolution', function () {
        updateAllWaterOverlays();
    });
	
	$(document).on('change', '#wl_select, #resolution_select', function(e){
		let wl_val = $('#wl_select').val();
		let resolution_val = $('#resolution_select').val();
		
		selectImageLayer(wl_val, resolution_val);
	});
	
	$(document).on('click', '#display_tif', function(){
		selectTifLayer();
		
		$('.type_select .select_item #resolution_select').prop('selectedIndex', 0);
	});
	
	selectNodeData();
	selectLinkData();
});
</script>
</head>
<body>
<div class="map_wrap" id="map_wrap"></div>
<div class="type_select">
	<div class="select_item">
		<button class="display_tif" id="display_tif">TIFF</button>
	</div>
	<div class="select_item">
		<select id="resolution_select">
			<option disabled selected>해상도</option> <!-- 해상도 -->
			<option class="font-highlight" value="no_scale">100% (원본)</option> <!-- 32Kb -->
			<option value="upscale200">200%</option> <!-- 96Kb -->
			<option value="upscale300">300%</option> <!-- 201Kb -->
			<option class="font-highlight" value="upscale400">400%</option> <!-- 339Kb -->
			<option value="upscale500">500%</option> <!-- 516Kb -->
			<option value="upscale600">600%</option> <!-- 733Kb -->
			<!-- <option value="20000">20000</option>
			<option value="18000">18000</option>
			<option value="16000">16000</option>
			<option value="14000">14000</option>
			<option value="13000">13000</option>
			<option value="12000">12000</option>
			<option value="12000">11000</option>
			<option value="10000">10000</option>
			<option value="9000">9000</option> -->
		</select>
	</div>
	<div class="select_item">
		<select id="mm_select">
			<option disabled selected>200mm</option> <!-- 총 강우량 -->
		</select>
	</div>
	<div class="select_item">
		<select id="time_select">
			<option disabled selected>120min</option> <!-- 강우 지속 시간 -->
		</select>
	</div>
	<div class="select_item">
		<select id="wl_select">
			<option disabled selected>10yr</option><!-- 기점 수위 -->
			<!-- <option value="10yr">10yr</option>
			<option value="30yr">30yr</option>
			<option value="free">자유단</option>
			<option value="plan">계획빈도</option> -->
		</select>
	</div>
	<div class="select_item">
		<select id="time_select">
			<option disabled selected>max</option> <!-- 시간 -->
		</select>
	</div>
</div>
</body>
</html>