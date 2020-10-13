package org.jgtdsl.reports;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jgtdsl.actions.BaseAction;
import org.jgtdsl.dto.CollectionDTO;

import org.jgtdsl.dto.UserDTO;
import org.jgtdsl.enums.Area;


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

public class AdvancedCollectionReport extends BaseAction {
	private static final long serialVersionUID = 1L;
	public  ServletContext servlet;
	Connection conn = ConnectionManager.getConnection();
	CollectionDTO collectiondto=null;
	ArrayList<CollectionDTO> AdvCollectionInfo=new ArrayList<CollectionDTO>();
	UserDTO loggedInUser=(UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
	
	static Font fonth = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	static Font font1 = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	static Font font1nb = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);
	static Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	static Font font2 = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
	static DecimalFormat taka_format = new DecimalFormat("#,##,##,##,##,##0.00");
	
	
		private   String area;
		public String execute() throws Exception
		{
			String fileName = "AdvancedCollection.pdf";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.LEGAL.rotate());
			
			try{
				


				ReportFormat Event = new ReportFormat(getServletContext());
				PdfWriter writer = PdfWriter.getInstance(document, baos);
				writer.setPageEvent(Event);
				PdfPCell pcell = null;
				
				document.open();
				PdfPTable headerTable = new PdfPTable(3);
			    Rectangle page = document.getPageSize();
			 
			    headerTable.setTotalWidth(page.getWidth());
				float a = ((page.getWidth() * 15) / 100) / 2;
				float b = ((page.getWidth() * 40) / 100) / 2;

				headerTable.setWidths(new float[] { a, b, a });
		
				pcell= new PdfPCell(new Paragraph(""));
				pcell.setBorder(Rectangle.NO_BORDER);
				headerTable.addCell(pcell);
				
	// for logo			
		
/*				String realPath = servlet.getRealPath("/resources/images/logo/JG.png");  // image path
				   Image img = Image.getInstance(realPath);
				      
				             //img.scaleToFit(10f, 200f);
				             //img.scalePercent(200f);
				            img.scaleAbsolute(32f, 35f);
				            //img.setAbsolutePosition(145f, 780f);  
				            img.setAbsolutePosition(320f, 535f);  // rotate
				            
				         document.add(img);*/
				
				
				
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
				
				//error in chunk2			
				
				Chunk chunk1 = new Chunk("Regional Office: ",font2);
				Chunk chunk2 = new Chunk(String.valueOf(Area.values()[Integer.valueOf(area)-1]),font3);
				//Chunk chunk2 = new Chunk(String.valueOf(Area.values()[1]),font3);
				
				Paragraph p = new Paragraph(); 
				p.add(chunk1);
				p.add(chunk2);
				pcell=new PdfPCell(p);
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				mTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph("Advanced Collection Report  "));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				pcell.setPaddingTop(10);
				mTable.addCell(pcell);
				
				pcell=new PdfPCell(new Paragraph(" "));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(Rectangle.NO_BORDER);
				pcell.setPaddingTop(10);
				mTable.addCell(pcell);
				

							
				pcell=new PdfPCell(mTable);
				pcell.setBorder(Rectangle.NO_BORDER);
				headerTable.addCell(pcell);			
				
				pcell = new PdfPCell(new Paragraph(""));
				pcell.setBorder(Rectangle.NO_BORDER);
				headerTable.addCell(pcell);			
				
				document.add(headerTable);
				

				
				
				PdfPTable dataTable = new PdfPTable(6);
				
				dataTable.setWidthPercentage(90);
				dataTable.setWidths(new float[] {10,10,10,20,10,10});
				
