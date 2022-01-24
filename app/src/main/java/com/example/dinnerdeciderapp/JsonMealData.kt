package com.example.dinnerdeciderapp

import android.content.Context
import java.io.File
import java.io.IOException

class JsonMealData {

    private val myFileName = "myMeals.json"

    fun getJSONMealData(context: Context, filesDir: File): String? {

        var json: String? = null
        val file = File(filesDir, myFileName)

        try {
            //Check it the file does not exist
            if(!file.exists()){
                //Creates the file if not exists
                context.openFileOutput(myFileName, Context.MODE_PRIVATE).use{
                    it.write("".toByteArray())
                }
            } else {
                //Else, opens the file and reads the text
                context.openFileInput(myFileName).use { stream ->
                    json = stream.bufferedReader().use {
                        it.readText()
                    }
                }
            }
        } catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun writeJSONMealData(context: Context, byteArray: ByteArray){
        context.openFileOutput(myFileName, Context.MODE_PRIVATE).use{
            it.write(byteArray)
        }
    }

}