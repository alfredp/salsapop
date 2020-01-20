<?php
    $d = new DateTime();
    header("Location: " . $d->format("/Y/") . "wk" . date("W"), true, 302);

//    echo $d->sub(new DateInterval(sprintf("P%dD",$d->format("w"))));
//    echo $d->sub(new DateInterval("P0D"))->format("Y/m/d");

//    printf("Day of the week: %d<br>", date("w"));
//    $d = new DateTime(new DateInterval(""));
//    echo $d->format("/Y/m/d");
?>