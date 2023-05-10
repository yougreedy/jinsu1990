package egovframework.let.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.crud.service.CrudVO;
import egovframework.let.crud.service.impl.CrudMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService{
   
	  @Resource(name = "boardMapper")
	  private BoardMapper boardMapper;
	  
	  @Resource(name ="egovBoardIdGnrService")
	  private EgovIdGnrService idgenService;
  
	  //게시물 목록 가져오기
	  public List<EgovMap> selectBoardList(BoardVO vo) throws Exception{
		  return boardMapper.selectBoardList(vo);
	  }
	  
	  //게시물 목록수 
	  public int selectBoardListCnt(BoardVO vo) throws Exception{
		  return boardMapper.selectBoardListCnt(vo);
	  }
		 
}
