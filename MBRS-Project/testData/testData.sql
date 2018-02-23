insert into mbrs.country (code, name) values ('SRB', 'Serbia');
insert into mbrs.country (code, name) values ('RUS', 'Russia');
insert into mbrs.country (code, name) values ('ESP', 'Spain');
insert into mbrs.country (code, name) values ('FRA', 'France');
insert into mbrs.country (code, name) values ('ITA', 'Italia');
insert into mbrs.country (code, name) values ('GRB', 'Great Britain');

insert into mbrs.city (code, name, country) values ('ZR', 'Zrenjanin', '1');
insert into mbrs.city (code, name, country) values ('NS', 'Novi Sad', '1');
insert into mbrs.city (code, name, country) values ('BG', 'Belgrade', '1');
insert into mbrs.city (code, name, country) values ('MO', 'Moscow', '2');
insert into mbrs.city (code, name, country) values ('MD', 'Madrid', '3');
insert into mbrs.city (code, name, country) values ('BR', 'Barcelona', '3');
insert into mbrs.city (code, name, country) values ('PR', 'Paris', '4');
insert into mbrs.city (code, name, country) values ('RO', 'Rome', '5');
insert into mbrs.city (code, name, country) values ('LD', 'London', '6');
insert into mbrs.city (code, name, country) values ('LP', 'Liverpool', '6');

insert into mbrs.currency (code, name, country, domicile) values ('RSD','Dinar','1',1);
insert into mbrs.currency (code, name, country, domicile) values ('RUB','Ruble','2',1);
insert into mbrs.currency (code, name, country, domicile) values ('EUR','Euro','3',0);
insert into mbrs.currency (code, name, country, domicile) values ('GBP','Pound','6',1);

insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('12312312564', 'Zrenjaninska banka', '1', 'Zrenjaninska ulica 2', '023564825', 'zr@banka.rs');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('12354685215', 'Novosadska banka', '2', 'Novosadska ulica 3', '021458365', 'ns@banka.rs');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('12385467712', 'NS banka', '2', 'Novosadska ulica 4', '021635421', 'nsb@banka.rs');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('12365231452', 'Beogradska banka', '3', 'Beogradska ulica 3', '011523524', 'bg@banka.rs');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('68521425354', 'Bank of Moscow', '4', 'Moscow street 1', '075631524', 'mo@bank.ru');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('15642156412', 'Bank of Paris', '7', 'Paris street 2', '085462151', 'pr@bank.fr');
insert into mbrs.bank (swift, name, city, address, phoneNumber, email) values ('75314425456', 'Bank of Liverpool', '10', 'Liverpool ulica 5', '036521523', 'lp@bank.uk');

insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('165823574','1','2015-12-7','1','1','1');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('268532101','1','2016-3-24','1','2','1');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('456489123','2','2007-5-16','3','0','1');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('468512345','3','2001-10-8','1','3','0');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('851223458','5','2012-7-12','2','0','1');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('632511542','6','2016-8-4','3','1','0');
insert into mbrs.account (code, bank, est, currency, paymentMethod, active) values ('652315242','7','2012-12-12','4','2','1');

insert into mbrs.company (address, description, email, idNumber, name, phoneNumber, account, city) values ('Zrenjaninski trg', 'Srpska kompanija iz Zrenjanina', 'zr@comp.rs', '5232145', 'Zr kompanija', '023856421', '1', '1');
insert into mbrs.company (address, description, email, idNumber, name, phoneNumber, account, city) values ('Madrid square', 'Spanish company from Madrid', 'md@comp.es', '6325124', 'Mardid co', '064853256', '3', '5');
insert into mbrs.company (address, description, email, idNumber, name, phoneNumber, account, city) values ('Red square', 'Russian company from Moscow', 'mo@comp.ru', '7521452', 'Moscow company', '065321425', '4', '4');
insert into mbrs.company (address, description, email, idNumber, name, phoneNumber, account, city) values ('Camp Nou', 'Spanish company from Barcelona', 'br@comp.es', '1235425', 'Barca co', '085654123', '6', '6');
insert into mbrs.company (address, description, email, idNumber, name, phoneNumber, account, city) values ('London street', 'British company from London', 'ld@comp.uk', '4856325', 'British comp', '065321485', '5', '9');

insert into mbrs.person (jmbg, firstName, lastName, phoneNumber, account, city, address) values ('2586325412531', 'Pera', 'Peric', '023564123', '1', '1', 'ZR ulica');
insert into mbrs.person (jmbg, firstName, lastName, phoneNumber, account, city, address) values ('4521228699356', 'Mika', 'Mikic', '021564535', '3', '2', 'Mikina ulica');
insert into mbrs.person (jmbg, firstName, lastName, phoneNumber, account, city, address) values ('6532564856791', 'Carles', 'Puyol', '064512354', '4', '6', 'Camp Nou');
insert into mbrs.person (jmbg, firstName, lastName, phoneNumber, account, city, address) values ('3562148521554', 'Steven', 'Gerrard', '064324521', '7', '10', 'Anfield');
insert into mbrs.person (jmbg, firstName, lastName, phoneNumber, account, city, address) values ('4851315645121', 'Vladimir', 'Putin', '069841324', '2', '4', 'Kremlin');
