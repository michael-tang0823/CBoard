--

CREATE TABLE dashboard_board (
"board_id" serial NOT NULL,
"user_id" varchar(50) COLLATE "default" NOT NULL,
"category_id" int4,
"board_name" varchar(100) COLLATE "default" NOT NULL,
"layout_json" text COLLATE "default",
"create_time" timestamp(6) DEFAULT now() NOT NULL,
"update_time" timestamp(6) DEFAULT now() NOT NULL,
CONSTRAINT "dashboard_board_pkey" PRIMARY KEY ("board_id")
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
  category_name varchar(100) DEFAULT NULL,
  dataset_name varchar(100) DEFAULT NULL,
  data_json text,
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
  job_name varchar(200) DEFAULT NULL,
  cron_exp varchar(200) DEFAULT NULL,
  start_date timestamp NULL DEFAULT NULL,
  end_date timestamp NULL DEFAULT NULL,
  job_type varchar(200) DEFAULT NULL,
  job_config text,
  user_id varchar(100) DEFAULT NULL,
  last_exec_time timestamp NULL DEFAULT NULL,
  job_status int DEFAULT NULL,
  exec_log text,
  PRIMARY KEY (job_id)
);


CREATE TABLE dashboard_role (
  role_id varchar(100) NOT NULL,
  role_name varchar(100) DEFAULT NULL,
  user_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE dashboard_role_res (
  role_res_id serial,
  role_id varchar(100) DEFAULT NULL,
  res_type varchar(100) DEFAULT NULL,
  res_id int DEFAULT NULL,
  permission varchar(20) DEFAULT NULL,
  PRIMARY KEY (role_res_id)
);


CREATE TABLE dashboard_user (
  user_id varchar(50) NOT NULL,
  login_name varchar(100) DEFAULT NULL,
  user_name varchar(100) DEFAULT NULL,
  user_password varchar(100) DEFAULT NULL,
  user_status varchar(100) DEFAULT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE dashboard_user_role (
  user_role_id serial,
  user_id varchar(100) DEFAULT NULL,
  role_id varchar(100) DEFAULT NULL,
  PRIMARY KEY (user_role_id)
);

CREATE TABLE dashboard_widget (
  widget_id serial,
  user_id varchar(100) NOT NULL,
  category_name varchar(100) DEFAULT NULL,
  widget_name varchar(100) DEFAULT NULL,
  data_json text,
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
  create_time TIMESTAMP DEFAULT now(),
  update_time TIMESTAMP DEFAULT now(),
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
  create_time TIMESTAMP DEFAULT now(),
  update_time TIMESTAMP DEFAULT now()
);


--------
INSERT INTO dashboard_user("user_id", "login_name", "user_name", "user_password", "user_status") VALUES ('1', 'admin', 'Administrator', 'ff9830c42660c1dd1942844f8069b74a', '1');
