//@ sourceURL=role.js
alert("in this js");
var roleId;
var roleName;

function deleteRoleClick(uid){
	roleId=uid;
	alert(uid);
}
//给编辑的超链接的click事件方法
function updateRoleClick(uid,name,version,filetype){
	roleId=uid;
	roleName=name;
    var dom=getRoleNode_tr(roleId,"urole");
	$("#editRole #caonimaM").val(name);
	$("#editRole #versM").val(version);
	$("#editRole #packFileTypeM").val(filetype);
	$("#editRole #descM").val(dom.find("td:eq(6)").html());

}
$(function(){

	findRoles(1);
	//给模糊查询按钮添加click事件
	$("#search_button").click(function(){
		return findRoles(1);
	});
	//给新增角色表单添加submit事件
	$("#addUserPanel form").submit(function(){
		return addRole();
	});
	//给删除的model框的确认按钮添加click事件
	$("#deleterole_button").click(function(){
		var dom=getRoleNode_tr(roleId,"delRole");
		return deleteRole(roleId,dom);
	});
	//给修改的modal框的表单添加submit事件
	$("#editRole form").submit(function(){
		var dom=getRoleNode_tr(roleId,"urole");

		return updateRole(roleId,dom);
	});
	//给文件上传的input标签添加一个change事件
	$("#fileUpload").change(function(){
	alert("in change")
	    return cbMassage();
	});
});

//更新角色
function updateRole(roleId,dom){
    var checkbox1="#addUserPanel input:checkbox[name='supsList']:checked";
	//获取要更新的数据
	var mytest = {
            name : $("#editRole #caonimaM").val(),
            version : $("#editRole #versM").val(),
            packFileName : $("#editRole packFileTypeM").val(),
            description : $("#editRole #descM").val(),
            category : $("#editRole #roleCategoryM").val(),
            supsList : $.parseJSON("["+getCheckBoxValueOne(checkbox1)+"]")

        	};
	var newRoleName=$("#editRole form input[type=text]").val();
	$.ajax({
		url:basePath+"/role/update",
		type:"post",
		data:{"id":roleId,"name":newRoleName},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//数据库已经更新成功了
				alert(result.message);
				//把页面的那行数据更新
				dom.find("td:eq(2)").html(newRoleName);
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!")
		}
	});
	//关闭修改的modal框
	$("#editRole").modal("toggle");
	return false;
}


//获取当前编辑或删除超链接所在的tr行对象
function getRoleNode_tr(roleId,type){
	return $("#"+type+roleId).parent().parent();
}

