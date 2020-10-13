$("#new_metered_customer_creation_div").hide();
$("#new_non_metered_customer_creation_div").hide();


/*
$("#load_div_btn").click(function() {
	var oldcustomer= $("#customer_id").val();
	var oldcustomercat= oldcustomer.substring(2, 4);
	
	if(oldcustomercat=="01"||oldcustomercat=="09"){
		$("#new_metered_customer_creation_div").hide();
		$("#new_non_metered_customer_creation_div").show();

	}else{
		$("#new_metered_customer_creation_div").show();
		$("#new_non_metered_customer_creation_div").hide();

	}

});*/

$("#customer_id").unbind();
$("#customer_id").autocomplete($.extend(true, {}, acMCustomerOption, {
	serviceUrl : sBox.CUSTOMER_LIST,
	onSelect : function() {
		
		
		
		

		// clearField.apply(this,old_customer_fields);
		getCustomerInfoforEdit($('#customer_id').val());
	}
}));

function getCustomerInfoforEdit(customer_id) {

	if (customer_id == "")
		return;
	$
			.ajax({
				type : 'POST',
				url : 'getCustomerInfoAsJson.action',
				data : {
					customer_id : customer_id
				},
				success : function(data) {
					if (typeof data.personalInfo === "undefined") {
						$("#new_metered_customer_creation_div").hide();
						$("#new_non_metered_customer_creation_div").hide();
						// clearCustomerInfoForm(field_prefix);
						alert("Sorry, this is not either a valid customer or a customer whose connection has not yet been established.");
					} else {
						
						var oldcustomer= $("#customer_id").val();
						var oldcustomercat= oldcustomer.substring(2, 4);
						
						if(oldcustomercat=="01"||oldcustomercat=="09"){
							$("#new_metered_customer_creation_div").hide();
							$("#new_non_metered_customer_creation_div").show();

						}else{
							$("#new_metered_customer_creation_div").show();
							$("#new_non_metered_customer_creation_div").hide();

						}
						
						$("#non_metered_customer").val(oldcustomer);
						$("#metered_customer").val(oldcustomer);
						
						setCustomerInfos(data);
						if (typeof callback !== 'undefined') {
							callback(data);
						}
					}

				},
				error : function() {

				}
			});
}

function setCustomerInfos(data) {

	// clearCustomerInfoForm(field_prefix);

	var personal = data.personalInfo;
	var address = data.addressInfo;
	var connection = data.connectionInfo;

	if (typeof data.personalInfo === "undefined") {
	} else {
		$("#area_id").val(data.area);
		$("#full_name").val(personal.full_name);
		$("#father_name").val(personal.father_name);
		$("#mother_name").val(personal.mother_name);
		$("#mobile").val(personal.mobile);
		$("#phone").val(personal.phone);
		$("#fax").val(personal.fax);
		$("#national_id").val(personal.national_id);
		$("#gender").val(personal.gender);
		$("#email").val(personal.email);
		$("#freedom_fighter").val(personal.freedom_fighter);
		$("#ministry_id").val(personal.ministry_id);
		// $("#ministry_name").val(personal.ministry_name);

		$("#customer_category_name").val(data.customer_category_name);
		$("#area_name").val(data.area_name);
		$("#app_sl_no").val(data.app_sl_no);

		// Address
		// var divStr=(address.division_name=="" || typeof address.division_name
		// === "undefined" )?"":"Division : "+address.division_name;
		var distStr = (address.district_id == "" || typeof address.district_id === "undefined") ? ""
				: address.district_id;
		var uzillaStr = (address.upazila_id == "" || typeof address.upazila_id === "undefined") ? ""
				: address.upazila_id;

		fetchSelectBox(district_sbox);
		setTimeout(function() {
			$("#district_id").val(distStr);
			fetchSelectBox(upazila_sbox);
		}, 1000);
		setTimeout(function() {
			$("#upazila_id").val(uzillaStr);
		}, 3000);

		var roadStr = (address.road_house_no == "" || typeof address.road_house_no === "undefined") ? ""
				: address.road_house_no;
		var postOfficeStr = (address.post_office == "" || typeof address.post_office === "undefined") ? ""
				: address.post_office;
		var postCodeStr = (address.post_code == "" || typeof address.post_code === "undefined") ? ""
				: address.post_code;
		var adr1Str = (address.address_line1 == "" || typeof address.address_line1 === "undefined") ? ""
				: address.address_line1;
		var adr2Str = (address.address_line2 == "" || typeof address.address_line2 === "undefined") ? ""
				: address.address_line2;

		$("#road_house_no").val(roadStr);
		$("#post_office").val(postOfficeStr);
		$("#post_code").val(postCodeStr);
		$("#address_line1").val(adr1Str);
		$("#address_line2").val(adr2Str);

	}
}
function validateAndSaveCustomerInfo() {

	var validate = false;
	var area_code = $("#active_area").val();

	validate = validateCustomerInfo();
	/*if (validate == true) {
		alert("something");
	}*/
	updateCustomerInfo();
}

