<%@ taglib prefix="s" uri="/struts-tags"%>
<script  type="text/javascript">
	navCache("salesReportHome.action");
	setTitle("Pre Printed Reports");
</script>
<link href="/JGTDSL_WEB/resources/css/page/meterReading.css" rel="stylesheet" type="text/css" />
<style>
input[type="radio"],input[type="checkbox"] {
	margin-top: -3px !important;
}

.alert {
	padding-top: 4px !important;
	padding-bottom: 4px !important;
}

.ui-icon,.ui-widget-content .ui-icon {
	cursor: pointer;
}

.sFont {
	font-size: 12px;
}
</style>
<div class="meter-reading" style="width: 80%;height: 50%;">
	<div class="row-fluid">
		<div class="span12" id="rightSpan">
			<div class="w-box">
				<div class="w-box-header">
    				<h4 id="rightSpan_caption">Pre Printed Reports</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
				
     				<form id="billProcessForm" name="billProcessForm" action="due_bill_notice.action" style="margin-bottom: 1px;">
												
						<div class="row-fluid">
							
								<jsp:include page="../common/CustomerInfo.jsp" />											
												
						</div>
						<br/>
						<input type="hidden" name="customer_id" id="customer_id"/>						
						
						
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">To Month-Year<m class='man'/></label>
								<select name="month_to" id="to_billing_month" style="width: 56%;margin-left: 0px;">
							       	<option value="">Select Month</option>           
							        <s:iterator  value="%{#application.MONTHS}">   
							   			<option value="<s:property value='id'/>"><s:property value="label"/></option>
									</s:iterator>
						       </select>								      
							</div>
							<div class="span6">
								
								<select name="year_to" id="to_billing_year" style="width: 56%;">
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
								<label style="width: 40%">Issue Date</label>
								<input type="text" required style="width: 40%"  name="issue_date" id="issue_date" />
							</div>
							
						</div>
						
						<div class="row-fluid">							
							
							<div class="span6">									    
								<label style="width: 40%">Due Date</label>
								<input type="text" required style="width: 40%"  name="due_date" id="due_date" />
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

  
<p style="clear: both;margin-top: 5px;"></p>
<script type="text/javascript">

$("#from_billing_month").val(getCurrentMonth());
$("#from_billing_year").val(getCurrentYear());
$("#to_billing_month").val(getCurrentMonth());
$("#to_billing_year").val(getCurrentYear());

$("#comm_customer_id").unbind();
$("#comm_customer_id").autocomplete($.extend(true, {}, acMCustomerOption,{
		serviceUrl: sBox.CUSTOMER_LIST,
    	onSelect:function (){getCustomerInfo("comm",$('#comm_customer_id').val()), $('#customer_id').val($('#comm_customer_id').val());
		}
}));

Calendar.setup({
    inputField : "issue_date",
    trigger    : "issue_date",
	eventName : "focus",
    onSelect   : function() { this.hide();},
    showTime   : 12,
    dateFormat : "%d-%m-%Y",
	showTime : true
});

Calendar.setup({
    inputField : "due_date",
    trigger    : "due_date",
	eventName : "focus",
    onSelect   : function() { this.hide();},
    showTime   : 12,
    dateFormat : "%d-%m-%Y",
	showTime : true
});

</script>
