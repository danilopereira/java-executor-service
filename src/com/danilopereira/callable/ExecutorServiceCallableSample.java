package com.danilopereira.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorServiceCallableSample {

    public void submit() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(2);
            Future<Double> task1 = executorService.submit(new Callable<Double>() {

                @Override
                public Double call() throws Exception {
                    System.out.println(String.format("starting expensive task thread %s",
                            Thread.currentThread().getName()));
                    Double returnedValue = someExpensiveRemoteCall();
                    System.out.println(String.format("finished expensive task thread %s",
                            Thread.currentThread().getName()));

                    return returnedValue;
                }
            });

            Future<Double> task2 = executorService.submit(() -> {
                System.out.println(String.format("starting expensive task thread %s",
                        Thread.currentThread().getName()));
                Double returnedValue = someExpensiveRemoteCall();
                System.out.println(String.format("finished expensive task thread %s",
                        Thread.currentThread().getName()));

                return returnedValue;
            });

            Double valueFromTask1 = task1.get(4, TimeUnit.SECONDS);
            Double valueFromTask2 = task2.get(4, TimeUnit.SECONDS);

            System.out.println(String.format("TaskFuture1 returned value %s and " +
                    "TaskFuture2 returned value %s", valueFromTask1, valueFromTask2));

        } finally {
            executorService.shutdown();
            executorService.shutdownNow();
        }
    }

    private Double someExpensiveRemoteCall() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 100;
    }
}
