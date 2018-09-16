package internship.asiantech.a2018summerfinal.service.utils

class TimeTransferUtils {
        fun millisecondToClock(millisecond: Int): String {
            var second = millisecond / 1000
            val minutes = second / 60
            second %= 60
            var sec = "" + second
            if (second < 10) {
                sec = "0$sec"
            }
            return "$minutes:$sec"
        }
}