package egovframework.let.member.service.impl;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.let.member.service.MemberVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("memberMapper")
public interface MemberMapper {

	//회원id찻기
	public MemberVO findId(MemberVO vo)throws Exception;

	//회원비빌번호잦기
	public MemberVO findPassword(MemberVO vo)throws Exception;

	//회원비밀번호업데이트
	public void passwordUpdate(MemberVO vo)throws Exception;
  
	//회원목록
	public List<EgovMap> selectMberList(MemberVO vo)throws Exception;

	//회원목록 수
	public int selectMberListCnt(MemberVO vo)throws Exception;

	//회원상세
	public EgovMap selectMber(MemberVO vo)throws Exception;

	
	
}
