package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {
}
