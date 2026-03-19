package uniquindio.edu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uniquindio.edu.core.navigation.Pantalla
import uniquindio.edu.core.theme.TemaMuecaMemoria
import uniquindio.edu.features.inicio.presentacion.PantallaInicioScreen
import uniquindio.edu.features.inicio.presentacion.PantallaInicioViewModel
import uniquindio.edu.features.juego.PantallaJuegoScreen
import uniquindio.edu.features.juego.PantallaJuegoViewModel
import uniquindio.edu.features.resultado.PantallaResultadoViewModel


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import uniquindio.edu.core.theme.TemaMuecaMemoria
import uniquindio.edu.features.inicio.presentacion.PantallaInicioScreen
import uniquindio.edu.features.resultado.PantallaResultadoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemaMuecaMemoria {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaApp()
                }
            }
        }
    }
}

@Composable
fun PantallaApp() {
    val pantallaActual = remember { mutableStateOf("inicio") }

    val vistaModeloInicio = PantallaInicioViewModel()
    val vistaModeloJuego = PantallaJuegoViewModel()
    val vistaModeloResultado = PantallaResultadoViewModel()

    when (pantallaActual.value) {
        "inicio" -> {
            PantallaInicioScreen(
                vistaModelo = vistaModeloInicio,
                enComenzarJuego = {
                    vistaModeloJuego.reiniciarJuego()
                    pantallaActual.value = "juego"
                }
            )
        }
        "juego" -> {
            PantallaJuegoScreen(
                vistaModelo = vistaModeloJuego,
                enJuegoGanado = {
                    pantallaActual.value = "resultado"
                }
            )
        }
        "resultado" -> {
            PantallaResultadoScreen(
                vistaModeloJuego = vistaModeloJuego,
                vistaModeloResultado = vistaModeloResultado,
                enJugarDeNuevo = {
                    pantallaActual.value = "inicio"
                }
            )
        }
    }
}