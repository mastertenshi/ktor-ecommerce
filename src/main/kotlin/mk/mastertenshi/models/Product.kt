package mk.mastertenshi.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val title: String,
    val category: String,
    val description: String,
    val imgUrls: List<String>,
    val type: Map<String, List<String>>,
    val price: Map<String, Int>,
    val stock: MutableMap<String, Int> = mutableMapOf(),
    val clicks: Int = 0
) {
    init {
        initStock()
    }

    /**
     * Initializes [stock] with all combinations of [type].values
     * in the format of "1:2:3..." with the numbers indicating the List number
     *
     * Example:
     *
     *     Given the [type].values [["L", "XL"], ["white", "red", "yellow"]]
     *     You would get "L:white", "L:red", "L:yellow", "XL:white",...
     */
    private fun initStock() {
        if (type.isNotEmpty()) {
            crawl()
        } else {
            stock[""] = 0
        }
    }

    private fun crawl(index: Int = 0, prefix: String = "") {
        if (type.values.size - 1 == index) {
            addAll(type.values.elementAt(index), prefix)
        } else {
            type.values.elementAt(index).forEach {
                crawl(index + 1, "$prefix$it:")
            }
        }
    }

    private fun addAll(list: List<String>, prefix: String = "") {
        list.forEach {
            stock["$prefix$it"] = 0
        }
    }
}
