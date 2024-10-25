CREATE TABLE IF NOT EXISTS `sys_user`
(
    `id`
    INTEGER
    PRIMARY
    KEY
    auto_increment,
    `name`
    VARCHAR
(
    100
),
    `role_id` INTEGER NOT NULL default 1,
    `create_date` DATE NOT NULL default current_date
(
),
    `create_time` DATETIME NOT NULL DEFAULT NOW
(
)
    );


CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`
    INTEGER
    PRIMARY
    KEY
    auto_increment,
    `name`
    VARCHAR
(
    100
) default '',
    `create_time` DATETIME NOT NULL DEFAULT NOW
(
)
    );
