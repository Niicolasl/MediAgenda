-- Insertar Especialidades
INSERT INTO especialidades (nombre) VALUES ('Medicina General');
INSERT INTO especialidades (nombre) VALUES ('Cardiología');
INSERT INTO especialidades (nombre) VALUES ('Dermatología');
INSERT INTO especialidades (nombre) VALUES ('Pediatría');

-- Insertar Pacientes en la tabla Usuarios
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Ana', 'Pérez', 'ana.perez@example.com', '$2a$10$HlK.xzWs1RHKqBAm8QTdF.ELhvZs.kJ7QZFoPKxxqod7gqQsLQzhK', 'PACIENTE');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Juan', 'González', 'juan.gonzalez@example.com', '$2a$10$Qxy03ogr72uehW7Uv7knZeo6A7MaFGpLMxAJvJZkT1nkL6jAX3Oca', 'PACIENTE');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('María', 'Rodríguez', 'maria.rodriguez@example.com', '$2a$10$R0mYnb0pPrfYPNQZH8daAenDJkUf2GtfbaViYa7bg5NH1mHJ0PSSG', 'PACIENTE');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Carlos', 'López', 'carlos.lopez@example.com', '$2a$10$uNXAB02OCnWIwYGBnQDJx.c.i6KiCtD.wWPU.sRmF2nJ/fjEOhKoq', 'PACIENTE');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Sofía', 'Martínez', 'sofia.martinez@example.com', '$2a$10$Td2WuS5y1rvT5wYdX1TQSe.LjPuZ4wFnGRblAuQPAmR7tzkPbtG96', 'PACIENTE');

-- Insertar Proveedores en la tabla Usuarios
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Dr. Gómez', 'Sánchez', 'dr.gomez@example.com', '$2a$10$0xhg5LIgzP3zAMlBK8kOyOMp2eFArP.P.wjwM0emixE1Zc0qjfvqy', 'PROVEEDOR');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Dra. Vargas', 'Ramírez', 'dra.vargas@example.com', '$2a$10$zub.np8K.jfl8eUXsr00JOZadC69slxtq6tYF9A9UilRC2tp1r.Ri', 'PROVEEDOR');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Dr. Torres', 'Fernández', 'dr.torres@example.com', '$2a$10$z/Y2/bFO0/jqgCCmd4EvZuipbo4FGzG1HWemk1uSzyriFPqizk9z6', 'PROVEEDOR');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Dra. Ruiz', 'Castro', 'dra.ruiz@example.com', '$2a$10$yKDPOvt/ooENWmhp8VYbLO/6S.WM.wHqCHXsfT/Ddz5dj1zraBwK.', 'PROVEEDOR');
INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES ('Dr. Díaz', 'Gutiérrez', 'dr.diaz@example.com', '$2a$10$pwyL/1QXntRre5MxuQm0ceibgUSiCPAYO6QKpE47cva.IDiLSOkHuPROVEEDOR', 'PROVEEDOR');

-- Insertar Pacientes en la tabla pacientes
INSERT INTO pacientes (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, id) VALUES ('CC', '123456789', '3001234567', 'FEMENINO', NOW(), TRUE, (SELECT id FROM usuarios WHERE email = 'ana.perez@example.com'));
INSERT INTO pacientes (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, id) VALUES ('CC', '987654321', '3109876543', 'MASCULINO', NOW(), TRUE, (SELECT id FROM usuarios WHERE email = 'juan.gonzalez@example.com'));
INSERT INTO pacientes (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, id) VALUES ('CE', '555555555', '3205551212', 'FEMENINO', NOW(), TRUE, (SELECT id FROM usuarios WHERE email = 'maria.rodriguez@example.com'));
INSERT INTO pacientes (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, id) VALUES ('CC', '111222333', '3154448899', 'MASCULINO', NOW(), TRUE, (SELECT id FROM usuarios WHERE email = 'carlos.lopez@example.com'));
INSERT INTO pacientes (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, id) VALUES ('TI', '999888777', '3017772323', 'FEMENINO', NOW(), TRUE, (SELECT id FROM usuarios WHERE email = 'sofia.martinez@example.com'));


-- Insertar Proveedores en la tabla proveedores
INSERT INTO proveedores (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, id) VALUES ('MEDICO_INDEPENDIENTE', 'MG12345', '3112223344', 'Consultorio 1', 'Médico general experto', NOW(), TRUE, 'Neiva', (SELECT id FROM usuarios WHERE email = 'dr.gomez@example.com'));
INSERT INTO proveedores (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, id) VALUES ('MEDICO_INDEPENDIENTE', 'CR67890', '3028889900', 'Consultorio 2', 'Cardióloga dedicada', NOW(), TRUE, 'Neiva', (SELECT id FROM usuarios WHERE email = 'dra.vargas@example.com'));
INSERT INTO proveedores (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, id) VALUES ('MEDICO_INDEPENDIENTE', 'DM11223', '3165556677', 'Consultorio 3', 'Dermatólogo con experiencia', NOW(), TRUE, 'Neiva', (SELECT id FROM usuarios WHERE email = 'dr.torres@example.com'));
INSERT INTO proveedores (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, id) VALUES ('MEDICO_INDEPENDIENTE', 'MG44556', '3051112233', 'Consultorio 4', 'Médico general y cardiólogo', NOW(), TRUE, 'Neiva', (SELECT id FROM usuarios WHERE email = 'dra.ruiz@example.com'));
INSERT INTO proveedores (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, id) VALUES ('MEDICO_INDEPENDIENTE', 'PD77889', '3179990011', 'Consultorio 5', 'Pediatra infantil', NOW(), TRUE, 'Neiva', (SELECT id FROM usuarios WHERE email = 'dr.diaz@example.com'));

