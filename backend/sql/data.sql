INSERT INTO tennis_bird.persons (uuid, login, password, first_name, last_name, username, birth_date, telephone_number, mail_address, mail_verified) VALUES
(UNHEX(REPLACE(UUID(), '-', '')), 'kate.revinskaya', '12344321XX', 'Ekaterina', 'Revinskaya', 'kate_revinska', '2005-08-03', '987-654-3210', 'katierevinska05@gmail.com', false),
(UNHEX(REPLACE(UUID(), '-', '')), 'john.doe', 'password123', 'John', 'Doe', 'john_doe', '1990-01-15', '123-456-7890', 'john.doe@example.com', false),
(UNHEX(REPLACE(UUID(), '-', '')), 'jane.smith', 'password456', 'Jane', 'Smith', 'jane_smith', '1985-06-22', '912-654-3210', 'jane.smith@example.com', false),
(UNHEX(REPLACE(UUID(), '-', '')), 'mike.johnson', 'mikepassword', 'Mike', 'Johnson', 'mike_johnson', '1992-03-30', '555-123-4567', 'mike.johnson@example.com', false),
(UNHEX(REPLACE(UUID(), '-', '')), 'anna.brown', 'annapassword', 'Anna', 'Brown', 'anna_brown', '1998-11-05', '555-987-6543', 'anna.brown@example.com', false);

INSERT INTO tennis_bird.teams(name) VALUES('tennisBird');

INSERT INTO tennis_bird.workers(person_id, team_id, person_role)
SELECT p.uuid, t.id, 'backend_developer'
FROM tennis_bird.persons AS p, tennis_bird.teams AS t
WHERE p.username = 'kate-revinska' AND t.name = 'tennisBird';

INSERT INTO tennis_bird.tasks(code, title, status, priority)
SELECT 'BACK_123', 'prepare db', 'open', 'medium';

INSERT INTO tennis_bird.worker_tasks(worker_id, task_id, worker_role)
SELECT w.id, t.id, 'executor'
FROM tennis_bird.workers AS w
JOIN tennis_bird.persons AS p ON w.person_id = p.uuid
JOIN tennis_bird.tasks AS t ON t.code = 'BACK_123'
WHERE p.username = 'kate-revinska';

INSERT INTO tennis_bird.chat (id, name) VALUES (3, "fiirst_team");

INSERT INTO tennis_bird.chat_room_members (chat_id, member_id) VALUES
(3, (SELECT uuid FROM tennis_bird.persons WHERE username = 'john_doe')),
(3, (SELECT uuid FROM tennis_bird.persons WHERE username = 'jane_smith')),
(3, (SELECT uuid FROM tennis_bird.persons WHERE username = 'kate_revinska'));

INSERT INTO tennis_bird.chat_messages (chat_id, sender_id, content, timestamp) VALUES
(3, (SELECT id FROM tennis_bird.chat_room_members
WHERE member_id = (SELECT uuid FROM tennis_bird.persons WHERE username = 'john_doe')), 'Hello everyone!', NOW()),
(3, (SELECT id FROM tennis_bird.chat_room_members
WHERE member_id = (SELECT uuid FROM tennis_bird.persons WHERE username = 'jane_smith')), 'Hi John!', NOW()),
(3, (SELECT id FROM tennis_bird.chat_room_members
WHERE member_id = (SELECT uuid FROM tennis_bird.persons WHERE username = 'kate_revinska')), 'Need assistance.', NOW());

INSERT INTO chat_message_viewers (message_id, viewer_id, timestamp) VALUES
((SELECT id FROM chat_messages WHERE chat_id = 3 ORDER BY timestamp DESC LIMIT 1),
(SELECT id FROM tennis_bird.chat_room_members WHERE member_id = (SELECT uuid FROM tennis_bird.persons WHERE username = 'john_doe')), NOW()),
((SELECT id FROM chat_messages WHERE chat_id = 3 ORDER BY timestamp DESC LIMIT 1),
(SELECT id FROM tennis_bird.chat_room_members WHERE member_id = (SELECT uuid FROM tennis_bird.persons WHERE username = 'jane_smith')), NOW());

