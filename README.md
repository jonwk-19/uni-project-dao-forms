# Formularios con patron DAO

Proyecto en Java con Swing y MySQL. Tiene tres formularios (Clientes, Empleados y Marcas) que guardan en la base de datos usando una clase DAO por entidad.

## Requisitos

- Java 17
- Maven
- MySQL 8

## Estructura

```
db/script.sql          esquema (7 tablas) y datos de prueba
src/main/java
  Main.java            punto de entrada
  modelo/              Cliente, Empleado, Puesto, Marca
  dao/                 ClienteDAO, EmpleadoDAO, PuestoDAO, MarcaDAO
  vista/               VentanaPrincipal, FormCliente, FormEmpleado, FormMarca
  util/Conexion.java   datos de conexion
```

## Crear la base de datos

Desde la carpeta del proyecto:

```
mysql -u root -p < db/script.sql
```

Esto crea la base `tienda_dao` con las tablas y 3 registros de prueba por tabla.

## Configurar la conexion

Editar las constantes de `src/main/java/util/Conexion.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/tienda_dao?useSSL=false&serverTimezone=UTC";
private static final String USUARIO = "root";
private static final String CLAVE = "root";
```

## Ejecutar

```
mvn compile exec:java
```

Se abre el menu principal con un boton por formulario. Cada formulario valida que no haya campos vacios, guarda con su DAO y muestra la lista actualizada. En Empleados el puesto se elige de un combo cargado desde la tabla `puestos`.

## Video

Enlace: _pendiente_
