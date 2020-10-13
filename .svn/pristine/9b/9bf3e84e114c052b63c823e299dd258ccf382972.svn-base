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
				
     				<form id="billProcessForm" name="billProcessForm" action="preprintedreports.action" style="margin-bottom: 1px;">
						<div class="row-fluid">
							<div class="span12">
								<div class="alert alert-info">
									<table width="100%" align="center">
										<tr>
											<td width="100%" align="left" style="font-size: 12px;font-weight: bold;">
												<input type="radio" value="securityandBG" id="securityandBG" name="report_for" onclick="checkType(this.id)" /> Security-BG Notice  &nbsp;&nbsp;&nbsp;
											</td>											
										</tr>
									</table>
                                </div>
                                
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span6">
								<label style="width: 40%">Customer ID</label>
								<input type="text" name="customer_id" id="customer_id" maxlength="12" style="border: 1px solid #add9e4;width: 40%;;font-weight: bold;color: blue;" />
							</div>
							
							<div class="span6">
								<label style="width: 40%">Certification Number</label>
								<input type="text" name="certification_id" id="certification_id" style="border: 1px solid #add9e4;width: 40%;;font-weight: bold;color: blue;" />
							</div>
						</div>
					
						<div class="row-fluid">							
							<div class="span6">									    
								<label style="width: 40%">Issue Date</label>
								<input type="text" style="width: 40%"  name="issue_date" id="issue_date" />
							</div>
							<div class="span6">									    
								<label style="width: 40%">Due Date</label>
								<input type="text" style="width: 40%"  name="due_date" id="due_date" />
							</div>
						</div>
						<br>
						<br>
						
						
						
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
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/salesReport.js"></script>	
