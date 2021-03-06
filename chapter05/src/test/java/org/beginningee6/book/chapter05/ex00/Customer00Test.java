package org.beginningee6.book.chapter05.ex00;

import org.beginningee6.book.chapter05.AbstractPersistentTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 6 with Glassfish
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
public class Customer00Test extends AbstractPersistentTest {

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldShowDifferentLifeCyclePhases() throws Exception {

        // Instanciates an object
        Customer00 customer = new Customer00("John", "Smith", "jsmith@gmail.com", "1234565");
        assertFalse(em.contains(customer));

        // Persists the object
        System.out.println("\nPERSIST");
        tx.begin();
        em.persist(customer);
        tx.commit();
        assertTrue("should be in the persistence context after persisting", em.contains(customer));

        // Finds the object
        System.out.println("\nFIND");
        em.clear();
        assertFalse("should not be in the persistence context after clearing", em.contains(customer));
        customer = em.find(Customer00.class, customer.getId());
        assertTrue("should be in the persistence context after finding", em.contains(customer));

        // Detaches the object
        System.out.println("\nDETACH");
        em.detach(customer);
        assertFalse("should not be in the persistence context after detaching", em.contains(customer));
        customer = em.find(Customer00.class, customer.getId());
        assertTrue("should be in the persistence context after finding", em.contains(customer));

        // Refreshes the object
        System.out.println("\nREFRESH");
        customer.setEmail("newone@mail.com"); // With no update, only the PostLoad is called
        tx.begin();
        em.refresh(customer);
        tx.commit();
        assertTrue("should be in the persistence context after refreshing", em.contains(customer));

        System.out.println("\nSET");
        tx.begin();
        customer.setFirstName("new first name");
        customer.setFirstName("new last name");
        tx.commit();

        // Merges the object
        System.out.println("\nMERGE");
        em.clear();
        assertFalse("should not be in the persistence context after clearing", em.contains(customer));
        customer.setEmail("newone@mail.com"); // With no update, only the PostLoad is called
        tx.begin();
        customer = em.merge(customer);
        tx.commit();
        assertTrue("should be in the persistence context after merging", em.contains(customer));

        // Removes the entity
        System.out.println("\nREMOVE");
        tx.begin();
        em.remove(customer);
        tx.commit();
        assertFalse("should not be in the persistence context after removing", em.contains(customer));
    }
}