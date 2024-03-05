package vn.fs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.fs.entities.Voucher;

@Service
public interface VoucherService {

	Optional<Voucher> getVouById(Long id);

}
