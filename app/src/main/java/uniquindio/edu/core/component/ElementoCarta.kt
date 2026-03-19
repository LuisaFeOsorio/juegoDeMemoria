package uniquindio.edu.core.component


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uniquindio.edu.domain.model.Carta

@Composable
fun ElementoCarta(
    carta: Carta,
    alHacerClic: () -> Unit,
    modificador: Modifier = Modifier
) {
    val rotacion by animateFloatAsState(
        targetValue = if (carta.estaVolteada || carta.estaEnparejada) 180f else 0f,
        label = "rotacionCarta"
    )

    Box(
        modifier = modificador
            .aspectRatio(1f)
            .graphicsLayer {
                rotationY = rotacion
                cameraDistance = 12 * density
            }
            .clickable(
                enabled = !carta.estaVolteada && !carta.estaEnparejada,
                onClick = alHacerClic
            )
    ) {
        if (rotacion <= 90f) {
            // LADO TRASERO
            Card(
                modifier = Modifier.fillMaxSize(), // Cambiado de matchParentSize
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                // Usamos un Box aquí para centrar el texto dentro de la Card
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "?",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            // LADO FRONTAL
            Card(
                modifier = Modifier
                    .fillMaxSize() // Cambiado de matchParentSize
                    .graphicsLayer {
                        rotationY = 180f // Invertimos para que el texto no se vea al revés
                    },
                colors = CardDefaults.cardColors(
                    containerColor = if (carta.estaEnparejada)
                        MaterialTheme.colorScheme.tertiary // Color diferente si ya se encontró
                    else
                        MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = carta.valor.toString(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}