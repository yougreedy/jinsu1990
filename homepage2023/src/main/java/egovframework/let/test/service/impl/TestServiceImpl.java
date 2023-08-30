package egovframework.let.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.test.service.TestService;
import egovframework.let.test.service.TestVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("testService")
public class TestServiceImpl extends EgovAbstractServiceImpl implements TestService{
	
	@Resource(name ="testMapper")
	private TestMapper testMapper;
	
	@Resource(name = "egovtestIdGnrService")
	private EgovIdGnrService idgenService;

	
	//test 목록 가져오기
	@Override
	public List<EgovMap> selectTestList(TestVO vo) throws Exception {
		return testMapper.selectTestList(vo);
	}

	//test 목록수
	@Override
	public int selectTestListCnt(TestVO vo) throws Exception {
		return testMapper.selectTestListCnt(vo);
	}

	//test 가져오기
	@Override
	public TestVO selectTest(TestVO vo) throws Exception {
		return testMapper.selectTest(vo);
	}

	//test 등록
	@Override
	public void insertTest(TestVO vo) throws Exception {
	     String id = idgenService.getNextStringId();
		 vo.setTestId(id);
	     testMapper.insertTest(vo);
	}

	//test 수정
	@Override
	public void updateTest(TestVO vo) throws Exception {
         testMapper.updateTest(vo);
		
	}

	//test 삭제
	@Override
	public void deleteTest(TestVO vo) throws Exception {
         testMapper.deleteTest(vo);	
	}
	
	
	
  

  
  
  
  
		 
}
