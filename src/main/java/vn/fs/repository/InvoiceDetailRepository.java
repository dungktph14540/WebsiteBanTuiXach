package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>{
	@Query(value = "select * from invoice_detail where invoice_id=?", nativeQuery = true)
	List<InvoiceDetail> findByInvoiceDeTailByInvoiceId(Long id);
	@Query(value = "select * from invoice_detail where invoice_detail_id=?", nativeQuery = true)
	List<InvoiceDetail> findByIdDetail(Long id);
	
}
