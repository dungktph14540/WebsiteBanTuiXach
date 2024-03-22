package vn.fs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entities.Category;
import vn.fs.repository.CategoryRepository;
import vn.fs.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public Optional<Category> getCateById(Long id){
		return categoryRepository.findById(id);
	}
}
