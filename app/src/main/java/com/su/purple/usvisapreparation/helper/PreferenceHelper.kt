package com.su.purple.usvisapreparation.helper

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {
    private const val APPOINTMENT_DATE = "APPOINTMENT_DATE"
    private const val APPOINTMENT_TIME = "APPOINTMENT_TIME"
    private const val APPOINTMENT_CITY = "APPOINTMENT_CITY"
    private const val POPULAR_QUESTION = "POPULAR_QUESTION"

    fun getSharedPreference(context: Context, name: String): SharedPreferences
            = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    var SharedPreferences.appointmentDate
        get() = getString(APPOINTMENT_DATE, "")
        set(value) {
            editMe {
                value?.let { v ->
                    it.putString(APPOINTMENT_DATE, v)
                }
            }
        }

    var SharedPreferences.appointmentTime
        get() = getString(APPOINTMENT_TIME, "")
        set(value) {
            editMe {
                value?.let { v ->
                    it.putString(APPOINTMENT_TIME, v)
                }
            }
        }

    var SharedPreferences.appointmentCity
        get() = getString(APPOINTMENT_CITY, "")
        set(value) {
            editMe {
                value?.let { v ->
                    it.putString(APPOINTMENT_CITY, v)
                }
            }
        }

    var SharedPreferences.popularQuestion
        get() = getString(POPULAR_QUESTION, "")
        set(value) {
            editMe {
                value?.let { v ->
                    it.putString(POPULAR_QUESTION, v)
                }
            }
        }


    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    inline fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        when (val value = pair.second) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }
}