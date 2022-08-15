package lerrain.tool;

import lerrain.tool.script.Script;
import lerrain.tool.script.SyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test
{
	static Random ran = new Random();

	public static void main(String[] arg) throws Exception
	{
		String str = "var sts = 200;\n" +
				"if (order.pay ~= 2 && order.status ~= 3) { // 已出单\n" +
				"    sts = 420;\n" +
				"} else if(order.pay ~= 5) {\t// 支付失败\n" +
				"    sts = 310;\n" +
				"    // TODO: 失败原因\n" +
				"} else if(order.pay ~= 1 && order.status ~= 2) {\t// 支付中\n" +
				"    sts = 300;\n" +
				"} else if(order.pay ~= 2 && order.status ~= 2) {\t// 出单中\n" +
				"    sts = 400;\n" +
				"} else if(order.pay ~= 2 && order.status ~= 4) {\t// 承保失败\n" +
				"    sts = 410;\n" +
				"    // TODO: 失败原因\n" +
				"} else if(order.status ~= 4) {\t// 核保失败\n" +
				"    sts = 210;\n" +
				"    // TODO: 失败原因\n" +
				"}\n" +
				"//下期缴费日\n" +
				"var effectiveTime = timestr(time(time(), 0, 0, 1), \"yyyy-MM-dd\" );\n" +
				"var payTime = timestr(time(time(effectiveTime),0,(val(1) * 1),0),\"yyyy-MM-dd\");\n" +
				"var createTimeYMD = timestr(time(effectiveTime),\"yyyy-MM-dd\");\n" +
				"if (str(TimeFX.getDiffMonth(createTimeYMD,payTime)) != str(val(1) * 1)){\n" +
				"    payTime = TimeFX.getLastMD(payTime);\n" +
				"}\n" +
				"var pack = service(\"sale\", \"v2/detail.json\", {packId: order.productId});\n" +
				"var rst = try(order.extra.result, null);\t// 返回数据\n" +
				"var singlePay = rst..singlePay;\n" +
				"var app = try(order.detail.applicant, null);\n" +
				"var inss = try(order.detail.insurants, null);\n" +
				"var rstAccept = try(rst.policyList, null);\t// 承保返回数据\n" +
				"var premDay = timestr(time(time(), 0, 0, 1), 'dd');\n" +
				"var payFreq = try(order.detail.plan.product[0].pay_freq_code, null);\n" +
				"var premiumDesc = \"\";\n" +
				"var tips = \"\";\n" +
				"var gift = false;\n" +
				"if (param.payFreq !='single' && inss != null && size(rstAccept) > 0 && sts ~= 420){\n" +
				"    premiumDesc = \"\";\n" +
				"    if (payFreq == \"month\"){\n" +
				"        tips = \"注：后续每月\"+premDay+\"日，将通过微信自动扣除续期保费\"+order.detail.premium+\"元(总计自动扣费11次）,您可随时取消自动扣费\";\n" +
				"        premiumDesc = \"每月起RDOM(span: {\\\"value\\\":\\\"\"+order.detail.premium+\"元/月\\\",\\\"style\\\": {\\\"color\\\": \\\"#FF7F00\\\"}})\";\n" +
				"        if (order.detail.router == 'market'){\n" +
				"            premiumDesc = order.detail.premium;\n" +
				"        }\n" +
				"    } else {\n" +
				"        premiumDesc = order.detail.premium + \"元\";\n" +
				"        tips = \"注：投保完成后，您可以关注微信公众号“保通保险服务”查看和下载您的电子保单。\";\n" +
				"    }\n" +
				"} else if(rst..singlePay..status ~= '4200'){\n" +
				"    gift = true;\n" +
				"    premiumDesc = rst..singlePay..premium + '元';\n" +
				"    tips = \"注：投保完成后，您可以关注微信公众号“保通保险服务”查看和下载您的电子保单。\";\n" +
				"}\n" +
				"var imgUrl = \"https://static.iyb.tm/web/h5/market-h5/ware\";\n" +
				"var titil={\"mode\":\"price\",\"showPre\":\"每月\",\"label\":\"14\",\"unit\":\"元起\",\"suffixValue\":\"\"};\n" +
				"var PageLayout={\"widget\":\"pay-freq\",\"name\":\"PAY_FREQ\",\"dataBind\":\"PAY_FREQ\",\"value\":\"month\",\"hidden\":false,\"isNoRules\":true,\"wprops\":{\"label\":\"缴费方式\",\"log\":\"inputInsPayType\",\"refreshPrem\":true,\"className\":\"market-pay-freq-switch\",\"options\":[{\"mode\":\"switch\",\"detail\":[[\"month\",\"按月缴费RDOM(p: {\\\"value\\\": \\\"（12期）\\\", \\\"className\\\": \\\"tips\\\"})\"],[\"year\",\"全额缴费\"]],\"logs\":{\"month\":\"inputInsPayType1\",\"year\":\"inputInsPayType2\"}}],\"cascade\":{\"target\":\"premium\",\"props\":{\"month\":{\"type\":\"month\",\"className\":\"\"},\"year\":{\"type\":\"year\",\"className\":\"year\"}}},\"rootcascade\":{\"year\":{\"update\":[{\"target\":\"price\",\"wprops\":{\"type\":\"price\",\"showSuffix\":\"元起\",\"showPre\":\"\",\"showPreDay\":\"\",\"showSuffixDay\":\"\"}}]},\"month\":{\"update\":[{\"target\":\"price\",\"wprops\":{\"mode\":\"text\",\"type\":\"price\",\"flex\":\"2\",\"showPre\":\"每月\",\"showSuffix\":\"元\",\"showPreDay\":\"(每天\",\"showSuffixDay\":\"元)起\",\"emptyValueMask\":\"\",\"valueMask\":\"\",\"originalPrice\":\"\"}}]}},\"moreData\":{\"label\":\"完整费率表\",\"className\":\"ui-market-switch-payFreq noBorderTop\",\"title\":\"RDOM(span:{\\\"value\\\": \\\"查看\\\", \\\"style\\\": {\\\"color\\\": \\\"#999\\\"}})完整费率表\",\"footer\":[{\"type\":\"cancel\",\"className\":\"market-popup-btn\",\"label\":\"知道了\"}],\"subOptions\":[{\"mode\":\"image\",\"style\":{\"marginBottom\":\"60px\"},\"value\":imgUrl + pack.wareId + \"/img-table.png\"}]}}};\n" +
				"var PageLay={\"widget\":\"show-premium\",\"name\":\"premium\",\"dataBind\":\"premium\",\"value\":\"14\",\"hidden\":false,\"className\":\"show-premium-two-list\",\"isNoRules\":true,\"mode\":\"show-premium\",\"wprops\":{\"label\":\"保费\",\"type\":\"month\",\"className\":\"show-premium-two-list\",\"mode\":\"show-premium\",\"month\":[{\"label\":\"1～12期金额\",\"mode\":\"premium\",\"unit\":\"元/月起\"},{\"label\":\"下期缴费时间\",\"mode\":\"time\",\"month\":timestr(time(payTime),'MM'),\"day\":timestr(time(payTime),'dd')}],\"year\":{\"unit\":\"元起\"}}};\n" +
				"var resultsP={\"widget\":\"fixed-bottom-content\",\"name\":\"price\",\"value\":\"14\",\"dayValue\":\"0.5\",\"wprops\":{\"mode\":\"text\",\"type\":\"price\",\"flex\":\"2\",\"showPre\":\"每月\",\"showSuffix\":\"元\",\"showPreDay\":\"(每天\",\"showSuffixDay\":\"元)起\",\"emptyValueMask\":\"\",\"valueMask\":\"\",\"originalPrice\":\"\"}};\n" +
				"if (param.accountId == \"10013852093\" || param.accountId == \"50003895002\" || param.accountId == \"25280002\"){\n" +
				"    titil={\"mode\":\"price\",\"showPre\":\"\",\"label\":\"161\",\"unit\":\"元/年起\"};\n" +
				"    PageLayout={\"widget\":\"pay-freq\",\"name\":\"PAY_FREQ\",\"dataBind\":\"PAY_FREQ\",\"value\":\"year\",\"hidden\":false,\"isNoRules\":true,\"wprops\":{\"label\":\"缴费方式\",\"log\":\"inputInsPayType\",\"refreshPrem\":true,\"className\":\"market-pay-freq-switch\",\"options\":[{\"mode\":\"switch\",\"logs\":{\"month\":\"inputInsPayType1\",\"year\":\"inputInsPayType2\"},\"detail\":[[\"year\",\"全额缴费\"]]}],\"cascade\":{\"target\":\"premium\",\"props\":{\"month\":{\"type\":\"month\",\"className\":\"\"},\"year\":{\"type\":\"year\",\"className\":\"year\"}}},\"rootcascade\":{\"year\":{\"update\":[{\"target\":\"price\",\"wprops\":{\"type\":\"price\",\"showSuffix\":\"元起\",\"showPre\":\"\"}}]},\"month\":{\"update\":[{\"target\":\"price\",\"wprops\":{\"type\":\"staging-price\",\"valueMask\":\"¥{{value}}元/月起\",\"showPre\":\"次月\"}}]}},\"moreData\":{\"label\":\"完整费率表\",\"className\":\"ui-market-switch-payFreq noBorderTop\",\"title\":\"RDOM(span:{\\\"value\\\": \\\"查看\\\", \\\"style\\\": {\\\"color\\\": \\\"#999\\\"}})完整费率表\",\"footer\":[{\"type\":\"cancel\",\"className\":\"market-popup-btn\",\"label\":\"知道了\"}],\"subOptions\":[{\"mode\":\"image\",\"style\":{\"marginBottom\":\"60px\"},\"value\":imgUrl + pack.wareId + \"/img-table.png\"}]}}};\n" +
				"    PageLay = {\"widget\":\"show-premium\",\"name\":\"premium\",\"dataBind\":\"premium\",\"value\":\"161\",\"hidden\":false,\"isNoRules\":true,\"wprops\":{\"label\":\"保费\",\"type\":\"year\",\"month\":[{\"label\":\"首期缴费金额\",\"value\":\"1\",\"unit\":\"元\"},{\"label\":\"2～12期金额\",\"mode\":\"premium\",\"unit\":\"元/月起\"},{\"label\":\"下期缴费时间\",\"mode\":\"time\",\"month\":timestr(time(payTime),'MM'),\"day\":timestr(time(payTime),'dd')}],\"year\":{\"unit\":\"元/年起\"}}};\n" +
				"    resultsP={\"widget\":\"fixed-bottom-content\",\"widget\":\"fixed-bottom-content\",\"name\":\"price\",\"value\":\"161\",\"wprops\":{\"mode\":\"text\",\"type\":\"price\",\"flex\":\"2\",\"showSuffix\":\"元/年起\"}};\n" +
				"}\n" +
				"//增加坐席个人企微二维码方式展示\n" +
				"var getPanelUrl = function(orderId){\n" +
				"    var policyList = service(\"policy\", \"policy/list.json\", {orderId: orderId});\n" +
				"    var policyNo = try(policyList.list[0].policyNo ,null);\n" +
				"    var r1 = try(req(IYB.SRV_INNER_ORIGIN[\"iyb_middleground\"]+\"wework/getHuoShuiContactWay?insuranceNum=\"+policyNo,null , 25000,'get'),null);\n" +
				"    return r1..result;\n" +
				"}\n" +
				"\n" +
				"var relationValue = 'self';\n" +
				"var relationReadOnly = false;\n" +
				"\n" +
				"return {\n" +
				"    \"widget\": \"page\",\n" +
				"    \"wprops\": {\n" +
				"        \"status\": sts,\n" +
				"        \"title\": \"结果页\",\n" +
				"        \"log\": \"RESULT/\" + order.productId,\n" +
				"        \"className\": \"market-result-page-tb\"\n" +
				"    },\n" +
				"    \"childrens\": {[\n" +
				"        {\n" +
				"            \"widget\": \"new-special-success-box\",\n" +
				"            \"wprops\": {\n" +
				"                \"buttons\": [\n" +
				"                    {\n" +
				"                        \"log\": \"viewPolicy\",\n" +
				"                        \"label\": \"查看保单\",\n" +
				"                        \"type\": \"image-modal\",\n" +
				"                        \"data\": \"https://api.baoinsurance.com/activity/file/CGJDCDBGJDIDCDFDBGHDBGCDGGDDJDEDJDHDADDGEDJDFDBGEDFDIDHDCGEDJDOCAHOGHG20200825183755.png\"\n" +
				"                    }\n" +
				"                ],\n" +
				"                \"options\": [\n" +
				"                    {\n" +
				"                        \"label\": \"保障期限\",\n" +
				"                        \"value\": timestr(time(order.extra.effectiveTime), 'yyyy/MM/dd') + \"-\" + timestr(time(order.extra.expireTime), 'yyyy/MM/dd')\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"label\": \"保费\",\n" +
				"                        \"value\": premiumDesc\n" +
				"                    }\n" +
				"                ],\n" +
				"                \"title\": \"恭喜您投保成功\",\n" +
				"                \"tips\": tips\n" +
				"            }\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"adverts\",\n" +
				"            \"wprops\": {\n" +
				"                \"carouselProp\": {\"showPagination\": false, \"height\": 60},\n" +
				"                \"options\": [\n" +
				"                    {\n" +
				"                        \"mode\": \"normal\",\n" +
				"                        \"imgUrl\": \"http://static.iyb.tm/web/h5/sky/result/icon_gift.png\",\n" +
				"                        \"desc\": \"您有一份礼品待领取\",\n" +
				"                        \"btnText\": \"去领取\",\n" +
				"                        \"log\": \"underwriting/advert\",\n" +
				"                        \"styleObj\": { \"height\": \"50px\" },\n" +
				"                        \"action\": {\n" +
				"                            \"type\": \"url\",\n" +
				"                            \"data\": IYB.WEB_ORIGIN[\"saas_bs_h5\"] +'m/policy/myrights/tbrights/rightlist'\n" +
				"                        }\n" +
				"                    }\n" +
				"                ]\n" +
				"            }\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\":\"panel\",\n" +
				"            \"childrens\":[\n" +
				"                {\n" +
				"                    \"widget\":\"text\",\n" +
				"                    \"dataSource\":{\n" +
				"                        \"value\":\"我是您的专属咨询顾问\"\n" +
				"                    },\n" +
				"                    \"prefixCls\":\"ui-code-text ui-code-text-title\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"widget\":\"text\",\n" +
				"                    \"dataSource\":{\n" +
				"                        \"value\":\"微信在线，方便随时咨询哦\"\n" +
				"                    },\n" +
				"                    \"prefixCls\":\"ui-code-text\"\n" +
				"                },\n" +
				"                {\n" +
				"                    \"widget\":\"orImage\",\n" +
				"                    \"wprops\": {\n" +
				"                        \"align\":\"center\"\n" +
				"                    },\n" +
				"                    \"value\": getPanelUrl(order.id),\n" +
				"                    \"prefixCls\":\"ui-tmode-code\"\n" +
				"                }\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"head-line\",\n" +
				"            \"wprops\": {\n" +
				"                \"options\": [\n" +
				"                    {\n" +
				"                        \"mode\": \"text\",\n" +
				"                        \"label\": \"为家人也准备一份保障\"\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"mode\": \"title\",\n" +
				"                        \"titleImage\": imgUrl + pack.wareId + \"/titleImage.png\",\n" +
				"                        \"description\": \"不限疾病 保险责任内医疗费用\"\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"mode\": \"btn\",\n" +
				"                        \"label\": \"立即投保\",\n" +
				"                        \"src\": \"https://static.iyb.tm/web/h5/sky/market/head_btn.png\",\n" +
				"                        \"log\": \"BTN/HEADLINE\",\n" +
				"                        \"className\": \"market-result-head-btn\"\n" +
				"                    }\n" +
				"                ]\n" +
				"            }\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"hr\",\n" +
				"            \"wprops\": {\n" +
				"                \"style\": {\n" +
				"                    \"height\": \"20px\",\n" +
				"                    \"backgroundColor\": \"#f2f2f2\"\n" +
				"                }\n" +
				"            }\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"form\",\n" +
				"            \"name\": \"form\",\n" +
				"            \"paramType\": \"object\",\n" +
				"            \"childrens\": [\n" +
				"                {\n" +
				"                    \"widget\": \"person-lists\",\n" +
				"                    \"name\": \"insureform\",\n" +
				"                    \"dataSource\": [\n" +
				"                        [\n" +
				"                            {\n" +
				"                                \"widget\": \"person-list\",\n" +
				"                                \"name\": \"insurants\",\n" +
				"                                \"dataBind\": \"detail.insurants\",\n" +
				"                                \"wprops\": {\n" +
				"                                    \"canDelete\": true,\n" +
				"                                    \"isCard\": false,\n" +
				"                                    \"title\": \"为谁投保（被保人）\",\n" +
				"                                    \"className\": \"ui-cr-market-panel\"\n" +
				"                                },\n" +
				"                                \"fields\": [\n" +
				"                                    {\n" +
				"                                        \"widget\": \"switch\",\n" +
				"                                        \"name\": \"relation\",\n" +
				"                                        \"dataBind\": \"relation\",\n" +
				"                                        \"value\": relationValue,\n" +
				"                                        \"hidden\": false,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"\",\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"refreshPrem\": true,\n" +
				"                                            \"readonly\": relationReadOnly,\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"placeholder\": \"请选择\",\n" +
				"                                            \"log\": \"inputInsRelation\",\n" +
				"                                            \"className\": \"ui-switch-market-relation\",\n" +
				"                                            \"detail\": [\n" +
				"                                                [\n" +
				"                                                    \"self\",\n" +
				"                                                    \"本人\"\n" +
				"                                                ],\n" +
				"                                                [\n" +
				"                                                    \"coupon\",\n" +
				"                                                    \"配偶\"\n" +
				"                                                ]\n" +
				"                                            ] + (param.captcha != null ? [] : [[\n" +
				"                                                \"child\",\n" +
				"                                                \"子女\"\n" +
				"                                            ]]) +\n" +
				"                                            [\n" +
				"                                                [\n" +
				"                                                    \"parent\",\n" +
				"                                                    \"父母\"\n" +
				"                                                ]\n" +
				"                                            ],\n" +
				"                                            \"cascade\": {\n" +
				"                                                \"__display__\": {\n" +
				"                                                    \"self\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ],\n" +
				"                                                    \"coupon\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ],\n" +
				"                                                    \"child\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"Appname\",\n" +
				"                                                        \"Appname1\",\n" +
				"                                                        \"AppcertType\",\n" +
				"                                                        \"AppcertNo\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"formtip\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ],\n" +
				"                                                    \"parent\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ]\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请选择与投保人关系\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            }\n" +
				"                                        }\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"switch\",\n" +
				"                                        \"name\": \"SOCIAL_INS\",\n" +
				"                                        \"dataBind\": \"socialIns\",\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"有无社保RDOM(p: {\\\"value\\\": \\\"(含新农合)\\\", \\\"style\\\": {\\\"color\\\": \\\"#999\\\", \\\"fontSize\\\": \\\"12px\\\"}})\",\n" +
				"                                            \"className\": \"ui-market-switch-socialIns noBorderTop\",\n" +
				"                                            \"detail\": [\n" +
				"                                                [\n" +
				"                                                    \"Y\",\n" +
				"                                                    \"有社保\"\n" +
				"                                                ],\n" +
				"                                                [\n" +
				"                                                    \"N\",\n" +
				"                                                    \"无社保\"\n" +
				"                                                ]\n" +
				"                                            ],\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"refreshPrem\": true,\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请选择社保\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputInsSocialIns\"\n" +
				"                                        },\n" +
				"                                        \"value\": \"Y\",\n" +
				"                                        \"hidden\": false\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"input\",\n" +
				"                                        \"name\": \"name\",\n" +
				"                                        \"dataBind\": \"name\",\n" +
				"                                        \"hidden\": false,\n" +
				"                                        \"value\": \"\",\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"姓名\",\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"readonly\": false,\n" +
				"                                            \"placeholder\": \"信息保密，仅用于投保\",\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请输入被保人姓名\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"pattern\": \"^[\\\\u4e00-\\\\u9fa5]{2,4}$\",\n" +
				"                                                        \"message\": \"字数过少或有特殊符号\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputInsName\"\n" +
				"                                        }\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"hidden\",\n" +
				"                                        \"dataBind\": \"certType\",\n" +
				"                                        \"name\": \"certType\",\n" +
				"                                        \"value\": \"SFZ\",\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"cascade\": {\n" +
				"                                                \"__display__\": {\n" +
				"                                                    \"SFZ\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"Appname\",\n" +
				"                                                        \"AppcertType\",\n" +
				"                                                        \"AppcertNo\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"formtip\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ]\n" +
				"                                                },\n" +
				"                                                \"rules\": {\n" +
				"                                                    \"SFZ\": {\n" +
				"                                                        \"validate\": \"isIdCard\",\n" +
				"                                                        \"path\": \"certNo\"\n" +
				"                                                    }\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputInsCertType\",\n" +
				"                                            \"label\": \"证件类型\",\n" +
				"                                            \"detail\": [\n" +
				"                                                [\n" +
				"                                                    \"SFZ\",\n" +
				"                                                    \"身份证\"\n" +
				"                                                ]\n" +
				"                                            ]\n" +
				"                                        },\n" +
				"                                        \"hidden\": false\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"input\",\n" +
				"                                        \"name\": \"certNo\",\n" +
				"                                        \"dataBind\": \"certNo\",\n" +
				"                                        \"value\": \"\",\n" +
				"                                        \"hidden\": false,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"身份证号\",\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"refreshPrem\": true,\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"readonly\": false,\n" +
				"                                            \"hideocr\": true,\n" +
				"                                            \"placeholder\": \"信息保密，仅用于投保\",\n" +
				"                                            \"cascade\": {\n" +
				"                                                \"BIRTHDAY\": {\n" +
				"                                                    \"rule\": \"\",\n" +
				"                                                    \"defaultVal\": \"getBirthday\"\n" +
				"                                                },\n" +
				"                                                \"GENDER\": {\n" +
				"                                                    \"rule\": \"\",\n" +
				"                                                    \"defaultVal\": \"getGender\"\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"validate\": \"isIdCard\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请输入被保人证件号码\"\n" +
				"                                                    },\n" +
				"                                                    {}\n" +
				"                                                ],\n" +
				"                                                \"defaultRules\": [\n" +
				"                                                    {\n" +
				"                                                        \"validate\": \"isIdCard\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"* [百万医疗] 被保险人年龄要求出生满30天-70周岁\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputInsCertNo\"\n" +
				"                                        }\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"form-tip\",\n" +
				"                                        \"name\": \"formtip\",\n" +
				"                                        \"value\": \"为子女投保，需填写投保人(您本人)信息\",\n" +
				"                                        \"wprops\": {},\n" +
				"                                        \"hidden\": true\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"input\",\n" +
				"                                        \"name\": \"Appname\",\n" +
				"                                        \"dataBind\": \"Appname\",\n" +
				"                                        \"value\": \"\",\n" +
				"                                        \"hidden\": true,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"本人姓名\",\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"placeholder\": \"信息保密，仅用于投保\",\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请输入被保人姓名\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"pattern\": \"^[\\\\u4e00-\\\\u9fa5]{2,4}$\",\n" +
				"                                                        \"message\": \"字数过少或有特殊符号\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputAppName\"\n" +
				"                                        }\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"hidden\",\n" +
				"                                        \"dataBind\": \"AppcertType\",\n" +
				"                                        \"name\": \"AppcertType\",\n" +
				"                                        \"value\": \"SFZ\",\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"本人证件类型\",\n" +
				"                                            \"detail\": [\n" +
				"                                                [\n" +
				"                                                    \"SFZ\",\n" +
				"                                                    \"身份证\"\n" +
				"                                                ]\n" +
				"                                            ],\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"cascade\": {\n" +
				"                                                \"__display__\": {\n" +
				"                                                    \"SFZ\": [\n" +
				"                                                        \"relation\",\n" +
				"                                                        \"Appname\",\n" +
				"                                                        \"AppcertType\",\n" +
				"                                                        \"AppcertNo\",\n" +
				"                                                        \"name\",\n" +
				"                                                        \"certType\",\n" +
				"                                                        \"certNo\",\n" +
				"                                                        \"formtip\",\n" +
				"                                                        \"mobile\",\n" +
				"                                                        \"smsCode\",\n" +
				"                                                        \"SOCIAL_INS\",\n" +
				"                                                        \"PAY_FREQ\",\n" +
				"                                                        \"payType2\",\n" +
				"                                                        \"rate\",\n" +
				"                                                        \"premium\",\n" +
				"                                                        \"foldUser\",\n" +
				"                                                        \"nextMonthPremium\",\n" +
				"                                                        \"ADDIT_SERVICE\",\n" +
				"                                                        \"insuredoc\",\n" +
				"                                                        \"renewal\"\n" +
				"                                                    ]\n" +
				"                                                },\n" +
				"                                                \"rules\": {\n" +
				"                                                    \"SFZ\": {\n" +
				"                                                        \"validate\": \"isIdCard\",\n" +
				"                                                        \"path\": \"certNo\"\n" +
				"                                                    }\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputAppCertType\"\n" +
				"                                        },\n" +
				"                                        \"hidden\": true\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"input\",\n" +
				"                                        \"name\": \"AppcertNo\",\n" +
				"                                        \"dataBind\": \"AppcertNo\",\n" +
				"                                        \"value\": \"\",\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"身份证号\",\n" +
				"                                            \"type\": \"string\",\n" +
				"                                            \"refreshPrem\": true,\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"hideocr\": true,\n" +
				"                                            \"placeholder\": \"信息保密，仅用于投保\",\n" +
				"                                            \"cascade\": {\n" +
				"                                                \"BIRTHDAY\": {\n" +
				"                                                    \"rule\": \"\",\n" +
				"                                                    \"defaultVal\": \"getBirthday\"\n" +
				"                                                },\n" +
				"                                                \"GENDER\": {\n" +
				"                                                    \"rule\": \"\",\n" +
				"                                                    \"defaultVal\": \"getGender\"\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"validate\": \"isIdCard\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请输入被保人证件号码\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputAppCertNo\"\n" +
				"                                        },\n" +
				"                                        \"hidden\": true\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"input\",\n" +
				"                                        \"name\": \"mobile\",\n" +
				"                                        \"dataBind\": \"mobile\",\n" +
				"                                        \"value\": \"\",\n" +
				"                                        \"isNoRules\": true,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"手机号码\",\n" +
				"                                            \"type\": \"tel\",\n" +
				"                                            \"readonly\": false,\n" +
				"                                            \"placeholder\": \"信息保密，仅用于投保\",\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"* 您输入的手机号有误\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"pattern\": \"^1[3456789]\\\\d{9}$\",\n" +
				"                                                        \"message\": \"请输入正确的手机号码\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputAppMobile\",\n" +
				"                                            \"maxLength\": 11,\n" +
				"                                            \"eventemitter\": {\n" +
				"                                                \"eventType\": \"SEND_SMSCODE\"\n" +
				"                                            }\n" +
				"                                        },\n" +
				"                                        \"hidden\": false\n" +
				"                                    },\n" +
				"                                    {\n" +
				"                                        \"widget\": \"smscode\",\n" +
				"                                        \"name\": \"smsCode\",\n" +
				"                                        \"isNoRules\": true,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"label\": \"验证码\",\n" +
				"                                            \"type\": \"tel\",\n" +
				"                                            \"log\": \"inputAppSMSCode\",\n" +
				"                                            \"placeholder\": \"请输入验证码\",\n" +
				"                                            \"source\": [\n" +
				"                                                {\n" +
				"                                                    \"key\": \"mobile\"\n" +
				"                                                }\n" +
				"                                            ],\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": true,\n" +
				"                                                        \"message\": \"请输入验证码\"\n" +
				"                                                    },\n" +
				"                                                    {\n" +
				"                                                        \"pattern\": \"^\\\\d{6}$\",\n" +
				"                                                        \"message\": \"验证码为6位数字\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"inputAppSMSCode\",\n" +
				"                                            \"maxLength\": 6,\n" +
				"                                            \"submit\": {\n" +
				"                                                \"params\": {\n" +
				"                                                    \"type\": \"options-ajax\",\n" +
				"                                                    \"needGrantAuth\": true,\n" +
				"                                                    \"log\": \"BTN/SUBMIT\",\n" +
				"                                                    \"data\": {\n" +
				"                                                        \"type\": \"post\",\n" +
				"                                                        \"url\": IYB.WEB_ORIGIN['apitm']+\"sale/v5/transition.json\",\n" +
				"                                                        \"dataType\": \"json\",\n" +
				"                                                        \"data\": {\n" +
				"                                                            \"_eventsType\": \"insure_next_c_a\",\n" +
				"                                                            \"wareId\": pack.wareId,\n" +
				"                                                            \"packId\": pack.id,\n" +
				"                                                            \"waitPageParentId\": order.id,\n" +
				"                                                            \"platType\": null,\n" +
				"                                                            \"channelUserId\": null,\n" +
				"                                                            \"orderId\": null,\n" +
				"                                                            \"oSign\": null\n" +
				"                                                        }\n" +
				"                                                    }\n" +
				"                                                }\n" +
				"                                            },\n" +
				"                                            \"eventemitter\": {\n" +
				"                                                \"eventType\": \"SEND_SMSCODE\"\n" +
				"                                            }\n" +
				"                                        },\n" +
				"                                        \"hidden\": false\n" +
				"                                    },\n" +
				"                                    PageLayout,\n" +
				"                                    PageLay,\n" +
				"                                    {\n" +
				"                                        \"widget\": \"agree-terms\",\n" +
				"                                        \"value\": false,\n" +
				"                                        \"name\": \"insuredoc\",\n" +
				"                                        \"dataBind\": \"insuredoc\",\n" +
				"                                        \"isNoRules\": true,\n" +
				"                                        \"path\": \"insuredoc\",\n" +
				"                                        \"hidden\": false,\n" +
				"                                        \"isNoRules\": true,\n" +
				"                                        \"wprops\": {\n" +
				"                                            \"alwaysShow\": true,\n" +
				"                                            \"label\": \"我确认符合RDOM(popup:{\\\"name\\\":\\\"TBXZ\\\", \\\"value\\\":\\\"投保须知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}})\n" +
				"                                            并接受RDOM(popup:{\\\"name\\\":\\\"JKGZ\\\", \\\"value\\\":\\\"健康告知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"BXTK\\\", \\\"value\\\":\\\"保险条款\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"TBYD\\\", \\\"value\\\":\\\"特别约定\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"DKXY\\\", \\\"value\\\":\\\"授权及代扣服务协议\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"FLB\\\", \\\"value\\\":\\\"费率表\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"ZYB\\\", \\\"value\\\":\\\"职业表\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"MZNR\\\", \\\"value\\\":\\\"责任免除\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"TBRSM\\\", \\\"value\\\":\\\"投保人声明\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"YXBDXL\\\", \\\"value\\\":\\\"免责内容\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"FWSC\\\", \\\"value\\\":\\\"服务手册\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"LPXZ\\\", \\\"value\\\":\\\"理赔须知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"TXYQD\\\", \\\"value\\\":\\\"特效药清单\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                                            RDOM(popup:{\\\"name\\\":\\\"YSXY\\\", \\\"value\\\":\\\"隐私协议\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) \",\n" +
				"                                            \"validate\": {\n" +
				"                                                \"rules\": [\n" +
				"                                                    {\n" +
				"                                                        \"required\": false,\n" +
				"                                                        \"message\": \"请阅读并授权此内容\"\n" +
				"                                                    }\n" +
				"                                                ]\n" +
				"                                            },\n" +
				"                                            \"log\": \"PRODUCTNOTICE\",\n" +
				"                                            \"fetch\": {\n" +
				"                                                \"path\": \"/dict/view.json\",\n" +
				"                                                \"server\": \"iybtm\",\n" +
				"                                                \"method\": \"POST\",\n" +
				"                                                \"data\": {\n" +
				"                                                    \"name\": \"insuredoc\",\n" +
				"                                                    \"company\": pack.vendor.code,\n" +
				"                                                    \"version\": \"1\"\n" +
				"                                                }\n" +
				"                                            }\n" +
				"                                        }\n" +
				"                                    }\n" +
				"                                ]\n" +
				"                            }\n" +
				"                        ]\n" +
				"                    ]\n" +
				"                }\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"fixed-bottom\",\n" +
				"            \"hidden\": false,\n" +
				"            \"wprops\": {\n" +
				"                \"className\": \"market-fixed-bottom\"\n" +
				"            },\n" +
				"            \"childrens\": [\n" +
				"                resultsP,\n" +
				"                {\n" +
				"                    \"widget\": \"fixed-bottom-content\",\n" +
				"                    \"wprops\": {\n" +
				"                        \"mode\": \"btn\",\n" +
				"                        \"type\": \"ajax\",\n" +
				"                        \"label\": \"立即投保\",\n" +
				"                        \"flex\": \"1\",\n" +
				"                        \"needGrantAuth\": true,\n" +
				"                        \"log\": \"BTN/SUBMIT\",\n" +
				"                        \"data\": {\n" +
				"                            \"type\": \"post\",\n" +
				"                            \"url\": IYB.WEB_ORIGIN['apitm']+\"sale/v5/transition.json\",\n" +
				"                            \"dataType\": \"json\",\n" +
				"                            \"data\": {\n" +
				"                                \"_eventsType\": \"insure_next_c_a\",\n" +
				"                                \"wareId\": pack.wareId,\n" +
				"                                \"packId\": pack.id,\n" +
				"                                \"platType\": null,\n" +
				"                                \"channelUserId\": null,\n" +
				"                                \"orderId\": null,\n" +
				"                                \"oSign\": null\n" +
				"                            }\n" +
				"                        }\n" +
				"                    }\n" +
				"                }\n" +
				"            ]\n" +
				"        },\n" +
				"        {\n" +
				"            \"widget\": \"insuredoc-modal\",\n" +
				"            \"name\": \"insuredocModal\",\n" +
				"            \"dataBind\": \"insuredocModal\",\n" +
				"            \"hidden\": false,\n" +
				"            \"wprops\": {\n" +
				"                \"visible\": false,\n" +
				"                \"options\": [\n" +
				"                    {\n" +
				"                        \"widget\": \"close\",\n" +
				"                        \"type\": \"guanbi_\"\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"widget\": \"title\",\n" +
				"                        \"value\": \"温馨提示\"\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"widget\": \"premium\",\n" +
				"                        \"options\": [\n" +
				"                            {\n" +
				"                                \"label\": \"首月保费\",\n" +
				"                                \"value\": \"1.00元\"\n" +
				"                            },\n" +
				"                            {\n" +
				"                                \"widget\": \"item-price\",\n" +
				"                                \"label\": \"次月保费\",\n" +
				"                                \"value\": \"17\",\n" +
				"                                \"suffix\": \"元/月 共11期\"\n" +
				"                            }\n" +
				"                        ]\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"widget\": \"item-desc\",\n" +
				"                        \"fetch\": {\n" +
				"                            \"path\": \"/dict/view.json\",\n" +
				"                            \"server\": \"iybtm\",\n" +
				"                            \"method\": \"POST\",\n" +
				"                            \"data\": {\n" +
				"                                \"name\": \"insuredoc\",\n" +
				"                                \"company\": pack.vendor.code,\n" +
				"                                \"version\": \"1\"\n" +
				"                            }\n" +
				"                        },\n" +
				"                        \"value\": \"我确认符合RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/CDOCFJCGNNPELHIJFOHHOCAHEGGG20211008173943.pdf\\\", \\\"value\\\":\\\"投保须知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}})|\n" +
				"                        并接受RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/DDOCFGAFHLOFKEEFFOHHOCAHEGGG20211008174007.pdf\\\", \\\"value\\\":\\\"健康告知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}})|\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/EDOCNCOEOEDFEFAIIAEFCCNIHKOENNPEJGGJBKAINPOEJAHGAFGJMGBFIPDFNNPEJGGJBGHGODLGOCAHEGGG20211008174858.pdf\\\", \\\"value\\\":\\\"保险条款\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/IDOCJHCHLCCFGKOHKJLFOCAHEGGG20211008174417.pdf\\\", \\\"value\\\":\\\"特别约定\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/IIDGDEHGENJFIFCGGGOEKMDFDOOEDGCGNAHGBKCFPEDFOKLIICBDJCOCAHEGGG20210618104558.pdf\\\", \\\"value\\\":\\\"授权及代扣服务协议\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/CDFGCGCGCDBDHDGGIDDGGDBDADGGGDDDHDEDDDCDGGJDFDEGDDADHDFGIDBGADOCAHOGHG20211021094437.png\\\", \\\"value\\\":\\\"费率表\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/FDOCNCOEOEDFCCNIJGGJJHCHKILGMEAIKBOEIGIIOCAHEGGG20211008174609.pdf\\\", \\\"value\\\":\\\"职业表\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/JDOCDCNILPOENEBFEGGJOCAHEGGG20211008174140.pdf\\\", \\\"value\\\":\\\"责任免除\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/GDOCFJCGNNPEKLOEAPIFOAGGOCAHEGGG20211008174048.pdf\\\", \\\"value\\\":\\\"投保人声明\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/HDOCBHPFNMEFNNPEFFDFIEFGLJCFMIEFPODFNPAIDCNILPOENEBFEGGJEIGHFIBFJLLFOCAHEGGG20211008174119.pdf\\\", \\\"value\\\":\\\"免责内容\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/BDCDOCNMBJOLFHPPOHKBAJIAPPKAMFLKOEIECHJAPPICFHHDCGNAHGBKCFLECGMIBFIAPPCADGBFPHIECHJAPPOCAHEGGG20211008174246.pdf\\\", \\\"value\\\":\\\"服务手册\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/BDADOCGAEHEFNILHIJFOHHOCAHEGGG20211008174203.pdf\\\", \\\"value\\\":\\\"理赔须知\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/BDBDOCACFDADNMJHJHCHPGDIFAOGFFDFOCAHEGGG20211008174224.pdf\\\", \\\"value\\\":\\\"特效药清单\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}}) |\n" +
				"                        RDOM(file:{\\\"href\\\":\\\"https://api.baoinsurance.com/activity/file/CDADCDBDADCDADCDNCNNPEKBAJNCAJGJBMJHPEDFOKLIOCAHEGGG20210625174202.pdf\\\", \\\"value\\\":\\\"隐私协议\\\", \\\"style\\\":{\\\"color\\\":\\\"#2688f6\\\"}})\",\n" +
				"                    },\n" +
				"                    {\n" +
				"                        \"widget\": \"button\",\n" +
				"                        \"log\": \"BTN/PopInsureDoc\",\n" +
				"                        \"value\": \"确认下一步\",\n" +
				"                        \"type\": \"ajax\",\n" +
				"                        \"data\": {\n" +
				"                            \"type\": \"post\",\n" +
				"                            \"url\": IYB.WEB_ORIGIN['apitm']+\"sale/v5/transition.json\",\n" +
				"                            \"dataType\": \"json\",\n" +
				"                            \"data\": {\n" +
				"                                \"_eventsType\": \"insure_next_c_a\",\n" +
				"                                \"wareId\": pack.wareId,\n" +
				"                                \"packId\": pack.id,\n" +
				"                                \"waitPageParentId\": order.id,\n" +
				"                                \"platType\": null,\n" +
				"                                \"channelUserId\": null,\n" +
				"                                \"orderId\": null,\n" +
				"                                \"oSign\": null\n" +
				"                            }\n" +
				"                        }\n" +
				"                    }\n" +
				"                ]\n" +
				"            }\n" +
				"        }\n" +
				"    ]}\n" +
				"}\n";

//		str = "{aaa:'bbb', ccc:{eee:'bb', d:1}}";

		try
		{
			long t1 = System.currentTimeMillis();
			Script[] sss = new Script[1000];
			for (int i=0;i<100;++i)
			{
				sss[i] = Script.scriptOf(str);
				System.out.println(System.currentTimeMillis() - t1);

				str += ";";
			}

			Thread.sleep(2000000);
		}
		catch (SyntaxException e)
		{
			int[] s = e.getRange();
			System.out.println(str.substring(0, s[0]) + " <*" + str.substring(s[0], s[1]) + "*>" + str.substring(s[1]));
		}
	}

	public static void main222(String[] s) throws Exception
	{
		Map m1 = new HashMap<>();
		for (int i=0;i<10000;i++)
			m1.put(i, i);

		Runnable r = new Runnable()
		{
			@Override
			public void run()
			{
				long t1 = System.currentTimeMillis();
				for (int i=10000;i<100000;i++)
					m1.get(ran.nextInt(10000));

				System.out.println(System.currentTimeMillis() - t1);
			}
		};

		for (int i=0;i<100;++i)
		{
			Thread th = new Thread(r);
			th.start();
		}

		Thread th = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long t1 = System.currentTimeMillis();
				for (int i=10000;i<100000;i++)
					m1.put(ran.nextInt(10000), i);

				System.out.println("T:" + (System.currentTimeMillis() - t1));
			}
		});

		th.start();
	}

	public static void main2(String[] s) throws Exception
	{
		int[] p = new int[100];

		int ts = 1000;
		int zz = 0;
		for (int z = 0; z < ts; z++)
		{
			for (int i = 0; i < 100; i++)
				p[i] = i;
			for (int i = 0; i < 100; i++)
			{
				int j = ran.nextInt(100 - i) + i;
				int y = p[i];
				p[i] = p[j];
				p[j] = y;
			}

			if (t(p))
				++zz;
		}

		System.out.println(zz * 100 / ts + "%");
	}

	private static boolean t(int[] p)
	{
		for (int i=0;i<1;i++)
		{
			int m = i;
			int k;
			for (k = 0; k < 50; k++)
			{
				if (p[m] == i)
					break;
				m = p[m];
			}

			if (k >= 50)
				return false;
		}

		return true;
	}
}
