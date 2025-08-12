-- Insert test users
INSERT INTO users (id, username, password) VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 'juan.perez', '$2a$10$LYm5yEFihJol2VZ9yfMSFOzhpSXE4h8duJobmLSgsjl6trZH98Ox6'),
    ('234e5678-e89b-12d3-a456-426614174001', 'maria.rodriguez', '$2a$10$ewObNwAvTkjFDIH3g55IHOkZcoV83usB9KdENK/Z2z7KEzjF2a8f6'),
    ('345e6789-e89b-12d3-a456-426614174002', 'carlos.gomez', '$2a$10$EMErx56zYAHhG2q4668sDOe9QIuD05Bw6HkGlt8wc/g//paNdv6lq'),
    ('456e7890-e89b-12d3-a456-426614174003', 'ana.sanchez', '$2a$10$F.CNzOWxFxQT7sB5NiIjyedEUP6JGLsQshMQk2tBfD2YYCN5.lo6u'),
    ('567e8901-e89b-12d3-a456-426614174004', 'pedro.martinez', '$2a$10$srpDjhuvevoloA5vyOvYQu1gzDS1hXq7a2e70b3IeP7CRR8daiQGS');

-- Insert test tasks
INSERT INTO tasks (id, title, description, completed, user_id) VALUES
    ('456e7890-e89b-12d3-a456-426614174005', 'Revisar informe de ventas', 'Revisar el informe de ventas del mes pasado y enviar comentarios a la gerencia', false, '123e4567-e89b-12d3-a456-426614174000'),
    ('567e8901-e89b-12d3-a456-426614174006', 'Crear presentación para cliente', 'Crear una presentación para el cliente XYZ y enviarla antes del viernes', true, '234e5678-e89b-12d3-a456-426614174001'),
    ('678e9012-e89b-12d3-a456-426614174007', 'Llamar a proveedor', 'Llamar al proveedor ABC para discutir el pedido pendiente', false, '345e6789-e89b-12d3-a456-426614174002'),
    ('789e0123-e89b-12d3-a456-426614174008', 'Enviar correo electrónico a equipo', 'Enviar un correo electrónico al equipo de marketing para discutir la campaña publicitaria', true, '456e7890-e89b-12d3-a456-426614174003'),
    ('890e1234-e89b-12d3-a456-426614174009', 'Crear documento de especificaciones', 'Crear un documento de especificaciones para el proyecto XYZ', false, '567e8901-e89b-12d3-a456-426614174004'),
    ('901e2345-e89b-12d3-a456-426614174010', 'Actualizar documentación API', 'Revisar y actualizar la documentación de la API REST con los nuevos endpoints', false, '123e4567-e89b-12d3-a456-426614174000'),
    ('012e3456-e89b-12d3-a456-426614174011', 'Realizar pruebas unitarias', 'Implementar pruebas unitarias para el módulo de autenticación', false, '234e5678-e89b-12d3-a456-426614174001'),
    ('123e4567-e89b-12d3-a456-426614174012', 'Optimizar consultas SQL', 'Revisar y optimizar las consultas SQL del módulo de reportes', true, '345e6789-e89b-12d3-a456-426614174002'),
    ('234e5678-e89b-12d3-a456-426614174013', 'Configurar entorno CI/CD', 'Configurar pipeline de integración continua en Jenkins', false, '456e7890-e89b-12d3-a456-426614174003'),
    ('345e6789-e89b-12d3-a456-426614174014', 'Reunión sprint planning', 'Preparar y coordinar la reunión de planificación del próximo sprint', false, '567e8901-e89b-12d3-a456-426614174004'),
    ('456e7890-e89b-12d3-a456-426614174015', 'Resolver bugs críticos', 'Investigar y corregir errores reportados en el sistema de tickets', true, '123e4567-e89b-12d3-a456-426614174000'),
    ('567e8901-e89b-12d3-a456-426614174016', 'Implementar nuevo diseño', 'Actualizar la interfaz de usuario según el nuevo diseño aprobado', false, '234e5678-e89b-12d3-a456-426614174001'),
    ('678e9012-e89b-12d3-a456-426614174017', 'Revisión código legacy', 'Analizar y documentar código legacy para futura refactorización', false, '345e6789-e89b-12d3-a456-426614174002'),
    ('789e0123-e89b-12d3-a456-426614174018', 'Actualizar dependencias', 'Revisar y actualizar dependencias del proyecto a las últimas versiones estables', true, '456e7890-e89b-12d3-a456-426614174003'),
    ('890e1234-e89b-12d3-a456-426614174019', 'Crear guía de desarrollo', 'Documentar estándares y mejores prácticas para el equipo de desarrollo', false, '567e8901-e89b-12d3-a456-426614174004');