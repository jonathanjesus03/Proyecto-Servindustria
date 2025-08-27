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



INSERT INTO categoria (nombre) VALUES
('EXTINTORES'),
('ALARMAS'),
('BOTIQUINES'),
('SE�ALES'),
('LUZ DE EMERGENCIA'),		
('CONFECCION DE PLANOS'),
('CERTIFICACIONES'),
('CAPACITACIONES');
go

INSERT INTO inventario (cod, ubicacion, stock_total) VALUES 
('INV-001', 'Almac�n Central', 100);
go




INSERT INTO producto (
    cod, tipo, descripcion, stock, precio, id_categoria, id_inventario,
    alto, largo, fondo, marca, modelo, img,
    aplicacion, efecto, contenido, observaciones
) VALUES(
    'EXT01', 'Extintor PQS 1 KG', 'Extintor port�til PQS ABC 1kg', 30, 45.00, 1, 1,
    '30 cm', '12 cm', '9 cm', 'GEN�RICO', 'PQS-1KG', null ,
    'El Polvo Qu�mico Seco se descompone por calor, paraliza la reacci�n en cadena. Eficaz en incendios A, B y C.',
    'Fosfato monoam�nico. Interrumpe la combusti�n.', '1 kg PQS / Rating: 1A-10B:C', 
    'Procedencia: China. Presi�n: 195 bar. Tiempo descarga: 10s. Soporte vehicular incluido.'
),(
    'EXT02', 'Extintor PQS 2 KG', 'Extintor port�til PQS ABC 2kg', 25, 60.00, 1, 1,
    '35 cm', '14 cm', '10 cm', 'GEN�RICO', 'PQS-2KG', null,
    '�til en oficinas, veh�culos, almacenes. Polvo seco interrumpe la reacci�n en cadena.', 
    'Fosfato monoam�nico. Efectivo en fuegos tipo A-B-C.', 
    '2 kg PQS / Rating: 2A-20B:C', 
    'Presi�n: 195 bar. Tiempo descarga: 12s. Incluye soporte met�lico.'
),(
    'EXT03', 'Extintor PQS 4 KG', 'Extintor port�til PQS ABC 4kg', 20, 75.00, 1, 1,
    '40 cm', '16 cm', '12 cm', 'GEN�RICO', 'PQS-4KG', null,
    'Aplicaci�n vers�til en f�bricas, oficinas y centros comerciales.',
    'Fosfato monoam�nico. Supresi�n r�pida de incendios tipo A-B-C.',
    '4 kg PQS / Rating: 3A-30B:C', 
    'Presi�n de trabajo: 195 bar. Tiempo descarga: 14s. Incluye man�metro.'
),(
    'EXT04', 'Extintor PQS 6 KG', 'Extintor port�til PQS ABC 6kg', 18, 85.00, 1, 1,
    '50 cm', '18 cm', '14 cm', 'GEN�RICO', 'PQS-6KG', null,
    'Usado com�nmente en instalaciones industriales y comerciales.',
    'Fosfato monoam�nico de alta eficiencia. Fuegos tipo A-B-C.',
    '6 kg PQS / Rating: 4A-40B:C', 
    'Tiempo descarga: 16s. Incluye v�lvula con man�metro y soporte.'
),  (
    'EXT05', 'Extintor PQS 9 KG', 'Extintor port�til PQS ABC 9kg', 12, 95.00, 1, 1,
    '60 cm', '20 cm', '16 cm', 'GEN�RICO', 'PQS-9KG', null,
    'Indicado para grandes espacios industriales y almacenes de alto riesgo.',
    'Agente seco interrumpe la combusti�n de fuegos A, B y C.',
    '9 kg PQS / Rating: 6A-60B:C', 
    'Tiempo descarga: 18s. Base con ruedas opcional.'
), (
    'EXT06', 'Extintor PQS 12 KG', 'Extintor port�til PQS ABC 12kg', 8, 120.00, 1, 1,
    '65 cm', '22 cm', '18 cm', 'GEN�RICO', 'PQS-12KG', null,
    'Recomendado para plantas de producci�n, almacenes y centros log�sticos.',
    'Fosfato monoam�nico de amplio alcance. Fuegos clase A-B-C.',
    '12 kg PQS / Rating: 8A-80B:C', 
    'Tiempo descarga: 20s. Incluye carro y manguera flexible.'
),(
    'EXT07', 'Extintor PQS 25 KG', 'Extintor rodante PQS ABC 25kg', 5, 210.00, 1, 1,
    '80 cm', '30 cm', '25 cm', 'GEN�RICO', 'PQS-25KG',null,
    'Ideal para zonas industriales amplias con alto riesgo de incendio.',
    'PQS presurizado. R�pida supresi�n de fuego clase A-B-C.',
    '25 kg PQS / Rating: 10A-100B:C', 
    'Incluye ruedas y manguera de descarga. Presi�n 195 bar.'
), (
    'EXT08', 'Extintor PQS 50 KG', 'Extintor rodante PQS ABC 50kg', 3, 350.00, 1, 1,
    '100 cm', '40 cm', '35 cm', 'GEN�RICO', 'PQS-50KG', null,
    'Dise�ado para grandes industrias, almacenes y estaciones de servicio.',
    'Fosfato monoam�nico de alto volumen. Supresi�n r�pida y potente.',
    '50 kg PQS / Rating: 20A-160B:C', 
    'Ruedas industriales, lanza de descarga reforzada. Tiempo descarga: 25s.'
), (
    'EXT09', 'Extintor UL PQS 5 LBS', 'Extintor port�til UL PQS 5LBS ABC', 10, 90.00, 1, 1,
    '53.3 cm', '19.7 cm', '13 cm', '25614', 'UL-PQS-5', null,
    'Ideal para grifos, almacenes, transporte de hidrocarburos y dep�sitos de pinturas. No usar en equipos electr�nicos.', 
    'Fosfato Monomonico al 90% UL. Fuegos clase A-B-C. Alta efectividad.',
    '5 LBS / Rating: 3A-40B:C / Temperatura: -54�C a 49�C / Presi�n trabajo: 195 psi',
    'Tiempo de descarga: 14 seg. Incluye soporte de pared.'
), (
    'EXT10', 'Extintor UL PQS 10 LBS', 'Extintor port�til UL PQS 10LBS ABC', 8, 135.00, 1, 1,
    '54 cm', '20 cm', '13 cm', '#11340', 'UL-PQS-10', null,
    'Protecci�n en estaciones de servicios, refiner�as, unidades de transporte de GLP/GNV, etc. No recomendado para electr�nicos.', 
    'Fosfato Monomonico al 90% UL. Extingue fuegos clase A-B-C.',
    '10 LBS / Rating: 4A-80B:C / Temperatura: -54�C a 49�C / Presi�n trabajo: 195 psi',
    'Descarga: 22 seg. Alcance chorro: 4.6 � 6.4 m. V�lvula y manija de aluminio. Incluye soporte pared.'
),  ('ALR001', 'Alarma DSC', 'Central de alarma DSC completo con tarjeta, teclado y transformador', 5, 550.00, 2, 1,
'20 cm', '30 cm', '10 cm', 'DSC', 'DSC-AB', null,
'Sistema de detecci�n de incendios con certificaci�n UL. Detecta tempranamente y genera se�ales de alarma.',
'Incluye relay, teclado, transformador y tarjeta. Ideal para todo tipo de inmuebles.',
'Sistema completo de alarma', 'Uso profesional en detecci�n temprana de incendios.'), 
('ALR002', 'Sensor De Humo', 'Sensor de humo fotoel�ctrico de 4 hilos', 20, 85.00, 2, 1,
'5 cm', '12 cm', '12 cm', 'Mircom', 'SH-4H', null,
'Oficinas, viviendas, �reas comunes con altura m�xima de 2.50 m.',
'Detecci�n de humo fotoel�ctrico. Salida de rel� N/C o N/O.',
'Voltaje 12/24 Vdc. F�cil mantenimiento. UL Listed.',
'Carcasa atractiva y duradera.'), 
('ALR003', 'Sensor De Temperatura', 'Sensor de temperatura con auto restauraci�n', 15, 88.00, 2, 1,
'5 cm', '12 cm', '12 cm', 'Mircom', 'ST-4H', null,
'Laboratorios, cocinas, cocheras, talleres. Altura m�xima 2.50 m.',
'Detecci�n t�rmica precisa con montaje en caja octagonal.',
'Voltaje 12/24 Vdc. Listado ULC.', 'Exterior blanco de perfil bajo.'),
('ALR004', 'Pulsador Manual', 'Pulsador manual simple acci�n tipo palanca', 25, 65.00, 2, 1,
'10 cm', '12 cm', '5 cm', 'GEN', 'PM-SP', null,
'Evacuaciones, robos, primeros auxilios.',
'M�dulo direccionable, convertible a doble acci�n.',
'10A, 125VAC. Montaje en caja el�ctrica simple.',
'Rojo con palanca blanca, aluminio esmaltado.'),
('ALR005', 'Pulsador Manual Dual', 'Pulsador manual con llave y acci�n dual', 20, 75.00, 2, 1,
'10 cm', '12 cm', '6 cm', 'GEN', 'PM-LL', null,
'Evacuaci�n, robo o primeros auxilios. Uso en interiores o intemperie.',
'M�dulo direccionable con llave de reseteo.',
'Compatible con caja BB-700 y BB-700WP.', 'Acabado rojo esmaltado.'),
('ALR006', 'Luz Estrobosc�pica', 'Luz estrobosc�pica con flasher y campana', 18, 98.00, 2, 1,
'8 cm', '12 cm', '8 cm', 'Sensor System', 'SS-1224', 'luz_estroboscopica.jpg',
'Oficinas, �reas comunes, lugares con ruido.',
'Alarma visual y sonora. 101 dB @ 3m. Bajo consumo.',
'Voltaje 12VDC.', 'Funciona junto con el pulsador.'),
('ALR007', 'Rollo cable FPL 4x18', 'Rollo cable FPL 4x18 AWG para incendio', 10, 360.00, 2, 1,
'N/A', '300 m', 'N/A', 'Honeywell', 'FPL-418', null,
'Instalaciones de sistemas contra incendios.',
'Flexible, resistente a propagaci�n de fuego.',
'Caja de 300 m. Cobre s�lido.', 'Certificado UL/NEC.'), 
('ALR008', 'Rollo Cable FPL 4x22', 'Rollo cable FPL 4x22 AWG para incendio', 10, 310.00, 2, 1,
'N/A', '300 m', 'N/A', 'Honeywell', 'FPL-422', null,
'Instalaciones de sistemas contra incendios.',
'Flexible, resistente a propagaci�n de fuego.',
'Caja de 300 m. Cobre s�lido.', 'Certificado UL/NEC.'), 
('ALR009', 'Sirena DSC 12V', 'Sirena DSC 12V 30W con certificaci�n UL', 12, 115.00, 2, 1,
'10 cm', '15 cm', '8 cm', 'DSC', 'SIR-12V30', null,
'Alerta en detecci�n de humo v�a central.',
'120dB por metro. Pl�stico de alta durabilidad.',
'2 tonos. 30W. 12Vcd 1200 mA.', 'F�cil montaje.'),
('ALR010', 'Sensor De Humo Fotobeam', 'Sensor de humo tipo rayo (fotobeam)', 5, 425.00, 2, 1,
'25.4 cm', '19.1 cm', '8.4 cm', 'System Sensor', 'BEAM1224', null,
'�reas amplias: 5�100 metros de cobertura.',
'Indicadores LED de alarma y falla. Certificaci�n UL/FM.',
'Voltaje 10.2 a 32 VDC. �ngulo regulable.', 'Temp: �30�C a 55�C. Detecci�n precisa.'), 
('B001', 'Botiquin PVC Tipo Maletin', 'Botiqu�n de PVC compacto con medicina', 10, 45.00, 3, 1,
'22', '21', '9', 'Gen�rico', 'PVC-M', NULL, 'Uso en oficinas, hogares y veh�culos', 'Atenci�n de emergencias menores', 'Incluye medicamentos b�sicos', 'Ligero y port�til'),
('B002', 'Botiquin PVC Maletin Grande', 'Botiqu�n de PVC con art�culos de primeros auxilios', 8, 85.00, 3, 1,
'32', '21', '9', 'Gen�rico', 'PVC-GR', NULL, 'Empresas, colegios, veh�culos', 'Atenci�n de emergencias', 
'Venda, tijera, alcohol, guantes, algod�n, esparadrapo, gasa, ap�sitos, jab�n', 'Completo para uso institucional'),
('B003', 'Kit Antiderrame', 'Kit de emergencia para derrames qu�micos o industriales', 5, 120.00, 3, 1,
'45', '29', '13', 'Gen�rico', 'KIT-AD', NULL, 'Industrias, talleres, almacenes', 'Control y contenci�n de derrames', 
'Absorbentes, guantes, mascarilla, bolsa de residuos', 'Recomendado en zonas de riesgo qu�mico'),
('B004', 'Camilla de PVC', 'Camilla r�gida de PVC con correas de sujeci�n', 3, 280.00, 3, 1,
'184', '45', '5', 'Gen�rico', 'CML-PVC', NULL, 'Hospitales, ambulancias, centros laborales', 'Traslado seguro de personas heridas', 
'Estructura PVC, 3 correas de nylon', 'Resistente, lavable y ligera'),
('B005', 'Botiquin de Lona 20x16x6', 'Botiqu�n port�til de lona peque�o', 12, 30.00, 3, 1,
'20', '16','6', 'Gen�rico', 'LN-2016', NULL, 'Hogar, auto, unidades m�viles', 'Atenci�n r�pida de emergencias', 
'Vac�o o con insumos b�sicos', 'Compacto y f�cil de transportar'),
('B006', 'Botiquin de Lona 31x27x16', 'Botiqu�n de lona de mayor capacidad', 10, 55.00, 3, 1,
'31', '27', '16', 'Gen�rico', 'LN-3127', NULL, 'Empresas, instituciones educativas', 'Primeros auxilios generales', 
'Incluye varios compartimentos', 'Resistente y con asa de transporte'),
('B007', 'Mochila de Emergencia Roja', 'Mochila equipada para situaciones de emergencia', 6, 150.00, 3, 1,
'45', '34', '9', 'Gen�rico', 'EM-RJ', NULL, 'Hogares, empresas, centros educativos', 'Respuesta inmediata a emergencias', 
'Linterna, radio, botiqu�n, cuchilla, manta, mascarilla', 'Debe estar en lugar visible y accesible'), 
('S001', 'Se�al Vinil Normal', 'Se�ales de seguridad impresas en vinil normal', 100, 5.00, 4, 1,
'20', '30', '0', 'Gen�rico', 'VIN-N', NULL, 'Identificaci�n y se�alizaci�n de seguridad', 'Visibilidad est�ndar', 
'Material vinil adhesivo', 'Dise�os variados: salida, extintor, emergencia, etc.'),
('S002', 'Se�al Vinil Fotoluminiscente', 'Se�ales de seguridad en vinil fotoluminiscente o con base celtex', 80, 12.00, 4, 1,
'20', '30', '0', 'Gen�rico', 'VIN-FL', NULL, 'Zonas con baja iluminaci�n o evacuaci�n nocturna', 'Brillan en la oscuridad', 
'Vinil fotoluminiscente o base celtex', 'Alta visibilidad incluso sin luz'), 
('E001', 'Luz De Emergencia Opalux 9909.220', 'Luz de emergencia con 24 LED ultrabrillantes, luz blanca 6500K. Puede montarse en techo o pared. Ideal para apagones.', 10, 45.0, 5, 1,'5', '20', '6', 'OPALUX', '9909.220', NULL, 'Montaje en pared o techo', 'Luz blanca 6500K, 24 LED ultrabrillante', 'Bater�a Nickel-cadmium 3.6v 1000mAh', 'Tiempo de operaci�n 90 min'),
('E002', 'Luz De Emergencia Opalux HB-8667T', 'Luz compacta de emergencia con 16 LED SMD. Proporciona hasta 12 horas de luz continua. Incluye cable de carga.', 15, 38.0, 5, 1, '5', '18',' 5', 'OPALUX', 'HB-8667T', NULL, 'Emergencias generales', '12 horas, 16 LED SMD', 'Bater�a 4V 4AH', 'Incluye cable de carga 220v'),
('E003', 'Luz De Emergencia Phelix LED3048A', 'Luminaria LED de emergencia con 16 LED y 1.5 horas de autonom�a. Dise�o compacto y eficiente para interiores.', 12, 40.0, 5, 1, '6', '25', '6', 'PHELIX', 'LED3048A', NULL, 'Emergencias en edificios', '16 LEDs, 2x2 watts', 'Bater�a integrada', 'Autonom�a 1.5 horas'),
('E004', 'Leds De Salida Opalux', 'Luminaria de emergencia con se�al �SALIDA� en bajo relieve, ideal para salidas en oficinas e instituciones. Duraci�n hasta 6 horas.', 20, 50.0, 5, 1, '22', '35', '6', 'OPALUX', 'SALIDA', NULL, 'Salidas de emergencia', 'LED, bater�a Ni-Cd', 'Bater�a 6h', 'Incluye accesorios de montaje'),
('E005', 'Luz Halogenada Opalux 9808UL', 'Potente luz hal�gena de emergencia con 2 faros LED de 20W. Cubre �reas amplias (200 m�). Ideal para industrias.', 8, 65.0, 5, 1, '25', '30', '12', 'OPALUX', '9808UL', NULL, 'Industria y construcciones', '2 faros LED 20W', 'Bater�a 2V 7Ah', '�rea cubierta 200 m�'),
('E006', 'Luz Slim Opalux 9101.220', 'Luz de emergencia tipo slim con 14 LED ultra brillantes. Autonom�a de 8 horas. F�cil instalaci�n en interiores.', 14, 42.0, 5, 1, '5', '20', '5', 'OPALUX', '9101.220', NULL, 'Oficinas, hogares', '14 LED SMD', 'Bater�a 4V 2.5Ah', 'Autonom�a 8 horas'),
('E007', 'Luz De Emergencia Opalux HB-890T', 'Luz port�til de alta duraci�n con 28 LED SMD. Funciona hasta 15 horas. Recomendado para lugares sin acceso inmediato a energ�a.', 10, 55.0, 5, 1, '6', '22', '6', 'OPALUX', 'HB-890T', NULL, 'Emergencias generales', '28 LED SMD', 'Bater�a 6V 4AH', 'Duraci�n 15 horas'),
('E008', 'Luz De Emergencia Phelix LED 3038A', 'L�mpara de emergencia econ�mica con doble luz, f�cil instalaci�n y bot�n probador. Autonom�a de hasta 8 horas.', 10, 48.0, 5, 1, '6', '20',' 5', 'PHELIX', 'LED 3038A', NULL, 'Emergencias en empresas', 'Doble luz, alto rendimiento', 'Bater�a integrada', 'Bot�n testeador, 8 horas');
