package com.labs.thread.base.future.completable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author panlf
 * @date 2021/8/18
 */
public class CompletableFutureTest {


    public static void main(String[] args) {
        CompletableFutureTest test = new CompletableFutureTest();
        test.test12();
    }

    //创建一个完成的CompletableFuture
    public void test01()  {
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Message");
        //getNow如果结果已经计算完则返回结果或者抛出异常，否则返回给定的valueIfAbsent值。
        //如果任务完成则获得返回值，如果调用时未完成则返回设置的默认值
        System.out.println(completableFuture.getNow("Hello"));
    }


    //异步执行任务
    public void test02()  {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
            System.out.println("我开始处理了");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("是否完成了===>"+completableFuture.isDone());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("是否完成了===>"+completableFuture.isDone());
    }

    //应用函数
    public void test03()  {
        CompletableFuture<String> completableFuture = CompletableFuture
                .completedFuture("message")
                //thenApplyAsync 异步执行
                .thenApply(String::toUpperCase);
        System.out.println(completableFuture.getNow(null));

    }

    public void test04()  {
        CompletableFuture<String> completableFuture = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(String::toUpperCase);
        completableFuture.join();
        System.out.println(completableFuture.getNow(null));

    }

    //消费前一阶段的结果
    public void test05()  {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(result::append);
        System.out.println(result.toString());

    }

    //异步消费
    public void test06()  {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture("thenAccept message")
                .thenAcceptAsync(result::append);
        completableFuture.join();
        System.out.println(result.toString());
    }


    //取消计算
    public void test07(){
        CompletableFuture<String> cf = CompletableFuture
                .completedFuture("message")
                .thenApplyAsync(String::toUpperCase);
        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        System.out.println(cf.cancel(true));
        System.out.println(cf.isCompletedExceptionally());
        //System.out.println(cf.join());
        System.out.println(cf2.join());
    }

    public void test08(){
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(String::toUpperCase);
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(String::toLowerCase),
                s -> s + " from applyToEither");
        System.out.println(cf2.join());
    }


    //组合
    public void test09(){
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> s.toUpperCase())
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> s.toLowerCase())
                        .thenApply(s -> upper + s));
        System.out.println(cf.join());
    }

    public void test10(){
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures =  messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(String::toUpperCase))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).whenComplete((v, th) -> {
            futures.forEach(cf -> System.out.println(cf.getNow(null)));
            result.append("done");
        });
        System.out.println(result.toString());
    }

    public void test11(){
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(String::toUpperCase))
                .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> System.out.println(cf.getNow(null)));
                    result.append("done");
                });
        allOf.join();
        System.out.println(result.toString());
    }

    /**
     * 串行方法
     */
    public void test12(){
        System.out.println("主线程start...");
        CompletableFuture.runAsync(()->{
            System.out.println("任务1 --- "+Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRunAsync(()->{
            System.out.println("任务2 --- "+Thread.currentThread().getName());
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程end...");
    }
}
