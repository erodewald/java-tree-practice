package io.erode.orgHierarchy;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.*;

public final class OrgImplTest {

    @Test
    public void addUserWithNegativeFiles_IllegalArgumentException() {
        OrgImpl o = new OrgImpl(1);
        exception.expect(IllegalArgumentException.class);
        o.addUser(-1, 0);
    }

    @Test
    public void createWithIdZero_IllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        new OrgImpl(0);
    }

    @Test
    public void makeOrgWithNegativeId_IllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        new OrgImpl(-1);
    }

    @Test
    public void addUserWithNegativeBytes_IllegalArgumentException() {
        OrgImpl o = new OrgImpl(1);
        exception.expect(IllegalArgumentException.class);
        o.addUser(1, -1);
    }

    @Test
    public void addUser_ProducesCorrectValues() {
        OrgImpl o = new OrgImpl(1);
        assertEquals(o.getTotalNumUsers(), 0);
        assertEquals(o.getTotalNumFiles(), 0);
        assertEquals(o.getTotalNumBytes(), 0);

        // add 4 users
        o.addUser(1, 1);
        o.addUser(10, 10 ^ 2);
        o.addUser(20, 10 ^ 3);
        o.addUser(30, 10 ^ 4);

        // Ensure values align
        assertEquals(o.getTotalNumUsers(), 4);
        assertEquals(o.getTotalNumFiles(), 1 + 10 + 20 + 30);
        assertEquals(o.getTotalNumBytes(), 1 + (10 ^ 2) + (10 ^ 3) + (10 ^ 4));
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();
}
