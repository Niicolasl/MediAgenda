-- Insertar Especialidades
INSERT INTO especialidad (nombre) VALUES ('Medicina General');
INSERT INTO especialidad (nombre) VALUES ('Cardiología');
INSERT INTO especialidad (nombre) VALUES ('Dermatología');
INSERT INTO especialidad (nombre) VALUES ('Pediatría');

-- Insertar Pacientes
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '123456789', '3001234567', 'FEMENINO', NOW(), TRUE, 'Ana', 'Pérez', 'ana.perez@example.com', '$2a$10$HlK.xzWs1RHKqBAm8QTdF.ELhvZs.kJ7QZFoPKxxqod7gqQsLQzhK'); -- Reemplaza la contraseña hasheada
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '987654321', '3109876543', 'MASCULINO', NOW(), TRUE, 'Juan', 'González', 'juan.gonzalez@example.com', '$2a$10$Qxy03ogr72uehW7Uv7knZeo6A7MaFGpLMxAJvJZkT1nkL6jAX3Oca'); -- Reemplaza la contraseña hasheada
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CE', '555555555', '3205551212', 'FEMENINO', NOW(), TRUE, 'María', 'Rodríguez', 'maria.rodriguez@example.com', '$2a$10$R0mYnb0pPrfYPNQZH8daAenDJkUf2GtfbaViYa7bg5NH1mHJ0PSSG'); -- Reemplaza la contraseña hasheada
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '111222333', '3154448899', 'MASCULINO', NOW(), TRUE, 'Carlos', 'López', 'carlos.lopez@example.com', '$2a$10$uNXAB02OCnWIwYGBnQDJx.c.i6KiCtD.wWPU.sRmF2nJ/fjEOhKoq'); -- Reemplaza la contraseña hasheada
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('TI', '999888777', '3017772323', 'FEMENINO', NOW(), TRUE, 'Sofía', 'Martínez', 'sofia.martinez@example.com', '$2a$10$Td2WuS5y1rvT5wYdX1TQSe.LjPuZ4wFnGRblAuQPAmR7tzkPbtG96'); -- Reemplaza la contraseña hasheada

-- Insertar Proveedores
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'MG12345', '3112223344', 'Consultorio 1', 'Médico general experto', NOW(), TRUE, 'Neiva', 'Dr. Gómez', 'Sánchez', 'dr.gomez@example.com', '$2a$10$0xhg5LIgzP3zAMlBK8kOyOMp2eFArP.P.wjwM0emixE1Zc0qjfvqy'); -- Reemplaza la contraseña hasheada
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'CR67890', '3028889900', 'Consultorio 2', 'Cardióloga dedicada', NOW(), TRUE, 'Neiva', 'Dra. Vargas', 'Ramírez', 'dra.vargas@example.com', '$2a$10$zub.np8K.jfl8eUXsr00JOZadC69slxtq6tYF9A9UilRC2tp1r.Ri'); -- Reemplaza la contraseña hasheada
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'DM11223', '3165556677', 'Consultorio 3', 'Dermatólogo con experiencia', NOW(), TRUE, 'Neiva', 'Dr. Torres', 'Fernández', 'dr.torres@example.com', '$2a$10$z/Y2/bFO0/jqgCCmd4EvZuipbo4FGzG1HWemk1uSzyriFPqizk9z6'); -- Reemplaza la contraseña hasheada
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'MG44556', '3051112233', 'Consultorio 4', 'Médico general y cardiólogo', NOW(), TRUE, 'Neiva', 'Dra. Ruiz', 'Castro', 'dra.ruiz@example.com', '$2a$10$yKDPOvt/ooENWmhp8VYbLO/6S.WM.wHqCHXsfT/Ddz5dj1zraBwK.'); -- Reemplaza la contraseña hasheada

-- Insertar Proveedor_Especialidades (Tabla intermedia para la relación ManyToMany)
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (1, 1); -- Dr. Gómez - Medicina General
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (2, 2); -- Dra. Vargas - Cardiología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (3, 3); -- Dr. Torres - Dermatología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (4, 1); -- Dra. Ruiz - Medicina General
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (4, 2); -- Dra. Ruiz - Cardiología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (5, 4); -- Dr. Díaz - Pediatría

-- Insertar Citas
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-28 10:00:00', '2025-05-28 10:30:00', 'Consulta general', 'PENDIENTE', 1, 1);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-29 11:00:00', '2025-05-29 11:30:00', 'Control de cardiología', 'PENDIENTE', 2, 2);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-28 14:00:00', '2025-05-28 14:30:00', 'Revisión de piel', 'PENDIENTE', 3, 3);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-30 09:30:00', '2025-05-30 10:00:00', 'Control pediátrico', 'PENDIENTE', 4, 5);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-29 16:00:00', '2025-05-29 16:30:00', 'Consulta general', 'PENDIENTE', 5, 1);

