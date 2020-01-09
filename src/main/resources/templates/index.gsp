<!DOCTYPE html>
<html>
<head>
    <title>Salsapop! | Salsa Dancing in Los Angeles, Orange County, and Southern California!</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0' />
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<header>
    <div class="container">
        <h1>Salsapop!</h1>
        <strong class="subheader">Dancing in LA/OC/SoCal today</strong>
    </div>
</header>
<main>
    <%
        for(day in days) {
            day = day as java.time.LocalDate
    %>
    <article>
        <div class="container">
            <div class="day-box">
                <h2>
                    ${day.format("EEEE")}
                </h2>
                <p class="date">
                    <time datetime="${day.format("YYYY-MM-DD")}">${day.format("MMM DD")}</time>
                </p>
            </div>
            <%
                    for(vevent in vevents) {
                        vevent = vevent as net.fortuna.ical4j.model.component.VEvent
                        def encodedAddr = java.net.URLEncoder.encode(vevent.getLocation().getValue(), "UTF-8")
                        if(com.salsapop.Util.eventOccursOnDay(vevent, day.toDate())) {
            %>
            <section class="event">
                <section class="event-name">
                    <h3>${vevent.getSummary().getValue()}</h3>
                </section>
                <section class="event-details">
                    <% if(vevent.getDescription()) { %>
                    <div class="description">
                        <p>
                            <em>${vevent.getDescription().getValue()}</em>
                        </p>
                    </div>
                    <% } %>
                    <div class="address-icons">
                        <p>
                            <span class="addr">${vevent.getLocation().getValue()}</span>
                        </p>
                        <div class="icons">
                            <div>
                                <a target="_blank" href="${vevent.getUrl().getValue()}">
                                    <img class="icon" alt="Website" src="images/link.svg">
                                </a>
                            </div>
                            <div>
                                <a target="_blank" href="https://www.google.com/maps?q=${encodedAddr}">
                                    <img class="icon" alt="Website" src="images/map.svg">
                                </a>
                            </div>
                        </div>
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