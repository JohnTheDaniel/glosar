
function changePictureWidth(){
	if ($(window).width() > 901){
		/* Dimensionen för bilden är 1148 × 1994 
		Höjden = 1.73693379791 * bredden
		y = 1.73693379791 * x
	*/
	var kp = 1994/1148; //k - konstant, p - picture
	
	//Initialize the width 
	var initialHeight = $(".phoneholder").height();
	$(".phoneholder").width(initialHeight/kp);
	$(".picture").width(initialHeight/kp);
	
	
	/* Del 2 */
	
	var pictureWidth = initialHeight/kp
	
	var ksw = 1148/720 //k - konstant, s - screenshot, w - width
	var ksh = 1994/1280 //h - height
	var screenshotWidth = pictureWidth/ksw + 2
	var screenshotHeight = initialHeight/ksh + 2
	//The +2 is a safety margin. It makes the screenshot a little bit too big, to be on the safe side. 
	
	$(".screenshot").height(screenshotHeight).width(screenshotWidth).css({
		marginTop : -screenshotHeight/2,
		marginLeft : -screenshotWidth/2.05
	});
	
	
	/* Make the padding of the footer a bit to the right */
	$(".footerContent").css({
		paddingLeft : $(".picture").width()
	});
	
	//If we resize the window, repeat
	$(window).resize(function(){
		if ($(window).width() > 901){
			var initialHeight = $(".phoneholder").height();
			$(".phoneholder").width(initialHeight/kp);
			$(".picture").width(initialHeight/kp);
			
			
			//For the phone 
			var pictureWidth = $(".picture").width();
		
			var screenshotWidth = pictureWidth/ksw + 2;
			var screenshotHeight = $(".picture").height()/ksh + 2;
			
			$(".screenshot").height(screenshotHeight).width(screenshotWidth).css({
				marginTop : -screenshotHeight/2,
				marginLeft : -screenshotWidth/2.05
				});
			
			$(".footerContent").css({
				paddingLeft : $(".picture").width()
			});	
		}
		else {
			location.reload();
		}
		
	});

	}
	else {
		location.reload();
	}
}

$(document).ready(function() {
	if ($(window).width() > 901){
		changePictureWidth();
	}
	$(window).resize(function(){
		if ($(window).width() > 901){
			changePictureWidth();
		}
	});
	
	$(".picture").click(function(){
		$(".screenshot").animate({opacity: 0},500);
	});
});