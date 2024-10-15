package compressionTool

import java.util.*

fun buildNodeTree(characterOccurrences: List<CharacterNode>): PriorityQueue<Node> {
    val nodeTree = PriorityQueue(compareBy<Node> { it.weight() })

    nodeTree.addAll(characterOccurrences)

    while (nodeTree.size > 1) {
        val node1 = nodeTree.poll()
        val node2 = nodeTree.poll()

        val newNode = NumericNode(node1.weight() + node2.weight(), node1, node2)

        nodeTree.add(newNode)
    }

    return nodeTree
}