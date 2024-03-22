package vn.fs.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product1")
public class Product1 implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId1;
	private String productName1;
	private String productImage1;
	public Long getProductId1() {
		return productId1;
	}
	public void setProductId1(Long productId1) {
		this.productId1 = productId1;
	}
	public String getProductName1() {
		return productName1;
	}
	public void setProductName1(String productName1) {
		this.productName1 = productName1;
	}
	public String getProductImage1() {
		return productImage1;
	}
	public void setProductImage1(String productImage1) {
		this.productImage1 = productImage1;
	}
	
}
