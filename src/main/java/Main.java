public class Main {
    public static void main(String[] args) throws InterruptedException {
        CallCentre cc = new CallCentre();

        Thread atc = new Thread(null, cc::generateIncomingCalls, "Клиент");
        ThreadGroup consultants = new ThreadGroup("Консультанты");
        Thread consultant1 = new Thread(consultants, cc::answerTheCall, "Консультант 1");
        Thread consultant2 = new Thread(consultants, cc::answerTheCall, "Консультант 2");
        Thread consultant3 = new Thread(consultants, cc::answerTheCall, "Консультант 3");
        Thread consultant4 = new Thread(consultants, cc::answerTheCall, "Консультант 4");

        atc.start();
        consultant1.start();
        consultant2.start();
        consultant3.start();
        consultant4.start();

        while (Thread.currentThread().isAlive()) {
            atc.join();
            if (consultants.activeCount() == 0) {
                System.out.println("Фух, всех обслужили");
                return;
            }
        }
    }
}
