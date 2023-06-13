CREATE TABLE public.projects  (
  id bigserial NOT NULL,
  associate_id bigserial NOT NULL,
  name text NOT NULL,
  description text NOT NULL,
  area text NOT NULL,
  state text NOT NULL,
  license text NOT NULL,
  date_created date,
  active boolean DEFAULT 'true',
  PRIMARY KEY (id)
);


