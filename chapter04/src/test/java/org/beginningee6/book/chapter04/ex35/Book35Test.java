package org.beginningee6.book.chapter04.ex35;

import org.beginningee6.book.chapter04.AbstractPersistentTest;
import org.junit.Test;

import javax.persistence.LockModeType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 6 with Glassfish
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
public class Book35Test extends AbstractPersistentTest {

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void shouldCreateABook() throws Exception {

        // Creates a book, version number should be 1
        Book35 book = new Book35("The Hitchhiker's Guide to the Galaxy", 12.5F, "The Hitchhiker's Guide to the Galaxy is a science fiction comedy series created by Douglas Adams.", "1-84023-742-2", 354, false);
        tx.begin();
        em.persist(book);
        tx.commit();
        assertNotNull("ID should not be null", book.getId());
        assertEquals("Version number should be 1", new Integer(1), book.getVersion());

        // Updates the same book
        tx.begin();
        book = em.find(Book35.class, book.getId());
        book.setDescription("new description");
        tx.commit();
        assertEquals("Version number should be 2", new Integer(2), book.getVersion());
    }

    @Test
    public void shouldCreateABookAndRaisePrice() throws Exception {

        // Creates a book, version number should be 1
        Book35 book = new Book35("The Hitchhiker's Guide to the Galaxy", 12.5F, "The Hitchhiker's Guide to the Galaxy is a science fiction comedy series created by Douglas Adams.", "1-84023-742-2", 354, false);
        tx.begin();
        em.persist(book);
        tx.commit();
        assertNotNull("ID should not be null", book.getId());
        assertEquals("Version number should be 1", new Integer(1), book.getVersion());

        // Updates the same book
        tx.begin();
        book.raisePriceByTwoDollars();
        tx.commit();
        assertEquals("Version number should be 2", new Integer(2), book.getVersion());
    }

    @Test
    public void shouldReadThenLock() throws Exception {

        // Creates a book, version number should be 1
        Book35 book = new Book35("The Hitchhiker's Guide to the Galaxy", 12.5F, "The Hitchhiker's Guide to the Galaxy is a science fiction comedy series created by Douglas Adams.", "1-84023-742-2", 354, false);
        tx.begin();
        em.persist(book);
        tx.commit();
        assertNotNull("ID should not be null", book.getId());
        assertEquals("Version number should be 1", new Integer(1), book.getVersion());

        // Reads then locks
        tx.begin();
        book = em.find(Book35.class, book.getId());
        em.lock(book, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        book.raisePriceByTwoDollars();
        tx.commit();
        assertEquals("Version number should be 2", new Integer(2), book.getVersion());
        assertEquals("Price shouls have been raise by 2 dollars", new Float(14.5), book.getPrice());
    }

    @Test
    public void shouldReadAndLock() throws Exception {

        // Creates a book, version number should be 1
        Book35 book = new Book35("The Hitchhiker's Guide to the Galaxy", 12.5F, "The Hitchhiker's Guide to the Galaxy is a science fiction comedy series created by Douglas Adams.", "1-84023-742-2", 354, false);
        tx.begin();
        em.persist(book);
        tx.commit();
        assertNotNull("ID should not be null", book.getId());
        assertEquals("Version number should be 1", new Integer(1), book.getVersion());

        // Reads then locks
        tx.begin();
        book = em.find(Book35.class, book.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        book.raisePriceByTwoDollars();
        tx.commit();
        assertEquals("Version number should be 2", new Integer(2), book.getVersion());
        assertEquals("Price shouls have been raise by 2 dollars", new Float(14.5), book.getPrice());
    }
}