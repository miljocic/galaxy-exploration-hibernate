CREATE DATABASE milica_jocic;

-- Tabela za galaksije
CREATE TABLE Galaksija (
    GalaksijaID INT  PRIMARY KEY,
    Ime VARCHAR(100) NOT NULL,
    Masa DECIMAL(25, 10),
    ProsecniPrecnik DECIMAL(25, 5),
    UgaoniMomenat DECIMAL(25, 10)
);

-- Tabela za morfoloske tipove galaksija
CREATE TABLE MorfoloskiTipovi (
    MorfoloskiTipID INT  PRIMARY KEY, 
    TipIme VARCHAR(100) NOT NULL
);

-- Veza izmedju galaksija i morfoloskih tipova
CREATE TABLE GalaksijaMorfoloskiTip (
    GalaksijaID INT,
    MorfoloskiTipID INT,
    PRIMARY KEY (GalaksijaID, MorfoloskiTipID),
    FOREIGN KEY (GalaksijaID) REFERENCES Galaksija(GalaksijaID),
    FOREIGN KEY (MorfoloskiTipID) REFERENCES MorfoloskiTipovi(MorfoloskiTipID)
);

-- Tabela za nebeska tela
CREATE TABLE NebeskoTelo (
    TeloID INT PRIMARY KEY,
    Ime VARCHAR(100) NOT NULL,
    Tip VARCHAR(50),
    ProsecniRadijus DECIMAL(10, 2),
    EkvatorijalniRadijus DECIMAL(10, 2),
    PolarniRadijus DECIMAL(10, 2),
    Zapremina DECIMAL(20, 5),
    Masa DECIMAL(30, 15),
    MinTemp DECIMAL(10, 2),
    MaxTemp DECIMAL(10, 2),
    RotacijaTrajanje DECIMAL(10, 5),
    RevolucijaTrajanje DECIMAL(10, 5),
    TeloOkretanjaID INT,
    GalaksijaID INT,
    FOREIGN KEY (TeloOkretanjaID) REFERENCES NebeskoTelo(TeloID),
    FOREIGN KEY (GalaksijaID) REFERENCES Galaksija(GalaksijaID)
);

-- Tabela za institucije
CREATE TABLE Institucija (
    InstitucijaID INT PRIMARY KEY,
    Ime VARCHAR(100) NOT NULL,
    Grad VARCHAR(50),
    Drzava VARCHAR(50)
);

-- Tabela za istrazivace
CREATE TABLE Istrazivac (
    IstrazivacID INT PRIMARY KEY,
    Ime VARCHAR(50) NOT NULL,
    Prezime VARCHAR(50) NOT NULL,
    GradRodjenja VARCHAR(50),
    DrzavaRodjenja VARCHAR(50),
    NivoObrazovanja VARCHAR(100),
    InstitucijaID INT,
    FOREIGN KEY (InstitucijaID) REFERENCES Institucija(InstitucijaID)
);

-- Tabela za kategorije istrazivaca
CREATE TABLE KategorijaIstrazivaca (
    KategorijaID INT PRIMARY KEY,
    KategorijaIme VARCHAR(50) NOT NULL,
    IdeUSvemir BOOLEAN NOT NULL
);

CREATE SEQUENCE kategorija_istrazivaca_seq;

ALTER TABLE KategorijaIstrazivaca ALTER COLUMN KategorijaID SET DEFAULT nextval('kategorija_istrazivaca_seq');

SELECT setval('kategorija_istrazivaca_seq', (SELECT MAX(KategorijaID) FROM KategorijaIstrazivaca));


CREATE TYPE status_enum AS ENUM ('u planu', 'zapoceta', 'uspesna', 'neuspesna');

-- Tabela za misije
CREATE TABLE Misija (
    MisijaID INT PRIMARY KEY,
    MisijaIme VARCHAR(100) NOT NULL,
    Cilj TEXT,
    Ishod TEXT,
    Status status_enum NOT NULL
);

