package uniquindio.edu.features.inicio.presentacion


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaInicioScreen(
    vistaModelo: PantallaInicioViewModel,
    enComenzarJuego: () -> Unit
) {
    val estadoInicio = vistaModelo.estadoInicio.collectAsState().value

    LaunchedEffect(Unit) {
        vistaModelo.cargarMejorPuntuacion()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = " JUEGO DE MEMORIA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Encuentra todas las parejas",
            fontSize = 18.sp
        )

        if (estadoInicio.mejorPuntuacion > 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Mejor puntuación: ${estadoInicio.mejorPuntuacion}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (estadoInicio.cantidadJuegosJugados > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Juegos jugados: ${estadoInicio.cantidadJuegosJugados}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = enComenzarJuego,
            modifier = Modifier
                .height(60.dp)
        ) {
            Text("Iniciar Juego", fontSize = 18.sp)
        }
    }
}