				pcell= new PdfPCell(new Paragraph(" ", font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setBorder(0);
				pcell.setPadding(5);
				pcell.setColspan(9);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);	
				

				pcell = new PdfPCell(new Paragraph("Sr. No.",font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				dataTable.addCell(pcell);
				

				
				pcell = new PdfPCell(new Paragraph("Customer Id",font3));		
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				pcell.setPadding(3);
				dataTable.addCell(pcell);
				
				
				pcell= new PdfPCell(new Paragraph("Transaction Date",font3));				
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				pcell.setPadding(3);
				dataTable.addCell(pcell);
				
				
				pcell= new PdfPCell(new Paragraph("Bank Branch",font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(3);				
				dataTable.addCell(pcell);	
				
				pcell= new PdfPCell(new Paragraph("Advanced Amount",font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(3);				
				dataTable.addCell(pcell);	
				
				pcell= new PdfPCell(new Paragraph("Advanced Surcharge",font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(3);				
				dataTable.addCell(pcell);	
				

				AdvCollectionInfo = getCollection();
				int i=1;
				int total_advamount=0;
				int total_advsurcharge=0;
				for(CollectionDTO x: AdvCollectionInfo){
					
					pcell= new PdfPCell(new Paragraph(String.valueOf(i), font2));
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
					pcell.setPadding(5);			
					dataTable.addCell(pcell);

					
					pcell=new PdfPCell(new Paragraph(x.getCustomer_id(),font2));		
					pcell.setPadding(3);					
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					dataTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getTransDate() ,font2));		
					pcell.setPadding(3);					
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					dataTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getBranch_name(),font2));		
					pcell.setPadding(3);					
					pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					dataTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getAdvancedAmount(),font2));		
					pcell.setPadding(3);					
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					dataTable.addCell(pcell);
					
					pcell=new PdfPCell(new Paragraph(x.getSurcharge(),font2));		
					pcell.setPadding(3);					
					pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					dataTable.addCell(pcell);
					if(x.getAdvancedAmount()!=null){
						
						total_advamount+= Integer.parseInt(x.getAdvancedAmount());
					}else{
						
					}
					if(x.getSurcharge()!=null){
						
						total_advsurcharge+= Integer.parseInt(x.getSurcharge());
					}
					

					i++;
					
					
					
				}
				
				pcell= new PdfPCell(new Paragraph("Total: ", font3));
				pcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);
				pcell.setColspan(4);
				dataTable.addCell(pcell);
				

				pcell= new PdfPCell(new Paragraph(taka_format.format(total_advamount), font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				

				
				
				
				pcell= new PdfPCell(new Paragraph(taka_format.format(total_advsurcharge), font3));
				pcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				pcell.setVerticalAlignment(Element.ALIGN_MIDDLE);			
				pcell.setPadding(5);			
				dataTable.addCell(pcell);
				
				
				
				dataTable.setHeaderRows(1);
				document.add(dataTable);
				
	
				document.close();
				document.close();
				ReportUtil rptUtil = new ReportUtil();
				rptUtil.downloadPdf(baos, getResponse(), fileName);
				document = null;
	
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return null;
		}
		
		
		
		private ArrayList<CollectionDTO> getCollection(){
			PreparedStatement stmt =null;
			ResultSet r =null;
			
			ArrayList<CollectionDTO> collectionrList= new ArrayList<CollectionDTO>();
			try{
				String sql= " SELECT bca.*, getbankbranch (BRANCH_ID) bank_branch,to_char(bca.TRANS_DATE) transDate "
						+ "    FROM BILL_COLL_ADVANCED bca "
						+ "   WHERE     status = 1 "
						+ "         AND SUBSTR (customer_id, 1, 2) = '"+ loggedInUser.getArea_id()+"' "
						+ "         AND ADVANCED_AMOUNT > 0  and bca.TRANS_DATE is not null "
						+ " ORDER BY TRANS_DATE DESC ";
	
				stmt=conn.prepareStatement(sql);
				r=stmt.executeQuery();
				while(r.next()){
					collectiondto=new CollectionDTO();
					collectiondto.setCustomer_id(r.getString("CUSTOMER_ID"));
					collectiondto.setTransDate(r.getString("TRANSDATE"));
					collectiondto.setBranch_name(r.getString("BANK_BRANCH"));
					collectiondto.setAdvancedAmount(r.getString("ADVANCED_AMOUNT"));
					collectiondto.setSurcharge(r.getString("ADVANCED_SURCHARGE"));
					collectionrList.add(collectiondto);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return collectionrList;
		}
		
		
		

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}
}
