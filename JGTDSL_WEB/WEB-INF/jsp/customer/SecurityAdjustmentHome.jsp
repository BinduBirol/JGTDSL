<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	navCache("securityAdjustmentHome.action");
	setTitle("Security Deposit Adjustment");
</script>

<div id="customer_meter_div" style="height: 60%;width: 99%;">
	<div class="customer_info" style="float:center; width: 100%;height:100%;">
		<div class="row-fluid" style="height: 57%; width:50%">
			<jsp:include page="../common/CustomerInfo.jsp" />
		</div>
		<div class="row-fluid">



			<div class="span12" id="rightSpan">
				<div class="w-box">
					<div class="w-box-header">
						<h4 id="rightSpan_caption">Adjustment Detail</h4>
					</div>
					<div class="w-box-content" style="padding: 10px; " id="content_div">
						<form id="meterForm" name="meterForm" style="margin-bottom: 1px; ">
							<div class="row-fluid" style="width:50%">

								<div class="span12"></div>

								<div class="span12">
									<label style="width: 40%">Total security Amount (Cash)</label> <input
										type="text" id="totalsecurityAmount"
										style="width: 40%;font-weight: bold;" maxlength="30"
										disabled="disabled" />
								</div>

								<div class="span12">
									<label style="width: 40%">Adjustment Amount</label> <b>(-)</b>&nbsp;<input
										type="text" id="totalAdjustableAmount"
										style="width: 38%;font-weight: bold;" maxlength="30"
										tabindex="101"  />
								</div>
								<div class="span12">
									<label style="width: 40%">Adjustment Mode</label>
									<div style="float: left;">
										<input type="radio" id="adjustmentMode_refund" 
											name="adjustmentMode" value="1"
											onclick="setAdjustmentMode('refund')"  />&nbsp;
									</div>
									<div style="float:left;padding-right: 10px;margin-top: 3px;">Refund</div>
									<div style="float: left;">
										<input type="radio" id="adjustmentMode_withBill"  checked="checked"
											name="adjustmentMode" value="2"
											onclick="setAdjustmentMode('withBill')" />&nbsp;
									</div>
									<div style="float:left;padding-right: 10px;margin-top: 3px;">Adjustment
										with arrear Bill</div>
								</div>
								<div class="span12">
									<label style="width: 40%">Comment</label> <input type="text"
										id="comment" style="width: 40%" maxlength="30" tabindex="101" />
								</div>
								<div class="span12">
									<label style="width: 40%">Date</label> <input type="text"
										id="collectionDate" style="width: 40%" maxlength="30"
										tabindex="101" />
								</div>
							 
							 <!-- 
								<fieldset class="span12 control"
									style="width: 90%;padding-left: 10px; " id="refundFieldset">
									<legend style="text-align: left;color: red;">Check's
										Bank Account Info</legend>
									<div class="span12 control" >
										<div class="row-fluid">
											<div class="span12">
												<label style="width: 40%">Bank</label> <select
													id="refund_bank_id" style="width: 40.5%;"
													onchange="fetchSelectBox(refund_branch_sbox)"
													onkeypress="selectvalue.apply(this, arguments),fetchSelectBox(refund_branch_sbox)">
													<option value="" selected="selected">Select Bank</option>
													<s:iterator value="%{#session.USER_BANK_LIST}">
														<option value="<s:property value="bank_id" />">
															<s:property value="bank_name" />
														</option>
													</s:iterator>
												</select> &nbsp;<input id="otherCheckBox_refund" type="checkbox"
													onclick="setOtherOption()" />&nbsp;Others

											</div>
										</div>
									</div>
									<div class="span12 control">
										<div class="row-fluid">
											<div class="span12">
												<label style="width: 40%">Branch</label> <select
													id="refund_branch_id" style="width: 54.5%;"
													onchange="fetchSelectBox(refund_account_sbox)"
													onkeypress="selectvalue.apply(this, arguments),fetchSelectBox(refund_account_sbox)">
													<option value="" selected="selected">Select Branch</option>
												</select>
											</div>
										</div>
									</div>
									<div class="span12 control">
										<div class="row-fluid">
											<div class="span12">
												<label style="width: 40%">Account</label> <select
													id="refund_account_id"
													onkeypress="selectvalue.apply(this, arguments)"
													style="width: 54.5%;">
													<option value="" selected="selected">Select
														Account</option>
												</select>
											</div>
										</div>
									</div>
								</fieldset>
								
								 -->
							

							</div>


							<div class="formSep"
								style="padding-top: 2px; padding-bottom: 2px;">
								<div id="aDiv" style="height: 0px;"></div>
							</div>
							
							<br/>

							<div class="row-fluid"
								style="width:100%;height: 100%;padding-top: 0px;" id="dues_div">

								<div id="the_grid">
									<table id="dues_bill_grid"></table>
									<div id="dues_bill_grid_pager"></div>
								</div>
								<input type="hidden" id="totalAdjustAmount" />

							</div>

							<div class="formSep sepH_b"
								style="padding-top: 3px; margin-bottom: 0px; padding-bottom: 2px;">
								<table id="buttonTable" width="100%">
									<tr>
										<td width="" align="left">
											<button class="btn btn-beoro-3" 
												type="button" id="btn_save"
												>
												<span class="splashy-document_letter_okay"></span> Save
											</button>
										</td>

										<td width="" align="left"><span id="msg1Div" style="color:red"></span><br />
											<span id="msg2Div" style="color:red"></span></td>
										<td width="" align="right">
											<button class="btn btn-beoro-3" type="button" id="btn_close"
												onclick="callAction('blankPage.action')">
												<i class="splashy-folder_classic_remove"></i> Close
											</button>
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

	<div style="width: 51%; height: 99%;float: left;margin-left: 1%;">










	</div>
</div>

<div id="customer_grid_div" style="height: 38%;width: 99%;">
	<table id="customer_grid"></table>
	<div id="customer_grid_pager"></div>
</div>

<script type="text/javascript"
	src="/JGTDSL_WEB/resources/js/page/securityAdjustment.js"></script>
<script>
	
</script>


