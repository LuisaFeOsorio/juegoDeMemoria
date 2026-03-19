package uniquindio.edu.core.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaColoresClaro = lightColorScheme(
    primary = Colores.PrimaryPurple,
    secondary = Colores.SecondaryTeal,
    tertiary = Colores.TertiaryTeal,
    background = Colores.BackgroundWhite,
    surface = Colores.SurfaceWhite,
    error = Colores.ErrorRed
)

@Composable
fun TemaMuecaMemoria(contenido: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaColoresClaro,
        typography = TypografiaApp,
        content = contenido
    )
}