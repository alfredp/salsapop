<%
    def events = util.events.collect { x -> x as net.fortuna.ical4j.model.component.VEvent }
    def eventDays = util.eventDays.collect { x -> x as java.time.LocalDate }

    def printWeekBox = util.printDates && util.eventDays.size() == 1

    def getTitle = {
        if(printWeekBox) {
            def firstDay = util.eventDays.head() as java.time.LocalDate
            "Salsapop! | Salsa dancing for ${firstDay.format("EEEE, MMMM DD, YYYY")}"
        }
        else if(!util.printDates) {
            "Salsapop! | Weekly salsa dancing schedule - LA, OC, SoCal"
        }
        else {
            def (monday, sunday) = util.getMondayAndSunday(util.eventDays.head())
            "Salsapop! | Dancing in LA, OC, SoCal - ${monday.format("MMM DD, YYYY")} to ${sunday.format("MMM DD, YYYY")}"
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>${getTitle()}</title>
    <meta charset="UTF-8">
    <meta name="description" content="Get your salsa dancing fix today in Los Angeles, Orange County, and Southern California!">
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0' />
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<header>
    <div class="container">
        <h1><a href="/">Salsapop!</a></h1>
        <strong class="tagline">Dancing in LA/OC/SoCal today</strong>
    </div>
</header>
<main>
    <% if(printWeekBox) { %>
    <div class="week-box">
        <div class="container">
            <%
                def (monday, sunday) = util.getMondayAndSunday(util.eventDays.head())
            %>
            <h2>
                <a href="/${monday.format("YYYY")}/wk${monday.format("ww")}">
                    <strong><span class="week-of-txt">Week of </span>${monday.format("MM/dd/YYYY")} - ${sunday.format("MM/dd/YYYY")}</strong>
                    <img alt="Go" src="/images/lo.svg">
                </a>
            </h2>
        </div>
    </div>
    <% } else { %>
    <div class="day-jump">
        <div class="container">
            <nav>
                <ul>
                    <li><a href="#monday">M</a></li>
                    <li><a href="#tuesday">T</a></li>
                    <li><a href="#wednesday">W</a></li>
                    <li><a href="#thursday">Th</a></li>
                    <li><a href="#friday">F</a></li>
                    <li><a href="#saturday">Sa</a></li>
                    <li><a href="#sunday">Su</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <% } %>
    <%
        for(day in eventDays) {
    %>
    <article>
        <div class="container">
            <section id="${day.format("EEEE").toLowerCase()}" class="day-box">
                <h3>
                    <time datetime="${day.format("YYYY-MM-dd")}">
                        <%
                            if(util.printDates) {
                                def printDayLink = eventDays.size() > 1
                                if(printDayLink) {
                        %>
                            <a href="/${day.format("YYYY/MM/dd")}">${day.format("EEEE, MMM dd, YYYY")}</a>
                        <%
                                } else { %>
                            ${day.format("EEEE, MMM dd, YYYY")}
                        <%
                                }
                            }
                            else {
                        %>
                            ${day.format("EEEE")}
                        <%
                            }
                        %>
                    </time>
                </h3>
            </section>
            <%
                    for(vevent in events) {
                        def encodedAddr = java.net.URLEncoder.encode(vevent.getLocation().getValue(), "UTF-8")
                        if(com.salsapop.HtmlUtil.eventOccursOnDay(vevent, day.toDate())) {
            %>
            <section class="event">
                <section class="event-name">
                    <h4><a target="_blank" href="${vevent.getUrl().getValue()}">${vevent.getSummary().getValue()}</a></h4>
                    <a target="_blank" href="${vevent.getUrl().getValue()}"><img alt="Website" src="/images/lo.svg"></a>
                </section>
                <section class="event-details">
                    <% if(vevent.getDescription()) { %>
                    <p class="description">
                        <em>${vevent.getDescription().getValue()}</em>
                    </p>
                    <% } %>
                    <div class="event-address">
                        <a target="_blank" href="https://www.google.com/maps?q=${encodedAddr}">
                            <img class="icon" alt="Website" src="/images/map.svg">
                        </a>
                        <p class="addr">
                            ${vevent.getLocation().getValue()}
                        </p>
                    </div>
                </section>
            </section>
            <%
                        }
                    }
            %>
        </div>
    </article>
    <% } %>
</main>
</body>
</html>