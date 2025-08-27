CREATE DATABASE Servindustria;
USE Servindustria;

-- RRHH
CREATE TABLE user_account (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(150) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'EMPLOYEE', 'CLIENT')),
    CONSTRAINT UQ_user_account_email UNIQUE (email)
);	

CREATE TABLE cliente (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod NVARCHAR(45) NOT NULL,
    telefono1 NVARCHAR(45) NOT NULL,
    telefono2 NVARCHAR(45),
    tipodoc NVARCHAR(45) NOT NULL,
    numdoc NVARCHAR(60) NOT NULL,
    email NVARCHAR(150) NOT NULL,
    id_account BIGINT UNIQUE,
    tipo_cliente NVARCHAR(255) NOT NULL,
    FOREIGN KEY (id_account) REFERENCES user_account(id) ON DELETE CASCADE
);

CREATE TABLE natural_client (			
    id BIGINT PRIMARY KEY,				
    nombre NVARCHAR(255) NOT NULL,		
    apellido NVARCHAR(255) NOT NULL,	
    fecha_nacimiento DATE NOT NULL,		
    sexo NVARCHAR(10) NOT NULL CHECK (se('MALE', 'FEMALE', 'N')),
    FOREIGN KEY (id) REFERENCES cliente( DELETE CASCADE
);										
										
CREATE TABLE company_client (			
    id BIGINT PRIMARY KEY,				
    razon_social NVARCHAR(255) NOT NULL,
    direccion NVARCHAR(255) NOT NULL,	
    referencia NVARCHAR(255),			
    FOREIGN KEY (id) REFERENCES cliente( DELETE CASCADE
);										
										
CREATE TABLE empleado (					
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    codigo VARCHAR(45) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    document VARCHAR(45) NOT NULL,
    telephone VARCHAR(50) NOT NULL,
    address VARCHAR(150) NOT NULL,
    cargo VARCHAR(45) NOT NULL,
    fechacontrato DATETIME2 NOT NULL,
	id_account BIGINT UNIQUE,
	FOREIGN KEY (id_account) REFERENCES user_account(id) ON DELETE CASCADE

);

CREATE TABLE tecnico (
    id_tecnico BIGINT PRIMARY KEY,
    especialidad VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_tecnico) REFERENCES empleado(id)
);

CREATE TABLE almacenero (
    id_almacen BIGINT PRIMARY KEY,
    areaResponsable VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_almacen) REFERENCES empleado(id)
);

CREATE TABLE transportista (
    id_transportista BIGINT PRIMARY KEY,
    numlicencia VARCHAR(60),
    gradolicencia VARCHAR(45),
    FOREIGN KEY (id_transportista) REFERENCES empleado(id)
);

-- VENTAS

