# UC06: Language Report

**Team Member:** Mohammad Hasan  
**Matriculation Number:** 40805651  
**Requirements:** R32  
**Functional Area:** Language Information

Columns for the language report: Language, Speakers, % of world population.

Actor for all use cases: **Organisation analyst**

Preconditions for all use cases: the world database is available and the application is running.

---

## Language Report (R32) - 40805651

---

### UC-R32: View the number of people who speak the major languages

**Goal:** See how many people speak Chinese, English, Hindi, Spanish and Arabic, and what percentage of the world population each language represents, ordered from the most speakers to the fewest.

**Main success scenario:**
1. The analyst selects the "language report" option.
2. The system retrieves, for each country, its population and the percentage of people who speak each of the five languages.
3. The system calculates the number of speakers in each country as its population multiplied by the language's percentage.
4. The system adds up the speakers of each language across all countries.
5. The system calculates each language's speakers as a percentage of the world population.
6. The system displays Language, Speakers and % of world population, ordered from the most speakers to the fewest.

**Extensions:**
- 2a. The database is unavailable: the system shows an error message.
- 2b. No language data is found: the system displays a message indicating that no results are available.

**Postcondition:** The analyst has seen the number of speakers of each of the five languages and their share of the world population.

---
