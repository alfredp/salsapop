package com.salsapop

import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.Calendars
import spock.lang.Specification

class VEventToHTMLTest extends Specification {
    def "can print event html"() {
        setup:
        def cal = Calendars.load(getClass().getResource("/cal.ics"))
        def events = cal.getComponents().findAll { c -> c instanceof VEvent }

        when:
            def vevent2html = new VEventToHTML()
            events.each { e ->
                println(vevent2html.parse(e as VEvent))
            }

        then:
            def fragments = events.collect { e -> vevent2html.parse(e as VEvent) }
            fragments.every { e -> e }
    }
}
