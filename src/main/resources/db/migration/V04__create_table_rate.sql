CREATE TABLE public.rates (
  id bigserial NOT NULL,
  owner_id bigserial NOT NULL,
  project_id bigserial NOT NULL,
  score integer NOT NULL,
  PRIMARY KEY (id)
);

