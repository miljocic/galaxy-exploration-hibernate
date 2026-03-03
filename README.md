# Galaxy Exploration Database


A Java application for querying a galaxy exploration database using Hibernate ORM, demonstrating three query approaches: HQL, native SQL, and Java Criteria API.

---

## Tech Stack

- **Java 11+** — core language
- **Hibernate ORM 6.2** — object-relational mapping
- **Jakarta Persistence (JPA 3.1)** — entity annotations
- **PostgreSQL** — database
- **Maven** — build system
- **Lombok** — boilerplate reduction

---

## Domain Model

| Entity | Description |
|--------|-------------|
| `Galaksija` | Galaxy — name, mass, radius, angular momentum, morphological types |
| `NebeskoTelo` | Celestial body — type, mass, volume, orbital relationships |
| `MorfoloskiTip` | Morphological type of a galaxy |
| `Misija` | Mission — goal, outcome, status (U_PLANU / ZAPOCETA / USPESNA / NEUSPESNA) |
| `MisijaIstrazivac` | Researcher participation in a mission (with role) |
| `Istrazivac` | Researcher — education level, city, institution |
| `Institucija` | Research institution |
| `KategorijaIstrazivaca` | Researcher category |
| `Uloga` | Role within a mission team |
| `Sum` | Research signal/log submitted by a researcher during a mission |

---

## Query Types

**HQL (Hibernate Query Language)**
- Celestial bodies in the largest galaxy below a given radius
- Check if two celestial bodies orbit each other
- Highest education level per institution

**Native SQL**
- Parse structured text input about a celestial body (regex validation)
- Log malformed entries as `Sum` records

**Java / Criteria API**
- Average mass of celestial bodies per galaxy
- Celestial body hierarchy traversal
- Researchers filtered by city
- Researchers by mission participation count range

---

## Setup

1. Create a PostgreSQL database and update credentials in `src/main/resources/hibernate.cfg.xml`
2. Hibernate auto-creates the schema on first run (`hbm2ddl.auto=update`)

```bash
mvn compile exec:java
```

---

## Authors
Milica Jocic 
