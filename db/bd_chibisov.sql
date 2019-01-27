CREATE TABLE country (
  country_id BIGSERIAL PRIMARY KEY,
  name       VARCHAR(255) NOT NULL
);

CREATE TABLE region (
  region_id  BIGSERIAL PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  country_id BIGINT REFERENCES country (country_id)
);
CREATE TABLE city (
  city_id   BIGSERIAL PRIMARY KEY,
  name      VARCHAR(255) NOT NULL,
  region_id BIGINT REFERENCES region (region_id)
);
CREATE TABLE mayor (
  mayor_id BIGSERIAL PRIMARY KEY,
  fio      VARCHAR(255) NOT NULL,
  city_id  BIGINT REFERENCES city (city_id) ON DELETE CASCADE
);
ALTER TABLE city add mayor_id  BIGINT REFERENCES mayor (mayor_id);

CREATE TABLE attributetype (
  attribute_type_id BIGSERIAL PRIMARY KEY,
  name              VARCHAR(255) NOT NULL
);

CREATE TABLE attribute (
  attribute_id      BIGSERIAL PRIMARY KEY,
  name              VARCHAR(255) NOT NULL,
  value             VARCHAR(255),
  attribute_type_id BIGINT REFERENCES attributetype (attribute_type_id)
);

CREATE TABLE city_attribute(
  city_id BIGINT REFERENCES city(city_id),
  attribute_id BIGINT REFERENCES attribute(attribute_id),
  PRIMARY KEY (city_id, attribute_id)
);
