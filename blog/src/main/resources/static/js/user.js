let index = {
	init: function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
	},
	
	save:function(){
		//alert('user의 save함수 호출됨 ');
		let data ={
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
			
		};
		// console.log(data);$
		
		// ajax 통신을 이용해 3개의 데이터를 json 으로 변경하여 insert 요청 
		// ajax 호출 시 default 가 비동기 호출 
		$.ajax({
			// 회원가입 수행 요청 
			type:"POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			// body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 
		}).done(function(resp){
			// 응답 결과가 정상이면 done 실행 
			alert("회원가입이 완료되었습니다. ");
			console.log(resp);
			location.href="/blog";
		}).fail(function(error){
			// 응답 결과가 비정상이면 fail 실행 
			alert(JSON.stringify(error));
		});
		
	}  
}
index.init();