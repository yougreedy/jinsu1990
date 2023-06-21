package egovframework.let.join.web;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Property;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.crud.service.CrudVO;
import egovframework.let.join.service.JoinVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.fcc.service.FileMngUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class JoinController {
  
	//약관동의
	@RequestMapping(value = "/join/siteUseAgree.do")
	  public String siteUseAgree (@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest requset, ModelMap model, HttpSession  session ) throws Exception{
         return "join/SiteUseAgree";
	}
	
	//회원구분
	@RequestMapping(value = "/join/memberType.do")
	  public String memberType(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest requset, ModelMap model, HttpSession  session ) throws Exception{
         return "join/MemberType";
	}
	
	//회원등록 폼
	@RequestMapping(value = "/join/memberRegist.do")
	  public String memberRegist(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest requset, ModelMap model, HttpSession  session ) throws Exception{
         return "join/MemberRegist";
	}
	
}