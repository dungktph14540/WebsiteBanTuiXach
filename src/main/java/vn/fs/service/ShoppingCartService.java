package vn.fs.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import vn.fs.entities.CartItem;
import vn.fs.entities.InvoiceCart;
import vn.fs.entities.Product;


@Service
public interface ShoppingCartService {

	int getCount();

	double getAmount();

	void clear();

	Collection<CartItem> getCartItems();
	Collection<InvoiceCart> getInvoiceCarts();
	
	InvoiceCart updateInvoice(Long id, int quantity1);
	
	void removeCartInvoice(InvoiceCart item);


	void remove(CartItem item);

	void add(CartItem item);

	void remove(Product product);

	void add2(CartItem item, Product product);



	CartItem update2(Long id, int quantity1);

	CartItem update3(Long id, int quantity, Product product);


	CartItem update4(Long id, int quantity);

	CartItem update(Long id, int quantity, CartItem item);

	void getId(CartItem item);

	void add3(InvoiceCart item, Product product);

	void clearInvoice();



}
