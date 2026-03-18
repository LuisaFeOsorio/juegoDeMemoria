package uniquindio.edu.domain.model


enum class EstadoJuegoEnum {
    JUGANDO,
    GANADO,
    INACTIVO
}

data class EstadoJuego(
    val cartas: List<Carta> = emptyList(),
    val cartasVolteadas: List<Int> = emptyList(),
    val parejasEncontradas: Int = 0,
    val movimientos: Int = 0,
    val estado: EstadoJuegoEnum = EstadoJuegoEnum.INACTIVO,
    val puedeVoltearse: Boolean = true,
    val tiempoTranscurrido: Long = 0L
)