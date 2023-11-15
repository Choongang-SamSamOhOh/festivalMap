<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PhotoEventBoardDetail</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
	crossorigin="anonymous">
	
</script>

<link href="/css/board.css" rel="stylesheet" type="text/css">

<!-- jQuery 라이브러리 불러오기 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
	function deleteAndRedirect(id, smallCode, userId) {
	    $.ajax({
	        url: 'boardDelete?id=' + id + '&smallCode=' + smallCode + '&userId=' + userId,
	        method: 'GET',
	        success: function () {
	            // 삭제 요청이 성공 후 부모 창 redirect
	           	if (smallCode === 5) {
	                // 이벤트 게시판으로 리디렉션
	                window.opener.location.href = '/eventBoardList';
	            } else {
	                // 그 외의 경우, 포토 게시판으로 리디렉션
	                window.opener.location.href = '/photoBoardList';
	            }
		        window.close();
	        }
	    });
	}
	
	function updateBoard(boardId) {
	    window.opener.location.href = 'boardUpdateForm?id=' + boardId;
	    window.close();
	}

	function closeAndRedirect(smallCode) {
	    $.ajax({
	        url: '/',
	        method: 'GET',
	        success: function () {
	            // 취소 버튼 실행 시 이전페이지 이동 + 새로고침
	           	if (smallCode === 4) {
	                // 포토게시판으로 리디렉션
	                window.opener.location.href = '/photoBoardList';
	            } else if(smallCode === 5) {
	            	// 이벤트게시판으로 리디렉션
	                window.opener.location.href = '/eventBoardList';
	            } else{
	            	// 기본 & 오류 처리
	            	window.opener.location.href = '/home';
	            }
	           	window.close();
	        }
	    });
	}
	<!-- 게시판 신고기능 -송환 -->
	function report(boardId) {
	    window.open("reportBoardFoam?boardId=" + boardId, "_blank", "width=600, height=400, top=100, left=100");
	}
</script>
</head>
<body>
	<!-- 전체 content 영역  Start-->
	<div class="container p-0" style="width: 100%;">
	
		<div class="container p-3"> 
			<div class="row row-cols-5 align-items-center division_photo_custom">
				<div class="col-md-1">글번호</div>
				<div class="col-md-6">제목</div>
				<div class="col-md-2">작성자</div>
				<div class="col-md-2">작성일</div>
				<div class="col-md-1">조회수</div>			
			</div>		
		</div>
		
		<div class="container p-3"> 
			<div class="row row-cols-5 align-items-center list_photo_custom">
				<div class="col-md-1">1</div>
				<div class="col-md-6">${board.title }</div>
				<div class="col-md-2">${board.name }</div>
				<div class="col-md-2">
					<fmt:formatDate value="${board.created_at }" type="date"
									pattern="YYYY.MM.dd"/>				
				</div>
				<div class="col-md-1">${board.read_count }</div>			
			</div>		
		</div>	
		
	</div>
	
	<div class="container p-3 border">
		이미지 영역
	</div>
	
	<div class="container p-3 border">
		content
	</div>
	
	<div class="container p-3 border">
		해시태그
	</div>
	
	<div class="text-center">
		<button class="btn btn-primary" onclick="updateBoard(${board.id})">수정</button>
		<button class="btn btn-warning" onclick="deleteAndRedirect(${board.id}, ${board.small_code}, ${userId })">삭제</button>
		<button class="btn btn-secondary" onclick="closeAndRedirect(${board.small_code })">취소</button>
		<!-- 게시판 신고기능 -송환 -->
		<button class="btn btn-danger" onclick="report(${board.id})">신고</button>
	</div>
</body>
</html>