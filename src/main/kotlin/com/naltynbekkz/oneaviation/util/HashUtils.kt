package com.naltynbekkz.oneaviation.util

import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.security.spec.KeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.xor

/**
 * Hash passwords for storage, and test passwords against password tokens.
 * Instances of this class can be used concurrently by multiple threads.
 */
@Service
class HashUtils {
    private val random: SecureRandom
    private val cost: Int

    /**
     * Hash a password for storage.
     *
     * @return a secure authentication token to be stored for later authentication
     */
    fun hash(password: CharArray): String {
        val salt = ByteArray(SIZE / 8)
        random.nextBytes(salt)
        val dk = pbkdf2(password, salt, 1 shl cost)
        val hash = ByteArray(salt.size + dk.size)
        System.arraycopy(salt, 0, hash, 0, salt.size)
        System.arraycopy(dk, 0, hash, salt.size, dk.size)
        val enc = Base64.getUrlEncoder().withoutPadding()
        return ID + cost + '$' + enc.encodeToString(hash)
    }

    /**
     * Authenticate with a password and a stored password token.
     *
     * @return true if the password and token match
     */
    fun authenticate(password: String, token: String): Boolean {
        val m = layout.matcher(token)
        require(m.matches()) { "Invalid token format" }
        val iterations = iterations(m.group(1).toInt())
        val hash = Base64.getUrlDecoder().decode(m.group(2))
        val salt = Arrays.copyOfRange(hash, 0, SIZE / 8)
        val check = pbkdf2(password.toCharArray(), salt, iterations)
        var zero = 0
        for (idx in check.indices) {
            zero = zero or (hash[salt.size + idx] xor check[idx]).toInt()
        }
        return zero == 0
    }

    companion object {
        /**
         * Each token produced by this class uses this identifier as a prefix.
         */
        const val ID = "$31$"

        /**
         * The minimum recommended cost, used by default
         */
        const val DEFAULT_COST = 16
        private const val ALGORITHM = "PBKDF2WithHmacSHA1"
        private const val SIZE = 128
        private val layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})")
        private fun iterations(cost: Int): Int {
            require(!(cost < 0 || cost > 30)) { "cost: $cost" }
            return 1 shl cost
        }

        private fun pbkdf2(password: CharArray, salt: ByteArray, iterations: Int): ByteArray {
            val spec: KeySpec = PBEKeySpec(password, salt, iterations, SIZE)
            return try {
                val f = SecretKeyFactory.getInstance(ALGORITHM)
                f.generateSecret(spec).encoded
            } catch (ex: NoSuchAlgorithmException) {
                throw IllegalStateException("Missing algorithm: $ALGORITHM", ex)
            } catch (ex: InvalidKeySpecException) {
                throw IllegalStateException("Invalid SecretKeyFactory", ex)
            }
        }
    }

    /**
     * Create a password manager with a specified cost
     */
    init {
        val cost = DEFAULT_COST
        iterations(cost) /* Validate cost */
        this.cost = cost
        random = SecureRandom()
    }
}