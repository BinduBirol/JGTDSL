<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script  type="text/javascript">
	//navCache("billCreationHome.action?bill_parameter.isMetered_str=1");
	setTitle("Bill Delete for Customers");
</script>
<link href="/JGTDSL_WEB/resources/css/page/meterReading.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="supply-off" style="width: 50%;height: 45%;">
		<div class="row-fluid">
			<div class="span12" id="rightSpan">
				<div class="w-box">
					<div class="w-box-header">
						<h4 id="rightSpan_caption">Search Bill</h4>
					</div>

					<div class="w-box-content" style="padding: 30px;" id="content_div">
						<form id="deletebillform" action="" name="deletebillform" style="margin-bottom: 1px;">
							<div class="row-fluid">
								<div class="span12">
									<label style="width: 19.5%">Customer ID <m class='man' /></label>
									<input type="text" name="customer_id"id="customer_id"	style="font-weight: bold;color: #3b5894; width: 36.5%;margin-top: -4px;"required="required" /> 
										 
								</div>
							</div>
							
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">From<m class='man'/></label>
								<select name="to_billing_month" id="billing_month1" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="to_billing_year" id="billing_year1" style="width: 56%;">
							       	<option value="">Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">To<m class='man'/></label>
								<select name="form_billing_month" id="billing_month2" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="form_billing_year" id="billing_year2" style="width: 56%;">
							       	<option value="">Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						
						

						</form>
						
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">										    
						    <button class="btn btn-info" type="button" id="btn_search" onclick="reloadBillGrid()" >Search Bill</button> 	
															
						</div>
					</div>
				</div>
			</div>
		</div>
		<p style="color:#ff5733;">You can not delete collected bills.</p>
	</div>
	
	


<div id="bill_grid_div" style="width: 99%;height: 48%;"> 
	<table id="bill_delete_grid"></table>
	<div id="bill_grid_pager"></div>
</div>

<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/billDeleteGrid.js"></script>		
</body>

</html>