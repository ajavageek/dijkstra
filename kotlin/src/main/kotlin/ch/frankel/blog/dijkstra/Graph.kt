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
        weightedPaths[node]?.forEach {
            val currentDistance = evaluatedPaths.getOrDefault(it.key, Integer.MAX_VALUE)
            val newDistance = it.value + evaluatedPaths.getOrDefault(node, 0)
            if (newDistance < currentDistance)
                evaluatedPaths[it.key] = newDistance
        }
        evaluatedPaths.remove(node)
        return evaluatedPaths.minBy { it.value }?.key
            ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
    }
}