<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko" >
<meta http-equiv="X-UA-Compatible" content="IE=dege" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>회원</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>

<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />

<%-- 기본 URL --%>
<c:url var="_BASE_PARAM" value="">
	<c:if test="${not empty searchVO.searchCondition}"><c:param name="searchCondition" value="${searchVO.searchCondition}" /></c:if>
	<c:if test="${not empty searchVO.searchKeyword}"><c:param name="searchKeyword" value="${searchVO.searchKeyword}" /></c:if>
</c:url>

<div class="container">
	<div id="contents">
		<%-- 목록영역 --%>
		<div id="bbs_wrap">
			<div class="total">
				총 게시물
				<strong><c:out value="${paginationInfo.totalRecordCount}"/></strong>건 |
				현재페이지<strong><c:out value="${paginationInfo.currentPageNo}"/></strong>/
				<c:out value="${paginationInfo.totalPageCount}"/>
			</div>
			
			<div class="bss_list">
				<table class="list_table">
					<thead>
						<tr>
							<th class="num" scope="col">번호</th>
							<th scope="col">회원아이디</th>
							<th scope="col">회원명</th>
							<th scope="col">가입일</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td><c:out value="${paginationInfo.totalRecordCount - ((searchVO.pageIndex-1) * searchVO.pageUnit) - (status.count -1)}" /></td>
								<td><c:out value="${result.emplyrId}"/></td>
								<td><c:out value="${result.userNm}"/></td>
								<td><fmt:formatDate value="${result.sbscrbDe}" pattern="yyyy-MM-dd"/></td>
								<td>
									<c:url var="viewUrl" value="/admin/member/memberRegist.do${_BASE_PARAM}">
										<c:param name="esntlId" value="${result.esntlId}"/>
										<c:param name="pageIndex" value="${searchVO.pageIndex}" />
									</c:url>
									<a href="${viewUrl}" class="btn spot">수정</a>
								</td>
							</tr>
						</c:forEach>
								
						<%-- 결과 값이 없을 경우 --%>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="empty"><td colspan="5">검색 데이터가 없습니다.</td></tr>
						</c:if>
					</tbody>	
				</table>
			</div>
			
			<div id="paging">
				<c:url var="pageUrl" value="/admin/member/memberList.do${_BASE_PARAM}"/>
				<c:set var="pagingParam"><c:out value="${pageUrl}"/></c:set>
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="${pagingParam}"/>
			</div>
		</div>
	</div>
</div>

</body>
</html>