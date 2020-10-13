<%@ taglib prefix="s" uri="/struts-tags"%>
<script  type="text/javascript">
	navCache("deleteCollection.action");
	setTitle("Delete Daily Collection");
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
<div class="meter-reading" style="width: 80%;height: 50%;">
	<div class="row-fluid">
		<div class="span9" id="rightSpan">
			
				<div class="w-box-header">
    				<h4 id="rightSpan_caption">Delete Collection</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="content_div">
				
     									
						<div class="row-fluid">							

							
						<jsp:include page="../common/CustomerInfo.jsp" />
						
							 
						</div>
						
						<!-- 
						<div class="row-fluid" id="customer_id_div">							
							 <div class="row-fluid">
								<div class="span9">
									<label style="width: 40%">Customer ID</label>
									<input type="text" name="customer_id" id="customer_id" style="width: 25%" />
								</div>
							</div>
						</div>
						 -->
						
						<div class="formSep" style="padding-top: 2px;padding-bottom: 2px;">
							<div id="aDiv" style="height: 0px;"></div>
						</div>
						
						<div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
						   <table width="100%">
						   	<tr>
						   		
						   		<td style="width: 60%" align="right">				   			     
						   			 <button class="btn" id="btn_save" onclick="fetchWrongCollectionList()">Search</button>	
									 <button class="btn btn-danger"  type="button" id="btn_cancel" onclick="callAction('blankPage.action')">Cancel</button>
						   		</td>
						   	</tr>
						   </table>								    
						   									
						</div>
					
					
				
																				
				</div>
			</div>
	</div>
	
		<div style="width: 47%;text-align: center;float: left;padding-top:20px;margin-left: 5px;display: none;" id="stat_div">
			<table>
				<tr>
					<td style="text-align: left;padding-left: 10px;padding-bottom: 20px;background-color: #387C44;color: white;"  id="loading_div"></td>
				</tr>
			</table>
		</div>


		<div id="detailDiv">
		
		</div>
	
</div>

  
<p style="clear: both;margin-top: 5px;"></p>

<script type="text/javascript">


// Delete Collection Grid 

  function fetchWrongCollectionList() {

        
		
		$("#detailDiv").html("");
		$("#stat_div").show();
		$("#loading_div").html(jsImg.LOADING_MID+"<br/><br/><font style='color:white;font-weight:bold'>Please wait. Searching the Collection list </font>");
		
		
		
        $.ajax({
            type    : "POST",
            url     : "fetchCustomerCollection4Delete",
            dataType: 'text',
            async   : false,
            data    : {customer_id: $("#comm_customer_id").val()
            }
        	}).done(function (msg) {
        			$("#stat_div").hide();
                    $("#detailDiv").html(msg);                    
                })
                .always(function () {
                    //$('#sw-val-step-3').unmask();
                })
                .fail(function (data) {
                    if (data.responseCode)
                        alert(data.responseCode);
                });

		
    }   //End of fetchInformation

/*

function checkType(type){
	if(type=="area_wise")
	{
	 disableChosenField("customer_id");
	 disableField("customer_category");
	 resetSelectBoxSelectedValue("customer_category");
	 autoSelect("area_id");
	 enableField("area_id");
	}
	else if(type=="by_category"){
	 disableChosenField("customer_id");
	 enableField("customer_category","area_id");
	 autoSelect("customer_category","area_id");
	}
	else if(type=="individual"){
	 enableChosenField("customer_id");
	 disableField("customer_category","area_id");
	 resetSelectBoxSelectedValue("customer_category","area_id");
	}
	
	if(type=="month_wise"){
		hideElement("date_div");
		showElement("month_div","year_div");
		enableField("bank_id");
		enableField("branch_id");
		enableField("account_id");	
		//enableButton("btn_save");
	}
	else if(type=="date_wise"){
		hideElement("month_div","year_div");
		showElement("date_div");
		enableField("bank_id");
		enableField("branch_id");
		enableField("account_id");	
		//enableButton("btn_save");	
	}
	else if(type=="month_wiseDetails"){
		hideElement("date_div");
		showElement("month_div","year_div");
		enableField("bank_id");
		enableField("branch_id");
		enableField("account_id");
		//enableButton("btn_save");
	}
	else if(type=="bank_wise"){
		hideElement("date_div");
		showElement("month_div","year_div");
		disableField("bank_id");
		disableField("branch_id");
		disableField("account_id");
		
		
		if($("#collection_month").val()=="Select Month" || $("#collection_year").val()=="year")
		{
			disableButton("btn_save");
		}else{
			enableButton("btn_save");
		}
		
	}
	else if(type="all_bank_wise_monthly"){
		disableField("bank_id");
		disableField("branch_id");
		disableField("account_id");
		showElement("month_div","year_div");
	}
}	
*/
$("#comm_customer_id").unbind();
	$("#comm_customer_id").autocomplete($.extend(true, {}, acMCustomerOption,{
		
			serviceUrl: sBox.CUSTOMER_LIST,
	    	onSelect:function (){
		
				getCustomerInfo("comm",$("#comm_customer_id").val());	
				$("#appliance_info_customer_id").val($("#comm_customer_id").val());
				$("#raizer_diss_customer_id").val($("#comm_customer_id").val());
				$("#customer_id").val($("#comm_customer_id").val());
				fetchWrongCollectionList();
			}
	}));
</script>	
