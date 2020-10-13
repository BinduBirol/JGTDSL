<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 

  
  
  <style>
.loader {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid blue;
  border-right: 16px solid green;
  border-bottom: 16px solid red;
  border-left: 16px solid pink;
  width: 120px;
  height: 120px;
  -webkit-animation: spin 2s linear infinite;
  animation: spin 2s linear infinite;
}

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
  
  
  
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
					<select name="bank_name" id="bank" style="width: 35%;" >
		                <option value="all" selected="selected">All</option>
						<s:iterator value="%{#session.MPG_BANK_LIST}">
							<option value="<s:property value="bank_name" />" ><s:property value="bank_name" /></option>
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
		 
		 
		 
		  <div class="row-fluid" style="padding:2px">	
		  	
		  	<div class="span3" align="right" >
		  			  											
					<input type="button" onclick="fetchMpgCollectionList()" class="btn btn-info" value="Search" id="search"/>
					
										
			</div>
		  </div>
		</div>	
		
			
	</div>
</fieldset>
	

<div style="width: 47%;text-align: center;float: left;padding-top:20px;margin-left: 5px;display: none;" id="stat_div">
 
			<table>
				<tr>					
					<td style="text-align: left;padding-left: 10px;padding-bottom: 20px;color: black;"  id="loading_div"></td>
				</tr>
			</table>
</div>


 


<div id="detailDiv" style="max-width:98%">
		
</div>
		

<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/mpgDashboard.js"></script>
</body>
</html>