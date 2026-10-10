
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

| Team Member    | Matriculation Number | GitHub Username      |
|----------------|----------------------|----------------------|
| Mohammad Hasan | 40805651             | mkhasan2605          |
| Moe Swam Pyae  | 40858561             | 40858561-MoeSwamPyae |
| Sandiisme      | 40841071             | Sandiisme            |
| Yashiro        | 40841087             | yashiroexe123456-cmd |
| Raysharris     | 40840483             | raysharris822-sketch |
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

The following is the **intended structure**. Update it to match the actual repository as files are added; do not leave planned files presented as if they already exist.

```text
DevOps-Group22/
├── .github/
├── db/
│   ├── Dockerfile
│   └── world.sql
├── src/
│   ├── main/
│   └── test/
├── use-cases/
│   └── UC02-city-reports.md
├── .gitignore
├── CODE_OF_CONDUCT.md
├── docker-compose.yml
├── Dockerfile
├── LICENSE
├── pom.xml
├── README.md
└── SETUP.md
```

## Quick Start

See [SETUP.md](SETUP.md) for the full installation guide and for database/Docker steps.

Once the Maven project and its configuration are present, the standard build and test commands are:

```bash
mvn clean test
mvn package
```

These commands must be run from the directory containing `pom.xml`. Check the current repository and confirm the commands pass before treating them as verified project instructions.

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

## Functional Requirements and Evidence

The coursework specification states that there are **32 requirements**. The final README must state how many are met and the percentage, with evidence for each requirement. Mark a requirement as met only after checking the implementation and capturing evidence of its output.

**Verified completion count: not yet assessed.** This does not mean that no requirements have been implemented; the team must assess each one against the coursework specification before reporting a count or percentage.

| ID  | Requirement                                                                                                                   | Met (Yes/No) | Screenshot |
|-----|-------------------------------------------------------------------------------------------------------------------------------|--------------|------------|
| R01 | All countries worldwide, ordered by population descending                                                                     | No           | —          |
| R02 | All countries in a continent, ordered by population descending                                                                | No           | —          |
| R03 | All countries in a region, ordered by population descending                                                                   | No           | —          |
| R04 | Top N populated countries worldwide                                                                                           | No           | —          |
| R05 | Top N populated countries in a continent                                                                                      | No           | —          |
| R06 | Top N populated countries in a region                                                                                         | No           | —          |
| R07 | All cities worldwide, ordered by population descending                                                                        | No           | —          |
| R08 | All cities in a continent, ordered by population descending                                                                   | No           | —          |
| R09 | All cities in a region, ordered by population descending                                                                      | No           | —          |
| R10 | All cities in a country, ordered by population descending                                                                     | No           | —          |
| R11 | All cities in a district, ordered by population descending                                                                    | No           | —          |
| R12 | Top N populated cities worldwide                                                                                              | No           | —          |
| R13 | Top N populated cities in a continent                                                                                         | No           | —          |
| R14 | Top N populated cities in a region                                                                                            | No           | —          |
| R15 | Top N populated cities in a country                                                                                           | No           | —          |
| R16 | Top N populated cities in a district                                                                                          | No           | —          |
| R17 | All capital cities worldwide, ordered by population descending                                                                | No           | —          |
| R18 | All capital cities in a continent, ordered by population descending                                                           | No           | —          |
| R19 | All capital cities in a region, ordered by population descending                                                              | No           | —          |
| R20 | Top N populated capital cities worldwide                                                                                      | No           | —          |
| R21 | Top N populated capital cities in a continent                                                                                 | No           | —          |
| R22 | Top N populated capital cities in a region                                                                                    | No           | —          |
| R23 | Population, city population and non-city population by continent                                                              | No           | —          |
| R24 | Population, city population and non-city population by region                                                                 | No           | —          |
| R25 | Population, city population and non-city population by country                                                                | No           | —          |
| R26 | Population of a city                                                                                                          | No           | —          |
| R27 | Population of the world                                                                                                       | No           | —          |
| R28 | Population of a continent                                                                                                     | No           | —          |
| R29 | Population of a region                                                                                                        | No           | —          |
| R30 | Population of a country                                                                                                       | No           | —          |
| R31 | Population of a district                                                                                                      | No           | —          |
| R32 | Number and percentage of world population speaking Chinese, English, Hindi, Spanish and Arabic, ordered by number of speakers | No           | —          |

**Important:** R01–R32 are the team's tracking IDs. Confirm that their descriptions and numbering match the group's Issues, use cases and coursework specification before final submission. Replace `No` and `—` only when implementation and evidence have been checked.

## Licence

The group proposes the [MIT License](LICENSE), subject to review and agreement by all team members.