// JavaScript Document
var oBanner=document.getElementById('banner');
var oPic=document.getElementById('pic');
var aLi=oPic.getElementsByTagName('li');
var oNum=document.getElementById('num');
var a_Li=oNum.getElementsByTagName('li');
var timer=null;
var i=0;

//自动轮播
start()  //1-调用自动播放的函数
function start(){
timer=setInterval(function(){
	if(i==aLi.length-1){
		i=0
		
		}
	else{
		i++
		}
		
	del();	//调用清空之前效果的函数
	
	aLi[i].style.zIndex=6;
	a_Li[i].className='active';	
	
	} ,2000)		
	}
	
//清空之前效果的函数	
	function del(){
	for( var j=0; j<aLi.length;j++){
		aLi[j].style.zIndex=0;
		a_Li[j].className='';		
		}
	}
	
//2-大框鼠标进去，停止播放	
	oBanner.onmouseover=function(){
	
	clearInterval(timer)
}

//3-圆点鼠标上去，切换图片
	for(var k=0;k<a_Li.length;k++){
		a_Li[k].index=k
		a_Li[k].onmouseover=function(){
			
			del()
		
		aLi[this.index].style.zIndex=6;	
		this.className='active'	 //圆点的样式			
			}
		
		}	
	
//4-鼠标在banner移开，继续播放	
oBanner.onmouseout=function(){
	start()
	
	}