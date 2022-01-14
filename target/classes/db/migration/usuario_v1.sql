CREATE TABLE usuario (
	id    int8 NOT NULL,
    email varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	nome  varchar(255) NOT NULL,
	ativo boolean NOT NULL;
	admin boolean NOT NULL;
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);