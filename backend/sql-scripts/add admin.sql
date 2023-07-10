-- Add user admin
-- password '$2a$10$9AG1FdIHKUqT1a0TyvbqBugUYi0rt8OW8qIpoYNsksl7yhB3ehnHC' stands for "test"
INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `username`)
VALUES (2, "admin@gmail.com", "aN", "aLN", "$2a$10$9AG1FdIHKUqT1a0TyvbqBugUYi0rt8OW8qIpoYNsksl7yhB3ehnHC", "adm");

-- Add admin role to created user
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES (2, 3);