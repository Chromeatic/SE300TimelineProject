package timeline.project;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Timeline
{
    private String filePath; // Add filePath field
    private ArrayList<Event> events = new ArrayList<Event>();

    public Timeline(Timeline other) {
        this.events = new ArrayList<>();
        for (Event e : other.getEvents()) {
            this.events.add(new Event(e.getName(), e.getTimeString(), e.getDescription()));
        }
    }

    public Timeline(String csvFilePath) throws IOException {
        this.filePath = csvFilePath; // Store the file path
        events = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
        if (lines.size() < 3) {
            throw new IllegalArgumentException("CSV file must contain at least 3 lines");
        }
        String[] names = lines.get(0).split(",");
        String[] times = lines.get(1).split(",");
        String[] descriptions = lines.get(2).split(",");
        int len = Math.min(names.length, Math.min(times.length, descriptions.length));
        for (int i = 0; i < len; i++) {
            events.add(new Event(names[i].trim(), times[i].trim(), descriptions[i].trim()));
        }
    }

    // Add getter for filePath
    public String getFilePath() {
        return filePath;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void displayTimeline()
    {
        for (int i = 0; i < events.size(); i++)
        {
            System.out.println(events.get(i).getName() + " " + events.get(i).getTimeString() + " " + events.get(i).getDescription());
        }
    }

    public ArrayList<Event> shuffleList(ArrayList<Event> sortedEvents)
    {
        ArrayList<Event> shuffledEvents = new ArrayList<Event>();
        int randomIndex = getRandomListIndex(sortedEvents.size());
        ArrayList<Integer> markedIndices = new ArrayList<>();

        while(markedIndices.size() < sortedEvents.size())
        {
            Event eventToAdd = sortedEvents.get(randomIndex);
            shuffledEvents.add(eventToAdd);
            markedIndices.add(randomIndex);

            boolean uniqueIndexFound;
            do
            {
                uniqueIndexFound = true;
                randomIndex = getRandomListIndex(sortedEvents.size());

                int i = 0;
                
                while(i < markedIndices.size())
                {
                    // return when list is finished; otherwise loop will be stuck trying to find an index that does not exist
                    if(markedIndices.size() == sortedEvents.size()) return shuffledEvents;

                    // go through marked indices and compare
                    if(randomIndex == markedIndices.get(i))
                    {
                        uniqueIndexFound = false; // if at least one match, index is not unique
                    }
                    i++;

                } 

            } while(!uniqueIndexFound);
        
        }
        return shuffledEvents;
    }

    public int getRandomListIndex(int listSize)
    {
        return (int) (Math.random() * (listSize));
    }

    public void swap(ArrayList<Event> events, int index1, int index2)
    {
        Event temp = events.get(index1);
        events.set(index1, events.get(index2));
        events.set(index2, temp);
    }

    public void sortTimeline(ArrayList<Event> events, Scanner scanner)
    {
        int index1 = 0;
        int index2 = -1;
        if(events.size() <= 1)
        {
            System.out.println("INVALID EVENTS LIST SIZE; events list must have more than one event...");
            return;
        }
        while (!(index1 >= 0 && index1 < index2 && index2 < events.size()) && events.size() != 1)
        {
            String prompt = "Enter first index to swap: Enter a number between 0 and " + (events.size()-1);

            System.out.println(prompt);
            index1 = Integer.parseInt(scanner.nextLine());
            System.out.println(prompt);
            index2 = Integer.parseInt(scanner.nextLine());
            if(!(index1 >= 0 && index1 < index2 && index2 < events.size()))
            {
                System.out.println("Invalid input. Try again");
            }
        } 
        swap(events, index1, index2);
    }

    public Event getEventByName(ArrayList<Event> events, String name)
    {
        int i = 0;
        while(i < events.size())
        {
            if(events.get(i).getName().equals(name))
            {
                return events.get(i);
            }
            i++;
        }
        return null;
    }

    public int getIndexByName(ArrayList<Event> events, String name)
    {
        int i = 0;
        while(i < events.size())
        {
            if(events.get(i).getName().equals(name))
            {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public boolean[] getHints(ArrayList<Event> shuffledEvents, ArrayList<Event> sortedEvents)
    {
        boolean[] hints = new boolean[shuffledEvents.size() - 1];
        // Create a map for quick lookup of sorted event index by name
        Map<String, Integer> sortedEventIndexMap = new HashMap<>();
        for (int i = 0; i < sortedEvents.size(); i++) {
            sortedEventIndexMap.put(sortedEvents.get(i).getName(), i);
        }

        int i = 0;
        while(i < hints.length)
        {
            Event currentShuffledEvent = shuffledEvents.get(i);
            Event nextShuffledEvent = shuffledEvents.get(i+1);

            // Find the index of the current event in the *sorted* list
            Integer currentSortedIndex = sortedEventIndexMap.get(currentShuffledEvent.getName());

            boolean isCorrect = false;
            // Check if the current event is not the last in the sorted list
            if (currentSortedIndex != null && currentSortedIndex < sortedEvents.size() - 1) {
                // Get the event that *should* come next according to the sorted list
                Event expectedNextSortedEvent = sortedEvents.get(currentSortedIndex + 1);
                // Compare the name of the *actual* next event in the shuffled list
                // with the name of the *expected* next event from the sorted list.
                isCorrect = nextShuffledEvent.getName().equals(expectedNextSortedEvent.getName());
            }
            // If currentSortedIndex is null or it's the last event, the next one can't be correct in sequence.

            hints[i] = isCorrect;

            // Optional: Keep debug prints if needed, otherwise remove
            // System.out.print(currentShuffledEvent.getName());
            // System.out.print(" -> ");
            // System.out.print(nextShuffledEvent.getName());
            // System.out.print(" : ");
            // System.out.print(isCorrect);
            // System.out.println();

            i++;
        }

        // Remove the old System.out.println calls inside the loop if they exist
        // System.out.print(isCorrect);
        // System.out.println();

        return hints;
    }

}