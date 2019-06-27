package com.danilopereira.runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceRunnableSample {

    public void execute() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        try{
            cachedThreadPool.execute(()->{
                System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
                doSomethingExpensive();
                System.out.println(String.format("finished expensive task thread %s", Thread.currentThread().getName()));
            });

            cachedThreadPool.execute(()->{
                System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
                doSomethingExpensive();
                System.out.println(String.format("finished expensive task thread %s", Thread.currentThread().getName()));
            });
        }finally{
            cachedThreadPool.shutdown();
            cachedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void submite() throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        try{
            Future<?> task1 = fixedThreadPool.submit(() -> {
                System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
                doSomethingExpensive();
                System.out.println(String.format("finished expensive task thread %s", Thread.currentThread().getName()));
            });

            Future<?> task2 = fixedThreadPool.submit(() -> {
                System.out.println(String.format("starting expensive task thread %s", Thread.currentThread().getName()));
                doSomethingExpensive();
                System.out.println(String.format("finished expensive task thread %s", Thread.currentThread().getName()));
            });

            while(!task1.isDone() && !task2.isDone()){
                System.out.println("Task 1 and Task 2 are not yet complete....sleeping");
                Thread.sleep(1000);

            }
        }finally{
            fixedThreadPool.shutdown();
            fixedThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        }

    }

    private static void doSomethingExpensive() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
