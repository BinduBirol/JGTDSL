<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	navCache("editCustomerInfoHome.action");
	setTitle("Customer Info Update");
</script>
<style type="text/css">
.span6.extra {
	min-height: 33px !important;
}
</style>

<div class="row-fluid">

	<div style="width: 40%;height: 98%;float: left;">
		<div style="width: 100%;float: left;margin-top: 0px;">
			<div class="w-box">
				<div class="w-box-header">
					<h4 id="rightSpan_caption">Change Customer ID</h4>
				</div>
				<div class="w-box-content" style="padding: 10px;" id="metered_div">


					<div class="row-fluid">
						<div class="span8 extra">
							<label style="width: 60%">Customer Id</label> <input type="text"
								id="customer_id" name="customer_id"
								style="font-weight: bold;color: #3b5894;width: 35%;margin-top:-2px;"
								value="<s:property value="customer.customer_id"/>" />



						</div>

					</div>
				</div>
				<div id="new_metered_customer_creation_div"
					class="row-fluid w-box-content">
					<div class="w-box-header">
						<h4 id="rightSpan_caption">Make new Customer ID</h4>
					</div>
					<br />

					<form id="meterForm" name="" >

						<input name="oldCustomerID" id="metered_customer" type="text"
							style="display:none;" />

						<div class="row-fluid" style="margin-left: 5px;">
							<select name="newArea" id="active_area_meter" style="width: 30%;">
								<option value="">New Area</option>
								<s:iterator value="%{#application.ACTIVE_AREA}" id="new_area">
									<option value="<s:property value="area_id"/>">
										<s:property value="area_name" />
									</option>
								</s:iterator>
							</select>
							<!-- value="%{#application.ACTIVE_NON_CUSTOMER_CATEGORY}" -->
							<select id="customer_category_meter" hidden style="width: 35%; "
								name="newCategory" onchange="fetchCategoryName()">
								<option value="" selected="selected">Metered Category</option>
								<s:iterator
									value="%{#application.ACTIVE_METERED_CUSTOMER_CATEGORY}"
									id="categoryListZ">
									<option value="<s:property value="category_id" />">
										<s:property value="category_name" />
									</option>
								</s:iterator>
							</select> <input name="newExtention" placeholder="Serial" type="text"
								id="extention_meter" maxlength="10"
								style="border: 1px solid #add9e4;width: 30%;;font-weight: bold;color: blue;" />

						</div>
						

					</form>
					
					<button class="btn btn-success pull-right" style="margin-right: 20px; margin-bottom: 77px;"  onclick="updateCustomerInfoMeter()">Change ID</button>
					<br/>
				</div>


				<div id="new_non_metered_customer_creation_div"
					class="row-fluid w-box-content" >
					<div class="w-box-header">
						<h4 id="rightSpan_caption">Make new Customer ID</h4>
					</div>
					<br />
					<form id="nonMeterForm" name="" >

						<input name="oldCustomerID" id="non_metered_customer" type="text"
							style="display:none;" />

						<div class="row-fluid" style="margin-left: 5px;">
							<select name="newArea" id="non_meter_active_area" style="width: 30%;">
								<option  value="">New Area</option>
								<s:iterator value="%{#application.ACTIVE_AREA}" id="new_area">
									<option value="<s:property value="area_id"/>">
										<s:property value="area_name" />
									</option>
								</s:iterator>
							</select>
							<!-- value="%{#application.ACTIVE_NON_CUSTOMER_CATEGORY}" -->
							<select id="customer_category_non_meter" hidden style="width: 35%; "
								name="newCategory" onchange="fetchCategoryName()">
								<option value="" selected="selected">Non-Metered
									Category</option>
								<s:iterator value="%{#application.ACTIVE_NON_CUSTOMER_CATEGORY}"
									id="categoryListZ">
									<option value="<s:property value="category_id" />">
										<s:property value="category_name" />
									</option>
								</s:iterator>
							</select> <input name="newExtention" type="text" id="extention_non_meter"
								placeholder="Serial" maxlength="10"
								style="border: 1px solid #add9e4;width: 30%;;font-weight: bold;color: blue;" />

							

						</div>
					</form>
					<button style="margin-right: 20px; margin-bottom: 77px;" class="btn btn-success pull-right" onclick="updateCustomerInfoNonMeter()">Change ID</button>
					
				</div>






			</div>










		</div>
	</div>


	<div style="width: 60%;height: 98%;float: left;margin-left: 0px;">



		<div style="width: 100%;float: left;margin-top: 0px;">
			<div class="row-fluid">
				<div style="width: 100%;height: 98%;float: left;">
					<div style="width: 100%;float: left;margin-top: 0px;">
						<div class="w-box">
							<div class="w-box-header">
								<h4 id="rightSpan_caption">Customer Information</h4>
							</div>
							<div class="w-box-content" style="padding: 10px;"
								id="content_div">




								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Customer Name</label> <input
											disabled="disabled" type="text" style="width: 65%"
											id=full_name name="personal.full_name" />

									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Father Name</label> <input
											disabled="disabled" type="text" style="width: 65%"
											name="personal.father_name" id="father_name" />

									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Mother Name</label> <input
											disabled="disabled" type="text" style="width: 65%"
											name="personal.mother_name" id="mother_name" />

									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Category</label> <input type="text"
											style="width: 65%" name="customer.customer_category"
											id="customer_category_name" disabled="disabled" />

									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Area/Region</label> <input
											type="text" style="width: 65%" name="customer.area"
											id="area_name" disabled="disabled" />

									</div>
								</div>



								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Address Line 1</label> <input
											disabled="disabled" type="text" name="address.address_line1"
											id="address_line1" style="width: 65%;" />
									</div>
								</div>
								<div class="row-fluid">
									<div class="span6 extra">
										<label style="width: 30%">Address Line 2</label> <input
											disabled="disabled" type="text" name="address.address_line2"
											id="address_line2" style="width: 65%;" />
									</div>
								</div>



							</div>
						</div>
					</div>
				</div>
			</div>


		</div>


	</div>


</div>





<script type="text/javascript"
	src="/JGTDSL_WEB/resources/js/page/changeCustomerID.js"></script>