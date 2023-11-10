<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/components/AdminHeader.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script type="text/javascript">
			function tagDelete(pIndex) {
				var deleteId = $('#id'+pIndex).val();
				if(confirm("정말 삭제하시겠습니까?")) {
					$.ajax(
							{
								method:"POST",
								url:"<%=request.getContextPath()%>/admin/tag/deleteTags",
								data:{id : deleteId},
								dataType:'text',
								success:
									function(result) {
											if(result == '1') {
												$('#tag'+pIndex).remove();
												alert("성공적으로 삭제되었습니다.");
												location.reload();
											} else {
												alert("삭제에 실패하였습니다.");
											}		
									}
							}		
					)
				}
			}
		</script>
	</head>
	<body>
		<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/components/AdminSideBar.jsp" %>
			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 overflow-auto">
			
				<!-- Section1: Title -->
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="border">태그 관리</h1>
				</div>
		
				<!-- Section2: Search Form -->		
				<div class="border p-3 m-3">
					<form action="list">
						검색어<input type="text" name="keyword" placeholder="검색어를 입력해주세요.">
						<button type="submit" class="btn btn-outline-secondary">검색</button>
						<button type="reset" class="btn btn-outline-secondary">초기화</button>
					</form>	
				</div>		
				
				<!-- Section3: Table -->		
				<div class="border p-3 m-3">
					<button type="button" class="btn btn-outline-secondary" onclick="location.href='insertTagsForm'">등록</button>
					<table class="table table-striped table-sm">
						<thead>
							<tr>
								<th scope="col">순번</th>
								<th scope="col">태그명</th>
								<th scope="col">수정</th>
								<th scope="col">삭제</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="num" value="${page.start}"/>
							<c:forEach var="tag" items="${listTags}" varStatus="st">
								<tr id="tag${st.index}">
									<td><input type="hidden" value="${tag.id}" id="id${st.index}">${num}</td>
									<td>${tag.name}</td>
									<td><input type="button" value="수정" onclick="location.href='updateTagsForm?id=${tag.id}'"></td>
									<td><input type="button" value="삭제" onclick="tagDelete(${st.index})"></td>
								</tr>
								<c:set var="num" value="${num + 1}"/>
							</c:forEach>
						</tbody>
					</table>
					<p>총 건수 : ${totalTags}</p>
					
					<div align="center">
						<c:if test="${page.startPage > page.pageBlock}">
							<a href="list?currentPage=${page.startPage-page.pageBlock}" class="pageblock">[이전]</a>
						</c:if>
						<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
							<a href="list?currentPage=${i}" class="pageblock">[${i}]</a>
						</c:forEach>
						<c:if test="${page.endPage < page.totalPage}">
							<a href="list?currentPage=${page.startPage+page.pageBlock}" class="pageblock">[다음]</a>
						</c:if>
					</div>	
				</div>	
			</main>
		</div>
		</div>
	</body>
</html>