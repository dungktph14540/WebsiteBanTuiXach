package vn.fs.controller.admin;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
@Service
public class DataService {
	public String htmltopdf (String html) {
    	ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
    	try {
    	PdfWriter pdfWriter= new PdfWriter(byteArrayOutputStream);
    	DefaultFontProvider defaultFontProvider= new DefaultFontProvider();
    	ConverterProperties converterProperties= new ConverterProperties();
    	converterProperties.setFontProvider (defaultFontProvider);
    	HtmlConverter.convertToPdf(html, pdfWriter, converterProperties);
    	FileOutputStream fout= new FileOutputStream("C:/Users/Acer/Downloads/HoaDon/invoice.pdf");
    	byteArrayOutputStream.writeTo(fout);
    	byteArrayOutputStream.close();
    	byteArrayOutputStream.flush();
    	fout.close();
    	return null;
    	}
    		    	
    	catch (Exception e) {
    		e.printStackTrace();
    		return "Lỗi khi chuyển đổi HTML sang PDF";
    	}
    	
    }
}
