package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.actions.ipg.IpgController;
import org.jgtdsl.dto.CodelessDTO;
import org.jgtdsl.dto.CustomerDTO;
import org.jgtdsl.dto.FreedomFighterDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.enums.Month;
import org.jgtdsl.models.CustomerService;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class IPGreceipt extends BaseAction {

	private static final long serialVersionUID = 1L;
	public ServletContext servlet;
	
	
	
	
	
	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat("##########0.000");
	
	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");

	public String execute() throws Exception {

		String fileName = "ReceiptForIPG.pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		//document.setMargins(5,5,60,72);
		
		
		try {

			ReportFormatIPG Event = new ReportFormatIPG(getServletContext());
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setPageEvent(Event);
			PdfPCell pcell = null;
			
			document.open();
			PdfPTable headerTable = new PdfPTable(3);
		    Rectangle page = document.getPageSize();
		 
		    headerTable.setTotalWidth(page.getWidth());
			/*
		    float a=((page.getWidth()*15)/100)/2;
			float b=((page.getWidth()*40)/100)/2;
				
			headerTable.setWidths(new float[] {
				a,b,a
			});
			*/
		    headerTable.setWidths(new float[] {1,98,1});
				
			pcell= new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);
			
// for logo			
	
			String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
			   Image img = Image.getInstance(realPath);
			      
			             //img.scaleToFit(10f, 200f);
			             //img.scalePercent(200f);
			            img.scaleAbsolute(32f, 35f);
			            img.setAbsolutePosition(110f, 780f);  
			            //img.setAbsolutePosition(320f, 535f);  // rotate
			            
			         document.add(img);
			
			
			
			PdfPTable mTable=new PdfPTable(1);
			//mTable.setWidths(new float[]{b});
			mTable.setWidthPercentage(100);
			pcell=new PdfPCell(new Paragraph("JALALABAD GAS T & D SYSTEM LIMITED",fonth));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);	
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("(A Company of PetroBangla)", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Payment Receipt for JGTDSL Bill Collection using IPG", font3));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setBorder(Rectangle.NO_BORDER);
			mTable.addCell(pcell);
			
			//error in chunk2			
			
			
			pcell=new PdfPCell(mTable);
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);			
			
			pcell = new PdfPCell(new Paragraph(""));
			pcell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(pcell);			
			
			document.add(headerTable);
			
			
			IpgController ipgController= new IpgController();
			
			PdfPTable dataTable=new PdfPTable(2);
			dataTable.setWidths(new float[] {5, 15 });
			dataTable.setWidthPercentage(90);
			
			pcell=new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setColspan(2);
			pcell.setPadding(10);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Customer ID:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			CustomerService cService= new CustomerService();
			CustomerDTO custDto= new CustomerDTO();
			custDto= cService.getCustomerInfo(ipgController.getCustomerId());
			
			pcell=new PdfPCell(new Paragraph("Customer Name:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(custDto.getCustomer_name(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Transaction ID:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setPadding(5);
			pcell.setBorder(Rectangle.NO_BORDER);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Message:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Card No:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Paid Amount:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph("Transaction Date:", ReportUtil.f8));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			pcell=new PdfPCell(new Paragraph(ipgController.getCustomerId(), ReportUtil.f8B));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setBorder(Rectangle.NO_BORDER);
			pcell.setPadding(5);
			dataTable.addCell(pcell);
			
			
			document.add(dataTable);
			document.close();
			ReportUtil rptUtil = new ReportUtil();
			rptUtil.downloadPdf(baos, getResponse(),fileName);
			document=null;
		

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

	public ServletContext getServlet() {
		return servlet;
	}

	public void setServletContext(ServletContext servlet) {
		this.servlet = servlet;
	}

}
