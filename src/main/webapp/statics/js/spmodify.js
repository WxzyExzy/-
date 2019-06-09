var spJg = null;
var saveBtn = null;
var backBtn = null;

$(function(){
    spJg = $("#spJg");
	saveBtn = $("#save");
	backBtn = $("#back");
	
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    spJg.next().html("*");
	
	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */
    spJg.on("focus",function(){
		validateTip(spJg.next(),{"color":"#666666"},"* 请输入价格",false);
	}).on("blur",function(){
		if(spJg.val() != null && spJg.val() != ""){
			validateTip(spJg.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(spJg.next(),{"color":"red"},imgNo+" 价格不能为空，请重新输入",false);
		}
		
	});

	
	saveBtn.on("click",function(){
        spJg.blur();
		if(spJg.attr("validateStatus") == "true" &&
            spJg.attr("validateStatus") == "true"){
			if(confirm("是否确认提交数据")){
				$("#spForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
		//alert("modify: "+referer);
		if(referer != undefined 
			&& null != referer 
			&& "" != referer
			&& "null" != referer
			&& referer.length > 4){
		 window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});