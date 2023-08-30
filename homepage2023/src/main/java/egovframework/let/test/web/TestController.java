package egovframework.let.test.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.test.service.TestService;
import egovframework.let.test.service.TestVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class TestController {
  
	@Resource(name = "testService")
	private TestService testService;
	
	
	
	//test 목록 가져오기
	@RequestMapping(value = "/test/selectList.do")
    public String selectTestList(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
    		throws Exception {
		
		    PaginationInfo paginationInfo = new PaginationInfo();
		    paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		    paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		    paginationInfo.setPageSize(searchVO.getPageSize());

		    searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		    searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
	        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	        
	        //test 목록수
	        int total = testService.selectTestListCnt(searchVO);
	        paginationInfo.setTotalRecordCount(total);
	        model.addAttribute("paginationInfo",paginationInfo);
		
		
		    List<EgovMap> resultList = testService.selectTestList(searchVO);
		    model.addAttribute("resultList", resultList);
				return "test/TestSelectList";
    	
    } 
	
	//test 가져오기
	@RequestMapping(value = "/test/select.do")
    public String selectTest(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
    		throws Exception {
				TestVO result = testService.selectTest(searchVO);
				model.addAttribute("result", result);
		
		
		return "test/TestSelect";
		
	}
	
	//test 등록/수정
	@RequestMapping(value = "/test/regist.do")
    public String registTest(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
    		throws Exception{
		     TestVO result = new TestVO();
		     if(!EgovStringUtil.isEmpty(searchVO.getTestId())) {
		    	 result = testService.selectTest(searchVO);
		     }
		     model.addAttribute("result", result);
		
				return "test/TestRegist";
		
	}
	
	//test 등록
	@RequestMapping(value = "/test/insert.do")
	 public String insertTest(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
	         throws Exception{
			      testService.insertTest(searchVO);
			      LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); 
				  if(user == null || user.getId() == null) {
					  model.addAttribute("message", "로그인 후 사용가능합니다.");
			          return "forward:/test/selectList.do";
				  } else {
					  model.addAttribute("USER_INFO", user);
				  }
			      
			      
				return "forward:/test/selectList.do";
			
		}
	
	
	//test 수정
	@RequestMapping(value = "/test/update.do")
    public String updateTest(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
    		throws Exception{
		         testService.updateTest(searchVO);
		
		         LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); 
				  if(user == null || user.getId() == null) {
					  model.addAttribute("message", "로그인 후 사용가능합니다.");
			          return "forward:/test/selectList.do";
				  } else {
					  model.addAttribute("USER_INFO", user);
				  }
		         
				return "forward:/test/selectList.do";
		
	}
	
	//test 삭제
	@RequestMapping(value = "/test/delete.do")
    public String deleteTest(@ModelAttribute("searchVO")TestVO searchVO, HttpServletRequest request, ModelMap model)
    		throws Exception{
		         testService.deleteTest(searchVO);
		
				return "forward:/test/selectList.do";
		
	}
 	
	
}



















