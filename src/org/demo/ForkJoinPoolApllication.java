package org.demo;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolApllication {

    static long numberOfOperation = 1000000;
    static int numOfTreads = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(numOfTreads);
        pool.invoke(new MyFork(0, numberOfOperation));
    }

    static class MyFork extends RecursiveTask<Long> {

        long from, to;

        public MyFork(long from, long to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            boolean b = true;
            ArrayList arrayList = new ArrayList();
            if ((to - from) <= numberOfOperation / numOfTreads) {
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

                return countNumber;

            } else {
                long middle = (to + from) / 2;
                MyFork firstHalf = new MyFork(from, middle);
                firstHalf.fork();
                MyFork secondHalf = new MyFork(middle + 1, to);
                long secondValue = secondHalf.compute();
                return firstHalf.join() + secondValue;
            }
        }
    }
}
