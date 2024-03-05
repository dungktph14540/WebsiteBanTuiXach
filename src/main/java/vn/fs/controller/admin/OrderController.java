package vn.fs.controller.admin;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.fs.dto.OrderExcelExporter;
import vn.fs.entities.Category;
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


@Controller
@RequestMapping("/admin")
public class OrderController {

	@Autowired
	OrderDetailService orderDetailService;

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

	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	// list order
	@GetMapping(value = "/orders")
	public String orders(Model model, Principal principal) {

		List<Order> orderDetails = orderRepository.findAll();
		model.addAttribute("orderDetails", orderDetails);

		return "admin/orders";
	}
	@ModelAttribute("userList")
	public List<User> showUser(Model model) {
		List<User> userList = userRepository.findAll();
		model.addAttribute("userList", userList);

		return userList;
	}
	@GetMapping("/order/detail/{order_id}")
	public ModelAndView detail(ModelMap model, Model modell, 
			Principal principal, User user, 
			@PathVariable("order_id") Long id) {

		List<OrderDetail> listO = orderDetailRepository.findByOrderId(id);
//		User userrUser =userRepository.findById(iduser).orElse(null);
//		model.addAttribute("user", userrUser);
		List<Product> cboPro = productRepository.findAll();
		model.addAttribute("orderDetails", new OrderDetail());
		model.addAttribute("cboPro",cboPro);
		double totalPrice = listO.stream()
                .mapToDouble(item -> (item.getPrice() - (item.getPrice() * item.getProduct().getDiscount() / 100)) * item.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);
        
		
		// set active front-end
		model.addAttribute("amount", orderRepository.findById(id).get().getAmount());
		model.addAttribute("orderDetail", listO);
		model.addAttribute("orderId", id);
		// set active front-end
		model.addAttribute("menuO", "menu");
		model.addAttribute("status",orderRepository.findById(id).get().getStatus());
		return new ModelAndView("admin/editOrder", model);
	}
	@PostMapping(value = "/updateUserOrder")
	public String updateUser(@Validated 
			@ModelAttribute("user") User user, ModelMap model,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "failure");

			return "/orders";
		}

		userRepository.save(user);
		model.addAttribute("message", "successful!");

		return "/orders";
	}
	@RequestMapping("/order/confirmpp/{order_id}")
	public ModelAndView confirmpp(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 7);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	@RequestMapping("/order/deliveredpp/{order_id}")
	public ModelAndView deliveredpp(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 8);
		orderRepository.save(oReal);

		Product p = null;
		List<OrderDetail> listDe = orderDetailRepository.findByOrderId(id);
		for (OrderDetail od : listDe) {
			p = od.getProduct();
			p.setQuantity(p.getQuantity() - od.getQuantity());
			productRepository.save(p);
		}

		return new ModelAndView("forward:/admin/orders", model);
	}
	@RequestMapping("/order/cancel/{order_id}/{LyDoHuy}")
	public ModelAndView cancel(ModelMap model, @PathVariable("order_id") Long id,@PathVariable("LyDoHuy") String LyDoHuy ) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 5);
		oReal.setLyDo(LyDoHuy);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}

	@RequestMapping("/order/confirm/{order_id}")
	public ModelAndView confirm(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 1);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	
	@RequestMapping("/order/delivered/{order_id}")
	public ModelAndView delivered(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 2);
		orderRepository.save(oReal);

		Product p = null;
		List<OrderDetail> listDe = orderDetailRepository.findByOrderId(id);
		for (OrderDetail od : listDe) {
			p = od.getProduct();
			p.setQuantity(p.getQuantity() - od.getQuantity());
			productRepository.save(p);
		}

		return new ModelAndView("forward:/admin/orders", model);
	}
	
	
	
	
	@RequestMapping("/order/confirmDgh/{order_id}")
	public ModelAndView confirm2(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 3);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	
	@RequestMapping("/order/confirmDghpp/{order_id}")
	public ModelAndView confirmpp2(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 10);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	
	
	
	
	
	
	@RequestMapping("/order/confirmGiao/{order_id}")
	public ModelAndView confirmpp2GH(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 4);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	@RequestMapping("/order/confirmGiaopp/{order_id}")
	public ModelAndView confirmpp2GHpp(ModelMap model, @PathVariable("order_id") Long id) {
		Optional<Order> o = orderRepository.findById(id);
		if (o.isEmpty()) {
			return new ModelAndView("forward:/admin/orders", model);
		}
		Order oReal = o.get();
		oReal.setStatus((short) 9);
		orderRepository.save(oReal);

		return new ModelAndView("forward:/admin/orders", model);
	}
	
	// to excel
	@GetMapping(value = "/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachement; filename=orders.xlsx";

		response.setHeader(headerKey, headerValue);

		List<Order> lisOrders = orderDetailService.listAll();

		OrderExcelExporter excelExporter = new OrderExcelExporter(lisOrders);
		excelExporter.export(response);

	}

}
