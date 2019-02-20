-- Category
insert into tb_category values(1, 'office');
insert into tb_category values(2, 'Development');
insert into tb_category values(3, 'Utility');
insert into tb_category values(4, 'Network');
insert into tb_category values(5, 'Education');
insert into tb_category values(6, 'Graphics');
insert into tb_category values(7, 'AudioVideo');
insert into tb_category values(8, 'Game');

-- SupportSystem

insert into tb_support_system values(1, '04e4bfeb-a9b4-4be1-a65c-a2176fd186c3', 'ubuntu', '14.04');
insert into tb_support_system values(2, 'ee3f4052-3cba-41dd-8bf4-2cf917dbdb62', 'ubuntu', '16.04');

-- Package
insert into tb_package
values(
'1',
'a1d88df3-d038-40d4-b370-cdeb7fe7620f',
'apache2',
'2.4.29-1ubuntu4.5',
'2',
'Apache HTTP Server\n The Apache HTTP Server Project\'s goal is to build a secure, efficient and\n extensible HTTP server as standards-compliant open source software. The\n result has long been the number one web server on the Internet.\n .\n Installing this package results in a full installation, including the\n configuration files, init scripts and support scripts.',
'/home/swegnhan/IdeaProjects/packhub/apache2_2.4.29-1ubuntu4.5_amd64.deb',
'.deb',
now(),
now(),
'0');

-- PackSups Relation

insert into rel_pack_sups values(1, 'a1d88df3-d038-40d4-b370-cdeb7fe7620f', '04e4bfeb-a9b4-4be1-a65c-a2176fd186c3');
insert into rel_pack_sups values(2, 'a1d88df3-d038-40d4-b370-cdeb7fe7620f', 'ee3f4052-3cba-41dd-8bf4-2cf917dbdb62');