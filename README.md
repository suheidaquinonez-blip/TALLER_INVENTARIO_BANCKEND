Sistema de Inventario - Spring Boot

API REST para gestionar productos, controlar el stock y registrar movimientos de entrada y salida.

Hecho por: Genesis Quiñonez 

Tecnologías:

Java 21, Spring Boot 4.0.8, Spring Data JPA, MySQL (XAMPP), Lombok y Swagger.

Nota: se usó MySQL en lugar de PostgreSQL.

Cómo ejecutarlo:

1. Encender MySQL en XAMPP.
2. Crear la base de datos `inventario` en phpMyAdmin.
3. Revisar el usuario y la contraseña en `src/main/resources/application.properties`.
4. Ejecutar la clase `InventarionocheApplication`.
5. Abrir Swagger en http://localhost:8080/swagger-ui.html

Las tablas se crean solas al arrancar.

Endpoints:

Productos: GET, POST, PUT y DELETE en `/api/productos`
Movimientos: GET, POST, PUT y DELETE en `/api/movimientos`

Reglas:

- Una ENTRADA suma al stock.
- Una SALIDA resta al stock, pero solo si hay suficiente.
- No se permiten productos con el mismo nombre.

Errores:

- 404: el producto o movimiento no existe.
- 400: datos inválidos o stock insuficiente.
- 409: producto duplicado.
