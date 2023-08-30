package egovframework.let.admin.member.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.crud.service.CrudVO;
import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.login.service.LoginService;
import egovframework.let.mail.service.MailService;
import egovframework.let.member.service.MemberService;
import egovframework.let.member.service.MemberVO;
import egovframework.let.utl.fcc.service.EgovNumberUtil;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.fcc.service.FileMngUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import net.sf.json.JSONObject;



@Controller
public class MemberManageController {
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "mailService")
	MailService mailService;
	
	//회원목록
	@RequestMapping(value = "/admin/member/memberList.do")
	public String memeber(@ModelAttribute("searchVO") MemberVO vo, HttpServletRequest request, ModelMap model, HttpSession seddion) throws Exception{
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); //관리자 아니면 팅김, 코드는 현재 로그인한 사용자의 정보를 가져오는 역할
		if(!"admin".equals(user.getId())) { //"admin"이 아닌 경우를 확인하는 조건문
			return "redirect:/";
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		paginationInfo.setPageSize(vo.getPageSize());
		
		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
        vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
        
        List<EgovMap> resultList = memberService.selectMberList(vo);
  	    model.addAttribute("resultList", resultList);
  	  
  	    int totCnt = memberService.selectMberListCnt(vo); //페이지네이션 하기 위해서 
  	    
  	    paginationInfo.setTotalRecordCount(totCnt);
  	    model.addAttribute("paginationInfo", paginationInfo);
        
        return "/admin/member/MemberList";
		
	}
	
	//회원상세
	@RequestMapping(value = "/admin/member/memberRegist.do")
	public String memeberRegist(@ModelAttribute("searchVO") MemberVO vo, HttpServletRequest request, ModelMap model, HttpSession seddion) throws Exception{
	   LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	   if(!"admin".equals(user.getId())) {
		   return "redirect:/";
	   }
	   
	   EgovMap result = memberService.selectMber(vo);
	   model.addAttribute("result", result);
	   
	return "/admin/member/MemberRegist";
	
	}
	
	//비밀번호 랜덤 재발급
	@RequestMapping(value = "/admin/member/changePwRandom.do")
	public void changePwRandom(@ModelAttribute("searchVO") MemberVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String successYn = "Y";
		String message ="성공";
		
		JSONObject jo = new JSONObject(); //JSON 형태의 객체를 생성합니다. 이 객체는 재발급 결과와 메시지를 담아 클라이언트에 반환할 용도로 사용
		response.setContentType("application/json; charset-utf-8");
		
		if(EgovStringUtil.isEmpty(vo.getEmplyrId()) || EgovStringUtil.isEmpty(vo.getEsntlId())) {
			successYn = "N";
			message = "ID가 없습니다.";
		}else {
			//임시데이터 비밀번호를 생성(영+영+숫+명+명+숫=6자리)
			String newpassword = "";
			for(int i = 1; i <=6; i++) {
				//영자
				if(i % 3 != 0) {
					newpassword += EgovStringUtil.getRandomStr('a', 'z');
			    //숫자		
				}else {
					newpassword += EgovNumberUtil.getRandomNum(0, 9);
				}
			}
			vo.setPassword(newpassword);
	        memberService.passwordUpdate(vo);//자동으로 업테이드 좋음점 보안이 좋다. 
	        
	        //암호화 변경 후 메일전송
	        String title ="비밀번호 변경";
	        String content = "변경 된 비밀번호 (" + newpassword + ") 입니다.";
	        
	        Session session = mailService.mailSetting(new Properties());
	        mailService.sendMail(session, title, content, vo.getEmailAdres());
		}
		
		
		jo.put("successYn", successYn);
		jo.put("message", message);
		
		PrintWriter printWriter = response.getWriter();
		printWriter.println(jo.toString());
		printWriter.flush();
		printWriter.close();
		
		
	}
}
	
	
	






