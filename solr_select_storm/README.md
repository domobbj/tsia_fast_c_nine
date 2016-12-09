sql

```sql
CREATE TABLE `hot_select` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(255) DEFAULT '' COMMENT '关键字',
  `create_time` int(11) unsigned DEFAULT '0',
  `dt` int(11) unsigned DEFAULT '0',
  `hr` int(11) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `key` (`key`),
  KEY `dt_hr` (`dt`,`hr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```