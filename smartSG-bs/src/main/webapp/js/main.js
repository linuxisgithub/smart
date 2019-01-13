var main_path = $("#contextPath").val();
var ajaxObj = {};
/**
 * 小数验证，返回：true=验证通过，false=验证不通过 value：要验证的数字 intNum：多少位整数 decimalNum：多少位小数
 */
function validateDecimal(value, intNum, decimalNum) {
	var reguStr = "^0{1}([.]\\d{1," + decimalNum + "})?$|^[1-9]\\d{0,"
			+ (intNum - 1) + "}([.]{1}[0-9]{1," + decimalNum + "})?$";
	var regExp = new RegExp(reguStr);
	if (value.match(regExp) != null) {
		return true;
	}
	return false;
};
/**
 * 正整数验证，返回：true=验证通过，false=验证不通过 value：要验证的数字
 */
function validateInteger(value) {
	if (value == undefined || value == "") {
		return false;
	}
	var reg = /^[1-9]\d*$/;
	return reg.test(value);
}
/**
 * 整数验证，返回：true=验证通过，false=验证不通过 value：要验证的数字 intNum：多少位整数
 */
function validateNum(value, intNum) {
	var reguStr = "^0{1}$|^[1-9]{1}[0-9]{0," + (intNum - 1) + "}$";
	var regExp = new RegExp(reguStr);
	if (value.match(regExp) != null) {
		return true;
	}
	return false;
};
/**
 * 格式化小数 value：要格式化的值 decimalNum：保留多少位小数
 */
function fixedDecimal(value, decimalNum) {
	value = parseFloat(value);
	return value.toFixed(decimalNum);
}
/**
 * 验证手机号，返回：true=验证通过，false=验证不通过 phnoe：手机号
 */
function validatePhone(phnoe) {
	var regExp = /^1[345678]\d{9}$/;
	return regExp.test(phnoe);
}
/**
 * 验证邮箱，返回：true=验证通过，false=验证不通过 meail：手机号
 */
