package uniquindio.edu.features.juego


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uniquindio.edu.domain.model.CasoUsoJuego
import uniquindio.edu.domain.model.EstadoJuego
import uniquindio.edu.domain.model.EstadoJuegoEnum

class PantallaJuegoViewModel : ViewModel() {

    private val casoUsoJuego = CasoUsoJuego()

    private val _estadoJuego = MutableStateFlow(EstadoJuego())
    val estadoJuego: StateFlow<EstadoJuego> = _estadoJuego

    init {
        inicializarJuego()
    }

    fun inicializarJuego() {
        val cartas = casoUsoJuego.crearCartasIniciales()
        _estadoJuego.value = EstadoJuego(
            cartas = cartas,
            cartasVolteadas = emptyList(),
            parejasEncontradas = 0,
            movimientos = 0,
            estado = EstadoJuegoEnum.JUGANDO,
            puedeVoltearse = true,
            tiempoTranscurrido = 0L
        )
    }

    fun voltearCarta(idCarta: Int) {
        val estadoActual = _estadoJuego.value


        if (!estadoActual.puedeVoltearse) return
        if (estadoActual.cartasVolteadas.contains(idCarta)) return
        if (estadoActual.cartas[idCarta].estaEnparejada) return

        val cartasVoltadasActualizadas = estadoActual.cartasVolteadas + idCarta

        _estadoJuego.value = estadoActual.copy(
            cartasVolteadas = cartasVoltadasActualizadas,
            cartas = casoUsoJuego.actualizarCartaVolteada(estadoActual.cartas, idCarta)
        )

        // Si ya hay 2 cartas volteadas, validar
        if (cartasVoltadasActualizadas.size == 2) {
            verificarSiHayPareja(cartasVoltadasActualizadas)
        }
    }

    private fun verificarSiHayPareja(idsCartasVolteadas: List<Int>) {
        val estadoActual = _estadoJuego.value
        val carta1 = estadoActual.cartas[idsCartasVolteadas[0]]
        val carta2 = estadoActual.cartas[idsCartasVolteadas[1]]

        viewModelScope.launch {
            _estadoJuego.value = estadoActual.copy(puedeVoltearse = false)

            delay(500)

            if (casoUsoJuego.sonCartasIguales(carta1, carta2)) {
                // ¡Pareja encontrada!
                val cartasActualizadas = casoUsoJuego.actualizarCartasEnparejadas(
                    estadoActual.cartas,
                    idsCartasVolteadas
                )
                val nuevasParejasEncontradas = estadoActual.parejasEncontradas + 1
                val esJuegoGanado = nuevasParejasEncontradas == 8

                _estadoJuego.value = estadoActual.copy(
                    cartas = cartasActualizadas,
                    cartasVolteadas = emptyList(),
                    parejasEncontradas = nuevasParejasEncontradas,
                    movimientos = estadoActual.movimientos + 1,
                    puedeVoltearse = true,
                    estado = if (esJuegoGanado) EstadoJuegoEnum.GANADO else EstadoJuegoEnum.JUGANDO
                )
            } else {
                // Parejas no coinciden, volver a ocultar
                delay(500)

                _estadoJuego.value = estadoActual.copy(
                    cartas = casoUsoJuego.ocultarCartasNoEnparejadas(
                        estadoActual.cartas,
                        idsCartasVolteadas
                    ),
                    cartasVolteadas = emptyList(),
                    movimientos = estadoActual.movimientos + 1,
                    puedeVoltearse = true
                )
            }
        }
    }

    fun reiniciarJuego() {
        inicializarJuego()
    }
}