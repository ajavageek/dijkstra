package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    fun findShortestPath(start: String, end: String): Int {
        val (_, paths) = recurseFindShortestPath(start, end, mutableMapOf())
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(node: String, end: String, paths: MutableMap<String, Int>): Pair<String, MutableMap<String, Int>> {
        return if (node == end) end to paths
        else {
            weightedPaths[node]?.forEach {
                val currentDistance = paths.getOrDefault(it.key, Integer.MAX_VALUE)
                val newDistance = it.value + paths.getOrDefault(node, 0)
                if (newDistance < currentDistance)
                    paths[it.key] = newDistance
            }
            paths.remove(node)
            val nextNode = paths.minBy { it.value }?.key
                ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
            recurseFindShortestPath(nextNode, end, paths)
        }
    }
}