CREATE TABLE categoria (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE cliente (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    tipodoc VARCHAR(45) NOT NULL,
    numdoc VARCHAR(60) NOT NULL,
    nombre_razonsoc VARCHAR(60) NOT NULL,
    fechanaci DATETIME NOT NULL,
    sexo VARCHAR(45) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    referencia VARCHAR(255) NOT NULL,
    telefono1 VARCHAR(45) NOT NULL,
    telefono2 VARCHAR(45),
    email VARCHAR(150) NOT NULL
);

CREATE TABLE inventario (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    ubicacion VARCHAR(50) NOT NULL,
    stock_total INT NOT NULL
);


CREATE TABLE servicio (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(50) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    ciclo VARCHAR(45) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    metraje DECIMAL(10,2) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    id_categoria BIGINT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

CREATE TABLE producto (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    id_categoria BIGINT NOT NULL,
    id_inventario BIGINT,
    
    -- Atributos t�cnicos generales (opcional seg�n tipo de producto)
    alto VARCHAR(20),
    largo VARCHAR(20),
    fondo VARCHAR(20),
    marca VARCHAR(100),
    modelo VARCHAR(100),
	img varchar(50),
    aplicacion TEXT,
    efecto TEXT,
    contenido TEXT,  -- para art�culos contenidos (botiquines, kits)
    observaciones TEXT,
    
    FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id)
);

CREATE TABLE post_venta (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    tipo_servicio VARCHAR(45) NOT NULL,
    fechavcto DATETIME2 NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    estado VARCHAR(45) NOT NULL,
    cantidad INT NOT NULL,
    id_producto BIGINT NULL,
    id_servicio BIGINT NULL,
    id_cliente BIGINT NOT NULL,
	CONSTRAINT det_postventa_chk_1 CHECK (
        (id_producto IS NOT NULL AND id_servicio is null) OR
        (id_producto IS NULL AND id_servicio IS NOT NULL)
    ),
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE cotizacion (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) UNIQUE,
    fechaemi DATETIME NOT NULL,
    fechavenc DATETIME NOT NULL,
    estado VARCHAR(45) NOT NULL CHECK (estado IN ('EN_PROCESO', 'APROBADA', 'CANCELADA', 'RECHAZADA')),
	subtotal decimal(10,2) not null,
	dscto decimal(5,2) not null default 0,
    total DECIMAL(10,2) NOT NULL,
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);


CREATE TABLE det_cotizacion (
    id_det_cotizacion BIGINT IDENTITY(1,1) PRIMARY KEY,
    id_cotizacion BIGINT NOT NULL,
    id_producto BIGINT,
    id_servicio BIGINT,
    id_post_venta BIGINT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT det_cotizacion_chk_1 CHECK (
        (id_producto IS NOT NULL AND id_servicio IS NULL AND id_post_venta IS NULL) OR
        (id_producto IS NULL AND id_servicio IS NOT NULL AND id_post_venta IS NULL) OR
        (id_producto IS NULL AND id_servicio IS NULL AND id_post_venta IS NOT NULL)
    ),
    FOREIGN KEY (id_cotizacion) REFERENCES cotizacion(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id),
    FOREIGN KEY (id_post_venta) REFERENCES post_venta(id)
);

CREATE TABLE empresa (
    cod VARCHAR(45) PRIMARY KEY,
    ruc VARCHAR(60) NOT NULL,
    nombre VARCHAR(80) NOT NULL
);

CREATE TABLE orden_servicio (
    id_orden BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    dias bigint not null,
    fechemi datetime not null, 
    fechaentr DATETIME NOT NULL,
    estado VARCHAR(45) NOT NULL CHECK (estado IN ('EN_PROCESO', 'APROBADA', 'CANCELADA', 'RECHAZADA')),
    id_cotizacion BIGINT NOT NULL,
	CONSTRAINT UQ_ordenservicio_idCotizacion UNIQUE(id_cotizacion),
    FOREIGN KEY (id_cotizacion) REFERENCES cotizacion(id)
);

CREATE TABLE guia_remision (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    fechaemi DATETIME NOT NULL,
    cod_empresa VARCHAR(45) NOT NULL,
    id_transportista BIGINT NOT NULL,
    id_almacenero BIGINT NOT NULL,
    id_orden BIGINT NOT NULL,
	CONSTRAINT UQ_guiaremision_idOrden UNIQUE(id_orden),
    FOREIGN KEY (cod_empresa) REFERENCES empresa(cod),
    FOREIGN KEY (id_transportista) REFERENCES transportista(id_transportista),
    FOREIGN KEY (id_almacenero) REFERENCES almacenero(id_almacen),
    FOREIGN KEY (id_orden) REFERENCES orden_servicio(id_orden)
);

CREATE TABLE factura (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) UNIQUE,
    fechaemi DATETIME NOT NULL,
	subtotal decimal(10,2) not null,
	dscto decimal(5,2) not null default 0,
    total DECIMAL(10,2) NOT NULL,
    tipopago VARCHAR(60) NOT NULL,
    modopago VARCHAR(60) NOT NULL,
    montopago DECIMAL(10,2) not null,
    saldopendiente DECIMAL(10,2) not null,
    id_cotizacion BIGINT,
    id_guia BIGINT,
    id_empleado BIGINT NOT NULL,
    id_cliente BIGINT NOT NULL,
	CONSTRAINT UQ_factura_idCotizacion UNIQUE(id_cotizacion),
    CONSTRAINT UQ_factura_idGuia UNIQUE(id_guia),
    FOREIGN KEY (id_cotizacion) REFERENCES cotizacion(id),
    FOREIGN KEY (id_guia) REFERENCES guia_remision(id),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE det_factura (
    id_det_factura BIGINT IDENTITY(1,1) PRIMARY KEY,
    id_factura BIGINT NOT NULL,
    id_producto BIGINT,
    id_servicio BIGINT,
    id_post_venta BIGINT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT det_factura_chk_1 CHECK (
        (id_producto IS NOT NULL AND id_servicio IS NULL AND id_post_venta IS NULL) OR
        (id_producto IS NULL AND id_servicio IS NOT NULL AND id_post_venta IS NULL) OR
        (id_producto IS NULL AND id_servicio IS NULL AND id_post_venta IS NOT NULL)
    ),
    FOREIGN KEY (id_factura) REFERENCES factura(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id),
    FOREIGN KEY (id_post_venta) REFERENCES post_venta(id)
);

-- POSTVENTA

CREATE TABLE seguimiento_postventa (
    id_seguimiento BIGINT IDENTITY(1,1) PRIMARY KEY,
    fecha_realizacion DATETIME NOT NULL,
    observacion VARCHAR(150) NOT NULL,
    estado VARCHAR(45) NOT NULL,
    id_post_venta BIGINT NOT NULL,
    id_tecnico BIGINT NOT NULL,
	CONSTRAINT UQ_seguimiento_idPostventa UNIQUE(id_post_venta),
    FOREIGN KEY (id_post_venta) REFERENCES post_venta(id),
    FOREIGN KEY (id_tecnico) REFERENCES tecnico(id_tecnico)
);

-- LOG�STICA

CREATE TABLE proveedor (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    razonsocial VARCHAR(100) NOT NULL,
    ruc VARCHAR(50) NOT NULL,
    direccion VARCHAR(60) NOT NULL,
    telefono1 VARCHAR(50) NOT NULL,
    telefono2 VARCHAR(50),
    email VARCHAR(150) NOT NULL
);

CREATE TABLE insumo (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    nombre VARCHAR(60) NOT NULL,
    stock INTEGER NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
x    id_inventario BIGINT NOT NULL,
    FOREIGN KEY (id_inventario) REFERENCES inventario(id)
);


CREATE TABLE orden_compra (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    modopago VARCHAR(50) NOT NULL,
    tipopago VARCHAR(50) NOT NULL,
    saldopendiente DECIMAL(10,2),
    fechaped DATETIME NOT NULL,
    fechaentr DATETIME NOT NULL,
    estado VARCHAR(45) NOT NULL CHECK (estado IN ('EN_PROCESO', 'APROBADA', 'CANCELADA', 'RECHAZADA')),
    total DECIMAL(10,2) NOT NULL,
    id_proveedor BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id)
);

