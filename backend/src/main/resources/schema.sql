CREATE DATABASE tennis_bird;

CREATE TABLE persons (
  uuid UUID NOT NULL PRIMARY KEY,
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

CREATE UNIQUE INDEX ix_unique_telephone_number_persons ON persons(telephone_number);
CREATE UNIQUE INDEX ix_unique_username_persons ON persons(username);
CREATE UNIQUE INDEX ix_unique_login_persons ON persons(login);

CREATE TABLE teams (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE UNIQUE INDEX ix_unique_name_teams ON teams(name);

CREATE TABLE workers (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  person_id UUID NOT NULL,
  team_id BIGINT NOT NULL,
  person_role VARCHAR(200) NOT NULL,
  foreign key (person_id) references persons(uuid) ON DELETE CASCADE,
  foreign key (team_id) references teams(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_worker ON workers(person_id, team_id, person_role);

CREATE TABLE tasks (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  code VARCHAR(200) NOT NULL,
  title VARCHAR(200) NOT NULL,
  description VARCHAR(200) NULL,
  status VARCHAR(200) NOT NULL DEFAULT 'open',
  priority VARCHAR(200) NOT NULL DEFAULT 'medium',
  estimate INT NULL
);

CREATE UNIQUE INDEX ix_unique_code_tasks ON tasks(code);

CREATE TABLE worker_tasks (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  worker_id BIGINT NOT NULL,
  task_id BIGINT NOT NULL,
  worker_role VARCHAR(200) NOT NULL, --executors observers author
  foreign key (worker_id) references workers(id) ON DELETE CASCADE,
  foreign key (task_id) references tasks(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_worker_tasks ON worker_tasks(worker_id, task_id);

CREATE TABLE chat (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  name VARCHAR(200) NOT NULL
);

CREATE TABLE chat_room_members (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  chat_id BIGINT NOT NULL,
  member_id UUID NOT NULL,
  foreign key (chat_id) references chat(id) ON DELETE CASCADE,
  foreign key (member_id) references persons(uuid) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_chat_member ON chat_room_members(chat_id, member_id);

CREATE TABLE chat_messages (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  chat_id BIGINT NOT NULL,
  sender_id BIGINT NOT NULL,
  content VARCHAR(255) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  foreign key (chat_id) references chat(id) ON DELETE CASCADE,
  foreign key (sender_id) references chat_room_members(id) ON DELETE CASCADE
);

CREATE TABLE chat_message_viewers (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY DEFAULT ON NULL PRIMARY KEY,
  message_id BIGINT NOT NULL,
  viewer_id BIGINT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  foreign key (message_id) references chat_messages(id) ON DELETE CASCADE,
  foreign key (viewer_id) references chat_room_members(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_unique_message_viewer ON chat_message_viewers(message_id, viewer_id);