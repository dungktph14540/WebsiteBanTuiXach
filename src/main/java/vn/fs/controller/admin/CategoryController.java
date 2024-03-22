package vn.fs.controller.admin;

import java.security.Principal;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.entities.Category;
import vn.fs.entities.User;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.UserRepository;



@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

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

	// show list category - table list
	@ModelAttribute("categories")
	public List<Category> showCategory(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		return categories;
	}

	@GetMapping(value = "/categories")
	public String categories(Model model, Principal principal) {
		Category category = new Category();
		model.addAttribute("category", category);

		return "admin/categories";
	}

	// add category
	@PostMapping(value = "/addCategory")
	public String addCategory(@Validated @ModelAttribute("category") Category category, ModelMap model,
			RedirectAttributes attributes) {
		try {
			List<Category> lsCate = categoryRepository.findAll();
			boolean isCate = true;
			for(Category categoryLs : lsCate) {
				if(category.getCategoryName().equalsIgnoreCase(categoryLs.getCategoryName())) {
					isCate = false;
					break;
				}
			}
			if(isCate) {
				categoryRepository.save(category);
				attributes.addFlashAttribute("successadd", "Thành công");
				System.out.println("acdckajs" + category.getCategoryName());
			}
			else {
				attributes.addFlashAttribute("erroradd", "Thất bại");
				model.addAttribute("errors","Thể loại " + category.getCategoryName() + " đã tồn tại");
				return "admin/categories";
			}
//			categoryRepository.save(category);
//			attributes.addFlashAttribute("successadd", "Thành công");
//			System.out.println("acdckajs" + category.getCategoryName());

		} catch (Exception e) {
			attributes.addFlashAttribute("erroradd", "Thất bại");

			return "admin/categories";

		}
		
		return "redirect:/admin/categories";
	}

	// get Edit category
	@GetMapping(value = "/editCategory/{id}")
	public String editCategory(@PathVariable("id") Long id, ModelMap model) {
		Category category = categoryRepository.findById(id).orElse(null);

		model.addAttribute("category", category);

		return "admin/editCategory";
	}

	// delete category
	@GetMapping("/delete/{id}")
	public String delCategory(@PathVariable("id") Long id, Model model,RedirectAttributes attributes) {
		try {
			categoryRepository.deleteById(id);
			attributes.addFlashAttribute("successmessage", "Đã xóa thành công");

		} catch (Exception e) {
			attributes.addFlashAttribute("errormessage", "Không thể xóa");

		}


		return "redirect:/admin/categories";
	}
}
