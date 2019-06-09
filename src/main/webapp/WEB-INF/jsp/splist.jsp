<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>商品管理页面</span>
       </div>
       <div class="search">
       <form method="post" action="${pageContext.request.contextPath }/sys/sp/splist.do">
			<span>商品名称：</span>
			<input name="querySpName" type="text" value="${queryspName }">

			 <input type="hidden" name="pageIndex" value="1"/>
			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${pageContext.request.contextPath }/sys/sp/add.html">添加商品</a>
		</form>
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="20%">商品名称</th>
              <th width="10%">商品价格</th>
              <th width="10%">商品产地</th>
              <th width="10%">销售情况</th>
              <th width="30%">操作</th>
          </tr>
          <c:forEach var="sp" items="${spPage.pageList }" varStatus="status">
				<tr>
					<td>
					<span>${sp.spName }</span>
					</td>
					<td>
					<span>${sp.spJg }</span>
					</td>
					<td>
					<span>${sp.spCd}</span>
					</td>
					<td>
					<span>
						<c:if test="${sp.spSsqk == 1}">热销</c:if>
						<c:if test="${sp.spSsqk == 2}">滞销</c:if>
					</span>
					</td>
					<td>
					<span><a class="viewSp" href="javascript:;" spid=${sp.id } spcc=${sp.spName }><img src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
					<span><a class="modifySp" href="javascript:;" spid=${sp.id } spcc=${sp.spName }><img src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
					<span><a class="deleteSp" href="javascript:;" spid=${sp.id } spcc=${sp.spName }><img src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
			</c:forEach>
      </table>
	</form>
 			<input type="hidden" id="totalPageCount" value="${spPage.totalPageCount}"/>
		  	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${spPage.totalCount}"/>
	          	<c:param name="currentPageNo" value="${spPage.pageIndex}"/>
	          	<c:param name="totalPageCount" value="${spPage.totalPageCount}"/>
          	</c:import>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeBi">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该订单吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script>
    var spObj;

    //订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
    function deleteSp(obj){
        $.ajax({
            type:"GET",
            url:path+"/sys/sp/delete.do",
            data:{spid:obj.attr("spid")},
            dataType:"json",
            success:function(data){
                if(data.delResult == "true"){//删除成功：移除删除行
                    cancleBtn();
                    obj.parents("tr").remove();
                }else if(data.delResult == "false"){//删除失败
                    //alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                    changeDLGContent("对不起，删除商品【"+obj.attr("spcc")+"】失败");
                }else if(data.delResult == "notexist"){
                    //alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                    changeDLGContent("对不起，商品【"+obj.attr("spcc")+"】不存在");
                }
            },
            error:function(data){
                alert("对不起，删除失败");
            }
        });
    }

    function openYesOrNoDLG(){
        $('.zhezhao').css('display', 'block');
        $('#removeBi').fadeIn();
    }

    function cancleBtn(){
        $('.zhezhao').css('display', 'none');
        $('#removeBi').fadeOut();
    }
    function changeDLGContent(contentStr){
        var p = $(".removeMain").find("p");
        p.html(contentStr);
    }

    $(function(){
        $(".viewSp").on("click",function(){
            //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            var obj = $(this);
            window.location.href=path+"/sys/sp/view.do?spid="+ obj.attr("spid");
        });

        $(".modifySp").on("click",function(){
            var obj = $(this);
            window.location.href=path+"/sys/sp/modify.do?spid="+ obj.attr("spid");
        });
        $('#no').click(function () {
            cancleBtn();
        });

        $('#yes').click(function () {
            deleteSp(spObj);
        });

        $(".deleteSp").on("click",function(){
            spObj = $(this);
            changeDLGContent("你确定要删除商品【"+spObj.attr("spcc")+"】吗？");
            openYesOrNoDLG();
        });
    });
</script>