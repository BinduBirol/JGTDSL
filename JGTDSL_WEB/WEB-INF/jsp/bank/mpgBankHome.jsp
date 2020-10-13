




<head>

  <%@ taglib prefix="s" uri="/struts-tags"%>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  

<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/jqGridDefault.js"></script>
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/mpgBank.js"></script>
<script type="text/javascript" src="/JGTDSL_WEB/resources/js/page/jqGridCommon.js"></script>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <link href="/JGTDSL_WEB/resources/css/page/meterReading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/JGTDSL_WEB/resources/js/page/jqGridDialog.js"></script>
  
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
  
  
  
</head>


<style>

</style>

<body >

<div class="" >
  <br/>
  <ul class="nav nav-tabs">
    <li class="active"><a data-toggle="tab" href="#home">Edit Bank Info</a></li>
    <li><a data-toggle="tab" href="#menu1">Add Bank</a></li>  
  </ul>

  <div class="tab-content">
  
    <div id="home" class="tab-pane fade in active">
    
    <div id="gridWrapper">
		<table id="gridTable"></table>
		<div id="gridPager"></div>
</div>
    <!-- 
      <div class="formSep sepH_b" style="padding-top: 3px;margin-bottom: 0px;padding-bottom: 2px;">		
			
				
						
						<table width="90%" id="mpgTable" style="font-size: 20px;"  class="table-bordered table table-condensed table-striped" border="1">
							<thead>
								<tr>
									<th align="center">Serial</th>									
									<th align="center">Bank Name</th>
									<th align="center">IP Adress</th>									
									<th align="center">User ID</th>
									<th align="center">Edit</th>											
									
								</tr>
							</thead>
							<tbody>
								<s:iterator value="mpg_bank">
									<tr>
										<td id="scroll_no" align="center"><s:property value="sl" /></td>
										<td class="bank_name" id="" align="center"><s:property value="bank_name" /></td>
										<td class="ip_ads" align="center">
											<s:property value="ip_address" />
											<input  type="hidden" value="<s:property value="userid" />" class="form-control" id="user"  name="userid">
											<input type="hidden" class="form-control" id="edit_Ip_<s:property value="userid" />" value="<s:property value="ip_address" />" name="ipadd">
										</td>
										<td class="bank_user" align="left" style="max-width:100px;" ><s:property value="userid" /></td>
										<td align="center">
											<a href="#pagetwo"   class="btn btn-info edit_btn text-center">Edit IP Adress</a>
										</td>			
									</tr>
								</s:iterator>
							</tbody>							
							
						</table>
					</div>
					 -->
    </div>
    <div id="menu1" style="font-size: 20px;" class="tab-pane fade">
    
   
				 <div class="container">
					  <h2>Create Online Bank</h2>
					  <div>
					    <div class="form-group">
					      <label for="name">Bank Name:</label>
					      <input style="width: 59%" type="text" class="form-control" id="bname" placeholder="Enter Bank Name (Avoid space)" name="bank_name">
					    </div>
					    <br/>
					    <div class="form-group">
					      <label for="user">User:</label>
					      <input style="width: 59%" type="text" class="form-control" id="user" placeholder="User ID (Avoid space)" name="userid">
					    </div>	
					     <br/>
					     <div class="form-group">
					      <label for="pwd">Password:</label>
					      <input style="width: 59%" type="text" class="form-control" id="pass" placeholder="(Avoid space)" name="password">
					    </div>
					     <br/>
					     <div class="form-group">
					      <label for="ipadd">IP Address:</label>
					      <input style="width: 59%" type="text" class="form-control" id="ipadd" placeholder="Seperate by comma (Avoid space)" name="ipadd">
					    </div>	
					     <br/>			    
					    <button  onclick="createUser()" class="btn btn-beoro-3 btn-default">Submit</button>
					  </div>
				</div>

    </div>
    
    
    
    
    
  </div>
</div>






<body>







<script type="text/javascript">

  

</script>


</body>



