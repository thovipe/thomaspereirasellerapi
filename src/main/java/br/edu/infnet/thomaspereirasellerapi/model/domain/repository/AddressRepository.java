package br.edu.infnet.thomaspereirasellerapi.model.domain.repository;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findAddressById(Long id);
}
