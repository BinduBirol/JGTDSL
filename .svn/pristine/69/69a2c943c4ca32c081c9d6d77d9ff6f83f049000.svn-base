
<head>

  <%@ taglib prefix="s" uri="/struts-tags"%>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  
</head>
<body>

<div class="" >
  <br/>
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#home">Customer wise details</a></li>
    <li><a data-toggle="tab" href="#menu1">Date wise summary</a></li>  
    <li><a data-toggle="tab" href="#menu2">Area wise summary</a></li> 
  </ul>

  <div class="tab-content">
  
    <div id="home" class="tab-pane fade in active">
      <div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
			
				
						<form id="form1" action="mpg_coll_dtl_report.action">
							<input name="from_date" type="hidden" id="f_date"/>
							<input name="to_date" type="hidden" id="t_date"/>
							<input name="bank_name" type="hidden" id="bank_n"/>
							<input type="submit" class="btn btn-success pull-left" value="Print"/>						
						</form>
						<table width="100%" class="table-bordered" border="1">
							<thead>
								<tr>
									<th align="center">Serial</th>
									<th align="center">Collection Date</th>
									<th align="center">Customer ID</th>
									<th align="center">Bills</th>									
									<th align="center">Gas bill</th>
									<th align="center">Surcharge</th>
									<th align="center">Total</th>
									<th align="center">Bank</th>
									<th align="center">Trans ID</th>									
									
								</tr>
							</thead>
							<tbody>
								<s:iterator value="mpg_dashboard">
									<tr>
										<td id="scroll_no" align="center"><s:property
												value="sl" /></td>
										<td id="collection_date" align="center"><s:property value="trans_date" /></td>
										<td align="center"><s:property value="customer_id" /></td>
										<td align="left" style="max-width:100px;" ><s:property value="particulars" /></td>
										
										<td align="right" style="padding-right:3px;"><s:property value="gas_bill" />
										</td>
										<td align="right" style="padding-right:3px;"><s:property
												value="surcharge" /></td>
												<td align="right" style="padding-right:3px;"><s:property
												value="debit" /></td>
										<td align="left"><s:property value="bank_name" /></td>
										<td align="left"><s:property value="trans_id" /></td>										
										
									</tr>
								</s:iterator>
							</tbody>
							
							<thead>
								<tr>
									<th align="center" colspan="4">Total</th>
																	
									<th align="right" style="padding-right:3px;" ><s:property value="total_gas_bill" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="total_surcharge" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="grand_total" /></th>
									<th align="center" colspan="2"></th>									
									
								</tr>
							</thead>
						</table>
					</div>
    </div>
    <div id="menu1" class="tab-pane fade">
     	<div class="formSep sepH_b"
						style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">

						
						<form id="form1" action="mpg_coll_sum_report.action">
							<input name="from_date" type="hidden" id="f_date2"/>
							<input name="to_date" type="hidden" id="t_date2"/>
							<input name="bank_name" type="hidden" id="bank_n2"/>
							<input type="submit" class="btn btn-success pull-left" value="Print"/>						
						</form>

						<table width="100%" class="table-bordered" border="1">
							<thead>
								<tr>
									<th align="center">Serial</th>
									<th align="center">Collection Date</th>																		
									<th align="center">Gas bill</th>
									<th align="center">Surcharge</th>
									<th align="center">Total</th>
									<th align="center">Customer Count</th>																	
									
								</tr>
							</thead>
							<tbody>
								<s:iterator value="mpg_dashboard_date_wise">
									<tr>
										<td id="scroll_no" align="center"><s:property
												value="sl" /></td>
										<td id="collection_date" align="center"><s:property value="trans_date" /></td>
										<td align="right" style="padding-right:3px;"><s:property value="gas_bill" /></td>
										<td align="right" style="padding-right:3px;" ><s:property value="surcharge" /></td>
										
										<td align="right" style="padding-right:3px;"><s:property value="debit" />
										</td>
										
										<td align="center"><s:property value="count" /></td>										
										
									</tr>
								</s:iterator>
							</tbody>
							
							<thead>
								<tr>
									<th align="center" colspan="2">Total</th>
																	
									<th align="right" style="padding-right:3px;" ><s:property value="total_gas_bill" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="total_surcharge" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="grand_total" /></th>
									<th align="center" colspan="2"><s:property value="mpg_dashboard.size()"/> </th>									
									
								</tr>
							</thead>
						</table>
					</div>
    </div>
    
    <div id="menu2" class="tab-pane fade">
    	<div class="formSep sepH_b"
						style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">

						
						<form id="form1" action="mpg_coll_sum_report_area_wise.action">
							<input name="from_date" type="hidden" id="f_date3"/>
							<input name="to_date" type="hidden" id="t_date3"/>
							<input name="bank_name" type="hidden" id="bank_n3"/>
							<input type="submit" class="btn btn-success pull-left" value="Print"/>						
						</form>

						<table width="100%" class="table-bordered" border="1">
							<thead>
								<tr>
									<th align="center">Serial</th>
									<th align="center">Area</th>																		
									<th align="center">Gas bill</th>
									<th align="center">Surcharge</th>
									<th align="center">Total</th>
									<th align="center">Customer Count</th>																	
									
								</tr>
							</thead>
							<tbody>
								<s:iterator value="mpg_dashboard_area_wise">
									<tr>
										<td id="scroll_no" align="center"><s:property
												value="sl" /></td>
										<td id="collection_date" align="left"><s:property value="area_name" /> (<s:property value="area_id" />)</td>
										<td align="right" style="padding-right:3px;"><s:property value="gas_bill" /></td>
										<td align="right" style="padding-right:3px;" ><s:property value="surcharge" /></td>
										
										<td align="right" style="padding-right:3px;"><s:property value="debit" />
										</td>
										
										<td align="center"><s:property value="count" /></td>										
										
									</tr>
								</s:iterator>
							</tbody>
							
							<thead>
								<tr>
									<th align="center" colspan="2">Total</th>
																	
									<th align="right" style="padding-right:3px;" ><s:property value="total_gas_bill" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="total_surcharge" /></th>
									<th align="right" style="padding-right:3px;" ><s:property value="grand_total" /></th>
									<th align="center" colspan="2"><s:property value="mpg_dashboard.size()"/> </th>									
									
								</tr>
							</thead>
						</table>
					</div>
    
    </div>
    
    
    
  </div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	  /*
	  $(".nav-tabs a").click(function(){	  	
	    $(this).tab('show');
	  });
  
  	  */
  var from_date= $("#from_date").val();
  var to_date= $("#to_date").val();
  var bank_name=$("#bank").val();
  $("#f_date").val(from_date);
  $("#t_date").val(to_date);
  $("#f_date2").val(from_date);
  $("#t_date2").val(to_date);
  $("#bank_n").val(bank_name);
  $("#bank_n2").val(bank_name);
  $("#f_date3").val(from_date);
  $("#t_date3").val(to_date);
  $("#bank_n3").val(bank_name);
  
  //$("#form1").setAttribute("action", "mpg_coll_dtl?from_date='"+from_date+"'&to_date='"+to_date+"'");  

  
  
});
</script>


</body>