-- Insertar Horario
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (1, 'LUNES', '08:00', '12:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (1, 'LUNES', '14:00', '17:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (2, 'MARTES', '09:00', '13:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (2, 'MARTES', '15:00', '18:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (3, 'MIERCOLES', '08:30', '12:30');

-- Insertar Usuarios (las contraseñas están como 'password' y DEBES reemplazarlas con hashes reales)
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'ana.perez@example.com', '$2a$10$HlK.xzWs1RHKqBAm8QTdF.ELhvZs.kJ7QZFoPKxxqod7gqQsLQzhK', 'PACIENTE', 'CC', '123456789', '3001234567', 'FEMENINO', NOW(), TRUE);
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'juan.gonzalez@example.com', '$2a$10$Qxy03ogr72uehW7Uv7knZeo6A7MaFGpLMxAJvJZkT1nkL6jAX3Oca', 'PACIENTE', 'CC', '987654321', '3109876543', 'MASCULINO', NOW(), TRUE);
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'maria.rodriguez@example.com', '$2a$10$R0mYnb0pPrfYPNQZH8daAenDJkUf2GtfbaViYa7bg5NH1mHJ0PSSG', 'PACIENTE', 'CE', '555555555', '3205551212', 'FEMENINO', NOW(), TRUE);
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'carlos.lopez@example.com', '$2a$10$uNXAB02OCnWIwYGBnQDJx.c.i6KiCtD.wWPU.sRmF2nJ/fjEOhKoq', 'PACIENTE', 'CC', '111222333', '3154448899', 'MASCULINO', NOW(), TRUE);
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'sofia.martinez@example.com', '$2a$10$Td2WuS5y1rvT5wYdX1TQSe.LjPuZ4wFnGRblAuQPAmR7tzkPbtG96', 'PACIENTE', 'TI', '999888777', '3017772323', 'FEMENINO', NOW(), TRUE);
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.gomez@example.com', '$2a$10$0xhg5LIgzP3zAMlBK8kOyOMp2eFArP.P.wjwM0emixE1Zc0qjfvqy', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'MG12345', '3112223344', 'Consultorio 1', 'Médico general experto', NOW(), TRUE, 'Neiva');
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dra.vargas@example.com', '$2a$10$zub.np8K.jfl8eUXsr00JOZadC69slxtq6tYF9A9UilRC2tp1r.Ri', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'CR67890', '3028889900', 'Consultorio 2', 'Cardióloga dedicada', NOW(), TRUE, 'Neiva');
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.torres@example.com', '$2a$10$z/Y2/bFO0/jqgCCmd4EvZuipbo4FGzG1HWemk1uSzyriFPqizk9z6', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'DM11223', '3165556677', 'Consultorio 3', 'Dermatólogo con experiencia', NOW(), TRUE, 'Neiva');
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dra.ruiz@example.com', '$2a$10$yKDPOvt/ooENWmhp8VYbLO/6S.WM.wHqCHXsfT/Ddz5dj1zraBwK.', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'MG44556', '3051112233', 'Consultorio 4', 'Médico general y cardiólogo', NOW(), TRUE, 'Neiva');
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.diaz@example.com', '$2a$10$pwyL/1QXntRre5MxuQm0ceibgUSiCPAYO6QKpE47cva.IDiLSOkHu''PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'PD77889', '3179990011', 'Consultorio 5', 'Pediatra infantil', NOW(), TRUE, 'Neiva');





 -- Insertar Especialidades
INSERT INTO especialidad (nombre) VALUES ('Medicina General');
INSERT INTO especialidad (nombre) VALUES ('Cardiología');
INSERT INTO especialidad (nombre) VALUES ('Dermatología');
INSERT INTO especialidad (nombre) VALUES ('Pediatría');

