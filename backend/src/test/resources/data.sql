INSERT INTO persons(first_name, last_name, nickname, birth_date, mail_address)
VALUES('Ekaterina', 'Revinskaya', 'kate-revinska', '2005-08-03', 'katierevinska05@gmail.com');

INSERT INTO teams(name) VALUES('tennisBird');

INSERT INTO workers(person_id, team_id, person_role)
SELECT p.id, t.id, 'backend_developer'
FROM persons AS p, teams AS t
WHERE p.nickname = 'kate-revinska' AND t.name = 'tennisBird';

INSERT INTO tasks(code, title, author_id, status, priority)
SELECT 'BACK_123', 'prepare db', w.id, 'open', 'medium'
FROM workers AS w
JOIN persons AS p ON w.person_id = p.id
WHERE p.nickname = 'kate-revinska';

INSERT INTO worker_tasks(worker_id, task_id, worker_role)
SELECT w.id, t.id, 'executor'
FROM workers AS w
JOIN persons AS p ON w.person_id = p.id
JOIN tasks AS t ON t.code = 'BACK_123'
WHERE p.nickname = 'kate-revinska';