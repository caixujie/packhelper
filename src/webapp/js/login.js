$(function(){
	$("#inputName").val(getCookie("loginName"));
	$(".container form").submit(function(e){
		
		return login_form();
	});
});
//登录方法
function login_form(){
	//获取表单中的用户名和密码
	var lName=$("#inputName").val();
	var lPwd=$("#inputPassword").val();
	var remember=$(".container form input[type=checkbox]").get(0).checked;
	//alert(lName+"   "+lPwd);
	//console.log(lName+"   "+lPwd);
	//断点跟踪
	$.ajax({
		url:basePath+"user/login/"+lName+"/"+lPwd,
		type:"get",
		dataType:"json",
		success:function(result){
			//result是服务端返回的数据
			if(result.status==0){
				window.location.href="index.html";
				if(remember){
					addCookie("loginName",lName,5);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}