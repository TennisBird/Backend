INSERT INTO persons (uuid, login, password, first_name, last_name, username, birth_date, telephone_number, mail_address) VALUES
(uuid(), 'kate.revinskaya', '12344321XX', 'Ekaterina', 'Revinskaya', 'kate-revinska', '2005-08-03', '', 'katierevinska05@gmail.com'),
(uuid(), 'john.doe', 'password123', 'John', 'Doe', 'john_doe', '1990-01-15', '123-456-7890', 'john.doe@example.com'),
(uuid(), 'jane.smith', 'password456', 'Jane', 'Smith', 'jane_smith', '1985-06-22', '987-654-3210', 'jane.smith@example.com'),
(uuid(), 'mike.johnson', 'mikepassword', 'Mike', 'Johnson', 'mike_johnson', '1992-03-30', '555-123-4567', 'mike.johnson@example.com'),
(uuid(), 'anna.brown', 'annapassword', 'Anna', 'Brown', 'anna_brown', '1998-11-05', '555-987-6543', 'anna.brown@example.com');

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

INSERT INTO chat (id) VALUES (1);

INSERT INTO chat_room_members (chat_id, member_id) VALUES
(1, (SELECT uuid FROM persons WHERE username = 'john_doe')),
(1, (SELECT uuid FROM persons WHERE username = 'jane_smith')),
(1, (SELECT uuid FROM persons WHERE username = 'kate_revinska'));

INSERT INTO chat_messages (chat_id, sender_id, content, timestamp) VALUES
(1, (SELECT uuid FROM persons WHERE username = 'john_doe'), 'Hello everyone!', NOW()),
(1, (SELECT uuid FROM persons WHERE username = 'jane_smith'), 'Hi John!', NOW()),
(1, (SELECT uuid FROM persons WHERE username = 'kate_revinska'), 'Need assistance.', NOW());

INSERT INTO chat_message_viewers (message_id, viewer_id, timestamp) VALUES
((SELECT id FROM chat_messages WHERE chat_id = 1 ORDER BY timestamp DESC LIMIT 1), (SELECT uuid FROM persons WHERE username = 'jane_smith'), NOW()),
((SELECT id FROM chat_messages WHERE chat_id = 1 ORDER BY timestamp DESC LIMIT 1), (SELECT uuid FROM persons WHERE username = 'kate_revinska'), NOW());