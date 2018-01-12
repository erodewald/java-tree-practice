# OrgHierarchy

*Java Tree practice kata*

The task is to create a tool, using java, that reads an organizational hierarchy into memory and produce stats on
demand. Code should be ready to turn over to someone else for maintenance.

### Provided requirements
* **Read an org hierarchy data file**

    * format: `orgId, parentOrgId, name`
    * e.g.:
        ```
         1,null,Foo
         2,1,Bar
         4,3,Baz
         5,2,Qux
         3,null,Xyzzy
        ```
* **Read a user data file**
   
    * format: `userId, orgId, numFiles, numBytes`
    * e.g.:
        ```
        1,1,10,200
        ```
* **After reading the data files log the organizational tree to a third file:**
    * plaintext output
    * Tree order is assumed, parent and then its children, then the next parent
    * One line per org:
        * `orgId, totalNumUsers, totalNumFiles, totalNumBytes` // stats for an org are itslf + all of its children
        * Indent child orgs to indicate the tree structure 
        
* **Public API**

    The public API should at least contain the following methods.
     ```
    - OrgCollection
      - Org getOrg(orgId)
      // return list of orgs below this node in the tree;
      // inclusive = include orgId within list
      - List<Org> getOrgTree(orgId, boolean inclusive)
    - Org
      - int getTotalNumUsers() // return total number of users in this org and any children
      - int getTotalNumFiles() // return total number of files in this org and any children
      - int getTotalNumBytes() // return total number of bytes in this org and any children
      - List<Org> getChildOrgs() // return the immediate children of the org
    ```
    
### Non-functional requirements
    * No dependencies other than the jvm (a test framework such as JUnit or TestNG is acceptable)
    * Provide source code - please do not send .jar or .class files, they will be rejected by our email system
    * Implement a few executable tests.. an exhaustive test suite is not needed
        * Provide your test input file(s)
    * Provide directions:
        * How to compile and run your tests
        * How to create/execute a new test of your code with new data files
    * Document your thought process
        * Did you make any assumptions? what were they?
        * Why did you do use the algorithms you did? what alternatives were there?
        * What if there were 500 million rows in the data file, would you make any changes? Where would
    your code break?
    
 ## Rationale, thoughts, assumptions
 
 * I am assuming that the data example provided exhibits all possible variants - for example, there will never be an org ID with 0. With this assumption, I wrote some tests to ensure the appropriate exceptions are thrown when the data is "incorrect". 
 * I chose to use `long` over `int` for `getTotalNumBytes()` due to the likelihood of exceeding the  maximum value of Java's `int` data type (`2 147 483 647`). `long` is capable of `9 223 372 036 854 775 807`.
 * Making this tool operate only in a command-line for the sake of simplicity. Of course, it would be simple enough to incorporate this functionality into a web app or some kind of GUI desktop application, but the requirements didn't ask for it. 
 * With my C# background, proper Java naming conventions were a little foggy to me. I made the decision to go with `Org` as the interface and `OrgImpl` as the implementation.
 * In `OrgCollectionImpl`, I opted to utilize the `HashMap` data structure due to the lower time complexity, O(1), or O(n) at worst.
 * Although there are more elegant parsing solutions, I opted to simply read a string and split it like a comma-separated file structure since the requirements only allude to that being the format. 
 * If there were millions of rows, memory would become a major bottleneck but I do believe that performance would be reasonably good. Some kind of persistence system would be required to overcome the memory concerns when working with significant rows. 