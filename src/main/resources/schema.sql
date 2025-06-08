-- Tabla: categorias
CREATE TABLE IF NOT EXISTS CATEGORIAS (
                                          id SERIAL PRIMARY KEY,
                                          nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
    );

-- Tabla: restaurantes
CREATE TABLE IF NOT EXISTS RESTAURANTES (
                                            id SERIAL PRIMARY KEY,
                                            nombre VARCHAR(255) NOT NULL,
    nit VARCHAR(50),
    direccion TEXT,
    telefono VARCHAR(50),
    url_Logo TEXT,
    id_propietario BIGINT
    );

-- Tabla: platos
CREATE TABLE IF NOT EXISTS PLATOS (
                                      id SERIAL PRIMARY KEY,
                                      nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10,2) NOT NULL,
    id_categoria INTEGER,
    id_restaurante INTEGER,
    activo BOOLEAN DEFAULT TRUE,
    url_imagen TEXT,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id),
    FOREIGN KEY (id_restaurante) REFERENCES restaurantes(id)
    );

-- Tabla: pedidos
CREATE TABLE IF NOT EXISTS PEDIDOS (
                                       id SERIAL PRIMARY KEY,
                                       id_cliente BIGINT,
                                       id_restaurante INTEGER,
                                       fecha TIMESTAMP,
                                       estado VARCHAR(50)
    );


-- Tabla: pedido_plato (relación muchos a muchos con cantidad)
CREATE TABLE IF NOT EXISTS PEDIDO_PLATO (
                                            id_pedido INTEGER,
                                            id_plato INTEGER,
                                            cantidad INTEGER NOT NULL,
                                            PRIMARY KEY (id_pedido, id_plato),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
    FOREIGN KEY (id_plato) REFERENCES platos(id)
    );
