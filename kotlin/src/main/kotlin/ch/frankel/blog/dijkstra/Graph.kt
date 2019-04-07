package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    private val evaluatedPaths = mutableMapOf<String, Int>()

    fun findShortestPath(start: String, end: String): Int {
        var current = start
        while (current != end) {
            current = updateShortestPath(current)
        }
        return evaluatedPaths.getValue(end)
    }

    private fun updateShortestPath(node: String): String {
        val subGraph = weightedPaths[node]
        for (entry in subGraph!!) {
            val currentDistance = evaluatedPaths.getOrDefault(entry.key, Integer.MAX_VALUE)
            val newDistance = entry.value + evaluatedPaths.getOrDefault(node, 0)
            if (newDistance < currentDistance)
                evaluatedPaths[entry.key] = newDistance
        }
        evaluatedPaths.remove(node)
        return evaluatedPaths.minBy { it.value }?.key!!
    }
}