CREATE TABLE det_orden_compra (
    id_det_orden BIGINT IDENTITY(1,1) PRIMARY KEY,
    id_orden BIGINT NOT NULL,
    id_producto BIGINT,
    id_insumo BIGINT,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT det_ordencompra_chk_1 CHECK (
        (id_producto IS NOT NULL AND id_insumo IS NULL) OR
        (id_producto IS NULL AND id_insumo IS NOT NULL)
    ),
    FOREIGN KEY (id_orden) REFERENCES orden_compra(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_insumo) REFERENCES insumo(id)
);

CREATE TABLE ingreso_mercaderia (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cod VARCHAR(45) NOT NULL UNIQUE,
    fecha_ingreso DATETIME NOT NULL,
    observaciones VARCHAR(100) NOT NULL,
    id_orden BIGINT NOT NULL,
	CONSTRAINT UQ_ingresomercaderia_idOrden UNIQUE(id_orden),
    FOREIGN KEY (id_orden) REFERENCES orden_compra(id)
);

CREATE TABLE det_ingreso_mercaderia (
    id_det_ingreso BIGINT IDENTITY(1,1) PRIMARY KEY,
    id_ingreso BIGINT NOT NULL,
    id_producto BIGINT,
    id_insumo BIGINT,
    cantidad INTEGER NOT NULL,
    CONSTRAINT det_ingresomercaderia_chk_1 CHECK (
        (id_producto IS NOT NULL AND id_insumo IS NULL) OR
        (id_producto IS NULL AND id_insumo IS NOT NULL)
    ),
    FOREIGN KEY (id_ingreso) REFERENCES ingreso_mercaderia(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_insumo) REFERENCES insumo(id)
);