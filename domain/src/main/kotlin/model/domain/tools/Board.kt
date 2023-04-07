package model.domain.tools

class Board(system: Map<Location, Stone>) {
    private val _system: MutableMap<Location, Stone> = system.toMutableMap()
    val system
        get() = _system.toMap()

    fun getStone(location: Location): Stone = requireNotNull(system[location])

    fun canPlace(location: Location) = getStone(location) == Stone.EMPTY

    fun placeStone(location: Location, stone: Stone): Board {
        _system[location] = stone
        return Board(system)
    }

    companion object {
        fun from(size: Int): Board {
            val locations = (0 until size).map { x ->
                getLocation(x, size)
            }.flatten()

            val system = locations.associateWith { Stone.EMPTY }.toMutableMap()
            return Board(system)
        }

        private fun getLocation(x: Int, size: Int) =
            List(size) {
                Location(x, it)
            }
    }
}
