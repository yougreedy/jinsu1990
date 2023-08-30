package egovframework.let.test.service;

import java.io.Serializable;

import egovframework.com.cmm.ComDefaultVO;

public class TestVO extends ComDefaultVO implements Serializable {
  
    //test id
	private String testId;
	//test 제목 
	private String sj;
	//test 내용
	private String cn;
	//test 작성자
    private String userNm;
    //최초등록시점 
    private java.util.Date frstRegistPnttm;
    //최도등록자id
    private String frstRegisterId;  //FRST_REGISTER_ID
    //최종수점자시점
    private java.util.Date lastUpdtPnttm;
    //최종수정자id
    private String lastUpdusrId;
    
    //로그인 아이디 
    private String userId;
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public java.util.Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}
    
    

	  
    
    
    
    
    
    
    
    
    
}
