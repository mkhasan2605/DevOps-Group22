# SET09803 DevOps Group 22 – Population Information System
[![Build – master](https://img.shields.io/github/actions/workflow/status/mkhasan2605/DevOps-Group22/main.yml?branch=master&label=build%20%28master%29)](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml?query=branch%3Amaster)
[![Build – develop](https://img.shields.io/github/actions/workflow/status/mkhasan2605/DevOps-Group22/main.yml?branch=develop&label=build%20%28develop%29)](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml?query=branch%3Adevelop)
[![Release](https://img.shields.io/github/v/release/mkhasan2605/DevOps-Group22)](https://github.com/mkhasan2605/DevOps-Group22/releases)
[![License](https://img.shields.io/github/license/mkhasan2605/DevOps-Group22)](LICENSE)
<!-- Code coverage badge (master): added for Code Review 2 (JaCoCo + Codecov) -->

## Project Overview

This project is a population information reporting system developed
as part of the SET09803 DevOps module at Edinburgh Napier University.

The system uses the provided MySQL population database to generate
reports relating to countries, cities, capital cities, population
information and languages.

The project is developed by Group 22 using Scrum and DevOps practices.

## Progress

![Requirements met](https://img.shields.io/badge/requirements%20met-28%2F32%20%2887.5%25%29-brightgreen)

See [Functional Requirements and Evidence](#functional-requirements-and-evidence) for the full list.

## Team Members

| Team Member    | Matriculation Number | GitHub Username      |
|----------------|----------------------|----------------------|
| Mohammad Hasan | 40805651             | mkhasan2605          |
| Moe Swam Pyae  | 40858561             | 40858561-MoeSwamPyae |
| Sandi Tun      | 40841071             | Sandiisme            |
| Shan Htet Aung | 40841087             | yashiroexe123456-cmd |
| Kyaw Zin Hein  | 40840483             | raysharris822-sketch |
| Ye Yint Aung   | 40840498             | YeYintAung           |

## Technologies

- Java 17
- Maven
- MySQL
- Git
- GitHub
- GitHub Actions
- Docker
- JUnit

## Project Structure

```text
DevOps-Group22/
├── .github/
│   ├── CODEOWNERS
│   ├── pull_request_template.md    # Checklist added to every new PR
│   └── workflows/main.yml          # CI: Maven build, then run the app against MySQL in Docker
├── db/
│   ├── Dockerfile                  # MySQL 8.4 image that loads world.sql on first start
│   └── world.sql
├── docs/
│   ├── screenshots/                # Report output used as requirement evidence
│   └── use-cases/                  # Use case descriptions and diagram
├── src/
│   ├── main/java/com/napier/devops/
│   │   ├── App.java                # Entry point: connects and runs every report
│   │   ├── QueryRunner.java        # Shared JDBC query helper
│   │   ├── ReportPrinter.java      # Console table output for all reports
│   │   ├── CountryReports.java     # UC01  R01-R06  (Country)
│   │   ├── CityReports.java        # UC02  R07-R16  (City)
│   │   ├── CapitalReports.java     # UC03  R17-R22  (CapitalCity)
│   │   ├── PopulationReports.java  # UC04  R23-R25  (PopulationReport)
│   │   └── PlacePopulationReports.java  # UC05  R27-R29  (PlacePopulation)
│   └── test/java/com/napier/devops/
├── docker-compose.yml
├── Dockerfile                      # Runs target/app.jar against the db service
├── pom.xml
├── CODE_OF_CONDUCT.md
├── LICENSE
├── README.md
└── SETUP.md
```

## Quick Start

See [SETUP.md](SETUP.md) for the full installation guide.

```bash
mvn clean package                      # builds target/app.jar
docker compose up --build              # starts MySQL and runs the app against it
```

To run from IntelliJ instead, start only the database (`docker compose up db`) and run `App`
with no arguments; it connects to `localhost:33060`. The R07-R11 city reports are written as
text files to the `reports/` directory.

## Development Workflow

1. Pick or create a GitHub Issue for the task.
2. Update your local `develop` branch.
3. Create a feature branch for the issue.
4. Implement the change and add or update tests.
5. Commit and push the feature branch.
6. Open a pull request targeting `develop`; include `Closes #<issue-number>` in the PR description when the PR completes that issue.
7. Request review and address comments. Merge only when the required approvals and checks pass.
8. Follow the team's agreed release process for `release` and `master`. Do not commit directly to `master`.

See [SETUP.md](SETUP.md) for commands and further detail.

## Use Cases

The use case descriptions are in [`docs/use-cases`](docs/use-cases), with an overview in the
[use case diagram](docs/use-cases/Usecase_diagram.svg).

| Use Case                                                                      | Requirements |
|-------------------------------------------------------------------------------|--------------|
| [UC01: Country Reports](docs/use-cases/UC01-country-reports.md)               | R01–R06      |
| [UC02: City Reports](docs/use-cases/UC02-city-reports.md)                     | R12–R16      |
| [UC03: Capital City Reports](docs/use-cases/UC03-capital-city-reports.md)     | R17–R22      |
| [UC04: Population Breakdowns](docs/use-cases/UC04-population-breakdowns.md)   | R23–R25      |
| [UC05: Population Information](docs/use-cases/UC05-population-information.md) | R27–R31      |

## Functional Requirements and Evidence

**28 of 32 requirements met (87.50%).**

| ID  | Requirement                                                                                                                   | Met | Screenshot                        |
|-----|-------------------------------------------------------------------------------------------------------------------------------|-----|-----------------------------------|
| R01 | All countries worldwide, ordered by population descending                                                                     | Yes | [View](docs/screenshots/R-01.jpg) |
| R02 | All countries in a continent, ordered by population descending                                                                | Yes | [View](docs/screenshots/R-02.jpg) |
| R03 | All countries in a region, ordered by population descending                                                                   | Yes | [View](docs/screenshots/R-03.jpg) |
| R04 | Top N populated countries worldwide                                                                                           | Yes | —                                 |
| R05 | Top N populated countries in a continent                                                                                      | Yes | —                                 |
| R06 | Top N populated countries in a region                                                                                         | Yes | —                                 |
| R07 | All cities worldwide, ordered by population descending                                                                        | Yes | —                                 |
| R08 | All cities in a continent, ordered by population descending                                                                   | Yes | —                                 |
| R09 | All cities in a region, ordered by population descending                                                                      | Yes | —                                 |
| R10 | All cities in a country, ordered by population descending                                                                     | Yes | —                                 |
| R11 | All cities in a district, ordered by population descending                                                                    | Yes | —                                 |
| R12 | Top N populated cities worldwide                                                                                              | Yes | —                                 |
| R13 | Top N populated cities in a continent                                                                                         | Yes | —                                 |
| R14 | Top N populated cities in a region                                                                                            | Yes | —                                 |
| R15 | Top N populated cities in a country                                                                                           | Yes | —                                 |
| R16 | Top N populated cities in a district                                                                                          | Yes | —                                 |
| R17 | All capital cities worldwide, ordered by population descending                                                                | Yes | —                                 |
| R18 | All capital cities in a continent, ordered by population descending                                                           | Yes | —                                 |
| R19 | All capital cities in a region, ordered by population descending                                                              | Yes | —                                 |
| R20 | Top N populated capital cities worldwide                                                                                      | Yes | —                                 |
| R21 | Top N populated capital cities in a continent                                                                                 | Yes | —                                 |
| R22 | Top N populated capital cities in a region                                                                                    | Yes | —                                 |
| R23 | Population, city population and non-city population by continent                                                              | Yes | [View](docs/screenshots/R-23.jpg) |
| R24 | Population, city population and non-city population by region                                                                 | Yes | [View](docs/screenshots/R-24.jpg) |
| R25 | Population, city population and non-city population by country                                                                | Yes | [View](docs/screenshots/R-25.jpg) |
| R26 | Population of the world                                                                                                       | No  | —                                 |
| R27 | Population of a continent                                                                                                     | Yes | —                                 |
| R28 | Population of a region                                                                                                        | Yes | —                                 |
| R29 | Population of a country                                                                                                       | Yes | —                                 |
| R30 | Population of a district                                                                                                      | No  | —                                 |
| R31 | Population of a city                                                                                                          | No  | —                                 |
| R32 | Number and percentage of world population speaking Chinese, English, Hindi, Spanish and Arabic, ordered by number of speakers | No  | —                                 |

Screenshots for the remaining requirements are still to be added.

### UC01 Country Reports - Screenshot Evidence (R01-R06)

- [R01 - All countries in the world](docs/screenshots/R-01.jpg)
- [R02 - All countries in a continent](docs/screenshots/R-02.jpg)
- [R03 - All countries in a region](docs/screenshots/R-03.jpg)
- [R04 - Top N countries in the world](docs/screenshots/R-04.jpg)
- [R05 - Top N countries in a continent](docs/screenshots/R-05.jpg)
- [R06 - Top N countries in a region](docs/screenshots/R-06.jpg)

### UC02 City Reports - Screenshot Evidence (R07-R11)
- [R07 - All Cities Worldwide](docs/screenshots/R-07.png)
- [R08 - Cities by Continent](docs/screenshots/R-08.png)
- [R09 - Cities by Region](docs/screenshots/R-09.png)
- [R10 - Cities by Country](docs/screenshots/R-10.png)
- [R11 - Cities by District](docs/screenshots/R-11.png)

### UC03 Capital City Reports - Screenshot Evidence (R17-R22)

- [R17 - Capital cities worldwide](docs/screenshots/R-17.jpg)
- [R18 - Capital cities by continent](docs/screenshots/R-18.jpg)
- [R19 - Capital cities by region](docs/screenshots/R-19.jpg)
- [R20 - Top N capital cities worldwide](docs/screenshots/R-20.jpg)
- [R21 - Top N capital cities by continent](docs/screenshots/R-21.jpg)
- [R22 - Top N capital cities by region](docs/screenshots/R-22.jpg)

## Licence

The group proposes the [MIT License](LICENSE), subject to review and agreement by all team members.