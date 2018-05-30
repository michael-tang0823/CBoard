--

CREATE TABLE dashboard_board (
  "board_id" serial NOT NULL,
  "user_id" varchar(50) NOT NULL,
  "category_id" int,
  "board_name" varchar(100) NOT NULL,
  "layout_json" text,
  "folder_id" int DEFAULT 10000,
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "update_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY ("board_id")
);


CREATE TABLE dashboard_board_param (
  board_param_id serial,
  user_id varchar(50) NOT NULL,
  board_id int NOT NULL,
  config text,
  PRIMARY KEY (board_param_id)
);


CREATE TABLE dashboard_category (
  category_id serial,
  category_name varchar(100) NOT NULL,
  user_id varchar(100) NOT NULL,
  PRIMARY KEY (category_id)
);


CREATE TABLE dashboard_dataset (
  dataset_id serial,
  user_id varchar(100) NOT NULL,
  category_name varchar(100),
  dataset_name varchar(100),
  data_json text,
  "folder_id" int DEFAULT 10000,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dataset_id)
);

CREATE TABLE dashboard_datasource (
  datasource_id serial,
  user_id varchar(50) NOT NULL,
  source_name varchar(100) NOT NULL,
  source_type varchar(100) NOT NULL,
  config text,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (datasource_id)
);


CREATE TABLE dashboard_job (
  job_id serial,
  job_name varchar(200),
  cron_exp varchar(200),
  start_date timestamp,
  end_date timestamp,
  job_type varchar(200),
  job_config text,
  user_id varchar(100),
  last_exec_time timestamp,
  job_status int,
  exec_log text,
  PRIMARY KEY (job_id)
);


CREATE TABLE dashboard_role (
  role_id varchar(100) NOT NULL,
  role_name varchar(100),
  user_id varchar(50),
  PRIMARY KEY (role_id)
);

CREATE TABLE dashboard_role_res (
  role_res_id serial,
  role_id varchar(100),
  res_type varchar(100),
  res_id int,
  permission varchar(20),
  PRIMARY KEY (role_res_id)
);


CREATE TABLE dashboard_user (
  user_id varchar(50) NOT NULL,
  login_name varchar(100),
  user_name varchar(100),
  user_password varchar(100),
  user_status varchar(100),
  PRIMARY KEY (user_id)
);

CREATE TABLE dashboard_user_role (
  user_role_id serial,
  user_id varchar(100),
  role_id varchar(100),
  PRIMARY KEY (user_role_id)
);

CREATE TABLE dashboard_widget (
  widget_id serial,
  user_id varchar(100) NOT NULL,
  category_name varchar(100),
  widget_name varchar(100),
  data_json text,
  "folder_id" int DEFAULT 10000,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (widget_id)
);

DROP TABLE  IF EXISTS dashboard_folder;
CREATE TABLE dashboard_folder (
  folder_id serial,
  folder_name VARCHAR(50),
  parent_id int DEFAULT -1,
  is_private int DEFAULT 0,
  user_id VARCHAR(50) DEFAULT '1',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (folder_id)
);

ALTER SEQUENCE dashboard_folder_folder_id_seq
MINVALUE 10000
START 10000
RESTART 10000;

-- Root Folder
INSERT INTO dashboard_folder (folder_name,parent_id) VALUES ('Root', -1);
-- Private Folder
INSERT INTO dashboard_folder (folder_name, parent_id, is_private) VALUES ('.private', 10000, 1);
-- Dashboard Category Folder
INSERT INTO dashboard_folder (folder_name, parent_id) SELECT category_name, 10000 FROM dashboard_category;


DROP TABLE IF EXISTS Meta_Version;
CREATE TABLE Meta_Version (
  id serial PRIMARY KEY,
  name VARCHAR(50),
  status int DEFAULT 0,
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--------
INSERT INTO dashboard_user("user_id", "login_name", "user_name", "user_password", "user_status") VALUES ('1', 'admin', 'Administrator', 'ff9830c42660c1dd1942844f8069b74a', '1');
