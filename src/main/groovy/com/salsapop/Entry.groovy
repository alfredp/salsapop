package com.salsapop

import net.fortuna.ical4j.util.Calendars

class Entry {
    public static void main(String[] args) {
        assert args[0]
        assert args[1]

        def icsFile = new File(args[0])
        def outFile = new File(args[1])

        def cal = Calendars.load(icsFile.toURI().toURL())
        def component2Html = new ComponentToHTML()

        outFile.withWriter { r ->
            r.write(component2Html.parseDefaultIndex(cal))
        }
    }
}