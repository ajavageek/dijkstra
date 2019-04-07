package ch.frankel.blog.dijkstra

class Graph(private val weightedPaths: Map<String, Map<String, Int>>) {

    fun findShortestPath(start: String, end: String): Int {
        val paths = recurseFindShortestPath(NodePaths(start, end)).paths
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(nodePaths: NodePaths): NodePaths {
        return if (nodePaths.isFinished()) nodePaths
        else {
            val nextNode = nodePaths.nextNode(weightedPaths)
            recurseFindShortestPath(nextNode)
        }
    }
}

data class NodePaths(private val node: String, private  val end: String, val paths: Map<String, Int> = emptyMap()) {

    private fun updatePath(entry: Map.Entry<String, Int>): NodePaths {
        val currentDistance = paths.getOrDefault(entry.key, Integer.MAX_VALUE)
        val newDistance = entry.value + paths.getOrDefault(node, 0)
        return if (newDistance < currentDistance)
            this + (entry.key to newDistance)
        else
            this
    }

    private fun updatePaths(weightedPaths: Map<String, Map<String, Int>>) = (weightedPaths[node]
        ?.map { updatePath(it) }
        ?.fold(copy()) { acc, item -> acc + item } ?: copy()) - node

    fun nextNode(weightedPaths: Map<String, Map<String, Int>>): NodePaths {
        val updatedPaths = updatePaths(weightedPaths)
        val nextNode = updatedPaths.paths.minBy { it.value }?.key as String
        return updatedPaths.copy(node = nextNode)
    }

    fun isFinished() = node == end

    operator fun plus(other: NodePaths) = copy(paths = paths + other.paths)

    operator fun plus(pair: Pair<String, Int>) = copy(paths = paths + pair)

    operator fun minus(node: String)= copy(paths = paths - node)
}