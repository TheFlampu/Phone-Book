package phonebook;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        long startTime;
        long sortTime;
        long searchTime;
        long createTime;
        try {
            System.out.println("Start searching (linear search)");
            startTime = System.currentTimeMillis();
            String[] lines = Files.lines(Paths.get("C:\\Users\\Влад\\IdeaProjects\\Phone Book\\find.txt")).toArray(String[]::new);
            long linearSearchTime = System.currentTimeMillis() - startTime;
            System.out.println("Found " + lines.length + " / 500 entries. Time taken: 0 min. 0 sec. " + linearSearchTime + " ms." + "\n");

            System.out.println("Start searching (bubble sort + jump search)...");
            String[] bubbleSortLines = lines.clone();
            startTime = System.currentTimeMillis();
            bubbleSort(bubbleSortLines);
            sortTime = System.currentTimeMillis() - startTime;

            startTime = System.currentTimeMillis();
            jumpSearch(bubbleSortLines, " ");
            searchTime = System.currentTimeMillis() - startTime;

            System.out.println("Found " + bubbleSortLines.length + " / 500 entries. Time taken: 0 min. 0 sec. " + (sortTime + searchTime) + " ms.");
            System.out.println("Sorting time: 0 min. 0 sec. " + sortTime +" ms.");
            System.out.println("Searching time: 0 min. 0 sec. " + searchTime + " ms." + "\n");

            System.out.println("Start searching (quick sort + binary search)...");
            startTime = System.currentTimeMillis();
            String[] quickSortLines = lines.clone();
            quickSort(quickSortLines, 0, quickSortLines.length - 1);
            sortTime = System.currentTimeMillis() - startTime;

            startTime = System.currentTimeMillis();
            binarySearch(quickSortLines, " ", 0, quickSortLines.length - 1);
            searchTime = System.currentTimeMillis() - startTime;

            System.out.println("Found " + quickSortLines.length + " / 500 entries. Time taken: 0 min. 0 sec. " + (sortTime + searchTime) + " ms.");
            System.out.println("Sorting time: 0 min. 0 sec. " + sortTime +" ms.");
            System.out.println("Searching time: 0 min. 0 sec. " + searchTime + " ms." + "\n");

            System.out.println("Start searching (hash table)...");
            Hashtable<String, String> table = new Hashtable<>();
            startTime = System.currentTimeMillis();
            for (String str : lines) {
                table.put(str, "");
            }
            createTime = System.currentTimeMillis() - startTime;

            startTime = System.currentTimeMillis();
            table.get(" ");
            searchTime = System.currentTimeMillis() - startTime;

            System.out.println("Found " + table.size() + " / 500 entries. Time taken: 0 min. 0 sec. " + (createTime + searchTime) + " ms.");
            System.out.println("Creating time: 0 min. 0 sec. " + createTime +" ms.");
            System.out.println("Searching time: 0 min. 0 sec. " + searchTime + " ms.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bubbleSort(String[]  list) {
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = 0; j < list.length - i - 1; j++) {
                int compare = list[j].compareTo(list[j + 1]);
                if (compare > 0) {
                    String temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }

    public static int jumpSearch(String[] list, String element) {
        int blockSize = (int) Math.floor(Math.sqrt(list.length));

        int currentLastIndex = blockSize-1;

        while (currentLastIndex < list.length && element.compareTo(list[currentLastIndex]) > 0) {
            currentLastIndex += blockSize;
        }

        for (int currentSearchIndex = currentLastIndex - blockSize + 1; currentSearchIndex <= currentLastIndex && currentSearchIndex < list.length; currentSearchIndex++) {
            if (element.compareTo(list[currentSearchIndex]) == 0) {
                return currentSearchIndex;
            }
        }

        return -1;
    }

    public static void quickSort(String[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    private static int partition(String[] array, int left, int right) {
        String pivot = array[right];
        int partitionIndex = left;

        for (int i = left; i < right; i++) {
            int compare = pivot.compareTo(array[i]);
            if (compare > 0) {
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(array, partitionIndex, right);

        return partitionIndex;
    }

    static void swap(String[] arr, int i, int j)
    {
        String t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static int binarySearch(String[] array, String elem, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            int compare = elem.compareTo(array[mid]);
            if (compare == 0) {
                return mid;
            } else if (compare < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}

