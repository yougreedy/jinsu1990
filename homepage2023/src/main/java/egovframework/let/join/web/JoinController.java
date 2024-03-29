package egovframework.let.join.web;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.api.naver.service.NaverLoginService;
import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import net.sf.json.JSONObject;


@Controller
public class JoinController {
	
	@Resource(name = "joinService")
	private JoinService joinService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "naverLoginService")
	private NaverLoginService naverLoginService;
	
	//약관동의
	@RequestMapping(value = "/join/siteUseAgree.do")
	public String siteUseAgree(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		
		return "join/SiteUseAgree";
	}
	
	//회원구분
	@RequestMapping(value = "/join/memberType.do")
	public String memberType(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		
		//Naver
		String domain = request.getServerName();
		String port = Integer.toString(request.getServerPort());
		String naverAutUrl = naverLoginService.getAuthorizationUrl(session, domain, port);
		model.addAttribute("naverAuthUrl", naverAutUrl);
		
		return "join/MemberType";
	}
	
	//회원등록 폼
	@RequestMapping(value = "/join/memberRegist.do")
	public String memberRegist(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model/*, HttpSession session*/) throws Exception {
		
		return "join/MemberRegist";
	}
	
	//아이디 중복체크
	@RequestMapping(value= "/join/duplicateCheck.do")
	public void duplicateCheck(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String successYn = "Y";
		String message = "성공";
		
		JSONObject jo = new JSONObject();
		response.setContentType("application/json; charset=utf-8");
		
		int duplicateCnt = joinService.duplicateCheck(vo);
		if(duplicateCnt > 0) {
			successYn = "N";
			message = egovMessageSource.getMessage("fail.duplicate.member"); //이미 사용중인 ID입니다.;
		}
		

		jo.put("successYn", successYn);
		jo.put("message", message);

		PrintWriter printwriter = response.getWriter();
		printwriter.println(jo.toString());
		printwriter.flush();
		printwriter.close();
	}
	
	//회원가입
	@RequestMapping(value="/join/insertMember.do")
	public String insertMember(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model) throws Exception{
		if(!EgovStringUtil.isEmpty(vo.getLoginType())) {
			//일반가입을 제외하고는 ID값을 SNS명 + '-' + ID값
			if(!("nomal").equals(vo.getLoginType())) {
				vo.setEmplyrId(vo.getLoginType() + "-" + vo.getEmplyrId());
				vo.setPassword("");
				vo.setPasswordHint("sns가입자");
				vo.setPasswordCnsr("sns가입자");
			}
		}
		
		if(joinService.duplicateCheck(vo) > 0) {
			if(!("nomal").equals(vo.getLoginType())) {
				model.addAttribute("message", "이디 등록 된 sns계정입니다.");
			}else {
			model.addAttribute("message", egovMessageSource.getMessage("fail.duplicate.member")); //이미 사용중인 ID입니다.
			}
			return "forward:/join/memberType.do";
		} else {
			joinService.insertJoin(vo);
			model.addAttribute("message", egovMessageSource.getMessage("join.request.msg")); //회원신청이 정상적으로 완료되었습니다. \n로그인 후 이용해 주세요.
		}
		
		return "join/MemberComplete";
	}
	
	
	
}


