<%@ taglib prefix="s" uri="/struts-tags"%>
 <input type="hidden" value="<s:property value='customer_id'/>" id="customer_id" />
<table class="table table-bordered">
    <thead>
        <tr>
            <th>SL</th>
            <th>Deposit Type</th>
            <th>Total</th>         
        </tr>
    </thead>
    <tbody>
       <s:if test="%{depositList.size!=0}">
			<s:iterator value="depositList" status="indx">
		        <tr>
		            <td><s:property value="#indx.count" /></td>
		            <td><s:property value="deposit_id" /></td>
		            <td><s:property value="total_deposit" /></td>		         
		        </tr>
			</s:iterator>
		</s:if>
    </tbody>
</table>

                        
