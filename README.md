
# SET09803 DevOps Group 22 – Population Information System
[![Build – master](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml/badge.svg?branch=master)](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml)
[![Build – develop](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml/badge.svg?branch=develop)](https://github.com/mkhasan2605/DevOps-Group22/actions/workflows/main.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> **Project status:** The build and coverage badges must be checked against the actual GitHub Actions workflow. A coverage badge and release badge will be added when the corresponding reporting and release setup is available.

## Project Overview

This project is a population information reporting system developed
as part of the SET09803 DevOps module at Edinburgh Napier University.

The system uses the provided MySQL population database to generate
reports relating to countries, cities, capital cities, population
information and languages.

The project is developed by Group 22 using Scrum and DevOps practices.

## Team Members

| Team Member | Matriculation Number | GitHub Username |
|---|--|---|
| Mohammad Hasan | 40805651 | mkhasan2605 |
| Moe Swam Pyae | 40858561 | 40858561-MoeSwamPyae |
| Sandiisme | 40841071 | Sandiisme |
| Yashiro | TBC | yashiroexe123456-cmd |
| Raysharris | TBC | raysharris822-sketch |
| Ye Yint Aung | 40840498 | YeYintAung |

## Technologies

- Java 17
- Maven
- MySQL
- Git
- GitHub
- GitHub Actions
- Docker
- JUnit

## Setup

See the [Setup Guide](SETUP.md) for prerequisites and the current local setup steps.

## Build and Test

The project uses Maven. From the repository root, run these commands once the Maven project is available:

```bash
mvn clean test
mvn package
```

The generated artefacts are placed in the `target/` directory. The exact run command will be documented after the application packaging and database configuration have been verified.

## Development Workflow

Work on a feature branch and open a pull request to `develop`. Pull requests should be reviewed and pass the repository's required checks before merging. Do not commit directly to `master`.

## Licence

The group proposes the [MIT License](LICENSE), subject to agreement by all team members. Please confirm the choice before treating it as the team's final licensing decision.

## Functional Requirements and Evidence

The coursework specification states that there are **32 requirements**. The final README must state how many are met and the percentage, with evidence for each requirement. Only mark a requirement as met when the implementation has been checked and a screenshot of its output is available.

**Current verified completion count: not yet assessed.** This is not a claim that zero requirements are implemented; the count and percentage should be updated after the team reviews the implementation against the specification.

| ID | Requirement | Met (Yes/No) | Screenshot |
|---|---|---|---|
| R01 | All countries worldwide, ordered by population descending | No | — |
| R02 | All countries in a continent, ordered by population descending | No | — |
| R03 | All countries in a region, ordered by population descending | No | — |
| R04 | Top N populated countries worldwide | No | — |
| R05 | Top N populated countries in a continent | No | — |
| R06 | Top N populated countries in a region | No | — |
| R07 | All cities worldwide, ordered by population descending | No | — |
| R08 | All cities in a continent, ordered by population descending | No | — |
| R09 | All cities in a region, ordered by population descending | No | — |
| R10 | All cities in a country, ordered by population descending | No | — |
| R11 | All cities in a district, ordered by population descending | No | — |
| R12 | Top N populated cities worldwide | No | — |
| R13 | Top N populated cities in a continent | No | — |
| R14 | Top N populated cities in a region | No | — |
| R15 | Top N populated cities in a country | No | — |
| R16 | Top N populated cities in a district | No | — |
| R17 | All capital cities worldwide, ordered by population descending | No | — |
| R18 | All capital cities in a continent, ordered by population descending | No | — |
| R19 | All capital cities in a region, ordered by population descending | No | — |
| R20 | Top N populated capital cities worldwide | No | — |
| R21 | Top N populated capital cities in a continent | No | — |
| R22 | Top N populated capital cities in a region | No | — |
| R23 | Population, city population and non-city population by continent | No | — |
| R24 | Population, city population and non-city population by region | No | — |
| R25 | Population, city population and non-city population by country | No | — |
| R26 | Population of a city | No | — |
| R27 | Population of the world | No | — |
| R28 | Population of a continent | No | — |
| R29 | Population of a region | No | — |
| R30 | Population of a country | No | — |
| R31 | Population of a district | No | — |
| R32 | Number and percentage of world population speaking Chinese, English, Hindi, Spanish and Arabic, ordered by number of speakers | No | — |

> **Requirement IDs:** R01–R32 are Group 22's tracking IDs. Keep them consistent with the team's GitHub Issues and use-case documentation. Confirm the grouping with the team against the coursework's stated total of 32 requirements before final submission.

## Release and Coverage

The release-name and master-coverage badges will be added once GitHub Releases and test coverage reporting are configured. Badge URLs should reflect the actual workflow and coverage service; do not add badges that report an unverified status.