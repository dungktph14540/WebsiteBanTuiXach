package vn.fs.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.dto.ProductDto;
import vn.fs.entities.Category;
import vn.fs.entities.Product;
import vn.fs.entities.Size;
import vn.fs.entities.User;
import vn.fs.repository.CategoryRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.SizeRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.CategoryService;
import vn.fs.service.ProductService;
import vn.fs.service.impl.ProductServiceImpl;


@Controller
@RequestMapping("/admin")
public class ProductController{	
	@Value("${upload.path}")
	private String pathUploadImage;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	@Autowired
	ProductService productService;	
	@Autowired
	CategoryService categoryService;
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

	public ProductController(CategoryRepository categoryRepository,
			ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	// show list product - table list
	@ModelAttribute("products")
	public List<ProductDto> showProduct(Model model) {
		List<ProductDto> products = productService.findAll();
		model.addAttribute("products", products);

		return products;
	}
	

	@GetMapping(value = "/products")
	public String products(Model model, Principal principal) {
		ProductDto product = new ProductDto();
		model.addAttribute("product", product);
		return "admin/products";
	}
	@GetMapping(value = "/addddproducts")
	public String productsadd(Model model, Principal principal) {
		 model.addAttribute("productdto", new ProductDto());
	        model.addAttribute("categories", categoryRepository.findAll());
	        return "admin/addproduct";
	}
	// add product
	@PostMapping(value = "/addProduct")
	public String addProduct(@ModelAttribute("product") Product product, ModelMap model,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

		try {

			File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		product.setProductImage(file.getOriginalFilename());
		Product p =	productRepository.save(product);
		
		
		if (product != p) {
			model.addAttribute("message", "Update success");
			model.addAttribute("product", product);
		} else {
			model.addAttribute("message", "Update failure");
			model.addAttribute("product", product);
		}
		return "redirect:/admin/products";
	}

	// show select option ở add product
	@ModelAttribute("categoryList")
	public List<Category> showCategory(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		return categoryList;
	}
	@ModelAttribute("sizeList")
	public List<Size> showSize(Model model) {
		List<Size> sizeList = sizeRepository.findAll();
		model.addAttribute("sizeList", sizeList);

		return sizeList;
	}
	@GetMapping(value = "/saveproduct")
	    public String saveProduct(Model model){
	        model.addAttribute("productdto", new ProductDto());
	        model.addAttribute("categories", categoryRepository.findAll());
	        model.addAttribute("size", sizeRepository.findAll());
	        return "redirect:/admin/products";
	    }
	@PostMapping(value = "/saveproduct")
	public String addPro(@ModelAttribute("product")ProductDto productdto, 
						@RequestParam("file")MultipartFile file, RedirectAttributes attributes
						) throws IOException {
		
		try {
			Product product = new Product();
			File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			product.setProductId(productdto.getProductId());
			product.setProductName(productdto.getProductName());
			product.setCategory(productdto.getCategory());
			product.setSize(productdto.getSize());
			product.setChatLuong(productdto.getChatLuong());
			product.setGhiChu(productdto.getGhiChu());
			product.setQuantity(productdto.getQuantity());
			product.setPrice(productdto.getPrice());
			product.setDescription(productdto.getDescription());
			product.setDiscount(productdto.getDiscount());
			product.setEnteredDate(productdto.getEnteredDate());
			product.setMaSP(productdto.getMaSP());
			product.setProductImage(file.getOriginalFilename());
			productService.save(product);
			attributes.addFlashAttribute("successadd", "Thành công");
		} catch (IOException e) {
			e.printStackTrace();
			attributes.addFlashAttribute("erroradd", "Thất bại");
		}	
		return "redirect:/admin/products";

	}
	// get Edit brand
	@GetMapping(value = "/editProduct/{id}")
	public String editCategory(@PathVariable("id") Long id, 
			ModelMap model) {
		//Product product = productRepository.findById(id).orElse(null);
		ProductDto product = productService.getById(id);
		model.addAttribute("product", product);

		return "admin/editProduct";
	}
		
	
	// delete category
	@GetMapping("/deleteProduct/{id}")
	public String delProduct(@PathVariable("id") Long id, Model model,RedirectAttributes attributes) {
		try {
			productRepository.deleteById(id);
			attributes.addFlashAttribute("successmessage", "Đã xóa thành công");

		} catch (Exception e) {
			attributes.addFlashAttribute("errormessage", "Sản phẩm đã mua không thể xóa");
		}

		return "redirect:/admin/products";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	@GetMapping(value = "/getProductCBO/{id}")
	@ResponseBody
	public ResponseEntity<ProductDto> getProductPrice(@PathVariable("id") Long id,ModelMap model) {
	    ProductDto product = productService.getById(id);
	    if (product != null) {
	    	model.addAttribute("price0",product.getPrice());
	    	System.out.println(product.getPrice());
	        return new ResponseEntity<>(product, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
