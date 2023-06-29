
	function checkLengthNoDelivery(com, no){
		console.log(`${com}, ${no}`);
		//정보 퍼옴 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=sjh7622&logNo=220689234596 
		var isAlert=false;
		var msg = '';
		
		switch(com){
			case '1': //우체국 (13자리)
				if(!(no.length == 13)){
					isAlert = true;
					msg = '운송장번호가 13자리여야 합니다.';
				}			
				break;
			case '2': //로젠택배(11자리)
				if(!(no.length == 11)){
					isAlert = true;
					msg = '운송장번호가 11자리여야 합니다.';
				}	
				break;
// 			case 1:
// 			2. 대한통운 
// 			http://www.doortodoor.co.kr/servlets/cmnChnnel?tc=dtd.cmn.command.c03condiCrg01Cmd&invc_no=(10자리)
// 				break;
// 			case 1:
// 			3.한진택배 
// 			http://www.hanjin.co.kr/Delivery_html/inquiry/result_waybill.jsp?wbl_num=(10, 12자리)
// 				break;
// 			case 1:
// 			5. 현대택배
// 			http://www.hlc.co.kr/hydex/jsp/tracking/trackingViewCus.jsp?InvNo={10,12자리}
// 				break;
// 			case 1:
// 			6. KGB택배 
// 			http://www.kgbls.co.kr/sub5/trace.asp?f_slipno=(10자리)
// 				break;
// 			case 1:
// 			7. CJ GLS 
// 			http://nexs.cjgls.com/web/service02_01.jsp?slipno=(12자리)
// 				break;
// 			case 1:
// 			8. KG옐로우캡택배 
// 			http://www.yellowcap.co.kr/custom/inquiry_result.asp?invoice_no=(11자리)
// 				break;
// 			case 1:
// 			9. 우체국 EMS 
// 			http://service.epost.go.kr/trace.RetrieveEmsTrace.postal?ems_gubun=E&POST_CODE=(13자리)
// 				break;
// 			case 1:
// 			10. DHL
// 			http://www.dhl.co.kr/publish/kr/ko/eshipping/track.high.html?pageToInclude=RESULTS&type=fasttrack&AWB=(10자리)
// 				break;
// 			case 1:
// 			11. 한덱스
// 			http://btob.sedex.co.kr/work/app/tm/tmtr01/tmtr01_s4.jsp?IC_INV_NO=1234567890 (10자리)
// 				break;
// 			case 1:
// 			12. FedEx
// 			http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcomreg&cntry_code=kr&language=korean&tracknumbers=123456789012 (12자리)
// 				break;
// 			case 1:
// 			13. 동부익스프레스
// 			http://www.dongbuexpress.co.kr/Html/Delivery/DeliveryCheckView.jsp?item_no=123456789012 (12자리)
// 				break;
// 			case 1:
// 			14. UPS
// 			http://www.ups.com/WebTracking/track?loc=ko_KR&InquiryNumber1=M1234567890 (최대 25자리)
// 				break;
// 			case 1:
// 			15. 하나로택배
// 			http://www.hanarologis.com/branch/chase/listbody.html?a_gb=center&a_cd=4&a_item=0&fr_slipno=1234567890 (최대 10자리)
// 				break;
// 			case 1:
// 			16. 대신택배
// 			http://home.daesinlogistics.co.kr/daesin/jsp/d_freight_chase/d_general_process2.jsp?1234567890123 (13자리)
// 				break;
// 			case 1:
// 			17. 경동택배
// 			http://www.kdexp.com/sub4_1.asp?stype=1&p_item=12345678901 (최대11자리)
// 			18. 이노지스택배
// 			http://www.innogis.net/trace02.asp?invoice=1234567890123 (최대13자리)
// 			19. 일양로지스택배
// 			http://www.ilyanglogis.com/functionality/tracking_result.asp?hawb_no=123456789 (9자리)
// 			20. CVSnet 편의점택배
// 			http://was.cvsnet.co.kr/src/ctod_status.jsp?invoice_no=1234567890 (10자리)
// 			21. TNT Express
// 			http://www.tnt.com/webtracker/tracking.do?respCountry=kr&respLang=ko&searchType=CON&cons=GE123456789WW (9,13자리)
// 			22. HB한방택배
// 			"http://www.hbtb.co.kr/search/s_search.asp?f_slipno=123456789012 (12자리)
// 			23. GTX
// 			http://www.gtx2010.co.kr/del_inquiry_result.html?s_gbn=1&awblno=123456789012 (12자리)
// 				break;
// 			case 1:
// 				break;
		}
		
		if(isAlert){
			alert(msg);
		} 
		
		return isAlert;
	}


	function showDeliveryInfo(com, no){
		//정보 퍼옴 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=sjh7622&logNo=220689234596 
		let url='';
		
		if( checkLengthNoDelivery(com, no) ) return;
		
		switch(com){
			case '1': //우체국 (13자리)
// 					url='http://service.epost.go.kr/trace.RetrieveRegiPrclDeliv.postal?sid1='+ no
				url='https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1='+ no +'&displayHeader=N';
				break;
			case '2': //로젠택배(11자리)
//					url='http://www.ilogen.com/iLOGEN.Web.New/TRACE/TraceView.aspx?gubun=slipno&slipno='+ no
				url='https://www.ilogen.com/web/personal/trace/'+ no
				break;
// 			case 1:
// 			2. 대한통운 
// 			http://www.doortodoor.co.kr/servlets/cmnChnnel?tc=dtd.cmn.command.c03condiCrg01Cmd&invc_no=(10자리)
// 				break;
// 			case 1:
// 			3.한진택배 
// 			http://www.hanjin.co.kr/Delivery_html/inquiry/result_waybill.jsp?wbl_num=(10, 12자리)
// 				break;
// 			case 1:
// 			5. 현대택배
// 			http://www.hlc.co.kr/hydex/jsp/tracking/trackingViewCus.jsp?InvNo={10,12자리}
// 				break;
// 			case 1:
// 			6. KGB택배 
// 			http://www.kgbls.co.kr/sub5/trace.asp?f_slipno=(10자리)
// 				break;
// 			case 1:
// 			7. CJ GLS 
// 			http://nexs.cjgls.com/web/service02_01.jsp?slipno=(12자리)
// 				break;
// 			case 1:
// 			8. KG옐로우캡택배 
// 			http://www.yellowcap.co.kr/custom/inquiry_result.asp?invoice_no=(11자리)
// 				break;
// 			case 1:
// 			9. 우체국 EMS 
// 			http://service.epost.go.kr/trace.RetrieveEmsTrace.postal?ems_gubun=E&POST_CODE=(13자리)
// 				break;
// 			case 1:
// 			10. DHL
// 			http://www.dhl.co.kr/publish/kr/ko/eshipping/track.high.html?pageToInclude=RESULTS&type=fasttrack&AWB=(10자리)
// 				break;
// 			case 1:
// 			11. 한덱스
// 			http://btob.sedex.co.kr/work/app/tm/tmtr01/tmtr01_s4.jsp?IC_INV_NO=1234567890 (10자리)
// 				break;
// 			case 1:
// 			12. FedEx
// 			http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcomreg&cntry_code=kr&language=korean&tracknumbers=123456789012 (12자리)
// 				break;
// 			case 1:
// 			13. 동부익스프레스
// 			http://www.dongbuexpress.co.kr/Html/Delivery/DeliveryCheckView.jsp?item_no=123456789012 (12자리)
// 				break;
// 			case 1:
// 			14. UPS
// 			http://www.ups.com/WebTracking/track?loc=ko_KR&InquiryNumber1=M1234567890 (최대 25자리)
// 				break;
// 			case 1:
// 			15. 하나로택배
// 			http://www.hanarologis.com/branch/chase/listbody.html?a_gb=center&a_cd=4&a_item=0&fr_slipno=1234567890 (최대 10자리)
// 				break;
// 			case 1:
// 			16. 대신택배
// 			http://home.daesinlogistics.co.kr/daesin/jsp/d_freight_chase/d_general_process2.jsp?1234567890123 (13자리)
// 				break;
// 			case 1:
// 			17. 경동택배
// 			http://www.kdexp.com/sub4_1.asp?stype=1&p_item=12345678901 (최대11자리)
// 			18. 이노지스택배
// 			http://www.innogis.net/trace02.asp?invoice=1234567890123 (최대13자리)
// 			19. 일양로지스택배
// 			http://www.ilyanglogis.com/functionality/tracking_result.asp?hawb_no=123456789 (9자리)
// 			20. CVSnet 편의점택배
// 			http://was.cvsnet.co.kr/src/ctod_status.jsp?invoice_no=1234567890 (10자리)
// 			21. TNT Express
// 			http://www.tnt.com/webtracker/tracking.do?respCountry=kr&respLang=ko&searchType=CON&cons=GE123456789WW (9,13자리)
// 			22. HB한방택배
// 			"http://www.hbtb.co.kr/search/s_search.asp?f_slipno=123456789012 (12자리)
// 			23. GTX
// 			http://www.gtx2010.co.kr/del_inquiry_result.html?s_gbn=1&awblno=123456789012 (12자리)
// 				break;
// 			case 1:
// 				break;
		}
		console.log(com,no);
    	window.open(url,'_blank');
	}