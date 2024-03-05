package vn.fs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.fs.commom.CommomDataService;
import vn.fs.entities.Product;
import vn.fs.entities.Size;
import vn.fs.entities.User;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.SizeRepository;
import vn.fs.service.impl.Product1ServiceImpl;
import vn.fs.service.impl.ProductServiceImpl;


@Controller
public class ProductDetailController extends CommomController{
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductServiceImpl productServiceImpl;
	@Autowired
	SizeRepository sizeRepository;
	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "productDetail")
	public String productDetail(@RequestParam("id") Long id, Model model, User user) {

		Product product = productRepository.findById(id).orElse(null);
		model.addAttribute("product", product);

		commomDataService.commonData(model, user);
		listProductByCategory10(model, product.getCategory().getCategoryId());

		return "web/productDetail";
	}
	@ModelAttribute("productList")
	public List<Product> showProduct(Model model) {
		List<Product> productList = productRepository.findAll();
		model.addAttribute("productList", productList);

		return productList;
	}
	@ModelAttribute("sizeList")
	public List<Size> showSize(Model model) {
		List<Size> sizeList = sizeRepository.findAll();
		model.addAttribute("sizeList", sizeList);
		return sizeList;
	}
	@GetMapping(value = "productDetail2")
	public String productDetail2(@RequestParam("maSP") int masp,@RequestParam("sizeId") Long sizeid, Model model, User user) {

		Product product = (Product) productServiceImpl.getAllSize2(masp,sizeid);
		model.addAttribute("product", product);

		commomDataService.commonData(model, user);
		listProductByCategory10(model, product.getCategory().getCategoryId());

		return "web/productDetail";
	}
	
	// Gợi ý top 10 sản phẩm cùng loại
	public void listProductByCategory10(Model model, Long categoryId) {
		List<Product> products = productRepository.listProductByCategory10(categoryId);
		model.addAttribute("productByCategory", products);
	}
}
