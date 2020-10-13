<%@ taglib prefix="s" uri="/struts-tags"%>
<script  type="text/javascript">
	navCache("billCreationHome.action?bill_parameter.isMetered_str=ministry");
	setTitle("Bill Creation for Ministry");
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
    				<h4 id="rightSpan_caption">Bill Collection(Ministry)</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
     				<form id="billProcessForm" name="billProcessForm" style="margin-bottom: 1px;">
						
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">Region/Area</label>
								<select id="area_id"  style="width: 56%;"   name="collection.area_id">
									<option value="" disabled selected hidden >Select Area</option>
									<s:iterator value="%{#session.USER_AREA}" id="areaList">
										<option selected="selected" value="<s:property value="area_id" />" ><s:property value="area_name" /></option>
								</s:iterator>
								</select>									      
							</div>
							
							<div class="span6">								
								<select name="collection.MINISTRY_ID" id="MINISTRY_ID" style="width: 56%;">
							       	<option value="all">All Ministry</option>
							       	<s:iterator  value="%{#application.MINISTRY_CUSTOMER_CATEGORY}" id="MINISTRY_CAT">
							            <option value="<s:property value="Ministry_id"/>"><s:property value="Ministry_name" /></option>
									</s:iterator>
						       </select>     
							</div>  
							
						</div>
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">From Month-Year<m class='man'/></label>
								<select name="collection.month_from" id="from_billing_month" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="collection.year_from" id="from_billing_year" style="width: 56%;">
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
								<select name="collection.month_to" id="to_billing_month" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="collection.year_to" id="to_billing_year" style="width: 56%;">
							       	<option value="">Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						
						
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
						   <table width="100%">
						   	<tr>
						   		<td style="width: 30%;" align="right">
						   			 <button class="btn btn-primary" type="button" id="btn_parameter" onclick="reloadBillGrid()">Reload Grid</button>
						   			 
						   		</td>
						   		
						   	</tr>
						   </table>								    
						   									
						</div>
						<div class="row-fluid">
						<div class="span6">	
							<label style="width: 40%">JV NO</label>
							<input type="text" name="collection.jbNo" id="jbNo"  style="font-weight: bold;color: #3b5894;width: 55%;" />
						</div>				
						</div>
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">Collection Date<m class='man'/></label>
								<input type="text" style="width: 55%"  name="collection.collection_date" id="collection_date" />
							</div>
							<div class="span6"></div>
						</div>
						
						<div class="row-fluid">							
							<div class="span12">									    
								<label style="width: 19.5%">Remarks</label>
								<textarea rows="1" style="width: 76%" name="collection.remarks"></textarea>
								
							</div>
						</div>

						<div class="formSep" style="padding-top: 2px;padding-bottom: 2px;">
							<div id="aDiv" style="height: 0px;"></div>
							
							<input style="display:none;" type="text" style="width: 55%"  name="collection.customer_ids" id="customer_ids" />
							<input style="display:none;"  type="text" style="width: 55%"  name="collection.billed_amounts" id="billed_amounts" />
							
						
						</div>
						</form>	
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
						   <table width="100%">
						   	<tr>						   		
						   		<td style="width: 70%" align="right">
						   			<button class="btn btn-success pull-right" id="saveCollection" name="saveCollection" > <b>Save Collection</b> </button>	
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

<p style="clear: both;margin-top: 5px;"></p>

<div id="bill_grid_div" style="width: 99%;height: 48%;"> 
	<table id="bill_grid"></table>
	<div id="bill_grid_pager"></div>
</div>
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/billCreationMinistry.js"></script>	
