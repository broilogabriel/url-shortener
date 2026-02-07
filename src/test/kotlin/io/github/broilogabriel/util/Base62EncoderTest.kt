package io.github.broilogabriel.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Base62EncoderTest {

    @Test
    fun `encode returns correct Base62 string for various numbers`() {
        assertEquals("0", Base62Encoder.encode(0))
        assertEquals("1", Base62Encoder.encode(1))
        assertEquals("a", Base62Encoder.encode(36))
        assertEquals("Z", Base62Encoder.encode(35))
        assertEquals("10", Base62Encoder.encode(62))
        assertEquals("19", Base62Encoder.encode(71))
    }

    @Test
    fun `decode returns correct number from Base62 string`() {
        assertEquals(0L, Base62Encoder.decode("0"))
        assertEquals(1L, Base62Encoder.decode("1"))
        assertEquals(36L, Base62Encoder.decode("a"))
        assertEquals(35L, Base62Encoder.decode("Z"))
        assertEquals(62L, Base62Encoder.decode("10"))
        assertEquals(71L, Base62Encoder.decode("19"))
    }

    @Test
    fun `encode and decode are inverse operations`() {
        val numbers = listOf(0L, 1L, 100L, 1000L, 10000L, 100000L, 1000000L)
        numbers.forEach { num ->
            val encoded = Base62Encoder.encode(num)
            val decoded = Base62Encoder.decode(encoded)
            assertEquals(num, decoded, "Failed for number: $num")
        }
    }
}
