package io.erode.orgHierarchy;

import java.util.LinkedList;
import java.util.List;

public class OrgImpl implements Org {

    private final int id;
    private final LinkedList<OrgImpl> children;

    private int users;
    private int userFiles;
    private long userBytes;


    public OrgImpl(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.children = new LinkedList<>();
    }

    /**
     * Add a user without the use of a POJO for memory concerns
     * @param files
     * @param bytes
     */
    public void addUser(final int files, final long bytes) {
        if (files < 0 || bytes < 0) {
            throw new IllegalArgumentException();
        }

        // Maintain a record of users/files/bytes without storing POJO
        this.userFiles += files;
        this.userBytes += bytes;
        this.users++;
    }

    public void addChildOrg(final OrgImpl org) {
        this.children.add(org);
    }

    private int getUsers() {
        return this.users;
    }

    private int getUserFiles() {
        return this.userFiles;
    }

    private long getUserBytes() {
        return this.userBytes;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getTotalNumUsers() {
        int total = this.getUsers();
        for (OrgImpl org : this.children) {
            total += org.getTotalNumUsers();
        }
        return total;
    }

    @Override
    public int getTotalNumFiles() {
        int total = this.getUserFiles();
        for (OrgImpl org : this.children) {
            total += org.getTotalNumFiles();
        }
        return total;
    }

    @Override
    public long getTotalNumBytes() {
        long total = this.getUserBytes();
        for (OrgImpl org : this.children) {
            total += org.getTotalNumBytes();
        }
        return total;
    }

    @Override
    public List<Org> getChildOrgs() {
        return (List) this.children;
    }
}