-- Veza izmedju misija i istrazivaca
CREATE TABLE MisijaIstrazivac (
    ID SERIAL PRIMARY KEY,
    MisijaID INT NOT NULL,
    IstrazivacID INT NOT NULL,
    UlogaID INT NOT NULL,
    FOREIGN KEY (MisijaID) REFERENCES Misija(MisijaID),
    FOREIGN KEY (IstrazivacID) REFERENCES Istrazivac(IstrazivacID),
    FOREIGN KEY (UlogaID) REFERENCES Uloga(UlogaID)
);

-- Veza izmedju misija i nebeskih tela
CREATE TABLE MisijaCilj (
    MisijaID INT,
    TeloID INT,
    PRIMARY KEY (MisijaID, TeloID),
    FOREIGN KEY (MisijaID) REFERENCES Misija(MisijaID),
    FOREIGN KEY (TeloID) REFERENCES NebeskoTelo(TeloID)
);

-- Tabela za sumove
CREATE TABLE Sum (
    SumID INT PRIMARY KEY,
    MisijaID INT,
    IstrazivacID INT,
    TeloID INT,
    Podatak TEXT,
    FOREIGN KEY (MisijaID) REFERENCES Misija(MisijaID),
    FOREIGN KEY (IstrazivacID) REFERENCES Istrazivac(IstrazivacID),
    FOREIGN KEY (TeloID) REFERENCES NebeskoTelo(TeloID)
);

CREATE TABLE Uloga (
    UlogaID SERIAL PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL UNIQUE,
    KategorijaID INT NOT NULL,
    FOREIGN KEY (KategorijaID) REFERENCES KategorijaIstrazivaca(KategorijaID)
);


---INSERT_INTO

-- Tabela za galaksije
INSERT INTO Galaksija (GalaksijaID, Ime, Masa, ProsecniPrecnik, UgaoniMomenat) VALUES
(1, 'Mlecni put', 1.15e12, 87400, 9.0),
(2, 'Andromeda', 1.23e12, 220000, 10.0),
(3, 'Sombrero', 0.8e12, 49000, 6.0),
(4, 'Whirlpool', 1.05e12, 76000, 8.0),
(5, 'Triangulum', 0.6e12, 60000, 4.0),
(6, 'Sculptor', 0.7e12, 50000, 5.0),
(7, 'Large Magellanic Cloud', 0.2e12, 14000, 1.0),
(8, 'Small Magellanic Cloud', 0.1e12, 7000, 0.9),
(9, 'Centaurus A', 1.5e12, 120000, 10.0),
(10, 'Cartwheel', 0.9e12, 52000, 7.0);

-- Tabela za morfoloske tipove galaksija
INSERT INTO MorfoloskiTipovi (MorfoloskiTipID, TipIme) VALUES
(1, 'Sb'),
(2, 'Sbc'),
(3, 'SB(rs)bc'),
(4, 'E0'),
(5, 'Irr'),
(6, 'Sc'),
(7, 'S0'),
(8, 'SBa'),
(9, 'Sd'),
(10, 'Sa');

-- Veza izmedju galaksija i morfoloskih tipova
INSERT INTO GalaksijaMorfoloskiTip (GalaksijaID, MorfoloskiTipID) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 1),
(5, 6),
(6, 7),
(7, 5),
(8, 5),
(9, 4),
(10, 2),  
(9, 1);  

