package vn.fs.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.dto.ProductDto;

import vn.fs.controller.LoadImageController;
import vn.fs.entities.Product;
import vn.fs.repository.ProductRepository;
import vn.fs.service.ProductService;
//import vn.fs.util.ImageUpload;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepository;
	
	
//	@Autowired
//	private ImageUpload imageUpload;
	
	public List<Product> getAll(){
		return productRepository.findAll();
	}
	
	@Override
	public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

	
	
	@Override
	public void save(Product product) {
		 productRepository.save(product);
	}
	public Optional<Product> getProductByid(Long id){
		return productRepository.findById(id);
	}
	
	public List<Product> getAllCate(Long id){
		return productRepository.listProductByCategory(id);
	}
	
	public List<Product> getAllSize(Long id){
		return productRepository.listProductBySize(id);
	}
	
	public Product getAllSize2( int masp, Long idSize){
		return productRepository.ProductBySize2(masp, idSize);
	}
	
	public List<Product> listProductByMaSP(int masp, String sizeReal){
		return productRepository.listProductByMaSP2(masp, sizeReal);
	}
	
	public Optional<Product> updatePro (Long id,Product product) {
		Product productOld = productRepository.getById(id);
		product.setProductImage(productOld.getProductImage());
		return productRepository.findById(id);
	}
	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);;
	}
	@Override
	public ProductDto getById(Long id) {
		Product product = productRepository.getById(id);
		ProductDto productDto = new ProductDto();
		productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setSize(product.getSize());
        productDto.setChatLuong(product.getChatLuong());
        productDto.setGhiChu(product.getGhiChu());
        productDto.setDiscount(product.getDiscount());
        productDto.setEnteredDate(product.getEnteredDate());
        productDto.setProductImage(product.getProductImage());
        productDto.setPrice(product.getPrice());
        productDto.setMaSP(product.getMaSP());
		return productDto;
	}
	private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setDescription(product.getDescription());
            productDto.setQuantity(product.getQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setSize(product.getSize());
            productDto.setChatLuong(product.getChatLuong());
            productDto.setGhiChu(product.getGhiChu());
            productDto.setDiscount(product.getDiscount());
            productDto.setEnteredDate(product.getEnteredDate());
            productDto.setProductImage(product.getProductImage());
            productDto.setPrice(product.getPrice());
            productDto.setMaSP(product.getMaSP());
            productDto.setStatus(product.getStatus());
            productDto.setFavorite(false);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
	
}
