<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/components/AdminHeader.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>festival content</title>
	</head>
	<body>
		<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/components/AdminSideBar.jsp" %>
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			
				<!-- Section1: Title -->
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="border">지역정보 - 명소</h1>
				</div>
		
				<!-- Section2: Search Form -->		
				<div class="border p-3 m-3">
					<h1 class="border">검색폼</h1>
					<button type="button" class="btn btn-outline-secondary">검색</button>
					<button type="button" class="btn btn-outline-secondary">초기화</button>
				</div>		
				
				<!-- Section3: Table -->		
				<div class="border p-3 m-3">
					<button type="button" class="btn btn-outline-secondary">등록</button>
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th scope="col">순번</th>
								<th scope="col">테마</th>
								<th scope="col">명소명</th>
								<th scope="col">주소</th>
								<th scope="col">신청일</th>
								<th scope="col">승인여부</th>
								<th scope="col">수정</th>
								<th scope="col">삭제</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="num" value="${page.start}"/>
							<c:forEach var="spot" items="${listSpot}">
								<tr>
									<td>${num}</td>
									<td>${spot.cc_content}</td>
									<td>${spot.title}</td>
									<td>${spot.address}</td>
									<td><fmt:formatDate value="${spot.created_at}" type="date" pattern="YY/MM/dd"/></td> <!-- 신청일 컬럼?? -->
									<td>
										<c:if test="${spot.status == 0}">승인대기</c:if>
										<c:if test="${spot.status == 1}">승인완료</c:if>
										<!-- 승인반려됐을 경우 status -->
									</td>
									<td><input type="button" value="수정"></td>
									<td><input type="button" value="삭제"></td>
								</tr>
								<c:set var="num" value="${num + 1}"/>
							</c:forEach>
						</tbody>
				</table>
			<div align="center">
				<c:if test="${page.startPage > page.pageBlock}">
					<a href="spot?currentPage=${page.startPage-page.pageBlock}">[이전]</a>
				</c:if>
				<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
					<a href="spot?currentPage=${i}">[${i}]</a>
				</c:forEach>
				<c:if test="${page.endPage < page.totalPage}">
					<a href="spot?currentPage=${page.startPage+page.pageBlock}">[다음]</a>
				</c:if>
			</div>
			</div>		
		</main>
	</div>
	</div>
</body>
</html>