-- changeset nerkebaev:1
insert into users(id, username, email, role, password)
values (1, 'administrator', 'admin@gmail.com', 'ROLE_ADMIN', '$2a$10$o8jCeoI3w56npO2Osj62BOGEr2INHrdM2tPToiAi9sIoxoBpmMk7q'),
       (2, 'moderator', 'moderator@gmail.com', 'ROLE_MODERATOR', '$2a$10$By56WFYnfB1tN1Mcs4.eDeYxXQDwEqxIL11ONfy.0T2niIcA07o3y')

-- $2a$10$o8jCeoI3w56npO2Osj62BOGEr2INHrdM2tPToiAi9sIoxoBpmMk7q (administrator)
-- $2a$10$By56WFYnfB1tN1Mcs4.eDeYxXQDwEqxIL11ONfy.0T2niIcA07o3y (moderator)