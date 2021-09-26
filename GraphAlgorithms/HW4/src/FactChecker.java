import java.util.*;

public class FactChecker {

    private static final class Person {

        private final String name;
        private final HashMap<String, Person> together = new HashMap<>();
        private final HashMap<String, Person> before = new HashMap<>(); //left b4 so on the left
        private final HashMap<String, Person> after = new HashMap<>(); //came after so on the right

        private Person(String name) {
            this.name = name;
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof Person)) return false;
            Person p = (Person)o;
            return this.name.equals(p.name);
        }

    }

    private static HashMap<String, Person> friends;

    private static boolean any(Person p1, Person p2, String what, HashSet<Person> checked) {

        if (p1 == null || p2 == null) {
            return false;
        }

        if (p1.equals(p2)) {
            return true;
        }

        ArrayList<Iterator<Person>> itA = new ArrayList<>();
        itA.add(p1.together.values().iterator());

        switch (what) {
            case "Before":
                if (p1.before.get(p2.name) != null) {
                    return true;
                }
                itA.add(p1.before.values().iterator());
                break;
            case "After":
                if (p1.after.get(p2.name) != null) {
                    return true;
                }
                itA.add(p1.after.values().iterator());
                break;
            case "Together":
                if (p1.together.get(p2.name) != null) {
                    return true;
                }
                break;
            default:
                return false;
        }

        checked.add(p1);
        Person p3;
        for (Iterator<Person> it : itA) {
            while (it.hasNext()) {
                p3 = it.next();
                if (p3.equals(p2)) {
                    return true;
                }
                if (!checked.contains(p3) && any(p3, p2, what, checked)) {
                    return true;
                }
                checked.add(p3);
            }
        }

        return false;
    }

    /**
     * Checks if a list of facts is internally consistent. 
     * That is, can they all hold true at the same time?
     * Or are two (or potentially more) facts logically incompatible?
     * 
     * @param facts list of facts to check consistency of
     * @return true if all the facts are internally consistent, otherwise false.
     */
    public static boolean areFactsConsistent(List<Fact> facts) {
        friends = new HashMap<>();
        Person personA;
        Person personB;
        for (Fact fact: facts) {
            personA = friends.get(fact.getPersonA());
            personB = friends.get(fact.getPersonB());

            boolean[] res = {false,false,false};
            if (personA != null && personB != null) {
                if (fact.getType().equals(Fact.FactType.TYPE_ONE)) { // type 1 connection
                    // p2 cant be together or b4 p1
                    // p1 cant be together or after p2
                    res[0] = any(personA, personB, "Together", new HashSet<>());
                    res[1] = any(personA, personB, "Before", new HashSet<>());
                    res[2] = any(personA, personB, "After", new HashSet<>());
                    if (res[0] || res[1] || res[2]) {
                        return false;
                    }
                } else { // type 2 connection
                    res[1] = any(personA, personB, "Before", new HashSet<>());
                    res[2] = any(personA, personB, "After", new HashSet<>());
                    if (res[1] || res[2]) {
                        return false;
                    }
                }
            }

            if (personA == null) {
                personA = new Person(fact.getPersonA());
                friends.put(fact.getPersonA(), personA);
            }
            if (personB == null) {
                personB = new Person(fact.getPersonB());
                friends.put(fact.getPersonB(), personB);
            }

            if (fact.getType().equals(Fact.FactType.TYPE_ONE)) {
                personB.before.put(fact.getPersonA(), personA);
                personA.after.put(fact.getPersonB(), personB);
            } else {
                personB.together.put(fact.getPersonA(), personA);
                personA.together.put(fact.getPersonB(), personB);
            }
        }

        return true;
    }

}
