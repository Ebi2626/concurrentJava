// PKmon.java
// Edwin Harmata, 44764, 2IS131-SEP
public class PKmon {
	public static void main(String[] args) {
		long sleepTime = (long) (Math.random() * 100);

		try {
			if (args.length == 1) {
				sleepTime = Long.parseLong(args[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		long startTime = System.nanoTime();
		System.out.println("\nProgram starts at: " + (System.nanoTime()) + "ns from epoch time");

		Bufor bufor = new Bufor(5); // tworzymy bufor z wartością inicjalną, przedstawiająca liczbę tablicy bufora
		Producent producent = new Producent(bufor, sleepTime); // tworzymy producenta i podajemy mu nasz bufor
		Konsument konstument = new Konsument(bufor, sleepTime); // tworzymy konsumenta i podajem mu nasz bufor

		// startujemy wątek producenta i konsumenta
		producent.start();
		konstument.start();

		// Czekamy na koniec pracy wątków do pobrania czasu końca programu:
		try {
			producent.join();
			konstument.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Mierzymy czas końca pracy programu
		System.out.println("\nProgram works: " + (System.nanoTime() - startTime) + "ns -> "
				+ (double) (System.nanoTime() - startTime) / 1_000_000_000 + "s");
	}
}