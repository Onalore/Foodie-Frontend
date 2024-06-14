package com.example.foodiefrontend.data.hardware.buzzer

interface Buzzer {

    /**
     * Beeps
     *
     * @param   count       Beep count.
     * @param   time        Beep sound time.
     * @param   interval    Beep silence time.
     * */
    fun beep(
        count: Int = 1,
        time: Int = 300,
        interval: Int = 300,
    )
}