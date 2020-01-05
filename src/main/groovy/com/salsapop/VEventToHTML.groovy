package com.salsapop

import net.fortuna.ical4j.model.component.VEvent
import groovy.text.GStringTemplateEngine

class VEventToHTML {

    private gte = new GStringTemplateEngine()
    private eventTemplate = new File(getClass().getResource("/templates/vevent.gsp").toURI()).getText()

    def parse(VEvent vevent) {
        def binding = ['incomingEvent': vevent]
        return gte.createTemplate(eventTemplate).make(binding).toString()
    }
}
