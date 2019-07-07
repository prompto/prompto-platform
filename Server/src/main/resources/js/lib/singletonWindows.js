// use this script to ensure windows are opened only once
function manageSingletonWindow(e) {
    var target = e.target.target;
    if (target && !target.startsWith("_")) {
        openWindowOrBringItToFront(this.event.target.href, target);
        e.preventDefault();
    }
}

function openWindowOrBringItToFront(href, target) {
	// resolve target window location href
	var url = new URL(href, document.location);
	// bring existing window to front or create empty one
    var w = window.open("", target); 
    // if window is incorrect, load it
    if(!w || w.location.href !== url.href) {
        window.open(href, target);
    }
}

document.addEventListener("click", function(e) {
    if(e.target.constructor===HTMLAnchorElement)
        manageSingletonWindow(e);
});