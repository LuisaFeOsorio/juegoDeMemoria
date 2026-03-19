package uniquindio.edu.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uniquindio.edu.domain.model.EstadoInicio

data class ResultadoJuego(
    val parejasEncontradas: Int,
    val movimientos: Int,
    val tiempoTranscurrido: Long,
    val fechaJuego: Long = System.currentTimeMillis()
)

class RepositorioResultado private constructor() {

    private val _resultados = MutableStateFlow<List<ResultadoJuego>>(emptyList())
    val resultados: StateFlow<List<ResultadoJuego>> = _resultados

    private var mejorPuntuacion = 0
    private var cantidadJuegosJugados = 0

    fun guardarResultado(resultado: ResultadoJuego) {
        val resultadosActuales = _resultados.value.toMutableList()
        resultadosActuales.add(resultado)
        _resultados.value = resultadosActuales

        actualizarEstadisticas(resultado)
    }

    private fun actualizarEstadisticas(resultado: ResultadoJuego) {
        cantidadJuegosJugados++

        if (mejorPuntuacion == 0 || resultado.movimientos < mejorPuntuacion) {
            mejorPuntuacion = resultado.movimientos
        }
    }

    fun obtenerEstadoInicio(): EstadoInicio {
        return EstadoInicio(
            estaCargando = false,
            mejorPuntuacion = mejorPuntuacion,
            cantidadJuegosJugados = cantidadJuegosJugados
        )
    }

    fun obtenerUltimoResultado(): ResultadoJuego? {
        return _resultados.value.lastOrNull()
    }


    companion object {
        private var instancia: RepositorioResultado? = null

        fun obtenerInstancia(): RepositorioResultado {
            return instancia ?: synchronized(this) {
                RepositorioResultado().also { instancia = it }
            }
        }
    }
}