package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.DepositDTO;
import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;
import org.jgtdsl.models.DepositService;
import org.jgtdsl.utils.connection.ConnectionManager;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class BGforIndividual extends BaseAction{
	private static final long serialVersionUID = 1L;
	public ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();

	private String customer_id;
	ArrayList<DepositDTO> expireList = new ArrayList<DepositDTO>();

	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	static DecimalFormat consumption_format = new DecimalFormat(
			"##########0.000");
	UserDTO loggedInUser = (UserDTO) ServletActionContext.getRequest()
			.getSession().getAttribute("user");

	public String execute() throws Exception {

		String fileName = "BG_REPORT_"+this.customer_id+".pdf";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		document.setMargins(20, 20, 50, 80);
		PdfPCell pcell = null;

		ReportFormat eEvent = new ReportFormat(getServletContext());

		PdfWriter.getInstance(document, baos).setPageEvent(eEvent);

		document.open();
		
		try {
/////////////////header starts/////////////
			
	PdfPTable headerTable = new PdfPTable(3);
	   
	
	headerTable.setWidths(new float[] {
		5,190,5
	});
	
	headerTable.setWidths(new float[] {
			5,190,5
		});

		
		pcell= new PdfPCell(new Paragraph(""));
		pcell.setBorder(0);
		headerTable.addCell(pcell);
		
		
		String realPath = servlet.getRealPath("/resources/images/logo/JG.png"); 	// image path
		Image img = Image.getInstance(realPath);
					
        	//img.scaleToFit(10f, 200f);
        	//img.scalePercent(200f);
        img.scaleAbsolute(28f, 31f);
        img.setAbsolutePosition(123f, 760f);		
        	//img.setAbsolutePosition(290f, 540f);		// rotate
        
        document.add(img);
		
		
		PdfPTable mTable=new PdfPTable(1);
		mTable.setWidthPercentage(90);
		mTable.setWidths(new float[]{100});
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
		///////////header ends/////////////

		DepositService depositeService = new DepositService();
		expireList = depositeService.getBGExpireWithinOneYear(this.customer_id);

		int expireListSize = expireList.size();

		document.setMargins(20, 20, 48, 72);
		PdfPTable headLinetable = null;
		headLinetable = new PdfPTable(1);
		headLinetable.setWidthPercentage(100);
		headLinetable.setWidths(new float[] { 100 });

		
		pcell = new PdfPCell(new Paragraph(" ", ReportUtil.f8B));
		pcell.setMinimumHeight(18f);
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setBorder(0);
		headLinetable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph("COLLECTION MONTH : "));
		pcell.setMinimumHeight(18f);
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setBorder(0);
		pcell.setPaddingBottom(25);
		headLinetable.addCell(pcell);


		document.add(headLinetable);


		// document.add(new Paragraph("\n"));

		PdfPTable pdfPTable = new PdfPTable(9);
		pdfPTable.setWidthPercentage(100);
		pdfPTable.setWidths(new float[] { 5, 10, 15, 15, 10, 10, 15, 10, 10 });
		pdfPTable.setHeaderRows(1);

		pcell = new PdfPCell(new Paragraph("SL No", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Deposit ID", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Customer ID", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Customer Name", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Bank", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Branch", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Valid Till", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Total Deposit", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		pcell = new PdfPCell(new Paragraph("Expire Within (days)",
				font3));
		pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(1);
		pcell.setPadding(5);
		pdfPTable.addCell(pcell);

		// document.add(pdfPTable);

		// double totalSecurity=0.0;
		// double totalSurcharge=0.0;
		// double totalFees=0.0;
		// double totalSecurityDeposit=0.0;
		// double total=0.0;
		int total_deposit=0;
		
		for (int j = 0; j < expireListSize; j++) {

			pcell = new PdfPCell(new Paragraph(String.valueOf(j + 1),
					ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j)
					.getDeposit_id(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j)
					.getCustomer_id(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j)
					.getCustomer_name(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(
					expireList.get(j).getBank_name(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j)
					.getBranch_name(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j).getValid_to(),
					ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(expireList.get(j).getTotal_deposit(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
			pcell.setColspan(1);
			pcell.setPadding(5);
			pdfPTable.addCell(pcell);

			pcell = new PdfPCell(new Paragraph(
					expireList.get(j).getExpire_in(), ReportUtil.f9));
			pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			pcell.setPadding(5);
			pcell.setColspan(1);
			pdfPTable.addCell(pcell);

			total_deposit+= Integer.parseInt(expireList.get(j).getTotal_deposit());
		}
		
		pcell = new PdfPCell(new Paragraph("Total Deposit:", font3));
		pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(7);
		pcell.setPadding(5);
		pcell.setBorder(0);
		pdfPTable.addCell(pcell);
		
		pcell = new PdfPCell(new Paragraph(String.valueOf(total_deposit), font3));
		pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		pcell.setColspan(2);
		pcell.setPadding(5);
		pcell.setBorder(0);
		pdfPTable.addCell(pcell);

		document.add(pdfPTable);		
		//document.add(dataTable);
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

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
}
