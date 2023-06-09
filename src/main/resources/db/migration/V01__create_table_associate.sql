CREATE TABLE public.associate (
  id serial NOT NULL,
  name text NOT NULL,
  email text NOT NULL,
  login text UNIQUE NOT NULL,
  password text NOT NULL,
  role text NOT NULL,
  relevancy boolean NOT NULL,
  date_created date,
  status text DEFAULT 'ACTIVE',
  PRIMARY KEY (id)
);
