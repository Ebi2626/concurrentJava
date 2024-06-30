class Producent extends Thread {
  private AbstractBuforInterface _bufor;
  private long _sleepTime;

  public Producent(AbstractBuforInterface bufor, long sleepTime) { // Wstrzyknięcie bufora
    _bufor = bufor;
    _sleepTime = sleepTime;
  }

  public void run() {
    for (int i = 0; i < 100; ++i) {
      try { // Imitacja realizacji jakiejś czynności
        Thread.sleep(_sleepTime);
      } catch (Exception e) {
      }
      _bufor.put(i); // Dodanie do bufora wartości
    }
  }
}
