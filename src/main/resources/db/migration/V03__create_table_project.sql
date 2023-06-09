CREATE TABLE public.project  (
  id bigserial NOT NULL,
  owner_id bigserial NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  area text NOT NULL,
  state text NOT NULL,
  license text NOT NULL,
  date_creation date,
  PRIMARY KEY (id)
);


