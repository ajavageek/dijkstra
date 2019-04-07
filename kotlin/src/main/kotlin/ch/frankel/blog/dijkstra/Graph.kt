package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    fun findShortestPath(start: String, end: String): Int {
        val (_, paths) = recurseFindShortestPath(start, end, mapOf())
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(node: String, end: String, paths: Map<String, Int>): Pair<String, Map<String, Int>> {
        return if (node == end) end to paths
        else {
            val updatedPaths = updatePaths(node, paths)
            val nextNode = updatedPaths.minBy { it.value }?.key
                ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
            recurseFindShortestPath(nextNode, end, updatedPaths)
        }
    }

    private fun updatePaths(node: String, paths: Map<String, Int>): Map<String, Int> {
        var updatedPaths = paths
        weightedPaths[node]?.forEach {
            updatedPaths = updatedPaths + updatePath(paths, it, node)
        }
        return updatedPaths - node
    }

    private fun updatePath(paths: Map<String, Int>, entry: Map.Entry<String, Int>, node: String): Map<String, Int> {
        val currentDistance = paths.getOrDefault(entry.key, Integer.MAX_VALUE)
        val newDistance = entry.value + paths.getOrDefault(node, 0)
        return if (newDistance < currentDistance)
            paths + (entry.key to newDistance)
        else
            paths
    }
}