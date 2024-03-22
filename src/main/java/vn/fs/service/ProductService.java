package vn.fs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.fs.dto.ProductDto;
import vn.fs.entities.Product;

@Service
public interface ProductService {


	void deleteById(Long id);


	void save(Product product);

	List<ProductDto> findAll();

	//Product savePro(MultipartFile imageProduct, ProductDto productDto);

	//Product update(MultipartFile imageProduct, ProductDto productDto);


	ProductDto getById(Long id);





}
