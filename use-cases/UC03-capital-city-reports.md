# UC03: Capital City Reports

Columns for every capital city report: Name, Country, Population.

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.

---

## Capital City Reports (R17 to R22) - 40840498

---

### UC-R17: View all capital cities in the world

**Goal:** See all capital cities in the world, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all capital cities in the world" report.
2. The system retrieves all capital cities in the world.
3. The system sorts the capital cities from largest to smallest population.
4. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. The database is unavailable: the system shows an error message.
- 2b. No capital cities are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all capital cities in the world, ordered by population.

---

### UC-R18: View all capital cities in a continent

**Goal:** See all capital cities in one continent, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all capital cities in a continent" report.
2. The analyst enters a continent.
3. The system retrieves the capital cities in that continent.
4. The system sorts the capital cities from largest to smallest population.
5. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 3a. The database is unavailable: the system shows an error message.
- 3b. No matching capital cities are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all capital cities in the selected continent, ordered by population.

---

### UC-R19: View all capital cities in a region

**Goal:** See all capital cities in one region, ordered from largest to smallest population.

**Main success scenario:**
1. The analyst selects the "all capital cities in a region" report.
2. The analyst enters a region.
3. The system retrieves the capital cities in that region.
4. The system sorts the capital cities from largest to smallest population.
5. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 3a. The database is unavailable: the system shows an error message.
- 3b. No matching capital cities are found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen all capital cities in the selected region, ordered by population.

---

### UC-R20: View top N capital cities in the world

**Goal:** See the N most populated capital cities in the world.

**Main success scenario:**
1. The analyst selects the "top N capital cities in the world" report.
2. The analyst enters a number N.
3. The system retrieves all capital cities and sorts them from largest to smallest population.
4. The system keeps the first N capital cities.
5. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N capital cities are available: the system displays all available capital cities.

**Postcondition:** The analyst has seen up to N of the largest capital cities in the world.

---

### UC-R21: View top N capital cities in a continent

**Goal:** See the N most populated capital cities in one continent.

**Main success scenario:**
1. The analyst selects the "top N capital cities in a continent" report.
2. The analyst enters a continent and a number N.
3. The system retrieves the capital cities in that continent, sorted from largest to smallest population.
4. The system keeps the first N capital cities.
5. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N matching capital cities are available: the system displays all available matching capital cities.

**Postcondition:** The analyst has seen up to N of the largest capital cities in the selected continent.

---

### UC-R22: View top N capital cities in a region

**Goal:** See the N most populated capital cities in one region.

**Main success scenario:**
1. The analyst selects the "top N capital cities in a region" report.
2. The analyst enters a region and a number N.
3. The system retrieves the capital cities in that region, sorted from largest to smallest population.
4. The system keeps the first N capital cities.
5. The system displays Name, Country and Population for each capital city.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.
- 4a. Fewer than N matching capital cities are available: the system displays all available matching capital cities.

**Postcondition:** The analyst has seen up to N of the largest capital cities in the selected region.
