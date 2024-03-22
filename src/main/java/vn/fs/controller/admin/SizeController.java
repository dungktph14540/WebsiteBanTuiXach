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

import vn.fs.entities.Size;
import vn.fs.entities.User;
import vn.fs.repository.SizeRepository;
import vn.fs.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class SizeController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	SizeRepository sizeRepository;
	
	
	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	// show list category - table list
	@ModelAttribute("sizes")
	public List<Size> showSize(Model model) {
		List<Size> sizes = sizeRepository.findAll();
		model.addAttribute("sizes", sizes);

		return sizes;
	}

	@GetMapping(value = "/size")
	public String sizes(Model model, Principal principal) {
		Size size = new Size();
		model.addAttribute("size", size);

		return "admin/size";
	}

	// add category
	@PostMapping(value = "/addSize")
	public String addSize(@Validated @ModelAttribute("size") Size size, ModelMap model,
			RedirectAttributes attributes) {
		try {
			List<Size> sizes = sizeRepository.findAll();
			Boolean isNameSize = true;
			for (Size lsize : sizes) {
				if (size.getNameSize().equalsIgnoreCase(lsize.getNameSize())) {
					isNameSize = false;
					break;
				}
			}
			if (isNameSize) {
				sizeRepository.save(size);
				attributes.addFlashAttribute("successsize", "Thành công");
			} else {
				attributes.addFlashAttribute("errorsize", "Thất bại"); // check trùng ở đây
				model.addAttribute("errors", "Size " + size.getNameSize() + " đã tồn tại ");

				return "admin/size";
			}
//			sizeRepository.save(size);
//			attributes.addFlashAttribute("successsize", "Thành công");

		} catch (Exception e) {
			attributes.addFlashAttribute("errorsize", "Thất bại");

			return "admin/size";

		}
		
		return "redirect:/admin/size";
	}

	// get Edit category
	@GetMapping(value = "/editSiae/{id}")
	public String editCategory(@PathVariable("id") Long id, ModelMap model) {
		Size size = sizeRepository.findById(id).orElse(null);

		model.addAttribute("size", size);

		return "admin/size";
	}
}
