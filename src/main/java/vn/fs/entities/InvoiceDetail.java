package vn.fs.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long invoiceDetailId;

	private int quantity;

	private double price;

	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product products;

	


	public InvoiceDetail() {
		super();
	}

	public InvoiceDetail(Long invoiceDetailId, int quantity, double price, Invoice invoice, Product products) {
		super();
		this.invoiceDetailId = invoiceDetailId;
		this.quantity = quantity;
		this.price = price;
		this.invoice = invoice;
		this.products = products;
	}

	public Long getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(Long invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
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

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getProducts() {
		return products;
	}

	public void setProducts(Product products) {
		this.products = products;
	}
}