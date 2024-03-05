package vn.fs.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entities.Voucher;
import vn.fs.repository.VoucherRepository;
import vn.fs.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
	VoucherRepository voucherRepository;
	@Override
	public Optional<Voucher> getVouById(Long id){
		return voucherRepository.findById(id);
	}
}
