package com.salsapop

import net.fortuna.ical4j.model.DateTime
import net.fortuna.ical4j.model.Period
import net.fortuna.ical4j.model.component.VEvent

import java.time.Duration
import java.time.LocalDate

class HtmlUtil {
    List<LocalDate> eventDays
    List<VEvent> events
    Boolean printDates

    HtmlUtil(List<LocalDate> eventDays, List<VEvent> events, Boolean printDates = false) {
        this.eventDays = eventDays
        this.events = events
        this.printDates = printDates
    }

    static Boolean eventOccursOnDay(VEvent vEvent, java.util.Date date) {
        def rs = vEvent.calculateRecurrenceSet(new Period(new DateTime(date), Duration.ofDays(1)))
        return rs != null && rs.size() > 0
    }

    static String urlFriendlyName(String s) {
        s.replaceAll(~"[^0-9a-zA-Z]", "-").toLowerCase()
    }

    static def getMondayAndSunday(LocalDate d) {
        def monday = d.minusDays(d.getDayOfWeek().getValue() - 1)
        def sunday = d.plusDays(7 - d.getDayOfWeek().getValue())
        return [monday, sunday]
    }
}
