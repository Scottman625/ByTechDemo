package com.bytech.demo.dao;

import com.bytech.demo.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Integer> {

    Optional<Asset> findBySymbolAndPerson_id(String symbol, Integer personId);
}