-- Tabela za nebeska tela
INSERT INTO NebeskoTelo (TeloID, Ime, Tip, ProsecniRadijus, EkvatorijalniRadijus, PolarniRadijus, Zapremina, Masa, MinTemp, MaxTemp, RotacijaTrajanje, RevolucijaTrajanje, TeloOkretanjaID, GalaksijaID) VALUES
(1, 'Sunce', 'Zvezda', 695700, 696342, 695000, 1.41e6, 1.989e12, 5500, 5800, 25.38, NULL, NULL, 1),
(2, 'Zemlja', 'Planeta', 6371, 6378.1, 6356.8, 1.08321e5, 5.972e11, -89.2, 56.7, 0.997, 365.256, 1, 1),
(3, 'Mesec', 'Satelit', 1737.4, 1738.1, 1736.0, 2.1968e4, 7.342e10, -233, 123, 27.32, 27.32, 2, 1),
(4, 'Mars', 'Planeta', 3389.5, 3396.2, 3376.2, 1.6318e5, 6.417e11, -143, 35, 1.026, 687, 1, 1),
(5, 'Jupiter', 'Planeta', 69911, 71492, 66854, 1.43128e6, 1.898e12, -145, -108, 0.413, 4331, 1, 1),
(6, 'Venus', 'Planeta', 6051.8, 6051.8, 6051.8, 9.2843e4, 4.867e11, 462, 465, -243.025, 224.701, 1, 1),
(7, 'Pluton', 'Planeta', 1188.3, 1188.3, 1188.3, 7.057e3, 1.309e10, -229, -223, -6.39, 90560, 1, 1),
(8, 'Titan', 'Satelit', 2574.7, 2576.2, 2574.2, 7.161e4, 1.345e11, -179, -95, 15.945, 15.945, 5, 1),
(9, 'Europa', 'Satelit', 1560.8, 1562.6, 1560.3, 1.593e3, 4.8e10, -160, -220, 3.551, 3.551, 5, 1),
(10, 'Io', 'Satelit', 1821.6, 1829.4, 1821.5, 2.529e3, 8.93e10, -143, -83, 1.769, 1.769, 5, 1);

-- Tabela za institucije
INSERT INTO Institucija (InstitucijaID, Ime, Grad, Drzava) VALUES
(1, 'Univerzitet u Beogradu', 'Beograd', 'Srbija'),
(2, 'Univerzitet u Novom Sadu', 'Novi Sad', 'Srbija'),
(3, 'Univerzitet u Nisu', 'Nis', 'Srbija'),
(4, 'Univerzitet u Kragujevcu', 'Kragujevac', 'Srbija'),
(5, 'Univerzitet u Subotici', 'Subotica', 'Srbija'),
(6, 'Univerzitet u Zagrebu', 'Zagreb', 'Hrvatska'),
(7, 'Univerzitet u Ljubljani', 'Ljubljana', 'Slovenija'),
(8, 'Univerzitet u Sarajevu', 'Sarajevo', 'Bosna i Hercegovina'),
(9, 'Univerzitet u Skoplju', 'Skoplje', 'Severna Makedonija'),
(10, 'Univerzitet u Podgorici', 'Podgorica', 'Crna Gora');

--Tabela za istrazivace
INSERT INTO Istrazivac (IstrazivacID, Ime, Prezime, GradRodjenja, DrzavaRodjenja, NivoObrazovanja, InstitucijaID) VALUES
(1, 'Ana', 'Nikolić', 'Beograd', 'Srbija', 'Master', 1),
(2, 'Mihajlo', 'Petrović', 'Novi Sad', 'Srbija', 'Doktorat', 2),
(3, 'Jelena', 'Vuković', 'Niš', 'Srbija', 'Osnovne', 3),
(4, 'Marko', 'Stojanović', 'Kragujevac', 'Srbija', 'Master', 1),
(5, 'Milica', 'Kostić', 'Subotica', 'Srbija', 'Srednja škola', 4),
(6, 'Stefan', 'Lukić', 'Leskovac', 'Srbija', 'Specijalističke', 5),
(7, 'Ivana', 'Mladenović', 'Ljubljana', 'Slovenija', 'Master', 7),
(8, 'Petar', 'Jovanović', 'Sarajevo', 'Bosna i Hercegovina', 'Doktorat', 8),
(9, 'Sanja', 'Bogdanović', 'Skoplje', 'Severna Makedonija', 'Osnovne', 9),
(10, 'Nikola', 'Ilić', 'Podgorica', 'Crna Gora', 'Master', 10);

