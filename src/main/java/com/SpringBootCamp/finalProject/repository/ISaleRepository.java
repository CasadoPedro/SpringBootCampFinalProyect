package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {

    @Query("SELECT s FROM Sale s WHERE s.sale_date = :date")
    List<Sale> findAllByDate(@Param("date") LocalDate date);

    @Query(value="SELECT * FROM sale ORDER BY total_cost DESC LIMIT 1", nativeQuery = true)
    Sale findTopSale();
}
