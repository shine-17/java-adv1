package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CollableMainV2 {

    static Thread main;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        main = Thread.currentThread();

        ExecutorService es = Executors.newFixedThreadPool(1);
        log("submit() 호출");
        Future<Integer> future = es.submit(new MyCallable());
        log("future 즉시 반환, future = " + future);

//        task();
        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 " + main.getState());
        Integer result = future.get();
        log("future.get() [블로킹] 메서드 호출 시작 -> main 스레드 " + main.getState());

        log("result value = " + result);
        log("future 완료, future = " + future);
        es.close();
    }

    static void task() {
        log("task 수행중");
        sleep(2000);
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log("Callable 시작");
            sleep(2000);
            log("main thread state : " + main.getState());
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("Callable 완료");
            return value;
        }
    }

}
