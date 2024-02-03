package com.SpringBootCamp.finalProject.repository;

import com.SpringBootCamp.finalProject.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository <Client,Long>{
}