-- Tabela za misije
INSERT INTO Misija (MisijaID, MisijaIme, Cilj, Ishod, Status) VALUES
(1, 'Galactic Survey', 'Istrazivanje Mlecnog puta', 'Uspesna misija', 'uspesna'),
(2, 'Andromeda Exploration', 'Analiza Andromeda galaksije', 'Misija u planu', 'u planu'),
(3, 'Star Search', 'Pronaci nove zvezde', 'Uspesna misija', 'uspesna'),
(4, 'Planet Hunt', 'Identifikacija planeta', 'Misija zapoceta', 'zapoceta'),
(5, 'Moon Mapping', 'Kartografija Meseca', 'Uspesna misija', 'uspesna'),
(6, 'Asteroid Analysis', 'Ispitivanje asteroida', 'Misija neuspesna', 'neuspesna'),
(7, 'Jupiter Mission', 'Orbitalna analiza Jupitera', 'Uspesna misija', 'uspesna'),
(8, 'Exoplanet Study', 'Trazenje egzoplaneta', 'Misija u planu', 'u planu'),
(9, 'Black Hole Research', 'Istrazivanje crnih rupa', 'Misija zapoceta', 'zapoceta'),
(10, 'Galaxy Mapping', 'Mapiranje galaksija', 'Uspesna misija', 'uspesna');

-- Tabela za sumove
INSERT INTO Sum (SumID, MisijaID, IstrazivacID, TeloID, Podatak) VALUES
(1, 1, 1, 2, 'Nije pronadjeno nista relevantno'),
(2, 2, 3, 4, 'Nejasne informacije o planetama'),
(3, 3, 5, 5, 'Greska u podacima sa Marsa'),
(4, 4, 7, 6, 'Nevazeci podaci sa Jupitera'),
(5, 5, 9, 3, 'Informacije o Mesecevim kraterima'),
(6, 6, 10, 8, 'Nedovoljno podataka za analizu'),
(7, 7, 4, 9, 'Zapazanja sa Io'),
(8, 8, 2, 7, 'Nepravilnosti u podacima'),
(9, 9, 8, 10, 'Crne rupe nisu detektovane'),
(10, 10, 6, 1, 'Greske u podacima sa Sunca'),
(11, 1, 1, 2, 'Sumnjivi tragovi'),
(12, 3, 5, 5, 'Jos jedan nevalidan signal'),
(13, 3, 5, 5, 'Zvucni sum'),
(14, 3, 5, 5, 'Bez podataka'),
(15, 3, 5, 5, 'Podaci necitljivi'),
(16, 3, 5, 5, 'Ponavljanje signala'); 

-- Tabela za kategorije istrazivaca
INSERT INTO KategorijaIstrazivaca (KategorijaID, KategorijaIme, IdeUSvemir) VALUES
(1, 'Astronaut', TRUE),
(2, 'Naucnik', FALSE),
(3, 'Inzenjer', TRUE),
(4, 'Doktor', FALSE),
(5, 'Pilot', TRUE),
(6, 'Tehnicar', TRUE),
(7, 'Biolog', FALSE),
(8, 'Geolog', TRUE),
(9, 'Fizicar', FALSE),
(10, 'Hemijski inzenjer', TRUE);

-- Veza izmedju misija i nebeskih tela
INSERT INTO MisijaCilj (MisijaID, TeloID) VALUES
(1, 2),
(2, 4),
(3, 5),
(4, 6),
(5, 3),
(6, 8),
(7, 9),
(8, 7),
(9, 10),
(10, 1),
(2, 3),  
(6, 10), 
(4, 9); 

INSERT INTO Uloga (Naziv, KategorijaID) VALUES
('Komandant', 1),         
('Pilot', 5),            
('Inzenjering', 3),       
('Navigacija', 1),
('Lekar', 4),            
('Bioloska analiza', 7),  
('Geoloski nadzor', 8),  
('Fizicka analiza', 9),  
('Hemijska analiza', 10),
('Tehnicka podrska', 6);  

