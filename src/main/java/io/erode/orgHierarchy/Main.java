package io.erode.orgHierarchy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        File orgFile = null, userFile = null;
        if (args.length == 0) {
            System.out.println("Expected two arguments (org and user files). Substituting with a large data set.");
            orgFile = new File("./dist/org_data.large");
            userFile = new File("./dist/user_data.large");
        } else if (args.length == 2) {
            orgFile = new File(args[0]);
            userFile = new File(args[1]);
        }

        Stream<String> orgData;
        Stream<String> userData;

        try {
            orgData = new BufferedReader(new FileReader(orgFile)).lines();
            userData = new BufferedReader(new FileReader(userFile)).lines();

            OrgCollectionImpl orgs = new OrgCollectionImpl();

            loadOrgData(orgData, orgs);
            loadUserData(userData, orgs);
            printTree(orgs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadOrgData(final Stream<String> orgData, final OrgCollectionImpl orgs) {
        orgData.forEach(line -> {
            String[] attr = line.split(",");
            int id = Integer.parseInt(attr[0]);
            String parentOrgIdStr = attr[1].toLowerCase();
            Integer parentOrgId = !parentOrgIdStr.equals("null")
                    ? Integer.parseInt(parentOrgIdStr)
                    : null;

            OrgImpl org = new OrgImpl(id);
            orgs.addOrg(org, parentOrgId);
        });
    }

    private static void loadUserData(final Stream<String> userData, final OrgCollectionImpl orgs) {
        userData.forEach(line -> {

            String[] attr = line.split(",");
            // userId at index 0 is unnecessary because POJOs are not used
            int orgId = Integer.parseInt(attr[1]);
            int files = Integer.parseInt(attr[2]);
            long bytes = Long.parseLong(attr[3]);

            try {
                OrgImpl org = orgs.getOrg(orgId);
                org.addUser(files, bytes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static void printTree(final OrgCollection orgs) {
        for (Org org : orgs.getRoot()) {
            printOrg(org, 0);
        }
    }

    private static void printOrg(final Org org, final int depth) {
        // Get the depth and add a tab for every level
        System.out.print(String.join("", Collections.nCopies(depth, "\t")));
        System.out.println(String.format("%d, %d, %d, %d", org.getId(), org.getTotalNumUsers(), org.getTotalNumFiles(), org.getTotalNumBytes()));

        // Print children recursively
        for (Org child : org.getChildOrgs()) {
            printOrg(child, depth + 1);
        }
    }

}
