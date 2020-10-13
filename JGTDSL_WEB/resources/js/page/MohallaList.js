var editParams={
		 width: jsVar.CRUD_WINDOW_WIDTH,
         editCaption: jqCaption.EDIT_CUSTOMER_CATEGORY,
         url: jsEnum.CRUD_ACTION,
         serializeEditData: function (postdata) 
         {
	 		if (postdata.id === '_empty')
	 	        postdata.id = null;
	             return {data: JSON.stringify(postdata), service:jsEnum.CUSTOMER_SERVICE,  method:jsEnum.CUSTOMER_CATEGORY_UPDATE};
         },
         afterSubmit: jqGridDataPostResponseHandler,
         errorTextFormat: formatErrorText
}


var addParams={
		closeAfterAdd:true,
        width: jsVar.CRUD_WINDOW_WIDTH,
        addCaption:'Add Moholla List ' ,
        url: jsEnum.CRUD_ACTION,
        serializeEditData: function (postdata) 
        {
    		if (postdata.id === '_empty')
    	        postdata.id = null;
                return {data: JSON.stringify(postdata), service:jsEnum.CUSTOMER_SERVICE,  method:jsEnum.CUSTOMER_MOHOLLA_ADD};
        },
        afterShowForm: function(frm) {
    		disableOnClick();
       		$("#zones_name").focus();
        },
        beforeShowForm: function( formId ) {
		   $('#zone_id', formId ).attr( jsEnum.READ_ONLY, false );    
		   $.ajax({type: 'POST',url: jsEnum.NEXT_ID_ACTION,
	   		  data: { service:jsEnum.CUSTOMER_SERVICE,method:jsEnum.NEXT_ID_METHOD,data: '{\"id\":\"\"}'},
	   		  success:function(data){if(data.status=="OK")$('#zone_id', formId).val(data.message);else{$("#zone_id").focus();alert(jsMsg.ERROR_NEXT_ID);}},
	   		  error:function(){}
		   });
		},
		afterSubmit: jqGridDataPostResponseHandler, 
   		errorTextFormat: formatErrorText
}




var deleteParams={
		beforeShowForm:function(form) {    
    		ret = $("#gridTable").getRowData($("#gridTable").jqGrid('getGridParam','selrow'));
    		$("td.delmsg",form).html("Are you sure you want to delete the selected record ?<br>Category Id :<b>"+ret.zone_id+'</b><br>Category Name: <b>'+ret.zone_name+"</b>");            
		},
		onclickSubmit: function (rp_ge, postdata) {		
			var ret = $("#gridTable").getRowData($("#gridTable").jqGrid('getGridParam','selrow'));	    
		    rp_ge.url = jsEnum.CRUD_ACTION+"?" +
		        $.param({
		        	url:jsEnum.GRID_RECORED_FETCHER+'?service=org.jgtdsl.models.CustomerService&method=deleteZoneArealist',
		            data: '{\"id\":\"'+ret.category_id+'\"}'
		        });
		},
		afterSubmit: jqGridDataDeleteResponseHandler
}

var viewParams ={
		width: jsVar.CRUD_WINDOW_WIDTH,
		caption: jqCaption.VIEW_CUSTOMER_CATEGORY
}


jQuery("#gridTable")

    .jqGrid({
        url: jsEnum.GRID_RECORED_FETCHER+'?service=org.jgtdsl.models.CustomerService&method=getZoneAreaList',
        jsonReader: {
	        datatype:"json",	
	        repeatitems: false,
	        id: "zone_id"            
        },
        colNames: [ 'Moholla Name', 'Moholla Id','Area Name','Area Id'],
        colModel: [
        		   {
		                name: 'zones_name',
		                index: 'zones_name',
		                sorttype: "string",
		                width:30,
		                align:'center',
		               editable: true,
		                search: true,
		                editrules: {required: true},
		                formoptions: {elmsuffix: jsEnum.STAR}
        		   },
        		   {
	                    name: 'zone_id',
	                    index: 'zone_id',
	                    sorttype: "string",
	                    width:30,
	                    align:'center',
	                    editable: true,
	                    edittype: "select",	                   
	                    editrules: {required: true},
	                    editoptions: { readonly: false },
	                    formoptions: {elmsuffix: jsEnum.STAR}
	                   
	                    
        		   },
        		   {
		                name: 'area_name',
		                index: 'area_name',
		                width:30,
		                align:'center',
		                sorttype: "string",
		                editable: true,
		                search: true,
		                editoptions: { readonly: jsEnum.READ_ONLY,maxlength: 2 },
		        		addoptions: { readonly: false },
		        		/*	
		        		formoptions: {elmsuffix: jsEnum.STAR},
		                editrules: {required: true}*/
       		   },        		  
        		   {
        			name: 'area_id',
        			index: 'area_id',
        			sorttype: "string",    
        			 width:30,editable:true, 
       	         editrules:{
        	                         required:true, 
        	                         edithidden:true
        	                      },
        	            hidden:true, 
        	            editoptions:{ 
    	                dataInit: function(element) {                     
        	                             jq(element).attr("readonly", "readonly"); 
        	                          } 
        	                     }
        		   },
        		   
        		   
        ],
        caption:'Moholla List',
        sortname: 'zones_name'  
    }).navGrid('#gridPager',{edit:true,add:true,del:true,search:true,refresh:true},{},editParams,addParams,deleteParams,{},viewParams);

/*
jQuery("#gridTable").jqGrid (
        "navButtonAdd","#gridPager",
         {
             caption: "", buttonicon: "ui-icon-print", title: "Download Report",
             onClickButton: function() {
        		window.location="customerCategoryReport.action";
          }
});
*/