package vn.fs.controller.admin;
import javax.servlet.http.HttpServletResponse;

import javax.swing.text.StyleConstants.FontConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import vn.fs.entities.Category;
import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceDetail;
import vn.fs.entities.Product;
import vn.fs.repository.ProductRepository;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@Service
public class UserPDFExporter {
	@Autowired
	ProductRepository productRepository;
	 private List<InvoiceDetail> listCate;
	 private static final String PHONE_NUMBER = "0356879244";
	 private static final String ADDRESS = "Số 88 Nam Từ Liêm Hà Nội";
	 private static final String EMAIL = "adoshop@gmail.com";
	 private static final String BANK = "MBBank-Số tài khoản: 0002229992002";
	 private static final String USER = "Nguyễn Trung Hiếu";
	 NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	    public UserPDFExporter(List<InvoiceDetail> listCate) {
	        this.listCate = listCate;
	       
	    }
	    private double calculateTotalAmount() {
	        double totalAmount = 0;

	        for (InvoiceDetail user : listCate) {
	            double productAmount = user.getInvoice().getAmount();
	            totalAmount = productAmount;
	            
	        }

	        return totalAmount;
	    }
	    private String getPurchaseDate() {
	        
	    	for (InvoiceDetail user : listCate) {
	            Date purchaseDate = (Date) user.getInvoice().getInvoiceDate(); 
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        return dateFormat.format(purchaseDate);
	        }
	        
	        
	        return null;
	    }
	    private String nameDh() {
	      

	        for (InvoiceDetail user : listCate) {
	            String nameeee = user.getInvoice().getUsername();
	            return nameeee;
	        }
	       return null;
	        
	    }
	    private String sdtDh() {
		      

	        for (InvoiceDetail user : listCate) {
	            String sdt = user.getInvoice().getPhonenumber();
	            return sdt;
	        }
	       return null;
	        
	    }
	    private int statusDh() {
		      

	        for (InvoiceDetail user : listCate) {
	            int status = user.getInvoice().getStatus();
	            return status;
	        }
	       return (Integer) null;
	        
	    }
	    private void writeTableHeader(PdfPTable table) throws DocumentException, IOException {
	        Font font = FontFactory.getFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setPadding(6);
	        cell.setPhrase(new Phrase("STT", font));
	        table.addCell(cell);

	        cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setPadding(5);
	        cell.setPhrase(new Phrase("Số điện thoại", font));
	        table.addCell(cell);

	        cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setPadding(5);
	        cell.setPhrase(new Phrase("Tên sản phẩm", font));
	        table.addCell(cell);

	        cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setPadding(5);
	        cell.setPhrase(new Phrase("Số lượng", font));
	        table.addCell(cell);

	        cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setPadding(5);
	        cell.setPhrase(new Phrase("Thành tiền", font));
	        table.addCell(cell);
	        
	        
	        
	        
	    }



	    
	    private void writeTableData(PdfPTable table) throws DocumentException, IOException {
	        Font font = FontFactory.getFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
	        

	        int count = 0;
	        for (InvoiceDetail user : listCate) {
	        	
	        	 if (count < 10) {
	        		 table.addCell(new PdfPCell(new Phrase(String.valueOf(count + 1), font)));
	 	            table.addCell(new PdfPCell(new Phrase(user.getInvoice().getPhonenumber(), font)));
	 	            table.addCell(new PdfPCell(new Phrase(user.getProducts().getProductName(), font)));
	 	            table.addCell(new PdfPCell(new Phrase(String.valueOf(user.getQuantity()), font)));
	 	           double totalPrice = (user.getPrice() - (user.getPrice() * user.getProducts().getDiscount() / 100)) * user.getQuantity();
	 	          table.addCell(new PdfPCell(new Phrase(currencyFormat.format(totalPrice), font)));	 	            
	        	        count++;
	        	    } else {
	        	        break; // Thoát khỏi vòng lặp sau khi in 10 phần tử
	        	    }
	           
	        }
	    }

	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        Font font = new Font(bf, 28, Font.NORMAL, BaseColor.GRAY);

	         
	        Paragraph p = new Paragraph("ADO SHOP", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);	  
	        p.setSpacingAfter(10);
	        document.add(p);
	        
	        Font infoFont = new Font(bf, 10, Font.BOLD, BaseColor.BLACK);
	        Paragraph phoneParagraph = new Paragraph("Số điện thoại: " + PHONE_NUMBER, infoFont);
	        phoneParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(phoneParagraph);

	        Paragraph addressParagraph = new Paragraph("Địa chỉ: " + ADDRESS, infoFont);
	        addressParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(addressParagraph);
	        
	        Paragraph emailParagraph = new Paragraph("Email: " + EMAIL, infoFont);
	        emailParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(emailParagraph);
	        
	        Paragraph stkParagraph = new Paragraph("Ngân hàng: " + BANK, infoFont);
	        stkParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(stkParagraph);
	        
	        Paragraph userParagraph = new Paragraph("Chủ tài khoản: " + USER, infoFont);
	        userParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        document.add(userParagraph);
	        
	        Paragraph pd = new Paragraph("HÓA ĐƠN BÁN HÀNG", font);
	        pd.setAlignment(Paragraph.ALIGN_CENTER);	
	        pd.setSpacingAfter(10);
	        document.add(pd);
	        
	        String purchaseDate = getPurchaseDate();
	        Paragraph ndhParagraph = new Paragraph("Ngày mua hàng: " + purchaseDate , infoFont);
	        ndhParagraph.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(ndhParagraph);
	        
	        String nameeee = nameDh();
	        Paragraph nameParagraph = new Paragraph("Khách hàng: " + nameeee , infoFont);
	        nameParagraph.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(nameParagraph);
	        
	        String sdt = sdtDh();
	        Paragraph sdtParagraph = new Paragraph("Số điện thoại: " + sdt , infoFont);
	        sdtParagraph.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(sdtParagraph);
	        
	        Font fontt = new Font(bf, 15, Font.BOLD, BaseColor.BLACK);
	        Paragraph pdd = new Paragraph("DANH SÁCH SẢN PHẨM ĐÃ MUA", fontt);
	        pdd.setAlignment(Paragraph.ALIGN_CENTER);	         
	        document.add(pdd);
	        
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[]{1.5f, 1.5f, 3.5f, 1.5f, 2.5f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	        
	        double totalAmount = calculateTotalAmount();
	        Paragraph totalAmountParagraph = new Paragraph("Tổng tiền: " + currencyFormat.format(totalAmount), infoFont);
	        totalAmountParagraph.setAlignment(Paragraph.ALIGN_LEFT);
	        totalAmountParagraph.setSpacingBefore(10);
	        document.add(totalAmountParagraph);
	        
	        int status = statusDh();
	        Paragraph stautsAmountParagraph = new Paragraph("Trạng thái hóa đơn: Hoàn thành", infoFont);
	        stautsAmountParagraph.setAlignment(Paragraph.ALIGN_LEFT);
	        document.add(stautsAmountParagraph);
	        
	        Font fonttt = new Font(bf, 10, Font.ITALIC, BaseColor.BLACK);
	        Paragraph pddd = new Paragraph("---Cảm ơn quý khách---", fonttt);
	        pddd.setAlignment(Paragraph.ALIGN_CENTER);	
	        pddd.setSpacingBefore(30);
	        document.add(pddd);
	        
	        document.close();
	        
	        
	        
	    }
	    
}

