// JavaScript Document

//新品推荐
$(".lu li").mouseover(function(){
	$(this).find(".bg").css("display","block");
	})
$(".lu li").mouseout(function(){
$(this).find(".bg").css("display","none");
})

$('#new').mouseover(function(){
$('#new div')	.show();
	
	})

$('#new').mouseout(function(){
$('#new div')	.hide();
	
	})

//热卖产品

$('#hot').mouseover(function(){
$('#hot div')	.show();
	
	})

$('#hot').mouseout(function(){
$('#hot div')	.hide();
	
	})	
	
//夏日必备

 window.onload=function(){
  var Oul=document.getElementById('ul');  //获取导航栏无序列表 ul 的 id
  var Oli=Oul.getElementsByTagName('li');     //获取导航栏无序列表 ul 的 li 元素
  var Odiv=Oul.getElementsByTagName('div');   //获取导航栏无序列表 ul 的 div元素
	for(var i=0;i<Oli.length;i++){                          
	   Oli[i].a=i
		Oli[i].onmouseover=function(){                //鼠标移入事件
	  
			Odiv[this.a].style.display = 'block'   //当鼠标移入li时显示下面的下拉菜单
			
			}
		}
		  for(var k=0;k<Oli.length;k++){
	   Oli[k].a=k
		Oli[k].onmouseout=function(){                      //鼠标移出事件
				
			Odiv[this.a].style.display = 'none'         //当鼠标移出li时隐藏下面的下拉菜单
			
			}
		}
		
  
		
 }  
//热卖产品

$(".pro li").mouseover(function(){
	
 		$(this).find(".moren").css("display","block");
	 });
 $(".pro li").mouseout(function(){
    
	 $(this).find(".moren").css("display","none");
 })

	
	