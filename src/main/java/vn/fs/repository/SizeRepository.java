package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.Size;
@Repository
public interface SizeRepository extends JpaRepository<Size, Long>{

}
