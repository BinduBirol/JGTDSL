Calendar.setup($.extend(true, {}, calOptions,{
    inputField : "from_date",
    trigger    : "from_date",
    onSelect   : function() { this.hide();}}));
Calendar.setup($.extend(true, {}, calOptions,{
    inputField : "to_date",
    trigger    : "to_date",
    onSelect   : function() { this.hide();}}));

$("#from_date").val(getCurrentDate());
$("#to_date").val(getCurrentDate());

function fetchMpgCollectionList() {

    
	
	$("#detailDiv").html("");
	$("#stat_div").show();
	$("#loading_div").html("<div class='row-fluid'><div style='width:10px;height:10px' class='loader span2'></div><div class='span6'>"+jsImg.LOADING_MID+"</div></div><font style='color:black;font-weight:bold'>Searching for the Collection list &nbsp;</font>");
	
	
	
    $.ajax({
        type    : "POST",
        url     : "fetchMPGcollections",
        dataType: 'text',
        async   : false,
        data    : {from_date: $("#from_date").val(), 
        			to_date:$("#to_date").val(),
        			bank_name:$("#bank").val()
        		  }
    	}).done(function (msg) {
    			$("#stat_div").hide();
                $("#detailDiv").html(msg);                    
            })
            .always(function () {
                //$('#sw-val-step-3').unmask();
            })
            .fail(function (data) {
                if (data.responseCode)
                    alert(data.responseCode);
            });

	
} 