-- Database: gisbase

-- DROP DATABASE gisbase;

CREATE DATABASE gisbase
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_GB.UTF-8'
       LC_CTYPE = 'en_GB.UTF-8'
       CONNECTION LIMIT = -1;
       
       
       
-- Table: agentposition

-- DROP TABLE agentposition;

CREATE TABLE agentposition
(
  idauto SERIAL, "agentId" character varying NOT NULL,
  course real NOT NULL,
  "simulationTime" bigint NOT NULL,
  speed real NOT NULL,
  coordinates point NOT NULL
)
WITH (
  OIDS=FALSE,
  autovacuum_enabled=true
);
ALTER TABLE agentposition
  OWNER TO postgres;

  
  
  
  
  

-- Table: agentpositionghost

-- DROP TABLE agentpositionghost;

CREATE TABLE agentpositionghost
(
   idauto SERIAL,  "agentId" character varying NOT NULL,
  course real NOT NULL,
  "simulationTime" bigint NOT NULL,
  speed real NOT NULL,
  coordinates point NOT NULL
)
WITH (
  OIDS=FALSE,
  autovacuum_enabled=true
);
ALTER TABLE agentpositionghost
  OWNER TO postgres;

