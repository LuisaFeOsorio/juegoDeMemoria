package uniquindio.edu.features.inicio.presentacion


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uniquindio.edu.data.repository.RepositorioResultado
import uniquindio.edu.domain.model.EstadoInicio

class PantallaInicioViewModel(
    private val repositorio: RepositorioResultado = RepositorioResultado()
) : ViewModel() {

    private val _estadoInicio = MutableStateFlow(EstadoInicio())
    val estadoInicio: StateFlow<EstadoInicio> = _estadoInicio

    fun cargarMejorPuntuacion() {
        val estadoDelRepositorio = repositorio.obtenerEstadoInicio()
        _estadoInicio.value = estadoDelRepositorio
    }

    override fun onCleared() {
        super.onCleared()
    }
}