package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.fs.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
	@Query(value = "select * from invoice where invoice_id = ?;", nativeQuery = true)
	List<Invoice> findByInvoices(Long id);

	
}
