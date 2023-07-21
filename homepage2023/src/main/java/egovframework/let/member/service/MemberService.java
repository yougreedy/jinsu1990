package egovframework.let.member.service;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;



public interface MemberService {
   

    //회원id찻기
	public MemberVO findId(MemberVO vo)throws Exception;

	//회원비빌번호찾기
	public MemberVO findPassword(MemberVO vo)throws Exception;

	//회원비밀번호업데이트
	public void passwordUpdate(MemberVO vo)throws Exception;

	



}
