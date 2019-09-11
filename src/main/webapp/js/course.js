//@ sourceURL=course.js

  var treeData = [{
   text: "航天二院",id:001,
   nodes: [{
    text: "706所",id:001,
    nodes: [{
     text: "研发部"
    }, {
     text: "总体部"
//     nodes: [{
//      text: "运维组",id:001,
//      nodes: [{
//       text: "软件运维",id:001
//      }, {
//       text: "硬件运维",id:001
//      }]
//     }, {
//      text: "研发组",id:001
//     }]
    }]
   }, {
   text: "206所",id:001,
   nodes: [{
     text: "财务部"
    }, {
     text: "科研部"
    }]
   }]
  }, {
   text: "航天一院",
   id:001
  }, {
   text: "航天三院",id:001,
   nodes: [{text:"lala"}]
  }];
  var nodeCheckedSilent = false;
  function nodeChecked(event, node) {
   if (nodeCheckedSilent) {
    return;
   }
   nodeCheckedSilent = true;
   checkAllParent(node);
   checkAllSon(node);
   nodeCheckedSilent = false;
  }
  var nodeUncheckedSilent = false;
  function nodeUnchecked(event, node) {
   if (nodeUncheckedSilent)
    return;
   nodeUncheckedSilent = true;
   uncheckAllParent(node);
   uncheckAllSon(node);
   nodeUncheckedSilent = false;
  }
  //选中全部父节点
  function checkAllParent(node) {
   $('#searchTree').treeview('checkNode', node.nodeId, {
    silent: true
   });
   var parentNode = $('#searchTree').treeview('getParent', node.nodeId);
   if (!("nodeId" in parentNode)) {
    return;
   } else {
    checkAllParent(parentNode);
   }
  }
  //取消全部父节点
  function uncheckAllParent(node) {
   $('#searchTree').treeview('uncheckNode', node.nodeId, {
    silent: true
   });
   var siblings = $('#searchTree').treeview('getSiblings', node.nodeId);
   var parentNode = $('#searchTree').treeview('getParent', node.nodeId);
   if (!("nodeId" in parentNode)) {
    return;
   }
   var isAllUnchecked = true; //是否全部没选中
   for (var i in siblings) {
    if (siblings[i].state.checked) {
     isAllUnchecked = false;
     break;
    }
   }
   if (isAllUnchecked) {
    uncheckAllParent(parentNode);
   }
  }
  //级联选中所有子节点
  function checkAllSon(node) {
   $('#searchTree').treeview('checkNode', node.nodeId, {
    silent: true
   });
   if (node.nodes != null && node.nodes.length > 0) {
    for (var i in node.nodes) {
     checkAllSon(node.nodes[i]);
    }
   }
  }
  //级联取消所有子节点
  function uncheckAllSon(node) {
   $('#searchTree').treeview('uncheckNode', node.nodeId, {
    silent: true
   });
   if (node.nodes != null && node.nodes.length > 0) {
    for (var i in node.nodes) {
     uncheckAllSon(node.nodes[i]);
    }
   }
  }
  $('#searchTree').treeview({
   showCheckbox: true,
   data: treeData,
   onNodeChecked: nodeChecked,
   onNodeUnchecked: nodeUnchecked
  });

  //选择选中的所有第三级
  function getDestinationList(){

        			var nodes = $('#searchTree').treeview('getChecked');
        			var s = '';
        			for(var i=0; i<nodes.length; i++){
//        			    alert(nodes.levels);
        			    if (nodes[i].id == 1) {continue;}
        				   else{if (s != '') s += ',';s += nodes[i].text;}

        			}
//        			alert(nodes);
//        			alert(s);
                    return s;

        		}
//文档就绪事件
  $(function(){
    $('#addCoursePanel form').submit(function(){
     return addTask();

});
});

    function addTask(){
    alert(getDestinationList());
    	//获取页面数据
    	var taskName=$("#addCoursePanel #taskName").val();
    	var pushWhere="[a,"+getDestinationList()+",b]";
    	var desc=$("#addCoursePanel #courseDesc").val();
//    	var mytest = {
//                taskName : $("#addCoursePanel #taskName").val(),
//                desc : $("#addCoursePanel #courseDesc").val(),
//                pushWhere :"["+getDestinationList()+"]"
//
//            	};
//    	var nickName=$("#addUserPanel #nickName").val();
//    	var age=$("#addUserPanel #age").val();
//    	var roleId=$("#addUserPanel #roleCategory").val();
//    	var sex=$("#addUserPanel input[name=user-type]:checked").val();
//    	if(password1!=password2){
//    		alert("两次密码不同");
//    		return false;
//    	}
//    	if(age<=0){
//    		$("#addUserPanel #age").focus();
//    		return false;
//    	}
//alert(JSON.stringify(mytest));
    	//异步提交
    	$.ajaxFileUpload({
    		url:"./push/now/",
    		secureuri:false,
    		fileElementId:"pushfile",
    		type:"post",
    		data:{"taskName":taskName,"pushWhere":pushWhere,"desc":desc},
//    		data:JSON.stringify(mytest),
    		dataType:"text",
    		success:function(data,status){
    			//alert(data);
//    			data=data.replace(/<PRE.*?>/g,'');
//    			data=data.replace("<PRE>",'');
//    			data=data.replace("</PRE>",'');
//    			data=data.replace(/<pre.*?>/g,'');
//    			data=data.replace("<pre>",'');
//    			data=data.replace("</pre>",'');
    			alert(data);
    		},
    		error:function(){
    			alert("请求失败!");
    		}
    	});
    	return false;
    }
