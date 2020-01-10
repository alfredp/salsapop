package com.salsapop.cli

import com.salsapop.HtmlUtil
import groovy.text.GStringTemplateEngine
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.Calendars

import java.time.LocalDate

class Entry {
    static def gte = new GStringTemplateEngine()
    static def indexTemplate = new File(Entry.class.getResource("/index.gsp").toURI()).getText()

    public static void main(String[] args) {
        assert args[0]
        assert args[1]

        def icsFile = new File(args[0])
        def outputRoot = args[1]

        def cal = Calendars.load(icsFile.toURI().toURL())
        def events = cal.getComponents("VEVENT").collect{ e -> e as VEvent}

        def ninetyDays = makeDays(90)
        printWeeksAndDays(ninetyDays, events, outputRoot)

        printDefaultIndex(events, outputRoot)
    }

    static def makeDays(int n) {
        def d = LocalDate.now()
        def thisPastMonday = d.minusDays(d.dayOfWeek.value - 1)
        return (0..n).collect { i -> thisPastMonday.plusDays(i) }
    }

    @groovy.transform.TailRecursive
    static def printWeeksAndDays(List<LocalDate> eventDays, List<VEvent> events, String outputRoot) {
        if(eventDays.empty)
            return
        def xs = eventDays.take(7)
        printWeek(xs, events, outputRoot)
        for(day in xs) {
            printDay(day, events, outputRoot)
        }
        return printWeeksAndDays(eventDays.drop(7), events, outputRoot)
    }

    static def printWeek(List<LocalDate> eventDays, List<VEvent> events, String outputRoot) {
        def h = eventDays.head()
        def outDir = "${outputRoot}/${h.format("YYYY")}/wk${h.format("ww")}"
        new File(outDir).mkdirs()
        def outFile = new File("${outDir}/index.html")
        def html = gte.createTemplate(indexTemplate).make(['util': new HtmlUtil(eventDays, events, true)]).toString()
        outFile.withWriter { r ->
            r.println(html)
        }
    }

    static def printDay(LocalDate day, List<VEvent> events, String outputRoot) {
        def outDir = "${outputRoot}/${day.format("YYYY/MM/dd")}"
        new File(outDir).mkdirs()
        def outFile = new File("${outDir}/index.html")
        def html = gte.createTemplate(indexTemplate).make(['util': new HtmlUtil([day], events, true)]).toString()
        outFile.withWriter { r ->
            r.println(html)
        }
    }

    static def printDefaultIndex(List<VEvent> events, String outputRoot) {
        def weeklyEventsOnly = events.findAll { e -> e.getProperty("RRULE").getValue().contains("WEEKLY") }
        def sevenDays = makeDays(7)
        def outFile = new File("${outputRoot}/index.html")
        def html = gte.createTemplate(indexTemplate)
                .make(['util': new HtmlUtil(sevenDays, weeklyEventsOnly)]).toString()
        outFile.withWriter { it.println(html) }
    }
}