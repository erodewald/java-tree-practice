package io.erode.orgHierarchy;

import java.util.LinkedList;
import java.util.List;

public interface OrgCollection {

    /**
     * List of root orgs, not on the original requirements
     *
     * @return LinkedList of orgs
     */
    LinkedList<OrgImpl> getRoot();

    /**
     * Get an org by its identifier
     *
     * @param orgId the identifier
     * @return the org
     */
    Org getOrg(int orgId);

    /**
     * Get the list (tree) of orgs at the specified org nod
     *
     * @param orgId     the specified org
     * @param inclusive include the specified org or only return its children
     * @return the list of orgs
     */
    List<Org> getOrgTree(int orgId, boolean inclusive);
}
