package vn.fs.dto;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.fs.entities.Category;
import vn.fs.entities.Size;

@Data

public class ProductDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private Long categoryId;
	private Long sizeId;
	private String productName;
	private String chatLuong;
	private String ghiChu;
	private int quantity;
	private int maSP;
	private double price;
	private int discount;
	private String productImage;
	private String description;
	private Date enteredDate;
	private Boolean status;
	public boolean favorite;
	private Category category;
	private Size size;
	
	
	public ProductDto() {
		super();
	}
	public ProductDto(Long productId, Long categoryId, String productName, int quantity, double price, int discount,
			String productImage, String description, Date enteredDate, Boolean status, boolean favorite,
			Category category) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.productImage = productImage;
		this.description = description;
		this.enteredDate = enteredDate;
		this.status = status;
		this.favorite = favorite;
		this.category = category;
	}
	
	
	public ProductDto(Long productId, Long categoryId, String productName, int quantity, int maSP, double price,
			int discount, String productImage, String description, Date enteredDate, Boolean status, boolean favorite,
			Category category) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.quantity = quantity;
		this.maSP = maSP;
		this.price = price;
		this.discount = discount;
		this.productImage = productImage;
		this.description = description;
		this.enteredDate = enteredDate;
		this.status = status;
		this.favorite = favorite;
		this.category = category;
	}
	
	public ProductDto(Long productId, Long categoryId, Long sizeId, String productName, int quantity, int maSP,
			double price, int discount, String productImage, String description, Date enteredDate, Boolean status,
			boolean favorite, Category category, Size size) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.sizeId = sizeId;
		this.productName = productName;
		this.quantity = quantity;
		this.maSP = maSP;
		this.price = price;
		this.discount = discount;
		this.productImage = productImage;
		this.description = description;
		this.enteredDate = enteredDate;
		this.status = status;
		this.favorite = favorite;
		this.category = category;
		this.size = size;
	}
	
	
	public ProductDto(Long productId, Long categoryId, Long sizeId, String productName, String chatLuong, String ghiChu,
			int quantity, int maSP, double price, int discount, String productImage, String description,
			Date enteredDate, Boolean status, boolean favorite, Category category, Size size) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.sizeId = sizeId;
		this.productName = productName;
		this.chatLuong = chatLuong;
		this.ghiChu = ghiChu;
		this.quantity = quantity;
		this.maSP = maSP;
		this.price = price;
		this.discount = discount;
		this.productImage = productImage;
		this.description = description;
		this.enteredDate = enteredDate;
		this.status = status;
		this.favorite = favorite;
		this.category = category;
		this.size = size;
	}
	public String getChatLuong() {
		return chatLuong;
	}
	public void setChatLuong(String chatLuong) {
		this.chatLuong = chatLuong;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public Long getSizeId() {
		return sizeId;
	}
	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public int getMaSP() {
		return maSP;
	}
	public void setMaSP(int maSP) {
		this.maSP = maSP;
	}
	
}
