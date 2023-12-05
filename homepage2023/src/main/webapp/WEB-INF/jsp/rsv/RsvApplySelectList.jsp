<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko" >
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>한국폴리텍 예약관리</title>
<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>

<%-- 기본 URL --%>
<c:url var="_BASE_PARAM" value="">
  	<c:param name="searchCondition" value="${searchVO.searchCondition}" />
  	<c:if test="${not empty searchVO.searchKeyword}"><c:param name="searchKeyword" value="${searchVO.searchKeyword}" /></c:if>
</c:url>

<!-- content 시작 -->
<div id="content">
   <div class="container">
       <div id="contents">
           
           <%-- 목록영역 --%>
           <div id="bbs_wrap">
              <div class="total">
                 총예약수
                 <strong><c:out value="${fn:length(resultList)}"></c:out></strong>건 
              </div>
              <div class="bbs_list">
                 <table class="list_table">
                   <thead>
                       <tr>
                          <th class="num" scope="col">번호</th>
                          <th scope="col">프로그램명</th>
                          <th scope="col">신청자명</th>
                          <th scope="col">신청일</th>
                          <th scope="col">신청상태</th>
                          <th scope="col">승인&반려일</th>
                          <th scope="col">관리</th>
                       </tr>
                   </thead>
                   <tbody>
                     <c:forEach var="result" items="${resultList}" varStatus="status">
                         <tr>
                           <td class="num"><c:out value="${fn:length(resultList) - (status.index)}"/></td>
                           <td>
                              <c:url var="rsvViewUrl" value="/rsv/rsvSelect.do${_BASE_PARAM}">
                                  <c:param name="resveId" value="${result.resveId}"/>
                              </c:url>
                              <a href="${rsvViewUrl}" target="_blank">
                                  <c:out value="${result.resveSj}"/>
                              </a>
                           </td>
                           <td>
                             <c:url var="viewUrl" value="/rsv/rsvApplySelect.do${_BASE_PARAM}">
                                <c:param name="resveId" value="${result.resveId}"/>
                                <c:param name="reqstId" value="${result.reqstId}"/>
                             </c:url>
                             <a href="${viewUrl}">
                                <c:out value="${result.chargerNm}"/>(<c:out value="${result.frstRegisterId}"/>)
                             </a>
                           </td>
                           <td><fmt:formatDate value="${result.frstRegistPnttm}" pattern="yyyy-MM-dd"/></td>
                           <td>
                             <c:choose>
                                <c:when test="${result.confmSeCode eq 'R'}">신청 접수 중</c:when>
                                <c:when test="${result.confmSeCode eq 'O'}">신청 승인</c:when>
                                <c:when test="${result.confmSeCode eq 'X'}">신청 관리</c:when>
                             </c:choose>
                           </td>
                           <td>
                              <c:choose>
                                 <c:when test="${result.confmSeCode ne 'R'}"><fmt:formatDate value="${result.confmPnttm}" pattern="yyyy-MM-dd"/></c:when>
                                  <c:otherwise></c:otherwise>
                              </c:choose>
                           </td>
                           <td>
                              <c:url var="deleteUrl" value="/rsv/rsvApplyDelete.do${_BASE_PARAM}">
                                 <c:param name="reqstId" value="${result.reqstId}"/>
                                 <c:param name="pageIndex" value="${searchVO.pageIndex}"></c:param>
                              </c:url>
                              <a href="${deleteUrl}" class="btn spot btn-del" data-status="${result.confmSeCode}">삭제</a>
                           </td>
                         </tr>
                     </c:forEach>
                    <%-- 글이없을 경우 --%>
                    <c:if test="${fn:length(resultList) == 0}">
                        <tr class="empty"><td colspan="7">신청내역이없습니다.</td></tr>
                    </c:if>
                   </tbody>
                 </table>
              </div>
              <div class="btn-cont ar">
                 <c:url var="listUrl" value="/rsv/selectList.do${_BASE_PARAM}"/>
                 <a href="${listUrl}" class="btn">목록</a>
              </div>
           </div>
       </div>
   </div>
</div>
<!-- //content 끝 -->

<script>
  <c:if test="${not empty message}">
     alert("${message}")
  </c:if>
     
 $(document).ready(function(){
	 $(".btn-del").click(function(){
	 	 var status = $(this).data("status");
	     if(status == "O"){
	    	 alert("신청 승인 상태에서는 삭제하실 수 없습니다.");
	    	 return false;
	     }else if(status == "X"){
	    	 alert("신청 반려 상태에서는 삭제하실 수 없습니다.");
	    	 return false;
	     }
	 });
 });    
</script>
</body>
</html>