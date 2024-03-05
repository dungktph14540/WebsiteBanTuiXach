package vn.fs.controller.admin;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceDetail;
import vn.fs.entities.Order;
import vn.fs.entities.User;
import vn.fs.repository.InvoiceDetailRepository;
import vn.fs.repository.InvoiceRepository;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.OrderRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.OrderDetailService;
import vn.fs.service.SendMailService;
import vn.fs.service.ShoppingCartService;

@Controller
@RequestMapping("/admin")
public class InvoiceDetailController {
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	CommomDataService commomDataService;
	@Autowired
	HttpSession session;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	InvoiceDetailRepository invoiceDetailRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
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

	@PostMapping("/invoideDetails/addInvoiceDetail")
	public String addInvoiceDetail(@ModelAttribute("invoiceDetails") InvoiceDetail invoiceDetails, ModelMap model,
			RedirectAttributes attributes) {
		Long idInvoice = invoiceDetails.getInvoice().getInvoiceId();
		try {
			invoiceDetailRepository.save(invoiceDetails);
			attributes.addFlashAttribute("successadd", "Thêm sản phâm thành công");
			System.out.println("acdckajs" + invoiceDetails.getInvoiceDetailId());
			return "redirect:/admin/invoices/detail/" + idInvoice;
		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thêm sản phâm thất bại");
			return "redirect:/admin/invoices/detail/" + idInvoice;
		}
	}
	@PostMapping("/invoiceDetails/updatePriceForInvoice")
	public String updatePriceInvoice(@ModelAttribute("invoices") Invoice invoices,ModelMap model,RedirectAttributes attributes) {
		try {
			Long idInvoice = invoices.getInvoiceId();
			List<InvoiceDetail> list0 = invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(idInvoice);
			Invoice existingInvoice = invoiceRepository.findById(idInvoice).get();
			double totalPrice = list0.stream()
	                .mapToDouble(item -> (item.getPrice() - (item.getPrice() * item.getProducts().getDiscount() / 100)) * item.getQuantity())
	                .sum();
			existingInvoice.setAmount(totalPrice);
			invoiceRepository.save(existingInvoice);
			attributes.addFlashAttribute("successadd", "Đã cập nhật lại giá thành công");

		} catch (Exception e) {
			attributes.addFlashAttribute("errorsadd", "Cập nhật thất bại");
			return "redirect:/admin/invoices/lsInvoice";
		}
		return "redirect:/admin/invoices/lsInvoice";
	}

}
