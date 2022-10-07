INSERT INTO "user"
(first_name, last_name, email, active, "password", creation_date)
VALUES('Super', 'Admin', 'admin@mail.com', true, '$2a$10$TkbIpSLDOlwsze/ICSFqxOMYsliFkHHwXEfQEHTeo/XGAE8kmqzom', current_timestamp);

INSERT INTO user_role (id, "user", "role") VALUES('userRoleId01', 1, 1);

INSERT INTO "user"
(first_name, last_name, email, active, "password", creation_date)
VALUES('User', 'Admin', 'user.admin@mail.com', true, '$2a$10$TkbIpSLDOlwsze/ICSFqxOMYsliFkHHwXEfQEHTeo/XGAE8kmqzom', current_timestamp);

INSERT INTO user_role (id, "user", "role") VALUES('userRoleId02', 2, 2);

INSERT INTO "user"
(first_name, last_name, email, active, "password", creation_date)
VALUES('Store', 'Manager', 'store.manager@mail.com', true, '$2a$10$TkbIpSLDOlwsze/ICSFqxOMYsliFkHHwXEfQEHTeo/XGAE8kmqzom', current_timestamp);

INSERT INTO user_role (id, "user", "role") VALUES('userRoleId03', 3, 3);