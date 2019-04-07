package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    fun findShortestPath(start: String, end: String): Int {
        val (_, paths) = recurseFindShortestPath(start, end, mapOf())
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(node: String, end: String, paths: Map<String, Int>): Pair<String, Map<String, Int>> {
        return if (node == end) end to paths
        else {
            val updatedPaths = updatePaths(node, paths.toMutableMap())
            val nextNode = updatedPaths.minBy { it.value }?.key
                ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
            recurseFindShortestPath(nextNode, end, updatedPaths)
        }
    }

    private fun updatePaths(node: String, paths: MutableMap<String, Int>): MutableMap<String, Int> {
        weightedPaths[node]?.forEach {
            val currentDistance = paths.getOrDefault(it.key, Integer.MAX_VALUE)
            val newDistance = it.value + paths.getOrDefault(node, 0)
            if (newDistance < currentDistance)
                paths[it.key] = newDistance
        }
        paths.remove(node)
        return paths
    }
}