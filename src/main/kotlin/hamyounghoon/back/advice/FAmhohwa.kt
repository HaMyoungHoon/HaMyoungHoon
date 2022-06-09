package hamyounghoon.back.advice

import hamyounghoon.back.advice.exception.NotExistResourceException
import java.lang.Exception
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class FAmhohwa {
    companion object {
        val AES_KEY = "6574852065748520".toByteArray()

        fun encrypt(data: String): String {
            val keySpec = SecretKeySpec(AES_KEY, "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            return cipher.doFinal(data.toByteArray()).toHex()
        }
        fun decrypt(data : String): String {
            try {
                val dataArray = data.hexStringToByteArray()
                val keySpec = SecretKeySpec(AES_KEY, "AES")
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                cipher.init(Cipher.DECRYPT_MODE, keySpec)
                return String(cipher.doFinal(dataArray))
            }
            catch (ex : Exception) {
                throw NotExistResourceException()
            }
        }

        private fun ByteArray.toHex(): String {
            var ret = ""
            for (by in this)
            {
                ret += String.format("%02X", by)
            }

            return ret
        }
        private fun String.hexStringToByteArray(): ByteArray {
            val len = this.length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len)
            {
                data[i / 2] = ((Character.digit(this[i], 16) shl 4) +
                        Character.digit(this[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }
    }
}
