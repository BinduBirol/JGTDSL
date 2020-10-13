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
	$("#loading_div").html(jsImg.LOADING_MID+"<br/><br/><font style='color:white;font-weight:bold'>Please wait. Searching the Collection list </font>");
	
	
	
    $.ajax({
        type    : "POST",
        url     : "fetchMPGcollections",
        dataType: 'text',
        async   : false,
        data    : {from_date: $("#from_date").val(), 
        			to_date:$("#to_date").val()
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