<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<div class="right">
     <div class="location">
         <strong>你现在所在的位置是:</strong>
         <span>商品管理页面 >> 信息查看</span>
     </div>
     <div class="providerView">
         <p><strong>商品名称：</strong><span>${sp.spName}</span></p>
         <p><strong>商品价格：</strong><span>${sp.spJg}</span></p>
         <p><strong>商品产地：</strong><span>${sp.spCd}</span></p>
         <p><strong>销售情况：</strong>
             <span>
         		<c:if test="${sp.spSsqk == 1}">热销</c:if>
				<c:if test="${sp.spSsqk == 2}">滞销</c:if>
			</span>
         </p>
		<div class="providerAddBtn">
         	<input type="button" id="back" name="back" value="返回" >
        </div>
     </div>
 </div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/spview.js"></script>