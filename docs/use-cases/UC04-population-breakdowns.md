# UC04 – Population Breakdowns

## Characteristic Information

**Goal in Context:** An analyst wants to compare the total population, population living in cities and population not living in cities for each continent, region and country.

**Scope:** Organisation  
**Level:** Primary task  
**Preconditions:** The world database is available and contains the required population and city data.  
**Success End Condition:** The requested population breakdown report is displayed.  
**Failed End Condition:** No report is produced and an appropriate error message is displayed.  
**Primary Actor:** Analyst  
**Trigger:** The analyst requests a population breakdown.

## Main Success Scenario

1. The analyst requests a population breakdown by continent, region or country.
2. The system retrieves the relevant population data from the database.
3. The system groups the data by the requested geographical level.
4. The system determines the total population, population living in cities and population not living in cities.
5. The system calculates the percentages of the total population living in cities and not living in cities.
6. The system displays the report with the relevant population figures and percentages.

## Extensions

2a. **Database unavailable:** The system displays an appropriate error message and does not produce the report.

## Sub-Variations

1. **Continent:** Generate the report for each continent (R23).
2. **Region:** Generate the report for each region (R24).
3. **Country:** Generate the report for each country (R25).

## Reports Covered

| ID  | Requirement                              |
|-----|------------------------------------------|
| R23 | Population breakdown for each continent. |
| R24 | Population breakdown for each region.    |
| R25 | Population breakdown for each country.   |

**Report columns:**
- Name
- Total population
- Population living in cities
- Population living in cities (%)
- Population not living in cities
- Population not living in cities (%)

**Related Issues:** #36, #37, #38