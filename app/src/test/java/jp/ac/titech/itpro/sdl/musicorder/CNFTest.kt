package jp.ac.titech.itpro.sdl.musicorder

import Sat.Solver
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CNFTest {
    @Test
    fun defaultCNF3() {
        //Assert.assertEquals(4, 2 + 2)
        val trueAns3 = "1 2 3 0\n-1 -2 0\n-1 -3 0\n-2 -3 0\n";
        val poi = CNF(3)
        Assert.assertEquals(trueAns3, poi.alternative(listOf(1, 2, 3)))
    }

    @Test
    fun defaultCNF5() {
        val poi = CNF(5)
        var ans: String = "";
        val tracknum = 5
        val tempList = (1..tracknum).toList()
        for (i in 1..tracknum) {
            ans += poi.alternative(tempList.map { it + (i - 1) * tracknum }) //曲iは１..n(it)番目のどこかに入る
        }
        for (i in 1..tracknum) {
            ans += poi.alternative(tempList.map { (it - 1) * tracknum + i })//i番目には曲1..nのどれかが入る
        }
        //Assert.assertEquals(ans, poi.cnf)
    }

    //曲数3で曲1と3が連続しない
    @Test
    fun out13CNF3() {
        val poi = CNF(3)
        poi.noSequencial(1, 3);
        val ans = CNF(3).getCNF() + "-1 -8 0\n-2 -9 0\n-7 -2 0\n-8 -3 0\n";
        Assert.assertEquals(ans, poi.getCNF())
    }

    //javaのテスト
    @Test
    fun satTest() {
        val poi = CNF(3)
        poi.noSequencial(1, 3);
        Assert.assertEquals("", Solver.main(poi.getCNF()))
    }
    @Test
    fun setOneOfListToNTest(){
        val poi = CNF(3)
        poi.noSequencial(1,2)
        poi.setOneOfListToN(listOf(1),3)
        Assert.assertEquals("", Solver.main(poi.getCNF()))
    }
}