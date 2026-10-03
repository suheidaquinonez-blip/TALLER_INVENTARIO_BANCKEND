package co.edu.cesde.inventarionoche.application.service.impl;

import co.edu.cesde.inventarionoche.application.service.MovimientoService;
import co.edu.cesde.inventarionoche.domain.model.Movimiento;
import co.edu.cesde.inventarionoche.domain.model.Producto;
import co.edu.cesde.inventarionoche.domain.model.TipoMovimiento;
import co.edu.cesde.inventarionoche.infrastructure.exception.InsufficientStockException;
import co.edu.cesde.inventarionoche.infrastructure.exception.ResourceNotFoundException;
import co.edu.cesde.inventarionoche.infrastructure.exception.ValidationException;
import co.edu.cesde.inventarionoche.infrastructure.repository.MovimientoRepository;
import co.edu.cesde.inventarionoche.infrastructure.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public Movimiento save(Movimiento movimiento) {
        if (movimiento.getProducto() == null || movimiento.getProducto().getId() == null) {
            throw new ValidationException("Debe indicar el id del producto");
        }

        Long idProducto = movimiento.getProducto().getId();
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id: " + idProducto));

        if (movimiento.getTipo() == TipoMovimiento.ENTRADA) {
            producto.sumarStock(movimiento.getCantidad());
        } else {
            if (producto.getStock() < movimiento.getCantidad()) {
                throw new InsufficientStockException("Stock insuficiente. Disponible: "
                        + producto.getStock() + ", solicitado: " + movimiento.getCantidad());
            }
            producto.restarStock(movimiento.getCantidad());
        }

        productoRepository.save(producto);
        movimiento.setId(null);
        movimiento.setProducto(producto);
        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento findById(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el movimiento con id: " + id));
    }

    @Override
    public void delete(Long id) {
        Movimiento movimiento = findById(id);
        movimientoRepository.delete(movimiento);
    }

    @Override
    public Movimiento update(Movimiento movimiento) {
        if (movimiento.getId() == null) {
            throw new ValidationException("El id del movimiento es obligatorio para actualizar");
        }
        findById(movimiento.getId());
        return movimientoRepository.save(movimiento);
    }
}