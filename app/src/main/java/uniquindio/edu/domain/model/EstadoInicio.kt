package uniquindio.edu.domain.model

data class EstadoInicio(
    val estaCargando: Boolean = false,
    val mejorPuntuacion: Int = 0,
    val cantidadJuegosJugados: Int = 0
)