<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<title>测试</title>
	<style type="text/css">
	.selected_seat {
		width:30px;
		height:30px;
		background:url(<c:url value="/images/seat/selected.jpg"/>);
		background-position:center;
		background-repeat:no-repeat;
		cursor: pointer;
		
	}
	.available_seat {
		width:30px;
		height:30px;
		background:url(<c:url value="/images/seat/available.jpg"/>);
		background-position:center;
		background-repeat:no-repeat;
		cursor: pointer;
	}
	.occupied_seat {
		width:30px;
		height:30px;
		background:url(<c:url value="/images/seat/occupied.jpg"/>);
		background-position:center;
		background-repeat:no-repeat;
		cursor: pointer;
	}
	.bus tr td {
		vertical-align:middle;
		text-align: center;
		padding: 15px;
		margin: 15px;
	}
		
	</style>
</head>
<body>


<div class="container">

	
<table class="bus">
  <tr>
    <td class="available_seat">1</td>
    <td class="available_seat">2</td>
    <td class="available_seat">3</td>
    <td class="available_seat">4</td>
    <td class="available_seat">5</td>
    <td class="available_seat">6</td>
    <td class="available_seat">7</td>
  </tr>
  <tr>
    <td class="available_seat">8</td>
    <td class="available_seat">9</td>
    <td class="available_seat">10</td>
    <td class="available_seat">11</td>
    <td class="available_seat">12</td>
    <td class="available_seat">13</td>
    <td class="available_seat">14</td>
  </tr>
  <tr>
    <td class="available_seat">15</td>
    <td class="available_seat">16</td>
    <td class="available_seat">17</td>
    <td class="available_seat">18</td>
    <td class="available_seat">19</td>
    <td class="available_seat">20</td>
    <td class="available_seat">21</td>
  </tr>
</table>


	<display:table export="true"  name="resources" requestURI="" pagesize="5" uid="resources" id="resources" partialList="false">
	
		<display:column escapeXml="false" title="父资源" >
			<translate:tableColumn tableName="SYS_RESOURCE" inputColumnName="RESOURCE_ID" outputColumnName="RESOURCE_NAME" inputValue="${resources.parentId }"/>
		</display:column>
		
		<display:column escapeXml="false" title="资源类型" >
			<translate:dict fieldCode="RESOURCE_TYPE" fieldValue="${resources.resourceType }"/>
		</display:column>
		
	    <display:setProperty name="paging.banner.item_name" value="部门" />
		<display:setProperty name="paging.banner.items_name" value="部门" />
		<display:setProperty name="export.excel.filename" value="aaa.xls" />
		<display:setProperty name="export.rtf.filename" value="aaa.rtf" />
		<display:setProperty name="export.amount" value="list" />
	</display:table>
</div>
<script>
	$(document).ready(function() {
		$(document).tooltip();
		
		$(".bus tr td").on("mouseover",function(e) {
			if ( $(e.target).hasClass("available_seat")) {
				$(e.target).removeClass("available_seat");
				$(e.target).addClass("selected_seat");
				$(e.target).attr("title","未售卖");
			};
			if ( $(e.target).hasClass("occupied_seat")) {
				$(e.target).attr("title","已售,时间:[]");
			}
		});
		
		$(".bus tr td").on("mouseout",function(e) {
			if ( $(e.target).hasClass("selected_seat")) {
				$(e.target).removeClass("selected_seat");
				$(e.target).addClass("available_seat");
			}
		});
		
		$(".bus tr td").on("click",function(e) {
			if ( $(e.target).hasClass("selected_seat")) {
				$(e.target).removeClass("selected_seat");
				$(e.target).addClass("occupied_seat");
			}
			
			$.ajax({
				url: "<c:url value="/ajax/book.do"/>",
				type: "POST",
				beforeSend: function(xhr) {
		            xhr.setRequestHeader("Accept", "application/json");
		            xhr.setRequestHeader("Content-Type", "application/json");
				},
				datatype:"json",
				success: function (data) {
					bootbox.alert("成功");
				},
				error: function( jqXHR, textStatus, errorThrown) {
					bootbox.alert("保存失败:[" + jqXHR.status + ": (" + jqXHR.statusText + ")]", function() {
						// do something
					});
				}
			});
		});
	});
</script>
</body>
</html>