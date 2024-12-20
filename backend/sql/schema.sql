CREATE DATABASE tennis_bird;

CREATE TABLE tennis_bird.persons (
  uuid  BINARY(16) NOT NULL PRIMARY KEY,
  login VARCHAR(200) NOT NULL,
  password VARCHAR(200) NOT NULL,
  first_name VARCHAR(200) NOT NULL,
  last_name VARCHAR(200) NOT NULL,
  username VARCHAR(200) NOT NULL,
  birth_date TIMESTAMP NOT NULL,
  telephone_number VARCHAR(200) NOT NULL,
  mail_address VARCHAR(200) NOT NULL,
  mail_verified BOOLEAN not null
);

CREATE UNIQUE INDEX ix_unique_telephone_number_persons ON tennis_bird.persons(telephone_number);
CREATE UNIQUE INDEX ix_unique_username_persons ON tennis_bird.persons(username);
CREATE UNIQUE INDEX ix_unique_login_persons ON tennis_bird.persons(login);

CREATE TABLE tennis_bird.teams (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE UNIQUE INDEX ix_unique_name_teams ON tennis_bird.teams(name);

CREATE TABLE tennis_bird.workers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  person_id BINARY(16) NOT NULL,
  team_id BIGINT NOT NULL,
  person_role VARCHAR(200) NOT NULL,
  foreign key (person_id) references tennis_bird.persons(uuid) ON DELETE CASCADE,
  foreign key (team_id) references tennis_bird.teams(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_worker ON tennis_bird.workers(person_id, team_id, person_role);

CREATE TABLE tennis_bird.tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(200) NOT NULL,
  title VARCHAR(200) NOT NULL,
  description VARCHAR(200) NULL,
  status VARCHAR(200) NOT NULL DEFAULT 'open',
  priority VARCHAR(200) NOT NULL DEFAULT 'medium',
  estimate INT NULL
);

CREATE UNIQUE INDEX ix_unique_code_tasks ON tennis_bird.tasks(code);

CREATE TABLE worker_tasks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  worker_id BIGINT NOT NULL,
  task_id BIGINT NOT NULL,
  worker_role VARCHAR(200) NOT NULL,
  foreign key (worker_id) references tennis_bird.workers(id) ON DELETE CASCADE,
  foreign key (task_id) references tennis_bird.tasks(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_worker_tasks ON tennis_bird.worker_tasks(worker_id, task_id);

CREATE TABLE tennis_bird.chat (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE TABLE tennis_bird.chat_room_members (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  chat_id BIGINT NOT NULL,
  member_id BINARY(16) NOT NULL,
  foreign key (chat_id) references tennis_bird.chat(id) ON DELETE CASCADE,
  foreign key (member_id) references tennis_bird.persons(uuid) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_chat_member ON tennis_bird.chat_room_members(chat_id, member_id);

CREATE TABLE tennis_bird.chat_messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  chat_id BIGINT NOT NULL,
  sender_id BIGINT NOT NULL,
  content VARCHAR(255) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  foreign key (chat_id) references tennis_bird.chat(id) ON DELETE CASCADE,
  foreign key (sender_id) references tennis_bird.chat_room_members(id) ON DELETE CASCADE
);

CREATE TABLE chat_message_viewers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  message_id BIGINT NOT NULL,
  viewer_id BIGINT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  foreign key (message_id) references chat_messages(id) ON DELETE CASCADE,
  foreign key (viewer_id) references chat_room_members(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_message_viewer ON chat_message_viewers(message_id, viewer_id);