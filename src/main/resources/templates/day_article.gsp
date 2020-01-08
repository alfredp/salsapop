<%
    def articleDate = date as java.time.LocalDate
%>
<article>
    <div class="container">
        <div class="day-box">
            <h2>${articleDate.format("EEEE")}</h2>
            <% if(includeShortDate) { %>
            <span class="short-day">${articleDate}</span>
            <% } %>
        </div>
        ${eventFragments}
    </div>
</article>