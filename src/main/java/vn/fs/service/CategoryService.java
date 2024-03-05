package vn.fs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.fs.entities.Category;

@Service
public interface CategoryService {

	Optional<Category> getCateById(Long id);

}
