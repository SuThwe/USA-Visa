package com.su.purple.usvisapreparation.helper.datetimepicker

interface DateTimePickerListener {
    fun getDate(year: Int, month: Int, day: Int)
    fun getTime(hourOfDay: Int, minute: Int)
}