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

    public static void permission() {
        // 获取用户信息
        int userId = 2; // 假设用户 ID 为 2
        Mono<Boolean> hasPermissionMono = MockServices.hasPermission(userId);

        // 获取商品信息
        Flux<String> productsFlux = MockServices.getProducts();

        Boolean block = hasPermissionMono.block();
        System.out.println("block result: " + block);

        // 根据用户权限决定是否获取商品信息
        hasPermissionMono
                .flatMapMany(hasPermission -> {
                    if (hasPermission) {
                        // 用户具有特定权限，获取商品信息
                        return productsFlux;
                    } else {
                        // 用户没有特定权限，返回空的 Flux
                        return Flux.empty();
                    }
                })
                .subscribe(
                        product -> System.out.println("Product: " + product),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Completed!")
                );

        // error操作如何处理
        Flux<Integer> flux = Flux.just(1, 2, 3, 4)
                .handle((i, sink) -> {
                    if (i == 3) {
                        sink.error(new RuntimeException("Error occurred!"));
                        return;
                    }
                    sink.next(i);
                });

        flux.subscribe(
                element -> System.out.println("Received: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Completed!")
        );
    }

    public static Mono<String> createOrder(int userId, int orderId) {
        Flux<String> userPurchases = MockServices.getUserPurchases(userId);
        Flux<String> userPayments = MockServices.getUserPayments(userId);
        Flux<String> orderDetails = MockServices.getOrderDetails(orderId);

        return Flux.zip(userPurchases.collectList(), userPayments.collectList(), orderDetails.collectList())
                .flatMap(tuple -> {
                    // 从元组中获取每个流的结果列表
                    List<String> purchases = tuple.getT1();
                    List<String> payments = tuple.getT2();
                    List<String> details = tuple.getT3();

                    // 根据获取的信息创建订单对象
//                    Order order = new Order(userId, orderId, purchases, payments, details);

                    // 返回一个 Mono 包含订单对象
                    return Mono.just("111");
                })
                // 由于我们只创建一个订单对象，因此我们可以将它转换为一个 Mono
                .single();
    }

    private static void java8() {
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

    public static void function(){
        // Function method
        Function<Integer, Integer> increment = x -> x + 1;
        Integer result = increment.apply(5);
        System.out.println(result.toString());
    }
}
