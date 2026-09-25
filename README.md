# Booking – black-box test case design

## User story

Som kunde vil jeg kunne foretage en booking, så jeg kan reservere et bord på restauranten.

## Acceptance criteria

- En booking skal være for 1–12 gæster.
- Kunden skal være mindst 18 år.
- Bookinger for mere end 8 personer kræver et depositum.
- En blokeret kunde kan ikke foretage bookinger.

Projektet viser tre black-box testdesignteknikker og omsætter test cases
til data-driven JUnit 5 tests med `@ParameterizedTest` og `@CsvFileSource`.

---

## 1. Ækvivalensanalyse

### Antal gæster

| Klasse | Værdier | Gyldig? | Repræsentant |
|---|---:|---|---:|
| EC1 | < 1 | Nej | 0 |
| EC2 | 1–12 | Ja | 6 |
| EC3 | > 12 | Nej | 13 |

### Alder

| Klasse | Værdier | Gyldig? | Repræsentant |
|---|---:|---|---:|
| EC4 | < 18 | Nej | 17 |
| EC5 | >= 18 | Ja | 30 |

Blocked er en binær tilstand og testes både som `true` og `false`.

Testdata findes i `src/test/resources/equivalence-partitioning.csv`.

---

## 2. Boundary Value Analysis

Vigtige grænser:

- Antal gæster: 1 og 12.
- Alder: 18.
- Depositumreglen skifter mellem 8 og 9 gæster.

Derfor er bl.a. disse værdier interessante:

- Gæster: `0, 1, 2`, `7, 8, 9`, `11, 12, 13`
- Alder: `17, 18, 19`

Ved 9 gæster testes både uden og med depositum.

Testdata findes i `src/test/resources/boundary-values.csv`.

---

## 3. Decision Table

Her fokuserer vi på samspillet mellem gruppestørrelse, depositum og
om kunden er blokeret. Alder holdes gyldig (`30`) i disse tests.

| Regel | Blokeret? | Mere end 8 gæster? | Depositum? | Resultat |
|---|---|---|---|---|
| R1 | Ja | - | - | Afvis |
| R2 | Nej | Nej | - | Accepter |
| R3 | Nej | Ja | Nej | Afvis |
| R4 | Nej | Ja | Ja | Accepter |

`-` betyder *don't care*: værdien påvirker ikke resultatet i den regel.

Testdata findes i `src/test/resources/decision-table.csv`.

---

## Hvorfor tre CSV-filer?

Filerne er bevidst opdelt efter testdesignteknik. Det gør det synligt,
**hvorfor** en testværdi er valgt. I et almindeligt produktionsprojekt
kunne man vælge en anden organisering.

Sammenhængen er:

`Acceptance criteria -> testdesign -> test cases -> CSV-testdata -> automatiseret test`

JUnit er altså værktøjet, der udfører testene. Equivalence Partitioning,
Boundary Value Analysis og Decision Tables er teknikkerne, der hjælper os
med at beslutte, hvilke test cases der er relevante.

## Kør projektet i IntelliJ IDEA

1. Åbn projektmappen som Maven-projekt.
2. Kontrollér at Project SDK er Java 21.
3. Vent på at Maven har hentet dependencies.
4. Åbn `BookingValidatorTest`.
5. Klik på den grønne Run-pil ved klassen for at køre alle tests.

Testene kan også køres fra terminalen med:

```bash
mvn test
```


---

# Fra råt testdesign til konsolideret testsuite

De tre teknikker kan finde den samme test case. Det er ikke en fejl:
teknikkerne bruges til at **finde relevante test cases**, ikke til at skabe tre
helt adskilte testsuiter.

Eksempler på overlap:

| Testidé | EP | BVA | Decision table |
|---|:---:|:---:|:---:|
| 0 gæster | X | X | |
| 13 gæster | X | X | |
| Alder 17 | X | X | |
| 9 gæster uden depositum | | X | X |
| Blokeret kunde | X | | X |

## Arbejdsgang

```text
Acceptance criteria
        |
        v
+---------------+   +---------------+   +----------------+
| Equivalence   |   | Boundary      |   | Decision       |
| Partitioning  |   | Value Analysis|   | Table          |
+-------+-------+   +-------+-------+   +--------+-------+
        \                 |                    /
         \                |                   /
          +--------------- v ----------------+
                  rå test cases
                       |
                       v
              find overlap/redundans
                       |
                       v
             konsolideret testsuite
```

## To niveauer i projektet

### 1. Råt testdesign

`BookingValidatorTest.java` bruger tre CSV-filer:

- `equivalence-partitioning.csv`
- `boundary-values.csv`
- `decision-table.csv`

De er bevidst opdelt efter teknik. Her kan man se **hvorfor** en test case
blev valgt.

### 2. Konsolideret testsuite

`BookingValidatorConsolidatedTest.java` bruger:

`consolidated-test-cases.csv`

Her er overlap fjernet, og en ekstra kolonne `reason` dokumenterer,
hvilke teknikker og forretningsregler den enkelte test case repræsenterer.

Eksempel:

```csv
guests,age,depositPaid,blocked,expected,reason
13,30,true,false,false,"EP + BVA: lige over maksimum for antal gæster"
9,30,false,false,false,"BVA + decision table: 9 gæster uden depositum afvises"
```

Vigtig pointe:

> Testdesignteknikkerne hjælper os med at finde test cases. Men de behøver ikke
> ende som separate automatiserede testsuiter.

