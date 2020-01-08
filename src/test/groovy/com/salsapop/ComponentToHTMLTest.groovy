package com.salsapop

import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.Calendars
import spock.lang.Specification

import java.time.LocalDate

class ComponentToHTMLTest extends Specification {
    def "can print default index"() {
        setup:
        def cal = Calendars.load(getClass().getResource("/cal.ics"))

        when:
        def component2Html = new ComponentToHTML()
        def indexHtml = component2Html.parseDefaultIndex(cal)

        then:
        println(indexHtml)
        indexHtml
    }

    def "can print article html"() {
        setup:
        def cal = Calendars.load(getClass().getResource("/cal.ics"))
        def events = cal.getComponents().findAll { c -> c instanceof VEvent }
                .collect{ e -> e as VEvent}

        when:
        def component2Html = new ComponentToHTML()
        def article = component2Html.parseArticle(LocalDate.now(), events)

        then:
        println(article)
        article
    }

    def "can print event html"() {
        setup:
        def cal = Calendars.load(getClass().getResource("/cal.ics"))
        def events = cal.getComponents().findAll { c -> c instanceof VEvent }

        when:
            def vevent2html = new ComponentToHTML()
            events.each { e ->
                println(vevent2html.parse(e as VEvent))
            }

        then:
            def fragments = events.collect { e -> vevent2html.parse(e as VEvent) }
            fragments.every { e -> e }
    }

    def "check that event occurs on a certain day"() {
        def cal = Calendars.load(getClass().getResource("/cal.ics"))
        def events = cal.getComponents().findAll { c -> c instanceof VEvent }

        when:
        def component2Html = new ComponentToHTML()

        then:
        def aMonday = LocalDate.parse("2020-01-06")
        component2Html.eventOccursOnDay(events[0] as VEvent, aMonday.toDate())
    }
}
