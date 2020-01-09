package com.salsapop

import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.component.VEvent
import spock.lang.Specification

import java.time.LocalDate

class UtilTest extends Specification {
    def "check that event occurs on a certain day"() {
        setup:
        def calString =
            """|BEGIN:VCALENDAR
               |VERSION:2.0
               |PRODID:-//salsapop.com//calendar salsa-bachata-etc//EN        
               |BEGIN:VEVENT
               |UID:06cabbc7-d4f2-4526-8a12-026f63a5c2c2
               |DTSTAMP:20200104T032923Z
               |SUMMARY:3 Vinos
               |DESCRIPTION:Bachata Mondays\\, Salsa Wednesdays
               |LOCATION:3 Vinos\\, 201 North Citrus Ave\\, Covina\\, CA 91723
               |URL:https://3vinos.com/live-entertainment
               |DTSTART:20200106T213000
               |RRULE:FREQ=WEEKLY;BYDAY=MO,TH
               |END:VEVENT
               |END:VCALENDAR""".stripMargin("|")
        def builder = new CalendarBuilder()
        def cal = builder.build(new StringReader(calString))
        def events = cal.getComponents("VEVENT")

        when:
        def aMonday = LocalDate.parse("2020-01-06")

        then:
        Util.eventOccursOnDay(events[0] as VEvent, aMonday.toDate())
    }
}