INSERT INTO MisijaIstrazivac (MisijaID, IstrazivacID, UlogaID) VALUES
(1, 1, 1),  
(2, 3, 3),  
(3, 5, 2),   
(4, 7, 6),  
(5, 9, 8),  
(6, 10, 9), 
(7, 4, 5),  
(8, 2, 4),  
(9, 6, 10),  
(10, 8, 7),  
(1, 2, 5),   
(2, 10, 9),  
(3, 1, 1),   
(4, 1, 3),  
(5, 5, 10); 

--Jedinstveni kljucevi
ALTER TABLE Galaksija ADD CONSTRAINT unique_galaksija_id UNIQUE (GalaksijaID);
ALTER TABLE Institucija ADD CONSTRAINT unique_institucija_id UNIQUE (InstitucijaID);
ALTER TABLE Galaksija ADD CONSTRAINT unique_galaksija_ime UNIQUE (Ime);
ALTER TABLE MorfoloskiTipovi ADD CONSTRAINT unique_morfoloski_tip_ime UNIQUE (TipIme);
ALTER TABLE NebeskoTelo ADD CONSTRAINT unique_nebesko_telo_ime UNIQUE (Ime);
ALTER TABLE Istrazivac ADD CONSTRAINT unique_istrazivac_ime_prezime UNIQUE (Ime, Prezime);
ALTER TABLE Institucija ADD CONSTRAINT unique_institucija_ime UNIQUE (Ime);
ALTER TABLE KategorijaIstrazivaca ADD CONSTRAINT unique_kategorija_ime UNIQUE (KategorijaIme);
ALTER TABLE Misija ADD CONSTRAINT unique_misija_ime UNIQUE (MisijaIme);
ALTER TABLE MisijaCilj ADD CONSTRAINT uq_MisijaCilj UNIQUE (MisijaID, TeloID);
ALTER TABLE MisijaIstrazivac ADD CONSTRAINT uq_misija_istrazivac_uloga UNIQUE (MisijaID, IstrazivacID, UlogaID);
ALTER TABLE Sum ADD CONSTRAINT uq_Sum UNIQUE (SumID);
ALTER TABLE GalaksijaMorfoloskiTip ADD CONSTRAINT uq_GalaksijaMorfoloskiTip UNIQUE (GalaksijaID, MorfoloskiTipID);
ALTER TABLE Uloga ADD CONSTRAINT uq_uloga_naziv UNIQUE (Naziv);

ALTER TABLE Istrazivac
ADD COLUMN KategorijaID INT,
ADD FOREIGN KEY (KategorijaID) REFERENCES KategorijaIstrazivaca(KategorijaID);


--Indeksi
CREATE INDEX idx_galaksija_id ON Galaksija (GalaksijaID);
CREATE INDEX idx_galaksija_ime ON Galaksija (Ime);
CREATE INDEX idx_institucija_id ON Institucija (InstitucijaID);
CREATE INDEX idx_istrazivac_id ON Istrazivac (IstrazivacID);
CREATE INDEX idx_morfoloski_tip_ime ON MorfoloskiTipovi (TipIme);
CREATE INDEX idx_galaksija_morfoloski ON GalaksijaMorfoloskiTip (GalaksijaID, MorfoloskiTipID);
CREATE INDEX idx_nebesko_telo_ime ON NebeskoTelo (Ime);
CREATE INDEX idx_istrazivac_ime ON Istrazivac (Ime);
CREATE INDEX idx_istrazivac_prezime ON Istrazivac (Prezime);
CREATE INDEX idx_institucija_ime ON Institucija (Ime);
CREATE INDEX idx_kategorija_ime ON KategorijaIstrazivaca (KategorijaIme);
CREATE INDEX idx_misija_ime ON Misija (MisijaIme);
CREATE INDEX idx_misija_cilj ON MisijaCilj (MisijaID, TeloID);
CREATE INDEX idx_sum ON Sum (MisijaID, IstrazivacID, TeloID);
CREATE INDEX idx_nebesko_telo_galaksija ON NebeskoTelo(GalaksijaID);
CREATE INDEX idx_uloga_kategorija ON Uloga(KategorijaID);

