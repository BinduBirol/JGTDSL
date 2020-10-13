<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="">

<fieldset style="margin-left: 10px;margin-top: 10px;">
	<legend>Search Box</legend>
	
	<div class="row-fluid" style="min-height: 70px;width:100%; padding:10px; padding-left:25%">
		<div class="row-fluid">
						  				  	
		</div>
		<div class="row-fluid" style="padding:2px">
			<div class="span4">
					<label style="width: 40%">MPG Bank</label>								
					<select id="bank_id" style="width: 35%;" onchange="fetchSelectBox(branch_sbox);clearGridData('transaction_grid');">
		                <option value="" selected="selected">All</option>
						<s:iterator value="%{#session.USER_BANK_LIST}">
							<option value="<s:property value="bank_id" />" ><s:property value="bank_name" /></option>
						</s:iterator>    
		        </select>
		  	</div> 
		 </div>
		 <div class="row-fluid" style="padding:2px"> 	
		
			<div class="span4">
					<label style="width: 40%">From Date</label>								
					<input type="text" style="width: 35%" id="from_date" name="from_date"/>
					&nbsp;
					<i class="fa fa-eraser" style="cursor: pointer;" onclick="$('#from_date').val('');"> </i>
		  	</div>
		 </div>
		 <div class="row-fluid" style="padding:2px"> 	
		
			<div class="span4">
					<label style="width: 40%">To Date</label>								
					<input type="text" style="width: 35%" id="to_date" name="to_date"/>
					&nbsp;
					<i class="fa fa-eraser" style="cursor: pointer;" onclick="$('#to_date').val('');"> </i>
		  	</div>
		 </div>
		 
		 <div style="width: 47%;text-align: center;float: left;padding-top:20px;margin-left: 5px;display: none;" id="stat_div">
			<table>
				<tr>
					<td style="text-align: left;padding-left: 10px;padding-bottom: 20px;background-color: #387C44;color: white;"  id="loading_div"></td>
				</tr>
			</table>
		</div>
		 
		  <div class="row-fluid" style="padding:2px">	
		  	
		  	<div class="span3" align="right" >
		  			  											
					<input type="button" onclick="fetchMpgCollectionList()" class="btn btn-info" value="Search" id="search"/>
					<input type="button" class="btn btn-success" value="Print"/>
										
			</div>
		  </div>
		</div>	
		
			
	</div>
</fieldset>

<div id="detailDiv" width="100%">
		
		</div>
		
</div>
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/mpgDashboard.js"></script>
</body>
</html>