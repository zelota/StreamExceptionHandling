import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import lombok.extern.java.Log;

/**
 * Stream exception handling.
 *
 * <p>Source article:
 * Brian Vermeer - Exception Handling in Java Streams
 * https://dzone.com/articles/exception-handling-in-java-streams?edition=433197&utm_source=Weekly%20Digest&utm_medium=email&utm_campaign=Weekly%20Digest%202019-01-02
 */
@Log
public class MainApp {

    // main stream for examples
    private static List<String> items;

    // init
    static {
        items = new ArrayList<String>();
        items.add("One");
        items.add("Two");
        items.add("Three");
        items.add("Ahoj");
        items.add("Gee");
        items.add(null);
        items.add("Nothing");
        items.add("Else");
    }

    /**
     * Entry point.
     *
     * @param args No need parameters.
     */
    public static void main( String[] args ) {
        example1();
        example2();
        //example3();  // comment - otherwise the program ends.
        example4();
    }

    /**
     * Error handling 1.
     * Very simple way.... or "Standard" way... or "Please don't do that" way.
     * IMHO: avoid as much as possible.
     */
    private static void example1() {
        log.info("-- begin example1");
        items.stream().map(item -> {
            try {
                return example1Do(item);
            } catch (Exception ex) {
                log.severe("example1 exception: ");
                ex.printStackTrace();
            }
            return "";
        }).forEach(System.out::println);
        log.info("-- end example1");
    }

    private static String example1Do( String item ) {
        return item.toUpperCase();
    }

    /**
     * Error handling 2.
     * Very simple way.... or "Standard" way... or "Please don't do that" way, but
     * it's a little bit better and more readable.
     */
    private static void example2() {
        log.info("-- begin example2");
        items.stream().map(item -> {
            return example2Try(item);
        }).forEach(System.out::println);
        log.info("-- end example2");
    }

    private static String example2Try( String item ) {
        try {
            return example1Do(item);
        } catch (Exception ex) {
            log.severe("example2 exception: ");
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Error handling 3.
     * Functional interface.
     * The only problem left is that when an exception occurs, the processing of the your stream stops immediately.
     */
    private static void example3() {
        log.info("-- begin example3");
        items.stream().map(example3Wrapper(item -> example3Try(item)))
                .forEach(System.out::println);
        log.info("-- end example3");
    }

    private static String example3Try( String item ) {
        return example1Do(item);
    }

    public static <T, R> Function<T, R> example3Wrapper( CheckedFunction<T, R> checkedFunction ) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Error handling 4.
     * Either type.
     * When working with streams, we probably don't want to stop processing the stream if an exception occurs.
     */
    private static void example4() {
        log.info("-- begin example4");
        items.stream()
                .map(Either.lift(item -> example4Try(item)))
                .forEach(System.out::println);
        log.info("-- end example4");
    }

    private static String example4Try( String item ) {
        return example1Do(item);
    }

}
