function startTime() {
	var today = new Date();
	var mon =  today.getMonth()+1;
	var day = today.getDate();
	mon = checkTime(mon);
	day = checkTime(day);
	var weekday = new Array(7);
	weekday[0] = "星期日";
	weekday[1] = "星期一";
	weekday[2] = "星期二";   
	weekday[3] = "星期三";
	weekday[4] = "星期四";
	weekday[5] = "星期五";
	weekday[6] = "星期六";
	document.getElementById('header_date').innerHTML = mon + "&#47;" + day + " "+ weekday[today.getDay()];
	// + h + ":" + m ;
	// windows对象方法，用于在指定毫秒后调用函数或计算表达式
	t = setTimeout(function() {
		startTime()
	}, 500);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i;
	}
	return i;
}
