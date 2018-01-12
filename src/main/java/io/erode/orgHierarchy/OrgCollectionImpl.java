package io.erode.orgHierarchy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrgCollectionImpl implements OrgCollection {

    private final HashMap<Integer, OrgImpl> orgs;
    private final LinkedList<OrgImpl> root;
    private Map<Integer, List<OrgImpl>> queue;

    public OrgCollectionImpl() {
        this.orgs = new HashMap<>();
        this.root = new LinkedList<>();
    }

    public void addOrg(final OrgImpl org, final Integer parentOrgId) {
        this.orgs.put(org.getId(), org);

        // Does it belong to any child orgs?
        List<OrgImpl> outstanding = null;

        if (this.queue != null) {
            outstanding = this.queue.get(org.getId());
        }

        // If there are any outstanding in the queue, process, remove from queue
        if (outstanding != null) {
            for (OrgImpl child : outstanding) {
                org.addChildOrg(child);
            }
            this.queue.remove(org.getId());
        }

        OrgImpl parent = null;
        if (parentOrgId != null) {
            parent = this.orgs.get(parentOrgId);
        }

        if (parent != null) {
            parent.addChildOrg(org);
        } else if (parentOrgId != null) {
            // Add to queue for processing
            this.addToQueue(org, parentOrgId);
        } else {
            // No parent, it is root org
            this.root.add(org);
        }
    }

    private void addToQueue(final OrgImpl org, final Integer parentOrgId) {
        if (this.queue == null) {
            this.queue = new HashMap<>();
        }
        List<OrgImpl> list = this.queue.computeIfAbsent(parentOrgId, k -> new LinkedList<>());
        list.add(org);
    }

    private void addChildren(final Org org, final List<Org> output) {
        List<Org> children = org.getChildOrgs();
        for (Org c : children) {
            output.add(c);
            this.addChildren(c, output);
        }
    }

    @Override
    public LinkedList<OrgImpl> getRoot() {
        return this.root;
    }

    @Override
    public OrgImpl getOrg(int orgId) {
        return this.orgs.get(orgId);
    }

    @Override
    public List<Org> getOrgTree(int orgId, boolean inclusive) {
        List<Org> output = new LinkedList<>();
        OrgImpl org = this.orgs.get(orgId);

        // return early, adding a null child would be bad
        if (org == null) {
            return output;
        }

        if (inclusive) {
            output.add(org);
        }
        this.addChildren(org, output);
        return output;
    }
}
