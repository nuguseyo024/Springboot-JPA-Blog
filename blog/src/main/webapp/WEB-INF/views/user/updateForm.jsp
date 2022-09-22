<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/user/join" method="POST">
		<input type="hidden" id="id" value="${principal.user.id }" />
		<div class="form-group">
			<label for="username">Username :</label> <input value="${principal.user.username }" type="text" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>


		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="email">Email address:</label> <input value="${principal.user.email }" type="email" class="form-control" placeholder="Enter email" id="email">
			</div>

		</c:if>

		<c:if test="${not empty principal.user.oauth }">
			<div class="form-group">
				<label for="email">Email address:</label> <input value="${principal.user.email }" type="email" class="form-control" placeholder="Enter email" id="email" readonly>
			</div>
		</c:if>

	</form>
	<button id="btn-update" class="btn btn-primary">회원정보 수정</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

