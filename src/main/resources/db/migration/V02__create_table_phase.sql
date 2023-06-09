CREATE TABLE public.phase (
  id bigserial NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  content text NOT NULL,
  project_id bigserial NOT NULL,
  status text DEFAULT 'ACTIVE',
  PRIMARY KEY (id)
);


