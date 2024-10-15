package compressionTool

fun generateEquivalenceTable(root: Node):Map<Char, String> {
    val equivalenceTable = mutableMapOf<Char, String>()

    fun traverse(node: Node, path: String) {
        when (node) {
            is CharacterNode -> {
                equivalenceTable[node.character()] = path
            }
            is NumericNode -> {
                traverse(node.left(), path + "0")
                traverse(node.right(), path + "1")
            }
        }
    }
    traverse(root, "")

    return equivalenceTable
}