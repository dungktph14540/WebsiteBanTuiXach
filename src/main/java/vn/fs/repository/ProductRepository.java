package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.fs.dto.ApiProduct;
import vn.fs.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// List product by category
	@Query(value = "SELECT * FROM products WHERE category_id = ?", nativeQuery = true)
	public List<Product> listProductByCategory(Long categoryId);

	@Query(value = "SELECT * FROM products WHERE size_id = ?", nativeQuery = true)
	public List<Product> listProductBySize(Long idSize);

	@Query(value = "SELECT * FROM products WHERE size_id = ?2 and masp = ?1", nativeQuery = true)
	public Product ProductBySize2(int masp, Long idSize);

	@Query(value = "SELECT * FROM products WHERE masp = ?", nativeQuery = true)
	public List<Product> listProductByMaSP(int masp);

	@Query(value = "SELECT * FROM products WHERE masp = ? and size_real = ?", nativeQuery = true)
	public List<Product> listProductByMaSP2(int masp, String sizeReal);

	// Top 10 product by category
	@Query(value = "SELECT * FROM products AS b WHERE b.category_id = ?;", nativeQuery = true)
	List<Product> listProductByCategory10(Long categoryId);

	// List product new
	@Query(value = "SELECT * FROM products ORDER BY entered_date DESC limit 20;", nativeQuery = true)
	public List<Product> listProductNew20();

	// Search Product
	@Query(value = "SELECT * FROM products WHERE product_name LIKE %?1%", nativeQuery = true)
	public List<Product> searchProduct(String productName);

	// count quantity by product
	@Query(value = "SELECT c.category_id, c.category_name, COUNT(*) AS SoLuong " + "FROM products p "
			+ "JOIN categories c ON p.category_id = c.category_id "
			+ "GROUP BY c.category_id, c.category_name", nativeQuery = true)
	List<Object[]> listCategoryByProductName();
	
	// count quantity by product
		@Query(value = "SELECT h.idhang, h.tenhang, COUNT(*) AS SoLuong " + "FROM products p "
				+ "JOIN hang h ON p.idhang = h.idhang "
				+ "GROUP BY h.idhang, h.tenhang", nativeQuery = true)
		List<Object[]> listHangByProductName();

	// Top 20 product best sale
	@Query(value = "SELECT p.product_id,\r\n" + "COUNT(*) AS SoLuong\r\n" + "FROM order_details p\r\n"
			+ "JOIN products c ON p.product_id = c.product_id\r\n" + "GROUP BY p.product_id\r\n"
			+ "ORDER by SoLuong DESC limit 20;", nativeQuery = true)
	public List<Object[]> bestSaleProduct20();

	@Query(value = "select * from products o where product_id in :ids", nativeQuery = true)
	List<Product> findByInventoryIds(@Param("ids") List<Integer> listProductId);

	@Query(value = "SELECT * FROM products p JOIN hang h ON p.idhang = h.idhang JOIN categories c ON p.category_id = c.category_id WHERE " +
	        "(:cateId IS NULL OR c.category_id = :cateId) " +
	        "AND (:idhang IS NULL OR h.idhang = :idhang) " +
	        "AND (:price IS NULL OR p.price <= :price)", nativeQuery = true)
	List<Product> productByHCP(@Param("cateId") Long cateId, @Param("idhang") Long idhang, @Param("price") Double price);

	@Query(value = "SELECT * FROM products WHERE idhang = ?", nativeQuery = true)
	public List<Product> listProductByHang(Long idhang);
	
	@Query(value = "SELECT * FROM products p WHERE LOWER(p.product_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(p.product_id) LIKE LOWER(CONCAT('%', :searchTerm, '%'))", nativeQuery = true)
	List<Product> findbyProWithIdOrName(@Param("searchTerm") String searchTerm);
	
	@Query(value = "select * from products p where product_id = ?",nativeQuery = true)
	ApiProduct findByIdProduct(Long id);
}
