
# UC02: City Reports

Columns for every city report: Name, Country, District, Population.

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.



---

## All Cities (R07 to R11) - 40841087

---

### UC-R07: View all cities in the world

**Goal:** See all cities in the world, ordered from largest to smallest population.

**Main success scenario:**

1. The analyst selects the "all cities in the world" report.
2. The system retrieves all cities from the world database.
3. The system sorts the cities from largest to smallest population.
4. The system displays Name, Country, District and Population for each city.

**Extensions:**

* 2a. The database is unavailable: the system shows an error message.
* 2b. No cities are found: the system displays a message indicating that no cities are available.

**Postcondition:** The analyst has seen all cities in the world ordered by population.

---

### UC-R08: View all cities in a continent

**Goal:** See all cities grouped by continent, ordered from largest to smallest population within each continent.

**Main success scenario:**

1. The analyst selects the "cities by continent" report.
2. The system retrieves the continents available in the world database.
3. The system retrieves the cities belonging to each continent.
4. The system sorts the cities in each continent from largest to smallest population.
5. The system displays Name, Country, District and Population for each city, grouped by continent.

**Extensions:**

* 2a. No continents are found: the system displays a message indicating that no continents are available.
* 3a. A continent has no cities: the system displays an empty group or omits that group.
* 3b. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen cities grouped by continent and ordered by population within each continent.

---

### UC-R09: View all cities in a region

**Goal:** See all cities grouped by region, ordered from largest to smallest population within each region.

**Main success scenario:**

1. The analyst selects the "cities by region" report.
2. The system retrieves the regions available in the world database.
3. The system retrieves the cities belonging to each region.
4. The system sorts the cities in each region from largest to smallest population.
5. The system displays Name, Country, District and Population for each city, grouped by region.

**Extensions:**

* 2a. No regions are found: the system displays a message indicating that no regions are available.
* 3a. A region has no cities: the system displays an empty group or omits that group.
* 3b. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen cities grouped by region and ordered by population within each region.

---

### UC-R10: View all cities in a country

**Goal:** See all cities grouped by country, ordered from largest to smallest population within each country.

**Main success scenario:**

1. The analyst selects the "cities by country" report.
2. The system retrieves the countries available in the world database.
3. The system retrieves the cities belonging to each country.
4. The system sorts the cities in each country from largest to smallest population.
5. The system displays Name, Country, District and Population for each city, grouped by country.

**Extensions:**

* 2a. No countries are found: the system displays a message indicating that no countries are available.
* 3a. A country has no cities: the system displays an empty group or omits that group.
* 3b. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen cities grouped by country and ordered by population within each country.

---

### UC-R11: View all cities in a district

**Goal:** See all cities grouped by district, ordered from largest to smallest population within each district.

**Main success scenario:**

1. The analyst selects the "cities by district" report.
2. The system retrieves the districts available in the world database.
3. The system retrieves the cities belonging to each district.
4. The system sorts the cities in each district from largest to smallest population.
5. The system displays Name, Country, District and Population for each city, grouped by district.

**Extensions:**

* 2a. No districts are found: the system displays a message indicating that no districts are available.
* 3a. A district has no cities: the system displays an empty group or omits that group.
* 3b. A city has a missing district: the system groups the city under "Unknown".
* 3c. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen cities grouped by district and ordered by population within each district.

---

## Top N Cities (R12 to R16) - 40840483

---

### UC-R12: View top N cities in the world

**Goal:** See the N most populated cities in the world.

**Main success scenario:**
1. The analyst selects the "top N cities in the world" report.
2. The analyst enters a number N.
3. The system retrieves all cities and sorts them from largest to smallest population.
4. The system keeps the first N cities.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**
- 2a. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the N largest cities in the world.

---

### UC-R13: View top N cities in a continent

**Goal:** See the N most populated cities in one continent.

**Main success scenario:**
1. The analyst selects the "top N cities in a continent" report.
2. The analyst enters a continent and a number N.
3. The system retrieves the cities in that continent, sorted from largest to smallest population.
4. The system keeps the first N cities.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the N largest cities in the continent.

---

### UC-R14: View top N cities in a region

**Goal:** See the N most populated cities in one region.

**Main success scenario:**
1. The analyst selects the "top N cities in a region" report.
2. The analyst enters a region and a number N.
3. The system retrieves the cities in that region, sorted from largest to smallest population.
4. The system keeps the first N cities.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the N largest cities in the region.

---

### UC-R15: View top N cities in a country

**Goal:** See the N most populated cities in one country.

**Main success scenario:**
1. The analyst selects the "top N cities in a country" report.
2. The analyst enters a country and a number N.
3. The system retrieves the cities in that country, sorted from largest to smallest population.
4. The system keeps the first N cities.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**
- 2a. The country does not exist: the system tells the analyst and asks for another country.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the N largest cities in the country.

---

### UC-R16: View top N cities in a district

**Goal:** See the N most populated cities in one district.

**Main success scenario:**
1. The analyst selects the "top N cities in a district" report.
2. The analyst enters a district and a number N.
3. The system retrieves the cities in that district, sorted from largest to smallest population.
4. The system keeps the first N cities.
5. The system displays Name, Country, District and Population for each city.

**Extensions:**
- 2a. The district does not exist: the system tells the analyst and asks for another district.
- 2b. N is not a positive whole number: the system asks the analyst to enter a valid number.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the N largest cities in the district.