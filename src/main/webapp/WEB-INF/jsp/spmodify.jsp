<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>

  <div class="right">
      <div class="location">
          <strong>你现在所在的位置是:</strong>
          <span>商品管理页面 >> 商品修改页</span>
      </div>
      <div class="providerAdd">
          <form id="spForm" name="spForm" method="post" action="${pageContext.request.contextPath }/sys/sp/modifysave.html" enctype="multipart/form-data">
              <input type="hidden" name="id" value="${sp.id }"/>
              <!--div的class 为error是验证错误，ok是验证成功-->
              <div class="">
                  <label for="spName">商品名称：</label>
                  <input type="text" name="spName" id="spName" value="${sp.spName}" readonly="readonly">
              </div>
              <div>
                  <label for="spJg">商品价格：</label>
                 <input type="text" name="spJg" id="spJg" value="${sp.spJg}">
			<font color="red"></font>
              </div>
              
              <div>
                  <label for="spCd">商品产地：</label>
                  <input type="text" name="spCd" id="spCd" value="${sp.spCd}">
			<font color="red"></font>
              </div>

              <div>
                  <label>销售情况：</label>
                  <c:if test="${sp.spSsqk == 1 }">
                      <input type="radio" name="spSsqk" value="1" checked="checked">热销
                      <input type="radio" name="spSsqk" value="2" >滞销
                  </c:if>
                  <c:if test="${sp.spSsqk == 2 }">
                      <input type="radio" name="spSsqk" value="1">热销
                      <input type="radio" name="spSsqk" value="2" checked="checked">滞销
                  </c:if>
              </div>
              <div class="providerAddBtn">
                  <input type="button" name="save" id="save" value="保存">
				  <input type="button" id="back" name="back" value="返回" >
              </div>
          </form>
      </div>
  </div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/spmodify.js"></script>