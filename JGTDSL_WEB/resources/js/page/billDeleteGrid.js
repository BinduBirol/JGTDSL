var p_customer_id="";
var p_month="";
var p_year="";
var p_area_id="";
var p_customer_category="";
var is_metered=1;

$("#billing_month1").val(getCurrentMonth());
$("#billing_year1").val(getCurrentYear());
$("#billing_month2").val(getCurrentMonth());
$("#billing_year2").val(getCurrentYear());



$("#bill_delete_grid").jqGrid($.extend(true, {}, scrollPagerGridOptions, {
	//url: jsEnum.GRID_RECORED_FETCHER+'?service=org.jgtdsl.models.BillingService&method=getMeteredBilledCustomerList'+'&extraFilter=area',
	url: jsEnum.GRID_RECORED_FETCHER+'?service=org.jgtdsl.models.BillingService&method=searchIndividualBill&extraFilter=area',
	jsonReader: {
            repeatitems: false,
            id: "not_bill_id"
	},
	colNames: [ 'Bill ID','Month Year','Payable Amount','Status','Issue Date','Delete'],
	   colModel: [
					{
					    name: 'month_year_customer_id',
					    index: 'month_year_customer_id',
					    width:80,
					   
					    sorttype: "string",
					    align:'left',
					    search: true,
					},
	           
	            	{
		                name: 'month_year',
		                index: 'month_year',
		                width:80,
		                sorttype: "string",
		                align:'left',
		                search: true,
	            	},
	            	
	            	{
		                name: 'total_bill_amount',
		                index: 'total_bill_amount',
		                sorttype: "string",
		                align:'right',
		                width:80,
		                search: true,
	            	},
	            	
	            	{
		                name: 'status',
		                index: 'status',
		                sorttype: "string",
		                align:'center',
		                width:80,
		                search: true,
	            	},
	            	
	            	{
		                name: 'prepeared_date',
		                index: 'prepeared_date',
		                sorttype: "string",
		                align:'center',
		                width:80,
		                search: true
	            	},
	            	            	
	            	{ 
	            		name: 'Delete', 
	            		width: 17,
	            		cursor: 'pointer',
	            		align:'center',
	            		formatter: function(cellvalue, options, rowObject){	            			
	            			
	                          return "<button class='btn btn-danger' ><span class='ui-icon 	ui-icon-closethick' style='align:center;   '></span></button>"
	          
	                    },
	                    cellattr: function (rowId, tv, rowObject, cm, rdata) {                 	
	                    	
	                    	return ' onClick="deleteBillByBillId('+rowObject.month_year_customer_id+')"';
	                    },
	                    
	                }
	        ],
	datatype: 'local',
	height: $("#bill_grid_div").height()-80,
	width: $("#bill_grid_div").width()-2,
   	pager: '#bill_grid_pager',
   	sortname: 'bill_id',
    sortorder: "desc",
	caption: "Bills to delete"
}));

jQuery("#bill_delete_grid").jqGrid('navGrid','#bill_grid_pager',$.extend({},footerButton,{search:true,refresh:true,beforeRefresh: function () {reloadBillGrid();}}),{},{},{},{multipleSearch:true});

function reloadBillGrid(){	
	
	var from_month=$("#billing_month1").val();
	var to_month= $("#billing_month2").val();
	
	if(from_month.length==1){
		from_month="0"+from_month;
	}
	
	if(to_month.length==1){
		to_month="0"+to_month;
	}
	
	 fromMonthYear= $("#billing_year1").val()+from_month;
	 toMonthYear= $("#billing_year2").val()+to_month;	
	
    var ruleArray=[["customer_id","fromMonthYear","toMonthYear",],["eq","eq","eq"],[$("#customer_id").val(),fromMonthYear,toMonthYear]];
    var $grid = $('#bill_delete_grid');
    
    //var ruleArray=[[$("#billing_year1").val()+$("#billing_month1").val(),$("#billing_year2").val()+$("#billing_month2").val()]];
    
    var caption_extra="";
    
	
	p_month=$("#billing_month").val();
	p_year=$("#billing_year").val();
	
	var postdata=getPostFilter("bill_delete_grid",ruleArray);
   	$("#bill_delete_grid").jqGrid('setGridParam',{search: true,postData: postdata,page:1,datatype:'json'});    		
	reloadGrid("bill_delete_grid");
	
	$grid.jqGrid('setCaption', ' Bills of || <b>'+$("#customer_id").val()+'</b> ||'+caption_extra);
}



Calendar.setup({
        inputField : "issue_date",
        trigger    : "issue_date",
		eventName : "focus",
        onSelect   : function() { this.hide();},
        showTime   : 12,
        dateFormat : "%d-%m-%Y",
		showTime : true
});
     

function deleteBillByBillId(bill_id)
{
	
	
	if ($("#customer_id").val().substr(2,2)=="01"||$("#customer_id").val().substr(2,2)=="09"){
		is_metered= 0;
		
	}
	
 	
	$.ajax({
	  type: 'POST',
	  url: 'deleteBill.action?is_metered='+is_metered,
	  data: {bill_id:bill_id},
	  success:function(data)
	  {	    
		alert(data.message);
        reloadBillGrid();
        $("#bill_id").show();
		//refreshTable1();
	  },
	  error:function (xhr, ajaxOptions, thrownError) {
	      alert(xhr.status);
	      alert(thrownError);
	      $("#bill_id").show();
      }
	});
	
}

reloadBillGrid();



