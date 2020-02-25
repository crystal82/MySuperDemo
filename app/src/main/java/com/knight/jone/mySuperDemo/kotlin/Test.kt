package com.knight.jone.mySuperDemo.kotlin

object Test {

    class People internal constructor(){
        var name = "aaa"
        var age = 1

        fun ageAdd(age: Int): Int {
            return age + 1
        }
    }

    fun main(args: Array<String>) {
        doAction ({
            val ageAdd = ageAdd(1)
            System.out.println("1111:$ageAdd")
            val ageAdd1 = ageAdd(2)
            System.out.println("2222:$ageAdd1")
        },1)
    }

    private fun doAction(action: People.() -> Unit, tag:Int) {
        System.out.println("doAction111")
        action(People())
        action(People(),1)
        System.out.println("doAction222")
    }

    private fun action(people: People, i: Int) {
    }
}