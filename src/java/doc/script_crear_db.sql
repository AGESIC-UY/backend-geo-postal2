--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.0
-- Dumped by pg_dump version 9.2.2
-- Started on 2013-09-05 14:57:33


create extension if not exists postgis;  

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 189 (class 1259 OID 17698)
-- Name: base_capa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE base_capa (
    id integer NOT NULL,
    base_id integer NOT NULL,
    capa_id integer NOT NULL,
    orden integer
);


ALTER TABLE public.base_capa OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 17701)
-- Name: base_capa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE base_capa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.base_capa_id_seq OWNER TO postgres;

--
-- TOC entry 4346 (class 0 OID 0)
-- Dependencies: 190
-- Name: base_capa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE base_capa_id_seq OWNED BY base_capa.id;


--
-- TOC entry 191 (class 1259 OID 17703)
-- Name: bases; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE bases (
    id integer NOT NULL,
    etiqueta character varying
);


ALTER TABLE public.bases OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 17709)
-- Name: bases_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bases_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bases_id_seq OWNER TO postgres;

--
-- TOC entry 4347 (class 0 OID 0)
-- Dependencies: 192
-- Name: bases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bases_id_seq OWNED BY bases.id;


--
-- TOC entry 193 (class 1259 OID 17711)
-- Name: campos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE campos (
    id integer NOT NULL,
    criterio_busqueda character varying(255),
    etiqueta character varying(255),
    nombre_columna character varying(255),
    capa_id integer
);


ALTER TABLE public.campos OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 17717)
-- Name: campos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE campos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.campos_id_seq OWNER TO postgres;

--
-- TOC entry 4348 (class 0 OID 0)
-- Dependencies: 194
-- Name: campos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE campos_id_seq OWNED BY campos.id;


--
-- TOC entry 269 (class 1259 OID 36067)
-- Name: capa_filtro; Type: TABLE; Schema: public; Owner: geomatica; Tablespace: 
--

CREATE TABLE capa_filtro (
    id integer NOT NULL,
    id_capa_filtro integer,
    id_capa integer
);



--
-- TOC entry 270 (class 1259 OID 36070)
-- Name: capa_filtro_id_seq; Type: SEQUENCE; Schema: public; Owner: geomatica
--

CREATE SEQUENCE capa_filtro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4349 (class 0 OID 0)
-- Dependencies: 270
-- Name: capa_filtro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: geomatica
--

ALTER SEQUENCE capa_filtro_id_seq OWNED BY capa_filtro.id;


--
-- TOC entry 195 (class 1259 OID 17719)
-- Name: capas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE capas (
    id integer NOT NULL,
    nombre character varying(255),
    tabla character varying(255)
);


ALTER TABLE public.capas OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 17725)
-- Name: capas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE capas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.capas_id_seq OWNER TO postgres;

--
-- TOC entry 4350 (class 0 OID 0)
-- Dependencies: 196
-- Name: capas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE capas_id_seq OWNED BY capas.id;


--
-- TOC entry 289 (class 1259 OID 45408)
-- Name: codigos_postales; Type: TABLE; Schema: public; Owner: geomatica; Tablespace: 
--

CREATE TABLE codigos_postales (
    apis_id integer NOT NULL,
    nombre character varying(1024),
    cod_postal character varying(1024),
    geom geometry(MultiPolygon,32721),
    tipo integer
);




--
-- TOC entry 288 (class 1259 OID 45406)
-- Name: codigos_postales_apis_id_seq; Type: SEQUENCE; Schema: public; Owner: geomatica
--

CREATE SEQUENCE codigos_postales_apis_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



--
-- TOC entry 4351 (class 0 OID 0)
-- Dependencies: 288
-- Name: codigos_postales_apis_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: geomatica
--

ALTER SEQUENCE codigos_postales_apis_id_seq OWNED BY codigos_postales.apis_id;


--
-- TOC entry 197 (class 1259 OID 17775)
-- Name: perfil_base; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_base (
    id integer NOT NULL,
    perfil_id integer NOT NULL,
    base_id integer NOT NULL,
    orden integer
);


ALTER TABLE public.perfil_base OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17778)
-- Name: perfil_base_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfil_base_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfil_base_id_seq OWNER TO postgres;

--
-- TOC entry 4352 (class 0 OID 0)
-- Dependencies: 198
-- Name: perfil_base_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE perfil_base_id_seq OWNED BY perfil_base.id;


--
-- TOC entry 199 (class 1259 OID 17780)
-- Name: perfil_campos; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_campos (
    id integer NOT NULL,
    criterio_busqueda character varying(255),
    etiqueta character varying(255),
    campo_id integer,
    perfil_capa_id integer NOT NULL,
    etiqueta_resultado boolean
);