function validateCustomerInfo() {

	

	return validateField("customer_category", "active_area", "extention");
}

function updateCustomerInfoNonMeter() {

	var formData = new FormData($('form')[0]);
	var cus_id = $("#non_metered_customer").val();
	var new_id= $("#non_meter_active_area").val()+$("#customer_category_non_meter").val()+$("#extention_non_meter").val();

	if($("#non_meter_active_area").val()==""||$("#customer_category_non_meter").val()==""||$("#extention_non_meter").val()==""){
		$.jgrid.info_dialog("Failed!!","Fill up all the fields");
	}else{
		$.ajax({
			url : 'changeCustomerIDaction.action?oldCustomerID='+cus_id+'&newCustomer_id='+new_id,
			type : 'POST',
			//data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(response) {
				$.jgrid.info_dialog(response.dialogCaption, response.message,
				jqDialogClose, jqDialogParam);
				$('#nonMeterForm')[0].reset();
				$('#meterForm')[0].reset();
			}
			
		});
	}
}






function updateCustomerInfoMeter() {

	//var formData = new FormData($('form')[0]);
	var cus_id = $("#metered_customer").val();
	var new_id= $("#active_area_meter").val()+$("#customer_category_meter").val()+$("#extention_meter").val();
	
	if($("#active_area_meter").val()==""||$("#customer_category_meter").val()==""||$("#extention_meter").val()==""){
		$.jgrid.info_dialog("Failed!!","Fill up all the fields");
	}else{
		$.ajax({
			url : 'changeCustomerIDaction.action?oldCustomerID='+cus_id+'&newCustomer_id='+new_id,
			type : 'POST',
			//data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(response) {
				$.jgrid.info_dialog(response.dialogCaption, response.message,
				jqDialogClose, jqDialogParam);
				$('#meterForm')[0].reset();
				$('#nonMeterForm')[0].reset();
			}
			
		});
	};
}

/*


$('#nonMeterForm').submit(function(){
	
	var oldid1= $("#non_metered_customer").val();
	var newarea1= $("#non_meter_active_area").val();
	var newcat1=$("#customer_category_non_meter").val();
	var ext1=$("#extention_non_meter").val();
	
    $.ajax({
    	url: 'changeCustomerIDaction.action?oldCustomerID='+oldid1+'&newArea='+newarea1+'&newCategory='+newcat1+'&newExtention='+ext1,
      type: 'POST',
      data : $('#nonMeterForm').serialize(),
      success:function (response) {
          alert(response);
      }
    });
    return false;
});

$('#meterForm').submit(function(){
	
	var oldid2= $("#metered_customer").val();
	var newarea2= $("#active_area").val();
	var newcat2=$("#customer_category_meter").val();
	var ext2=$("#extention_meter").val();
	
	
    $.ajax({
      url: 'changeCustomerIDaction.action?oldCustomerID='+oldid2+'&newArea='+newarea2+'&newCategory='+newcat2+'&newExtention='+ext2,
      type: 'POST',
      data : $('#meterForm').serialize(),
      success: function (response) {
          alert(response);
      }
    });
    return false;
});

$('select').on('change', function() {
	val area = $("#non_meter_active_area").val();
	  $("#area_cat_val").val($("#active_area_meter").val()+$("#customer_category_meter").val());
});

*/

$('select').on('change', function() {
	area_cat_code_nm= $("#non_meter_active_area").val()+$("#customer_category_non_meter").val();
	area_cat_code_m= $("#active_area_meter").val()+$("#customer_category_meter").val();
	
	  $("#readonly_non_metered").val(area_cat_code_nm);
	  $("#readonly_metered").val(area_cat_code_m);
	
});