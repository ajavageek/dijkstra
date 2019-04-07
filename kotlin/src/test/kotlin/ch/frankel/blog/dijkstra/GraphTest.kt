package ch.frankel.blog.dijkstra

import org.testng.annotations.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class GraphTest {

    @Test
    fun `should return 20 for simple graph on Wikipedia`() {
        // https://fr.wikipedia.org/wiki/Probl%C3%A8me_de_plus_court_chemin
        val nodes = mapOf<String, Map<String, Int>>(
            "A" to mapOf("B" to 4, "C" to 2),
            "B" to mapOf("C" to 5, "D" to 10),
            "C" to mapOf("E" to 3),
            "D" to mapOf("F" to 11),
            "E" to mapOf("D" to 4)
        )
        val graph = Graph(nodes)
        expect {
            that(graph.findShortestPath("A", "F")).isEqualTo(20)
        }
    }

    @Test
    fun `should return 487 for directed graph on Wikipedia`() {
        // https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
        val nodes = mapOf<String, Map<String, Int>>(
            "A" to mapOf("B" to 85, "C" to 217, "E" to 173),
            "B" to mapOf("A" to 85, "F" to 80),
            "C" to mapOf("A" to 217, "G" to 186, "H" to 103),
            "D" to mapOf("H" to 183),
            "E" to mapOf("A" to 173, "J" to 502),
            "F" to mapOf("B" to 80, "I" to 250),
            "G" to mapOf("C" to 186),
            "H" to mapOf("C" to 103, "D" to 183, "J" to 167),
            "I" to mapOf("F" to 250, "J" to 84),
            "J" to mapOf("I" to 84, "H" to 167, "E" to 502)
        )
        val graph = Graph(nodes)
        expect {
            that(graph.findShortestPath("A", "J")).isEqualTo(487)
        }
    }
}