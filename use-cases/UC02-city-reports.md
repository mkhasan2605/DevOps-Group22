# UC02: City Reports

Columns for every city report: **Name, Country, District, Population.**

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: The world database is available and the application is running.

---

## All Cities (R07 to R11) - 40841087

---

### UC-R07: View all cities in the world

**Goal:** See all cities in the world, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst selects the "all cities in the world" report.
2. The system retrieves all cities from the world database.
3. The system sorts the cities from largest to smallest population.
4. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system shows an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** The analyst has seen all cities in the world, ordered by population.

---

### UC-R08: View all cities in a continent

**Goal:** See all cities in one continent, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst selects the "all cities in a continent" report.
2. The analyst enters a continent.
3. The system retrieves all cities belonging to the selected continent.
4. The system sorts the cities from largest to smallest population.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The continent does not exist: the system tells the analyst and asks for another continent.
* 3a. The database is unavailable: the system shows an error message.
* 3b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** The analyst has seen all cities in the selected continent, ordered by population.

---

### UC-R09: View all cities in a region

**Goal:** See all cities in one region, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst selects the "all cities in a region" report.
2. The analyst enters a region.
3. The system retrieves all cities belonging to the selected region.
4. The system sorts the cities from largest to smallest population.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The region does not exist: the system tells the analyst and asks for another region.
* 3a. The database is unavailable: the system shows an error message.
* 3b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** The analyst has seen all cities in the selected region, ordered by population.

---

### UC-R10: View all cities in a country

**Goal:** See all cities in one country, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst selects the "all cities in a country" report.
2. The analyst enters a country.
3. The system retrieves all cities belonging to the selected country.
4. The system sorts the cities from largest to smallest population.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The country does not exist: the system tells the analyst and asks for another country.
* 3a. The database is unavailable: the system shows an error message.
* 3b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** The analyst has seen all cities in the selected country, ordered by population.

---

### UC-R11: View all cities in a district

**Goal:** See all cities in one district, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst selects the "all cities in a district" report.
2. The analyst enters a district.
3. The system retrieves all cities belonging to the selected district.
4. The system sorts the cities from largest to smallest population.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The district does not exist: the system tells the analyst and asks for another district.
* 3a. The database is unavailable: the system shows an error message.
* 3b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** The analyst has seen all cities in the selected district, ordered by population.
