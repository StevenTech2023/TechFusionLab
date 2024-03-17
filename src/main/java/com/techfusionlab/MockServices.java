package com.techfusionlab;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MockServices {

    public static Flux<String> getUserPurchases(int userId) {
        // 模拟从购买记录服务获取用户的购买记录
        return Flux.just("Purchase 1", "Purchase 2", "Purchase 3");
    }

    public static Flux<String> getUserPayments(int userId) {
        // 模拟从支付信息服务获取用户的支付信息
        return Flux.just("Payment 1", "Payment 2", "Payment 3");
    }

    public static Flux<String> getOrderDetails(int orderId) {
        // 模拟从订单详情服务获取订单的详情
        return Flux.just("Order Detail 1", "Order Detail 2", "Order Detail 3");
    }

    // 模拟从数据库中获取用户信息的异步操作
    public static Mono<Boolean> hasPermission(int userId) {
        // 这里假设用户 ID 为偶数时具有特定权限
        System.out.println(String.valueOf(userId));
        return Mono.just(userId % 2 == 0);
    }

    // 模拟从外部 API 中获取商品信息的异步操作
    public static Flux<String> getProducts() {
        // 这里假设从外部 API 中获取的商品信息为 A、B、C
        return Flux.just("A", "B", "C");
    }
}
