import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;

public class CallCentre {
    final int CALLS_NUMBER = 60;
    final int CALLS_FREQUENCY = 1000;
    final int WAITING_FOR_CALL = 5000;
    LinkedTransferQueue<String> clients = new LinkedTransferQueue<>();

    public static int getConsultationTime() {
        Random random = new Random();
        int min = 3000;
        int max = 5000;

        return random.nextInt((max - min) + 1) + min;
    }

    public void generateIncomingCalls() {
        try {
            for (int i = 1; i <= CALLS_NUMBER; i++) {
                Thread.sleep(CALLS_FREQUENCY);
                clients.add(Thread.currentThread().getName() + " " + i);
                System.out.printf("Входящий вызов от %s %d\n", Thread.currentThread().getName(), i);
            }
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

    public void answerTheCall() {
        try {
            while(!Thread.interrupted()) {
                if (clients.isEmpty()) {
                    Thread.sleep(WAITING_FOR_CALL);
                }

                String currentClient = clients.poll();
                System.out.printf("%s ответил %s\n", Thread.currentThread().getName(), currentClient);
                Thread.sleep(getConsultationTime());

                if (clients.isEmpty()) return;
            }
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }
}
