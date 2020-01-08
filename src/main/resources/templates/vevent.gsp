<%
    def vevent = (incomingEvent as net.fortuna.ical4j.model.component.VEvent)
    def encodedAddr = java.net.URLEncoder.encode(vevent.getLocation().getValue(), "UTF-8")
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