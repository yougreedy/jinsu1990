<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
  response.setHeader("content-Disposition", "attachment; filename=apply.xls");
  response.setHeader("content-Description", "JSP Generated Date");
%>


<!DOCTYPE html>
<html>
<head>
<title>목록</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<style type="text/css">
   body{
     font-size:12px;
     margin-left:0px;
     font-family:돋음;
   }
</style>

</head>
<body>
    <table>
       <thead>
           <tr>
              <th>번호</th>
              <th>신청자명</th>
              <th>연락처</th>
              <th>신청일</th>
           </tr>
       </thead>
       <tbody>
          <c:forEach var="result" items="${resultList}" varStatus="status">
               <tr>
                  <td class="num"><c:out value="${fn:length(resultList) - (status.index)}"/></td>
                  <td><c:out value="${result.chargerNm}"/>(<c:out value="${result.frstRegisterId}"></c:out>)</td>
                  <td><c:out value="${result.telno}"/></td>
                  <td><c:out value="${result.email}"/></td>
                  <td><fmt:formatDate value="${result.frstRegistPnttm}" pattern="yyyy-MM-dd"/></td>
               </tr>
          </c:forEach>
          <c:if test="${fn:length(resultList) == 0}">
              <tr>
                 <td colspan="5">
                    신청자가 없습니다.
                 </td>
              </tr>
          </c:if>
       </tbody>
    </table>


</body>
</html>





















