package br.edu.infnet.thomaspereirasellerapi.model.domain.repository;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository <Seller, Long>{

    Optional<Seller> findSellerById(long Id);
}
