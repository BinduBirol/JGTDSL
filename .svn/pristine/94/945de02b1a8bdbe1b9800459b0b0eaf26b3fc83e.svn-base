<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'mixedCustomerInfoHome.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div class="meter-reading" style="width: 50%;height: 50%;">
	<div class="row-fluid">
		<div class="span12" id="rightSpan">
			<div class="w-box">
				<div class="w-box-header">
    				<h4 id="rightSpan_caption">Mixed Customer List Info</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
				
     				<form id="billProcessForm" name="billProcessForm" action="MixedCustListInfo" style="margin-bottom: 1px;">
						
						<!-- 
						<div class="row-fluid">
							<div class="span12">
								<div class="alert alert-info">
									<table width="100%" align="center">
										<tr>
											<td width="100%" align="right" style="font-size: 12px;font-weight: bold;">
												<input type="radio" value="all"  name="report_for" checked="checked" /> All&nbsp;&nbsp;&nbsp;
												<input type="radio" value="conn" name="report_for"  /> Connected&nbsp;&nbsp;&nbsp;
												<input type="radio" value="disconn"  name="report_for" /> Disconnected&nbsp;&nbsp;&nbsp;

											</td>											
										</tr>
									</table>
                                </div>
                                
							</div>
						</div>
						 -->
						<div class="row-fluid" id="individual_div">
		    <div class="w-box-content" style="padding: 10px;" id="content_div">						

				<div class="row-fluid">							
					<div class="span6">									    
						<label style="width: 40%">Region/Area</label>
						<select id="area_id"  style="width: 56%;"   name="area">									
									<s:iterator value="%{#session.USER_AREA_LIST}" id="areaList">
										<option selected="selected" value="<s:property value="area_id" />" ><s:property value="area_name" /></option>
								</s:iterator>
						</select>									      
				    </div>

				</div>
				


				
					
													
					</div>
				</div>
						
						
			
						
						<div class="formSep" style="padding-top: 2px;padding-bottom: 2px;">
							<div id="aDiv" style="height: 0px;"></div>
						</div>
						
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
						   <table width="100%">
						   	<tr>
						   		
						   		<td style="width: 70%" align="right">
						   			  
						   			     
						   			 <button class="btn" type="submit">Generate Report</button>	
									 <button class="btn btn-danger"  type="button" id="btn_cancel" onclick="callAction('blankPage.action')">Cancel</button>
						   		</td>
						   	</tr>
						   </table>								    
						   									
						</div>
					</form>																	
				</div>
			</div>
		</div>
	</div>
</div>
    
  </body>
</html>
