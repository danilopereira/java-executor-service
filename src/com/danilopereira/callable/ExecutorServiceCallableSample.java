package com.danilopereira.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceCallableSample {

    public void submit() throws ExecutionException, InterruptedException {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        try{
            final Future<String> task1 = fixedThreadPool.submit(doSomethingExpensiveInCallable(Thread.currentThread().getName()));
            System.out.println(task1.get());

            final Future<String> task2 = fixedThreadPool.submit(doSomethingExpensiveInCallable(Thread.currentThread().getName()));
            System.out.println(task2.get());
            while(!task1.isDone() && !task2.isDone()){
                Thread.sleep(1000);
            }
        }finally {
            fixedThreadPool.shutdown();
            fixedThreadPool.shutdownNow();
        }
    }

    private Callable<String> doSomethingExpensiveInCallable(String threadName) {
        Callable<String> callableTask = () ->{
            TimeUnit.MILLISECONDS.sleep(3000);
            return String.format("Callable Task in thread %s", threadName);
        };

        return callableTask;
    }
}
