package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var bir: Button
    private lateinit var ikki: Button
    private lateinit var uch: Button
    private lateinit var tor: Button
    private lateinit var besh: Button
    private lateinit var olti: Button
    private lateinit var yetti: Button
    private lateinit var sakkiz: Button
    private lateinit var toqqiz: Button
    private lateinit var nol: Button

    private lateinit var bol: Button
    private lateinit var kopay: Button
    private lateinit var plus: Button
    private lateinit var minus: Button

    private lateinit var ochir: Button
    private lateinit var toza: Button
    private lateinit var teng: Button
    private lateinit var nuqta: Button

    private lateinit var misol: TextView
    private lateinit var javob: TextView

    private var belgibormi = false
    private var kasrmi = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemKirit()

        bir.setOnClickListener(this)
        ikki.setOnClickListener(this)
        uch.setOnClickListener(this)
        tor.setOnClickListener(this)
        besh.setOnClickListener(this)
        olti.setOnClickListener(this)
        yetti.setOnClickListener(this)
        sakkiz.setOnClickListener(this)
        toqqiz.setOnClickListener(this)
        nol.setOnClickListener(this)


        plus.setOnClickListener {
            belgibos("+")
        }
        minus.setOnClickListener {
            belgibos("-")
        }
        kopay.setOnClickListener {
            belgibos("X")
        }
        bol.setOnClickListener {
            belgibos("/")
        }

        toza.setOnClickListener {
            misol.text = "0"
            javob.text = "0"
            belgibormi = false
            kasrmi = false
        }

        teng.setOnClickListener {
            if (javob.text.toString()!="0"){
            var a = javob.text.toString()
            misol.setText(a)
            javob.text = "0"
            }
        }

        nuqta.setOnClickListener {
            if (!kasrmi){
                misol.text = misol.text.toString() + "."
                kasrmi = true
            }
        }

        ochir.setOnClickListener {
            misol.text = misol.text.dropLast(1).toString()
            hisobla(misol.text.toString())
        }
    }

    fun itemKirit(){

        bir = findViewById(R.id.bir)
        ikki = findViewById(R.id.ikki)
        uch = findViewById(R.id.uch)
        tor = findViewById(R.id.tor)
        besh = findViewById(R.id.besh)
        olti = findViewById(R.id.olti)
        yetti = findViewById(R.id.yetti)
        sakkiz = findViewById(R.id.sakkiz)
        toqqiz = findViewById(R.id.toqqiz)
        nol = findViewById(R.id.nol)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)
        kopay = findViewById(R.id.kopay)
        bol = findViewById(R.id.bol)
        misol = findViewById(R.id.misol)
        javob = findViewById(R.id.javob)
        toza = findViewById(R.id.toza)
        nuqta = findViewById(R.id.nuqta)
        teng = findViewById(R.id.teng)
        ochir = findViewById(R.id.ochir)
    }

    override fun onClick(p0: View?) {
        var btn = findViewById<Button>(p0!!.id)
        if (belgibormi){
            kasrmi = false
        }
        belgibormi = false
        if (misol.text=="0"){
            misol.text=""
            misol.text = misol.text.toString() + btn.text.toString()
        }else{
            misol.text = misol.text.toString() + btn.text.toString()
        }

        javob.setText(hisobla(misol.text.toString()))
    }

    fun belgibos(belgi:String){
        if (belgibormi){
            misol.text = misol.text.dropLast(1).toString() + belgi
            belgibormi = true
        }else{
            misol.text = misol.text.toString() + belgi
            belgibormi = true
        }

    }

    fun massivgaAjrat(s:String):MutableList<Any>{
        var list = mutableListOf<Any>()
        var hozirgi = ""

        for (i in s){
            if (i.isDigit() || i=='.'){
                hozirgi+=i
            }else{
                list.add(hozirgi.toFloat())
                hozirgi = ""
                list.add(i)
            }
        }
        if (hozirgi.isNotEmpty()){
            list.add(hozirgi.toFloat())
        }
        return list
    }


    fun hisobla(misol:String):String{
        var ty = misol.toCharArray()
        var op = misol
        if (ty[0] =='-'){
            op = "0$op"
        }else{
            op = "0+$op"
        }



        var ajratilgan = massivgaAjrat(op)
        var kopBolingan = kopBolHisobla(ajratilgan)
        var javob = plusMinusHisobla(kopBolingan)


        return butunmi(javob)
    }

    fun kopBolHisobla(l: MutableList<Any>): MutableList<Any>{
        var list = l
        var i = 0

        while (list.contains('X')||list.contains('/')){
            if (list[i]=='X' || list[i]=='/'){
                var oldin = list[i-1] as Float
                var keyin = list[i+1] as Float
                var belgi = list[i]
                var javob = 0F

                when(belgi){
                    'X'->{
                       javob = oldin*keyin
                    }
                    '/'->{
                        javob = oldin/keyin
                    }
                }
                list.set(i-1,javob)
                list.removeAt(i)
                list.removeAt(i)
                i -= 2
            }
            i++
        }
        return list
    }

    fun plusMinusHisobla(l: MutableList<Any>):Float{
        var list = l
        var i = 0
        var javob = 0F
        while (list.contains('+')||list.contains('-')){
            if (list[i]=='+' || list[i]=='-'){
                var oldin = list[i-1] as Float
                var keyin = list[i+1] as Float
                var belgi = list[i]

                when(belgi){
                    '+'->{
                        javob = oldin+keyin
                    }
                    '-'->{
                        javob = oldin-keyin
                    }
                }
                list.set(i-1,javob)
                list.removeAt(i)
                list.removeAt(i)
                i -= 2
            }
            i++
        }

        return javob

    }

    fun butunmi(son : Float):String{
        var a = ""


        if (son%1==0f){
            a = son.toString().dropLast(2)
        }else{
            a = son.toString()
        }

        return a
    }
}