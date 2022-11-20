SELECT * FROM tenmo_user;
INSERT INTO tenmo_user (username, password_hash) VALUES ('test','test1') RETURNING user_id;