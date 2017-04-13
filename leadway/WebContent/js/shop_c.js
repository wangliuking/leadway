 //JavaScript Document

$(".jia").each(function(k,v){
	$(v).click(function(){
		var num_obj = $(v).parent().parent().find(".shuz");
		var num = num_obj.val();
		num = parseInt(num);
		var total_price = $(v).parent().parent().parent().parent().parent().find(".sp3").text();
        total_price = parseFloat(total_price.replace("总计",""));
        var per_num = total_price/num;
		
		num_obj.val(++num);
		$(v).parent().parent().parent().parent().parent().find(".sp3").text("总计"+num*per_num);
		get_total();
	})
})
$(".jian").each(function(k,v){
	$(v).click(function(){
		var num_obj = $(v).parent().parent().find(".shuz");
		var num = num_obj.val();
        var total_price = $(v).parent().parent().parent().parent().parent().find(".sp3").text();
        total_price = parseFloat(total_price.replace("总计",""));
        var per_num = total_price/num;
		num = parseInt(num);
		if(num>1){
			num_obj.val(--num);
			$(v).parent().parent().parent().parent().parent().find(".sp3").text("总计"+num*per_num);
			get_total();
		}
	
	})
})
$(".sp4 input[type='checkbox']").each(function(k,v){
	$(v).click(function(){
		get_total();
	})
})
function get_total(){
	var total_num = 0;
	var total_price = 0;
	$(".nav_ct li").each(function(k,v){
		//alert($(v).find("input[type='checkbox']").attr('checked'));
		if($(v).find("input[type='checkbox']").prop('checked')){
			var num = parseInt($(v).find(".shuz").val());
			var price = $(v).find(".sp3").text();
			price = parseFloat(price.replace("总计",""));
			total_num += num;
			total_price += price;
		}
	})
	$(".pl_but span").eq(0).text(total_num);
	$(".pl_but span").eq(1).text("￥"+total_price);
}
get_total();
$(".pl_but input[type='checkbox']").click(function(){
	if($(this).prop("checked")){
		$(".nav_ct li input[type='checkbox']").prop("checked",true);
	}else{
		$(".nav_ct li input[type='checkbox']").prop("checked",false);
	}
	get_total();
})
