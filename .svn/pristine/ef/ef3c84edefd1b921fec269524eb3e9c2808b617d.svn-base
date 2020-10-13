<%@ taglib prefix="s" uri="/struts-tags"%>
<script  type="text/javascript">
	navCache("billCreationHome.action?bill_parameter.isMetered_str=FFCollection");
	setTitle("Freedom Fighter Collection");
</script>

<link href="/JGTDSL_WEB/resources/css/page/meterReading.css" rel="stylesheet" type="text/css" />
<style>
input[type="radio"], input[type="checkbox"]
{
margin-top: -3px !important;
}
.alert{
padding-top: 4px !important;
padding-bottom: 4px !important;
}
.ui-icon, .ui-widget-content .ui-icon {
    cursor: pointer;
}
.sFont{
font-size: 12px;
}
</style>

<div class="meter-reading" style="width: 50%;height: 50%;">
	<div class="row-fluid">
		<div class="span12" id="rightSpan">
			<div class="w-box">
				<div class="w-box-header">
    				<h4 id="rightSpan_caption">Freedom Fighter Collection</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
     				<form id="billProcessForm" name="billProcessForm" style="margin-bottom: 1px;">
						
						
						<div class="row-fluid">								
							  <div class="span6">									    
								<label style="width: 40%">Customer Id</label>
								<input type="text" name="FFCollection.customer_id" id="customer_id"  style="font-weight: bold;color: #3b5894;width: 55%;" />
							</div>
						</div>
						<br/>
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">From Month-Year<m class='man'/></label>
								<select name="FFCollection.from_month" id="from_month" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="FFCollection.from_year" id="from_year" style="width: 56%;">
							       	<option value="">Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">To Month-Year<m class='man'/></label>
								<select name="FFCollection.to_month" id="to_month" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="FFCollection.to_year" id="to_year" style="width: 56%;">
							       	<option value="">Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						<br/>
						<div class="row-fluid">
							<div class="span6">	
								<label style="width: 40%">JV NO</label>
								<input type="text" name="FFCollection.jvNo" id="jvNo"  style="font-weight: bold;color: #3b5894;width: 55%;" />
							</div>				
						</div>
						
						<div class="row-fluid">								
							<div class="span6">
									<label style="width: 40%">Bill Amount</label>								
									<input type="text" name="FFCollection.bill_amount" id="bill_amount"  style="font-weight: bold;color: #3b5894;width: 55%;" />
							</div>						
						</div>
						

						<div class="formSep" style="padding-top: 2px;padding-bottom: 2px;">
							<div id="aDiv" style="height: 0px;"></div>
						</div>
						</form>	
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
						   <table width="100%">
						   	<tr>
						   		<td style="width: 30%" align="left">
						   			 <button class="btn btn-primary" type="button" id="btn_parameter" onclick="reloadBillGrid()">Reload Grid</button>
						   			 <button class="btn btn-primary" type="button" id="btn_unlockDB" onclick="unlockDatabase()">Unlock</button>
						   			 
						   		</td>
						   		<td style="width: 70%" align="right">
						   			<button class="btn btn-save pull-right" id="saveCollection" name="saveCollection" onclick="validateAndSaveGankGarantieExpireExtentionInfo()" > Save Collection </button>	
						   		</td>
						   	</tr>
						   </table>								    
						   									
						</div>
						
						
					
																				
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 
<jsp:include page="BillCreationMeteredStat.jsp" />
  -->
</br>
<p style="clear: both;margin-top: 5px;"></p>

<div id="bill_grid_div" style="width: 99%;height: 48%;"> 
	<table id="bill_grid"></table>
	<div id="bill_grid_pager"></div>
</div>
<!--<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/billCreationMinistry.js"></script>	-->
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/FFCollection.js"></script>	
