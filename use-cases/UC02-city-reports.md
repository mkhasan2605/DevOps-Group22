# UC02: City Reports

Columns for every city report: Name, Country, District, Population.

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.

---

## Top N Cities (R12 to R16) - raysharris822-sketch

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