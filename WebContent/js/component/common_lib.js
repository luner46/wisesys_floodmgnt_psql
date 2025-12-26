/**
 *  Layer Library js 파일
 */

let map = "";
let base_layer = "";
const base_path = "https://floodmap.go.kr";

//-------------------------------------------------layer base
var base = new ol.layer.Tile({
    name : "base",
    visible: true,
    extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
    source: new ol.source.XYZ({
		// VWorld Proxy URL
		// url: "/vworld/2d/Base/service/{z}/{x}/{y}.png",
		// crossOrigin: 'anonymous'

		url: "https://xdworld.vworld.kr/2d/Base/service/{z}/{x}/{y}.png"
    })
});

var satellite = new ol.layer.Tile({
    name : "satellite",
    visible: true,
    extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],     
    source: new ol.source.XYZ({
		url : "https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg"
    })
});

// 백업 백맵 표출
/*
var satellite = createResilientXYZLayer(
	"satellite",
	"https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg",
	"/wiseWorld/2d/Satellite/service/{z}/{x}/{y}.png",
	true
);
*/

var hybrid = new ol.layer.Tile({
    name : "hybrid",
    visible: true,
    extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
    source: new ol.source.XYZ({
		url : "https://xdworld.vworld.kr/2d/Hybrid/service/{z}/{x}/{y}.png",
		crossOrigin: 'anonymous'
    })
});

var gray = new ol.layer.Tile({
    name : "gray",
    visible: true,
    extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
    source: new ol.source.XYZ({
		url : "https://xdworld.vworld.kr/2d/gray/service/{z}/{x}/{y}.png",
		crossOrigin: 'anonymous'
    })
});

var midnight = new ol.layer.Tile({
    name : "midnight",
    visible: true,
    extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
    source: new ol.source.XYZ({
		url : "https://xdworld.vworld.kr/2d/midnight/service/{z}/{x}/{y}.png",
		crossOrigin: 'anonymous'
    }) 
}); 