public class AdministradorTurnos {
    private static AdministradorTurnos instancia;
    private String[] profesionales;
    private String[] fechas;
    private String[] horarios;
    private String[] pacientes;
    private boolean[] ocupados;
    private int totalSlots;

    private AdministradorTurnos() {
        this.profesionales = new String[5];
        this.fechas = new String[5];
        this.horarios = new String[5];
        this.pacientes = new String[5];
        this.ocupados = new boolean[5];
        this.totalSlots = 0;

        // Carga inicial de la agenda del consultorio
        agregarSlot("Dr. Gomez", "10/06/2026", "10:00");
        agregarSlot("Dr. Gomez", "10/06/2026", "10:30");
        agregarSlot("Dra. Perez", "10/06/2026", "11:00");
    }

    private void agregarSlot(String profesional, String fecha, String horario) {
        if (totalSlots < 5) {
            profesionales[totalSlots] = profesional;
            fechas[totalSlots] = fecha;
            horarios[totalSlots] = horario;
            pacientes[totalSlots] = null;
            ocupados[totalSlots] = false;
            totalSlots++;
        }
    }

    // Creación segura de la instancia Singleton ante concurrencia
    public static synchronized AdministradorTurnos getInstancia() {
        if (instancia == null) {
            instancia = new AdministradorTurnos();
        }
        return instancia;
    }

    // Operación atómica y segura ante concurrencia para reservar turnos
    public synchronized boolean reservar(String paciente, String profesional, String fecha, String horario) {
        if (paciente == null || paciente.isEmpty() || profesional == null || fecha == null || horario == null) {
            return false;
        }

        for (int i = 0; i < totalSlots; i++) {
            if (profesionales[i].equals(profesional) && fechas[i].equals(fecha) && horarios[i].equals(horario)) {
                if (!ocupados[i]) {
                    ocupados[i] = true;
                    pacientes[i] = paciente;
                    System.out.println("¡Éxito! Turno reservado para " + paciente + " con " + profesional + " (" + fecha + " a las " + horario + ")");
                    return true;
                } else {
                    System.out.println("Rechazado: El turno con " + profesional + " el " + fecha + " a las " + horario + " ya está ocupado (Solicitado por " + paciente + ")");
                    return false;
                }
            }
        }
        System.out.println("Rechazado: Horario no encontrado en la agenda.");
        return false;
    }

    // Cancelar turnos de forma segura y controlada
    public synchronized void cancelar(String profesional, String fecha, String horario) {
        boolean encontrado = false;
        for (int i = 0; i < totalSlots; i++) {
            if (profesionales[i].equals(profesional) && fechas[i].equals(fecha) && horarios[i].equals(horario)) {
                encontrado = true;
                if (ocupados[i]) {
                    System.out.println("Cancelación exitosa: Turno de " + pacientes[i] + " con " + profesional + " (" + fecha + " " + horario + ") liberado.");
                    pacientes[i] = null;
                    ocupados[i] = false;
                    return;
                } else {
                    System.out.println("Error: El turno con " + profesional + " el " + fecha + " a las " + horario + " no tiene una reserva activa. No se modificó la agenda.");
                    return;
                }
            }
        }
        if (!encontrado) {
            System.out.println("Error: El turno especificado no existe en la agenda. No se modificó la agenda.");
        }
    }

    // Consultar el estado actual de la agenda
    public synchronized void consultarAgenda() {
        System.out.println("--- Agenda Actualizada de Turnos ---");
        for (int i = 0; i < totalSlots; i++) {
            String estado = ocupados[i] ? "Ocupado (Paciente: " + pacientes[i] + ")" : "Disponible";
            System.out.println((i + 1) + ". " + profesionales[i] + " | Fecha: " + fechas[i] + " | Hora: " + horarios[i] + " | Estado: " + estado);
        }
    }
}