Ejercicio 20 - Administrador Concurrente de Turnos de Consultorio

Explicación del programa

En este ejercicio se creó una clase llamada AdministradorTurnos que aplica el patrón Singleton para centralizar y proteger la agenda de turnos del consultorio frente a solicitudes simultáneas desde recepción y tótems de autoservicio, sin utilizar librerías externas ni imports.
La clase cuenta con:
- Una variable estática privada llamada instancia.
- Arreglos privados para almacenar los datos de los turnos (profesionales, fechas, horarios, pacientes y estado de ocupación).
- Un constructor privado y un método estático sincronizado (`getInstancia()`) que garantizan una creación segura en entornos multihilo.
- Métodos públicos sincronizados (`reservar`, `cancelar`, `consultarAgenda`) que aseguran la atomicidad en las operaciones para evitar condiciones de carrera y asignaciones duplicadas.

Luego, dentro del método main, se simulan múltiples componentes obteniendo referencias al Singleton:
AdministradorTurnos recepcion = AdministradorTurnos.getInstancia();
AdministradorTurnos autoservicio = AdministradorTurnos.getInstancia();

Finalmente, se utilizó System.out.println() para comprobar que ambas referencias comparten la misma instancia, simular veinte hilos intentando reservar exactamente el mismo horario de manera concurrente (garantizando que solo uno resulte exitoso), y probar la cancelación segura sin alterar la agenda ante datos inexistentes.

Lógica utilizada

La lógica del programa consiste en:
- Sincronizar tanto el método de acceso al Singleton como los métodos operacionales mediante la palabra clave `synchronized` para lograr exclusión mutua.
- Ejecutar la comprobación de disponibilidad y la asignación del turno como una única operación atómica, impidiendo que dos hilos ocupen el mismo espacio horario.
- Validar rigurosamente las cancelaciones para que los intentos sobre turnos inexistentes no alteren el estado de la agenda.

Resultado de la ejecución

La ejecución del programa muestra en la consola lo siguiente:
¿Es la misma instancia entre Recepción y Autoservicio?: true
Simulando 20 hilos intentando reservar el mismo turno simultáneamente...

¡Éxito! Turno reservado para Paciente5 con Dr. Gomez (10/06/2026 a las 10:00)
Rechazado: El turno con Dr. Gomez el 10/06/2026 a las 10:00 ya está ocupado (Solicitado por Paciente1)
Rechazado: El turno con Dr. Gomez el 10/06/2026 a las 10:00 ya está ocupado (Solicitado por Paciente2)
...
Rechazado: El turno con Dr. Gomez el 10/06/2026 a las 10:00 ya está ocupado (Solicitado por Paciente20)

--- Consultando Agenda Tras Concurrencia ---
--- Agenda Actualizada de Turnos ---
1. Dr. Gomez | Fecha: 10/06/2026 | Hora: 10:00 | Estado: Ocupado (Paciente: Paciente5)
2. Dr. Gomez | Fecha: 10/06/2026 | Hora: 10:30 | Estado: Disponible
3. Dra. Perez | Fecha: 10/06/2026 | Hora: 11:00 | Estado: Disponible

Intentando cancelar un turno inexistente:
Error: El turno especificado no existe en la agenda. No se modificó la agenda.

Cancelando el turno reservado para liberar el horario:
Cancelación exitosa: Turno de Paciente5 con Dr. Gomez (10/06/2026 10:00) liberado.

--- Consultando Agenda Tras Cancelación ---
--- Agenda Actualizada de Turnos ---
1. Dr. Gomez | Fecha: 10/06/2026 | Hora: 10:00 | Estado: Disponible
2. Dr. Gomez | Fecha: 10/06/2026 | Hora: 10:30 | Estado: Disponible
3. Dra. Perez | Fecha: 10/06/2026 | Hora: 11:00 | Estado: Disponible

Captura de pantalla
<img width="1014" height="735" alt="image" src="https://github.com/user-attachments/assets/c0bf4620-b465-423c-b8af-b397628cb4db" />
<img width="833" height="568" alt="image" src="https://github.com/user-attachments/assets/a1ecfb72-6684-4606-a790-feb616404a64" />

