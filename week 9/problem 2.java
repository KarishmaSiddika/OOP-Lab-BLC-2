import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class ParallelLetterFrequency {

    Map<Character, Integer> countLetters(List<String> texts) throws InterruptedException, ExecutionException {
        int threadCount = Math.max(1, Math.min(texts.size(), Runtime.getRuntime().availableProcessors()));
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            
            List<Future<Map<Character, Integer>>> futures = texts.stream()
                    .map(text -> executor.submit(() -> countLettersInText(text)))
                    .collect(Collectors.toList());

         
            Map<Character, Integer> result = new ConcurrentHashMap<>();
            for (Future<Map<Character, Integer>> future : futures) {
                Map<Character, Integer> partial = future.get();
                for (Map.Entry<Character, Integer> entry : partial.entrySet()) {
                    result.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            }
            return result;
        } finally {
            executor.shutdown();
        }
    }

    private Map<Character, Integer> countLettersInText(String text) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                counts.merge(lower, 1, Integer::sum);
            }
        }
        return counts;
    }
}
