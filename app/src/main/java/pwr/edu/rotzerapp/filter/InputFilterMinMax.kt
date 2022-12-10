package pwr.edu.rotzerapp.filter

import android.text.InputFilter
import android.text.Spanned


class InputFilterMinMax : InputFilter {
    private var min: Int
    private var max: Int

    constructor(min: Int, max: Int) {
        this.min = min
        this.max = max
    }

    constructor(min: String, max: String) {
        this.min = min.toInt()
        this.max = max.toInt()
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            //The commented line below only works if you append/modify the end of the text (not the beginning or middle)
            //int input = Integer.parseInt(dest.toString() + source.toString());
            //corrected solution below (3lines)
            val part1 = dest.subSequence(0, dstart)
            val part2 = dest.subSequence(dend, dest.length)
            val input = (part1.toString() + source.toString() + part2).toInt()
            if (isInRange(min, max, input)){
                return null
            }
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c >= a && c <= b else c >= b && c <= a
    }
}