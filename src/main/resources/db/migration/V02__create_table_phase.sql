CREATE TABLE public.phases (
  id bigserial NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  content text NOT NULL,
  project_id bigserial NOT NULL,
  active boolean DEFAULT 'true',
  PRIMARY KEY (id)
);


