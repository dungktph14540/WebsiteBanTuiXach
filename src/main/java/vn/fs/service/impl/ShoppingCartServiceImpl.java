package vn.fs.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.fs.entities.CartItem;
import vn.fs.entities.InvoiceCart;
import vn.fs.entities.Product;
import vn.fs.service.ShoppingCartService;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private Map<Long, CartItem> map = new HashMap<Long, CartItem>(); // <Long, CartItem>
	
	private Map<Long, InvoiceCart> map2 = new HashMap<Long, InvoiceCart>(); // <Long, CartItem>


	@Override
	public void add(CartItem item) {
		CartItem existedItem = map.get(item.getId());
		
		if (existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
			
			existedItem.setTotalPrice(item.getTotalPrice() + existedItem.getUnitPrice() * existedItem.getQuantity());
		} else {
			map.put(item.getId(), item);
		}
	}
	
	@Override
	public void add2(CartItem item, Product product) {
		CartItem existedItem = map.get(item.getId());
		if (existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
			if (existedItem.getQuantity()> product.getQuantity()) {
				existedItem.setQuantity(product.getQuantity());
			}
			existedItem.setTotalPrice(item.getTotalPrice() + existedItem.getUnitPrice() * existedItem.getQuantity());
		} else {
			map.put(item.getId(), item);
		}
		
	}
	@Override
	public void add3(InvoiceCart item, Product product) {
		InvoiceCart existedItem = map2.get(item.getId());
		if (existedItem != null) {
			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
			if (existedItem.getQuantity()> product.getQuantity()) {
				existedItem.setQuantity(product.getQuantity());
			}
			existedItem.setTotalPrice(item.getTotalPrice() + existedItem.getUnitPrice() * existedItem.getQuantity());
		} else {
			map2.put(item.getId(), item);
		}
		
	}
	
	@Override
	public CartItem update(Long id, int quantity,CartItem item) {
	
		CartItem cartItem = map.get(item.getId());
		
		cartItem.setQuantity(quantity);
		
		return cartItem;
	}
	@Override
	public CartItem update4(Long id, int quantity) {
		CartItem cartItem = map.get(id);
		
		cartItem.setQuantity(quantity);
		
		return cartItem;
	}
	@Override
	public CartItem update2(Long id, int quantity1) {
		Collection<CartItem> cartItems = map.values();
		for (CartItem cartItemss : cartItems) {
			cartItemss = map.get(id);
			cartItemss.setQuantity(quantity1);
		}
		
		
		return (CartItem) cartItems;
	}
	@Override
	public CartItem update3(Long id, int quantity, Product product) {
		CartItem cartItem = map.get(id);
		
			cartItem.setQuantity(quantity);
			if (cartItem.getQuantity()>product.getQuantity()) {
				cartItem.setQuantity(product.getQuantity());
			}
		return cartItem;
	}
	@Override
	public void remove(CartItem item) {

		map.remove(item.getId());

	}
	@Override
	public void getId(CartItem item) {
		map.get(item.getId());
	}

	@Override
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public void clearInvoice() {
		map2.clear();
	}
	
	@Override
	public double getAmount() {
		return map.values().stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();
	}

	@Override
	public int getCount() {
		if (map.isEmpty()) {
			return 0;
		}

		return map.values().size();
	}

	@Override
	public void remove(Product product) {

	}

	@Override
	public Collection<InvoiceCart> getInvoiceCarts() {
		return map2.values();
	}

	@Override
	public InvoiceCart updateInvoice(Long id, int quantity1) {
		Collection<InvoiceCart> cartItems = map2.values();
		for (InvoiceCart cartItemss : cartItems) {
			cartItemss = map2.get(id);
			cartItemss.setQuantity(quantity1);
		}
		
		
		return (InvoiceCart) cartItems;
	}

	@Override
	public void removeCartInvoice(InvoiceCart item) {
		map2.remove(item.getId());
		
	}
}
