ALTER TABLE public.phases
    ADD FOREIGN KEY (project_id)
    REFERENCES public.projects (id)
    NOT VALID;

ALTER TABLE public.projects
    ADD FOREIGN KEY (owner_id)
    REFERENCES public.associates (id)
    NOT VALID;

ALTER TABLE public.rates
    ADD FOREIGN KEY (owner_id)
    REFERENCES public.associates (id)
    NOT VALID;

ALTER TABLE public.rates
    ADD FOREIGN KEY (project_id)
    REFERENCES public.projects (id)
    NOT VALID;