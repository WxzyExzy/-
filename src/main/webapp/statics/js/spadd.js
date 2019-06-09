var spName=null;
var spJg=null;
var spCd=null
var addBtn = null;
var backBtn = null;


$(function(){
    spName = $("#spName");
    spJg = $("#spJg");
    spCd = $("#spCd");
	addBtn = $("#add");
	backBtn = $("#back");
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
    spName.next().html("*");
    spJg.next().html("*");
    spCd.next().html("*");

	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */
    spName.on("blur",function(){
		if(spName.val() != null && spName.val() != ""){
			validateTip(spName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(spName.next(),{"color":"red"},imgNo+" 商品名称不能为空，请重新输入",false);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip(spName.next(),{"color":"#666666"},"* 请输入商品价格",false);
	}).focus();

    spJg.on("focus",function(){
		validateTip(spJg.next(),{"color":"#666666"},"* 请输入商品价格",false);
	}).on("blur",function(){
		if(spJg.val() != null && spJg.val() != ""){
			validateTip(spJg.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(spJg.next(),{"color":"red"},imgNo+" 商品价格不能为空，请重新输入",false);
		}
		
	});

    spCd.on("focus",function(){
		validateTip(spCd.next(),{"color":"#666666"},"* 请输入商品产地",false);
	}).on("blur",function(){
		if(spCd.val() != null && spCd.val() != ""){
			validateTip(spCd.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(spCd.next(),{"color":"red"},imgNo+" 产地不能为空，请重新输入",false);
		}
		
	});

	
	addBtn.on("click",function(){
		if(spName.attr("validateStatus") != "true"){
            spName.blur();
		}else if(spJg.attr("validateStatus") != "true"){
			spJg.blur();
		}else if(spCd.attr("validateStatus") != "true"){
			spCd.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#spForm").submit();
			}
		}
	});
	
	backBtn.on("click",function(){
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