package vn.fs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.fs.entities.OrderDetail;
import vn.fs.entities.User;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Query(value = "select * from order_details where order_id = ?;", nativeQuery = true)
	List<OrderDetail> findByOrderId(Long id);
	@Query(value = "select * from user where user_id = ?1", nativeQuery = true)
	List<User> findByUserId(Long id);
	// Statistics by product sold
    @Query(value = "SELECT p.product_name,\n"
    		+ "    SUM(o.quantity+iv.quantity) as quantity,\n"
    		+ "    Sum((o.quantity * o.price)+(iv.quantity*iv.price)) as totalPrice \n"
    		+ "    from products p inner join order_details o on o.product_id = p.product_id \n"
    		+ "    inner join invoice_detail iv on iv.product_id = p.product_id \n"
    		+ "    GROUP BY p.product_name;", nativeQuery = true)
    public List<Object[]> repo();
    
    // Statistics by category sold
    @Query(value ="SELECT\n"
    		+ "    c.category_name,\n"
    		+ "    SUM(o.quantity + COALESCE(iv.quantity, 0)) AS quantity,\n"
    		+ "    SUM((o.quantity * o.price) + COALESCE((iv.quantity * iv.price), 0)) AS totalPrice\n"
    		+ "FROM\n"
    		+ "    products p\n"
    		+ "    INNER JOIN order_details o ON o.product_id = p.product_id\n"
    		+ "    LEFT JOIN invoice_detail iv ON iv.product_id = p.product_id\n"
    		+ "    JOIN categories c ON p.category_id = c.category_id\n"
    		+ "GROUP BY\n"
    		+ "    c.category_name;", nativeQuery = true)
    public List<Object[]> repoWhereCategory();
    
    // Statistics of products sold by year
    @Query(value = "Select YEAR(od.order_date) ,\r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.price) as sum,\r\n"
    		+ "AVG(o.price) as avg,\r\n"
    		+ "Min(o.price) as min,\r\n"
    		+ "max(o.price) as max \r\n"
    		+ "FROM order_details o\r\n"
    		+ "INNER JOIN orders od ON o.order_id = od.order_id\r\n"
    		+ "GROUP BY YEAR(od.order_date);", nativeQuery = true)
    public List<Object[]> repoOnWhereYear();
    
    // Statistics of products sold by year
    @Query(value = "Select YEAR(i.invoice_date) as yearPrice,\n"
    		+ "SUM(id.quantity) as quantity ,\n"
    		+ "SUM(id.quantity * id.price) as sum\n"
    		+ "FROM invoice_detail id\n"
    		+ "INNER JOIN invoice i  ON id.invoice_id = i.invoice_id\n"
    		+ "GROUP BY yearPrice;", nativeQuery = true)
    public List<Object[]> repoOffWhereYear();
    
    // Statistics of products sold by month
    @Query(value = "Select month(od.order_date) ,\r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.price) as sum,\r\n"
    		+ "AVG(o.price) as avg,\r\n"
    		+ "Min(o.price) as min,\r\n"
    		+ "max(o.price) as max\r\n"
    		+ "FROM order_details o\r\n"
    		+ "INNER JOIN orders od ON o.order_id = od.order_id\r\n"
    		+ "GROUP BY month(od.order_date);", nativeQuery = true)
    public List<Object[]> repoWhereMonthOn();
    
    @Query(value = "Select month(i.invoice_date) as monthOff,\n"
    		+ "SUM(id.quantity) as quantity ,\n"
    		+ "SUM(id.quantity * id.price) as sum\n"
    		+ "FROM invoice_detail id\n"
    		+ "INNER JOIN invoice i  ON id.invoice_id = i.invoice_id\n"
    		+ "GROUP BY monthOff;", nativeQuery = true)
    public List<Object[]> repoWhereMonthOff();
    
    
    
    // Statistics of products sold by quarter
    @Query(value = "Select QUARTER(od.order_date),\r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.price) as sum,\r\n"
    		+ "AVG(o.price) as avg,\r\n"
    		+ "Min(o.price) as min,\r\n"
    		+ "max(o.price) as max\r\n"
    		+ "FROM order_details o\r\n"
    		+ "INNER JOIN orders od ON o.order_id = od.order_id\r\n"
    		+ "GROUP By QUARTER(od.order_date);", nativeQuery = true)
    public List<Object[]> repoWhereQUARTEROn();
    
    // Statistics of products sold by quarter
    @Query(value = "Select QUARTER(i.invoice_date) as monthOff,\n"
    		+ "SUM(id.quantity) as quantity ,\n"
    		+ "SUM(id.quantity * id.price) as sum\n"
    		+ "FROM invoice_detail id\n"
    		+ "INNER JOIN invoice i  ON id.invoice_id = i.invoice_id\n"
    		+ "GROUP BY monthOff;", nativeQuery = true)
    public List<Object[]> repoWhereQUARTEROff();
    
    // Statistics by user
    @Query(value = "SELECT c.user_id,c.name,\r\n"
    		+ "SUM(o.quantity) as quantity,\r\n"
    		+ "SUM(o.quantity * o.price) as sum,\r\n"
    		+ "AVG(o.price) as avg,\r\n"
    		+ "Min(o.price) as min,\r\n"
    		+ "max(o.price) as max\r\n"
    		+ "FROM order_details o\r\n"
    		+ "INNER JOIN orders p ON o.order_id = p.order_id\r\n"
    		+ "INNER JOIN user c ON p.user_id = c.user_id\r\n"
    		+ "GROUP BY c.user_id, c.name;", nativeQuery = true)
    public List<Object[]> reportCustommer();
    @Query(value = "SELECT od.*  FROM order_details od\r\n"
    		+ "INNER JOIN products p ON p.product_id = od.product_id \r\n"
    		+ "INNER JOIN orders o ON od.order_id = o.order_id\r\n"
    		+ "INNER JOIN user u ON o.user_id = u.user_id\r\n"
    		+ "WHERE o.order_id = ?;",nativeQuery = true)
    public List<OrderDetail> findByOrderDetailByOrderId(Long orderId);
}
