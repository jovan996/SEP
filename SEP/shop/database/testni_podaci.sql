USE patike;

INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(1, "PUMA PUMA NRGY Neko Turbo", "M", "", 7990, 1, 1, 3, 1);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(2, "Adidas SENSEBOOST GO M", "M", "", 12951, 1, 1, 2, 1);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(3, "NIKE NIKE AIR MAX EXCEE", "M", "", 13691, 1, 2, 1, 1);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(4, "NIKE WMNS NIKE REVOLUTION 5", "Ž", "", 5841, 1, 2, 1, 1);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(5, "CONVERSE Chuck Taylor All Star", "M", "", 4990, 1, 2, 4, 1);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(6, "Nicola Benson cipele", "M", "NicolaBenson - ART2542B X86-NERO/BLU/GRIGIO-jz20", 13999, 1, 1, 5, 2);

INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(7, "Nicola Benson cipele", "M", "", 12999, 0, 3, 5, 2);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(8, "Nicola Benson cipele", "M", "", 11999, 0, 1, 5, 2);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(9, "Nero Giardini cipele visoka peta", "Ž", "NG-011070 nappa.pan.nero/tpu.dim.nero/pl20", 17999, 0, 1, 6, 2);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(10, "Nero Giardini cipele visoka peta", "Ž", "NG-011072 nappa.pan.nero/tpu.dim.nero/pl20", 20999, 0, 1, 6, 2);
INSERT INTO artikli(id, naziv, pol, opis, cena, istaknut, boje_id, brendovi_id, vrste_id)
	VALUES(11, "Antonella Rossi cipele ravne", "Ž", "AR6457-2554R camoscio-carusso-blu Zenska cipela ravna PL20", 8999, 0, 3, 6, 2);

INSERT INTO korisnici(id, email, lozinka, ime, prezime, token)
	VALUES(1, "grgurjovan.jg@gmail.com", "123456789", "Jovan", "Grgur", NULL);

INSERT INTO korisnici_uloge(korisnici_id, uloge_id)
	VALUES(1, 1);
INSERT INTO korisnici_uloge(korisnici_id, uloge_id)
	VALUES(1, 2);

INSERT INTO adrese(id, korisnici_id, mesto, postanski_broj, adresa)
	VALUES(1, 1, "Novi Sad", "21000", "Futoska 17")
