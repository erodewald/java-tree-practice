package io.erode.orgHierarchy;

import java.util.List;

public interface Org {

    /**
     * Unique identifier not on the original requirements
     *
     * @return the identifier
     */
    int getId();

    /**
     * Total users for everything beneath the org node
     *
     * @return the total number of users within the node
     */
    int getTotalNumUsers();

    /**
     * Total files for everything beneath the org node
     *
     * @return the total number of files
     */
    int getTotalNumFiles();

    /**
     * Total number of bytes in all files beneath the org node
     *
     * @return the total number of bytes
     */
    long getTotalNumBytes();

    /**
     * All child orgs
     *
     * @return the child orgs in a list
     */
    List<Org> getChildOrgs();
}
