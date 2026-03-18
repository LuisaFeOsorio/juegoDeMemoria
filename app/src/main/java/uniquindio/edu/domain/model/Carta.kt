package uniquindio.edu.domain.model

data class Carta(
    val id: Int,
    val valor: Int,
    val estaVolteada: Boolean = false,
    val estaEnparejada: Boolean = false
)