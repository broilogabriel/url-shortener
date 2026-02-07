package io.github.broilogabriel.util

object Base62Encoder {
    private const val BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private const val BASE = BASE62_CHARS.length

    tailrec fun encode(num: Long, str: String = ""): String {
        if (num == 0L) return str.ifEmpty { BASE62_CHARS[0].toString() }
        val remainder = (num % BASE).toInt()
        return encode(num / BASE, BASE62_CHARS[remainder] + str)
    }

    tailrec fun decode(str: String, num: Long = 0L): Long {
        if (str.isEmpty()) return num
        val char = str.first()
        return decode(str.drop(1), num * BASE + BASE62_CHARS.indexOf(char))
    }

}
