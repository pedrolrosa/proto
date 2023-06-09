ALTER TABLE public.phase
    ADD FOREIGN KEY (project_id)
    REFERENCES public.project (id)
    NOT VALID;

ALTER TABLE public.project 
    ADD FOREIGN KEY (owner_id)
    REFERENCES public.associate (id)
    NOT VALID;

ALTER TABLE public.rate
    ADD FOREIGN KEY (owner_id)
    REFERENCES associate (id)
    NOT VALID;

ALTER TABLE public.rate
    ADD FOREIGN KEY (project_id)
    REFERENCES public.project (id)
    NOT VALID;