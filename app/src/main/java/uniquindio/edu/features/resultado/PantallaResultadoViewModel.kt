package uniquindio.edu.features.resultado


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uniquindio.edu.data.repository.RepositorioResultado
import uniquindio.edu.data.repository.ResultadoJuego


data class EstadoResultado(
    val parejasEncontradas: Int = 0,
    val movimientos: Int = 0,
    val tiempoTranscurrido: Long = 0L,
    val esNuevoRecord: Boolean = false
)

class PantallaResultadoViewModel : ViewModel() {

    private val repositorio = RepositorioResultado.obtenerInstancia()

    private val _estadoResultado = MutableStateFlow(EstadoResultado())
    val estadoResultado: StateFlow<EstadoResultado> = _estadoResultado

    fun establecerResultado(parejasEncontradas: Int, movimientos: Int, tiempoTranscurrido: Long = 0L) {
        val esNuevoRecord = esNuevaRecordPuntuacion(movimientos)

        _estadoResultado.value = EstadoResultado(
            parejasEncontradas = parejasEncontradas,
            movimientos = movimientos,
            tiempoTranscurrido = tiempoTranscurrido,
            esNuevoRecord = esNuevoRecord
        )
    }

    fun guardarResultado() {
        val resultadoActual = _estadoResultado.value

        val resultadoJuego = ResultadoJuego(
            parejasEncontradas = resultadoActual.parejasEncontradas,
            movimientos = resultadoActual.movimientos,
            tiempoTranscurrido = resultadoActual.tiempoTranscurrido
        )

        repositorio.guardarResultado(resultadoJuego)
    }

    private fun esNuevaRecordPuntuacion(movimientos: Int): Boolean {
        val ultimoResultado = repositorio.obtenerUltimoResultado()
        return ultimoResultado == null || movimientos < ultimoResultado.movimientos
    }
}