-- Insertar en la tabla intermedia proveedor_especialidades
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.gomez@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Medicina General')
                                                                            ); -- Dr. Gómez - Medicina General

INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.vargas@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Cardiología')
                                                                            ); -- Dra. Vargas - Cardiología

INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.torres@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Dermatología')
                                                                            ); -- Dr. Torres - Dermatología

INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.ruiz@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Medicina General')
                                                                            ); -- Dra. Ruiz - Medicina General

INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.ruiz@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Cardiología')
                                                                            ); -- Dra. Ruiz - Cardiología

INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (
                                                                                (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.diaz@example.com')),
                                                                                (SELECT id FROM especialidades WHERE nombre = 'Pediatría')
                                                                            ); -- Dr. Díaz - Pediatría


-- Insertar horarios para cada proveedor (un horario genérico por proveedor)
INSERT INTO horarios (disponible, fecha_hora_inicio, fecha_hora_fin, proveedor_id) VALUES (
                                                                                              TRUE,
                                                                                              '2025-06-02 09:00:00',
                                                                                              '2025-06-02 12:00:00',
                                                                                              (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.gomez@example.com'))
                                                                                          ); -- Dr. Gómez

INSERT INTO horarios (disponible, fecha_hora_inicio, fecha_hora_fin, proveedor_id) VALUES (
                                                                                              TRUE,
                                                                                              '2025-06-03 14:00:00',
                                                                                              '2025-06-03 17:00:00',
                                                                                              (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.vargas@example.com'))
                                                                                          ); -- Dra. Vargas

INSERT INTO horarios (disponible, fecha_hora_inicio, fecha_hora_fin, proveedor_id) VALUES (
                                                                                              TRUE,
                                                                                              '2025-06-04 10:00:00',
                                                                                              '2025-06-04 13:00:00',
                                                                                              (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.torres@example.com'))
                                                                                          ); -- Dr. Torres

INSERT INTO horarios (disponible, fecha_hora_inicio, fecha_hora_fin, proveedor_id) VALUES (
                                                                                              TRUE,
                                                                                              '2025-06-05 15:00:00',
                                                                                              '2025-06-05 18:00:00',
                                                                                              (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.ruiz@example.com'))
                                                                                          ); -- Dra. Ruiz

INSERT INTO horarios (disponible, fecha_hora_inicio, fecha_hora_fin, proveedor_id) VALUES (
                                                                                              TRUE,
                                                                                              '2025-06-06 08:00:00',
                                                                                              '2025-06-06 11:00:00',
                                                                                              (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.diaz@example.com'))
                                                                                          ); -- Dr. Díaz


-- Insertar Citas relacionándolas con franjas_horarias_disponibles
INSERT INTO citas (fecha_hora, paciente_id, proveedor_id, notas, estado) VALUES (
                                                                                    '2025-05-28 10:00:00',
                                                                                    (SELECT id FROM pacientes WHERE id = (SELECT id FROM usuarios WHERE email = 'ana.perez@example.com')),
                                                                                    (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.gomez@example.com')),
                                                                                    'Consulta general',
                                                                                    'PENDIENTE'
                                                                                );

INSERT INTO citas (fecha_hora, paciente_id, proveedor_id, notas, estado) VALUES (
                                                                                    '2025-05-29 11:00:00',
                                                                                    (SELECT id FROM pacientes WHERE id = (SELECT id FROM usuarios WHERE email = 'juan.gonzalez@example.com')),
                                                                                    (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dra.vargas@example.com')),
                                                                                    'Control de cardiología',
                                                                                    'PENDIENTE'
                                                                                );

INSERT INTO citas (fecha_hora, paciente_id, proveedor_id, notas, estado) VALUES (
                                                                                    '2025-05-28 14:00:00',
                                                                                    (SELECT id FROM pacientes WHERE id = (SELECT id FROM usuarios WHERE email = 'maria.rodriguez@example.com')),
                                                                                    (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.torres@example.com')),
                                                                                    'Revisión de piel',
                                                                                    'PENDIENTE'
                                                                                );

INSERT INTO citas (fecha_hora, paciente_id, proveedor_id, notas, estado) VALUES (
                                                                                    '2025-05-30 09:30:00',
                                                                                    (SELECT id FROM pacientes WHERE id = (SELECT id FROM usuarios WHERE email = 'carlos.lopez@example.com')),
                                                                                    (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.diaz@example.com')),
                                                                                    'Control pediátrico',
                                                                                    'PENDIENTE'
                                                                                );

INSERT INTO citas (fecha_hora, paciente_id, proveedor_id, notas, estado) VALUES (
                                                                                    '2025-05-29 16:00:00',
                                                                                    (SELECT id FROM pacientes WHERE id = (SELECT id FROM usuarios WHERE email = 'sofia.martinez@example.com')),
                                                                                    (SELECT id FROM proveedores WHERE id = (SELECT id FROM usuarios WHERE email = 'dr.gomez@example.com')),
                                                                                    'Consulta general',
                                                                                    'PENDIENTE'
                                                                                );

