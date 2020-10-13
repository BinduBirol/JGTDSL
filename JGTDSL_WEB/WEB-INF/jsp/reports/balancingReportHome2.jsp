<%@ taglib prefix="s" uri="/struts-tags"%>
<script  type="text/javascript">
	navCache("jvHome.action");
	setTitle("Journal Voucher Report");
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
    				<h4 id="rightSpan_caption">Balancing Report</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
				
     				<form id="" name="billProcessForm" action="balancingReport2.action" style="margin-bottom: 1px;">		
     				
     				
     				
     				<div class="row-fluid" id= "report_for_div">
							<div class="span12">
								<div class="alert alert-info">
									
                                </div>
                                
							</div>
						</div>			
						
						
						<div class="row-fluid" style="width: 100%;" id="">							
							
							<div class="span6" id="fiscal_year_div">
								<label style="width: 40%">Year<m class='man'/></label>
								<select name="year" id="billing_year" style="width: 56%;">
							       	<option value="">Select Year</option>
							       	<s:iterator  value="%{#application.YEARS}" id="year">
							            <option value="<s:property/>"><s:property/></option>
									</s:iterator>
						       </select>     
							</div>  
						</div>
						
						
						
						
						
						<hr/>
						
						
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


	$("#type").hide();
	
	$("#report_for_div").click(function(){	
	var report_type = $("input[name='report_for']:checked").val();
		if (report_type == "summary") {
			$("#type").hide();
		} else {
			$("#type").show();
		};
	});
	
</script>
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/salesReport.js"></script>	
