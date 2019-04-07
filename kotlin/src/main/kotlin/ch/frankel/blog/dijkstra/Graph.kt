package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    fun findShortestPath(start: String, end: String): Int {
        val (_, paths) = recurseFindShortestPath(NodePaths(start), end)
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(nodePaths: NodePaths, end: String): NodePaths {
        return if (nodePaths.node == end) NodePaths(end, nodePaths.paths)
        else {
            val updatedPaths = updatePaths(nodePaths)
            val nextNode = updatedPaths.paths.minBy { it.value }?.key
                ?: throw RuntimeException("Map was empty, this cannot happen with a non-empty graph")
            recurseFindShortestPath(NodePaths(nextNode, updatedPaths.paths), end)
        }
    }

    private fun updatePaths(nodePaths: NodePaths) = (weightedPaths[nodePaths.node]
        ?.map { nodePaths.updatePath(it) }
        ?.fold(nodePaths.copy()) { acc, item -> acc + item } ?: nodePaths.copy()) - nodePaths.node

}

data class NodePaths(val node: String, val paths: Map<String, Int> = emptyMap()) {

    fun updatePath(entry: Map.Entry<String, Int>): NodePaths {
        val currentDistance = paths.getOrDefault(entry.key, Integer.MAX_VALUE)
        val newDistance = entry.value + paths.getOrDefault(node, 0)
        return if (newDistance < currentDistance)
            copy(paths = paths + (entry.key to newDistance))
        else
            copy(paths = paths)
    }

    operator fun plus(other: NodePaths): NodePaths {
        if (node != other.node) throw RuntimeException("Not possible to merge NodePaths with different nodes")
        return copy(paths = paths + other.paths)
    }

    operator fun minus(node: String): NodePaths {
        return copy(paths = paths - node)
    }
}