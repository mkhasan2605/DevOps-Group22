# Group 22 – Detailed Setup Guide

This guide explains how to prepare a development environment for the SET09803 DevOps Group 22 Population Information System, then verify the project step by step.

> **Repository status note:** The instructions distinguish between general setup steps and project-specific commands that still need verification. Before relying on a command, confirm that the relevant file (`pom.xml`, `Dockerfile`, workflow, or database configuration) exists in the current repository and that the command passes. Do not invent database credentials or commit secrets.

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
11. [Build and test with Docker](#11-build-and-test-with-docker)
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
- **Docker Desktop** — for building/running the container once the project has a tested Docker configuration.
- **The coursework MySQL population database** — use the access details and instructions supplied by the module.
- **GitHub access** — you must be a collaborator/member with permission to access the Group 22 repository.

Maven is normally configured in a Maven project through `pom.xml`; IntelliJ has Maven integration. You can use IntelliJ's bundled Maven support, or install Maven separately if you want to run `mvn` from PowerShell.

### Recommended installation order

1. Java JDK 17
2. Git
3. IntelliJ IDEA
4. Docker Desktop
5. Repository clone and project checks
6. Database access, once the module's connection instructions are available

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

> Installing Docker does not mean the application is containerised yet. A working `Dockerfile` and any required database/network configuration must also exist and be tested before the project can be built and run in a container.

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

In IntelliJ, look at the Project panel and check whether the repository contains the files below. This is the intended Maven structure; some files may still need to be created by the team.

```text
DevOps-Group22/
├── .github/
│   └── workflows/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       └── java/
├── .gitignore
├── CODE_OF_CONDUCT.md
├── Dockerfile
├── LICENSE
├── pom.xml
├── README.md
└── SETUP.md
```

What the main files/folders are for:

- `pom.xml` — Maven project definition, dependencies, Java version and build/test plugins.
- `src/main/java/` — application source code.
- `src/test/java/` — automated tests.
- `Dockerfile` — instructions for creating the application image.
- `.github/workflows/` — GitHub Actions workflows for automated build and tests.
- `README.md` — short project overview, requirement status and evidence.
- `SETUP.md` — this detailed setup guide.

**Important:** Do not create an empty `Dockerfile`, Maven configuration, or workflow just to make the tree look complete. Add these files with the team, then test them and update this guide to match the actual implementation.

## 8. Configure Maven and build the application

### 8.1 Confirm `pom.xml` exists

The Maven commands below must be run from the directory containing `pom.xml`. In PowerShell:

```powershell
Get-ChildItem
Test-Path .\pom.xml
```

`Test-Path` should return `True`. If it returns `False`, the Maven project has not yet been set up at that location. Do not run Maven build commands from a different folder and assume they test this project.

### 8.2 Open the Maven project in IntelliJ

1. Open the repository folder in IntelliJ.
2. If IntelliJ prompts to load/import the Maven project, accept.
3. Open the Maven tool window and check that the project is recognised.
4. Check that the Maven importer uses JDK 17. Depending on the IntelliJ version, this may be under **Settings → Build, Execution, Deployment → Build Tools → Maven**.
5. Wait for dependency downloads to finish.

### 8.3 Build from the terminal

In the IntelliJ terminal or PowerShell at the repository root, run:

```powershell
mvn -version
mvn clean package
```

`mvn -version` should show Maven and the Java runtime it uses. Confirm the Java runtime is 17.

If Maven is not installed on your PATH, use the Maven tool window in IntelliJ or install Maven following the official instructions. If the repository later includes a Maven Wrapper (`mvnw.cmd` and `.mvn/`), prefer the wrapper command documented by the team.

**Success check:** `mvn clean package` ends with `BUILD SUCCESS` and produces build output under `target/`.

If it fails, keep the full error output; do not treat the build as passed until the cause has been resolved.

## 9. Run automated tests

### 9.1 Run tests locally

From the directory containing `pom.xml`, run:

```powershell
mvn clean test
```

Maven should compile the application and run tests configured in the project. A successful command normally ends with `BUILD SUCCESS`.

### 9.2 Inspect the test results

- Review the test summary in the terminal.
- Inspect `target/surefire-reports/` if the project uses Maven Surefire.
- In IntelliJ, you can also run a test class or test method using the gutter run icon.

**Success check:** Tests have actually executed, the test report shows the expected number of tests, and there are no unexpected failures. A successful build with zero tests is not evidence of test coverage.

### 9.3 If tests fail

1. Read the first meaningful error and the failing test name.
2. Decide whether the issue is a compilation error, assertion failure, missing dependency, or database/configuration issue.
3. Reproduce it locally.
4. Fix the code or test, then rerun `mvn clean test`.
5. Include the test in the PR and ensure the CI workflow runs it.

The team should add tests for report logic and relevant edge cases. Integration tests that require MySQL must document how the test database is provided and must not depend on a developer's private machine configuration.

## 10. Configure and verify the MySQL database

The application uses the MySQL population database supplied for the coursework. Use the host, port, database name, username, password and access method provided by the module. Those details are intentionally not included here because they have not been verified for this guide.

### 10.1 Obtain the official connection details

1. Read the current module instructions for accessing the population database.
2. Confirm whether the database is accessed on the university network, through a VPN/SSH tunnel, or through a local database/container.
3. Confirm the host, port, schema/database name and permitted credentials.
4. Ask the module team if any detail is missing. Do not guess the hostname or reuse another student's credentials.

### 10.2 Keep credentials out of Git

- Never commit database passwords, tokens, private keys or other secrets.
- Use the configuration method agreed by the team, such as environment variables or a local untracked configuration file.
- Add any local secret/configuration file to `.gitignore`.
- If the team uses environment variables, document the variable **names** and example placeholder values, not real secrets.

Example placeholders only (these are not actual Group 22 settings):

```text
DB_HOST=<host supplied by the module>
DB_PORT=<port supplied by the module>
DB_NAME=<database name supplied by the module>
DB_USER=<username supplied by the module>
DB_PASSWORD=<password supplied securely>
```

The Java application must actually read the chosen configuration before these variables can be used; creating variables alone does not configure the application.

### 10.3 Verify database connectivity

Once the team has documented the supported connection method:

1. Confirm the database host is reachable from your network.
2. Connect using the approved database client or connection method.
3. Run a simple read-only query against a known table, following the module's instructions.
4. Run the application's database connectivity check or a database integration test, if implemented.
5. Record the verified steps in this guide without exposing credentials.

**Success check:** You can connect using your own authorised access and retrieve data from the supplied database. A successful Java compilation alone does not prove database connectivity.

## 11. Build and test with Docker

Only follow the image build/run commands after the repository contains a tested `Dockerfile`. The correct image name, JAR path, ports, environment variables and database networking depend on the actual implementation, so they must be confirmed in the project's files.

### 11.1 Check Docker is running

```powershell
docker info
```

### 11.2 Check for a Dockerfile

From the repository root:

```powershell
Test-Path .\Dockerfile
```

If the result is `False`, Docker packaging has not yet been added at that path. Do not try to build an image until the team has created and reviewed the Dockerfile.

### 11.3 Build the image

Once the team has committed a valid Dockerfile, use the image name and command agreed by the project. A typical build command is:

```powershell
docker build -t group22-population .
```

Run this only when the Dockerfile is ready. If the build fails, inspect the first failing build step and confirm the expected JAR name/path and Java base image.

### 11.4 Run the container

The correct `docker run` command depends on how the application receives database configuration and connects to MySQL. Use the exact command documented by the team after testing it. Do not put a real database password directly into a command that may be saved in shell history.

If a `docker-compose.yml` or `compose.yaml` is added, follow its documented service names and environment configuration.

### 11.5 Verify and clean up

- Check container output for startup errors.
- Confirm the application can reach the intended database from inside the container.
- Run a representative report or integration check.
- Stop/remove test containers and images only when they are no longer needed.

**Success check:** The image builds, the container starts, and the required application operation works with the documented configuration. Merely seeing an image in Docker Desktop is not enough.

## 12. Check GitHub Actions

1. Open the Group 22 repository on GitHub.
2. Select **Actions**.
3. Open the latest workflow run for the branch you are checking.
4. Inspect each job and step, including compilation and tests.
5. Confirm the run corresponds to your latest commit.
6. If it fails, open the failed step and read the logs. Fix the cause and push a new commit.

The workflow should build the Maven project and run tests. Docker build/publish steps should be added only as required by the coursework and configured to use the actual Dockerfile. Do not report a green CI badge as proof that tests ran unless the workflow really runs them.

**Success check:** The relevant workflow run for the pushed commit passes, and the workflow's steps show that the required build and test commands ran.

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

- Recheck the official host, port, schema and credentials.
- Confirm you are on the required network or VPN, if applicable.
- Check whether an SSH tunnel is required.
- Never work around access restrictions or share another person's credentials.
- Do not commit secrets while debugging.

### Git says there are conflicts

- Read the conflicted file markers.
- Resolve the conflict deliberately, preserving the intended changes.
- Run the relevant build/tests.
- Commit the resolution and push the branch.
- Ask the reviewer/team if you are unsure which version is correct.

### CI fails but local build passes

- Open the failed GitHub Actions step.
- Check differences in Java version, environment variables, database access and case-sensitive file paths.
- Make sure the workflow runs the same relevant build/test commands.
- Do not add credentials to workflow YAML. Use approved repository/environment secrets if the workflow requires secrets and the module permits that setup.

## 15. Setup checklist

Use this checklist after following the guide:

- [ ] Java JDK 17 installed and verified.
- [ ] Git installed and commit identity checked.
- [ ] IntelliJ installed and project SDK set to Java 17.
- [ ] Docker Desktop installed and `docker info` succeeds.
- [ ] Repository cloned from the Group 22 GitHub URL.
- [ ] Local `develop` branch updated.
- [ ] `pom.xml` exists and Maven recognises the project.
- [ ] `mvn clean package` passes.
- [ ] `mvn clean test` runs the expected tests and passes.
- [ ] Official coursework MySQL access is configured and verified.
- [ ] Docker image builds and container operation is tested, if Docker packaging has been implemented.
- [ ] GitHub Actions passes for the latest commit.
- [ ] A feature branch and pull request have been used successfully.

Tick an item only after you have performed and verified it. If a project file or capability is not implemented yet, leave the relevant item unticked and raise it with the team.