ALTER TABLE public.perfil_campos OWNER TO postgres;

--
-- TOC entry 4353 (class 0 OID 0)
-- Dependencies: 199
-- Name: COLUMN perfil_campos.etiqueta_resultado; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN perfil_campos.etiqueta_resultado IS 'Indica si se mostrará en la etiqueta del resultado de las busquedas';


--
-- TOC entry 200 (class 1259 OID 17786)
-- Name: perfil_campos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfil_campos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfil_campos_id_seq OWNER TO postgres;

--
-- TOC entry 4354 (class 0 OID 0)
-- Dependencies: 200
-- Name: perfil_campos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE perfil_campos_id_seq OWNED BY perfil_campos.id;


--
-- TOC entry 201 (class 1259 OID 17788)
-- Name: perfil_plugins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil_plugins (
    id integer NOT NULL,
    perfil_id integer,
    plugin_id integer
);


ALTER TABLE public.perfil_plugins OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17791)
-- Name: perfil_plugins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfil_plugins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfil_plugins_id_seq OWNER TO postgres;

--
-- TOC entry 4355 (class 0 OID 0)
-- Dependencies: 202
-- Name: perfil_plugins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE perfil_plugins_id_seq OWNED BY perfil_plugins.id;


--
-- TOC entry 203 (class 1259 OID 17793)
-- Name: perfiles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfiles (
    id integer NOT NULL,
    nombre character varying(255),
    plugins character varying
);


ALTER TABLE public.perfiles OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17799)
-- Name: perfiles_capas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfiles_capas (
    id integer NOT NULL,
    visible boolean,
    capa_id integer NOT NULL,
    perfil_id integer NOT NULL,
    orden integer,
    solo_buscable boolean DEFAULT false
);


ALTER TABLE public.perfiles_capas OWNER TO postgres;

--
-- TOC entry 4356 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN perfiles_capas.orden; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN perfiles_capas.orden IS 'Orden en que se mostrará la capa para el perfil';


--
-- TOC entry 205 (class 1259 OID 17802)
-- Name: perfiles_capas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfiles_capas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfiles_capas_id_seq OWNER TO postgres;

--
-- TOC entry 4357 (class 0 OID 0)
-- Dependencies: 205
-- Name: perfiles_capas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE perfiles_capas_id_seq OWNED BY perfiles_capas.id;


--
-- TOC entry 206 (class 1259 OID 17804)
-- Name: perfiles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE perfiles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.perfiles_id_seq OWNER TO postgres;

--
-- TOC entry 4358 (class 0 OID 0)
-- Dependencies: 206
-- Name: perfiles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE perfiles_id_seq OWNED BY perfiles.id;


--
-- TOC entry 207 (class 1259 OID 17806)
-- Name: plugins; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE plugins (
    id integer NOT NULL,
    nombre character varying,
    descripcion character varying,
    js character varying
);


ALTER TABLE public.plugins OWNER TO postgres;

--
-- TOC entry 4359 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN plugins.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN plugins.id IS 'Identificador';


--
-- TOC entry 4360 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN plugins.nombre; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN plugins.nombre IS 'Nombre del plugin';


--
-- TOC entry 208 (class 1259 OID 17812)
-- Name: plugins_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE plugins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.plugins_id_seq OWNER TO postgres;

--
-- TOC entry 4361 (class 0 OID 0)
-- Dependencies: 208
-- Name: plugins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE plugins_id_seq OWNED BY plugins.id;


--
-- TOC entry 209 (class 1259 OID 17862)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuarios (
    nombre character varying NOT NULL,
    password character varying
);


ALTER TABLE public.usuarios OWNER TO postgres;

--
-- TOC entry 4283 (class 2604 OID 17876)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY base_capa ALTER COLUMN id SET DEFAULT nextval('base_capa_id_seq'::regclass);


--
-- TOC entry 4284 (class 2604 OID 17877)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bases ALTER COLUMN id SET DEFAULT nextval('bases_id_seq'::regclass);


--
-- TOC entry 4285 (class 2604 OID 17878)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY campos ALTER COLUMN id SET DEFAULT nextval('campos_id_seq'::regclass);


--
-- TOC entry 4294 (class 2604 OID 36072)
-- Name: id; Type: DEFAULT; Schema: public; Owner: geomatica
--

ALTER TABLE ONLY capa_filtro ALTER COLUMN id SET DEFAULT nextval('capa_filtro_id_seq'::regclass);


--
-- TOC entry 4286 (class 2604 OID 17879)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY capas ALTER COLUMN id SET DEFAULT nextval('capas_id_seq'::regclass);


--
-- TOC entry 4295 (class 2604 OID 45411)
-- Name: apis_id; Type: DEFAULT; Schema: public; Owner: geomatica
--

