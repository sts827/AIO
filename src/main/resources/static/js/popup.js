// 팝업 열기 
function openPopup(modalname) {
	document.get
	$('.' + modalname).show();
	$(".shadow").show(); // 배경 어둡게
			
	//스크롤 막기
	/*
	$('.wrap').on('scroll touchmove mousewheel', function(event) {
		event.preventDefault();
		event.stopPropagation();
		return false;
		});
	*/
}

// 팝업 닫기
function closePopup(flag) {
	$('.popup').hide(); //팝업을 닫기다.
	$('.shadow').hide(); // 배경 어둡게 삭제
	//스크롤 막기 해제
	$('.wrap').off('scroll touchmove mousewheel');
};