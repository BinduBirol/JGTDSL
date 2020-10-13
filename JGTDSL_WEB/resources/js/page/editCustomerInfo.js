$("#for_admin_only").hide();

$("#customer_id").unbind();
$("#customer_id").autocomplete($.extend(true, {}, acMCustomerOption,{
		serviceUrl: sBox.CUSTOMER_LIST,
    	onSelect:function (){
	
				//clearField.apply(this,old_customer_fields);
				getCustomerInfoforEdit($('#customer_id').val());
		}
}));

$("#parent_customer").unbind();
$("#parent_customer").autocomplete($.extend(true, {}, acMCustomerOption,{
		serviceUrl: sBox.CUSTOMER_LIST    	
}));



function getCustomerInfoforEdit(customer_id)
{	
    if(customer_id=="") return;
	$.ajax({
   		  type: 'POST',
   		  url: 'getCustomerInfoAsJson.action',
   		  data: { customer_id:customer_id},
   		  success:function(data){
   			
			if(typeof data.personalInfo === "undefined")
			{
				//clearCustomerInfoForm(field_prefix);
			    alert("Sorry, this is not either a valid customer or a customer whose connection has not yet been established.");
			}
			else
			{
				setCustomerInfos(data);	 
				if(typeof callback !== 'undefined'){
					callback(data);
				}
			}
						
   		  },
   		  error:function(){
   			
   		  }
   	});
}

function setCustomerInfos(data){
	

	
	//clearCustomerInfoForm(field_prefix);
	
	var personal=data.personalInfo;
	var address=data.addressInfo;
	var connection=data.connectionInfo;
	
	if(typeof data.personalInfo === "undefined")
	{}
	else{
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
		
		$("#co_date").val(connection.connection_date);
		$("#radio_"+personal.bulk_sataus).attr('checked',true);		
		$("#statusList").val(connection.status_str);
		
		
		
		$("#customer_category_name").val(data.customer_category_name);
		$("#area_name").val(data.area_name);
		$("#app_sl_no").val(data.app_sl_no);
		
		Calendar.setup($.extend(true, {}, calOptions,{
		    inputField : "co_date",
		    trigger    : "co_date",
		    onSelect   : function() {$("#co_date").val();}
		}));
		
	
		//Address
		//var divStr=(address.division_name=="" || typeof address.division_name === "undefined" )?"":"Division : "+address.division_name;
		var distStr=(address.district_id=="" || typeof address.district_id === "undefined" )?"":address.district_id;
		
		//$("#statusList option[value='"+connVal+"']").prop('selected', true);
		
		var zonestr=(address.zone_id=="" || typeof address.zone_id === "undefined" )?"":address.zone_id;
		var uzillaStr=(address.upazila_id=="" || typeof address.upazila_id === "undefined" )?"":address.upazila_id;
		
		fetchSelectBox(district_sbox);
		setTimeout(function(){ $("#district_id").val(distStr);fetchSelectBox(upazila_sbox);}, 1000);
		setTimeout(function(){ $("#upazila_id").val(uzillaStr);}, 3000);
		setTimeout(function(){ $("#zone_id").val(zonestr);}, 3000);
		
		var roadStr=(address.road_house_no=="" || typeof address.road_house_no === "undefined" )?"":address.road_house_no;
		var postOfficeStr=(address.post_office=="" || typeof address.post_office === "undefined" )?"":address.post_office;
		var postCodeStr=(address.post_code=="" || typeof address.post_code === "undefined" )?"":address.post_code;		
		var adr1Str=(address.address_line1=="" || typeof address.address_line1 === "undefined" )?"":address.address_line1;
		var adr2Str=(address.address_line2=="" || typeof address.address_line2 === "undefined" )?"":address.address_line2;
		
		$("#road_house_no").val(roadStr);
		$("#post_office").val(postOfficeStr);
		$("#post_code").val(postCodeStr);
		$("#address_line1").val(adr1Str);
		$("#address_line2").val(adr2Str);
		
		
	
	}
	
	if($("#check_role").val()=='Super Admin'){		
		$("#for_admin_only").slideDown();	
}
}


function validateAndSaveCustomerInfo(){
	
	var validate=false;
	
	validate=validateCustomerInfo();
	if(validate==true ){
		updateCustomerInfo();
	}	
}

function validateCustomerInfo(){
	//return validateField("full_name","gender","mobile","division_id","district_id","upazila_id");	
	return validateField("full_name");	
}

function updateCustomerInfo(){
	
	
	var formData = new FormData($('form')[0]);
    $.ajax({
		    url: 'updateCustomerInfo.action',
		    type: 'POST',
		    data: formData,
		    async: false,
		    cache: false,
		    contentType: false,
		    processData: false,
		    success: function (response) {
		  				   
		   		$.jgrid.info_dialog(response.dialogCaption,response.message,jqDialogClose,jqDialogParam);		   
		    }		    
		  });
	
}