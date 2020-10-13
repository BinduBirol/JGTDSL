var p_customer_id="";
var p_month="";
var p_year="";
var p_area_id="";
var p_customer_category="";
var p_bill_id="";

$("#bill_grid").jqGrid($.extend(true, {}, scrollPagerGridOptions, {
	url: jsEnum.GRID_RECORED_FETCHER+'?service=org.jgtdsl.models.BillingService&method=getFFBills',
   	jsonReader: {
            repeatitems: false,
            id: "customer_id"
	},
   colNames: ['Customer Id', 'Customer Name','Bill Month','Bill Year','Bill Amount','Collected Amount','JV Weaver'],
   colModel: 	[
				{
	                name: 'customer_id',
	                index: 'customer_id',
	                width:40,
	                align:'center',
	                sorttype: 'string',
	                search: true
            	},
            	{
	                name: 'customer_name',
	                index: 'customer_name',
	                width:70,
	                sorttype: "string",
	                search: true,
            	},
            	{
	                name: 'month_id',
	                index: 'month_id',
	                sorttype: "string",
	                align:'center',
	                width:20,
	                search: true,
            	},      
            	{
	                name: 'year',
	                index: 'year',
	                sorttype: "string",
	                align:'center',
	                width:20,
	                search: false,
            	},
            	{
	                name: 'bill_amount',
	                index: 'bill_amount',
	                sorttype: "string",
	                align:'right',
	                width:30,
	                search: false,
            	},
            	{
	                name: 'collected_billed_amount',
	                index: 'collected_billed_amount',
	                sorttype: "string",
	                align:'right',
	                width:30,
	                search: false,
            	},
            	{
	                name: 'jvWeaverAmount',
	                index: 'jvWeaverAmount',
	                sorttype: "string",
	                align:'right',
	                width:30,
	                search: false,
            	},
//            	 {
//                    name: 'Select',
//                    width: 30,
//                    align: "center",
//                    editoptions: { value: "True:False" },
//                    editrules: { required: true },
//                    formatter: "checkbox",
//                    formatoptions: { disabled: false , defaultValue: "True"},
//                    editable: true  }
            	
            	],
	datatype: 'local',
	height: $("#bill_grid_div").height()-80,
	width: $("#bill_grid_div").width()-2,
   	pager: '#bill_grid_pager',
   	sortname: 'customer_id',
    sortorder: "asc",
    multiselect: true,
	caption: "Freedom Fighter Bills for JV Collection",
	
	/*
    beforeSelectRow : function(rowid, e) {
    	p_bill_id=rowid;
	alert(rowid);
	var rowData = jQuery('#bill_grid')
			.jqGrid('getRowData', rowid);
	alert(rowData.bill_amount);
	var $self = $(this), 
	iCol = $.jgrid.getCellIndex($(e.target).closest("td")[0]), 
	cm = $self.jqGrid("getGridParam","colModel");
	if (cm[iCol].name === "Select" && e.target.tagName.toUpperCase() === "INPUT") {
		// set local grid data
		alert(JSON.stringify(rowData));
	}
	$('#bill_grid').attr('checked', true);
	return true; // allow selection
}*/
}));

//get checked bill id

//$("#saveCollection").click(function(){
//	 var grid = $("#bill_grid");
//     var rowKey = grid.getGridParam("selrow");
//
//     if (!rowKey)
//         alert("Nothing is selected");
//     else {
//         var selectedIDs = grid.getGridParam("selarrrow");
//         var result = "";
//         for (var i = 0; i < selectedIDs.length; i++) {
//             result += selectedIDs[i] + ",";
//         }
//
//         alert(result);
//     }                
//});


jQuery("#bill_grid").jqGrid('navGrid','#bill_grid_pager',$.extend({},footerButton,{search:true,refresh:true,beforeRefresh: function () {reloadBillGrid();}}),{},{},{},{multipleSearch:true});
gridColumnHeaderAlignment("left","bill_grid",["full_name"]);




function reloadBillGrid(){
	
	var customer_id=$("#customer_id").val();
	
	var frommonth=parseInt($("#from_month").val());
	var frommonthyear= parseInt($("#from_year").val());
	var tomonth=parseInt($("#to_month").val());
	var tomonthyear= parseInt($("#to_year").val());
	
	
	if(frommonth==12){
		frommonth=1;
		frommonthyear=frommonthyear+1;
	}else{
		frommonth=frommonth+1;
	}
	
	if(tomonth==12){
		tomonth=1;
		tomonthyear=tomonthyear+1;
	}else{
		tomonth=tomonth+1;
	}
	
	//alert(frommonth+" | "+frommonthyear);
	//alert(tomonth+" | "+tomonthyear);
	
	
	if(frommonth.length==1){
		frommonth="0"+frommonth;
	}
	if(tomonth.length==1){
		tomonth="0"+tomonth;
	}
	
	
	
	var frommonthyear2 =frommonth+"/"+frommonthyear;
	
	var tomonthyear2 =tomonth+"/"+tomonthyear;
	//alert(frommonthyear+" "+tomonthyear);
    var ruleArray=[["TOMY","FRMY","area_id"],["eq","eq","eq"],[frommonthyear2,tomonthyear2,customer_id]];
    //alert(ruleArray[2]);
    var $grid = $('#bill_grid');
    var caption_extra="";
    
//    if($("#billing_month").val()=="" || $("#billing_year").val()==""){
//		clearGridData("bill_grid");
//		$grid.jqGrid('setCaption','Please Select Month, Year');
//		return;
//	}
    
	//caption_extra=". "+$("#area_id option:selected").text()+" ["+$("#MINISTRY_ID option:selected").text()+"] ";
	
	
	var postdata=getPostFilter("bill_grid",ruleArray);
	//alert(postdata);
   	$("#bill_grid").jqGrid('setGridParam',{search: true,postData: postdata,page:1,datatype:'json'});    		
	reloadGrid("bill_grid");
	
	$grid.jqGrid('setCaption', 'FF Customers Bill List - '+$("#billing_month option:selected").text()+', '+$("#billing_year").val()+caption_extra);
}


