<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<%-- <script --%>
<%-- 	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js"></script> --%>



<%-- <script src="https://unpkg.com/jspdf@latest/dist/jspdf.min.js" /> --%>
<%-- <script src="https://unpkg.com/jspdf@latest/dist/jspdf.min.js" /> --%>
<%-- <script type="text/javascript" --%>
<%-- 	src="/JGTDSL_WEB/resources/js/jspdf.min.js"></script> --%>
<script type="text/javascript"
	src="/JGTDSL_WEB/resources/js/jspdf.debug.js"></script>
<%-- <script --%>
<%-- 	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js" /> --%>


<!-- Added by Nasir for styling transaction success/failure page -->
<!-- 
<link rel="stylesheet" href="./resources/css/ipg/basic.css"
	type="text/css" media="screen" />
 -->
<style>
.wrapper {
	text-align: center;
}

.button {
	position: absolute;
	top: 50%;
}
</style>


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous" />

<div id="content">
	<div id="header" style="visibility:hidden">
		Pdf generated from JGTDSL Application after transaction Completed on <span
			id="date" style="font-weight:bold;"></span>
	</div>
	<h1 style="text-align: center;">Transaction Confirmation</h1>
	<br />
	<hr />
	<form style="padding-left:5%; font-weight: bold;font-size: 20px;">
		<div class="form-group row">
			<label for="trxDate" class=" col-form-label " style="color:#008080;">Transaction
				Date:</label>

			<div class="">
				<input id="trxDate" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='trxDate' />">
			</div>
		</div>
		<div class="form-group row">
			<label for="txnStatus" class=" col-form-label "
				style="color:#008080;">Transaction Status:</label>

			<div class="">
				<input id="txnStatus" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='txnStatus' />">
			</div>
		</div>
		<div class="form-group row">
			<label for="transID" class=" col-form-label" style="color:#008080;">Transaction
				ID from software:</label>

			<div class="">
				<input id="transID" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='transID' />">
			</div>
		</div>
		<!-- 
		<div class="form-group row">
			<label for="ipgTrxID" class=" col-form-label" style="color:#008080;">Transaction
				ID from Bank:</label>

			<div class="">
				<input id="ipgTrxID" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='ipgTrxID' />">
			</div>
		</div>
		 -->
		<div class="form-group row">
			<label for="error_msg" class=" col-form-label" style="color:#008080;">Message:</label>

			<div class="">
				<input id="error_msg" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='error_msg' />">
			</div>
		</div>
		<div class="form-group row">
			<label for="card_no" class=" col-form-label" style="color:#008080;">Card
				No:</label>

			<div class="">
				<input id="card_no" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='card_no' />">
			</div>
		</div>
		<!-- 
		<div class="form-group row">
			<label for="card_name" class=" col-form-label" style="color:#008080;">Card
				Name:</label>

			<div class="">
				<input id="card_name" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='card_name'/>">
			</div>
		</div>
		 -->
		<div class="form-group row">
			<label for="trxAmount" class=" col-form-label" style="color:#008080;">Paid
				Amount:</label>

			<div class="">
				<input id="trxAmount" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='subTotal'/>">
			</div>
		</div>
		<div class="form-group row">
			<label for="card_name" class=" col-form-label" style="color:#008080;">Customer
				Id:</label>

			<div class="">
				<input id="customerId" type="text" readonly
					class="form-control-plaintext"
					value="<s:property value='customerId'/>">
			</div>
		</div>

		<!-- 

		Transaction Status: <input type="text"
			value="<s:property value='txnStatus' />" style="width: 56%;" /><br>
		Transaction ID from software: <input type="text"
			value="<s:property value='transID' />" style="width: 56%;" /><br>
		Transaction ID from Bank: <input type="text"
			value="<s:property value='ipgTrxID' />" style="width: 56%;" /><br>
		Error Msg: <input type="text" value="<s:property value='error_msg' />"
			style="width: 66%;" /><br> Card No: <input type="text"
			value="<s:property value='card_no' />" style="width: 56%;" /><br>
		Card Holder Name: <input type="text"
			value="<s:property value='card_name'/>" style="width: 56%;" /><br>
		 -->
	</form>



</div>
<!-- 
<c:choose>
    <c:when test="${txnStatus == 'SUCCESS'}">
        <h3>jstl success txnStatus</h3>  
        <br />
    </c:when>    
    <c:otherwise>
      <h3>jstl failed txnStatus</h3>
        <br />
    </c:otherwise>
</c:choose>
 -->
<div id="footer" style="visibility:hidden">JALALABAD GAS
	TRANSMISSION AND DISTRIBUTION SYSTEM LIMITED, Gas Bhaban, Mendibag,
	Sylhet – 3100</div>

<c:choose>
	<c:when test="${ipgResponse.txnStatus == 'SUCCESS'}">
		<h1
			style="color: #009F72;font-size: 40px;text-align: center;margin-top: 0px">
			Payment Success <img src="./resources/images/ipg/success.png"
				style="width: 50px;" />
		</h1>
		<br />
		<div class="wrapper">
		
	<form action="downloadIPGreceipt.action">
		<input type="hidden" readonly class="form-control-plaintext"
			value="<s:property value='transID' />"> <input
			class='btn btn-info' type="submit" id="ipg_receipt"
			value="Generate PDF" style="position:relative;" />
	</form>
</div>
	</c:when>
	<c:otherwise>
		<h1
			style="color: #f04251;font-size: 40px;text-align: center;margin-top: 0px">
			Payment Failed <img src="./resources/images/ipg/failed.png"
				style="width: 50px;" />
		</h1>
		<br />

	</c:otherwise>
</c:choose>




<!-- <div class='btn-group'> -->
<!-- 	<input class='btn btn-info' type="button" value="Generate PDF" style="text-align: center;margin-top: 0px;" onclick='toPdf();' /> -->
<!-- </div> -->

<!-- <button onclick="toPdf();"></button> -->

<!--  

    <s:if test="{txnStatus=='SUCCESS'}">
        success**
        <br />
    </s:if>    
    <s:else>
       failed**
        <br />
    </s:else>



<s:if test="{ipgResponse.txnStatus == 'SUCCESS'}">
Success
</s:if>
<s:else>
Failed
</s:else>

-->
<script>
	// 	document.getElementById("date").innerHTML = Date();
	// 	var n =  new Date();
	// 	var y = n.getFullYear();
	// 	var m = n.getMonth() + 1;
	// 	var d = n.getDate();
	// 	var hh = n.getHours();
	// 	var mm = n.getMinutes();

	// 	document.getElementById("date").innerHTML = m + "/" + d + "/" + y + " " + hh + ":" + mm;

	var dt = new Date();
	var date = dt.toLocaleDateString();
	var time = dt.toLocaleTimeString();

	document.getElementById("date").innerHTML = date + " " + time

	var doc = new jsPDF();
	// 	var doc = new jsPDF('p', 'pt', 'a4');
	var specialElementHandlers = {
		'#editor' : function(element, renderer) {
			return true;
		}
	};

	function toPdf() {

		doc.fromHTML($('#content').html(), 12, 12, {
			'width' : 170,
			'elementHandlers' : specialElementHandlers
		}, function(dispose) {
			doc.save('Puppetboard.pdf');
		});

	}

</script>

