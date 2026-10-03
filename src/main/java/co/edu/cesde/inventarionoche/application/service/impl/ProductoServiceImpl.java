package co.edu.cesde.inventarionoche.application.service.impl;

import co.edu.cesde.inventarionoche.application.service.ProductoService;
import co.edu.cesde.inventarionoche.domain.model.Producto;
import co.edu.cesde.inventarionoche.infrastructure.exception.DuplicateResourceException;
import co.edu.cesde.inventarionoche.infrastructure.exception.ResourceNotFoundException;
import co.edu.cesde.inventarionoche.infrastructure.exception.ValidationException;
import co.edu.cesde.inventarionoche.infrastructure.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        if (productoRepository.findByNombre(producto.getNombre()).isPresent()) {
            throw new DuplicateResourceException("Ya existe un producto con el nombre: " + producto.getNombre());
        }
        producto.setId(null);
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id: " + id));
    }

    @Override
    public void delete(Long id) {
        Producto producto = findById(id);
        productoRepository.delete(producto);
    }

    @Override
    public Producto update(Producto producto) {
        if (producto.getId() == null) {
            throw new ValidationException("El id del producto es obligatorio para actualizar");
        }
        findById(producto.getId());
        return productoRepository.save(producto);
    }
}