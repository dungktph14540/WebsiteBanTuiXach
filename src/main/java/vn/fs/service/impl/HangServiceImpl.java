package vn.fs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entities.Hang;
import vn.fs.repository.HangRepository;
import vn.fs.service.HangService;

@Service
public class HangServiceImpl implements HangService{
	
	@Autowired 
	private HangRepository hangRepository;

	@Override
	public Optional<Hang> getHangById(Long id) {
		return hangRepository.findById(id);
	}

}