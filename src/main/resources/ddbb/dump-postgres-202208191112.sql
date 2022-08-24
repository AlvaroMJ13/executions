--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-08-19 11:12:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 31335)
-- Name: pagos; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA pagos;


ALTER SCHEMA pagos OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 479 (class 1259 OID 31364)
-- Name: entity; Type: TABLE; Schema: pagos; Owner: postgres
--

CREATE TABLE pagos.entity (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE pagos.entity OWNER TO postgres;

--
-- TOC entry 478 (class 1259 OID 31363)
-- Name: entity_id_seq; Type: SEQUENCE; Schema: pagos; Owner: postgres
--

ALTER TABLE pagos.entity ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME pagos.entity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 480 (class 1259 OID 31369)
-- Name: entity_status; Type: TABLE; Schema: pagos; Owner: postgres
--

CREATE TABLE pagos.entity_status (
	"identity" int4 NOT NULL,
	idstatus int4 NOT NULL,
	"order" int4 NOT NULL,
	rungateway bool NOT NULL DEFAULT true
);


ALTER TABLE pagos.entity_status OWNER TO postgres;

--ALTER TABLE pagos.entity_status ADD rungateway boolean NOT NULL DEFAULT true;

--
-- TOC entry 474 (class 1259 OID 31336)
-- Name: executions; Type: TABLE; Schema: pagos; Owner: postgres
--

CREATE TABLE pagos.executions (
    id uuid NOT NULL,
    entityid integer NOT NULL,
    gtsmessageid integer NOT NULL,
    globaloperationid integer
);


ALTER TABLE pagos.executions OWNER TO postgres;

--
-- TOC entry 475 (class 1259 OID 31344)
-- Name: executions_status; Type: TABLE; Schema: pagos; Owner: postgres
--

CREATE TABLE pagos.executions_status (
    id uuid NOT NULL,
    statusid integer NOT NULL,
    "timestamp" timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE pagos.executions_status OWNER TO postgres;

--
-- TOC entry 477 (class 1259 OID 31358)
-- Name: status; Type: TABLE; Schema: pagos; Owner: postgres
--

CREATE TABLE pagos.status (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE pagos.status OWNER TO postgres;

--
-- TOC entry 476 (class 1259 OID 31357)
-- Name: status_id_seq; Type: SEQUENCE; Schema: pagos; Owner: postgres
--

ALTER TABLE pagos.status ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME pagos.status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3896 (class 0 OID 31364)
-- Dependencies: 479
-- Data for Name: entity; Type: TABLE DATA; Schema: pagos; Owner: postgres
--

INSERT INTO pagos.entity OVERRIDING SYSTEM VALUE VALUES (1, 'OPENAPI');


--
-- TOC entry 3897 (class 0 OID 31369)
-- Dependencies: 480
-- Data for Name: entity_status; Type: TABLE DATA; Schema: pagos; Owner: postgres
--

INSERT INTO pagos.entity_status VALUES (1, 1, 1);
INSERT INTO pagos.entity_status VALUES (1, 2, 2);


--
-- TOC entry 3891 (class 0 OID 31336)
-- Dependencies: 474
-- Data for Name: executions; Type: TABLE DATA; Schema: pagos; Owner: postgres
--

INSERT INTO pagos.executions VALUES ('934959bb-12ec-4f7a-b6dd-8cac8223dffc', 1, 12235, NULL);
INSERT INTO pagos.executions VALUES ('ac23f827-7cbe-4ed8-8e40-b94c0a372006', 1, 1223, NULL);
INSERT INTO pagos.executions VALUES ('3eeaf275-78a4-4129-9728-93a8a9f3acef', 1, 12234, NULL);
INSERT INTO pagos.executions VALUES ('fcd4d5aa-ba8d-437f-8a53-425d768a7d1d', 1, 12236, NULL);
INSERT INTO pagos.executions VALUES ('ad74a923-6666-4487-995d-eea6a9505a7e', 1, 12237, NULL);
INSERT INTO pagos.executions VALUES ('368ab69e-f573-4de7-a82f-9129c191de24', 1, 12238, NULL);
INSERT INTO pagos.executions VALUES ('5da099c1-5548-4d26-9cf7-d726633b62d3', 1, 12239, NULL);
INSERT INTO pagos.executions VALUES ('308d43da-1c88-42be-88e6-9fbdf48c8aa6', 1, 12230, NULL);
INSERT INTO pagos.executions VALUES ('791bf97c-179b-42cb-8fcc-1000c3164fca', 1, 12231, NULL);
INSERT INTO pagos.executions VALUES ('577e6e23-5e56-4b03-b386-357cc0dac024', 1, 12232, NULL);
INSERT INTO pagos.executions VALUES ('b0674ab7-8099-43e4-8e80-03893655b729', 1, 12233, NULL);


--
-- TOC entry 3892 (class 0 OID 31344)
-- Dependencies: 475
-- Data for Name: executions_status; Type: TABLE DATA; Schema: pagos; Owner: postgres
--

INSERT INTO pagos.executions_status VALUES ('934959bb-12ec-4f7a-b6dd-8cac8223dffc', 1, '2022-08-18 10:44:42.923797');
INSERT INTO pagos.executions_status VALUES ('5da099c1-5548-4d26-9cf7-d726633b62d3', 1, '2022-08-18 13:50:21.573604');
INSERT INTO pagos.executions_status VALUES ('308d43da-1c88-42be-88e6-9fbdf48c8aa6', 1, '2022-08-18 14:09:00.184673');
INSERT INTO pagos.executions_status VALUES ('791bf97c-179b-42cb-8fcc-1000c3164fca', 1, '2022-08-19 08:08:14.602035');
INSERT INTO pagos.executions_status VALUES ('577e6e23-5e56-4b03-b386-357cc0dac024', 1, '2022-08-19 10:54:02.803414');
INSERT INTO pagos.executions_status VALUES ('577e6e23-5e56-4b03-b386-357cc0dac024', 2, '2022-08-19 10:59:08.711576');
INSERT INTO pagos.executions_status VALUES ('b0674ab7-8099-43e4-8e80-03893655b729', 1, '2022-08-19 11:06:04.147317');


--
-- TOC entry 3894 (class 0 OID 31358)
-- Dependencies: 477
-- Data for Name: status; Type: TABLE DATA; Schema: pagos; Owner: postgres
--

INSERT INTO pagos.status OVERRIDING SYSTEM VALUE VALUES (1, 'STARTED');
INSERT INTO pagos.status OVERRIDING SYSTEM VALUE VALUES (2, 'STEP 2');


--
-- TOC entry 3903 (class 0 OID 0)
-- Dependencies: 478
-- Name: entity_id_seq; Type: SEQUENCE SET; Schema: pagos; Owner: postgres
--

SELECT pg_catalog.setval('pagos.entity_id_seq', 2, true);


--
-- TOC entry 3904 (class 0 OID 0)
-- Dependencies: 476
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: pagos; Owner: postgres
--

SELECT pg_catalog.setval('pagos.status_id_seq', 3, true);


--
-- TOC entry 3742 (class 2606 OID 31379)
-- Name: entity entity_pk; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.entity
    ADD CONSTRAINT entity_pk PRIMARY KEY (id);


--
-- TOC entry 3746 (class 2606 OID 31400)
-- Name: entity_status entity_status_pk; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.entity_status
    ADD CONSTRAINT entity_status_pk PRIMARY KEY (identity, idstatus);


--
-- TOC entry 3744 (class 2606 OID 31381)
-- Name: entity entity_un; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.entity
    ADD CONSTRAINT entity_un UNIQUE (name);


--
-- TOC entry 3734 (class 2606 OID 31343)
-- Name: executions executions_pkey; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.executions
    ADD CONSTRAINT executions_pkey PRIMARY KEY (id);


--
-- TOC entry 3736 (class 2606 OID 31416)
-- Name: executions_status executions_status_pk; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.executions_status
    ADD CONSTRAINT executions_status_pk PRIMARY KEY (id, statusid);


--
-- TOC entry 3738 (class 2606 OID 31375)
-- Name: status status_pk; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.status
    ADD CONSTRAINT status_pk PRIMARY KEY (id);


--
-- TOC entry 3740 (class 2606 OID 31377)
-- Name: status status_un; Type: CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.status
    ADD CONSTRAINT status_un UNIQUE (name);


--
-- TOC entry 3750 (class 2606 OID 31394)
-- Name: entity_status entity_status_fk; Type: FK CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.entity_status
    ADD CONSTRAINT entity_status_fk FOREIGN KEY (identity) REFERENCES pagos.entity(id);


--
-- TOC entry 3751 (class 2606 OID 31405)
-- Name: entity_status entity_status_fk1; Type: FK CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.entity_status
    ADD CONSTRAINT entity_status_fk1 FOREIGN KEY (idstatus) REFERENCES pagos.status(id);


--
-- TOC entry 3747 (class 2606 OID 31433)
-- Name: executions executions_fk; Type: FK CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.executions
    ADD CONSTRAINT executions_fk FOREIGN KEY (entityid) REFERENCES pagos.entity(id);


--
-- TOC entry 3748 (class 2606 OID 31351)
-- Name: executions_status executions_status_fk; Type: FK CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.executions_status
    ADD CONSTRAINT executions_status_fk FOREIGN KEY (id) REFERENCES pagos.executions(id);


--
-- TOC entry 3749 (class 2606 OID 31421)
-- Name: executions_status executions_status_fk1; Type: FK CONSTRAINT; Schema: pagos; Owner: postgres
--

ALTER TABLE ONLY pagos.executions_status
    ADD CONSTRAINT executions_status_fk1 FOREIGN KEY (statusid) REFERENCES pagos.status(id);


-- Completed on 2022-08-19 11:12:23

--
-- PostgreSQL database dump complete
--

