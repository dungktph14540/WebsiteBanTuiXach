package vn.fs.controller;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import vn.fs.commom.CommomDataService;
import vn.fs.config.PaypalPaymentIntent;
import vn.fs.config.PaypalPaymentMethod;
import vn.fs.dto.ProductDto;
import vn.fs.entities.CartItem;
import vn.fs.entities.Order;
import vn.fs.entities.OrderDetail;
import vn.fs.entities.Product;
import vn.fs.entities.Size;
import vn.fs.entities.User;
import vn.fs.entities.Voucher;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.repository.SizeRepository;
import vn.fs.repository.VoucherRepository;
import vn.fs.service.PaypalService;
import vn.fs.service.ProductService;
import vn.fs.service.ShoppingCartService;
import vn.fs.service.VoucherService;
import vn.fs.util.Utils;


@Controller
public class CartController extends CommomController {

	@Autowired
	HttpSession session;

	@Autowired
	CommomDataService commomDataService;

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	private PaypalService paypalService;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	ProductService productService;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	VoucherService voucherService;
	@Autowired
	SizeRepository sizeRepository;
	public Order orderFinal = new Order();

	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	private Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/shoppingCart_checkout")
	public String shoppingCart(Model model) {

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());
		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "web/shoppingCart_checkout";
	}

	
	@GetMapping(value = "/addToCart/{productId}/{quantity}/{sizename}")
	public String add(@PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity,
			@PathVariable("sizename") String sizename,
			HttpServletRequest request, Model model, RedirectAttributes attributes) {

		Product product = productRepository.findById(productId).orElse(null);
		session = request.getSession();
		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		if (product != null) {
			CartItem item = new CartItem();
			item.setQuantity(item.getQuantity() + quantity);
			item.setProduct(product);
			item.setId(productId);
			item.setSizeName(sizename);
			shoppingCartService.add2(item, product);
		}
		session.setAttribute("cartItems", cartItems);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "redirect:/products";
	}
	//update checkout
	@PostMapping("/update")
	public String update(@RequestParam(value =  "id") Long id , @RequestParam("quantity1") 
	Integer quantity1) {
		try {
			//shoppingCartService.update3(id, quantity,product);
			//shoppingCartService.update(productId, quantity);
			shoppingCartService.update2(id, quantity1);
			//shoppingCartService.update(id, quantity1, item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/products";
	}
	@GetMapping("/updateCheckout/{id}/{quantity}")
	public String updateCheckout(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		try {
			shoppingCartService.update2(id, quantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/checkout";
	}
	//updateGio
	@GetMapping("/updateGio/{id}/{quantity}")
	public String updateGio(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		try {
			shoppingCartService.update2(id, quantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/giohang";
	}
	//DeleteGio
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/removegio/{id}")
	public String removeGio(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		Product product = productRepository.findById(id).orElse(null);

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		session = request.getSession();
		if (product != null) {
			CartItem item = new CartItem();
			BeanUtils.copyProperties(product, item);
			item.setProduct(product);
			item.setId(id);
			cartItems.remove(session);
			shoppingCartService.remove(item);
		}
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		return "redirect:/giohang";
	}
	// delete cartItem
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/remove/{id}")
	public String remove(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		Product product = productRepository.findById(id).orElse(null);

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		session = request.getSession();
		if (product != null) {
			CartItem item = new CartItem();
			BeanUtils.copyProperties(product, item);
			item.setProduct(product);
			item.setId(id);
			cartItems.remove(session);
			shoppingCartService.remove(item);
		}
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		return "redirect:/checkout";
	}
	//delete gio hang
//	@SuppressWarnings("unlikely-arg-type")
//	@GetMapping(value = "/remove/{id}")
//	public String removegiohang(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
//		Product product = productRepository.findById(id).orElse(null);
//
//		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
//		session = request.getSession();
//		if (product != null) {
//			CartItem item = new CartItem();
//			BeanUtils.copyProperties(product, item);
//			item.setProduct(product);
//			item.setId(id);
//			cartItems.remove(session);
//			shoppingCartService.remove(item);
//		}
//		model.addAttribute("totalCartItems", shoppingCartService.getCount());
//		return "redirect:/giohang";
//	}
	// show check out
	@GetMapping(value = "/checkout")
	public String checkOut(Model model, User user) {

		Order order = new Order();
		model.addAttribute("order", order);

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());
		model.addAttribute("NoOfItems", shoppingCartService.getCount());
		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		commomDataService.commonData(model, user);

		return "web/shoppingCart_checkout";
	}
	@GetMapping(value = "/giohang")
	public String giohang(Model model, User user) {

		Order order = new Order();
		model.addAttribute("order", order);

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());
		model.addAttribute("NoOfItems", shoppingCartService.getCount());
		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		commomDataService.commonData(model, user);

		return "web/giohang";
	}

	// submit checkout
	@PostMapping(value = "/checkout")
	@Transactional
	public String checkedOut(Model model, Order order, HttpServletRequest request, User user)
			throws MessagingException {

		String checkOut = request.getParameter("checkOut");

		Collection<CartItem> cartItems = shoppingCartService.getCartItems();

		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		BeanUtils.copyProperties(order, orderFinal);
		if (StringUtils.equals(checkOut, "paypal")) {

			String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
			String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
			try {
				totalPrice = totalPrice / 22;
				Payment payment = paypalService.createPayment(totalPrice, "USD", PaypalPaymentMethod.paypal,
						PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
				for (Links links : payment.getLinks()) {
					if (links.getRel().equals("approval_url")) {
						return "redirect:" + links.getHref();
					}
				}
			} catch (PayPalRESTException e) {
				log.error(e.getMessage());
			}

		}

		session = request.getSession();
		Date date = new Date();
		
		order.setOrderDate(date);
		order.setStatus(0);
		order.getOrderId();
		order.setAmount(totalPrice);
		order.setUser(user);
		order.getLoaiShip();
		orderRepository.save(order);

		for (CartItem cartItem : cartItems) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setOrder(order);
			orderDetail.setProduct(cartItem.getProduct());
			double unitPrice = cartItem.getProduct().getPrice();
			orderDetail.setPrice(unitPrice);
			orderDetailRepository.save(orderDetail);
		}

		// sendMail
		commomDataService.sendSimpleEmail(user.getEmail(), "Ado-Shop Xác Nhận Đơn hàng", "aaaa", cartItems,
				totalPrice, order);

		shoppingCartService.clear();
		session.removeAttribute("cartItems");
		model.addAttribute("orderId", order.getOrderId());

		return "redirect:/checkout_success";
	}

	// paypal
	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("" + "" + "") String paymentId, @RequestParam("PayerID") String payerId,
			HttpServletRequest request, User user, Model model) throws MessagingException {
		Collection<CartItem> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());

		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100) + 30000;
		}
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if (payment.getState().equals("approved")) {

				session = request.getSession();
				Date date = new Date();
				orderFinal.setOrderDate(date);
				orderFinal.setStatus(6);
				orderFinal.getOrderId();
				orderFinal.setUser(user);
				orderFinal.getLoaiShip();
				orderFinal.setAmount(totalPrice);
				orderRepository.save(orderFinal);

				for (CartItem cartItem : cartItems) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setQuantity(cartItem.getQuantity());
					orderDetail.setOrder(orderFinal);
					orderDetail.setProduct(cartItem.getProduct());
					double unitPrice = cartItem.getProduct().getPrice();
					orderDetail.setPrice(unitPrice);
					orderDetailRepository.save(orderDetail);
				}

				// sendMail
				commomDataService.sendSimpleEmail(user.getEmail(), "Ado-Shop Xác Nhận Đơn hàng", "aaaa", cartItems,
						totalPrice, orderFinal);

				shoppingCartService.clear();
				session.removeAttribute("cartItems");
				model.addAttribute("orderId", orderFinal.getOrderId());
				orderFinal = new Order();
				return "redirect:/checkout_paypal_success";
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return "redirect:/";
	}

	// done checkout ship cod
	@GetMapping(value = "/checkout_success")
	public String checkoutSuccess(Model model, User user) {
		commomDataService.commonData(model, user);

		return "web/checkout_success";

	}

	// done checkout paypal
	@GetMapping(value = "/checkout_paypal_success")
	public String paypalSuccess(Model model, User user) {
		commomDataService.commonData(model, user);

		return "web/checkout_paypal_success";

	}
	
}
