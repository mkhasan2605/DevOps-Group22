
package com.napier.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the PlacePopulation model.
 */
class PlacePopulationTest {

    @Test
    void constructorStoresNameAndPopulation() {
        PlacePopulation place =
                new PlacePopulation("Asia", 3705025700L);

        assertEquals("Asia", place.getName());
        assertEquals(3705025700L, place.getPopulation());
    }
}
