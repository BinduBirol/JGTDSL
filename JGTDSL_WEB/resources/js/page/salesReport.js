checkType("area_wise");

function checkType(type){
	if(type=="area_wise"){
		 disableChosenField("customer_id");
		 disableField("customer_category");
		 resetSelectBoxSelectedValue("customer_category");
		 autoSelect("area_id");
		 enableField("area_id");
		 enableField("customer_Type");
		 enableField("bill_month");
		 enableField("bill_year");
	} else if(type=="all_area"){
		disableField("area_id");
		disableField("customer_category");
		enableField("customer_Type");
	}else if(type=="area_wise_f_all"){
		 disableChosenField("customer_id");
		 disableField("customer_category");
		// resetSelectBoxSelectedValue("customer_category");
		// autoSelect("area_id");
		 disableField("area_id");
		// enableField("customer_Type");
		 enableField("bill_month");
		 enableField("bill_year");
	}
	
	
	
	
	else if(type=="by_category"){
	 disableChosenField("customer_id");
	 disableChosenField("customer_Type");
	 enableField("customer_category","area_id");
	 autoSelect("customer_category","area_id");
	}
	else if(type=="individual"){
	 enableChosenField("customer_id");
	 disableField("customer_category","area_id");
	 resetSelectBoxSelectedValue("customer_category","area_id");
	}
	else if(type=="by_category_type")
	{
		enableField("area_id");
		autoSelect("area_id");
		disableField("customer_category");		
	}
	else if(type=="by_category_type_f_all")
	{
		disableField("area_id");
		//autoSelect("area_id");
		disableField("customer_category");		
	}

	
	
	else if(type=="by_aday_bokeya"){
		enableField("area_id");
		autoSelect("area_id");
		disableField("customer_category");
	}
	
	if(type=="month_wise")
		{
		hideElement("from_to_date_div");
		showElement("month_div","year_div");
		}else if(type=="year_wise")
			{			
			hideElement("from_to_date_div","month_div");
			showElement("year_div");
			}else if(type=="date_wise")
			{
				
				hideElement("month_div","year_div");
				showElement("from_to_date_div");
				
				}
}	

$("#billing_month").val(getCurrentMonth());
$("#billing_year").val(getCurrentYear());

$("#month_div").hide();
$("#year_div").hide();
$("#from_to_date_div").hide();

Calendar.setup({
    inputField : "to_date",
    trigger    : "to_date",
	eventName : "focus",
    onSelect   : function() { this.hide();},        
    showTime   : 12,
    dateFormat : "%d-%m-%Y",
	showTime : true
	//onBlur: focusNext		
  });
  Calendar.setup({
    inputField : "from_date",
    trigger    : "from_date",
	eventName : "focus",
    onSelect   : function() { this.hide();},        
    showTime   : 12,
    dateFormat : "%d-%m-%Y",
	showTime : true
	//onBlur: focusNext		
  });
  
function fetchCategoryName()
{

	$("#category_name").val($( "#customer_category option:selected" ).text());
}
