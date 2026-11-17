package com.housemanager.data.repo;

import com.housemanager.data.entity.Apartment;
import com.housemanager.data.entity.Building;
import com.housemanager.data.entity.Company;
import com.housemanager.data.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ApartmentRepositoryTest {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Building building1;
    private Building building2;

    @BeforeEach
    public void init() {
        Company company = new Company();
        company.setCompanyName("HomeCare Management");
        testEntityManager.persist(company);

        Employee employee = new Employee();
        employee.setFirstName("Ivan");
        employee.setLastName("Petrov");
        employee.setBirthday(LocalDate.of(1985, 4, 12));
        employee.setCompany(company);
        testEntityManager.persist(employee);

        building1 = createBuilding("Sunrise Residence", "12A", company, employee);
        building2 = createBuilding("Green Park", "25B", company, employee);
    }

    private Building createBuilding(String name, String number, Company company, Employee employee) {
        Building building = new Building();
        building.setBuildingName(name);
        building.setBuildingNumber(number);
        building.setAddress("12 Main Street, Sofia");
        building.setNumberOfFloors(8);
        building.setBuildingArea(2400);
        building.setCommonArea(350);
        building.setCompany(company);
        building.setEmployee(employee);
        return testEntityManager.persist(building);
    }

    private Apartment createApartment(int number, Building building) {
        Apartment apartment = new Apartment();
        apartment.setApartmentNumber(number);
        apartment.setFloor(1);
        apartment.setApartmentArea(new BigDecimal("85.50"));
        apartment.setNumberOfPetsUsingCommonParts(0);
        apartment.setBuilding(building);
        return apartment;
    }

    @Test
    public void findByBuildingId() {
        Apartment apartment1 = createApartment(101, building1);
        Apartment apartment2 = createApartment(102, building1);
        Apartment apartment3 = createApartment(201, building2);

        this.apartmentRepository.saveAll(Arrays.asList(apartment1, apartment2, apartment3));

        List<Apartment> expectedApartments = Arrays.asList(apartment1, apartment2);

        List<Apartment> actualApartments = apartmentRepository.findByBuildingId(building1.getId());

        assertIterableEquals(expectedApartments, actualApartments);
    }

    @Test
    public void countByBuildingId() {
        this.apartmentRepository.saveAll(Arrays.asList(
                createApartment(101, building1),
                createApartment(102, building1),
                createApartment(201, building2)));

        assertEquals(2, apartmentRepository.countByBuildingId(building1.getId()));
        assertEquals(1, apartmentRepository.countByBuildingId(building2.getId()));
    }
}