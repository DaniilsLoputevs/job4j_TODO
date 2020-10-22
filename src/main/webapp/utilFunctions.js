function getContextPath() {
    return location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}

function substringFrom(String, start, charsSkipFromEnd) {
    return String.substring(start, String.length - charsSkipFromEnd);
}