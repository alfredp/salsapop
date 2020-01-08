package com.salsapop

import groovy.text.GStringTemplateEngine
import net.fortuna.ical4j.model.Calendar
import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.component.VEvent

import java.time.Duration
import java.time.LocalDate

class ComponentToHTML {
    private gte = new GStringTemplateEngine()
    private indexTemplate = new File(getClass().getResource("/templates/index.gsp").toURI()).getText()
    private articleTemplate = new File(getClass().getResource("/templates/day_article.gsp").toURI()).getText()
    private eventTemplate = new File(getClass().getResource("/templates/vevent.gsp").toURI()).getText()

    def parseDefaultIndex(Calendar calendar) {
        def now = LocalDate.now()
        def allEvents = calendar.getComponents("VEVENT")
                .collect{ e -> e as VEvent }
        def articleFragments = (0..6).collect { i ->
            def d = now.minusDays(now.getDayOfWeek().getValue() - (1 + i))
            def eventsForTheDay = allEvents.findAll { e ->
                e.getProperty("RRULE").getValue().contains("WEEKLY") && (eventOccursOnDay(e, d.toDate()))
            }
            parseArticle(d, eventsForTheDay)
        }.join("\n")
        return gte.createTemplate(indexTemplate).make(['articleFragments': articleFragments])
    }

    def parseArticle(LocalDate date, List<VEvent> events, Boolean includeShortDate = false) {
        def eventFragments = events.collect { e -> parse(e) }
        def binding = ['date': date, 'includeShortDate': includeShortDate, 'eventFragments': eventFragments.join("\n")]
        return gte.createTemplate(articleTemplate).make(binding).toString()
    }

    def parse(VEvent vevent) {
        def binding = ['incomingEvent': vevent]
        return gte.createTemplate(eventTemplate).make(binding).toString()
    }

    static Boolean eventOccursOnDay(VEvent vEvent, java.util.Date date) {
        def rs = vEvent.calculateRecurrenceSet(new Period(new DateTime(date), Duration.ofDays(1)))
        return rs != null && rs.size() > 0
    }
}