function validateEmail(meail) {
	if (meail.length == 0 || meail == "") {
		return false;
	}
	var regExp = /^[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+@[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+(\.[\-!#\$%&'\*\+\\\.\/0-9=\?A-Z\^_`a-z{|}~]+)+$/;
	return regExp.test(meail);
}
/**
 * 去掉空格
 * @param strVal
 * @returns
 */
function trim(strVal) {
	return $.trim(strVal);
}
/**
 * 获取某个层级的部门
 * @param $select
 * @param options.type 不传或者传-1查询所有部门
 * @param options.dtype 不传或者传-1查询全部
 */
function findDeptsByType($select, type, dtype) {
	initDeptsByType($select, {type: type,dtype: dtype, flag:false});
}
/**
 * 获取某个层级的部门
 * @param $select
 * @param options.type 不传或者传-1查询所有部门， -2=查公司层面包含董事会，1=查公司层面，2=查一级部门，3=查二级部门，4=查三级部门，5=查合作单位
 * @param options.dtype  不传或者传-1=查全部   1=查本公司部门  2=查食阁
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 * @returns
 */
function initDeptsByType($select, options) {
	if(options.type == undefined) {
		options.type = -1;
	}
    if(options.dtype == undefined) {
        options.dtype = -1;
    }
	var _options = {
		url : main_path + "/sysDepartment/data/" + options.type +"/" + options.dtype +".htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取部门数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 食阁类型
 * @param $select
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 */
function initSgType($select, options) {
	var _options = {
		url : main_path + "/sg/type/data.htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取食阁类型数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 摊位品种
 * @param $select
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 */
function initTwVarieties($select, options) {
	var _options = {
		url : main_path + "/twVarieties/data.htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取部门数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 报表字段
 * @param $select
 * @param options.typetype=1查APP顾客、type=2查摊位摊位、type=3查食阁、type=4查菜品
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 */
function initReportField($select, options) {
	var _options = {
		url : main_path + "/field/" + options.type + "/data.htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取报表字段数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 报表
 * @param $select
 * @param options.kind=1查APP顾客、type=2查摊位摊位、type=3查食阁、type=4查菜品
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 */
function initReport($select, options) {
	var _options = {
		url : main_path + "/report/" + options.kind + "/data.htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取报表字段数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 菜品分组
 * @param $select
 * @param options.deptId 摊位id
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 */
function initDishGroup($select, options) {
	var _options = {
		url : main_path + "/dishGroup/" + options.deptId + "/data.htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取菜品分组数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 获取某个部门下的员工
 * @param $select
 * @param options.deptId 不传或者传-1查询所有
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 * @returns
 */
function initUserByDeptId($select, options) {
	if(options.deptId == undefined || options.deptId == "") {
		options.deptId = -1;
	}
	if(options.isManager == undefined || options.isManager == "") {
		options.isManager = 0;
	}
	var _options = {
		url : main_path + "/sysUser/data/list/" + options.deptId  +".htm?isManager=" + options.isManager,
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取员工数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}



/**
 * 查询静态数据
 * @param $select
 * @param options
 * @returns
 */
function initDictionary($select, options) {
	
	var _options = {
		url : main_path + "/main/dictionaryList/" + options.type +".htm",
		selectValue : "value",
		selectText : "name",
		errorMsg : "获取静态数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}
/**
 * 初始化主项目和一二级项目
 * @param $select
 * @param options
 * @returns
 */
function initFirstSecondProject($select, options) {
	
	var _options = {
		url : main_path + "/main/firstSecondProjectList/" + options.bid +".htm",
		selectValue : "eidStr",
		selectText : "name",
		errorMsg : "获取项目失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}
/**
 * 获取某个部门下的员工-用来初始化多个下来框
 * @param selectArrays-->[{$select:$("#selectId"), defaultVal: "1"},{$select:$("#selectId2")}]
 * @param options.deptId 不传或者传-1查询所有
 * @param options.flag true=有返回值(同步获取)，false=无返回值(异步获取)
 * @returns
 */
function initMultUsersByDeptId(selectArrays, options) {
	if(options.deptId == undefined || options.deptId == "") {
		options.deptId = -1;
	}
	if(options.isManager == undefined || options.isManager == "") {
		options.isManager = 0;
	}
	var _options = {
		url : main_path + "/sysUser/data/list/" + options.deptId +".htm?isManager=" + options.isManager,
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取员工数据失败！"
	};
	_options = $.extend({}, _options, options);
	return multInitSelects2(selectArrays, _options);
}

/**
 * 获取公司员工
 * @param $select
 * @param options
 * @returns
 */
function initUser($select, options) {
	if(options.deptId == undefined || options.deptId == "") {
		options.deptId = -1;
	}
	var _options = {
		url : main_path + "/sysUser/data/detail/list/"+options.deptId+".htm",
		selectValue : "eid",
		selectText : "name",
		errorMsg : "获取员工数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}
/**
 * 获取公司员工
 * @param $select
 * @param options
 * @returns
 */
function initUser2($select, options) {
	if(options.deptId == undefined || options.deptId == "") {
		options.deptId = -1;
	}
	var _options = {
			url : main_path + "/sysUser/data/detail/list/"+options.deptId+".htm",
			selectValue : "eid",
			selectText : "name",
			errorMsg : "获取员工数据失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}


/**
 * $selectArrays：<select/> jquery对象数组对象 options：一些参数 flag：true=有返回值
 */
function multInitSelects($selectArrays, options, flag) {
	var _items = {};
	$.ajax({
			'url' : options.url,
			'type' : "post",
			'async' : !flag,
			'dataType' : 'json',
			'success' : function(data, statusText) {
				if (data.status == 200) {
					var s_data = [];
					if (typeof (options.firstOptions) != "undefined") {
						if(options.firstOptions.id != "false"){
							s_data.push(options.firstOptions);
						}
					} else {
						s_data.push({
							id : "-1",
							text : "请选择"
						});
					}
					$.each(data.data, function(index, item) {
						if (typeof (flag) != "undefined" && flag == true) {
							_items[item[options.selectValue]] = item;
						}
						s_data.push({
							id : item[options.selectValue],
							text : item[options.selectText]
						});
					});
					$.each($selectArrays,function(index, $select) {
										$select.select2({
													data : s_data,
													minimumResultsForSearch : minimumResultsForSearch_detault,
													language : "zh-CN"
												});
									});
				} else {
					layer.msg(options.errorMsg);
				}
			},
			'error' : function(xmlHttp, e1, e2) {
				layer.msg(options.errorMsg);
			},
		});
	return _items;
}

/**
 * 获取系统设置里的类型；根据userid获取（报告选择）
 * @param $select
 * @param options
 * @returns
 */
function initReportChoose($select, options) {
	
	var _options = {
		url : main_path + "/main/list/"+options.btype+".htm",
		selectValue : "eid",
		selectText : "title",
		errorMsg : "获取失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

/**
 * 获取系统设置里的类型，根据deptid
 * @param $select
 * @param options
 * @returns
 */
function initType($select, options) {
	
	var _options = {
		url : main_path + "/main/list/dept/"+options.btype+".htm",
		selectValue : "eid",
		selectText : "title",
		errorMsg : "获取失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}
/**
 * 超级搜索用 根据userid查找类型
 * @param $select
 * @param options
 * @returns
 */
function initTypeForSearch($select, options) {
	
	var _options = {
		url : main_path + "/main/list/dept/"+options.btype+"/"+options.userId+".htm",
		selectValue : "eid",
		selectText : "title",
		errorMsg : "获取失败！"
	};
	_options = $.extend({}, _options, options);
	return initSelect2($select, _options);
}

function initSelect2($select, options) {
	var _items = {};
	var a = $.ajax({
		'url' : options.url,
		'type' : "post",
		'async' : !options.flag,
		'dataType' : 'json',
		'success' : function(data, statusText) {
			if (data.status == 200) {
				var s_data = [];
				if (typeof (options.firstOptions) != "undefined") {
					if(options.firstOptions.id != "false"){
//						s_data.push(options.firstOptions);
						
						_items[options.firstOptions[options.selectValue]] = options.firstOptions;
						s_data.push({
							id : options.firstOptions[options.selectValue],
							text : options.firstOptions[options.selectText]
						});
					}
				} else {
					var d_item = {eid : "-1",name : "请选择"};
					_items[d_item[options.selectValue]] = d_item;
					s_data.push({
						id : "-1",
						text : "请选择"
					});
				}
				$.each(data.data, function(index, item) {
					if (typeof (options.flag) != "undefined" && options.flag == true) {
						_items[item[options.selectValue]] = item;
					}
					s_data.push({
						id : item[options.selectValue],
						text : item[options.selectText]
					});
				});
				$select.select2({
					data : s_data,
					minimumResultsForSearch : search_detault,
					language : "zh-CN"
				});
				if (typeof (options.defaultVal) != "undefined"
						&& options.defaultVal != "") {
					setDefault($select, options.defaultVal);
				}
				try{
					callBack(s_data,options.pid);
				}catch(error){}
			} else {
				$.prompt.message(options.errorMsg, $.prompt.error);
			}
		},
		'error' : function(xmlHttp, e1, e2) {
			$.prompt.message(options.errorMsg, $.prompt.error);
		},
	});
	if(typeof options.key != "undefined"){
		ajaxObj[options.key] = a;
	}
	return _items;
}
function setDefault($select, defaultVal) {
	setTimeout(function() {
		$select.val(defaultVal).trigger("change");
	}, 100);
}
/**
 * $select：<select/> jquery对象 options：一些参数 flag：true=有返回值
 */
function initSelect($select, options, flag) {
	var _items = {};
	$.ajax({
		'url' : options.url,
		'type' : "post",
		'async' : !flag,
		'dataType' : 'json',
		'success' : function(data, statusText) {
			if (data.status == 200) {
				var s_data = [];
				if (typeof (options.firstOptions) != "undefined") {
					if(options.firstOptions.id != "false"){
						s_data.push(options.firstOptions);
					}
				} else {
					s_data.push({
						id : "-1",
						text : "请选择"
					});
				}
				$.each(data.data, function(index, item) {
					if (typeof (flag) != "undefined" && flag == true) {
						_items[item[options.selectValue]] = item;
					}
					s_data.push({
						id : item[options.selectValue],
						text : item[options.selectText]
					});
				});
				$select.select2({
					data : s_data,
					minimumResultsForSearch : minimumResultsForSearch_detault,
					language : "zh-CN"
				});
				if (typeof (options.defaultVal) != "undefined"
						&& options.defaultVal != "") {
					//$select.val(options.defaultVal).trigger("change");
					setDefault($select, options.defaultVal);
				}
			} else {
				layer.msg(options.errorMsg);
			}
		},
		'error' : function(xmlHttp, e1, e2) {
			layer.msg(options.errorMsg);
		},
	});
	return _items;
}
function multInitSelects2(selectArrays, options) {
	var _items = {};
	$.ajax({
		'url' : options.url,
		'type' : "post",
		'async' : !options.flag,
		'dataType' : 'json',
		'success' : function(data, statusText) {
			if (data.status == 200) {
				var s_data = [];
				if (typeof (options.firstOptions) != "undefined") {
					if(options.firstOptions.id != "false"){
						s_data.push(options.firstOptions);
					}
				} else {
					var d_item = {eid : "-1",name : "请选择"};
					_items[d_item[options.selectValue]] = d_item;
					s_data.push({
						id : "-1",
						text : "请选择"
					});
				}
				$.each(data.data, function(index, item) {
					if (typeof (options.flag) != "undefined" && options.flag == true) {
						_items[item[options.selectValue]] = item;
					}
					s_data.push({
						id : item[options.selectValue],
						text : item[options.selectText]
					});
				});
				$.each(selectArrays, function(index, selectOptions) {
					selectOptions.$select.select2({
						data : s_data,
						minimumResultsForSearch : search_detault,
						language : "zh-CN"
					});
					/*if (typeof (selectOptions.defaultVal) != "undefined"
						&& selectOptions.defaultVal != "") {
						selectOptions.$select.val(selectOptions.defaultVal).trigger("change");
					}*/
				});
			} else {
				$.prompt.message(options.errorMsg, $.prompt.error);
			}
		},
		'error' : function(xmlHttp, e1, e2) {
			$.prompt.message(options.errorMsg, $.prompt.error);
		},
	});
	return _items;
}

/**
 * 小写转大写
 * @param money
 * @returns {String}
 */
var toBigNumber = function(money) {
	// 汉字的数字
	var cnNums = new Array(' 零', ' 壹', ' 贰', ' 叁', ' 肆',
			' 伍', ' 陆', ' 柒', ' 捌', ' 玖');
	// 基本单位
	var cnIntRadice = new Array('', ' 拾', ' 佰', ' 仟');
	// 对应整数部分扩展单位
	var cnIntUnits = new Array('', ' 万', ' 亿', ' 兆');
	// 对应小数部分单位
	var cnDecUnits = new Array(' 角', ' 分', ' 毫', ' 厘');
	// 整数金额时后面跟的字符
	var cnInteger = ' 整';
	// 整型完以后的单位
	var cnIntLast = ' 元';
	// 最大处理的数字
	var maxNum = 999999999999999.9999;
	// 金额整数部分
	var integerNum;
	// 金额小数部分
	var decimalNum;
	// 输出的中文金额字符串
	var chineseStr = '';
	// 分离金额后用的数组，预定义
	var parts;
	if (money == '') {
		return '';
	}
	money = parseFloat(money);
	if (money >= maxNum) {
		// 超出最大处理数字
		return '';
	}
	if (money == 0) {
		chineseStr = cnNums[0] + cnIntLast + cnInteger;
		return chineseStr;
	}
	// 转换为字符串
	money = money.toString();
	if (money.indexOf('.') == -1) {
		integerNum = money;
		decimalNum = '';
	} else {
		parts = money.split('.');
		integerNum = parts[0];
		decimalNum = parts[1].substr(0, 4);
	}
	// 获取整型部分转换
	if (parseInt(integerNum, 10) > 0) {
		var zeroCount = 0;
		var IntLen = integerNum.length;
		for (var i = 0; i < IntLen; i++) {
			var n = integerNum.substr(i, 1);
			var p = IntLen - i - 1;
			var q = p / 4;
			var m = p % 4;
			if (n == '0') {
				zeroCount++;
			} else {
				if (zeroCount > 0) {
					chineseStr += cnNums[0];
				}
				// 归零
				zeroCount = 0;
				chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
			}
			if (m == 0 && zeroCount < 4) {
				chineseStr += cnIntUnits[q];
			}
		}
		chineseStr += cnIntLast;
	}
	// 小数部分
	if (decimalNum != '') {
		var decLen = decimalNum.length;
		for (var i = 0; i < decLen; i++) {
			var n = decimalNum.substr(i, 1);
			if (n != '0') {
				chineseStr += cnNums[Number(n)] + cnDecUnits[i];
			}
		}
	}
	if (chineseStr == '') {
		chineseStr += cnNums[0] + cnIntLast + cnInteger;
	} else if (decimalNum == '') {
		chineseStr += cnInteger;
	}
	return chineseStr;
}