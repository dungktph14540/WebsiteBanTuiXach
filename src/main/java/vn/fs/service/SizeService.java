package vn.fs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.fs.entities.Size;

@Service
public interface SizeService {

	Optional<Size> getSizeById(Long id);

}
