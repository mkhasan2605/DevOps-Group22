# Group 22 – Detailed Setup Guide

This guide explains how to prepare a development environment for the SET09803 DevOps Group 22 Population Information System, then verify the project step by step.

## Contents

1. [What you need](#1-what-you-need)
2. [Install and verify Java 17](#2-install-and-verify-java-17)
3. [Install Git and configure your identity](#3-install-git-and-configure-your-identity)
4. [Install IntelliJ IDEA](#4-install-intellij-idea)
5. [Install Docker Desktop](#5-install-docker-desktop)
6. [Clone and open the repository](#6-clone-and-open-the-repository)
7. [Check the project structure](#7-check-the-project-structure)
8. [Configure Maven and build the application](#8-configure-maven-and-build-the-application)
9. [Run automated tests](#9-run-automated-tests)
10. [Configure and verify the MySQL database](#10-configure-and-verify-the-mysql-database)
11. [Run the application](#11-run-the-application)
12. [Check GitHub Actions](#12-check-github-actions)
13. [Use the team Git workflow](#13-use-the-team-git-workflow)
14. [Troubleshooting](#14-troubleshooting)
15. [Setup checklist](#15-setup-checklist)

---

## 1. What you need

Install or obtain access to:

- **Java JDK 17** — the team's agreed Java version for the coursework.
- **Git** — to clone the repository and work with branches.
- **IntelliJ IDEA** — to open and develop the Java application.
- **Docker Desktop** — runs the MySQL world database and the application containers.
- **GitHub access** — you must be a collaborator/member with permission to access the Group 22 repository.

Maven is normally configured in a Maven project through `pom.xml`; IntelliJ has Maven integration. You can use IntelliJ's bundled Maven support, or install Maven separately if you want to run `mvn` from PowerShell.

### Recommended installation order

1. Java JDK 17
2. Git
3. IntelliJ IDEA
4. Docker Desktop
5. Repository clone and project checks

Use the installation links and versions recommended by the current module materials. Do not install an old Docker Desktop release solely because it was mentioned in an older lecture without checking the current module guidance and compatibility.

## 2. Install and verify Java 17

### 2.1 Install the JDK

1. Install a Java **JDK 17** distribution supported by your operating system.
2. Complete the installer.
3. If the installer offers to configure `JAVA_HOME` or update `PATH`, enable that option if appropriate.
4. Open a **new** PowerShell window so it picks up the environment changes.

### 2.2 Verify Java

Run:

```powershell
java -version
javac -version
```

Both commands should report version **17**. If `java` reports a different version, check which executable Windows finds:

```powershell
where.exe java
$env:JAVA_HOME
```

If multiple JDKs are installed, set IntelliJ's project SDK to JDK 17 in Step 6. Do not uninstall another JDK if another project needs it.

**Success check:** `java -version` and `javac -version` both show Java 17, or IntelliJ is explicitly configured to use JDK 17 for this project.

## 3. Install Git and configure your identity

### 3.1 Verify Git

Install Git for Windows if it is not installed. Open a new PowerShell window and run:

```powershell
git --version
```

### 3.2 Check the configured identity

Run:

```powershell
git config --global user.name
git config --global user.email
```

If they are empty, configure your preferred Git author identity:

```powershell
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

Use an email address appropriate for your GitHub account. If you use different identities for university and professional repositories, configure the identity **inside this repository** instead of changing your global identity:

```powershell
git config user.name "Your Name"
git config user.email "your-university-email@example.com"
```

Run the repository-level commands only after you have cloned and entered the project folder.

**Success check:** `git config user.name` and `git config user.email` inside the repository show the identity you intend to use for coursework commits.

## 4. Install IntelliJ IDEA

1. Install IntelliJ IDEA using the current instructions recommended by the university or JetBrains.
2. Launch IntelliJ IDEA.
3. You will open the cloned repository in Step 6.
4. After opening it, select **File → Project Structure** (the exact menu label can vary by version).
5. Under **Project**, set **Project SDK** to JDK 17.
6. Set the project language level to the version supported by the coursework/project configuration. Do not set it higher than the build configuration supports.
7. If IntelliJ asks whether to trust the project, check that you cloned the expected Group 22 repository before trusting it.

**Success check:** IntelliJ shows JDK 17 as the project's SDK.

## 5. Install Docker Desktop

1. Install Docker Desktop using the current module guidance and official Docker installation instructions.
2. Follow the installer prompts and restart Windows if requested.
3. Start Docker Desktop and wait until it reports that the engine is running.
4. Open PowerShell and run:

```powershell
docker --version
docker compose version
docker info
```

`docker --version` and `docker compose version` should print version information. `docker info` should return server/engine details rather than an error that the daemon cannot be reached.

If Docker Desktop asks you to enable WSL 2 or another Windows feature, follow the official instructions for your Windows edition and restart when requested.

**Success check:** Docker Desktop is running and `docker info` completes successfully.

## 6. Clone and open the repository

### Option A — IntelliJ

1. Open IntelliJ IDEA.
2. Choose **Get from VCS**.
3. Enter the repository URL:

   `https://github.com/mkhasan2605/DevOps-Group22.git`

4. Choose a development folder, for example a folder under your university coursework directory. Avoid cloning inside `C:\Windows\System32`.
5. Select **Clone**.
6. When the project opens, allow IntelliJ to finish indexing.

### Option B — PowerShell

Move to the folder where you keep university projects, then clone:

```powershell
git clone https://github.com/mkhasan2605/DevOps-Group22.git
cd DevOps-Group22
```

If the repository is private, sign in to GitHub when prompted.

### Switch to `develop`

The team uses `develop` for normal integration work. Run:

```powershell
git branch --all
git switch develop
git pull origin develop
```

If `git switch develop` says the branch does not exist locally but `remotes/origin/develop` is listed, run:

```powershell
git switch --track origin/develop
```

If neither branch exists on the remote, stop and check the repository branches with the team rather than creating a replacement branch without agreement.

### Check your repository

Run:

```powershell
git status
git remote -v
```

The remote should point to the Group 22 repository. `git status` should show the current branch and whether you have uncommitted changes.

## 7. Check the project structure

In IntelliJ, check that the Project panel shows:

```text
DevOps-Group22/
├── .github/workflows/main.yml   # CI pipeline
├── db/
│   ├── Dockerfile               # MySQL 8.4 image that loads world.sql
│   └── world.sql                # The world database
├── src/main/java/com/napier/devops/   # Application source
├── src/test/java/com/napier/devops/   # Tests
├── use-cases/                   # Use case descriptions and diagram
├── docker-compose.yml           # Runs the db and app containers together
├── Dockerfile                   # Application image: runs target/app.jar
├── pom.xml                      # Maven build: Java 17, JUnit 5, MySQL driver
├── README.md
└── SETUP.md
```

See the README for what each Java class does.

## 8. Configure Maven and build the application

### 8.1 Open the Maven project in IntelliJ

1. Open the repository folder in IntelliJ.
2. If IntelliJ prompts to load/import the Maven project, accept.
3. Open the Maven tool window and check that the project is recognised.
4. Check that the Maven importer uses JDK 17 (**Settings → Build, Execution, Deployment → Build Tools → Maven**).
5. Wait for dependency downloads to finish.

### 8.2 Build the jar

From the repository root (the folder containing `pom.xml`), run:

```powershell
mvn clean package
```

If Maven is not on your PATH, run the `clean` and `package` lifecycle goals from IntelliJ's Maven tool window instead.

**Success check:** the build ends with `BUILD SUCCESS` and creates `target/app.jar`, a single jar that includes the MySQL driver.

## 9. Run automated tests

### 9.1 Unit tests

```powershell
mvn clean test
```

This runs the unit tests (`*Test` classes) through Maven Surefire. They do not need a database. Results are written to `target/surefire-reports/`.

### 9.2 Integration tests

`CapitalReportsIT` runs real queries against the world database on `localhost:33060`. Classes named `*IT` are **not** run by `mvn test` or `mvn package`, so run them from IntelliJ:

1. Start the database (see [10.1](#101-start-the-database)).
2. Right-click `src/test/java/com/napier/devops/CapitalReportsIT.java` and choose **Run**.

**Success check:** the tests actually ran (the summary shows the expected number of tests) and none failed. A green build with zero tests is not evidence that anything was tested.

## 10. Configure and verify the MySQL database

The application uses the coursework `world` database (`db/world.sql`), run locally in Docker. No external database or VPN is needed.

| Setting  | Value                                                                  |
|----------|------------------------------------------------------------------------|
| Image    | MySQL 8.4, built from `db/Dockerfile`                                  |
| Database | `world` (tables `city`, `country`, `countrylanguage`)                  |
| Host     | `localhost:33060` from your machine; `db:3306` from the app container  |
| User     | `root`                                                                 |
| Password | `example` (set in `db/Dockerfile`)                                     |

These are local development credentials for a throwaway container, not real secrets. Never commit real passwords.

### 10.1 Start the database

```powershell
docker compose up -d db
```

The first start takes about a minute while `world.sql` is imported. Check progress with:

```powershell
docker compose ps
```

Wait until the `db` service shows `(healthy)`.

### 10.2 Verify the data

```powershell
docker compose exec db mysql -uroot -pexample -e "SELECT COUNT(*) FROM world.city;"
```

**Success check:** the query returns `4079`.

### 10.3 Application database settings

The application takes the database location as its first argument and a start-up delay in milliseconds as its second:

| How it runs           | Arguments               | Notes                                                        |
|-----------------------|-------------------------|--------------------------------------------------------------|
| IntelliJ, default     | none                    | Connects to `localhost:33060` with no delay                  |
| IntelliJ, custom port | e.g. `localhost:3307 0` | Set under **Run → Edit Configurations → Program arguments**  |
| Docker / CI           | `db:3306 10000`         | Set by the `ENTRYPOINT` in `Dockerfile`                      |

The username and password default to `root` / `example`. To use different credentials, set the `DB_USER` and `DB_PASSWORD` environment variables (in IntelliJ: **Run → Edit Configurations → Environment variables**).

## 11. Run the application

### 11.1 From IntelliJ

1. Start the database ([10.1](#101-start-the-database)).
2. Run `src/main/java/com/napier/devops/App.java`.

The app prints the city count, then each report (R01-R06, R12-R25, R27-R29) to the console. Reports R07-R11 (all cities) are written as text files to the `reports/` folder in the project root. That folder is in `.gitignore`, so the files are never committed.

### 11.2 With Docker Compose

Build the jar first, because the application image copies `target/app.jar`:

```powershell
mvn clean package
docker compose up --build --exit-code-from app
```

Compose builds both images, waits until the database is healthy, then runs the app. The command exits when the app finishes, with the app's exit code: `0` if every report ran, `1` if the app could not reach the database.

### 11.3 Clean up

```powershell
docker compose down -v
```

`-v` also deletes the database volume, so the next start imports `world.sql` again from scratch.

**Success check:** the app prints `Successfully connected`, `Cities in database: 4079`, and every report section without `Query failed` messages.

## 12. Check GitHub Actions

The workflow in `.github/workflows/main.yml` runs on every push and pull request. It:

1. Sets up Java 17 (Temurin).
2. Runs `mvn -B package`, which compiles the code and runs the unit tests.
3. Runs `docker compose up --build --exit-code-from app`, so the app runs every report against the real database.
4. Runs `docker compose down -v` to clean up, even if an earlier step failed.

The workflow does **not** run the `*IT` integration tests yet.

To check a run:

1. Open the repository on GitHub and select **Actions**.
2. Open the run for your latest commit.
3. If a step failed, open it and read the log. A failure in step 3 usually means a report query failed or the app could not connect.

**Success check:** the run for your latest commit is green.

## 13. Use the team Git workflow

### 13.1 Start a task

1. Find or create the GitHub Issue for the task.
2. Note the issue number and agreed acceptance criteria.
3. Update `develop`:

```powershell
git switch develop
git pull origin develop
```

4. Create a feature branch with a meaningful name, for example:

```powershell
git switch -c docs/update-setup-guide
```

Use the branch naming convention agreed by the group.

### 13.2 Commit and push

After editing files, inspect your changes:

```powershell
git status
git diff
```

Stage and commit only the intended files:

```powershell
git add README.md SETUP.md LICENSE
git commit -m "Update project setup documentation"
git push -u origin docs/update-setup-guide
```

Replace the example branch name if you used another name. If your change includes other files, stage those intentionally rather than blindly adding everything.

### 13.3 Open a pull request

1. Open the repository on GitHub.
2. Create a pull request from your feature branch **into `develop`**.
3. Explain what changed and how you tested it.
4. If the PR completes a GitHub Issue, include `Closes #<issue-number>` in the PR description, replacing the placeholder with the actual issue number.
5. Request a teammate review.
6. Resolve comments and wait for the required approvals and checks.
7. Merge according to the repository's branch protection rules.

Do not commit directly to `master`. Follow the group's agreed process for release and final merges to `master`.

### 13.4 After the PR is merged

Update your local `develop` branch:

```powershell
git switch develop
git pull origin develop
```

You can then start the next task from the updated branch.

## 14. Troubleshooting

### `java` or `javac` is not recognised

- Install a JDK, not just a runtime.
- Open a new terminal.
- Check `PATH` and `JAVA_HOME`.
- Set IntelliJ's Project SDK to JDK 17.

### Maven says there is no POM

- Check that you are in the repository root.
- Run `Test-Path .\pom.xml`.
- If no `pom.xml` exists, the Maven project setup is incomplete; do not run Maven commands from a random subfolder.

### Maven uses the wrong Java version

- Run `mvn -version`.
- Check the JDK configured for Maven in IntelliJ.
- Confirm the project SDK and Maven runner use JDK 17.

### Docker says it cannot connect to the daemon

- Start Docker Desktop.
- Wait for the engine to finish starting.
- Run `docker info` again.
- Follow Docker's official Windows/WSL troubleshooting guidance if the engine still fails.

### Database connection fails

- Check the database container is running and healthy: `docker compose ps`.
- On the first start, wait for the `world.sql` import to finish before running the app.
- Check nothing else is using port `33060`. If you change the port, pass the new location to the app (see [10.3](#103-application-database-settings)).
- If the data looks wrong or incomplete, reset it with `docker compose down -v` and start the database again.

### Docker build fails with "target/app.jar not found"

- Run `mvn clean package` first; the application image copies the jar built by Maven.

### Git says there are conflicts

- Read the conflicted file markers.
- Resolve the conflict deliberately, preserving the intended changes.
- Run the relevant build/tests.
- Commit the resolution and push the branch.
- Ask the reviewer/team if you are unsure which version is correct.

### CI fails but local build passes

- Open the failed GitHub Actions step.
- Check differences in Java version, environment variables, database access and case-sensitive file paths.
- Reproduce the CI run locally with `mvn clean package` followed by `docker compose up --build --exit-code-from app`.

## 15. Setup checklist

Use this checklist after following the guide:

- [ ] Java JDK 17 installed and verified.
- [ ] Git installed and commit identity checked.
- [ ] IntelliJ installed and project SDK set to Java 17.
- [ ] Docker Desktop installed and `docker info` succeeds.
- [ ] Repository cloned from the Group 22 GitHub URL.
- [ ] Local `develop` branch updated.
- [ ] IntelliJ recognises the Maven project.
- [ ] `mvn clean package` passes and creates `target/app.jar`.
- [ ] The database container is healthy and `world.city` has 4079 rows.
- [ ] `App` runs from IntelliJ against `localhost:33060`.
- [ ] `docker compose up --build --exit-code-from app` exits with code 0.
- [ ] GitHub Actions passes for the latest commit.
- [ ] A feature branch and pull request have been used successfully.

Tick an item only after you have performed and verified it.
