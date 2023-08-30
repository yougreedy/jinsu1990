package egovframework.let.test.service.impl;

import java.util.List;

import egovframework.let.test.service.TestVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("testMapper")
public interface TestMapper {

	//test 목록 가져오기
    public List<EgovMap> selectTestList(TestVO vo)throws Exception;
	//test 목록수
    public int selectTestListCnt(TestVO vo)throws Exception;
	//test 가져오기
    public TestVO selectTest(TestVO vo)throws Exception;
	//test 등록
    public void insertTest(TestVO vo)throws Exception;
	//test 수정
    public void updateTest(TestVO vo)throws Exception;
	//test 삭제
    public void deleteTest(TestVO vo)throws Exception;
  
   
}
