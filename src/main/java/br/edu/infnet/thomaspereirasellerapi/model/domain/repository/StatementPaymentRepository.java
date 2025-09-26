package br.edu.infnet.thomaspereirasellerapi.model.domain.repository;

import br.edu.infnet.thomaspereirasellerapi.model.domain.StatementPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementPaymentRepository extends JpaRepository<StatementPayment, Long> {
}
