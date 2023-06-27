-- População inicial da tabela Associate
INSERT INTO associates (id, name, email, login, password, role, relevancy, date_created)
VALUES (100, 'John Doe', 'john@example.com', 'john', '{noop}123', 'USER', true, '2023-04-19');
INSERT INTO associates (id, name, email, login, password, role, relevancy, date_created)
VALUES (200, 'Jane Smith', 'jane@example.com', 'jane', '{noop}123', 'USER', false, '2023-04-19');
INSERT INTO associates (id, name, email, login, password, role, relevancy, date_created)
VALUES (300, 'Rangel DelRey', 'rangelo@example.com', 'rangel', '{noop}123', 'ADM', true, '2023-04-19');
INSERT INTO associates (id, name, email, login, password, role, relevancy, date_created)
VALUES (400, 'Rafael Sanzio', 'fael@example.com', 'fael', '{noop}123', 'ADM', true, '2023-04-19');
INSERT INTO associates (id, name, email, login, password, role, relevancy, date_created)
VALUES (500, 'Pedro', 'pedro@example.com', 'ped', '{noop}123', 'COMPANY', false, '2023-04-19');

-- População inicial da tabela Project
INSERT INTO projects (id, associate_id, name, description, area, state, license, date_created)
VALUES (100, 100, 'Project 1', 'Description 1', 'TECHNOLOGY', 'IN_PROGRESS', 'MIT', '2023-04-19');
INSERT INTO projects (id, associate_id, name, description, area, state, license, date_created)
VALUES (200, 200, 'Project 2', 'Description 2', 'INVESTMENT', 'COMPLETE', 'MIT', '2023-04-19');

-- População inicial da tabela Phase
INSERT INTO phases (id, name, description, content, project_id)
VALUES (100, 'Phase 1', 'Description of Phase 1', 'Content of Phase 1', 100);
INSERT INTO phases (id, name, description, content, project_id)
VALUES (200, 'Phase 2', 'Description of Phase 2', 'Content of Phase 2', 100);
INSERT INTO phases (id, name, description, content, project_id)
VALUES (300, 'Phase 3', 'Description of Phase 3', 'Content of Phase 3', 200);

