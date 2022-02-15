package converter

data class Unit (val type: String, val toBase: (Double) -> Double, val fromBase: (Double) -> Double, val names: Array<String>)

val units = arrayOf<Unit>(
    Unit ("Length", {it}, {it}, arrayOf("meter", "meters", "m")),
    Unit ("Length", {it * 1000.0}, {it /1000.0}, arrayOf("kilometer", "kilometers", "km")),
    Unit ("Length", {it * 0.01}, {it / 0.01}, arrayOf("centimeter", "centimeters", "cm")),
    Unit ("Length", {it * 0.001}, {it / 0.001}, arrayOf("millimeter", "millimeters", "mm")),
    Unit ("Length", {it * 1609.35}, {it / 1609.35}, arrayOf("mile", "miles", "mi")),
    Unit ("Length", {it * 0.9144}, {it / 0.9144}, arrayOf("yard", "yards", "yd")),
    Unit ("Length", {it * 0.3048}, {it / 0.3048}, arrayOf("foot", "feet", "ft")),
    Unit ("Length", {it * 0.0254}, {it / 0.0254}, arrayOf("inch", "inches", "in")),
    Unit ("Weight", {it}, {it}, arrayOf("gram", "grams", "g")),
    Unit ("Weight", {it * 1000.0}, {it / 1000.0}, arrayOf("kilogram", "kilograms", "kg")),
    Unit ("Weight", {it * 0.001},  {it / 0.001}, arrayOf("milligram", "milligrams", "mg")),
    Unit ("Weight", {it * 453.592}, {it / 453.592}, arrayOf("pound", "pounds", "lb")),
    Unit ("Weight", {it * 28.3495}, {it / 28.3495}, arrayOf("ounce", "ounces", "oz")),
    Unit ("Temp", {it - 273.15}, {it + 273.15}, arrayOf("kelvin", "kelvins", "k")),
    Unit ("Temp", {it}, {it}, arrayOf("degree Celsius", "degrees Celsius", "degree celsius", "degrees celsius",
        "celsius", "dc", "c")),
    Unit ("Temp", {(it - 32) * 5 / 9}, {it * 9 / 5 + 32}, arrayOf("degree Fahrenheit", "degrees Fahrenheit",
        "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f")), )

fun main() {
    while (true) {
        println("\nEnter what you want to convert (or exit):")
        val inputs = readLine()!!.split(" ").toMutableList()

        if (inputs.size == 1 && inputs.first() == "exit") break

        if (inputs.size < 4 || !inputs.first().matches(Regex("-?\\d*.?\\d+"))) { println ("Parse error"); continue }
        val value = inputs.removeFirst().toDouble()

        var firstName = inputs.removeFirst().lowercase()
        if (firstName == "degree" || firstName == "degrees") firstName = "$firstName ${inputs.removeFirst().lowercase()}"

        inputs.removeFirst()
        if (inputs.isEmpty()) { println ("Parse error"); continue }

        var secondName = inputs.removeFirst().lowercase()
        if (inputs.isNotEmpty() && (secondName == "degree" || secondName == "degrees"))
            secondName = "$secondName ${inputs.removeFirst().lowercase()}"

        val fromUnit = units.firstOrNull { it.names.contains(firstName) }
        val toUnit = units.firstOrNull { it.names.contains(secondName) }
        
        if (value < 0  && fromUnit != null && fromUnit.type != "Temp") {
            println("${fromUnit.type} shouldn't be negative")
            continue
        }
        
        if(fromUnit != null && toUnit != null && fromUnit.type == toUnit.type ) {
            val result = toUnit.fromBase( fromUnit.toBase(value) )
            println ("$value ${fromUnit.names[if (value == 1.0) 0 else 1]} is" +
                    " $result ${toUnit.names[if (result == 1.0) 0 else 1]}")
        }
        else println ("Conversion from ${fromUnit?.names?.get(1) ?: "???"} to ${toUnit?.names?.get(1) ?: "???"} is impossible")
    }
}