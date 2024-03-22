package vn.fs.service;

import java.util.Optional;

import org.springframework.stereotype.Service;


import vn.fs.entities.Hang;

@Service
public interface HangService {

	Optional<Hang> getHangById(Long id);
}