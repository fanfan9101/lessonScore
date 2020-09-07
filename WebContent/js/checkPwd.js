function checkPwd() {
	var userpasswd = document.getElementById('pwd');
	var errPasswd = document.getElementById('conPasswordErr');
	if (userpasswd.value=="") {
		errPasswd.innerHTML = "请输入密码~~"
		document.getElementById("pwd").focus();
		return false;
	} else {
		
	}
}

function ConfirmPassword() {
	var userpasswd = document.getElementById('pwd');
	var userConPassword = document.getElementById('rePwd');
	var errConPasswd = document.getElementById('conPasswordErr');

	if ((userpasswd.value) != (userConPassword.value) || userConPassword.value.length == 0) {
		errConPasswd.innerHTML = "注意：上下密码不一致!"
		return false;
	} else {
		errConPasswd.innerHTML = "OK"
		return true;
	}
}
