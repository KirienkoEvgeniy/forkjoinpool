package org.demo;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorApllication {

    public static void main(String[] args) {

        long numberOfOperation = 100000;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 1L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(512));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.submit(new Runnable() {
            @Override
            public void run() {
                boolean b = true;
                ArrayList arrayList = new ArrayList();
                for (long i = 2; i <= numberOfOperation; i++) {
                    for (long j = 2; j < i; j++) {
                        if (i % j == 0) {
                            b = false;
                            break;
                        }
                    }
                    if (b) {
                        arrayList.add(i);
                    } else b = true;
                }
                long countNumber = (long) arrayList.size();
                System.out.println(countNumber);
            }
        });

        executor.shutdown();
    }
}
