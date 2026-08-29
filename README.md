# Woordfrequentie API

Een REST-API voor het analyseren van woordfrequentie in tekst. Gebouwd als programmeer-assessment voor Sopra Steria.

## Technologiën

- **Java 21** — runtime en taalversie
- **Spring Boot 4.1.1** — framework voor de REST-API en dependency injection
- **Lombok** — minder boilerplate code (getters, constructor-injectie)
- **Maven** — build- en dependency management

## Endpoints

Alle endpoints zijn GET-verzoeken onder de basis-pads: `/api/v1/words/frequency`

### 1. Hoogste woordfrequentie

Geeft het hoogste aantal voorkomens van een enkel woord in de tekst terug.

```
GET /api/v1/words/frequency/highest?text=The+sun+shines+over+the+lake
```

Response (200 OK):
```json
1
```

### 2. Frequentie van een specifiek woord

Geeft het aantal keer terug dat een specifiek woord voorkomt in de tekst (hoofdletterongevoelig).

```
GET /api/v1/words/frequency?text=The+sun+shines+over+the+lake&word=sun
```

Response (200 OK):
```json
1
```

### 3. Top N meest frequente woorden

Geeft de N meest voorkomende woorden terug, gesorteerd op frequentie (aflopend), en bij gelijk aantal alfabetisch.

```
GET /api/v1/words/frequency/most?text=The+sun+shines+over+the+lake&n=3
```

Response (200 OK):
```json
[
  {"word": "lake", "frequency": 1},
  {"word": "over", "frequency": 1},
  {"word": "shines", "frequency": 1}
]
```

### Validatiefouten (400 Bad Request)

Wanneer een verplichte parameter ontbreekt of leeg is:

```
GET /api/v1/words/frequency/highest?text=
```

Response (400 Bad Request):
```
```


## Opstarten lokaal

```bash
# Project bouwen
mvn clean package

# Applicatie starten
mvn spring-boot:run
```

De API is daarna bereikbaar op `http://localhost:8080`.

