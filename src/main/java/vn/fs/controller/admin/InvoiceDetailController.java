package vn.fs.controller.admin;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import vn.fs.commom.CommomDataService;
import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceDetail;
import vn.fs.entities.Order;
import vn.fs.entities.Product;
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
		boolean productExists = false;
		try {
			List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(idInvoice);
			for (InvoiceDetail inDetail : details) {
				if (inDetail.getProducts().equals(invoiceDetails.getProducts())) {
					int quantityDetail =  invoiceDetails.getQuantity()*2;
					Long idProd = invoiceDetails.getProducts().getProductId();
					Product product = productRepository.findById(idProd).get();
					if (quantityDetail > product.getQuantity()) {
						inDetail.setQuantity(product.getQuantity());
						productExists = true;
						break;
					} else {
						inDetail.setQuantity(inDetail.getQuantity() + invoiceDetails.getQuantity());
						productExists = true;
						break;
					}
				}
			}
			if (!productExists) {
				details.add(invoiceDetails);
			}
			invoiceDetailRepository.saveAll(details);
			attributes.addFlashAttribute("successadd", "Thêm sản phâm thành công");
			System.out.println("acdckajs" + invoiceDetails.getInvoiceDetailId());
			return "redirect:/admin/invoices/detail/" + idInvoice;
		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thêm sản phâm thất bại");
			return "redirect:/admin/invoices/detail/" + idInvoice;
		}
	}
	@GetMapping("/invoideDetails/updateQuantity/{id}/{quantity}")
	public String updateInvoice(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity,
			ModelMap model,RedirectAttributes attributes) {
		try {
			InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(id).orElse(null);
			Long idInvoce = invoiceDetail.getInvoice().getInvoiceId();
			if (invoiceDetail != null) {
				invoiceDetail.setQuantity(quantity);
				invoiceDetailRepository.save(invoiceDetail);
				attributes.addFlashAttribute("successadd", "Đã cập nhật số lượng thành công");
			} 
			return "redirect:/admin/invoices/detail/" + idInvoce;
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("erroradd", "Cập nhật thất bại");

			return "redirect:/admin/invoices/lsInvoice";
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