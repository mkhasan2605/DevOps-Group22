# UC05: Population Information

**Team Member:** Sandiisme  
**Matriculation Number:** 40841071  
**Requirements:** R27–R31  
**Functional Area:** Population of a Named Place

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.

---

## Population of a Named Place (R27 to R31) - 40841071

---

### UC-R27: View population of a continent

**Goal:** See the total population of a selected continent.

**Main success scenario:**
1. The analyst selects the "population of a continent" option.
2. The analyst enters or selects a continent.
3. The system retrieves the population information for all countries in that continent.
4. The system calculates the total population of the selected continent.
5. The system displays the continent name and its total population.

**Extensions:**
- 2a. The continent does not exist: the system tells the analyst and asks for another continent.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the total population of the selected continent.

---

### UC-R28: View population of a region

**Goal:** See the total population of a selected region.

**Main success scenario:**
1. The analyst selects the "population of a region" option.
2. The analyst enters or selects a region.
3. The system retrieves the population information for all countries in that region.
4. The system calculates the total population of the selected region.
5. The system displays the region name and its total population.

**Extensions:**
- 2a. The region does not exist: the system tells the analyst and asks for another region.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the total population of the selected region.

---

### UC-R29: View population of a country

**Goal:** See the total population of a selected country.

**Main success scenario:**
1. The analyst selects the "population of a country" option.
2. The analyst enters or selects a country.
3. The system searches the world database for the selected country.
4. The system retrieves the population of that country.
5. The system displays the country name and its total population.

**Extensions:**
- 2a. The country does not exist: the system tells the analyst and asks for another country.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the total population of the selected country.

---

### UC-R30: View population of a district

**Goal:** See the total population of a selected district.

**Main success scenario:**
1. The analyst selects the "population of a district" option.
2. The analyst enters a district and its country.
3. The system retrieves the population information for all cities in the selected district and country.
4. The system calculates the total population of the district using the available city population records.
5. The system displays the district name, country and calculated population.

**Extensions:**
- 2a. The district does not exist: the system tells the analyst and asks for another district.
- 2b. The country does not exist: the system asks the analyst to enter a valid country.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the calculated population of the selected district based on the available city records.

---

### UC-R31: View population of a city

**Goal:** See the population of a selected city.

**Main success scenario:**
1. The analyst selects the "population of a city" option.
2. The analyst enters a city and its country.
3. The system searches the world database for the selected city in that country.
4. The system retrieves the population of the selected city.
5. The system displays the city name, country and population.

**Extensions:**
- 2a. The city does not exist: the system tells the analyst and asks for another city.
- 2b. The country does not exist: the system asks the analyst to enter a valid country.
- 3a. The database is unavailable: the system shows an error message.

**Postcondition:** The analyst has seen the population of the selected city.

---