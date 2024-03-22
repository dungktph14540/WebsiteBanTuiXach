package vn.fs.controller.admin;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.entities.User;
import vn.fs.entities.Voucher;
import vn.fs.repository.UserRepository;
import vn.fs.repository.VoucherRepository;

@Controller
@RequestMapping("/admin")
public class VoucherController {
	@Autowired
	VoucherRepository voucherRepository;
	
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

	@ModelAttribute("vouchers")
	public List<Voucher> showVoucher(Model model) {
		List<Voucher> vouchers = voucherRepository.findAll();
		model.addAttribute("vouchers", vouchers);

		return vouchers;
	}

	@GetMapping(value = "/voucher")
	public String vouchers(Model model, Principal principal) {
		Voucher voucher = new Voucher();
		model.addAttribute("voucher", voucher);

		return "admin/voucher";
	}

	@PostMapping(value = "/addVoucher")
	public String addVoucher(@Validated @ModelAttribute("voucher") Voucher voucher, ModelMap model,
			RedirectAttributes attributes) {
		try {
			
			voucherRepository.save(voucher);
			attributes.addFlashAttribute("succes", "Thành công");
			System.out.println("acdckajs" + voucher.getNameVoucher());
		} catch (Exception e) {
			attributes.addFlashAttribute("erro", "Thất bại");
			e.printStackTrace();
			return "admin/voucher";

		}
		
		return "redirect:/admin/voucher";
	}

	@GetMapping(value = "/editVoucher/{id}")
	public String editVoucher(@PathVariable("id") Long id, ModelMap model) {
		Voucher voucher = voucherRepository.findById(id).orElse(null);

		model.addAttribute("voucher", voucher);

		return "admin/editVoucher";
	}

//	@GetMapping("/delete/{id}")
//	public String delVoucher(@PathVariable("id") Long id, Model model,RedirectAttributes attributes) {
//		try {
//			voucherRepository.deleteById(id);
//			attributes.addFlashAttribute("successmessage", "Đã xóa thành công");
//
//		} catch (Exception e) {
//			attributes.addFlashAttribute("errormessage", "Không thể xóa");
//
//		}
//
//
//		return "redirect:/admin/voucher";
//	}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
