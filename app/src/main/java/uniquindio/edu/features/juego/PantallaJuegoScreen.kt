package uniquindio.edu.features.juego


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uniquindio.edu.core.component.ElementoCarta
import uniquindio.edu.domain.model.EstadoJuegoEnum

@Composable
fun PantallaJuegoScreen(
    vistaModelo: PantallaJuegoViewModel,
    enJuegoGanado: () -> Unit
) {
    val estadoJuego = vistaModelo.estadoJuego.collectAsState().value

    // Navegar a pantalla de resultado cuando se gane
    if (estadoJuego.estado == EstadoJuegoEnum.GANADO) {
        enJuegoGanado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header con estadísticas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TarjetaEstadistica(
                etiqueta = "Parejas",
                valor = "${estadoJuego.parejasEncontradas}/8"
            )
            TarjetaEstadistica(
                etiqueta = "Movimientos",
                valor = estadoJuego.movimientos.toString()
            )
        }

        // Grid de cartas 4x4
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(estadoJuego.cartas) { carta ->
                ElementoCarta(
                    carta = carta,
                    alHacerClic = { vistaModelo.voltearCarta(carta.id) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaEstadistica(
    etiqueta: String,
    valor: String
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = etiqueta,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = valor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}