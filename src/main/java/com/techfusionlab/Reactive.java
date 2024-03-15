package com.techfusionlab;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Reactive {


    // 模拟从数据库中获取用户信息的异步操作
    public Mono<Boolean> hasPermission(int userId) {
        // 这里假设用户 ID 为偶数时具有特定权限
        System.out.println(String.valueOf(userId));
        return Mono.just(userId % 2 == 0);
    }

    // 模拟从外部 API 中获取商品信息的异步操作
    public Flux<String> getProducts() {
        // 这里假设从外部 API 中获取的商品信息为 A、B、C
        return Flux.just("A", "B", "C");
    }

    public static void main(String[] args) {
        Reactive reactiveChallenge = new Reactive();

        // 获取用户信息
        int userId = 2; // 假设用户 ID 为 2
        Mono<Boolean> hasPermissionMono = reactiveChallenge.hasPermission(userId);

        // 获取商品信息
        Flux<String> productsFlux = reactiveChallenge.getProducts();

        Boolean block = hasPermissionMono.block();
        System.out.println("block result: " + block.toString());

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
                .map(i -> {
                    if (i == 3) {
                        throw new RuntimeException("Error occurred!");
                    }
                    return i;
                });

        flux.subscribe(
                element -> System.out.println("Received: " + element),
                error -> System.err.println("Error: " + error.getMessage()),
                () -> System.out.println("Completed!")
        );
    }
}