-- Insertar Pacientes
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '123456789', '3001234567', 'FEMENINO', NOW(), TRUE, 'Ana', 'Pérez', 'ana.perez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '987654321', '3109876543', 'MASCULINO', NOW(), TRUE, 'Juan', 'González', 'juan.gonzalez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CE', '555555555', '3205551212', 'FEMENINO', NOW(), TRUE, 'María', 'Rodríguez', 'maria.rodriguez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('CC', '111222333', '3154448899', 'MASCULINO', NOW(), TRUE, 'Carlos', 'López', 'carlos.lopez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO paciente (tipo_documento, numero_documento, telefono, genero, fecha_registro, activo, nombre, apellido, email, password) VALUES ('TI', '999888777', '3017772323', 'FEMENINO', NOW(), TRUE, 'Sofía', 'Martínez', 'sofia.martinez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!

-- Insertar Proveedores
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'MG12345', '3112223344', 'Consultorio 1', 'Médico general experto', NOW(), TRUE, 'Neiva', 'Dr. Gómez', 'Sánchez', 'dr.gomez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'CR67890', '3028889900', 'Consultorio 2', 'Cardióloga dedicada', NOW(), TRUE, 'Neiva', 'Dra. Vargas', 'Ramírez', 'dra.vargas@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'DM11223', '3165556677', 'Consultorio 3', 'Dermatólogo con experiencia', NOW(), TRUE, 'Neiva', 'Dr. Torres', 'Fernández', 'dr.torres@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'MG44556', '3051112233', 'Consultorio 4', 'Médico general y cardiólogo', NOW(), TRUE, 'Neiva', 'Dra. Ruiz', 'Castro', 'dra.ruiz@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO proveedor (tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad, nombre, apellido, email, password) VALUES ('MEDICO_INDEPENDIENTE', 'PD77889', '3179990011', 'Consultorio 5', 'Pediatra infantil', NOW(), TRUE, 'Neiva', 'Dr. Díaz', 'Gutiérrez', 'dr.diaz@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'); -- ¡REEMPLAZA ESTE HASH!

-- Insertar Proveedor_Especialidades (Tabla intermedia para la relación ManyToMany)
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (1, 1); -- Dr. Gómez - Medicina General
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (2, 2); -- Dra. Vargas - Cardiología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (3, 3); -- Dr. Torres - Dermatología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (4, 1); -- Dra. Ruiz - Medicina General
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (4, 2); -- Dra. Ruiz - Cardiología
INSERT INTO proveedor_especialidades (proveedor_id, especialidad_id) VALUES (5, 4); -- Dr. Díaz - Pediatría

-- Insertar Citas
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-28 10:00:00', '2025-05-28 10:30:00', 'Consulta general', 'PENDIENTE', 1, 1);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-29 11:00:00', '2025-05-29 11:30:00', 'Control de cardiología', 'PENDIENTE', 2, 2);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-28 14:00:00', '2025-05-28 14:30:00', 'Revisión de piel', 'PENDIENTE', 3, 3);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-30 09:30:00', '2025-05-30 10:00:00', 'Control pediátrico', 'PENDIENTE', 4, 5);
INSERT INTO cita (fecha_hora_inicio, fecha_hora_fin, motivo_cita, estado_cita, paciente_id, proveedor_id) VALUES ('2025-05-29 16:00:00', '2025-05-29 16:30:00', 'Consulta general', 'PENDIENTE', 5, 1);

-- Insertar Horario
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (1, 'LUNES', '08:00', '12:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (1, 'LUNES', '14:00', '17:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (2, 'MARTES', '09:00', '13:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (2, 'MARTES', '15:00', '18:00');
INSERT INTO horario (proveedor_id, dia_semana, hora_inicio, hora_fin) VALUES (3, 'MIERCOLES', '08:30', '12:30');

-- Insertar Usuarios (las contraseñas están como placeholder y DEBES reemplazarlas con hashes reales)
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'ana.perez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PACIENTE', 'CC', '123456789', '3001234567', 'FEMENINO', NOW(), TRUE); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'juan.gonzalez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PACIENTE', 'CC', '987654321', '3109876543', 'MASCULINO', NOW(), TRUE); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'maria.rodriguez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PACIENTE', 'CE', '555555555', '3205551212', 'FEMENINO', NOW(), TRUE); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'carlos.lopez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PACIENTE', 'CC', '111222333', '3154448899', 'MASCULINO', NOW(), TRUE); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_documento, numero_documento, telefono, genero, fecha_registro, activo) VALUES ('Paciente', 'sofia.martinez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PACIENTE', 'TI', '999888777', '3017772323', 'FEMENINO', NOW(), TRUE); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.gomez@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'MG12345', '3112223344', 'Consultorio 1', 'Médico general experto', NOW(), TRUE, 'Neiva'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dra.vargas@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'CR67890', '3028889900', 'Consultorio 2', 'Cardióloga dedicada', NOW(), TRUE, 'Neiva'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.torres@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'DM11223', '3165556677', 'Consultorio 3', 'Dermatólogo con experiencia', NOW(), TRUE, 'Neiva'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dra.ruiz@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'MG44556', '3051112233', 'Consultorio 4', 'Médico general y cardiólogo', NOW(), TRUE, 'Neiva'); -- ¡REEMPLAZA ESTE HASH!
INSERT INTO usuario (dtype, email, password, rol, tipo_proveedor, numero_registro_profesional, telefono_contacto, direccion_consultorio, descripcion, fecha_registro, activo, ciudad) VALUES ('Proveedor', 'dr.diaz@example.com', '$2a$10$xxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 'PROVEEDOR', 'MEDICO_INDEPENDIENTE', 'PD77889', '3179990011', 'Consultorio 5', 'Pediatra infantil', NOW(), TRUE, 'Neiva'); -- ¡REEMPLAZA ESTE HASH!