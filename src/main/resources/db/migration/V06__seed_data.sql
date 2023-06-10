-- População inicial da tabela Associate
INSERT INTO associate (id, name, email, login, password, role, relevancy, date_created)
VALUES (1, 'John Doe', 'john@example.com', 'john', '123', 'USER', true, '2023-04-19');
INSERT INTO associate (id, name, email, login, password, role, relevancy, date_created)
VALUES (2, 'Jane Smith', 'jane@example.com', 'jane', '123', 'USER', false, '2023-04-19');
INSERT INTO associate (id, name, email, login, password, role, relevancy, date_created)
VALUES (3, 'Rangel DelRey', 'rangelo@example.com', 'rangel', '123', 'ADM', true, '2023-04-19');
INSERT INTO associate (id, name, email, login, password, role, relevancy, date_created)
VALUES (4, 'Rafael Sanzio', 'fael@example.com', 'fael', '123', 'ADM', true, '2023-04-19');
INSERT INTO associate (id, name, email, login, password, role, relevancy, date_created)
VALUES (5, 'Pedro', 'pedro@example.com', 'ped', '123', 'COMPANY', false, '2023-04-19');

-- População inicial da tabela Project
INSERT INTO project (id, owner_id, name, description, area, state, license, date_created)
VALUES (1, 1, 'Project 1', 'Description 1', 'Technology', 'InProgress', 'MIT', '2023-04-19');
INSERT INTO project (id, owner_id, name, description, area, state, license, date_created)
VALUES (2, 2, 'Project 2', 'Description 2', 'Investment', 'Complete', 'MIT', '2023-04-19');

-- População inicial da tabela Phase
INSERT INTO phase (id, name, description, content, project_id)
VALUES (1, 'Phase 1', 'Description of Phase 1', 'Content of Phase 1', 1);
INSERT INTO phase (id, name, description, content, project_id)
VALUES (2, 'Phase 2', 'Description of Phase 2', 'Content of Phase 2', 1);
INSERT INTO phase (id, name, description, content, project_id)
VALUES (3, 'Phase 3', 'Description of Phase 3', 'Content of Phase 3', 2);

-- População inicial da tabela Rate
INSERT INTO rate (id, owner_id, project_id, score)
VALUES (1, 1, 1, 5);
INSERT INTO rate (id, owner_id, project_id, score)
VALUES (2, 2, 2, 4);
