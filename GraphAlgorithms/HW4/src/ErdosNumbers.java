import java.util.*;

public class ErdosNumbers {
    /**
     * String representing Paul Erdos's name to check against
     */
    public static final String ERDOS = "Paul Erdös";

    private static final class Person {
        private final String person_name;
        private final HashSet<String> papers = new HashSet<>();
        private final HashSet<String> co_author_str = new HashSet<>();
        private final List<Person> co_authorship = new LinkedList<>();

        public Person(String person_name) {
            this.person_name = person_name;
        }

    }

    private final List<Person> people = new LinkedList<>();
    private final Map<String, Person> people_track = new HashMap<>();

    /**
     * Initialises the class with a list of papers and authors.
     *
     * Each element in 'papers' corresponds to a String of the form:
     * 
     * [paper name]:[author1][|author2[|...]]]
     *
     * Note that for this constructor and the below methods, authors and papers
     * are unique (i.e. there can't be multiple authors or papers with the exact same name or title).
     * 
     * @param papers List of papers and their authors
     */
    public ErdosNumbers(List<String> papers) {
        Iterator<String> it = papers.iterator();
        String paper, paper_name;
        String[] authors;
        Person node;

        while(it.hasNext()) {
            paper = it.next();
            String[] split = paper.split(":");
            paper_name = split[0];
            authors = split[1].split("\\|");

            for (String author : authors) {
                node = people_track.get(author);
                if (node == null) {
                    node = new Person(author);
                    people_track.put(author, node);
                    people.add(node);
                }
                node.papers.add(paper_name);
            }
            for (String author : authors) {
                node = people_track.get(author);
                for (String co_author : authors) {
                    if (!author.equals(co_author) && !node.co_author_str.contains(co_author)) {
                        node.co_authorship.add(people_track.get(co_author));
                        node.co_author_str.add(co_author);
                    }
                }
            }
        }
    }
    
    /**
     * Gets all the unique papers the author has written (either solely or
     * as a co-author).
     * 
     * @param author to get the papers for.
     * @return the unique set of papers this author has written.
     */
    public Set<String> getPapers(String author) {
        Person result = people_track.get(author);
        if (result != null) {
            return result.papers;
        }
        
        return Set.of();
    }

    /**
     * Gets all the unique co-authors the author has written a paper with.
     *
     * @param author to get collaborators for
     * @return the unique co-authors the author has written with.
     */
    public Set<String> getCollaborators(String author) {
        Person person = people_track.get(author);
        Person co_auth;
        Iterator<Person> it = person.co_authorship.iterator();
        Set<String> co_authors = new HashSet<>();
        while(it.hasNext()) {
            co_auth = it.next();
            co_authors.add((co_auth.person_name));
        }
        return co_authors;
    }

    /**
     * Checks if Erdos is connected to all other author's given as input to
     * the class constructor.
     * 
     * In other words, does every author in the dataset have an Erdos number?
     * 
     * @return the connectivity of Erdos to all other authors.
     */
    public boolean isErdosConnectedToAll() {
        for (Person author : people) {
            if (!isErdosConnectedToAll_(author, new HashSet<>()))
                return false;
        }
        
        return true;
    }

    private boolean isErdosConnectedToAll_(Person depth, Set<String> checked) {
        boolean res;
        if (depth == null) {
            return false;
        }
        if (checked.contains(depth.person_name)) {
            return false;
        }
        checked.add(depth.person_name);
        if (depth.co_author_str.contains(ERDOS) || depth.person_name.equals(ERDOS)) {
            return true;
        }
        for (Person co_auth : depth.co_authorship) {
            res = isErdosConnectedToAll_(co_auth, checked);
            if (!res) {
                continue;
            }
            return true;
        }

        return false;
    }

