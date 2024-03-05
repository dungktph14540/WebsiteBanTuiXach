package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.Voucher;
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>{

}