ALTER TABLE ONLY codigos_postales ALTER COLUMN apis_id SET DEFAULT nextval('codigos_postales_apis_id_seq'::regclass);


--
-- TOC entry 4287 (class 2604 OID 17886)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_base ALTER COLUMN id SET DEFAULT nextval('perfil_base_id_seq'::regclass);


--
-- TOC entry 4288 (class 2604 OID 17887)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_campos ALTER COLUMN id SET DEFAULT nextval('perfil_campos_id_seq'::regclass);


--
-- TOC entry 4289 (class 2604 OID 17888)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_plugins ALTER COLUMN id SET DEFAULT nextval('perfil_plugins_id_seq'::regclass);


--
-- TOC entry 4290 (class 2604 OID 17889)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfiles ALTER COLUMN id SET DEFAULT nextval('perfiles_id_seq'::regclass);


--
-- TOC entry 4291 (class 2604 OID 17890)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfiles_capas ALTER COLUMN id SET DEFAULT nextval('perfiles_capas_id_seq'::regclass);


--
-- TOC entry 4293 (class 2604 OID 17891)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plugins ALTER COLUMN id SET DEFAULT nextval('plugins_id_seq'::regclass);


--
-- TOC entry 4302 (class 2606 OID 17967)
-- Name: campos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY campos
    ADD CONSTRAINT campos_pkey PRIMARY KEY (id);


--
-- TOC entry 4304 (class 2606 OID 17969)
-- Name: capas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY capas
    ADD CONSTRAINT capas_pkey PRIMARY KEY (id);


--
-- TOC entry 4328 (class 2606 OID 45440)
-- Name: codigos_postales_pkey; Type: CONSTRAINT; Schema: public; Owner: geomatica; Tablespace: 
--

ALTER TABLE ONLY codigos_postales
    ADD CONSTRAINT codigos_postales_pkey PRIMARY KEY (apis_id);


--
-- TOC entry 4298 (class 2606 OID 17975)
-- Name: id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY base_capa
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 4300 (class 2606 OID 17977)
-- Name: id_base; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bases
    ADD CONSTRAINT id_base PRIMARY KEY (id);


--
-- TOC entry 4326 (class 2606 OID 36077)
-- Name: id_capafiltro; Type: CONSTRAINT; Schema: public; Owner: geomatica; Tablespace: 
--

ALTER TABLE ONLY capa_filtro
    ADD CONSTRAINT id_capafiltro PRIMARY KEY (id);


--
-- TOC entry 4314 (class 2606 OID 17979)
-- Name: id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil_plugins
    ADD CONSTRAINT id_pk PRIMARY KEY (id);


--
-- TOC entry 4322 (class 2606 OID 17981)
-- Name: id_usuarios; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT id_usuarios PRIMARY KEY (nombre);


--
-- TOC entry 4307 (class 2606 OID 17987)
-- Name: perfil_base_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil_base
    ADD CONSTRAINT perfil_base_pk PRIMARY KEY (id);


--
-- TOC entry 4310 (class 2606 OID 17989)
-- Name: perfil_campos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil_campos
    ADD CONSTRAINT perfil_campos_pkey PRIMARY KEY (id);


--
-- TOC entry 4318 (class 2606 OID 17991)
-- Name: perfiles_capas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfiles_capas
    ADD CONSTRAINT perfiles_capas_pkey PRIMARY KEY (id);


--
-- TOC entry 4316 (class 2606 OID 17993)
-- Name: perfiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfiles
    ADD CONSTRAINT perfiles_pkey PRIMARY KEY (id);


--
-- TOC entry 4320 (class 2606 OID 17995)
-- Name: plugins_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plugins
    ADD CONSTRAINT plugins_pk PRIMARY KEY (id);


--
-- TOC entry 4305 (class 1259 OID 18002)
-- Name: fki_base_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_base_fk ON perfil_base USING btree (base_id);


--
-- TOC entry 4296 (class 1259 OID 18003)
-- Name: fki_capa; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_capa ON base_capa USING btree (capa_id);


--
-- TOC entry 4323 (class 1259 OID 36083)
-- Name: fki_capaFiltro_capa; Type: INDEX; Schema: public; Owner: geomatica; Tablespace: 
--

CREATE INDEX "fki_capaFiltro_capa" ON capa_filtro USING btree (id_capa);


--
-- TOC entry 4324 (class 1259 OID 36089)
-- Name: fki_capafiltro_capa_filtro; Type: INDEX; Schema: public; Owner: geomatica; Tablespace: 
--

CREATE INDEX fki_capafiltro_capa_filtro ON capa_filtro USING btree (id_capa_filtro);


