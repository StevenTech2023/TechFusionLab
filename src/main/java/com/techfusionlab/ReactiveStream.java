package com.techfusionlab;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ReactiveStream {

    public static void main(String[] args) {


    }

    private void java8() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> collect = names.stream().filter(name -> name.length() > 3).map(n -> n.concat("111")).toList();
        System.out.println(collect.toString());

        Flux<String> publisher = Flux.just("Apple", "Banana", "Orange");
        publisher.subscribe(
                item -> System.out.println("Received: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Done"));

        Flux<String> transformedPublisher = publisher
                .map(String::toUpperCase)
                    .filter(word -> word.startsWith("A"));

        transformedPublisher.subscribe(
                item -> System.out.println("Transformed: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Done")
        );

        Flux<String> asyncPublisher = Flux.fromIterable(Arrays.asList("One", "Two", "Three"))
                .delayElements(Duration.ofMillis(500));

        asyncPublisher.subscribe(
                item -> System.out.println("Received asynchronously: " + item),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Done")
        );
    }

    public void function(){
        // Function method
        Function<Integer, Integer> increment = x -> x + 1;
        Integer result = increment.apply(5);
        System.out.println(result.toString());
    }
}
