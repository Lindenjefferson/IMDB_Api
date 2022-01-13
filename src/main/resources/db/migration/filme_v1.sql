CREATE TABLE filme (
	id      int8 NOT NULL,
	nome    varchar(255) NOT NULL,
    diretor varchar(255) NOT NULL,
    genero  varchar(255) NOT NULL,
    atores  varchar(255) NOT NULL,
	CONSTRAINT filme_pkey PRIMARY KEY (id)
);