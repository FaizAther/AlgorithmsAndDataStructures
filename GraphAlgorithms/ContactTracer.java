import java.util.*;

public class ContactTracer {

    private static class Person {
        private final String name;
        private final HashMap<Person, List<Integer>> contacts;

        private Person(String name) {
            this.name = name;
            this.contacts = new HashMap<>();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof Person)) return false;
            Person p = (Person)o;
            return this.name.equals(p.name);
        }

        private void addTime(Person contact, Integer time) {
            List<Integer> times = this.contacts
                    .computeIfAbsent(contact, k -> new LinkedList<>());
            times.add(time);
            Collections.sort(times);
        }

    }

    private final HashMap<String, Person> data;


    /**
     * Initialises an empty ContactTracer with no populated contact traces.
     */
    public ContactTracer() {
        this.data = new HashMap<>();
    }

    /**
     * Initialises the ContactTracer and populates the internal data structures
     * with the given list of contract traces.
     * 
     * @param traces to populate with
     * @require traces != null
     */
    public ContactTracer(List<Trace> traces) {
        this.data = new HashMap<>();
        for(Trace trace : traces) {
            addTrace(trace);
        }
    }

    /**
     * Adds a new contact trace to 
     * 
     * If a contact trace involving the same two people at the exact same time is
     * already stored, do nothing.
     * 
     * @param trace to add
     * @require trace != null
     */
    public void addTrace(Trace trace) {
        Person personA;
        Person personB;
        personA = data.get(trace.getPerson1());
        personB = data.get(trace.getPerson2());

        if (personA == null) {
            personA = new Person(trace.getPerson1());
            data.put(trace.getPerson1(), personA);
        }
        if (personB == null) {
            personB = new Person(trace.getPerson2());
            data.put(trace.getPerson2(), personB);
        }

        personA.addTime(personB, trace.getTime());
        personB.addTime(personA, trace.getTime());
    }

    /**
     * Gets a list of times that person1 and person2 have come into direct 
     * contact (as per the tracing data).
     *
     * If the two people haven't come into contact before, an empty list is returned.
     * 
     * Otherwise the list should be sorted in ascending order.
     * 
     * @param person1 
     * @param person2
     * @return a list of contact times, in ascending order.
     * @require person1 != null && person2 != null
     */
    public List<Integer> getContactTimes(String person1, String person2) {
        if (person1 == null || person2 == null)
            return null;

        Person personA = data.get(person1);
        Person personB = data.get(person2);

        if (personA == null || personB == null)
            return List.of();

        List<Integer> ret = personA.contacts.get(personB);
        return ret == null ? List.of():ret;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * over the entire history of the tracing dataset.
     * 
     * @param person to list direct contacts of
     * @return set of the person's direct contacts
     */
    public Set<String> getContacts(String person) {
        Person personA = data.get(person);
        if (personA == null)
            return Set.of();

        Set<String> contacts = new HashSet<>();
        for (Person contact : personA.contacts.keySet()) {
            contacts.add(contact.name);
        }
        
        return contacts;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * at OR after the given timestamp (i.e. inclusive).
     * 
     * @param person to list direct contacts of
     * @param timestamp to filter contacts being at or after
     * @return set of the person's direct contacts at or after the timestamp
     */
    public Set<String> getContactsAfter(String person, int timestamp) {
        Set<String> contacts = getContacts(person);

        List<Integer> times;
        Set<String> people = new HashSet<>();
        for (String contact : contacts) {
            times = getContactTimes(person, contact);
            boolean found = false;
            for (int i = 0; i < times.size() && !found; i++) {
                var time1 = times.get(i);
                var time2 = times.get(times.size()-1-i);
                if (time1 >= timestamp || time2 >= timestamp) {
                    people.add(contact);
                    found = true;
                }
            }
        }
        
        return people;
    }

    private int getContactTime(Person a, Person b, int timeOfContagion) {
        List<Integer> times = a.contacts.get(b);
        int timeOfContact = Integer.MAX_VALUE;
        boolean found = false;
        for (int i = 0; i < times.size() && !found; i++) {
            var time1 = times.get(i);
            var time2 = times.get(times.size()-1-i);
            if (time1 >= timeOfContagion) {
                timeOfContact = time1;
                found = true;
            } else if (time2 <= timeOfContagion) {
                timeOfContact = time2;
                found = true;
            }
        }
        return timeOfContact;
    }

    /**
     * Initiates a contact trace starting with the given person, who
     * became contagious at timeOfContagion.
     * 
     * Note that the return set shouldn't include the original person the trace started from.
     * 
     * @param person to start contact tracing from
     * @param timeOfContagion the exact time person became contagious
     * @return set of people who may have contracted the disease, originating from person
     */
    public Set<String> contactTrace(String person, int timeOfContagion) {
        Set<String> contacts = getContactsAfter(person, timeOfContagion);
        Set<String> ret = new HashSet<>();
        ret.add(person);
        for (String contact: contacts) {
            contactTrace_(contact,
                    getContactTime(data.get(person), data.get(contact), timeOfContagion), ret);
        }
        contacts.addAll(ret);
        contacts.remove(person);
        
        return contacts;
    }

    private void contactTrace_(String person, int timeOfContact, Set<String> people) {
        people.add(person);
        Set<String> contacts = getContactsAfter(person, timeOfContact+60);
        for (String contact: contacts) {
            if (!people.contains(contact))
                contactTrace_(contact,
                        getContactTime(data.get(person), data.get(contact), timeOfContact+60), people);
            people.add(contact);
        }
    }
}
