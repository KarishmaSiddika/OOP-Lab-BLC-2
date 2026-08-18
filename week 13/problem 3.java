import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelLetterFrequency {

    public static Map<Character, Integer> calculateFrequencies(List<String> texts) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(texts.size());
        List<Future<Map<Character, Integer>>> futures = new ArrayList<>();

        for (String text : texts) {
            futures.add(executor.submit(() -> countLetters(text)));
        }

        Map<Character, Integer> combined = new ConcurrentHashMap<>();

        for (Future<Map<Character, Integer>> future : futures) {
            try {
                Map<Character, Integer> result = future.get();
                result.forEach((ch, count) ->
                    combined.merge(ch, count, Integer::sum)
                );
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return combined;
    }

    private static Map<Character, Integer> countLetters(String text) {
        return text.toLowerCase()
                   .chars()
                   .filter(Character::isLetter)
                   .mapToObj(c -> (char) c)
                   .collect(Collectors.toMap(
                       c -> c,
                       c -> 1,
                       Integer::sum
                   ));
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> texts = Arrays.asList(
            "Hello World",
            "Parallel Processing",
            "Letter Frequency"
        );

        Map<Character, Integer> frequencies = calculateFrequencies(texts);
        System.out.println("Letter Frequencies: " + frequencies);
    }
}
