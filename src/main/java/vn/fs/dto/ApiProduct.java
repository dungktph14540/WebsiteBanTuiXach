package vn.fs.dto;

public class ApiProduct {
    private Long productId;
    private Integer quantity;
    private Long sizeId;
    
	public ApiProduct(Long productId, Integer quantity, Long sizeId) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.sizeId = sizeId;
	}

	public ApiProduct() {
		super();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}
	
    
}
