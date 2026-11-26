# 🛒 Comercio Backend API

Sistema de gestión comercial RESTful desarrollado en Java con Spring Boot. La aplicación ofrece una solución integral para el control de stock, administración de catálogo y registro de ventas con trazabilidad multiusuario.

---

## 💡 Idea de Negocio y Alcance

Este proyecto está concebido como una plataforma de software para comercios (*SaaS* / Software como Servicio) organizada en tres pilares principales:

1. **Gestión Operativa de Comercio (Stock y Catálogo):**
   - Administración completa de productos categorizados y asociados a sus proveedores.
   - Control y actualización de stock en tiempo real tras cada venta.
   - Registro detallado de transacciones especificando forma de pago, cliente y totales.

2. **Arquitectura Multiusuario y Auditoría:**
   - **Administrador (SuperAdmin / Dev):** Gestión global de la plataforma y alta de comercios.
   - **Comercio (Cliente / Dueño):** Administración del local, productos, proveedores y reporte de ventas.
   - **Empleado (Cajero / Vendedor):** Asignación opcional por parte del dueño. Cada `Venta` queda vinculada al `Usuario` (empleado) que la procesó en caja, garantizando trazabilidad y auditoría.

3. **Integridad de Datos (Baja Lógica):**
   - Implementación del atributo `estado` (activo/inactivo) en entidades clave (`Producto`, `Categoria`, `Usuario`, `Cliente`, `Proveedor`) para mantener la integridad referencial y conservar el historial contable.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework Principal:** Spring Boot 3+
- **Persistencia de Datos:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL
- **Herramientas & Auxiliares:** Lombok, Maven
- **Pruebas de API:** Postman

---

## 📐 Modelo de Dominio (Entidades Clave)

- `Persona` (Clave abstracta `@MappedSuperclass` base para `Cliente` y `Empleado`)
- `Usuario` (Credenciales y asociación a empleado/rol)
- `Cliente` & `Proveedor`
- `Categoria` & `Producto`
- `Venta` & `DetalleVenta`

---

## 🚀 Estado del Proyecto

Actualmente el sistema cuenta con la estructura base de persistencia, Mappings DTO/Entities, servicios y controladores REST en proceso de construcción y testing de endpoints mediante Postman.
