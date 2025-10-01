package br.edu.infnet.thomaspereirasellerapi.controller;

import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementRequestDTO;
import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.StatementResponseDTO;
import br.edu.infnet.thomaspereirasellerapi.model.service.StatementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/statements")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping
    public ResponseEntity<List<StatementResponseDTO>> getStatements() {
        return ResponseEntity.status(HttpStatus.OK).body(statementService.getAllStatements());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<StatementResponseDTO> getStatementById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(statementService.getStatement(id));
    }

    @PostMapping
    public ResponseEntity<StatementResponseDTO> createStatement(@Valid @RequestBody StatementRequestDTO statement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statementService.addStatement(statement));
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteStatement(@PathVariable Long id) {
        statementService.deleteStatement(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
