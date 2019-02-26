CREATE TABLE `tb_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `tb_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `category` int(11) DEFAULT NULL,
  `description` varchar(1023) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `filetype` enum('.deb','.rpm') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `downloads` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`),
  UNIQUE KEY `name` (`name`,`version`),
  KEY `category` (`category`),
  CONSTRAINT `tb_packlist_ibfk_1` FOREIGN KEY (`category`) REFERENCES `tb_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1


CREATE TABLE `tb_support_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL UNIQUE,
  `name` varchar(255) NOT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `rel_pack_sups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pack_uid` varchar(255) NOT NULL,
  `sups_uid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`pack_uid`,`sups_uid`),
  CONSTRAINT `rel_pack_sups_ibfk_1` FOREIGN KEY (`pack_uid`) REFERENCES `tb_package` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rel_pack_sups_ibfk_2` FOREIGN KEY (`sups_uid`) REFERENCES `tb_support_system` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pack_uid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`),
  KEY `pack_uid` (`pack_uid`),
  CONSTRAINT `tb_comment_ibfk_1` FOREIGN KEY (`pack_uid`) REFERENCES `tb_package` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1