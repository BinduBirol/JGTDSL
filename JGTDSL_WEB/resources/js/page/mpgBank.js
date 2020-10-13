var editParams={
		width: jsVar.CRUD_WINDOW_WIDTH,
        editCaption: jqCaption.EDIT_BANK,
        url: jsEnum.CRUD_ACTION,
        serializeEditData: function (postdata) 
        {
			if (postdata.id === '_empty')
		        postdata.id = null;
			$('#gridTable').trigger( 'reloadGrid' );
	            return {data: JSON.stringify(postdata), service:jsEnum.BANK_SERVICE,  method:"updateMpgBank"};
	           
        }		
}

jQuery("#gridTable")
    .jqGrid({
        url: jsEnum.GRID_RECORED_FETCHER+'?service='+jsEnum.BANK_SERVICE+'&method=getMpgBankList',
        jsonReader: {
            repeatitems: false,
            id: "bank_id"
        },
        height: 600,
        colNames: ['User', 'Bank Name','IP Address','Edit'],
        colModel: [{
	                name: 'bank_id',
	                index: 'bank_id',
	                width:30,	                
	                align:'center',
	                editable: false,
	                search: true,
	                editoptions: { readonly: jsEnum.READ_ONLY,maxlength: 6 },
	        		addoptions: { readonly: false },
	        		formoptions: {elmsuffix: jsEnum.STAR},
	                editrules: {required: true}
            	},
            	{
	                name: 'bank_name',
	                index: 'bank_name',
	                sorttype: "string",
	                editable: true,
	                search: true,
	                editrules: {required: true},
	                formoptions: {elmsuffix: jsEnum.STAR}
            	},
            	
            	
            	{
                    name: 'address',
                    index: 'address',
                    sorttype: "string",
                    edittype: "textarea",
                    editable: true,
                    editoptions: { rows: "5",cols:"23" },
                    editrules: {required: true},
                    formoptions: {elmsuffix: jsEnum.STAR},
                    formatter: brToNewLine
            	},
            	
            	{ 
            		name: 'Edit', 
            		width: 17,
            		cursor: 'pointer',
            		align:'center',
            		editable: false,
            		formatter: function(cellvalue, options, rowObject){	            			
            			
                          return "<button class='btn btn-info' >Edit</button>"
          
                    },
                    cellattr: function (rowId, tv, rowObject, cm, rdata) {                 	
                    	
                    	return ' onClick="deleteBillByBillId('+rowObject.month_year_customer_id+')"';
                    },
                    
                }
        ],
        caption: jqCaption.LIST_BANK,
        sortname: 'bank_name'
    }).navGrid('#gridPager',editParams);


gridColumnHeaderAlignment("gridTable",["bank_name","phone","address"],"left");





















/*
$(document).ready(function () {


	jQuery(".edit_btn").on("click", function() {
	    var $row = jQuery(this).closest("tr");
	    var $user = $row.find(".bank_user");
	    var $ip = $row.find(".ip_ads");
	
	    //$columns.addClass("row-highlight");
	    var values = "";
	
	    jQuery.each($ip, function(i, item) {        
	        values= item.innerHTML;
	        //alert($("#edit_Ip_"+values).val());
	        alert(values);
	        
	    });
    //console.log(values);
	});

	
});
*/

function createUser() {	
	


		if (($.trim($('#bname').val()) == '') || ($.trim($('#user').val()) == '')
			|| ($.trim($('#pass').val()) == '')
			|| ($.trim($('#ipadd').val()) == '')) {
		alert("Fill up the form properly with correct value!");

	} else {
		$.ajax({
	        type    : "POST",
	        url     : "createMpgUser",        
	        async   : false,
	        data    : {userid: $("#user").val(), 
	        			password:$("#pass").val(),
	        			bank_name:$("#bname").val(),
	        			ipaddress:$("#ipadd").val()
	        		  },
	        		  success: function (response) {        	        	
	        	        	alert(response.message);
	        			  
	        			 
	        	        },
	        		  error: function (jqXHR, exception) {
	        	            var msg = "";
	        	            if (jqXHR.status === 0) {
	        	                msg = 'Not connect.\n Verify Network.';
	        	            } else if (jqXHR.status == 404) {
	        	                msg = 'Requested page not found. [404]';
	        	            } else if (jqXHR.status == 500) {
	        	                msg = 'Internal Server Error [500].';
	        	            } else if (exception === 'parsererror') {
	        	                msg = 'Requested JSON parse failed.';
	        	            } else if (exception === 'timeout') {
	        	                msg = 'Time out error.';
	        	            } else if (exception === 'abort') {
	        	                msg = 'Ajax request aborted.';
	        	            } else {
	        	                msg = 'Uncaught Error.\n' + jqXHR.responseText;
	        	            }
	        	            alert("Error: "+msg);
	        	        }
	    	}).done(function (msg) {    			
	                $("#msg_div").html(msg);                    
	            })
	            .always(function (response) {
	            	//alert("always "+response);
	            })
	            .fail(function (response) {
	                
	            	alert(response.message);
	        });
	}
} 