package egovframework.let.admin.rsv.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class ReservationAdminController {
  
@Resource(name ="reservationService")
private ReservationService reservationService;

//예약정보 목록 가져오기
@RequestMapping(value = "/admin/rsv/rsvSelectList.do")
public String rsvSelectList(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model)throws Exception{
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	if(user == null || EgovStringUtil.isEmpty(user.getId())) {
		model.addAttribute("message", "로그인후 사용 가능합니다.");
		return "forward:/login/login.do";
	}else {
		model.addAttribute("USER_INFO", user);
	}
	
    PaginationInfo paginationInfo = new PaginationInfo();

	paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
	paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
	paginationInfo.setPageSize(searchVO.getPageSize());

	searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
	searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
	List<EgovMap> resultList = reservationService.selectReservationList(searchVO);
	model.addAttribute("resultList", resultList);
	
	int totCnt = reservationService.selectReservationListCnt(searchVO);
	
	paginationInfo.setTotalRecordCount(totCnt);
	model.addAttribute("paginationInfo", paginationInfo);

    return"admin/rsv/RsvSelectList";
	
  }	

  //예약정보 등록/수정
  @RequestMapping(value = "/admin/rsv/rsvRegist.do")
  public String revRegist(@ModelAttribute("searchVO")ReservationVO ResrvatinoVO, HttpServletRequest request, ModelMap model)throws Exception {
	  LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인후 사용 가능합니다.");
			return "forward:/login/login.do";
		}else {
			model.addAttribute("USER_INFO", user);
		}
		
		ReservationVO result = new ReservationVO();
		if(!EgovStringUtil.isEmpty(ResrvatinoVO.getResveId())) {
			result = reservationService.selectReservation(ResrvatinoVO);
			
		}
		model.addAttribute("result", result);
		
		request.getSession().removeAttribute("sessionReservation");
		
		return "admin/rsv/RsvRegist";
		
  }
  
  //예약자 등록하기
  @RequestMapping(value = "/admin/rsv/rsvInsert.do")
  public String insert(@ModelAttribute("searchVO")ReservationVO searchVO, HttpServletRequest request, ModelMap model)throws Exception {
     //이중 서블릿 방지 채크
	  if(request.getSession().getAttribute("sessionReservation") != null) {
		  return "forward:/admin/rsv/rsvSelectList.do";
	  }
	  
	  LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			if(user == null || EgovStringUtil.isEmpty(user.getId())) {
				model.addAttribute("message", "로그인후 사용 가능합니다.");
				return "forward:/login/login.do";
			}
			
			searchVO.setUserId(user.getId());
			
			reservationService.insertReservation(searchVO);
			
			//이중서블릿방지
			request.getSession().setAttribute("sessionReservation", searchVO);
			return "forward:/admin/rsv/rsvSelectList.do";
	  
  }
    //예약자정보 수정하기
  @RequestMapping(value = "/admin/rsv/rsvUpdate.do")
  public String rsvUpdate(@ModelAttribute("searchVO")ReservationVO searchVO, HttpServletRequest request, ModelMap model)throws Exception {
	    //이중 서블릿 방지 채크
		  if(request.getSession().getAttribute("sessionReservation") != null) {
			  return "forward:/admin/rsv/rsvSelectList.do";
		  }
		  
		  LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			if(user == null || EgovStringUtil.isEmpty(user.getId())) {
				model.addAttribute("message", "로그인후 사용 가능합니다.");
				return "forward:/login/login.do";
			}
			
			searchVO.setUserId(user.getId());
			
			reservationService.updateReservation(searchVO);
			
			
			//이중서블릿방지
			request.getSession().setAttribute("sessionReservation", searchVO);
			return "forward:/admin/rsv/rsvSelectList.do";
  
   }
  
	  //예약자정보 삭제하기
	@RequestMapping(value = "/admin/rsv/rsvDelete.do")
	public String rsvDelete(@ModelAttribute("searchVO")ReservationVO searchVO, HttpServletRequest request, ModelMap model)throws Exception {
		  LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			if(user == null || EgovStringUtil.isEmpty(user.getId())) {
				model.addAttribute("message", "로그인후 사용 가능합니다.");
				return "forward:/login/login.do";
			}
			
			searchVO.setUserId(user.getId());
			
			reservationService.deleteReservation(searchVO);
			
			return "forward:/admin/rsv/rsvSelectList.do";
	  }

}












































