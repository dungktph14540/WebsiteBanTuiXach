package vn.fs.controller.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.fs.commom.CommomDataService;
import vn.fs.dto.ApiProduct;
import vn.fs.dto.ProductDto;
import vn.fs.entities.CartItem;
import vn.fs.entities.Invoice;
import vn.fs.entities.InvoiceCart;
import vn.fs.entities.InvoiceDetail;
import vn.fs.entities.Product;
import vn.fs.entities.Size;
import vn.fs.entities.User;
import vn.fs.repository.InvoiceDetailRepository;
import vn.fs.repository.InvoiceRepository;
import vn.fs.repository.ProductRepository;
import vn.fs.repository.SizeRepository;
import vn.fs.repository.UserRepository;
import vn.fs.service.ProductService;
import vn.fs.service.ShoppingCartService;

@Controller
@RequestMapping("/admin")
public class InvoiceController{
	@Autowired
	HttpSession session;
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	InvoiceDetailRepository invoiceDetailRepository;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	CommomDataService commomDataService;
	@Autowired
	SizeRepository sizeRepository;
	@Autowired
	ProductService productService;
	
	Invoice invoiceFinal = new Invoice();
	
	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}
	@ModelAttribute(value = "invoices")
	public List<Invoice> invoices(ModelMap model) {
		List<Invoice> invoice = invoiceRepository.findAll();
		model.addAttribute("invoices",invoice);
        model.addAttribute("optionPro", new Product());
		return invoice;
	}
	@GetMapping("/invoices")
	public String invoiceform(Model model,User user) {
		Invoice invoices = new Invoice();
		model.addAttribute("invoice",invoices);
        model.addAttribute("matchedProducts", productRepository.findAll());
        model.addAttribute("invoiceDetail",invoiceDetailRepository.findAll());
    	Collection<InvoiceCart> cartItems = shoppingCartService.getInvoiceCarts();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());
		model.addAttribute("NoOfItems", shoppingCartService.getCount());
		double totalPrice = 0;
		for (InvoiceCart cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		commomDataService.commonDataInvoice(model, user);
		return "admin/invoice";
	}
	@GetMapping("/invoice/tabInvoiceNew")
	public ModelAndView tabInvoice(Model model,User user) {
    	Collection<InvoiceCart> cartItems = new ArrayList<InvoiceCart>();
    	

    	model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmount());
		model.addAttribute("NoOfItems", shoppingCartService.getCount());
		double totalPrice = 0;
		for (InvoiceCart cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		commomDataService.commonDataInvoice(model, user);
		return new ModelAndView("forward:/admin/invoices");
	}
	
	@PostMapping(value = "/invoices/addToCart")
	public String add(
	    @RequestParam("productId") Long productId,
	    @RequestParam("quantity") Integer quantity,
	    @RequestParam("sizeId") Long sizeId,
	    HttpServletRequest request,
	    Model model,
	    RedirectAttributes attributes
	) {
	    Product product = productRepository.findById(productId).orElse(null);
	    Size size = sizeRepository.findById(sizeId).orElse(null);

	    if (product != null && size != null) {
	        InvoiceCart item = new InvoiceCart();
	        item.setQuantity(quantity);
	        item.setProduct(product);
	        item.setId(productId);
	        item.setSize(size);
	        shoppingCartService.add3(item, product);

	        // Lưu giỏ hàng vào session
	        session = request.getSession();
	        session.setAttribute("cartItems", shoppingCartService.getInvoiceCarts());
	    }

	    model.addAttribute("totalCartItems", shoppingCartService.getCount());

	    return "redirect:/admin/invoices";
	}
	
	@GetMapping("/updateInvoice/{id}/{quantity}")
	public String updateInvoice(@PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
		try {
			shoppingCartService.updateInvoice(id, quantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "forward:/admin/invoices";
	}
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/removeInvoice/{id}")
	public String removeInvoiceCart(@PathVariable("id") Long id, HttpServletRequest request, Model model) {
		Product product = productRepository.findById(id).orElse(null);

		Collection<InvoiceCart> cartItems = shoppingCartService.getInvoiceCarts();
		session = request.getSession();
		if (product != null) {
			InvoiceCart item = new InvoiceCart();
			BeanUtils.copyProperties(product, item);
			item.setProduct(product);
			item.setId(id);
			cartItems.remove(session);
			shoppingCartService.removeCartInvoice(item);
		}
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
	    return "redirect:/admin/invoices";
	}
	@GetMapping("/invoices/search")
	public String searchProduct(@RequestParam("searchTerm") String searchTerm, ModelMap model, RedirectAttributes attributes) {
	    try {
	    	 Invoice invoices = new Invoice();
	  		model.addAttribute("invoice",invoices);
	  		if(searchTerm.trim().equalsIgnoreCase("")) {
	  			attributes.addFlashAttribute("successadd", "Danh sách sản phẩm");
	    	    return "redirect:/admin/invoices";
	  		}
	        List<Product> matchedProducts = productRepository.findbyProWithIdOrName(searchTerm);
	        if (!matchedProducts.isEmpty()) {
	            model.addAttribute("matchedProducts", matchedProducts);
	            model.addAttribute("successadd", "Tìm kiếm thành công");
	        } 
	        else {
	            model.addAttribute("erroradd", "Không tìm thấy sản phẩm");
	    	    return "forward:/admin/invoices";
	        }	       
	    } catch (Exception e) {
  			attributes.addFlashAttribute("erroradd", "Có lỗi sảy ra");
    	    return "redirect:/admin/invoices";
	    }
	    return "admin/invoice";
	}
	
	@PostMapping(value = "/invoices/addInvoide")
	@Transactional
	public String checkoutInvoice(Model model,@ModelAttribute("invoice") Invoice invoice,HttpServletRequest request,User user) throws MessagingException {
		Collection<InvoiceCart> cartItems = shoppingCartService.getInvoiceCarts();
		double totalPrice = 0;
		for (InvoiceCart cartItem : cartItems) {
			double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
			totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
		}
		BeanUtils.copyProperties(invoice, invoiceFinal);
		session = request.getSession();
		Date date = new Date();
		invoice.setAmount(totalPrice);
		invoice.setInvoiceDate(date);
		invoice.setStatus(0);
		model.addAttribute("totalPrice", totalPrice);
		invoiceRepository.save(invoice);
		for(InvoiceCart invoiceCart : cartItems ) {
			InvoiceDetail detailInvoice = new InvoiceDetail();
			detailInvoice.setQuantity(invoiceCart.getQuantity());
			detailInvoice.setPrice(invoiceCart.getProduct().getPrice());
			detailInvoice.setProducts(invoiceCart.getProduct());
			detailInvoice.setInvoice(invoice);
			invoiceDetailRepository.save(detailInvoice);
		}
		Long idInvo = invoice.getInvoiceId();
		shoppingCartService.clearInvoice();
		session.removeAttribute("cartItems");
		return "redirect:/admin/invoices/detail/" + idInvo;
	}
	
	@GetMapping("/invoices/lsInvoice")
	public String lsInvoice(ModelMap model) {		
		List<Invoice> lsinvoice = invoiceRepository.findAll();
		model.addAttribute("lsinvoice",lsinvoice);
	    return "admin/lsinvoice";
	}
	@RequestMapping("/invoices/payForinvoice/{invoice_id}")
	public ModelAndView payForinvoice(ModelMap model,@PathVariable("invoice_id") Long id) {
		Optional<Invoice> iv = invoiceRepository.findById(id);
		if(iv.isEmpty()) {
			return new ModelAndView("forward:/admin/invoices", model);
		}
		Invoice ivReal = iv.get();
		ivReal.setStatus((short) 1);
		invoiceRepository.save(ivReal);
		Product p = null;
		List<InvoiceDetail> listDetailVoce = invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(id);
		for (InvoiceDetail lsIv : listDetailVoce) {
			p = lsIv.getProducts();
			p.setQuantity(p.getQuantity() - lsIv.getQuantity());
			productRepository.save(p);
		}
		return new ModelAndView("forward:/admin/invoices/lsInvoice", model);

	}
	@GetMapping("/invoices/delete/{id}")
	public String deleteInvoide(@PathVariable("id") Long id,Model model) {
		invoiceRepository.deleteById(id);
		return "redirect:/admin/invoices/lsInvoice";
	}
	@GetMapping("/invoices/detail/{id}")
	public String invoiceDetailLs(Model model,@PathVariable("id") Long id,User user) {
		List<InvoiceDetail> details = invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(id);
		double totalPrice = details.stream()
                .mapToDouble(item -> (item.getPrice() - (item.getPrice() * item.getProducts().getDiscount() / 100)) * item.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);
		List<Product> cboPro = productRepository.findAll();
		model.addAttribute("cboPro",cboPro);
        model.addAttribute("invoiceId",id);
model.addAttribute("invoiceDetails",new InvoiceDetail());
model.addAttribute("status",invoiceRepository.findById(id).get().getStatus());
		model.addAttribute("lsDetailInvoice",invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(id));
		return "admin/invoiceDetail";
	}
	@GetMapping("/invoiceDetail/delete/{id}")
	public String invoiceDetailDelete(@PathVariable("id") Long id) {
		InvoiceDetail detail = invoiceDetailRepository.findById(id).get();
		Long idInvoice = detail.getInvoice().getInvoiceId();
		invoiceDetailRepository.deleteById(id);
		List<InvoiceDetail> lsDetail = invoiceDetailRepository.findByInvoiceDeTailByInvoiceId(idInvoice);
		if(lsDetail.isEmpty()) {
			invoiceRepository.deleteById(idInvoice);
			return "redirect:/admin/invoices/lsInvoice";
		}
		return "redirect:/admin/invoices/detail/"+idInvoice;
	}
	@GetMapping(value = "/getProdcutApiById/{id}")
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
	@PostMapping(value = "/invoices/addToinvoiceCartVsQR")
	public String addTocartVsQR(
	    @RequestParam("productId") Long productId,
	    @RequestParam("quantity") Integer quantity,
	    @RequestParam("sizeId") Long sizeId,
	    HttpServletRequest request,
	    Model model,
	    RedirectAttributes attributes
	) {
	    Product product = productRepository.findById(productId).orElse(null);
	    Size size = sizeRepository.findById(sizeId).orElse(null);

	    if (product != null && size != null) {
	        InvoiceCart item = new InvoiceCart();
	        item.setQuantity(quantity);
	        item.setProduct(product);
	        item.setId(productId);
	        item.setSize(size);
	        shoppingCartService.add3(item, product);

	        // Lưu giỏ hàng vào session
	        session = request.getSession();
	        session.setAttribute("cartItems", shoppingCartService.getInvoiceCarts());
	    }

	    model.addAttribute("totalCartItems", shoppingCartService.getCount());

	    return "redirect:/admin/invoices";
	}
	
}
