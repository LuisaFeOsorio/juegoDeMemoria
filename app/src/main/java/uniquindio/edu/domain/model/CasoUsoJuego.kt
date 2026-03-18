package uniquindio.edu.domain.model


class CasoUsoJuego {

    fun crearCartasIniciales(): List<Carta> {
        val valoresCarta = (1..8).flatMap { listOf(it, it) }.shuffled()
        return valoresCarta.mapIndexed { indice, valor ->
            Carta(id = indice, valor = valor)
        }
    }

    fun sonCartasIguales(carta1: Carta, carta2: Carta): Boolean {
        return carta1.valor == carta2.valor
    }

    fun actualizarCartaVolteada(cartas: List<Carta>, idCarta: Int): List<Carta> {
        return cartas.mapIndexed { indice, carta ->
            if (indice == idCarta) carta.copy(estaVolteada = true) else carta
        }
    }

    fun actualizarCartasEnparejadas(cartas: List<Carta>, idsCartas: List<Int>): List<Carta> {
        return cartas.mapIndexed { indice, carta ->
            if (idsCartas.contains(indice)) {
                carta.copy(estaEnparejada = true, estaVolteada = true)
            } else {
                carta
            }
        }
    }

    fun ocultarCartasNoEnparejadas(cartas: List<Carta>, idsCartas: List<Int>): List<Carta> {
        return cartas.mapIndexed { indice, carta ->
            if (idsCartas.contains(indice)) {
                carta.copy(estaVolteada = false)
            } else {
                carta
            }
        }
    }
}