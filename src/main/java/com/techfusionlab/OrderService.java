package com.techfusionlab;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public class OrderService {
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
}
