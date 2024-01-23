package com.example.hollywoodProduction.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testIdGetterAndSetter() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testNameGetterAndSetter() {
        String name = "John Doe";
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    public void testEmailGetterAndSetter() {
        String email = "john.doe@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testInteractionsGetterAndSetter() {
        // Create a UserInteraction and add it to the interactions set
        UserInteraction interaction = new UserInteraction();
        interaction.setInteractionType(UserInteraction.InteractionType.IMPRESSION);
        interaction.setUser(user);

        user.getInteractions().add(interaction);

        // Verify that the interactions set contains the added interaction
        assertTrue(user.getInteractions().contains(interaction));
    }


    @Test
    public void testToString() {
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        String expectedToString = "User{name='John Doe', email='john.doe@example.com'}";
        assertEquals(expectedToString, user.toString());
    }
}
