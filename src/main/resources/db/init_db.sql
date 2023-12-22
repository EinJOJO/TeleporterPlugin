CREATE TABLE IF NOT EXISTS `teleporter`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT,
    `placed_by` varchar(36)  NOT NULL,
    `name`      varchar(255) NOT NULL UNIQUE,
    `x`         double       NOT NULL,
    `y`         double       NOT NULL,
    `z`         double       NOT NULL,
    `yaw`       float        NOT NULL,
    `pitch`     float        NOT NULL,
    `world`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
)