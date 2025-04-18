package com.officemanagement.resource;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for resource integration tests using QuarkusTest. Sets up REST Assured and provides
 * utility methods for cleaning test data.
 */
@QuarkusTest
public abstract class BaseResourceTest {

    @Inject EntityManager entityManager;

    // Re-added explicit cleanup before each test method, removed flush/clear
    @BeforeEach
    @Transactional
    public void setupTestDatabase() {
        // Delete in reverse order of dependencies to avoid constraint violations
        // Ensure FloorPlanimetry is deleted if it exists and has dependencies
        // Add any other entities that need cleanup
        entityManager.createQuery("DELETE FROM Employee e").executeUpdate();
        entityManager.createQuery("DELETE FROM Seat s").executeUpdate();
        entityManager.createQuery("DELETE FROM OfficeRoom r").executeUpdate();
        entityManager
                .createQuery("DELETE FROM FloorPlanimetry fp")
                .executeUpdate(); // Assuming this exists
        entityManager.createQuery("DELETE FROM Floor f").executeUpdate();

    }

    // Utility method to get Response.Status constants
    protected Response.Status getStatus(int statusCode) {
        return Response.Status.fromStatusCode(statusCode);
    }

}
