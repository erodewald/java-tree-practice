package io.erode.orgHierarchy;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.*;

public final class OrgCollectionImplTest {

    @Test
    public void createNewOrgCollection() {
        OrgCollectionImpl orgs = new OrgCollectionImpl();

        assertNotNull(orgs.getRoot());
        assertEquals(0, orgs.getRoot().size());
        assertNull(orgs.getOrg(1));

        List<Org> orgList = orgs.getOrgTree(1, true);
        assertNotNull(orgList);
        assertEquals(0, orgList.size());
    }

    @Test
    public void addOrgToCollection() {
        OrgCollectionImpl col = new OrgCollectionImpl();
        OrgImpl org = new OrgImpl(1);
        col.addOrg(org, null);
        assertEquals(col.getOrg(1), org);

        List<Org> roots = new LinkedList<>();
        roots.addAll(col.getRoot());

        assertEquals(roots.size(), 1);
        assertEquals(roots.get(0), org);

        List<Org> orgTree = col.getOrgTree(1, true);
        assertEquals(orgTree.size(), 1);
        assertEquals(orgTree.get(0), org);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
}
