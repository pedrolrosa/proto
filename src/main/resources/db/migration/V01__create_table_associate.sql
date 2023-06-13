CREATE TABLE public.associates (
  id serial NOT NULL,
  name text NOT NULL,
  email text NOT NULL,
  login text UNIQUE NOT NULL,
  password text NOT NULL,
  role text NOT NULL,
  relevancy boolean NOT NULL,
  date_created date,
  active boolean DEFAULT 'true',
  PRIMARY KEY (id)
);

-- teste

/*CREATE TABLE public.associate_interests (
  id serial NOT NULL,
  associate_id serial NOT NULL,
  interests text NOT NULL,
  PRIMARY KEY (id)
);*/
