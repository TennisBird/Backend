INSERT INTO persons(uuid, login, password, first_name, last_name, username, birth_date, telephone_number, mail_address)
VALUES(
uuid(), 'kate.revinskaya', '12344321XX', 'Ekaterina', 'Revinskaya',
'kate-revinska', '2005-08-03', '', 'katierevinska05@gmail.com');

INSERT INTO teams(name) VALUES('tennisBird');

INSERT INTO workers(person_id, team_id, person_role)
SELECT p.uuid, t.id, 'backend_developer'
FROM persons AS p, teams AS t
WHERE p.username = 'kate-revinska' AND t.name = 'tennisBird';

INSERT INTO tasks(code, title, status, priority)
SELECT 'BACK_123', 'prepare db', 'open', 'medium';

INSERT INTO worker_tasks(worker_id, task_id, worker_role)
SELECT w.id, t.id, 'executor'
FROM workers AS w
JOIN persons AS p ON w.person_id = p.uuid
JOIN tasks AS t ON t.code = 'BACK_123'
WHERE p.username = 'kate-revinska';