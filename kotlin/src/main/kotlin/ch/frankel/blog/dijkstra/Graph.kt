package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    private val evaluatedPaths = mutableMapOf<String, Int>()

    fun findShortestPath(start: String, end: String): Int {
        recurseFindShortestPath(start, end)
        return evaluatedPaths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(node: String, end: String): String {
        return if (node == end) end
        else {
            weightedPaths[node]?.forEach {
                val currentDistance = evaluatedPaths.getOrDefault(it.key, Integer.MAX_VALUE)
                val newDistance = it.value + evaluatedPaths.getOrDefault(node, 0)
                if (newDistance < currentDistance)
                    evaluatedPaths[it.key] = newDistance
            }
            evaluatedPaths.remove(node)
            val nextNode = evaluatedPaths.minBy { it.value }?.key
                ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
            recurseFindShortestPath(nextNode, end)
        }
    }
}