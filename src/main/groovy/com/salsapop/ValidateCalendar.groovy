package com.salsapop

import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.Calendars

class ValidateCalendar {
    static void main(String[] args) {
        assert args[0] // path to ics file
        def cal = Calendars.load(args[0])
        def vevents = cal.components.findAll({ c -> c instanceof VEvent })
        println("Found " + vevents.size() + " event(s) to validate\n")
        vevents.each { c->
            (c as VEvent).with { e ->
                e.validate()
                println(e.summary.getValue())
                if(e.description)
                    println(e.description.getValue())
                println e.getStartDate().getDate().toDayOfWeek().toString().toLowerCase().capitalize()
                def addr = e.getLocation().getValue()
                def url = e.getUrl().getValue()
                println "${addr}\n${url}\n"
            }
        }
        println("Done.")
    }
}
