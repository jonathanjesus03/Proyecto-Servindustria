/*INSERTS*/

use NegocioWebIntegrado

INSERT INTO categoria (nombre) VALUES
('EXTINTORES'),
('ALARMAS'),
('BOTIQUINES'),
('SEÑALES'),
('LUZ DE EMERGENCIA'),		
('CONFECCION DE PLANOS'),
('CERTIFICACIONES'),
('CAPACITACIONES');
go

INSERT INTO inventario (cod, ubicacion, stock_total) VALUES 
('INV-001', 'Almacén Central', 100);
go

INSERT INTO producto (
    cod, tipo, descripcion, stock, precio, id_categoria, id_inventario,
    alto, largo, fondo, marca, modelo, img,
    aplicacion, efecto, contenido, observaciones
) VALUES(
    'EXT01', 'Extintor PQS 1 KG', 'Extintor portátil PQS ABC 1kg', 30, 45.00, 1, 1,
    '30 cm', '12 cm', '9 cm', 'GENÉRICO', 'PQS-1KG', null ,
    'El Polvo Químico Seco se descompone por calor, paraliza la reacción en cadena. Eficaz en incendios A, B y C.',
    'Fosfato monoamónico. Interrumpe la combustión.', '1 kg PQS / Rating: 1A-10B:C', 
    'Procedencia: China. Presión: 195 bar. Tiempo descarga: 10s. Soporte vehicular incluido.'
),(
    'EXT02', 'Extintor PQS 2 KG', 'Extintor portátil PQS ABC 2kg', 25, 60.00, 1, 1,
    '35 cm', '14 cm', '10 cm', 'GENÉRICO', 'PQS-2KG', null,
    'Útil en oficinas, vehículos, almacenes. Polvo seco interrumpe la reacción en cadena.', 
    'Fosfato monoamónico. Efectivo en fuegos tipo A-B-C.', 
    '2 kg PQS / Rating: 2A-20B:C', 
    'Presión: 195 bar. Tiempo descarga: 12s. Incluye soporte metálico.'
),(
    'EXT03', 'Extintor PQS 4 KG', 'Extintor portátil PQS ABC 4kg', 20, 75.00, 1, 1,
    '40 cm', '16 cm', '12 cm', 'GENÉRICO', 'PQS-4KG', null,
    'Aplicación versátil en fábricas, oficinas y centros comerciales.',
    'Fosfato monoamónico. Supresión rápida de incendios tipo A-B-C.',
    '4 kg PQS / Rating: 3A-30B:C', 
    'Presión de trabajo: 195 bar. Tiempo descarga: 14s. Incluye manómetro.'
),(
    'EXT04', 'Extintor PQS 6 KG', 'Extintor portátil PQS ABC 6kg', 18, 85.00, 1, 1,
    '50 cm', '18 cm', '14 cm', 'GENÉRICO', 'PQS-6KG', null,
    'Usado comúnmente en instalaciones industriales y comerciales.',
    'Fosfato monoamónico de alta eficiencia. Fuegos tipo A-B-C.',
    '6 kg PQS / Rating: 4A-40B:C', 
    'Tiempo descarga: 16s. Incluye válvula con manómetro y soporte.'
),  (
    'EXT05', 'Extintor PQS 9 KG', 'Extintor portátil PQS ABC 9kg', 12, 95.00, 1, 1,
    '60 cm', '20 cm', '16 cm', 'GENÉRICO', 'PQS-9KG', null,
    'Indicado para grandes espacios industriales y almacenes de alto riesgo.',
    'Agente seco interrumpe la combustión de fuegos A, B y C.',
    '9 kg PQS / Rating: 6A-60B:C', 
    'Tiempo descarga: 18s. Base con ruedas opcional.'
), (
    'EXT06', 'Extintor PQS 12 KG', 'Extintor portátil PQS ABC 12kg', 8, 120.00, 1, 1,
    '65 cm', '22 cm', '18 cm', 'GENÉRICO', 'PQS-12KG', null,
    'Recomendado para plantas de producción, almacenes y centros logísticos.',
    'Fosfato monoamónico de amplio alcance. Fuegos clase A-B-C.',
    '12 kg PQS / Rating: 8A-80B:C', 
    'Tiempo descarga: 20s. Incluye carro y manguera flexible.'
),(
    'EXT07', 'Extintor PQS 25 KG', 'Extintor rodante PQS ABC 25kg', 5, 210.00, 1, 1,
    '80 cm', '30 cm', '25 cm', 'GENÉRICO', 'PQS-25KG',null,
    'Ideal para zonas industriales amplias con alto riesgo de incendio.',
    'PQS presurizado. Rápida supresión de fuego clase A-B-C.',
    '25 kg PQS / Rating: 10A-100B:C', 
    'Incluye ruedas y manguera de descarga. Presión 195 bar.'
), (
    'EXT08', 'Extintor PQS 50 KG', 'Extintor rodante PQS ABC 50kg', 3, 350.00, 1, 1,
    '100 cm', '40 cm', '35 cm', 'GENÉRICO', 'PQS-50KG', null,
    'Diseñado para grandes industrias, almacenes y estaciones de servicio.',
    'Fosfato monoamónico de alto volumen. Supresión rápida y potente.',
    '50 kg PQS / Rating: 20A-160B:C', 
    'Ruedas industriales, lanza de descarga reforzada. Tiempo descarga: 25s.'
), (
    'EXT09', 'Extintor UL PQS 5 LBS', 'Extintor portátil UL PQS 5LBS ABC', 10, 90.00, 1, 1,
    '53.3 cm', '19.7 cm', '13 cm', '25614', 'UL-PQS-5', null,
    'Ideal para grifos, almacenes, transporte de hidrocarburos y depósitos de pinturas. No usar en equipos electrónicos.', 
    'Fosfato Monomonico al 90% UL. Fuegos clase A-B-C. Alta efectividad.',
    '5 LBS / Rating: 3A-40B:C / Temperatura: -54°C a 49°C / Presión trabajo: 195 psi',
    'Tiempo de descarga: 14 seg. Incluye soporte de pared.'
), (
    'EXT10', 'Extintor UL PQS 10 LBS', 'Extintor portátil UL PQS 10LBS ABC', 8, 135.00, 1, 1,
    '54 cm', '20 cm', '13 cm', '#11340', 'UL-PQS-10', null,
    'Protección en estaciones de servicios, refinerías, unidades de transporte de GLP/GNV, etc. No recomendado para electrónicos.', 
    'Fosfato Monomonico al 90% UL. Extingue fuegos clase A-B-C.',
    '10 LBS / Rating: 4A-80B:C / Temperatura: -54°C a 49°C / Presión trabajo: 195 psi',
    'Descarga: 22 seg. Alcance chorro: 4.6 – 6.4 m. Válvula y manija de aluminio. Incluye soporte pared.'
),  ('ALR001', 'Alarma DSC', 'Central de alarma DSC completo con tarjeta, teclado y transformador', 5, 550.00, 2, 1,
'20 cm', '30 cm', '10 cm', 'DSC', 'DSC-AB', null,
'Sistema de detección de incendios con certificación UL. Detecta tempranamente y genera señales de alarma.',
'Incluye relay, teclado, transformador y tarjeta. Ideal para todo tipo de inmuebles.',
'Sistema completo de alarma', 'Uso profesional en detección temprana de incendios.'), 
('ALR002', 'Sensor De Humo', 'Sensor de humo fotoeléctrico de 4 hilos', 20, 85.00, 2, 1,
'5 cm', '12 cm', '12 cm', 'Mircom', 'SH-4H', null,
'Oficinas, viviendas, áreas comunes con altura máxima de 2.50 m.',
'Detección de humo fotoeléctrico. Salida de relé N/C o N/O.',
'Voltaje 12/24 Vdc. Fácil mantenimiento. UL Listed.',
'Carcasa atractiva y duradera.'), 
('ALR003', 'Sensor De Temperatura', 'Sensor de temperatura con auto restauración', 15, 88.00, 2, 1,
'5 cm', '12 cm', '12 cm', 'Mircom', 'ST-4H', null,
'Laboratorios, cocinas, cocheras, talleres. Altura máxima 2.50 m.',
'Detección térmica precisa con montaje en caja octagonal.',
'Voltaje 12/24 Vdc. Listado ULC.', 'Exterior blanco de perfil bajo.'),
('ALR004', 'Pulsador Manual', 'Pulsador manual simple acción tipo palanca', 25, 65.00, 2, 1,
'10 cm', '12 cm', '5 cm', 'GEN', 'PM-SP', null,
'Evacuaciones, robos, primeros auxilios.',
'Módulo direccionable, convertible a doble acción.',
'10A, 125VAC. Montaje en caja eléctrica simple.',
'Rojo con palanca blanca, aluminio esmaltado.'),
('ALR005', 'Pulsador Manual Dual', 'Pulsador manual con llave y acción dual', 20, 75.00, 2, 1,
'10 cm', '12 cm', '6 cm', 'GEN', 'PM-LL', null,
'Evacuación, robo o primeros auxilios. Uso en interiores o intemperie.',
'Módulo direccionable con llave de reseteo.',
'Compatible con caja BB-700 y BB-700WP.', 'Acabado rojo esmaltado.'),
('ALR006', 'Luz Estroboscópica', 'Luz estroboscópica con flasher y campana', 18, 98.00, 2, 1,
'8 cm', '12 cm', '8 cm', 'Sensor System', 'SS-1224', 'luz_estroboscopica.jpg',
'Oficinas, áreas comunes, lugares con ruido.',
'Alarma visual y sonora. 101 dB @ 3m. Bajo consumo.',
'Voltaje 12VDC.', 'Funciona junto con el pulsador.'),
('ALR007', 'Rollo cable FPL 4x18', 'Rollo cable FPL 4x18 AWG para incendio', 10, 360.00, 2, 1,
'N/A', '300 m', 'N/A', 'Honeywell', 'FPL-418', null,
'Instalaciones de sistemas contra incendios.',
'Flexible, resistente a propagación de fuego.',
'Caja de 300 m. Cobre sólido.', 'Certificado UL/NEC.'), 
('ALR008', 'Rollo Cable FPL 4x22', 'Rollo cable FPL 4x22 AWG para incendio', 10, 310.00, 2, 1,
'N/A', '300 m', 'N/A', 'Honeywell', 'FPL-422', null,
'Instalaciones de sistemas contra incendios.',
'Flexible, resistente a propagación de fuego.',
'Caja de 300 m. Cobre sólido.', 'Certificado UL/NEC.'), 
('ALR009', 'Sirena DSC 12V', 'Sirena DSC 12V 30W con certificación UL', 12, 115.00, 2, 1,
'10 cm', '15 cm', '8 cm', 'DSC', 'SIR-12V30', null,
'Alerta en detección de humo vía central.',
'120dB por metro. Plástico de alta durabilidad.',
'2 tonos. 30W. 12Vcd 1200 mA.', 'Fácil montaje.'),
('ALR010', 'Sensor De Humo Fotobeam', 'Sensor de humo tipo rayo (fotobeam)', 5, 425.00, 2, 1,
'25.4 cm', '19.1 cm', '8.4 cm', 'System Sensor', 'BEAM1224', null,
'Áreas amplias: 5–100 metros de cobertura.',
'Indicadores LED de alarma y falla. Certificación UL/FM.',
'Voltaje 10.2 a 32 VDC. Ángulo regulable.', 'Temp: –30°C a 55°C. Detección precisa.'), 
('B001', 'Botiquin PVC Tipo Maletin', 'Botiquín de PVC compacto con medicina', 10, 45.00, 3, 1,
'22', '21', '9', 'Genérico', 'PVC-M', NULL, 'Uso en oficinas, hogares y vehículos', 'Atención de emergencias menores', 'Incluye medicamentos básicos', 'Ligero y portátil'),
('B002', 'Botiquin PVC Maletin Grande', 'Botiquín de PVC con artículos de primeros auxilios', 8, 85.00, 3, 1,
'32', '21', '9', 'Genérico', 'PVC-GR', NULL, 'Empresas, colegios, vehículos', 'Atención de emergencias', 
'Venda, tijera, alcohol, guantes, algodón, esparadrapo, gasa, apósitos, jabón', 'Completo para uso institucional'),
('B003', 'Kit Antiderrame', 'Kit de emergencia para derrames químicos o industriales', 5, 120.00, 3, 1,
'45', '29', '13', 'Genérico', 'KIT-AD', NULL, 'Industrias, talleres, almacenes', 'Control y contención de derrames', 
'Absorbentes, guantes, mascarilla, bolsa de residuos', 'Recomendado en zonas de riesgo químico'),
('B004', 'Camilla de PVC', 'Camilla rígida de PVC con correas de sujeción', 3, 280.00, 3, 1,
'184', '45', '5', 'Genérico', 'CML-PVC', NULL, 'Hospitales, ambulancias, centros laborales', 'Traslado seguro de personas heridas', 
'Estructura PVC, 3 correas de nylon', 'Resistente, lavable y ligera'),
('B005', 'Botiquin de Lona 20x16x6', 'Botiquín portátil de lona pequeño', 12, 30.00, 3, 1,
'20', '16','6', 'Genérico', 'LN-2016', NULL, 'Hogar, auto, unidades móviles', 'Atención rápida de emergencias', 
'Vacío o con insumos básicos', 'Compacto y fácil de transportar'),
('B006', 'Botiquin de Lona 31x27x16', 'Botiquín de lona de mayor capacidad', 10, 55.00, 3, 1,
'31', '27', '16', 'Genérico', 'LN-3127', NULL, 'Empresas, instituciones educativas', 'Primeros auxilios generales', 
'Incluye varios compartimentos', 'Resistente y con asa de transporte'),
('B007', 'Mochila de Emergencia Roja', 'Mochila equipada para situaciones de emergencia', 6, 150.00, 3, 1,
'45', '34', '9', 'Genérico', 'EM-RJ', NULL, 'Hogares, empresas, centros educativos', 'Respuesta inmediata a emergencias', 
'Linterna, radio, botiquín, cuchilla, manta, mascarilla', 'Debe estar en lugar visible y accesible'), 
('S001', 'Señal Vinil Normal', 'Señales de seguridad impresas en vinil normal', 100, 5.00, 4, 1,
'20', '30', '0', 'Genérico', 'VIN-N', NULL, 'Identificación y señalización de seguridad', 'Visibilidad estándar', 
'Material vinil adhesivo', 'Diseños variados: salida, extintor, emergencia, etc.'),
('S002', 'Señal Vinil Fotoluminiscente', 'Señales de seguridad en vinil fotoluminiscente o con base celtex', 80, 12.00, 4, 1,
'20', '30', '0', 'Genérico', 'VIN-FL', NULL, 'Zonas con baja iluminación o evacuación nocturna', 'Brillan en la oscuridad', 
'Vinil fotoluminiscente o base celtex', 'Alta visibilidad incluso sin luz'), 
('E001', 'Luz De Emergencia Opalux 9909.220', 'Luz de emergencia con 24 LED ultrabrillantes, luz blanca 6500K. Puede montarse en techo o pared. Ideal para apagones.', 10, 45.0, 5, 1,'5', '20', '6', 'OPALUX', '9909.220', NULL, 'Montaje en pared o techo', 'Luz blanca 6500K, 24 LED ultrabrillante', 'Batería Nickel-cadmium 3.6v 1000mAh', 'Tiempo de operación 90 min'),
('E002', 'Luz De Emergencia Opalux HB-8667T', 'Luz compacta de emergencia con 16 LED SMD. Proporciona hasta 12 horas de luz continua. Incluye cable de carga.', 15, 38.0, 5, 1, '5', '18',' 5', 'OPALUX', 'HB-8667T', NULL, 'Emergencias generales', '12 horas, 16 LED SMD', 'Batería 4V 4AH', 'Incluye cable de carga 220v'),
('E003', 'Luz De Emergencia Phelix LED3048A', 'Luminaria LED de emergencia con 16 LED y 1.5 horas de autonomía. Diseño compacto y eficiente para interiores.', 12, 40.0, 5, 1, '6', '25', '6', 'PHELIX', 'LED3048A', NULL, 'Emergencias en edificios', '16 LEDs, 2x2 watts', 'Batería integrada', 'Autonomía 1.5 horas'),
('E004', 'Leds De Salida Opalux', 'Luminaria de emergencia con señal “SALIDA” en bajo relieve, ideal para salidas en oficinas e instituciones. Duración hasta 6 horas.', 20, 50.0, 5, 1, '22', '35', '6', 'OPALUX', 'SALIDA', NULL, 'Salidas de emergencia', 'LED, batería Ni-Cd', 'Batería 6h', 'Incluye accesorios de montaje'),
('E005', 'Luz Halogenada Opalux 9808UL', 'Potente luz halógena de emergencia con 2 faros LED de 20W. Cubre áreas amplias (200 m²). Ideal para industrias.', 8, 65.0, 5, 1, '25', '30', '12', 'OPALUX', '9808UL', NULL, 'Industria y construcciones', '2 faros LED 20W', 'Batería 2V 7Ah', 'Área cubierta 200 m²'),
('E006', 'Luz Slim Opalux 9101.220', 'Luz de emergencia tipo slim con 14 LED ultra brillantes. Autonomía de 8 horas. Fácil instalación en interiores.', 14, 42.0, 5, 1, '5', '20', '5', 'OPALUX', '9101.220', NULL, 'Oficinas, hogares', '14 LED SMD', 'Batería 4V 2.5Ah', 'Autonomía 8 horas'),
('E007', 'Luz De Emergencia Opalux HB-890T', 'Luz portátil de alta duración con 28 LED SMD. Funciona hasta 15 horas. Recomendado para lugares sin acceso inmediato a energía.', 10, 55.0, 5, 1, '6', '22', '6', 'OPALUX', 'HB-890T', NULL, 'Emergencias generales', '28 LED SMD', 'Batería 6V 4AH', 'Duración 15 horas'),
('E008', 'Luz De Emergencia Phelix LED 3038A', 'Lámpara de emergencia económica con doble luz, fácil instalación y botón probador. Autonomía de hasta 8 horas.', 10, 48.0, 5, 1, '6', '20',' 5', 'PHELIX', 'LED 3038A', NULL, 'Emergencias en empresas', 'Doble luz, alto rendimiento', 'Batería integrada', 'Botón testeador, 8 horas');

