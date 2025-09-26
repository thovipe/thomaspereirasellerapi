package br.edu.infnet.thomaspereirasellerapi.model.domain.repository;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Long> {
}
