package jp.ac.titech.itpro.sdl.musicorder

import android.util.Log

class CNF(val tracknum: Int) {
    private var cnf: String = ""
    private val logTag = "CNF"

    init {
        val tempList = (1..tracknum).toList()
        for (i in 1..tracknum) {
            alternative(tempList.map { it + (i - 1) * tracknum }) //曲iは１..n(it)番目のどこかに入る
        }
        for (i in 1..tracknum) {
            alternative(tempList.map { (it - 1) * tracknum + i })//i番目には曲1..nのどれかが入る
        }
    }

    //list中のものからひとつだけ取る
    fun alternative(ls: List<Int>) {
        var ans: String = ""
        val subAnsList: MutableList<String> = mutableListOf()

        //どれか一つはtrue,すべてorでつないだやつ
        for (item in ls) {
            ans += "$item "
        }
        ans += "0\n"

        //ふたつ同時にtrueになることはない
        for (item1 in ls) {
            for (item2 in ls) {
                if (item1 < item2) {
                    subAnsList.add("-$item1 -$item2 0\n")
                }
            }
        }
        ans += subAnsList.joinToString(separator = "")
        Log.d(logTag, ans);
        //return ans;
        cnf += ans
    }

    //引数の二つは連続しない
    fun noSequencial(poi1: Int, poi2: Int) {
        var ans: String = ""
        //poi1が先,(poi1-1)*n+iと(poi2-1)*n+i+1が連続しない
        for (i in 1..(tracknum - 1)) {
            ans += "-${(poi1 - 1) * tracknum + i} -${(poi2 - 1) * tracknum + i + 1} 0\n"
        }
        //poi2が先,(poi2-1)*n+iと(poi1-1)*n+i+1が連続しない
        for (i in 1..(tracknum - 1)) {
            ans += "-${(poi2 - 1) * tracknum + i} -${(poi1 - 1) * tracknum + i + 1} 0\n"
        }
        //return ans
        cnf += ans
    }

    //
    fun getCNF(): String {
        val header = "p cnf"
        val variables = tracknum * tracknum
        val clauses: Int = cnf.split("\n").count() - 1
        return "$header $variables $clauses \n$cnf"
    }
}