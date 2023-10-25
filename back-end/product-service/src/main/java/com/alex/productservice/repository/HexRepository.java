package com.alex.productservice.repository;

import com.alex.productservice.model.Hex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HexRepository extends JpaRepository<Hex, Integer> {
    Optional<Hex> findHexByIdHex(String idHex);

    Boolean existsByIdHex(String idHex);
    List<Hex> findHexesByProduct_ProductNumber(Integer product);
}
