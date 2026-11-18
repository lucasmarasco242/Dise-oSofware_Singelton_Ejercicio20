public class Main {
    public static void main(String[] args) throws InterruptedException {
        AdministradorTurnos recepcion = AdministradorTurnos.getInstancia();
        AdministradorTurnos autoservicio = AdministradorTurnos.getInstancia();

        System.out.println("¿Es la misma instancia entre Recepción y Autoservicio?: " + (recepcion == autoservicio));
        System.out.println("\nSimulando 20 hilos intentando reservar el mismo turno simultáneamente...\n");

        Thread[] hilos = new Thread[20];

        for (int i = 0; i < 20; i++) {
            final int numeroPaciente = i + 1;
            hilos[i] = new Thread(new Runnable() {
                public void run() {
                    AdministradorTurnos.getInstancia().reservar("Paciente" + numeroPaciente, "Dr. Gomez", "10/06/2026", "10:00");
                }
            });
        }

        // Iniciar todos los hilos concurrentes
        for (int i = 0; i < 20; i++) {
            hilos[i].start();
        }

        // Esperar a que terminen todos los hilos
        for (int i = 0; i < 20; i++) {
            hilos[i].join();
        }

        System.out.println("\n--- Consultando Agenda Tras Concurrencia ---");
        recepcion.consultarAgenda();

        System.out.println("\nIntentando cancelar un turno inexistente:");
        autoservicio.cancelar("Dr. Gomez", "10/06/2026", "15:00");

        System.out.println("\nCancelando el turno reservado para liberar el horario:");
        recepcion.cancelar("Dr. Gomez", "10/06/2026", "10:00");

        System.out.println("\n--- Consultando Agenda Tras Cancelación ---");
        autoservicio.consultarAgenda();
    }
}