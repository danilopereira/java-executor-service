import com.danilopereira.callable.ExecutorServiceCallableSample;
import com.danilopereira.runnable.ExecutorServiceRunnableSample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorServiceRunnableSample executorServiceRunnableSample = new ExecutorServiceRunnableSample();
        executorServiceRunnableSample.execute();
        executorServiceRunnableSample.submite();

        ExecutorServiceCallableSample executorServiceCallableSample = new ExecutorServiceCallableSample();
        executorServiceCallableSample.submit();


    }


}