--
-- TOC entry 4308 (class 1259 OID 18004)
-- Name: fki_fkasd; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_fkasd ON perfil_campos USING btree (campo_id);


--
-- TOC entry 4311 (class 1259 OID 18005)
-- Name: fki_perfil_fk; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_perfil_fk ON perfil_plugins USING btree (perfil_id);


--
-- TOC entry 4312 (class 1259 OID 18006)
-- Name: fki_plugin_id; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_plugin_id ON perfil_plugins USING btree (plugin_id);


--
-- TOC entry 4329 (class 2606 OID 18011)
-- Name: base; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY base_capa
    ADD CONSTRAINT base FOREIGN KEY (base_id) REFERENCES bases(id);


--
-- TOC entry 4332 (class 2606 OID 18016)
-- Name: base_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_base
    ADD CONSTRAINT base_fk FOREIGN KEY (base_id) REFERENCES bases(id);


--
-- TOC entry 4330 (class 2606 OID 18021)
-- Name: capa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY base_capa
    ADD CONSTRAINT capa FOREIGN KEY (capa_id) REFERENCES capas(id);


--
-- TOC entry 4340 (class 2606 OID 36078)
-- Name: fk_capaFiltro_capa; Type: FK CONSTRAINT; Schema: public; Owner: geomatica
--

ALTER TABLE ONLY capa_filtro
    ADD CONSTRAINT "fk_capaFiltro_capa" FOREIGN KEY (id_capa) REFERENCES perfiles_capas(id);


--
-- TOC entry 4341 (class 2606 OID 36084)
-- Name: fk_capafiltro_capa_filtro; Type: FK CONSTRAINT; Schema: public; Owner: geomatica
--

ALTER TABLE ONLY capa_filtro
    ADD CONSTRAINT fk_capafiltro_capa_filtro FOREIGN KEY (id_capa_filtro) REFERENCES perfiles_capas(id);


--
-- TOC entry 4331 (class 2606 OID 18026)
-- Name: fkae79ec25fc2ee4ea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY campos
    ADD CONSTRAINT fkae79ec25fc2ee4ea FOREIGN KEY (capa_id) REFERENCES capas(id);


--
-- TOC entry 4334 (class 2606 OID 18031)
-- Name: fkasd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_campos
    ADD CONSTRAINT fkasd FOREIGN KEY (campo_id) REFERENCES campos(id);


--
-- TOC entry 4338 (class 2606 OID 18036)
-- Name: fkd0f3727f4306312d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfiles_capas
    ADD CONSTRAINT fkd0f3727f4306312d FOREIGN KEY (perfil_id) REFERENCES perfiles(id);


--
-- TOC entry 4339 (class 2606 OID 18041)
-- Name: fkd0f3727ffc2ee4ea; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfiles_capas
    ADD CONSTRAINT fkd0f3727ffc2ee4ea FOREIGN KEY (capa_id) REFERENCES capas(id);


--
-- TOC entry 4335 (class 2606 OID 18046)
-- Name: fkf87e21f862ac571d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_campos
    ADD CONSTRAINT fkf87e21f862ac571d FOREIGN KEY (perfil_capa_id) REFERENCES perfiles_capas(id);


--
-- TOC entry 4333 (class 2606 OID 18051)
-- Name: perfil; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_base
    ADD CONSTRAINT perfil FOREIGN KEY (perfil_id) REFERENCES perfiles(id);


--
-- TOC entry 4336 (class 2606 OID 18056)
-- Name: perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_plugins
    ADD CONSTRAINT perfil_fk FOREIGN KEY (perfil_id) REFERENCES perfiles(id);


--
-- TOC entry 4337 (class 2606 OID 18061)
-- Name: plugin_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY perfil_plugins
    ADD CONSTRAINT plugin_id FOREIGN KEY (plugin_id) REFERENCES plugins(id);


-- Completed on 2013-09-05 14:57:34

--
-- PostgreSQL database dump complete
--

--REGISTROS POR DEFECTO

--usuario para Anubis
INSERT INTO public.usuarios(nombre, password) VALUES ('admin', '0da2e7fa0ba90f4ae031b0d232b8a57a'); --pass: anubis

--Perfil por defecto para el visualizador
INSERT INTO public.perfiles(nombre) VALUES ('Default');

--Capas base predefinidas
INSERT INTO public.bases(etiqueta) VALUES ('Open Street Map');
INSERT INTO public.bases(etiqueta) VALUES ('Google Physical');
INSERT INTO public.bases(etiqueta) VALUES ('Google Streets');
INSERT INTO public.bases(etiqueta) VALUES ('Google Hybrid');
INSERT INTO public.bases(etiqueta) VALUES ('Google Satellite');