// start

function validateAndSaveGankGarantieExpireExtentionInfo(){
	
	
	var validate=false;
	
	validate=validateGankGarantieExpireExtentionInfo();
	if(validate==true){	    
		saveGankGarantieExpireExtentionInfo();							
	}	
}

function validateGankGarantieExpireExtentionInfo(){
	
	var isValid=false;	
    isValid=validateField("customer_id","from_month","from_year","to_month","to_year","jvNo","bill_amount");	
   // alert(isValid);
	return isValid;
}
function saveGankGarantieExpireExtentionInfo(){
	
	//bankGarantieExpireExtentionForm(enableField);
	//bankGarantieExpireExtentionForm(readOnlyField);
	var customer_id=$("#customer_id").val();
	alert(customer_id);
	var form = document.getElementById('billProcessForm');
	//var formData = new FormData($('form')[0]);
	var formData = new FormData(form);
	alert(formData);
	 $.ajax({
		    url: 'saveFFCollection.action',
		    type: 'POST',
		    data: formData,
		    async: false,
		    cache: false,
		    contentType: false,
		    processData: false,
		    success: function (response) {
		  	  
		      if(response.status=="OK"){
		    	  bankGarantieExpireExtentionForm(clearField);
//		    	  bankGarantieExpireExtentionForm(removeReadOnlyField);
//		    	  bankGarantieExpireExtentionForm(disableField);
		    	  //disableButton("saveCollection");
		    	  reloadGrid("bill_grid");
		      }
		   
		   		$.jgrid.info_dialog(response.dialogCaption,response.message,jqDialogClose,jqDialogParam);		   
		    }
		  });
	
}

function bankGarantieExpireExtentionForm(plainFieldMethod){
	var fields=["customer_id","jvNo","bill_amount"];	
	plainFieldMethod.apply(this,fields);	
}


// end

 
function validateAndProcessBilling()
{

 var isValid=valiateFields();

 
 
 if(isValid==true)	 {
	 $("#depositDetailDiv").show();
	var form = document.getElementById('billProcessForm');
	var formData = new FormData(form);
	  $.ajax({
	    url: 'processBill.action',
	    type: 'POST',
	    data: formData,
	    async: false,
	    cache: false,
	    contentType: false,
	    processData: false,
	    success: function (response) {
	    $("#depositDetailDiv").hide();
	    if(response.status=="OK")
	    {
	       var customer_data = [{"customer_id":$("#customer_id").val(),"full_name":$("#customer_name").val(),"category_name":$.trim($("#customer_category option:selected").text()),"area_name":$.trim($("#area_id option:selected").text()),"mobile":$("#mobile").val(),"status":$("#connection_status").val() }];
		   $("#customer_table").jqGrid('addRowData', $("#customer_id").val(),customer_data[0] , "first");
		   cleanAllFields();				     
	    }	
	       $.jgrid.info_dialog(response.dialogCaption,response.message,jqDialogClose,jqDialogParam);
	    
	    }
	    
	  });
	
	}
}

function valiateFields(){
	 var isValid=true;
	 var bill_for=getRadioCheckedValue("bill_parameter\\.bill_for"); 
	 
	 if(bill_for=="area_wise")
	 	isValid=validateField("area_id","issue_date");
	 else if(bill_for=="category_wise")
	 	isValid=validateField("area_id","customer_category","issue_date");
	 else if(bill_for=="individual_customer")
	 	isValid=validateField("customer_id","issue_date");

	 if(isValid==true)
	 	isValid=validateField("billing_month","billing_year");
	 
	 return isValid;
}
$("#customer_id").unbind();
$("#customer_id").autocomplete($.extend(true, {}, acMCustomerOption,{
	    serviceUrl: sBox.METERED_CUSTOMER_LIST,
    	onSelect:function (){
    		getCustomerInfo("",$('#customer_id').val());
    	},
}));

$("#from_month").val(getCurrentMonth()-1);
$("#from_year").val(getCurrentYear());
$("#to_month").val(getCurrentMonth());
$("#to_year").val(getCurrentYear());
//$("#issue_date").val(getCurrentDate());
reloadBillGrid();

	
function cleanAllFields(){}	
function unlockDatabase()
{
disableButton("btn_unlockDB");
$.ajax({
    url: 'unlockDatabase.action',
    type: 'POST',
    data: {isMetered:$("#isMeter").val()},
    cache: false,
    success: function (response) {
    	enableButton("btn_unlockDB");
    	$.jgrid.info_dialog(response.dialogCaption,response.message,jqDialogClose,jqDialogParam);
    }
    
  });

}
	