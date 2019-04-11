var userId;
var userHead;
var roleType="all";
$(function(){
	console.log("load user");
	//第一次访问时,第一页数据
	findUsers(1);
	//给搜索按钮添加click事件
	$("#user_search_btn").click(function(){
		findUsers(1);
	});
	//给删除model框的确定按钮添加click事件
	$("#delete_user_btn").click(function(e){
		deleteUser();
	});
	//给"详情"标签添加点击事件
	$('#user_tab li:eq(1) a').click(function (e) {
		  e.preventDefault()
		  $(this).tab('show');
		  $("#detailPanel .media").html('请在"列表"中选择一个用户显示详情');
	});
	//给"新增标签添加点击事件"
	$('#user_tab li:eq(2) a').click(function (e) {
		  e.preventDefault()
		  $(this).tab('show');
		  findAllRoleName("#addUserPanel #roleCategory");
	});
	//给添加用户的表单添加submit事件
	$("#addUserPanel form").submit(function(){
		return addUser();
	});
	//给修改的模态框的"确定"添加click事件
	$("#editUser form").submit(function(){
		//alert("aaaaaaaaaaaaaaaaaaaaaaaaa");
		//
		return updateUser();
	});
	
	//给全部变量roleType赋值
	$("#role_type button").click(function(){
		roleType=$(this).html();
		if("全部"==roleType){
			roleType="all";
		}
		$(this).siblings("button").removeClass("active");
		$(this).addClass("active");
		findUsers(1);
	});
	//给导出按钮添加click事件
	$("#export_btn").click(function(){
		window.location.href=basePath+"user/exportuser";
	});
});
function updateUser(){
	
	var loginName=$("#editUser #inputEmail").val();
	
	var password=$("#editUser #inputPassword").val();
	var password2=$("#editUser #inputPassword2").val();
	var nickName=$("#editUser #nickName").val();
	var age=$("#editUser #age").val();
	var sex=$("#editUser input[name=user-type]:checked").val();
	var roleCheckboxs=$("#editUser input[type=checkbox]:checked");
	//alert(loginName+"  "+password+"  "+nickName+"  "+age+"  "+sex);
	var roleIds=[];
	var roleString='';
	//alert(roleCheckboxs)
	
	$(roleCheckboxs).each(function(n,value){
		var roleId=$(value).val();
		roleString+=$(value).next().text()+",";
		roleIds.push(roleId);
	});
	
	
	
	//alert(roleIds);
	if(password!=password2){
		alert("两次密码不相同");
		return false;
	}
	//异步请求更新数据
	$.ajaxFileUpload({
		url:basePath+"user/update",
		secureuri:false,
		fileElementId:"updateHeadPicture",
		type:"post",
		data:{
			    "id":userId,
			    "loginName":loginName,
			    "password":password,
			    "nickName":nickName,
			    "age":age,
			    "sex":sex,
			    "head":userHead,
			    "roleIds":roleIds
			 },
	    traditional:true,
		dataType:"text",
		success:function(data,status){
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE>",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
			$("#editUser").modal("hide");
			$("#update"+userId).parent().parent().find("td:eq(1)").find("a").text(loginName);
			$("#update"+userId).parent().parent().find("td:eq(2)").text(nickName);
			$("#update"+userId).parent().parent().find("td:eq(7)").text(roleString.substr(0,roleString.length-1));
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}
function updateUserClick(id,loginName,password,nickName,age,sex,roleString,head){
	userId=id
	userHead=head;
	//给页面框赋值
	$("#editUser #inputEmail").val(loginName);
	$("#editUser #inputPassword").val(password);
	$("#editUser #inputPassword2").val(password);
	$("#editUser #nickName").val(nickName);
	$("#editUser #age").val(age);
	if("男"==sex){
		//$("#editUser input[name=user-type]:eq(0)").val("男");
		$("#editUser input[name=user-type]:eq(0)").attr("checked","checked");
	}else if("女"==sex){
		//$("#editUser input[name=user-type]:eq(1)").val("女");
		$("#editUser input[name=user-type]:eq(1)").attr("checked","checked");
	}
	//处理角色
	var roleNames=roleString.split(",");
	//查询所有的角色
	$.ajax({
		url:basePath+"role/allroles",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$("#editUser #allRoleName").html("");
				var roles=result.data;
				$(roles).each(function(n,value){
					var flag=false;
					var checkBox1='<input  type="checkbox" name="roleName" value="'+value.id+'"/><span>'+value.name+'</span>&nbsp;&nbsp;';
					var checkBox2='<input  type="checkbox" name="roleName" value="'+value.id+'"  checked="checked"/><span>'+value.name+'</span>&nbsp;&nbsp;';
					
					$(roleNames).each(function(n1,value1){
						if(value1==value.name){
							$("#editUser #allRoleName").append(checkBox2);
							flag=true;
						}
					});
					if(!flag){
						$("#editUser #allRoleName").append(checkBox1);
					}
					if((n+1)%3==0){
						$("#editUser #allRoleName").append("<br />");
					}
				});
				
			}else if(result.status==1){
				var op='<option value="">无角色信息</option>';
				$(loc).append(op);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	
}
//查询所有角色信息并放在loc位置上
function findAllRoleName(loc){
	
		$.ajax({
			url:basePath+"role/allroles",
			type:"get",
			dataType:"json",
			success:function(result){
				if(result.status==0){
					$(loc).html("");
					var roles=result.data;
					
					$(roles).each(function(n,value){
						var op='<option value="'+value.id+'">'+value.name+'</option>';
						$(loc).append(op);
					});
				}else if(result.status==1){
					var op='<option value="">无角色信息</option>';
					$(loc).append(op);
				}
			},
			error:function(){
				alert("请求失败!");
			}
		});
	
	
}
function addUser(){
	//获取页面数据
	var loginName=$("#addUserPanel #inputEmail").val();
	var password1=$("#addUserPanel #inputPassword").val();
	var password2=$("#addUserPanel #inputPassword2").val();
	var nickName=$("#addUserPanel #nickName").val();
	var age=$("#addUserPanel #age").val();
	var roleId=$("#addUserPanel #roleCategory").val();
	var sex=$("#addUserPanel input[name=user-type]:checked").val();
	if(password1!=password2){
		alert("两次密码不同");
		return false;
	}
	if(age<=0){
		$("#addUserPanel #age").focus();
		return false;
	}
	//异步提交
	$.ajaxFileUpload({
		url:basePath+"user/new",
		secureuri:false,
		fileElementId:"addHeadPicture",
		type:"post",
		data:{"loginName":loginName,"password":password1,"nickName":nickName,"age":age,"sex":sex,"roleId":roleId},
		dataType:"text",
		success:function(data,status){
			//alert(data);
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE>",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}
function findUserById(id){
	//alert(id);
	$('#user_tab li:eq(1) a').tab('show');
	$.ajax({
		url:basePath+"/user/detail/"+id,
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$("#detailPanel .media").html("");
				var user=result.data;
				
				var roleString='';
				$(user.roles).each(function(n1,value1){
					roleString+=value1.name+",";
				});
				if(roleString.length==0){
					roleString="无角色信息";
				}else{
					roleString=roleString.substr(0,roleString.length-1);
				}
				
				var userDetail='<div class="media-left">'+
					              '<a href="#">'+
					                '<img class="media-object img-circle" src="head/'+user.head+'" alt="头像">'+
					              '</a>'+
					            '</div>'+
					            '<div class="media-body">'+
					              '<h1 class="media-heading">'+user.loginName+'</h1>'+
					              '<br/>'+
					              '<p>账号类型：<span>'+user.loginType+'</span></p>'+
					              '<p>昵称：<span>'+user.nickName+'</span></p>'+
					              '<p>性别：<span>'+user.sex+'</span></p>'+
					              '<p>年龄：<span>'+user.age+'</span></p>'+
					              '<p>积分：<span>'+user.score+'</span></p>'+
					              '<p>注册日期：<span>'+new Date(user.regDate).toLocaleDateString().replace("/","-").replace("/","-")+'</span></p>'+
					              '<p>锁定：<span>'+user.isLock+'</span></p>'+
					              '<p>角色：<span>'+roleString+'</span></p>'+
					            '</div>';
				$("#detailPanel .media").append(userDetail);
			}
			
		},
		error:function(){
			alert("请求失败!");
		}
		
	});
}
function deleteUserClick(id){
	userId=id;
}
function deleteUser(){
		$.ajax({
			url:basePath+"/user/delete/"+userId,
			type:"delete",
			dataType:"json",
			success:function(result){
				if(result.status==0){
					$("#deluser"+userId).parent().parent().remove();
					alert(result.message);
					
				}
				$("#deletemodal").modal("hide");
				return;
			},
			error:function(){
				alert("请求失败!");
			}
		});
}
function findUsers(currentPage){
	var userKeyword=$("#user_search").val();
	if(userKeyword==""){
		userKeyword="undefined";
	}
	$.ajax({
		url:basePath+"user/page/"+currentPage+"/"+userKeyword+"/"+roleType,
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//清空表格内容
				$("#user_table tbody").html("");
				//清空分页内容
				$("#user_page").html("");
				var users=result.data.data;
				$(users).each(function(n,value){
					var begin='<tr>'+
		                '<td>'+(n+1)+'</td>'+
		                '<td><a href=javascript:findUserById("'+value.id+'")>'+value.loginName+'</a></td>'+
		                '<td>'+value.nickName+'</td>'+
		                '<td>'+value.loginType+'</td>'+
		                '<td>'+value.score+'</td>'+
		                '<td>'+new Date(value.regDate).toLocaleDateString().replace("/","-").replace("/","-")+'</td>'+
		                '<td>'+value.isLock+'</td>'+
		                '<td>';
					
					var roleString='';
					$(value.roles).each(function(n1,value1){
						roleString+=value1.name+",";
					});
					if(roleString.length==0){
						roleString="无";
					}else{
						roleString=roleString.substr(0,roleString.length-1);
					}
					
					var end='</td>'+
		                    '<td>'+
	                           '<a href="" onclick=updateUserClick("'+value.id+'","'+value.loginName+'","'+value.password+'","'+value.nickName+'","'+value.age+'","'+value.sex+'","'+roleString+'","'+value.head+'") id="update'+value.id+'" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
	                           '<a href="" onclick=deleteUserClick("'+value.id+'") id="deluser'+value.id+'" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
	                        '</td>'+
	                      '</tr>';
					
					$("#user_table tbody").append(begin+roleString+end);
				});
				//处理分页条
				var page=result.data;
				if(page.aNum.length>0){
					//处理前一页
					var previous='<li>'+
			            '<a href="javascript:findUsers('+page.previousPage+')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#user_page").append(previous);
					//处理中间页数字
					$(page.aNum).each(function(n,value){
						var middle='<li><a href="javascript:findUsers('+value+')">'+value+'</a></li>';
						$("#user_page").append(middle);
					});
					//处理下一页
					var next='<li>'+
			            '<a href="javascript:findUsers('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#user_page").append(next);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
		
	});
}

