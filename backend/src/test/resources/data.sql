INSERT INTO persons(first_name, last_name, nickname, birth_date, mail_address)
VALUES('Ekaterina', 'Revinskaya', 'kate-revinska', '2005-08-03', 'katierevinska05@gmail.com');

--INSERT INTO teams(name) VALUES('tennisBird');
--
--INSERT INTO workers(person_id, team_id, person_role)
--SELECT p.id FROM persons as p
--WHERE p.nickname = 'kate-revinska',
--SELECT t.id FROM teams as t
--WHERE t.name = 'tennisBird',
--'backend_developer'
--
--INSERT INTO tasks(code, title, author_id, status, priority)
--'BACK_123', 'prepare db',
--SELECT w.id FROM workers as w
--JOIN persons as p ON w.person_id = p.id
--WHERE p.nickname = 'kate-revinska',
--'open', 'medium'
--
--INSERT INTO worker_tasks(worker_id, task_id, worker_role)
--SELECT w.id FROM workers as w
--JOIN persons as p ON w.person_id = p.id
--WHERE p.nickname = 'kate-revinska',
--SELECT t.id FROM tasks as t
--WHERE t.code = 'BACK_123',
--'executor'