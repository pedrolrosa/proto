CREATE TABLE public.rates (
  associate_login text NOT NULL,
  project_id bigserial NOT NULL,
  score integer NOT NULL,
  PRIMARY KEY (associate_login, project_id)
);

