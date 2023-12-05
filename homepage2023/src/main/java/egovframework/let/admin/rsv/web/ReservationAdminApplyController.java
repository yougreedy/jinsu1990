package egovframework.let.admin.rsv.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationApplyService;
import egovframework.let.rsv.service.ReservationApplyVO;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class ReservationAdminApplyController {
  
@Resource(name ="reservationService")
private ReservationService reservationService;

@Resource(name ="reservationApplyService")
private ReservationApplyService reservationApplyService;

//예악자 목록 가져오기
@RequestMapping(value = "/admin/rsv/selectApplyList.do")
public String selectApplyList(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model)throws Exception{
	 LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
     if(user == null || user.getId() == null) {
   	  model.addAttribute("message", "로그인 후 사용가능합니다.");
   	  return "redirect:" + Globals.MAIN_PAGE;
 
     }
     
     //관리자
     searchVO.setMngAt("Y");
	
     List<EgovMap> resultList = reservationApplyService.selectResrvationApplyList(searchVO);
     model.addAttribute("resultList", resultList);
     
    //엑셀 다운로드
     if("Y".equals(searchVO.getExcelAt())) {
    	 return "admin/rsv/RsvApplySelectListExcel";
     }
     
     
     return "admin/rsv/RsvApplySelectList";
	
	
 }
  
 //예약자정보 상세
@RequestMapping(value ="/admin/rsv/rsvApplySelect.do")
public String rsvApplySelect(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model)throws Exception{
	 LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
     if(user == null || user.getId() == null) {
   	     model.addAttribute("message", "로그인 후 사용가능합니다.");
   	     return "redirect:" + Globals.MAIN_PAGE;
 
     }else {
    	 model.addAttribute("USER_INFO", user);
     }
     
     ReservationApplyVO result = reservationApplyService.selectReservationApply(searchVO);
     
     //이중서블릿방지
     request.getSession().removeAttribute("sessionResrvationApply");
   
     model.addAttribute("result", result);
     return "admin/rsv/RsvApplySelect";
     
   }

//예약자정보 승인
@RequestMapping(value ="/admin/rsv/rsvApplyConfirm.do")
public String updateReservationConfirm(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model)throws Exception{
	   //이중서블릿방지체크
		if(request.getSession().getAttribute("sessionReservationApply") != null) {
			return"forward:/admin/rsv/selectApplyList.do";
		}
		
		 LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	     if(user == null || user.getId() == null) {
	   	  model.addAttribute("message", "로그인 후 사용가능합니다.");
	   	  return "redirect:" + Globals.MAIN_PAGE;
	     }
	     
	     searchVO.setUserId(user.getId());
       
	     reservationApplyService.updateReservationConfirm(searchVO);
	     
	   //이중서블릿방지
	     request.getSession().setAttribute("sessionResrvationApply", searchVO);
	     return "forward:/admin/rsv/selectApplyList.do";
   }
 

//예약정보 삭제하기
@RequestMapping(value = "/admin/rsv/rsvApplyDelete.do")
public String rsvApplyDelete(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model)throws Exception{
	  LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    if(user == null || user.getId() == null) {
  	  model.addAttribute("message", "로그인 후 사용가능합니다.");
  	  return "forward:" + Globals.MAIN_PAGE;

    }
    
    searchVO.setUserId(user.getId());
    
    reservationApplyService.deleteReservationApply(searchVO);
    
    return "forward:/admin/rsv/selectApplyList.do";
    }


//예약자정보 엑셀 다운로드
	@RequestMapping(value = "/admin/rsv/excel.do")
	public ModelAndView excel(@ModelAttribute("searchVO")ReservationApplyVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception {
	  Map<String, Object> map = new HashMap<String, Object>();
	  List<String> columMap = new ArrayList<String>();
	  List<Object> valueMap = new ArrayList<Object>();
	  String fileName = "";
	  
	  columMap.add("번호");
	  columMap.add("신정차명");
	  columMap.add("연락처");
	  columMap.add("이메일");
	  columMap.add("신청일");
	  
	  map.put("title", "예약신청형환");
	  fileName = EgovStringUtil.getConvertFileName(request, "예약신청현황");
	  
	  //관리자
	  searchVO.setMngAt("Y");
	  //목록
	  List<EgovMap> resultList = reservationApplyService.selectResrvationApplyList(searchVO);
   
    if(resultList != null) {
  	  EgovMap tmpVO = null;
  	  Map<String, Object> tmpMap = null;
  	  for(int i =0; i < resultList.size(); i++) {
  		  tmpVO = resultList.get(i);
  		  
  		  tmpMap = new HashMap<String, Object>();
  		  tmpMap.put("번호", i + 1);
  		  tmpMap.put("신청자명", tmpVO.get("chargerNm").toString() + "(" + tmpVO.get("frstRegisterId").toString() + ")");
  		  tmpMap.put("연락처", tmpVO.get("telno").toString());
  		  tmpMap.put("이메일", tmpVO.get("email").toString());
  		  tmpMap.put("신청일", tmpVO.get("frstRegistPnttmYmd").toString());
  		  
  		  valueMap.add(tmpMap);
  	  }
    }
	  
	  map.put("columMap", columMap);
	  map.put("valueMap", valueMap);
	  
	  
	  response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ".xls");
	  return new ModelAndView("excelDownloadView", "dataMap", map);
	  
	
	
	
	}

}







