    /**
     * Calculate the Erdos number of an author. 
     * 
     * This is defined as the length of the shortest path on a graph of paper 
     * collaborations (as explained in the assignment specification).
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * a defined Erdos number), returns Integer.MAX_VALUE.
     * 
     * Note: Erdos himself has an Erdos number of 0.
     * 
     * @param author to calculate the Erdos number of
     * @return authors' Erdos number or otherwise Integer.MAX_VALUE
     */
    public int calculateErdosNumber(String author) {
        if(author.equals(ERDOS)) {
            return 0;
        }
        int[] res = calculateErdosNumber_(people_track.get(author), new HashSet<>(), 1);
        return res[1]==1?res[0]:Integer.MAX_VALUE;
    }

    private int[] calculateErdosNumber_(Person depth, HashSet<String> checked, int erdos) {
        int[] res = {erdos,0};
        if (checked.contains(depth.person_name)) {
            return res;
        }
        checked.add(depth.person_name);
        if (depth.person_name.equals(ERDOS)||depth.co_author_str.contains(ERDOS)) {
            res[1] = 1;
            return res;
        }
        int[] span = {-1,-1};
        for (Person co_auth : depth.co_authorship) {
            if (!checked.contains(co_auth.person_name)) {
                span = calculateErdosNumber_(co_auth, (HashSet<String>) checked.clone(), erdos+1);
            }
            checked.add(co_auth.person_name);
            if(span[1] == 1) {
                if (res[1] == 0 || res[0] > span[0] && span[0] > 0) {
                    res = span;
                }
            }
        }

        return res;
    }

    /**
     * Gets the average Erdos number of all the authors on a paper.
     * If a paper has just a single author, this is just the author's Erdos number.
     *
     * Note: Erdos himself has an Erdos number of 0.
     *
     * @param paper to calculate it for
     * @return average Erdos number of paper's authors
     */
    public double averageErdosNumber(String paper) {
        int sum = 0;
        int count = 0;
        for (Person author: people) {
            if (author.papers.contains(paper)) {
                sum += calculateErdosNumber(author.person_name);
                count+=1;
            }
        }
        
        return (double)sum/count;
    }

    /**
     * Calculates the "weighted Erdos number" of an author.
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * an Erdos number), returns Double.MAX_VALUE.
     *
     * Note: Erdos himself has a weighted Erdos number of 0.
     * 
     * @param author to calculate it for
     * @return author's weighted Erdos number
     */
    public double calculateWeightedErdosNumber(String author) {
        if(author.equals(ERDOS)) {
            return 0;
        }
        double[] res = calculateWeightedErdosNumber_(people_track.get(author),
                new HashSet<>(), 1, 0.0);
        return res[1]==1?res[2]:Double.MAX_VALUE;
    }

    private double[] calculateWeightedErdosNumber_(Person depth, HashSet<String> checked, int erdos, double weight) {
        double[] res = {erdos, 0, weight};
        if (checked.contains(depth.person_name)) {
            res[1] = 0;
            return res;
        }
        checked.add(depth.person_name);
        if (depth.person_name.equals(ERDOS) || depth.co_author_str.contains(ERDOS)) {
            res[1] = 1;
            res[2] = weight + (1 / count_papers(depth.person_name,ERDOS));
            return res;
        }
        double[] span = {-1, -1,-1};
        for (Person co_auth : depth.co_authorship) {
            if (!checked.contains(co_auth.person_name)) {
                span = calculateWeightedErdosNumber_(co_auth,
                        (HashSet<String>) checked.clone(),
                        erdos + 1,
                        weight + ( 1 / count_papers(depth.person_name,co_auth.person_name)));
            }
            checked.add(co_auth.person_name);
            if (span[1] == 1) {
                if (res[1] == 0 || res[0] > span[0] && span[0] > 0 && span[2] < res[2]) {
                    res = span;
                }
            }
        }

        return res;
    }

    private double count_papers(String a1, String a2) {
        HashSet<String> papers1 = (HashSet<String>) people_track.get(a1).papers.clone();
        HashSet<String> papers2 = (HashSet<String>) people_track.get(a2).papers.clone();
        papers1.retainAll(papers2);
        return papers1.size();
    }

}
