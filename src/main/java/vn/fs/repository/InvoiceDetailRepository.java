package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>{
	@Query(value = "SELECT MIN(invoice_detail_id) AS invoice_detail_id,product_id,MIN(invoice_id) AS invoice_id,\r\n"
			+ "SUM(quantity) AS quantity,MIN(price) as price\r\n"
			+ "FROM invoice_detail\r\n"
			+ "where invoice_detail.invoice_id=? GROUP BY product_id;", nativeQuery = true)
	List<InvoiceDetail> findByInvoiceDeTailByInvoiceId(Long id);
	@Query(value = "select * from invoice_detail where invoice_detail_id=?", nativeQuery = true)
	List<InvoiceDetail> findByIdDetail(Long id);
	
}