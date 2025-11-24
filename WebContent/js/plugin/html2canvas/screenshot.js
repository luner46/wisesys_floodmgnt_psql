var selectionFocus, mask; // focus 대신 selectionFocus 사용

function blockContextMenu(){
	const block = (ev) => {
		ev.preventDefault();
		ev.stopPropagation();
	};
	
	window.addEventListener('contextmenu', block, {capture: true, once: true});
}

function init_screenshot(e) {
    var height = window.innerHeight;
    var width = $(document).width();
	let tooltip = '';
	
    mask = $('<div id="screenshot_mask"></div>').css("border-width", "0 0 " + height + "px 0");
    selectionFocus = $('<div id="screenshot_focus"></div>');
    
	tooltip = $('<div class="tooltip tooltip_distance" style="position: absolute; background-color: #ffffff; color: #0B1F65; padding: 8px; border: 1px solid #2457E7; borderRadius: 4px; boxShadow: inset 0 0 0 1px rgba(0, 0, 0, .05); z-index: 999999"><span class="tooltip_len">드래그하여 영역을 선택하세요.</span><hr /><p>마우스 오른쪽 버튼 또는</p><p>&#039;ESC&#039;키를 눌러 마침</p></div>');
	
    $("body").append(mask);
    $("body").append(selectionFocus);
	$("body").append(tooltip);
    
    var selectArea = false; 
    var startX, startY;
	let cancelFlag = false;
	
	const onCtxCancel = (ev) => {
		ev.preventDefault();
		ev.stopPropagation();
		cancelScreenshot();
	}
	
	window.addEventListener('contextmenu', onCtxCancel, {capture: true});
	
	function removeGlobalCtx(){
		window.removeEventListener('contextmenu', onCtxCancel, {capture: true});
	}
	
	function cancelScreenshot(){
		cancelFlag = true;
		selectArea = false;
		
		$('body').off('.screenshot');
		$(document).off('.screenshot');
		
		$('#screenshot_focus').remove();
		$('#screenshot_mask').remove();
		tooltip.remove();
		$('.captureBtn').removeClass('on');
		
		removeGlobalCtx();
	}
	
	function clamp(val, min, max) {return Math.max(min, Math.min(max, val));}

	function moveToolTip(clientX, clientY){
		let tooltipWidth = tooltip.outerWidth();
		let tooltipHeight = tooltip.outerHeight();
		let x = clamp(clientX, 0, window.innerWidth - tooltipWidth - 12);
		let y = clamp(clientY, 0, window.innerHeight - tooltipHeight - 12);
		
		tooltip.css({left: x + 'px', top: y + 'px'});
	}
	
    function mousemove(e) {
        var x = e.clientX;
        var y = e.clientY;

        if (selectionFocus) {
            selectionFocus.css("left", x);
            selectionFocus.css("top", y);
        }
		
		moveToolTip(x, y);

        if (mask && selectArea) {
            var top = Math.min(y, startY);
            var right = $(document).width() - Math.max(x, startX);
            var bottom = window.innerHeight - Math.max(y, startY);
            var left = Math.min(x, startX);

            mask.css("border-width", [top + 'px', right + 'px', bottom + 'px', left + 'px'].join(' '));
		}
    }

    $("body").one("mousedown.screenshot", function(e) {
        e.preventDefault();
		
		if(e.button === 2) {blockContextMenu(); cancelScreenshot(); return;}
        selectArea = true;
        startX = e.clientX;
        startY = e.clientY;

		tooltip.css('display', 'none');
    }).one('mouseup.screenshot', function(e) {
		if (cancelFlag) {return;}
		
        selectArea = false;
		 
        $("body").off('mousemove.screenshot');
        $("#screenshot_focus").remove();
        $("#screenshot_mask").remove();
		$('.captureBtn').removeClass('on');
		
		tooltip.remove();
		
		removeGlobalCtx();
		
		if (e.button === 2) {blockContextMenu(); cancelScreenshot(); return;}
        
        var x = e.clientX;
        var y = e.clientY;

        var top = Math.min(y, startY);
        var left = Math.min(x, startX);
        var width = Math.max(x, startX) - left;
        var height = Math.max(y, startY) - top;
		
		if (width <= 0 || height <= 0){cancelScreenshot(); return;}
        
        setTimeout(() => {
            html2canvas(document.body).then(function(canvas) {
				var img = canvas.getContext('2d').getImageData(left, top, width, height);
                var c = document.createElement("canvas");
                c.width = width;
                c.height = height;
                c.getContext('2d').putImageData(img, 0, 0);
                save(c);
            });
        }, 0);
    }).on("mousemove.screenshot", mousemove);
	
	$(document).on('keydown.screenshot', function(e){
		if (e.key === 'Escape' || e.keyCode === 27){
			e.preventDefault();
			cancelScreenshot();
		}
	});
	
	$(document).on('contextmenu.screenshot', function(e){
		e.preventDefault();
		e.stopPropagation();
		
		cancelScreenshot();
	});
	
	if (e && typeof e.clientX == 'number'){
		moveToolTip(e.clientX, e.clientY);
	} else {
		moveToolTip(window.innerWidth / 2, window.innerHeight / 2);
	}
}

function save(canvas) {
	if (navigator.msSaveBlob) {
		canvas.toBlob(function (blob) {
			navigator.msSaveBlob(blob, 'capture.jpg');
		}, 'image/jpeg');
	} else {
		canvas.toBlob(function (blob) {
			const url = URL.createObjectURL(blob);
			const a = document.createElement('a');
			a.href = url;
			a.download = 'capture.jpg';
			document.body.appendChild(a);
			a.click();
			a.remove();
			URL.revokeObjectURL(url);
		}, 'image/jpeg', 0.92);
	}
}

