package vn.fs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entities.Category;
import vn.fs.entities.Size;
import vn.fs.repository.SizeRepository;
import vn.fs.service.SizeService;
@Service
public class SizeServiceImpl implements SizeService{
	@Autowired
	SizeRepository sizeRepository;
	@Override
	public Optional<Size> getSizeById(Long id){
		return sizeRepository.findById(id);
	}
}
