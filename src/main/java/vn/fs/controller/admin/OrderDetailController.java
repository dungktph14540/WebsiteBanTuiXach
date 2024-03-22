package vn.fs.controller.admin;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.CartItem;
import vn.fs.entities.Order;
import vn.fs.entities.OrderDetail;
import vn.fs.entities.Product;
import vn.fs.entities.User;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.OrderDetailService;
import vn.fs.service.SendMailService;
import vn.fs.service.ShoppingCartService;
@Controller
@RequestMapping("/admin")
public class OrderDetailController {
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	CommomDataService commomDataService;
	@Autowired
	HttpSession session;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	UserRepository userRepository;
	public Order orderFinal = new Order();
	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}
	@GetMapping("/orderdetail/detail/{id}")
	public String editOrderDetail(@PathVariable("id") Long id,ModelMap model) {
		OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
		List<Product> cboPro = productRepository.findAll();
		model.addAttribute("orderDetails",orderDetail);
		model.addAttribute("cboPro",cboPro);
		return "admin/editOrderDetail";
	}
	@PostMapping("/orderdetail/addOrderDetail")
	public String addOrDetail(@Validated @ModelAttribute("orderdetail") OrderDetail orderDetail,ModelMap model,RedirectAttributes attributes) {
		try {
			Long idOrder = orderDetail.getOrder().getOrderId();
			orderDetailRepository.save(orderDetail);
			attributes.addFlashAttribute("successadd", "Thành công");
			System.out.println("acdckajs" + orderDetail.getOrderDetailId());
			return "redirect:/admin/order/detail/" + idOrder;
		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thất bại");
			return "/admin/orders";
		}
	}
	@PostMapping("/orderdetail/updatePriceForOrder")
	@Transactional
	public String updatePriceForOrder(@ModelAttribute("orders") Order orders, ModelMap model,
			RedirectAttributes attributes,User user) {
		try {

			// Kiểm tra orderId trước khi cập nhật
			Long orderId = orders.getOrderId();
			List<OrderDetail> listO = orderDetailRepository.findByOrderId(orderId);
			Order existingOrder = orderRepository.findById(orderId).orElse(null);
			double totalPrice = listO.stream()
	                .mapToDouble(item -> (item.getPrice() - (item.getPrice() * item.getProduct().getDiscount() / 100)) * item.getQuantity())
	                .sum();
			Double totalNew = totalPrice+orders.getPriceShip();
			existingOrder.setPriceShip(orders.getPriceShip());
			existingOrder.setAmount(totalNew);
			orderRepository.save(existingOrder);
			commomDataService.senmailUpdate(existingOrder.getUser().getEmail(), "Xac nhan don hang", "aaa", totalNew, orderId);
			attributes.addFlashAttribute("successadd", "Thành công");
		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thất bại");
			e.printStackTrace();
			return "/admin/orders";
		}
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/orderdetail/delete/{id}")
	public String deleteDetail(@PathVariable("id") Long id,RedirectAttributes attributes) {
		try {
			OrderDetail orderDetail = orderDetailRepository.findById(id).get();
			Long orderId = orderDetail.getOrder().getOrderId();
			orderDetailRepository.deleteById(id);
			attributes.addFlashAttribute("successmessage", "Đã xóa thành công");
			return "redirect:/admin/order/detail/" + orderId;

		} catch (Exception e) {
			attributes.addFlashAttribute("errormessage", "Không thể xóa");
			return "/admin/orders";

		}
	    
	}
}
