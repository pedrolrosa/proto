CREATE TABLE public.rates (
  associate_id bigserial NOT NULL,
  project_id bigserial NOT NULL,
  score integer NOT NULL,
  PRIMARY KEY (associate_id, project_id)
);

