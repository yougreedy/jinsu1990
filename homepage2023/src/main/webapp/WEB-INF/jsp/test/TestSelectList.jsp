<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>태스트 목록 가져오기</title>
</head>
 <style>
   table, th, td{
     border: 1px solid #ccc;
     border-collapse: collapse;
   }
   
    th, td{
     padding: 5px; 
   }
</style>
<body>
 <table>
    <thead>
       <tr>
         <th>ID</th>
         <th>제목</th>
         <th>작성자</th>
         <th>작성일</th>
         <th>수정일</th>
         <th>관리</th>
       </tr>
    </thead>
    <tbody>
       <c:forEach var="result" items="${resultList}">
         <tr>
           <td><c:out value="${result.testId}"></c:out></td>
           <td>
              <c:url var="viewUrl" value="/test/select.do">
                <c:param name="testId" value="${result.testId}"></c:param>
              </c:url>
              <a href="${viewUrl}" ><c:out value="${result.sj}"></c:out></a>
           </td>
           <td><c:out value="${result.userNm}"></c:out></td>
            <td><c:out value="${result.frstRegistPnttm}"/></td>
           <td><c:out value="${result.lastUpdtPnttm}"/></td>
           <td>
             <c:url var="delUrl" value="/test/delete.do">
               <c:param name="testId" value="${result.testId}"></c:param>
             </c:url>
             <a href="${delUrl}" class="btn-del">삭제</a>
           </td>
         </tr>
       </c:forEach>
    </tbody>
 </table>
  <div id = "paging_div">
     <ul class="paging_align">
       <c:url var="page_Url" value="/test/selectList.do"></c:url>
       <c:set var="pagingParam"><c:out value="${page_Url}"/></c:set>
       <ui:pagination paginationInfo="${paginationInfo}" type="image"  jsFunction="${pagingParam}"/>     
     </ul>
  </div>
  <a href="/test/regist.do">글쓰기</a>
  <script>
 
  <c:if test="${not empty message}">
  	alert("${message}");
  </c:if>
  
    $(document).ready(function() {
		$(".btn-del").click(function() {
			if(!confirm("삭제하시겠습니까!") ){
				return false;
			}
			
		});
	});
  </script>
  
</body>
</html>
 
















































