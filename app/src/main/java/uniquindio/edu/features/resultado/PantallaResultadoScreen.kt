package uniquindio.edu.features.resultado


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
import uniquindio.edu.features.juego.PantallaJuegoViewModel

@Composable
fun PantallaResultadoScreen(
    vistaModeloJuego: PantallaJuegoViewModel,
    vistaModeloResultado: PantallaResultadoViewModel,
    enJugarDeNuevo: () -> Unit
) {
    val estadoJuego = vistaModeloJuego.estadoJuego.collectAsState().value
    val estadoResultado = vistaModeloResultado.estadoResultado.collectAsState().value

    LaunchedEffect(Unit) {
        vistaModeloResultado.establecerResultado(
            parejasEncontradas = estadoJuego.parejasEncontradas,
            movimientos = estadoJuego.movimientos,
            tiempoTranscurrido = estadoJuego.tiempoTranscurrido
        )
        vistaModeloResultado.guardarResultado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉 ¡GANASTE!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Parejas encontradas: ${estadoResultado.parejasEncontradas}",
            fontSize = 20.sp
        )

        Text(
            text = "Movimientos realizados: ${estadoResultado.movimientos}",
            fontSize = 20.sp
        )

        if (estadoResultado.esNuevoRecord) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "🏆 ¡Nuevo récord!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                vistaModeloJuego.reiniciarJuego()
                enJugarDeNuevo()
            },
            modifier = Modifier
                .height(60.dp)
        ) {
            Text("Jugar de Nuevo", fontSize = 18.sp)
        }
    }
}