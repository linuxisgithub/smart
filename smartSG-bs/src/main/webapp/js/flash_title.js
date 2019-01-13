;
(function($) {
	$.extend({
		/**
		 * 调用方法： var timerArr = $.blinkTitle.show();
		 *     $.blinkTitle.clear(timerArr);
		 */
		blinkTitle : {
			show : function(msg) { //有新消息时在title处闪烁提示
				if(msg == undefined) {
					msg = '新消息';
				}
				var step = 0, _title = document.title;
				var timer = setInterval(function() {
					step++;
					if (step == 3) {
						step = 1;
					}
					if (step == 1) {
						if(msg == '新消息') {
							document.title = '【　　　】' + _title;
						} else {
							document.title = '【新消息】' + _title;
						}
					}
					if (step == 2) {
						document.title = '【' + msg + '】' + _title;
					}
				}, 500);
				return [ timer, _title , msg];
			},
			/**
			 * @param timerArr[0], timer标记
			 * @param timerArr[1], 初始的title文本内容
			 * @param timerArr[2], 闪烁的文本
			 */
			clear : function(timerArr) {
				//去除闪烁提示，恢复初始title文本
				if (timerArr) {
					clearInterval(timerArr[0]);
					document.title = timerArr[1];
				}
			},
			/**
			 * @param timerArr[0], timer标记
			 * @param timerArr[1], 初始的title文本内容
			 * @param timerArr[2], 闪烁的文本
			 */
			restart : function(timerArr) {
				// 恢复闪烁
				if (timerArr) {
					var step = 0, _title = timerArr[1];
					var msg = timerArr[2];
					var timer = setInterval(function() {
						step++;
						if (step == 3) {
							step = 1;
						}
						if (step == 1) {
							document.title = '【　　　】' + _title;
						}
						if (step == 2) {
							document.title = '【' + msg + '】' + _title;
						}
					}, 500);
					return [ timer, _title , msg];
				}
			}
		}
	});
})(jQuery);