package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.CustomerListDTO;
import org.jgtdsl.dto.TransactionDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MixedCustomerInfoReport extends BaseAction {
	private static final long serialVersionUID = 1L;
	public  ServletContext servlet;
	private  String report_for; 
	
	public String getReport_for() {
		return report_for;
	}


	public void setReport_for(String report_for) {
		this.report_for = report_for;
	}

	Connection conn = ConnectionManager.getConnection();
	CustomerListDTO customerListDTO = null;
	ArrayList<CustomerListDTO> MixedCustomerListInfo=new ArrayList<CustomerListDTO>();


    
	    static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
		static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
		static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
		static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
		static DecimalFormat  taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
		static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
		UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
		
	public String execute() throws Exception
	{
				
		String fileName="MixedCustomerList.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4.rotate());
		document.setMargins(20,20,80,80);
		PdfPCell pcell=null;
		
		
		try{
			
			ReportFormat2 eEvent = new ReportFormat2(getServletContext());
			
			PdfWriter.getInstance(document, baos).setPageEvent(eEvent);
			
			document.open();
			/*
			PdfPTable headerTable = new PdfPTable(3);
		   
				
			headerTable.setWidths(new float[] {
				5,190,5
			});

			
			pcell= new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);
			
			
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
			   Image img = Image.getInstance(realPath);
			      
			             //img.scaleToFit(10f, 200f);
			             //img.scalePercent(200f);
			            img.scaleAbsolute(32f, 35f);
			            //img.setAbsolutePosition(145f, 780f);  
			             img.setAbsolutePosition(228f, 520f);  // rotate
			            
			         document.add(img);			
			
			PdfPTable mTable=new PdfPTable(1);
			//mTable.setWidthPercentage(90);
			//mTable.setWidths(new float[]{100});
			pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED",fonth));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);	
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("(A COMPANY OF PETROBANGLA)", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);

			Chunk chunk1 = new Chunk("REGIONAL OFFICE : ",font2);
			Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(loggedInUser.getArea_id())-1]),font3);
			Paragraph p = new Paragraph(); 
			p.add(chunk1);
			p.add(chunk2);
			pcell=new PdfPCell(p);
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(0);
			mTable.addCell(pcell);
					
			pcell=new PdfPCell(mTable);
			pcell.setBorder(0);
			headerTable.addCell(pcell);
					
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(0);
			headerTable.addCell(pcell);
			document.add(headerTable);
			*/

			generatePdfMixedustomer(document);	
				
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
			
		    
		}catch(Exception e){e.printStackTrace();}
		
		return null;		
	}
	
	private void generatePdfMixedustomer(Document document) throws DocumentException
	{
		document.setMargins(0,0,70,72);
		PdfPTable headLinetable = null;
		PdfPCell pcell=null;
		headLinetable = new PdfPTable(3);
		headLinetable.setWidthPercentage(100);
		headLinetable.setWidths(new float[]{10,80,10});
		
		pcell = new PdfPCell(new Paragraph("",ReportUtil.f8));
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		String reportSign="Mixed customer Info";		
		
		pcell=new PdfPCell(new Paragraph(reportSign,ReportUtil.f11B));
		//pcell.setMinimumHeight(10f);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(""));
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		document.add(headLinetable);
		
		
		
		PdfPTable datatable1 = new PdfPTable(9);
		
		datatable1.setWidthPercentage(99);
		datatable1.setWidths(new float[] {10,20,50,20,20,10,20,30,20});
		
		pcell=new PdfPCell(new Paragraph(" ",font3));		
		pcell.setPadding(3);
		pcell.setBorder(0);
		pcell.setColspan(9);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sl",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Parent ID",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("P Name, Address",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("P Meter Sl no",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("P Min&Max load",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sl",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sub ID",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sub Name, Address",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		pcell=new PdfPCell(new Paragraph("Sub Burners",font3));		
		pcell.setPadding(3);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		datatable1.addCell(pcell);
		
		MixedCustomerListInfo= getMixedCustomerList();
		int i=1;
		
		for(CustomerListDTO x: MixedCustomerListInfo){
			String[] sub_cus_id_arr ;
			String[] sub_cus_name_arr ;
			String[] sub_cus_address_arr ;
			String[] sub_cus_burner_arr ;
			int arr_length=0;
			
			
			
				
				sub_cus_id_arr = x.getSub_ids().split("@");
				arr_length= sub_cus_id_arr.length;	
				
				sub_cus_name_arr = x.getSub_names().split("@");
				sub_cus_address_arr = x.getSub_address().split("@");
				sub_cus_burner_arr = x.getSub_burners().split("@");
			
			
				
			
			
				pcell=new PdfPCell(new Paragraph(String.valueOf(i),font2));		
				pcell.setPadding(3);
				pcell.setRowspan(arr_length);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomerId(),font2));		
				pcell.setPadding(3);
				pcell.setRowspan(arr_length);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getCustomerName()+",\n"+x.getCustomerAddress(),font2));		
				pcell.setPadding(3);
				pcell.setRowspan(arr_length);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(x.getMeter_info(),font2));		
				pcell.setPadding(3);
				pcell.setRowspan(arr_length);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Min: "+x.getMin_load()+"\nMax: "+x.getMaxLoad(),font2));		
				pcell.setPadding(3);
				pcell.setRowspan(arr_length);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
			
			
			for(int p=0; p<arr_length;p++){
				
				pcell=new PdfPCell(new Paragraph(String.valueOf(i)+"."+String.valueOf(p+1),font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(sub_cus_id_arr[p],font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(sub_cus_name_arr[p]+",\n"+sub_cus_address_arr[p],font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
				
				String [] burner_split_arr= sub_cus_burner_arr[p].split("#");
				
				pcell=new PdfPCell(new Paragraph("Single: "+burner_split_arr[0]+", Double: "+burner_split_arr[1],font2));		
				pcell.setPadding(3);
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				datatable1.addCell(pcell);
			}
			i++;
		}
		
		
	datatable1.setHeaderRows(2);
	document.add(datatable1);
		
	}
	
	
	
	private ArrayList<CustomerListDTO> getMixedCustomerList(){
		
		
		ArrayList<CustomerListDTO> MixedCustomerList= new ArrayList<CustomerListDTO>();
		try {
			String MixedCustomer_info_sql=
					" SELECT CUSTOMER_ID, "
					+ "         AA.FULL_NAME, "
					+ "         AA.ADDRESS, "
					+ "         AA.MIN_LOAD, "
					+ "         AA.MAX_LOAD, "
					+ "         nvl((SELECT LISTAGG (bb.CUSTOMER_ID ,  "
					+ "                          '@') "
					+ "                 WITHIN GROUP (ORDER BY aa.CUSTOMER_ID ASC) "
					+ "            FROM MVIEW_CUSTOMER_INFO bb "
					+ "           WHERE bb.PARENT_CONNECTION = aa.CUSTOMER_ID),'N/A') "
					+ "            sub_cust, "					
					+ " nvl((SELECT LISTAGG (bb.Full_name, '@') "
					+ "                    WITHIN GROUP (ORDER BY aa.CUSTOMER_ID ASC) "
					+ "            FROM MVIEW_CUSTOMER_INFO bb "
					+ "           WHERE bb.PARENT_CONNECTION = aa.CUSTOMER_ID),'N/A') "
					+ "            sub_cust_name, "							
					+ " nvl((SELECT LISTAGG (getburner (bb.CUSTOMER_ID), '@') "
					+ "           WITHIN GROUP (ORDER BY aa.CUSTOMER_ID ASC) "
					+ "   FROM MVIEW_CUSTOMER_INFO bb "
					+ "  WHERE bb.PARENT_CONNECTION = aa.CUSTOMER_ID),'0#0') "
					+ "            sub_burner, "					
					+ "         nvl((SELECT LISTAGG (bb.ADDRESS, '@') "
					+ "                    WITHIN GROUP (ORDER BY aa.CUSTOMER_ID ASC) "
					+ "            FROM MVIEW_CUSTOMER_INFO bb "
					+ "           WHERE bb.PARENT_CONNECTION = aa.CUSTOMER_ID),'N/A') "
					+ "            sub_address, "
					+ "         nvl((SELECT LISTAGG (cm.METER_SL_NO, ', ') "
					+ "                    WITHIN GROUP (ORDER BY aa.CUSTOMER_ID ASC) "
					+ "            FROM CUSTOMER_METER cm "
					+ "           WHERE cm.CUSTOMER_ID = aa.customer_id AND status <> 0),'N/A') "
					+ "            meter_info "
					+ "    FROM MVIEW_CUSTOMER_INFO aa "
					+ "   WHERE HAS_SUB_CONNECTION = 'Y' and AA.status=1 and AA.AREA_ID= '"+loggedInUser.getArea_id()+"' "
					+ " ORDER BY CUSTOMER_ID ";
											
											
											
			PreparedStatement stmt=conn.prepareStatement(MixedCustomer_info_sql);
			ResultSet r=stmt.executeQuery();
			while(r.next()){
				customerListDTO=new CustomerListDTO();
				customerListDTO.setCustomerId(r.getString("CUSTOMER_ID"));
				customerListDTO.setCustomerName(r.getString("FULL_NAME"));
				customerListDTO.setCustomerAddress(r.getString("ADDRESS"));
				customerListDTO.setMin_load(r.getDouble("MIN_LOAD"));
				customerListDTO.setMaxLoad(r.getDouble("MAX_LOAD"));
				customerListDTO.setMeter_info(r.getString("meter_info"));
				///for sub conns
				customerListDTO.setSub_ids(r.getString("sub_cust"));
				customerListDTO.setSub_names(r.getString("sub_cust_name"));
				customerListDTO.setSub_burners(r.getString("sub_burner"));
				customerListDTO.setSub_address(r.getString("sub_address"));

        		MixedCustomerList.add(customerListDTO);
				
			}
	
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
		return MixedCustomerList;
	}
	
	

	
	public ServletContext getServlet() {
		return servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}
}
