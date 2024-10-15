package compressionTool

class NumericNode(
    private val weight: Int,
    private val left: Node,
    private val right: Node
) :Node {
    override fun weight() :Int{
        return weight
    }

    fun left(): Node{
        return left
    }

    fun right(): Node{
        return right
    }

    override fun toString(): String {
        return "NumericNode[weight:$weight,elements:[$left,$right]]"
    }
}