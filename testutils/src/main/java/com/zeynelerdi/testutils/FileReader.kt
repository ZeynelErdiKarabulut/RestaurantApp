

package com.zeynelerdi.testutils

import android.content.Context
import java.io.*

/**
 * Created by zeynelerdi on 02-Jan-18.
 *
 * @author <a href="https://github.com/ZeynelErdiKarabulut">zeynelerdi</a>
 */
object FileReader {

    private fun getStringFromInputStream(`is`: InputStream): String {
        var br: BufferedReader? = null
        val sb = StringBuilder()

        try {
            br = BufferedReader(InputStreamReader(`is`))
            var hasNextLine = true
            while (hasNextLine) {
                val line = br.readLine()
                hasNextLine = line != null
                line?.let { sb.append(it) }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (br != null) {
                try {
                    br.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return sb.toString()
    }

    fun getStringFromRawFile(context: Context, filename: Int): String =
            getStringFromInputStream(context.resources.openRawResource(filename))


    fun getStringFromFile(file: File): String = getStringFromInputStream(FileInputStream(file))
}
