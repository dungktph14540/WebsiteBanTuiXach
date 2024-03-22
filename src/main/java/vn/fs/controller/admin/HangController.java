package vn.fs.controller.admin;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.entities.Category;
import vn.fs.entities.Hang;
import vn.fs.entities.User;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.HangRepository;
import vn.fs.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class HangController {
	
	@Autowired
	HangRepository hangRepository;

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

	// show list hang - table list
	@ModelAttribute("lshang")
	public List<Hang> showHang(ModelMap model){
		List<Hang> lsHang = hangRepository.findAll();
		model.addAttribute("lshang", lsHang);

		return lsHang;
	}

	@GetMapping(value = "/hangs")
	public String hangs(Model model, Principal principal) {
		Hang hangs = new Hang();
		model.addAttribute("hangs", hangs);

		return "admin/hang";
	}

	// add hang
	@PostMapping(value = "/hang/addHang")
	public String addHang(@Validated @ModelAttribute("hangs") Hang hangs, ModelMap model,
			RedirectAttributes attributes) {
		try {
			hangRepository.save(hangs);
			attributes.addFlashAttribute("successadd", "Thành công");
			System.out.println("acdckajs" + hangs.getTenhang());

		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thất bại");

			return "admin/hang";

		}
		
		return "redirect:/admin/hangs";
	}

	// get Edit hang
	@GetMapping(value = "/hang/editHang/{id}")
	public String editHang(@PathVariable("id") Long id, ModelMap model) {
		Hang hangs = hangRepository.findById(id).orElse(null);

		model.addAttribute("hangs", hangs);

		return "admin/editHang";
	}

	// delete hang
	@GetMapping("/hang/delete/{id}")
	public String delHang(@PathVariable("id") Long id, Model model,RedirectAttributes attributes) {
		try {
			hangRepository.deleteById(id);
			attributes.addFlashAttribute("successmessage", "Đã xóa thành công");

		} catch (Exception e) {
			attributes.addFlashAttribute("errormessage", "Không thể xóa");

		}


		return "redirect:/admin/hangs";
	}

}