//给删除超链接添加的click事件
//function deleteRoleClick(id){
//	roleId=id;
//	alert(id);
//}
//删除角色的方法
function deleteRole(roleId,dom){
	//1.根据roleId,走ajax删除数据的id对应角色信息
	//2.如果服务端删除成功.还要把页面的tr行删除
	$.ajax({
		url:"./package/delete/"+roleId,
		type:"delete",
//		dataType:"json",
        dataType:"text",
		success:function(result){
//			if(result.status==0){
//				//数据库删除完毕
//				alert(result.message);
//				//删除页面上的那个行tr
//				dom.remove();
//			}
			dom.remove();
		},
		error:function(){
			alert("请求失败!");
		}
	});
	//关闭当前删除确认modal框
	$("#deleterole_modal").modal('toggle');
	return false;
}
//添加角色方法
function addRole(){
	//获取页面数据
//    	var name=$("#addUserPanel #caonima").val();
//    	var version=$("#addUserPanel #vers").val();
//  	var supsList=$("#addUserPanel #supsList").val();
//    	var packFileName=$("#addUserPanel #packFileName").val();
//    	var description=$("#addUserPanel #desc").val();
//    	var category=$("#addUserPanel #roleCategory").val();
//    	var supsList="["+getCheckBoxValueOne()+"]";
        var mytest = {
        name : $("#addUserPanel #caonima").val(),
        version : $("#addUserPanel #vers").val(),
        packFileName : $("#addUserPanel #packFileName").val(),
        description : $("#addUserPanel #desc").val(),
        category : $("#addUserPanel #roleCategory").val(),
        supsList : $.parseJSON("["+getCheckBoxValueOne()+"]")

    	};
    	$.ajax({
    		url:"./package",
//    		secureuri:false,
//    		fileElementId:"addHeadPicture",
    		type:"post",
    		contentType: 'application/json',
    		data:JSON.stringify(mytest),
//    		data:{"name":name,"version":version,"supsList":JSON.stringify(mytest)/*supsList*/,"packFileName":packFileName,"description":description,"category":category},
    		dataType:"text",
    		success:function(data,status){
//    			//alert(data);
//    			data=data.replace(/<PRE.*?>/g,'');
//    			data=data.replace("<PRE>",'');
//    			data=data.replace("</PRE>",'');
//    			data=data.replace(/<pre.*?>/g,'');
//    			data=data.replace("<pre>",'');
//    			data=data.replace("</pre>",'');
//    			alert(data);
    		},
    		error:function(){
//    		    alert(data);
    			alert("请求失败!");
    		}
    	});
    	return false;
}
//查询当前页的数据
function findRoles(currentPage){
	//alert("findRoles()"+currentPage);
	//获取角色的模糊查询的关键字
	var roleKeyWord=$("#role_search").val();
	if(roleKeyWord==""){
		roleKeyWord="undefined";
	}
	//ajax请求当前页数据
	$.ajax({
		url:"./packages/page",
		type:"get",
		contentType: "application/json; charset=utf-8",
		data:{"currentPage":currentPage,"roleKeyword":roleKeyWord},
		dataType:"json",

//		jsonp: "callback",  //传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名,默认为callback
//        jsonpCallback: "userHandler",
		success:function(result){
			if(result.status==0){
				//查询成功,dom编程刷表格数据
				//1.清空页面数据
				$("#role_table tbody").html("");
				$("#role_page").html("");
				//2.给表格的tbody刷上数据
				var page=result.data;
				var roles=page.data;
				$(roles).each(function(n,value){
					//n:循环的是第几个元素的下标  从0开始
					//value:循环的当前元素,实际上就是一个role对象
//					if(value.name!='讲师' && value.name!='学员' && value.name!='超级管理员'){
						//需要添加上删除和修改
						var supss = "";
                        $(value.supsList).each(function(sn,svalue){
                            supss += svalue.name + ' ' + svalue.version + '\n';
                        });
//                        cxj = value.description;
                        var tr='<tr>'+
				              '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.uid+'">'+value.uid+'</td>'+
				              '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.name+'">'+value.name+'</td>'+
				              '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.version+'">'+value.version+'</td>'+
				              '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.category+'">'+value.category+'</td>'+
				              '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+supss+'">'+supss+'</td>'+
                          '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.filetype+'">'+value.filetype+'</td>'+
                          '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;" data-toggle="tooltip" title="'+value.description+'">'+value.description+'</td>'+
                          '<td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">'+
                            '<a href="" onclick=updateRoleClick(\''+value.uid+'\',\''+value.name+'\',\''+value.version+'\',\''+value.filetype+'\') id="urole'+value.uid+'" data-toggle="modal" data-target="#editRole" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
                            '<a href="" onclick=deleteRoleClick(\''+value.uid+'\') id="delRole'+value.uid+'" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
                          '</td>'+
                        '</tr>';
						$("#role_table tbody").append(tr);
//					}else{
//						//不需要添加删除和修改
//						var tr='<tr>'+
//				              '<td>'+(n+1)+'</td>'+
//				              '<td>'+value.id+'</td>'+
//				              '<td>'+value.name+'</td>'+
//				              '<td>'+
//				              '</td>'+
//				            '</tr>';
//						$("#role_table tbody").append(tr);
//					}
				});
				//3.刷表格下面的分页条组件
				if(page.aNum.length>0){
					//处理前一页
					var previousPage='<li>'+
					          '<a href="javascript:findRoles('+page.previousPage+')" aria-label="Previous">'+
			                  '<span aria-hidden="true">&laquo;</span>'+
			                  '</a>'+
		                  '</li>';
					$("#role_page").append(previousPage);

					//处理中间页
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findRoles('+value+')">'+value+'</a></li>';
						$("#role_page").append(middlePage);
					});

					//处理下一页
					var nextPage='<li>'+
				          '<a href="javascript:findRoles('+page.nextPage+')" aria-label="Next">'+
		                  '<span aria-hidden="true">&raquo;</span>'+
		                  '</a>'+
		                  '</li>';
					$("#role_page").append(nextPage);
				}

			}else if(result.status==1){
				//如果查询失败,提示错误信息
				alert(result.message+"error...")
			}
		},
		error:function(e){
		alert("请求错误！")
		}
	});

}
function cbMassage(){
    var forDebug = $("#addHeadPicture");
    alert(forDebug.val());
    $.ajaxFileUpload({
    		url:"./packagefile",
    		secureuri:false,
    		fileElementId:"addHeadPicture",
    		type:"post",
//    		data:{"loginName":loginName,"password":password1,"nickName":nickName,"age":age,"sex":sex,"roleId":roleId},
    		dataType:"text",
    		success:function(data,status){
//    		var data1 = data;
//    			alert(data);
//    			data=data.replace(/<PRE.*?>/g,'');
//    			data=data.replace("<PRE>",'');
//    			data=data.replace("</PRE>",'');
//    			data=data.replace(/<pre.*?>/g,'');
//    			data=data.replace("<pre>",'');
//    			data=data.replace("</pre>",'');
//    			alert(data);
//    			alert();
    			$("#caonima").val(data.name);
    			$("#vers").val(data.version);
    			$("#desc").val(data.description);
    		},
    		error:function(){
    			alert("请求失败!");
    		}
    	});


}
function getCheckBoxValueOne(){
    //获取name="box"选中的checkBox的元素
    var  ids = $("#addUserPanel input:checkbox[name='supsList']:checked");
    var data = "";
//    alert("ids="+ids);
    for(var i = 0; i < ids.length; i ++){
      //利用三元运算符去点
      data += ids[i].value + (i == ids.length - 1 ? "":",");
    }

    return data;
}