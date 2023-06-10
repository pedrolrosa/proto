CREATE TABLE public.projects  (
  id bigserial NOT NULL,
  owner_id bigserial NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  area text NOT NULL,
  state text NOT NULL,
  license text NOT NULL,
  date_created date,
  status text DEFAULT 'ACTIVE',
  PRIMARY KEY (id)
);


