function formSubmit() {
	if (parseInt(arguments[0]) == 1) {
		myform.setAttribute("action", "1.jsp");
		myform.submit();
	}
	if (parseInt(arguments[0]) == 2) {
		myform.action = "b.jsp";
	}
}
