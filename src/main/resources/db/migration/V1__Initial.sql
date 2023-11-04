-- DROP TABLE IF EXISTS public.users CASCADE ;
-- DROP TABLE IF EXISTS public.trainers CASCADE;
-- DROP TABLE IF EXISTS public.clients CASCADE;

CREATE TABLE IF NOT EXISTS public.clients
(
    id bigint NOT NULL DEFAULT nextval('clients_id_seq'::regclass),
    user_id bigint,
    bio character varying(255) COLLATE pg_catalog."default",
    fitness_level character varying(255) COLLATE pg_catalog."default",
    goals character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT clients_pkey PRIMARY KEY (id),
    CONSTRAINT clients_user_id_key UNIQUE (user_id),
    CONSTRAINT fktiuqdledq2lybrds2k3rfqrv4 FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT clients_fitness_level_check CHECK (fitness_level::text = ANY (ARRAY['BEGINNER'::character varying, 'NOVICE'::character varying, 'INTERMEDIATE'::character varying, 'ADVANCED'::character varying, 'ELITE'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clients
    OWNER to root;

-- Table: public.trainers

-- DROP TABLE IF EXISTS public.trainers;

CREATE TABLE IF NOT EXISTS public.trainers
(
    experience integer NOT NULL,
    id bigint NOT NULL DEFAULT nextval('trainers_id_seq'::regclass),
    user_id bigint,
    description character varying(255) COLLATE pg_catalog."default",
    specializations character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT trainers_pkey PRIMARY KEY (id),
    CONSTRAINT trainers_user_id_key UNIQUE (user_id),
    CONSTRAINT fkmkxcvfr0uu3pwv772aurye5w7 FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.trainers
    OWNER to root;

-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    date_of_birth date,
    client_id bigint,
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    trainer_id bigint,
    email character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    picture oid,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_client_id_key UNIQUE (client_id),
    CONSTRAINT users_trainer_id_key UNIQUE (trainer_id),
    CONSTRAINT fkh22b6bdb065nbri3q9dinb0k5 FOREIGN KEY (trainer_id)
    REFERENCES public.trainers (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkqvykjc6027qa8n5es37omu3xs FOREIGN KEY (client_id)
    REFERENCES public.clients (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT users_gender_check CHECK (gender::text = ANY (ARRAY['M'::character varying, 'F'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to root;