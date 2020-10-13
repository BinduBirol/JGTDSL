<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="meter-reading" style="width: 100%;height: 50%;">
	<div class="row-fluid">
		<div class="span12" id="rightSpan">
			<div class="w-box">
				<div class="w-box-header">
					<h4 id="rightSpan_caption">Mpg Collection Info.</h4> 					
				</div>
				
				<div class="w-box-content"  id="content_div">
					<div class="formSep sepH_b"
						style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">

						


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
										
										<td align="center"><s:property value="gas_bill" />
										</td>
										<td align="center"><s:property
												value="surcharge" /></td>
												<td align="center"><s:property
												value="debit" /></td>
										<td align="center"><s:property value="bank_name" /></td>
										<td align="center"><s:property value="trans_id" /></td>										
										
									</tr>
								</s:iterator>
							</tbody>
							
							<thead>
								<tr>
									<th align="center" colspan="4">Total</th>
																	
									<th align="center"><s:property value="total_gas_bill" /></th>
									<th align="center"><s:property value="total_surcharge" /></th>
									<th align="center"><s:property value="grand_total" /></th>
									<th align="center" colspan="2"></th>									
									
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	
</script>
