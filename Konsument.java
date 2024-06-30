class Konsument extends Thread {
  private AbstractBuforInterface _bufor;
  private long _sleepTime;

  public Konsument(AbstractBuforInterface bufor, long sleepTime) { // Wstrzyknięcie bufora
    _bufor = bufor;
    _sleepTime = sleepTime;
  }

  public void run() {
    for (int i = 0; i < 100; ++i) {
      try { // Imitacja realizacji czynności
        Thread.sleep(_sleepTime);
      } catch (Exception e) {
      }
      _bufor.get(); // Pobranie z bufora wartości
    }
  }
}
