# UC01: Country Reports

Columns for every country report: Code, Name, Continent, Region, Population, Capital.

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.

---

## Country Reports (R01 to R06) - 40858561

---

### UC-R01: View all countries in the world

**Goal:** See all countries in the world, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all countries in the world" report.
2. The system retrieves all countries in the world.
3. The system sorts the countries from largest to smallest population.
4. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. The database is unavailable: the system shows an error message.
- 2b. No countries are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all countries in the world, ordered by population.

---

### UC-R02: View all countries in a continent

**Goal:** See all countries in one continent, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all countries in a continent" report.
2. The analyst enters a continent.
3. The system retrieves the countries in that continent.
4. The system sorts the countries from largest to smallest population.
5. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 3a. The database is unavailable: the system shows an error message.
- 3b. No matching countries are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all countries in the selected continent, ordered by population.

---

### UC-R03: View all countries in a region

**Goal:** See all countries in one region, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all countries in a region" report.
2. The analyst enters a region.
3. The system retrieves the countries in that region.
4. The system sorts the countries from largest to smallest population.
5. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 3a. The database is unavailable: the system shows an error message.
- 3b. No matching countries are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all countries in the selected region, ordered by population.

---

### UC-R04: View top N countries in the world

**Goal:** See the N most populated countries in the world.

**Main success scenario:**
1. The analyst selects the "top N countries in the world" report.
2. The analyst enters a number N.
3. The system retrieves all countries and sorts them from largest to smallest population.
4. The system keeps the first N countries.
5. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N countries are available: the system displays all available countries.

**Postcondition:** The analyst has seen up to N of the largest countries in the world.

---

### UC-R05: View top N countries in a continent

**Goal:** See the N most populated countries in one continent.

**Main success scenario:**
1. The analyst selects the "top N countries in a continent" report.
2. The analyst enters a continent and a number N.
3. The system retrieves the countries in that continent, sorted from largest to smallest population.
4. The system keeps the first N countries.
5. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N matching countries are available: the system displays all available matching countries.

**Postcondition:** The analyst has seen up to N of the largest countries in the selected continent.

---

### UC-R06: View top N countries in a region

**Goal:** See the N most populated countries in one region.

**Main success scenario:**
1. The analyst selects the "top N countries in a region" report.
2. The analyst enters a region and a number N.
3. The system retrieves the countries in that region, sorted from largest to smallest population.
4. The system keeps the first N countries.
5. The system displays Code, Name, Continent, Region, Population and Capital for each country.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N matching countries are available: the system displays all available matching countries.

**Postcondition:** The analyst has seen up to N of the largest countries in the selected region.