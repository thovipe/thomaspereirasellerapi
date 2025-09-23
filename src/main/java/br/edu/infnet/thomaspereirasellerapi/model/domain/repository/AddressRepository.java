package br.edu.infnet.thomaspereirasellerapi.model.domain.repository;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
