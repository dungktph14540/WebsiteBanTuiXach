package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entities.Hang;
@Repository
public interface HangRepository extends JpaRepository<Hang, Long>{

}