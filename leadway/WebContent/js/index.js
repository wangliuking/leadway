// JavaScript Document

	
$("#oneo").mouseover(function(){
	$(".pd_navlist").show();
	})
$("#oneo").mouseout(function(){
	$(".pd_navlist").hide();
	})
$(".pd_navlist").mouseover(function(){
	$(".pd_navlist").show();
	})
$(".pd_navlist").mouseout(function(){
	$(".pd_navlist").hide();
	})	
/*--大小图片切换--*/
var Bimg = document.getElementById('Bigimg');
var Simg = document.getElementById('Smlimg');
var Bimgli = Bimg.getElementsByTagName('li');
var Simgli = Simg.getElementsByTagName('li');

for(var a = 0; a <= Simgli.length - 1; a ++){

Simgli[a].x = a;
Simgli[a].onclick = function(){	
	for(var b = 0; b <= Simgli.length - 1; b ++){
		Simgli[b].className = '';
		Bimgli[b].style.display = 'none';
	}
	this.className = 'now';
	Bimgli[this.x].style.display = 'block';
}
}


/*选择颜色*/
var Color = document.getElementById('color');
var aColor = Color.getElementsByTagName('a');

for(var i = 0; i <= aColor.length - 1; i ++){		
aColor[i].onclick = function(){
	for(var j = 0; j <= aColor.length - 1; j ++){
		aColor[j].style.borderColor = '#f00';
		
	}
	this.style.borderColor = '#f00';
}
}


/*选择尺寸*/
var Size = document.getElementById('size');
var aSize = Size.getElementsByTagName('a');

for(var n = 0; n <= aSize.length - 1; n ++){		
aSize[n].onclick = function(){
	for(var m = 0; m <= aSize.length - 1; m ++){
		aSize[m].style.borderColor = '#d2d2d2';
		
	}
	this.style.borderColor = '#f00';
}
}

/*数量选择*/
var Jian = document.getElementById('jian');
var Jia = document.getElementById('jia');
var Unber = document.getElementById('shuz');
var v = Unber.value;

V = parseInt(v);
Jian.onclick = function(){
	if(V !== 1){
		V = V - 1;
		Unber.value = V;
	}
}
Jia.onclick = function(){
	V = V + 1;
	Unber.value = V;
}


