--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-08-29 14:53:13

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
-- TOC entry 5 (class 2615 OID 31453)
-- Name: onetradecommands; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA onetradecommands;


ALTER SCHEMA onetradecommands OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 482 (class 1259 OID 31454)
-- Name: entity; Type: TABLE; Schema: onetradecommands; Owner: postgres
--

CREATE TABLE onetradecommands.entity (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE onetradecommands.entity OWNER TO postgres;

--
-- TOC entry 483 (class 1259 OID 31459)
-- Name: entity_id_seq; Type: SEQUENCE; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE onetradecommands.entity ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME onetradecommands.entity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 484 (class 1259 OID 31460)
-- Name: entity_status; Type: TABLE; Schema: onetradecommands; Owner: postgres
--

CREATE TABLE onetradecommands.entity_status (
    entity_id integer NOT NULL,
    status_id integer NOT NULL,
    order_step integer NOT NULL,
    run_gateway boolean DEFAULT true NOT NULL
);


ALTER TABLE onetradecommands.entity_status OWNER TO postgres;

--
-- TOC entry 485 (class 1259 OID 31464)
-- Name: executions; Type: TABLE; Schema: onetradecommands; Owner: postgres
--

CREATE TABLE onetradecommands.executions (
    id uuid NOT NULL,
    entity_id integer NOT NULL,
    global_operation_id character varying
);


ALTER TABLE onetradecommands.executions OWNER TO postgres;

--
-- TOC entry 486 (class 1259 OID 31469)
-- Name: executions_status; Type: TABLE; Schema: onetradecommands; Owner: postgres
--

CREATE TABLE onetradecommands.executions_status (
    id uuid NOT NULL,
    status_id integer NOT NULL,
    "timestamp" timestamp without time zone DEFAULT now() NOT NULL,
    gts_message_id character varying NOT NULL
);


ALTER TABLE onetradecommands.executions_status OWNER TO postgres;

--
-- TOC entry 487 (class 1259 OID 31473)
-- Name: status; Type: TABLE; Schema: onetradecommands; Owner: postgres
--

CREATE TABLE onetradecommands.status (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE onetradecommands.status OWNER TO postgres;

--
-- TOC entry 488 (class 1259 OID 31478)
-- Name: status_id_seq; Type: SEQUENCE; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE onetradecommands.status ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME onetradecommands.status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3910 (class 0 OID 31454)
-- Dependencies: 482
-- Data for Name: entity; Type: TABLE DATA; Schema: onetradecommands; Owner: postgres
--

INSERT INTO onetradecommands.entity OVERRIDING SYSTEM VALUE VALUES (1, 'ONE-APP');
INSERT INTO onetradecommands.entity OVERRIDING SYSTEM VALUE VALUES (2, 'EMI');
INSERT INTO onetradecommands.entity OVERRIDING SYSTEM VALUE VALUES (3, 'SAAS-MASSIVE');


--
-- TOC entry 3912 (class 0 OID 31460)
-- Dependencies: 484
-- Data for Name: entity_status; Type: TABLE DATA; Schema: onetradecommands; Owner: postgres
--

INSERT INTO onetradecommands.entity_status VALUES (1, 1, 1, false);
INSERT INTO onetradecommands.entity_status VALUES (1, 2, 2, true);
INSERT INTO onetradecommands.entity_status VALUES (1, 5, 5, false);
INSERT INTO onetradecommands.entity_status VALUES (2, 1, 1, false);
INSERT INTO onetradecommands.entity_status VALUES (2, 4, 1, true);
INSERT INTO onetradecommands.entity_status VALUES (2, 5, 2, false);
INSERT INTO onetradecommands.entity_status VALUES (3, 1, 1, false);
INSERT INTO onetradecommands.entity_status VALUES (3, 2, 2, true);
INSERT INTO onetradecommands.entity_status VALUES (3, 3, 3, false);
INSERT INTO onetradecommands.entity_status VALUES (3, 4, 4, true);
INSERT INTO onetradecommands.entity_status VALUES (3, 5, 5, false);
INSERT INTO onetradecommands.entity_status VALUES (1, 3, 3, false);


--
-- TOC entry 3913 (class 0 OID 31464)
-- Dependencies: 485
-- Data for Name: executions; Type: TABLE DATA; Schema: onetradecommands; Owner: postgres
--

INSERT INTO onetradecommands.executions VALUES ('1ecfa810-8c21-4963-83bc-a53087eb0a23', 1, NULL);
INSERT INTO onetradecommands.executions VALUES ('f5ff45a0-4e2e-48d8-9ddc-d4de5ba8ae1b', 1, NULL);
INSERT INTO onetradecommands.executions VALUES ('a22d5be0-f170-485d-80e8-45f752869306', 1, NULL);
INSERT INTO onetradecommands.executions VALUES ('05671fc6-48c2-4e20-9750-1205903dfee9', 1, NULL);


--
-- TOC entry 3914 (class 0 OID 31469)
-- Dependencies: 486
-- Data for Name: executions_status; Type: TABLE DATA; Schema: onetradecommands; Owner: postgres
--

INSERT INTO onetradecommands.executions_status VALUES ('f5ff45a0-4e2e-48d8-9ddc-d4de5ba8ae1b', 1, '2022-08-29 14:07:45.972754', '29081355');
INSERT INTO onetradecommands.executions_status VALUES ('a22d5be0-f170-485d-80e8-45f752869306', 1, '2022-08-29 14:31:15.610451', '29081455');
INSERT INTO onetradecommands.executions_status VALUES ('05671fc6-48c2-4e20-9750-1205903dfee9', 1, '2022-08-29 14:32:58.288836', '29081456');
INSERT INTO onetradecommands.executions_status VALUES ('a22d5be0-f170-485d-80e8-45f752869306', 2, '2022-08-29 14:31:15.674787', '29081455');
INSERT INTO onetradecommands.executions_status VALUES ('a22d5be0-f170-485d-80e8-45f752869306', 3, '2022-08-29 14:31:16.14329', '29081455');
INSERT INTO onetradecommands.executions_status VALUES ('05671fc6-48c2-4e20-9750-1205903dfee9', 2, '2022-08-29 14:32:58.297409', '29081456');


--
-- TOC entry 3915 (class 0 OID 31473)
-- Dependencies: 487
-- Data for Name: status; Type: TABLE DATA; Schema: onetradecommands; Owner: postgres
--

INSERT INTO onetradecommands.status OVERRIDING SYSTEM VALUE VALUES (1, 'CREATED');
INSERT INTO onetradecommands.status OVERRIDING SYSTEM VALUE VALUES (2, 'SAAS');
INSERT INTO onetradecommands.status OVERRIDING SYSTEM VALUE VALUES (3, 'SAAS-PENDING');
INSERT INTO onetradecommands.status OVERRIDING SYSTEM VALUE VALUES (4, 'EMIT');
INSERT INTO onetradecommands.status OVERRIDING SYSTEM VALUE VALUES (5, 'EMITTED');


--
-- TOC entry 3922 (class 0 OID 0)
-- Dependencies: 483
-- Name: entity_id_seq; Type: SEQUENCE SET; Schema: onetradecommands; Owner: postgres
--

SELECT pg_catalog.setval('onetradecommands.entity_id_seq', 2, true);


--
-- TOC entry 3923 (class 0 OID 0)
-- Dependencies: 488
-- Name: status_id_seq; Type: SEQUENCE SET; Schema: onetradecommands; Owner: postgres
--

SELECT pg_catalog.setval('onetradecommands.status_id_seq', 3, true);


--
-- TOC entry 3753 (class 2606 OID 31480)
-- Name: entity entity_pk; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.entity
    ADD CONSTRAINT entity_pk PRIMARY KEY (id);


--
-- TOC entry 3757 (class 2606 OID 31482)
-- Name: entity_status entity_status_pk; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.entity_status
    ADD CONSTRAINT entity_status_pk PRIMARY KEY (entity_id, status_id);


--
-- TOC entry 3755 (class 2606 OID 31484)
-- Name: entity entity_un; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.entity
    ADD CONSTRAINT entity_un UNIQUE (name);


--
-- TOC entry 3759 (class 2606 OID 31486)
-- Name: executions executions_pkey; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.executions
    ADD CONSTRAINT executions_pkey PRIMARY KEY (id);


--
-- TOC entry 3761 (class 2606 OID 31488)
-- Name: executions_status executions_status_pk; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.executions_status
    ADD CONSTRAINT executions_status_pk PRIMARY KEY (id, status_id);


--
-- TOC entry 3763 (class 2606 OID 31490)
-- Name: status status_pk; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.status
    ADD CONSTRAINT status_pk PRIMARY KEY (id);


--
-- TOC entry 3765 (class 2606 OID 31492)
-- Name: status status_un; Type: CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.status
    ADD CONSTRAINT status_un UNIQUE (name);


--
-- TOC entry 3766 (class 2606 OID 31493)
-- Name: entity_status entity_status_fk; Type: FK CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.entity_status
    ADD CONSTRAINT entity_status_fk FOREIGN KEY (entity_id) REFERENCES onetradecommands.entity(id);


--
-- TOC entry 3767 (class 2606 OID 31498)
-- Name: entity_status entity_status_fk1; Type: FK CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.entity_status
    ADD CONSTRAINT entity_status_fk1 FOREIGN KEY (status_id) REFERENCES onetradecommands.status(id);


--
-- TOC entry 3768 (class 2606 OID 31503)
-- Name: executions executions_fk; Type: FK CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.executions
    ADD CONSTRAINT executions_fk FOREIGN KEY (entity_id) REFERENCES onetradecommands.entity(id);


--
-- TOC entry 3769 (class 2606 OID 31508)
-- Name: executions_status executions_status_fk; Type: FK CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.executions_status
    ADD CONSTRAINT executions_status_fk FOREIGN KEY (id) REFERENCES onetradecommands.executions(id);


--
-- TOC entry 3770 (class 2606 OID 31513)
-- Name: executions_status executions_status_fk1; Type: FK CONSTRAINT; Schema: onetradecommands; Owner: postgres
--

ALTER TABLE ONLY onetradecommands.executions_status
    ADD CONSTRAINT executions_status_fk1 FOREIGN KEY (status_id) REFERENCES onetradecommands.status(id);


-- Completed on 2022-08-29 14:53:14

--
-- PostgreSQL database dump complete
--

