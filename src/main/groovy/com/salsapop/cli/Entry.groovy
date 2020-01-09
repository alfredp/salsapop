package com.salsapop.cli

import groovy.text.GStringTemplateEngine
import net.fortuna.ical4j.util.Calendars

import java.time.LocalDate

class Entry {
    public static void main(String[] args) {
        assert args[0]
        assert args[1]

        def icsFile = new File(args[0])
        def outFile = new File(args[1])

        def cal = Calendars.load(icsFile.toURI().toURL())
        def events = cal.getComponents("VEVENT")

        def today = LocalDate.now()
        def days = (1..7).collect { i -> today.minusDays(today.dayOfWeek.value - i) }

        def gte = new GStringTemplateEngine()
        def indexTemplate = new File(Entry.class.getResource("/templates/index.gsp").toURI()).getText()

        def html = gte.createTemplate(indexTemplate).make(['days': days, 'vevents': events]).toString()

        outFile.withWriter { r ->
            r.write(html)
        }
    }
}