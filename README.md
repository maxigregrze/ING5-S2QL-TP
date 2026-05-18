# ING5-S2QL-TP

![Quality](https://github.com/maxigregrze/ING5-S2QL-TP/actions/workflows/quality.yml/badge.svg)
![SonarQube](https://github.com/maxigregrze/ING5-S2QL-TP/actions/workflows/sonar.yml/badge.svg)

Java (Maven) project with quality tooling: **Checkstyle**, **PMD CPD** (duplication), **JaCoCo** (coverage), and **SonarQube** in GitHub Actions.

## Local build

```powershell
mvn -B clean verify
```

## SonarQube / GitHub Actions

Add these repository secrets (**Settings → Secrets and variables → Actions**): `SONAR_HOST_URL`, `SONAR_TOKEN`, `SONAR_PROJECT_KEY` (see your course quality guide).

On the Sonar server, create a quality gate (e.g. **TP-Qualite**) with the thresholds from the guide (new code coverage ≥ 80 %, duplication, ratings A, security hotspots reviewed 100 %, etc.).

## Volume metrics (LOC)

With [cloc](https://github.com/AlDanial/cloc):

```powershell
cloc src/main/java --include-lang=Java
```

The quality workflow also runs `cloc` on Ubuntu and prints the counts in the job log.
