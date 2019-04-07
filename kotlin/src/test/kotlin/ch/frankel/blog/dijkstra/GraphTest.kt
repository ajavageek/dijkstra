package ch.frankel.blog.dijkstra

import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class GraphTest {

    @DataProvider
    fun data(): Array<Array<Any>> {
        // https://fr.wikipedia.org/wiki/Probl%C3%A8me_de_plus_court_chemin
        val graph1 = Graph(
            mapOf(
                "A" to mapOf("B" to 4, "C" to 2),
                "B" to mapOf("C" to 5, "D" to 10),
                "C" to mapOf("E" to 3),
                "D" to mapOf("F" to 11),
                "E" to mapOf("D" to 4)
            )
        )
        // https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
        val graph2 = Graph(
            mapOf(
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
        )
        return arrayOf(
            arrayOf(graph1, "A", "J", 487),
            arrayOf(graph2, "A", "F", 20)
        )
    }

    @Test(dataProvider = "data")
    fun `should match shortest path`(graph: Graph, start: String, end: String, expectedLength: Int) {
        expect {
            that(graph.findShortestPath(start, end)).isEqualTo(expectedLength)
        }
    }
}