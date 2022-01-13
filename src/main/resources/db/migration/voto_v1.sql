CREATE TABLE voto (
	id         int8 NOT NULL,
    voto       int NOT NULL,
    usuario_id int8 NOT NULL,
    filme_id   int8 NOT NULL,
	CONSTRAINT voto_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_fk FOREIGN KEY usuario_id REFERENCES usuario (id),
    CONSTRAINT filme_fk FOREIGN KEY filme_id REFERENCES filme (id)
);