package co.edu.cesde.inventarionoche.presentation.controller;

import co.edu.cesde.inventarionoche.application.service.MovimientoService;
import co.edu.cesde.inventarionoche.domain.model.Movimiento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> findAll() {
        return movimientoService.findAll();
    }

    @GetMapping("/{id}")
    public Movimiento findById(@PathVariable Long id) {
        return movimientoService.findById(id);
    }

    @PostMapping
    public Movimiento save(@Valid @RequestBody Movimiento movimiento) {
        return movimientoService.save(movimiento);
    }

    @PutMapping
    public Movimiento update(@Valid @RequestBody Movimiento movimiento) {
        return movimientoService.update(movimiento);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movimientoService.delete(id);
    }
}