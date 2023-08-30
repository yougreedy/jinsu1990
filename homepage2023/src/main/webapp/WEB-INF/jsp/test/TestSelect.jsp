<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>테스트 가져오기</title>
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
      <tr>
        <th>제목</th>
        <td>${result.sj}</td>
      </tr>
      <tr>
        <th>작성자</th>
        <td>${result.userNm}</td>
      </tr>
      <tr>
        <th>작성일</th>
       <td> <fmt:formatDate value="${result.frstRegistPnttm}" pattern="yyyy-MM-dd"/></td>
      </tr>
      <tr>
        <th>내용</th>
        <td>${result.cn}</td>
      </tr>
   </table>
   
    <div class="box-btn">
      <c:url var="uptUrl" value="/test/regist.do">
          <c:param name="testId" value="${result.testId}"></c:param>
      </c:url>
      <a href="${uptUrl}">수정</a>
      
      <c:url var="delUrl" value="/test/delete.do">
         <c:param name="testId" value="${result.testId}"></c:param>
      </c:url>
      <a href="${delUrl}" class="btn-del">삭제</a>
      
      <a href="/test/selectList.do">목록</a>
   </div>
   <script>
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































