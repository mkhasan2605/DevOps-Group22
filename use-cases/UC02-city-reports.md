# UC02: City Reports

**Required columns:** Name, Country, District, Population.

**Actor:** Organisation analyst.

**Preconditions:** The World database is available and the application is running.

## R07–R11: City Reports — 40841087

### UC-R07: View all cities in the world

**Goal:** Display all cities in the world, ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst runs the city reports.
2. The system retrieves all cities from the World database.
3. The system orders the cities by population from largest to smallest.
4. The system displays Name, Country, District and Population.

**Extensions:**

* 2a. The database is unavailable: the system displays an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** All available cities are displayed in descending population order.

---

### UC-R08: View all cities by continent

**Goal:** Display cities grouped by continent, with cities in each continent ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst runs the city reports.
2. The system retrieves all cities and their corresponding continents.
3. The system groups the cities by continent.
4. The system orders cities within each continent by population from largest to smallest.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system displays an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** Cities from all available continents are displayed in groups, ordered by population within each group.

---

### UC-R09: View all cities by region

**Goal:** Display cities grouped by region, with cities in each region ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst runs the city reports.
2. The system retrieves all cities and their corresponding regions.
3. The system groups the cities by region.
4. The system orders cities within each region by population from largest to smallest.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system displays an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** Cities from all available regions are displayed in groups, ordered by population within each group.

---

### UC-R10: View all cities by country

**Goal:** Display cities grouped by country, with cities in each country ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst runs the city reports.
2. The system retrieves all cities and their corresponding countries.
3. The system groups the cities by country.
4. The system orders cities within each country by population from largest to smallest.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system displays an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** Cities from all available countries are displayed in groups, ordered by population within each group.

---

### UC-R11: View all cities by district

**Goal:** Display cities grouped by district, with cities in each district ordered by population from largest to smallest.

**Main success scenario:**

1. The analyst runs the city reports.
2. The system retrieves all cities and their corresponding districts.
3. The system groups the cities by district.
4. The system orders cities within each district by population from largest to smallest.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system displays an error message.
* 2b. No cities are found: the system informs the analyst that no results are available.

**Postcondition:** Cities from all available districts are displayed in groups, ordered by population within each group.
